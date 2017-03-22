package com.umessage.letsgo.service.impl.activity;

import com.umessage.letsgo.core.annotation.dataitem.DataItem;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.dao.activity.RoomDao;
import com.umessage.letsgo.domain.po.activity.RoomDetailEntity;
import com.umessage.letsgo.domain.po.activity.RoomEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.GroupEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.activity.request.RoomRequest;
import com.umessage.letsgo.domain.vo.activity.request.vo.RoomDetailVo;
import com.umessage.letsgo.domain.vo.activity.response.RoomResponse;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.team.requset.MemberRequest;
import com.umessage.letsgo.service.api.activity.IRoomDetailService;
import com.umessage.letsgo.service.api.activity.IRoomService;
import com.umessage.letsgo.service.api.security.IWxSendMessageService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITeamService;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RoomServiceImpl implements IRoomService {
	@Resource
	private RoomDao roomMapper;
	@Resource
	private IRoomDetailService roomDetailService;
	@Resource
	private ITeamService teamService;
	@Resource
	private Mapper dozerBeanMapper;
	@Resource
	private IMemberService memberService;
	@Resource
	private IWxSendMessageService wxSendMessageService;

	@Override
	public int addRoom(RoomEntity roomEntity) {
		roomEntity.setRoomType(2);
		roomEntity.setTotalCardCount(1);
		roomEntity.setCardCount(1);
		roomEntity.setCreateTime(new Date());
		roomEntity.setVersion(0l);
		return roomMapper.insert(roomEntity);
	}

	@Override
	public int deleteRoom(RoomEntity roomEntity) {
		return roomMapper.delete(roomEntity.getId());
	}

	@Override
	public int updateRoom(RoomEntity roomEntity) {
		roomEntity.setUpdateTime(new Date());
		return roomMapper.update(roomEntity);
	}

	@Override
	public RoomEntity selectRoom(Long id) {
		return roomMapper.selectRoomById(id);
	}

	@Override
	public CommonResponse createRoom(List<RoomRequest> roomRequests) {
		// 验证团队中的所有成人是否已经分到房间中
		if (!isAllMemberInRoom(roomRequests)) {
			return CommonResponse.createNotAssignedResponse();
		}

		//删除原有分房方案，创建新的。
		deleteRoomAndRoomDetail(roomRequests);
		createRoomAndRoomDetail(roomRequests);

		//发送微信模板消息
		wxSendMessageService.sendCheckInNoticeTemplateMessage(roomRequests);

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setRetMsg("成功保存分房方案！");
		return commonResponse;
	}

	private boolean isAllMemberInRoom(List<RoomRequest> roomRequests) {
		TeamEntity teamEntity = teamService.getTeamByTXGroupId(roomRequests.get(0).getTeamId());
		MemberRequest request = new MemberRequest();
		request.setRole("eq3");
		request.setType(1);
		request.settId(teamEntity.getId());

		List<Long> id1 = new ArrayList<>();
		List<Long> id2 = new ArrayList<>();
		List<MemberEntity> memberEntities = memberService.getMemberList(request);  // 获取成人游客列表
		for (MemberEntity memberEntity : memberEntities) {
			id1.add(memberEntity.getId());
		}

		for (RoomRequest roomRequest : roomRequests) {
			for (RoomDetailVo roomDetailVo : roomRequest.getRoomDetailVos()) {
				id2.add(roomDetailVo.getMemberId());
			}
		}

		return compare(id1, id2);
	}


	public String getMemberName(List<MemberEntity> roomMembers){
		String memberName = "";
		for (int i = 0; i < roomMembers.size(); i++) {
			MemberEntity memberEntity = roomMembers.get(i);
			String realName = memberEntity.getRealName();
			if(StringUtils.isEmpty(realName)){
				if (i < roomMembers.size() - 1){
					memberName = memberName + realName + ",";
				}else {
					memberName = memberName + realName + "。";
				}
			}
		}
		return memberName;
	}


	/**
	 * 队列比较
	 * @param <T>
	 * @param a
	 * @param b
	 * @return
	 */
	private <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
		/*if(a.size() != b.size())
			return false;
		Collections.sort(a);
		Collections.sort(b);
		for(int i=0;i<a.size();i++){
			if(!a.get(i).equals(b.get(i)))
				return false;
		}*/
		Collections.sort(a);
		Collections.sort(b);
		for (int i=0; i<b.size(); i++) {
			for (Iterator iterator = a.iterator(); iterator.hasNext();) {
				Long id = (Long) iterator.next();
				if (id.equals(b.get(i))) {
					iterator.remove();
					break;
				}
			}
		}

		if (CollectionUtils.isEmpty(a)) {
			return true;
		}

		return false;
	}


	/*@Override
	public RoomResponse getTeamRoomDetailList(Long teamId) {
		// 1、获取房间信息以及房间明细信息
		List<RoomEntity> roomList = roomMapper.selectRoomListByTeamId(teamId);

		if (CollectionUtils.isEmpty(roomList)) {
			return RoomResponse.createNotFoundResponse();
		}
		// 2、放入VO实体中
		RoomResponse responses = new RoomResponse();
		responses.setRoomList(roomList);
		return responses;
	}*/

	/**
	 * 获取团队分房情况
	 * @param teamId
	 * @return
     */
	@Override
	@DataItem
	public RoomResponse getTeamRoomDetailList(String teamId, Long userId) {
		// 判断权限
		List<Integer> roles = teamService.roleStatus(teamId, userId);
		Integer teamStatus = teamService.teamStatus(teamId);
		if (teamStatus == null || roles.get(0) ==null || roles.get(1) == null){
			throw new BusinessException(ErrorConstant.NOT_FOUND, "没有发现团队或成员！");
		}
		if (teamStatus == 3 || roles.get(1) == 0) {
			RoomResponse roomResponse = new RoomResponse();
			roomResponse.setTeamStatus(teamStatus);
			roomResponse.setRoleStatus(roles.get(0));
			roomResponse.setAdminStatus(roles.get(1));
			return roomResponse;
		}

		TeamEntity teamEntity = teamService.getTeamByTXGroupId(teamId);

		// 1、获取房间信息,已分组列表及成员，未分组成员
		List<RoomEntity> roomList = getRoomDetailList(teamId);
		List<GroupEntity> groupList = teamService.fetchGroupDetailListByTeamId(teamEntity.getId());
		List<MemberEntity> memberList =	teamService.fetchLeaderAndMemberListByTeamId(teamEntity.getId());
		// 2、从已分组成员的集合中除去已分房的成员。未分组的亦然
		if(!roomList.isEmpty()){
			List<Long> inRoomMemberIdList = getMemberIdList(roomList);
			groupList= fetchGroupsOfNotRoom(groupList,inRoomMemberIdList);
			memberList = fetchMembersOfNotRoom(memberList,inRoomMemberIdList);
		}

		RoomResponse response = new RoomResponse();
		response.setRoomList(roomList);
		response.setGroupList(groupList);
		response.setPersonalList(memberList);
		response.setTeamStatus(teamStatus);
		response.setRoleStatus(roles.get(0));
		response.setAdminStatus(roles.get(1));
		return response;
	}

	/**
	 * 根据团队id获取分房信息
	 * @param teamId
     */
	public List<RoomEntity> getRoomDetailList(String teamId){
		List<RoomEntity> roomList = roomMapper.selectRoomListByTeamId(teamId);
		if(CollectionUtils.isEmpty(roomList)){
			roomList = Collections.emptyList();
		}
		roomList = this.changeRoomPhotoUrl(roomList);
		return roomList;
	}
	/**
	 * 获取已经分房的所有成员的id集合
	 * @param roomList
     */
	public List<Long> getMemberIdList(List<RoomEntity> roomList){
		List<Long> list = new ArrayList<Long>();
		for (RoomEntity room : roomList) {
			List<RoomDetailEntity> roomDetails = room.getRoomDetailList();
			for (RoomDetailEntity detail : roomDetails){
				list.add(detail.getMemberId());
			}
		}
		return list;
	}

	/**
	 *获取已分组并不在分房中的成员所在组（从已分组成员的集合中除去已分房的成员）
	 * @param groupList
	 * @param roomList
     */
	public List<GroupEntity> fetchGroupsOfNotRoom(List<GroupEntity> groupList , List<Long> roomList){
		Iterator<GroupEntity> itGroup = groupList.iterator();
		while (itGroup.hasNext()){
			GroupEntity group = itGroup.next();

			List<MemberEntity> memberList = group.getMemberList();
			Iterator<MemberEntity> itMember = memberList.iterator();
			while (itMember.hasNext()){
				MemberEntity member = itMember.next();
				if(roomList.contains(member.getId())){
					itMember.remove();
				}
			}
			if (memberList.isEmpty())	itGroup.remove();
		}
		return groupList;
	}

	/**
	 *获取未分组并不在分房中的成员所在组（从未分组成员的集合中除去已分房的成员）
	 * @param memberList
	 * @param roomList
     */
	public List<MemberEntity> fetchMembersOfNotRoom(List<MemberEntity> memberList,List<Long> roomList){
		Iterator<MemberEntity> itMember = memberList.iterator();
		while (itMember.hasNext()){
			MemberEntity member = itMember.next();
			if(roomList.contains(member.getId())){
				itMember.remove();
			}
		}
		return memberList;
	}

	/**
	 * 创建分房方案
	 *
	 */
	private void createRoomAndRoomDetail(List<RoomRequest> list) {
		for (RoomRequest request : list) {
			// 1、新建一个房间实体，设置房间人数、房间类型、房卡数量等属性，并保存
			RoomEntity room = new RoomEntity();
			room.setTeamId(request.getTeamId());
			room.setRoomNum(request.getRoomNum());
			room.setCount(request.getRoomDetailVos().size());
			addRoom(room);

			// 2、给每个房间创建房间明细
			for (RoomDetailVo vo : request.getRoomDetailVos()) {
				RoomDetailEntity roomDetail = new RoomDetailEntity();
				roomDetail.setRoomId(room.getId());
				roomDetail.setGroupId(vo.getGroupId());
				roomDetail.setMemberId(vo.getMemberId());
				roomDetailService.addRoomDetail(roomDetail);
			}
		}
	}

	/**
	 * 删除分房方案
	 *
	 */
	private void deleteRoomAndRoomDetail(List<RoomRequest> list) {
		String teamId = list.get(0).getTeamId();

		if(teamId != null){
			List<RoomEntity> roomEntityList = getRoomDetailList(teamId);
			for (RoomEntity room : roomEntityList) {
				for (RoomDetailEntity roomDetail : room.getRoomDetailList()) {
					roomDetailService.deleteRoomDetail(roomDetail);
				}
				this.deleteRoom(room);
			}
		}

	}

	/**
	 * 游客端：获取分房详情
	 * @param teamId
	 * @param userId
     * @return
     */
	@Override
	@DataItem
	public RoomResponse getPersonalRoomDetailList(String teamId, Long userId) {

		// 判断权限
		List<Integer> roles = teamService.roleStatus(teamId, userId);
		Integer teamStatus = teamService.teamStatus(teamId);
		if (teamStatus == null || roles.get(0) ==null || roles.get(1) == null){
			throw new BusinessException(ErrorConstant.NOT_FOUND, "没有发现团队或成员！");
		}
		if (teamService.teamStatus(teamId) == 3) {
			RoomResponse response = new RoomResponse();
			response.setTeamStatus(teamStatus);
			response.setRoleStatus(roles.get(0));
			response.setAdminStatus(roles.get(1));
			return response;
		}

		//查看领队分房详情
		RoomEntity leaderRoomEntity = null;
		List<MemberEntity> leaders = memberService.getLeader(teamId);
		if (leaders != null && leaders.size() > 0){
			MemberEntity memberEntity = leaders.get(0);
			List<RoomEntity> leaderRoomEntities = selectRoomListByMember(memberEntity.getId());
			if (leaderRoomEntities != null && leaderRoomEntities.size()>0){
				leaderRoomEntity = leaderRoomEntities.get(0);
			}
		}

		MemberEntity member =  memberService.getMemberWithTeamIdAndUserId(teamId, userId);
		List<RoomEntity> roomEntities = null;
		List<RoomEntity> roomEntitiesGroup = null;
		if(member.getGroupId() != null && member.getGroupId() != -1){
			//同组的分房
			roomEntities = selectRoomListByGroup(member.getGroupId());
			//同一房间的还要查，把同一组和同一房间的合并
			roomEntitiesGroup = selectRoomListByMember(member.getId());
			//把同一房间的当前用户剔除，只剩下同一房间的其他成员
			if (!CollectionUtils.isEmpty(roomEntitiesGroup)){
				for (int i = 0; i < roomEntitiesGroup.size(); i++) {
					for (int j = 0; j < roomEntitiesGroup.get(i).getRoomDetailList().size(); j++) {
						MemberEntity memberEntity = roomEntitiesGroup.get(i).getRoomDetailList().get(j).getMemberEntity();
						if (userId.equals(memberEntity.getUserId())) {
							roomEntitiesGroup.get(i).getRoomDetailList().remove(j);
						}
					}
				}
				if (!CollectionUtils.isEmpty(roomEntities)){
					List<RoomDetailEntity> roomDetailList = roomEntities.get(0).getRoomDetailList();
					List<RoomDetailEntity> roomDetailListg = roomEntitiesGroup.get(0).getRoomDetailList();
					if (!CollectionUtils.isEmpty(roomDetailList) && !CollectionUtils.isEmpty(roomDetailListg)){
						roomDetailList.addAll(roomDetailListg);
					}
				}
			}
		}else{
			roomEntities = selectRoomListByMember(member.getId());
		}

		Boolean flag = true;
		String roomNumber = "";
		List<MemberEntity> memberEntityList = new ArrayList<>();
		List<RoomDetailEntity> leaderRoomDetailList = null;
		if (!CollectionUtils.isEmpty(roomEntities)) {
			// 找出当前用户并标识
			MemberEntity memberEntity = null;
			for (RoomEntity roomEntity : roomEntities) {
				for (RoomDetailEntity roomDetailEntity : roomEntity.getRoomDetailList()) {
					memberEntity = roomDetailEntity.getMemberEntity();
					if (userId.equals(memberEntity.getUserId())) {
						memberEntity.setIsCurrentUser(1);
						roomNumber = roomEntity.getRoomNum() == null ? "" : roomEntity.getRoomNum();
					} else {
						memberEntity.setIsCurrentUser(0);
					}
				}
				if (leaderRoomEntity != null){
					leaderRoomDetailList = leaderRoomEntity.getRoomDetailList();
					Long id = roomEntity.getId();
					Long idl = leaderRoomEntity.getId();
					Long groupId = memberEntity.getGroupId();
					//如果是同一房间把领队加进来
					if (id.longValue() == idl.longValue()){
						flag = false;
						roomEntity.setRoomDetailList(leaderRoomDetailList);
					}else {//不是同一房间，并且不是同一组
						if (leaderRoomDetailList != null){
							for (int i = 0; i < leaderRoomDetailList.size(); i++) {
								MemberEntity leaderMemberEntity = leaderRoomDetailList.get(i).getMemberEntity();
								Integer role = leaderMemberEntity.getRole();
								Long leaderGroupId = leaderMemberEntity.getGroupId();
								if (role != 1 && leaderGroupId == -1){
									leaderRoomDetailList.remove(i);
								}else if (role != 1 && groupId != -1 && groupId != leaderGroupId){
									leaderRoomDetailList.remove(i);
								}
							}
						}
					}
				}else {
					flag = false;
				}
			}

			//如果不是和领队同一房间，需要把领队分房单独加进去,并且把领队房间的其他成员剔除
			if (flag==true){
				roomEntities.add(leaderRoomEntity);
			}

			// 同组未分房
			if (member.getGroupId() != null && member.getGroupId() != -1) {
				memberEntityList = memberService.getGroupMembertList(member.getGroupId());
				List<Long> inRoomMemberIdList = getMemberIdList(roomEntities);
				memberEntityList = fetchMembersOfNotRoom(memberEntityList, inRoomMemberIdList);
			}
		}


		RoomResponse response = new RoomResponse();
		response.setRoomList(roomEntities);
		response.setPersonalList(memberEntityList);
		response.setCurrentUserRoomNum(roomNumber);
		response.setTeamStatus(teamStatus);
		response.setRoleStatus(roles.get(0));
		response.setAdminStatus(roles.get(1));
		return response;
	}

	public List<RoomEntity> selectRoomListByMember(Long memberId){
		List<RoomEntity> roomList = roomMapper.selectRoomListByMember(memberId);
		if(CollectionUtils.isEmpty(roomList)){
			roomList = Collections.emptyList();
		}
		roomList = this.changeRoomPhotoUrl(roomList);
		return roomList;
	}

	public List<RoomEntity> selectRoomListByGroup(Long groupId){
		List<RoomEntity> roomList = roomMapper.selectRoomListByGroup(groupId);
		if(CollectionUtils.isEmpty(roomList)){
			roomList = Collections.emptyList();
		}
		roomList = this.changeRoomPhotoUrl(roomList);
		return roomList;
	}

	/**
	 * 根据成员ID查询对应的房间
	 * @param memberId
	 * @return
     */
	@Override
	public RoomEntity selectByMemberId(Long memberId){
		return  roomMapper.selectByMemberId(memberId);
	}



	private List<RoomEntity> changeRoomPhotoUrl(List<RoomEntity> roomList){
		for (RoomEntity roomEntity:roomList) {
			List<RoomDetailEntity> roomDetailList = roomEntity.getRoomDetailList();
			for (RoomDetailEntity roomDetailEntity:roomDetailList) {
				MemberEntity memberEntity = roomDetailEntity.getMemberEntity();
				//同步头像
				memberEntity = this.changePhotoUrl(memberEntity);
			}
		}
		return  roomList;
	}


	/**
	 * 设置团队头像地址为用户头像地址
	 * @param memberEntity
	 * @return
	 */
	private MemberEntity changePhotoUrl(MemberEntity memberEntity){
		if(null == memberEntity){
			return memberEntity;
		}
		UserEntity userEntity = memberEntity.getUserEntity();
		if (userEntity != null){
			String photoUrl = userEntity.getPhotoUrl();
			//判断关联用户的头像地址是否为空，不为空设置团队头像地址为用户头像地址
			if (!StringUtils.isEmpty(photoUrl)){
				memberEntity.setPhotoUrl(photoUrl);
			}
		}

		return memberEntity;
	}


	/**
	 * 设置多个团队头像地址为用户头像地址
	 * @param memberList
	 * @return
	 */
	private List<MemberEntity> changePhotoUrlList(List<MemberEntity> memberList){
		for (MemberEntity memberEntity:memberList) {
			//更改团队头像地址为用户头像地址
			memberEntity = this.changePhotoUrl(memberEntity);
		}
		return memberList;
	}

}
