package com.umessage.letsgo.service.impl.team;

import com.qq.tim.ITimRestAPI;
import com.qq.tim.vo.request.DeleteGroupMemberRequest;
import com.umessage.letsgo.core.annotation.dataitem.DataItem;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.core.utils.QueryUtils;
import com.umessage.letsgo.dao.team.MemberDao;
import com.umessage.letsgo.domain.po.activity.RoomDetailEntity;
import com.umessage.letsgo.domain.po.activity.RoomEntity;
import com.umessage.letsgo.domain.po.notice.NoticeDetailEntity;
import com.umessage.letsgo.domain.po.notice.NoticeEntity;
import com.umessage.letsgo.domain.po.security.FriendsEntity;
import com.umessage.letsgo.domain.po.security.RoleEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.security.UserRoleEntity;
import com.umessage.letsgo.domain.po.system.LogManage;
import com.umessage.letsgo.domain.po.team.GroupEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.TouristResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.DataVo;
import com.umessage.letsgo.domain.vo.security.respone.CardResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserAuthorityResponse;
import com.umessage.letsgo.domain.vo.team.requset.MemberAddRequest;
import com.umessage.letsgo.domain.vo.team.requset.MemberRequest;
import com.umessage.letsgo.domain.vo.team.respone.MemberListResponse;
import com.umessage.letsgo.domain.vo.team.respone.MemberResponse;
import com.umessage.letsgo.service.api.activity.IRoomDetailService;
import com.umessage.letsgo.service.api.activity.IRoomService;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.notice.INoticeDetailService;
import com.umessage.letsgo.service.api.notice.INoticeService;
import com.umessage.letsgo.service.api.security.*;
import com.umessage.letsgo.service.api.system.ILogManageService;
import com.umessage.letsgo.service.api.team.IGroupService;
import com.umessage.letsgo.service.api.team.ILeaderService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.common.constant.HelpConstant;
import com.umessage.letsgo.service.common.helper.PhotoHelper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

@Service
public class MemberServiceImpl implements IMemberService {

    private Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	@Resource
	private MemberDao memberMapper;
	@Resource
	private IGroupService groupService;
	@Resource
	private ITeamService teamService;
	@Resource
	private RestTemplate restTemplate;
	@Resource
	private IFriendsService friendsService;
	@Resource
	private ITagsService tagsService;
	@Resource
	private IUserRoleService userRoleService;
	@Resource
	private IRoleService roleService;
	@Resource
	private ILeaderService leaderService;
	@Resource
	private IUserService userService;
	@Resource
	private IScheduleService scheduleService;
	@Resource
	private ITimRestAPI timRestAPI;
	@Resource
	private ILogManageService logManageService;
	@Resource
	private IRoomDetailService roomDetailService;
	@Resource
	private IRoomService roomService;
	@Resource
	private INoticeService noticeService;
	@Resource
	private INoticeDetailService noticeDetailService;

	@Override
	public int addMember(MemberEntity memberEntity) {
		memberEntity.setJoinStatus(0);
		memberEntity.setType(1);
		if(StringUtils.isEmpty(memberEntity.getUserId())){
			memberEntity.setUserId(-1l);
		}else{
			memberEntity.setUserId(memberEntity.getUserId());
		}
		memberEntity.setGroupId(-1l);
		memberEntity.setIsLeader(0);
//		memberEntity.setIsAdmin(0);
		memberEntity.setCreateTime(new Date());
		memberEntity.setVersion(0l);

		// 根据手机号获取归属地（省份）
		if (!StringUtils.isEmpty(memberEntity.getPhone())) {
			String province = getProvince(memberEntity.getPhone());
			memberEntity.setProvince(province);
		}

		return memberMapper.insert(memberEntity);
	}

	@Override
	public int insertMember(MemberEntity memberEntity) {
		memberEntity.setJoinStatus(0);
		memberEntity.setType(1);
		if(StringUtils.isEmpty(memberEntity.getUserId())){
			memberEntity.setUserId(-1l);
		}else{
			memberEntity.setUserId(memberEntity.getUserId());
		}
		memberEntity.setGroupId(-1l);
		memberEntity.setIsLeader(0);
//		memberEntity.setIsAdmin(0);
		memberEntity.setCreateTime(new Date());
		memberEntity.setVersion(0l);

		// 根据手机号获取归属地（省份）
		if (!StringUtils.isEmpty(memberEntity.getPhone())) {
			String province = getProvince(memberEntity.getPhone());
			memberEntity.setProvince(province);
		}

		return memberMapper.insertMember(memberEntity);
	}



	@Override
	public int deleteMember(MemberEntity memberEntity) {
		return memberMapper.delete(memberEntity.getId());
	}

	@Override
	public int updateMember(MemberEntity memberEntity) {
		memberEntity.setUpdateTime(new Date());
		return memberMapper.update(memberEntity);
	}

	@Override
	@DataItem
	public List<MemberEntity> getMemberList(MemberRequest request) {
		List<MemberEntity> memberEntityList = memberMapper.selectMemberListWithConditons(request);
		//更改多个团队头像地址为用户头像地址
		memberEntityList = this.changePhotoUrlList(memberEntityList);
		if (CollectionUtils.isEmpty(memberEntityList)) {
			return Collections.emptyList();
		}
		return memberEntityList;
	}

	@Override
	@DataItem
	public MemberEntity getMember(Long id) {
		MemberEntity memberEntity = memberMapper.select(id);
		if (memberEntity == null) {
			return new MemberEntity();
		}
		//更改团队头像地址为用户头像地址
		memberEntity = this.changePhotoUrl(memberEntity);
		return memberEntity;
	}

	@Override
	@DataItem
	public MemberEntity getMemberWithTeamIdAndUserId(String teamId, Long userId) {
		MemberRequest request = new MemberRequest();
		request.setTeamId(teamId);
		request.setUserId(userId);

		MemberEntity memberEntity = memberMapper.selectMemberWithConditons(request);
		if (memberEntity == null) {
			return new MemberEntity();
		}
		//更改团队头像地址为用户头像地址
		memberEntity = this.changePhotoUrl(memberEntity);
		return memberEntity;
	}

	//领队可修改行程 游客不可修改行程
	@Override
	public MemberEntity getMemberRole(String teamId, Long userId) {
		MemberRequest request = new MemberRequest();
		request.setTeamId(teamId);
		request.setUserId(userId);
		MemberEntity memberEntity = memberMapper.selectMemberWithConditons(request);
		if (memberEntity == null) {
			return new MemberEntity();
		}
		//更改团队头像地址为用户头像地址
		memberEntity = this.changePhotoUrl(memberEntity);
		return memberEntity;
	}
	@Override
	@DataItem
	public MemberEntity getMemberWithTIdAndUserId(Long teamId, Long userId) {
		MemberRequest request = new MemberRequest();
		request.settId(teamId);
		request.setUserId(userId);

		MemberEntity memberEntity = memberMapper.selectMemberWithConditons(request);
		if (memberEntity == null) {
			return new MemberEntity();
		}
		//更改团队头像地址为用户头像地址
		memberEntity = this.changePhotoUrl(memberEntity);
		return memberEntity;
	}
	/**
	 * 根据姓名性别和团号查找用户
	 * @param name
	 * @param sex
	 * @param teamId
	 * @return
	 */
	@Override
	public MemberEntity findMemberByNameSexAndTeamID(String name, int sex, long teamId) {
		MemberEntity memberEntity = new MemberEntity();
		memberEntity.settId(teamId);
		memberEntity.setRealName(name);
		memberEntity.setSex(sex);
		return memberMapper.selectMemberByNameSexAndTeamID(memberEntity);
	}

	/**
	 * 获取某个团队中的领队
	 * @param teamId
	 * @return
     */
	@Override
	@DataItem
	public List<MemberEntity> getLeader(String teamId) {
		MemberRequest request = new MemberRequest();
		request.setTeamId(teamId);
		request.setRole("eq1");

		List<MemberEntity> members = memberMapper.selectMemberListWithConditons(request);
		if (members == null) {
			return new ArrayList<>();
		}
		//更改多个团队头像地址为用户头像地址
		members = this.changePhotoUrlList(members);
		return members;
	}
	/**
	 * 获取某个团队中的领队通过团队id
	 * @param teamId
	 * @return
	 */
	@Override
	public List<MemberEntity> getLeaderByTId(Long teamId) {
		MemberRequest request = new MemberRequest();
		request.settId(teamId);
		request.setRole("eq1");

		List<MemberEntity> members = memberMapper.selectMemberListWithConditons(request);
		if (members == null) {
			return Collections.emptyList();
		}
		//更改多个团队头像地址为用户头像地址
		members = this.changePhotoUrlList(members);
		return members;
	}

	/**
	 * 获取某个团队中的领队的用户ID通过团队id
	 * @param teamId
	 * @return
     */
	@Override
	public List<Long> getLeaderUserIdByTId(Long teamId) {
		MemberRequest request = new MemberRequest();
		request.settId(teamId);
		request.setRole("eq1");
		List<Long> userIds = memberMapper.selectUserIdListWithConditons(request);
		return userIds;
	}

	@Override
	public List<String> getTeamIdsByUserId(Long userId) {
		List<String> teamIds = memberMapper.selectTeamIdsByUserId(userId);
		if (CollectionUtils.isEmpty(teamIds)) {
			return new ArrayList<String>();
		}
		return teamIds;
	}

	@Override
	public List<String> getTeamIdsByUserIdAndAdmin(Long userId) {
		List<String> teamIds = memberMapper.selectTeamIdsByUserIdAndAdmin(userId);
		if (CollectionUtils.isEmpty(teamIds)) {
			return new ArrayList<String>();
		}
		return teamIds;
	}

	/**
	 * 获取当前用户的成员列表
	 * @param userId
	 * @return
     */
	@Override
	@DataItem
	public List<MemberEntity> getMemberListByUserId(Long userId) {
		MemberRequest request = new MemberRequest();
		request.setUserId(userId);
		List<MemberEntity> members = this.getMemberList(request);
		if (CollectionUtils.isEmpty(members))
			return Collections.emptyList();

		return members;
	}

	@Override
	@DataItem
	public List<MemberEntity> getMemberListByTId(Long tId) {
		MemberRequest condition = new MemberRequest();
		condition.settId(tId);
		List<MemberEntity> members = this.getMemberList(condition);
		if (CollectionUtils.isEmpty(members))
			return Collections.emptyList();

		return members;
	}

	/**
	 * 获取团队中的所有成员（包括领队、导游及游客）
	 * @param teamId
	 * @return
     */
	@Override
	public List<MemberEntity> getMemberListByTeamId(String teamId) {
		MemberRequest condition = new MemberRequest();
		condition.setTeamId(teamId);
		List<MemberEntity> members = this.getMemberList(condition);
		if (CollectionUtils.isEmpty(members))
			return Collections.emptyList();

		return members;
	}

	/**
	 * 获取团队的游客列表
	 */
	@Override
	@DataItem
	public List<MemberEntity> getTouristList(String teamId) {
		MemberRequest request = new MemberRequest();
		request.setTeamId(teamId);
		request.setRole("eq3");

		List<MemberEntity> memberEntityList = memberMapper.selectMemberListWithConditons(request);
		if (CollectionUtils.isEmpty(memberEntityList)) {
			return Collections.emptyList();
		}
		//更改多个团队头像地址为用户头像地址
		memberEntityList = this.changePhotoUrlList(memberEntityList);
		return memberEntityList;
	}

	//新的获取团队的游客列表（无论角色是领队还是导游发公告，除了自己本身之外，团队的其他人都要接收到公告。）
	@Override
	@DataItem
	public List<MemberEntity> getTouristListOne(String teamId,Long userId) {
		MemberRequest request = new MemberRequest();
		request.setTeamId(teamId);
		request.setUserId(userId);
        logger.info("userId"+userId);
		List<MemberEntity> memberEntityList = memberMapper.selectMemberListWithConditonsOne(request);
		if (CollectionUtils.isEmpty(memberEntityList)) {
			return Collections.emptyList();
		}
		//更改多个团队头像地址为用户头像地址
		memberEntityList = this.changePhotoUrlList(memberEntityList);
		return memberEntityList;
	}


	/**
	 * 获取团队的游客列表
	 */
	@Override
	@DataItem
	public List<MemberEntity> getTouristList(Long tId) {
		MemberRequest request = new MemberRequest();
		request.settId(tId);
		request.setRole("eq3");

		List<MemberEntity> memberEntityList = memberMapper.selectMemberListWithConditons(request);
		if (CollectionUtils.isEmpty(memberEntityList)) {
			return Collections.emptyList();
		}
		//更改多个团队头像地址为用户头像地址
		memberEntityList = this.changePhotoUrlList(memberEntityList);
		return memberEntityList;
	}

	/**
	 * 获取团队的游客列表的用户ID
	 * @param tId
	 * @return
     */
	@Override
	public List<Long> getMemberUserIdList(Long tId) {
		MemberRequest request = new MemberRequest();
		request.settId(tId);
		request.setRole("eq3");
		List<Long> userIdList = memberMapper.selectUserIdListWithConditons(request);

		return userIdList;
	}

	/**
	 * 获取团队的领队&导游列表
	 * @param teamId
	 * @param isAdmin
	 * @return
	 */

	@Override
	public List<MemberEntity> getTourGuideList(String teamId, Integer isAdmin) {
		MemberRequest request = new MemberRequest();
		request.setTeamId(teamId);
		request.setIsAdmin(isAdmin);
		request.setRole("lt3");
		request.setSort_role("asc");

		List<MemberEntity> memberEntityList = memberMapper.selectMemberListWithConditons(request);
		if (CollectionUtils.isEmpty(memberEntityList)) {
			return new ArrayList<MemberEntity>();
		}
		//更改多个团队头像地址为用户头像地址
		memberEntityList = this.changePhotoUrlList(memberEntityList);
		return memberEntityList;
	}

	@Override
	@DataItem
	public List<MemberEntity> getTourGuideList(Long teamId, Integer isAdmin) {
		MemberRequest request = new MemberRequest();
		request.settId(teamId);
		request.setIsAdmin(isAdmin);
		request.setRole("lt3");
		request.setSort_role("asc");
		request.setSort_isAdmin("desc");
		request.setSort_joinStatus("desc");

		List<MemberEntity> memberEntityList = memberMapper.selectMemberListWithConditons(request);
		if (CollectionUtils.isEmpty(memberEntityList)) {
			return new ArrayList<MemberEntity>();
		}
		//更改多个团队头像地址为用户头像地址
		memberEntityList = this.changePhotoUrlList(memberEntityList);
		return memberEntityList;
	}

	@Override
	public MemberEntity getMemberForGuide(Long teamId,String phone) {
		MemberRequest request = new MemberRequest();
		request.settId(teamId);
		request.setPhone(phone);
		request.setRole("eq2");

		MemberEntity entity = memberMapper.selectMemberWithConditons(request);
		if(entity == null){
			entity = new MemberEntity();
		}
		//更改团队头像地址为用户头像地址
		entity = this.changePhotoUrl(entity);
		return entity;
	}
	/**
	 * 获取小组的组员列表，默认第一个为小组长
	 */
	@Override
	public List<MemberEntity> getGroupMembertList(Long groupId) {
		MemberRequest request = new MemberRequest();
		request.setGroupId(groupId);
		request.setSort_isLeader("desc");
		request.setSort_joinStatus("desc");

		List<MemberEntity> memberEntityList = memberMapper.selectMemberListWithConditons(request);
		if (CollectionUtils.isEmpty(memberEntityList)) {
			return Collections.emptyList();
		}
		//更改多个团队头像地址为用户头像地址
		memberEntityList = this.changePhotoUrlList(memberEntityList);
		return memberEntityList;
	}

	/**
	 * 判断该手机号的用户是否参团
	 * @param phone
	 * @return
     */
	@Override
	@DataItem
	public List<MemberEntity> hasMemberInTeam(String phone) {
		List<MemberEntity> members = memberMapper.selectMemberWithValidTeam(phone);
		//更改多个团队头像地址为用户头像地址
		members = this.changePhotoUrlList(members);
		return members;
	}

	/**
	 * 设置管理权限（设置管理员）
	 * @param id
	 * @param isAdmin
     * @return
     */
	@Override
	public CommonResponse setAdministrator(Long id, Integer isAdmin) {
		// 1、获取该团队的该成员
		MemberEntity memberEntity = this.getMember(id);
		if (null == memberEntity) {
			return CommonResponse.createNotFoundResponse("没有找到对应的成员");
		}

		// 设置管理员时，检查团队中是否已经存在有管理权限的导游，如有，则返回错误信息
		if (isAdmin == 1) {
			List<MemberEntity> memberEntities = this.getTourGuideList(memberEntity.getTeamId(), 1);
			for (MemberEntity member : memberEntities) {
				if (member.getRole() == 2) {
					String msg = "请取消管理员权限，重新录入。";
					return CommonResponse.createNotFoundResponse(msg);
				}
			}
		}

		// 2、设置"是否为管理员"字段；为1，表示为是管理员，具有管理员权限；为0，表示不是管理员
		memberEntity.setIsAdmin(isAdmin);
		this.updateMember(memberEntity);

		// 3、判断是设置管理员还是取消管理员，设置提示信息
		CommonResponse commonResponse = new CommonResponse();
		if (1 == isAdmin) {
			commonResponse.setRetMsg("设置管理员成功！");
		} else {
			commonResponse.setRetMsg("取消管理员成功！");
		}
		return commonResponse;
	}

	private void getIsPassword(UserEntity userEntity) {
		if (org.apache.commons.lang.StringUtils.isEmpty(userEntity.getPassword())) {
			userEntity.setIsPass(0);
		} else {
			userEntity.setIsPass(1);
		}
	}

	private void getRoleList(UserEntity userEntity, List<UserRoleEntity> userRoleEntityList) {
		List<RoleEntity> roleList = new ArrayList<>();
		for (UserRoleEntity userRoleEntity : userRoleEntityList) {
			RoleEntity role = roleService.get(userRoleEntity.getRoleId());
			roleList.add(role);
		}
		userEntity.setRoleList(roleList);
	}

	/**
	 * 获取该用户的所有证件信息
	 * @param userEntity
	 */
	private void getCardList(UserEntity userEntity) {
		// 获取用户的证件信息
		List<MemberEntity> memberList = this.getMemberListByUserId(userEntity.getId());
		List<CardResponse> cards = new ArrayList<>();
		for (MemberEntity mem : memberList) {
			if (org.apache.commons.lang.StringUtils.isEmpty(mem.getCardType())) {
				continue;
			}
			CardResponse card = new CardResponse();
			card.setCardType(mem.getCardType());
			card.setCardNum(mem.getCardNum());
			card.setCardTime(mem.getCardTime());
			cards.add(card);
		}
		// 去重
		List<CardResponse> lastCards = new ArrayList<>(new HashSet(cards));
		userEntity.setCardInfoList(lastCards);
	}

	/**
	 * 获取团队成员的详情
	 * 该接口用于团队成员列表中的查看成员信息
	 * @param id
	 * @return
     */
	@Override
	@DataItem
	public MemberResponse getMemberDetail(Long id, Long userId) {
		// 1、根据成员ID，获取该成员信息
		MemberEntity member = this.getMember(id);
		if (null == member.getId()) {
			return MemberResponse.createNotFoundResponse();
		}

		// 2、如果成员是当前用户，标识为1；否则标识为0
		member.setIsCurrentUser(0);
		if (member.getUserId().equals(userId)) {
			member.setIsCurrentUser(1);
		}

		// 判断是否在同一个小组
		MemberEntity currentUserMember = this.getMemberWithTIdAndUserId(member.gettId(), userId);
		if (currentUserMember.getGroupId() == -1 || member.getGroupId() == -1) {
			member.setIsPartner(0);
		} else if (currentUserMember.getGroupId().equals(member.getGroupId())) {
			member.setIsPartner(1);
		} else {
			member.setIsPartner(0);
		}

		// 3、如果成员在某个小组，设置小组名称及组长
		if (member.getGroupId() != -1) {
			GroupEntity group = groupService.getGroupMemberList(member.getGroupId());
			if (group != null) {
				member.setGroupName(group.getName());	// 小组名称
				member.setGroupLeaderPhone(group.getMemberList().get(0).getPhone());	// 设置小组组长电话
			}
		}

		// 检查是否有账号
		if (member.getUserId() != -1) {
			UserEntity userEntity = member.getUserEntity();
			// 判断当前登录用户和查看详情用户是否为好友关系
			FriendsEntity friendsEntity = friendsService.getFriendByUserId(userId, member.getUserEntity().getUserName());
			if (friendsEntity != null) {
				// 为好友关系
				userEntity.setIsFriend(1);
				userEntity.setFriendType(friendsEntity.getFriendType());

				// 获取标签
				List<String> tagNameList = new ArrayList<>();
				tagNameList = tagsService.getTagNameByUserId(member.getUserId(), userId);
				userEntity.setTagList(tagNameList);
			} else {
				// 不是好友
				userEntity.setIsFriend(0);
			}

			// 获取同团记录
			List<String> teamList = new ArrayList<>();
			teamList = teamService.getSameTeamByUserId(member.getUserId(), userId);
			member.getUserEntity().setSameTeamList(teamList);

			// 判断用户是否有密码
			this.getIsPassword(userEntity);

			// 获取用户的证件信息
			this.getCardList(userEntity);

			// 获取用户身份信息
			List<UserRoleEntity> userRoleEntityList = userRoleService.getUserRoleByUserId(userEntity.getId());
			this.getRoleList(userEntity, userRoleEntityList);
			userEntity.setUserInfo(userEntity);
			userEntity.setRole(member.getRole());
			// 如果是领队或者导游，获取擅长线路、擅长语种、自我评价
			//版本  合并不需要判断 用户都可以获取
//			for (UserRoleEntity userRoleEntity : userRoleEntityList) {
//				//if (userRoleEntity.getRoleId() == 1 || userRoleEntity.getRoleId() == 2) {
//				if(userRoleEntity!=null) {
//					userEntity.setRole(userRoleEntity.getRoleId().intValue());
//				}else{
//					userEntity.setRole(3);
//				}
//					break;
////				}else{
////					userEntity.setRole(3);
////				}
//			}
		}

		// 4、设置响应信息
		MemberResponse memberResponse = new MemberResponse();
		memberResponse.setMemberEntity(member);

		Integer teamStatus = teamService.teamStatus(currentUserMember.getTeamId());
		if (teamStatus == null || currentUserMember.getRole() ==null || currentUserMember.getIsAdmin() == null){
			throw new BusinessException(ErrorConstant.NOT_FOUND, "没有发现团队或成员！");
		}
		memberResponse.setRoleStatus(currentUserMember.getRole());
		memberResponse.setAdminStatus(currentUserMember.getIsAdmin());
		memberResponse.setTeamStatus(teamStatus);
		return memberResponse;
	}


	/**
	 * 根据团队id和用户id获取团队成员的详情
	 * 该接口用于通知，集合，公告中的点击头像查看成员信息
	 * @param teamId
	 * @return
	 */
	@Override
	@DataItem
	public MemberResponse getMemberDetailByUserId(String teamId, Long mUserId, Long userId) {
		// 1、根据成员ID，获取该成员信息
		MemberEntity member = this.getMemberWithTeamIdAndUserId(teamId, mUserId);
		if (null == member.getId()) {
			return MemberResponse.createNotFoundResponse();
		}

		// 2、如果成员是当前用户，标识为1；否则标识为0
		member.setIsCurrentUser(0);
		if (member.getUserId().equals(userId)) {
			member.setIsCurrentUser(1);
		}

		// 判断是否在同一个小组
		MemberEntity currentUserMember = this.getMemberWithTIdAndUserId(member.gettId(), userId);
		if (currentUserMember.getGroupId() == -1 || member.getGroupId() == -1) {
			member.setIsPartner(0);
		} else if (currentUserMember.getGroupId().equals(member.getGroupId())) {
			member.setIsPartner(1);
		} else {
			member.setIsPartner(0);
		}

		// 3、如果成员在某个小组，设置小组名称及组长
		if (member.getGroupId() != -1) {
			GroupEntity group = groupService.getGroupMemberList(member.getGroupId());
			if (group != null) {
				member.setGroupName(group.getName());	// 小组名称
				member.setGroupLeaderPhone(group.getMemberList().get(0).getPhone());	// 设置小组组长电话
			}
		}

		// 检查是否有账号
		if (member.getUserId() != -1) {
			UserEntity userEntity = member.getUserEntity();
			// 判断当前登录用户和查看详情用户是否为好友关系
			FriendsEntity friendsEntity = friendsService.getFriendByUserId(userId, member.getUserEntity().getUserName());
			if (friendsEntity != null) {
				// 为好友关系
				userEntity.setIsFriend(1);
				userEntity.setFriendType(friendsEntity.getFriendType());

				// 获取标签
				List<String> tagNameList = new ArrayList<>();
				tagNameList = tagsService.getTagNameByUserId(member.getUserId(), userId);
				userEntity.setTagList(tagNameList);
			} else {
				// 不是好友
				userEntity.setIsFriend(0);
			}

			// 获取同团记录
			List<String> teamList = new ArrayList<>();
			teamList = teamService.getSameTeamByUserId(member.getUserId(), userId);
			member.getUserEntity().setSameTeamList(teamList);

			// 判断用户是否有密码
			this.getIsPassword(userEntity);

			// 获取用户的证件信息
			this.getCardList(userEntity);

			// 获取用户身份信息
			List<UserRoleEntity> userRoleEntityList = userRoleService.getUserRoleByUserId(userEntity.getId());
			this.getRoleList(userEntity, userRoleEntityList);
			userEntity.setUserInfo(userEntity);
			userEntity.setRole(member.getRole());
			// 如果是领队或者导游，获取擅长线路、擅长语种、自我评价
			//版本  合并不需要判断 用户都可以获取
//			for (UserRoleEntity userRoleEntity : userRoleEntityList) {
//				//if (userRoleEntity.getRoleId() == 1 || userRoleEntity.getRoleId() == 2) {
//				if(userRoleEntity!=null) {
//					userEntity.setRole(userRoleEntity.getRoleId().intValue());
//				}else{
//					userEntity.setRole(3);
//				}
//					break;
////				}else{
////					userEntity.setRole(3);
////				}
//			}
		}

		// 4、设置响应信息
		MemberResponse memberResponse = new MemberResponse();
		memberResponse.setMemberEntity(member);

		Integer teamStatus = teamService.teamStatus(currentUserMember.getTeamId());
		if (teamStatus == null || currentUserMember.getRole() ==null || currentUserMember.getIsAdmin() == null){
			throw new BusinessException(ErrorConstant.NOT_FOUND, "没有发现团队或成员！");
		}
		memberResponse.setRoleStatus(currentUserMember.getRole());
		memberResponse.setAdminStatus(currentUserMember.getIsAdmin());
		memberResponse.setTeamStatus(teamStatus);
		return memberResponse;
	}


	/**
	 * 根据团队id和userName获取团队成员的详情
	 * 该接口用于聊天中的点击头像查看成员信息
	 * @param teamId
	 * @return
	 */
	@Override
	@DataItem
	public MemberResponse getMemberDetailByUserName(String teamId, String userName, Long userId) {
		UserEntity userEntityByName = userService.selecUserByUserName(userName);
		if (userEntityByName == null){
			return MemberResponse.createNotFoundResponse();
		}

		// 1、根据成员ID，获取该成员信息
		MemberEntity member = this.getMemberWithTeamIdAndUserId(teamId, userEntityByName.getId());
		if (null == member.getId()) {
			return MemberResponse.createNotFoundResponse();
		}

		// 2、如果成员是当前用户，标识为1；否则标识为0
		member.setIsCurrentUser(0);
		if (member.getUserId().equals(userId)) {
			member.setIsCurrentUser(1);
		}

		// 判断是否在同一个小组
		MemberEntity currentUserMember = this.getMemberWithTIdAndUserId(member.gettId(), userId);
		if (currentUserMember.getGroupId() == -1 || member.getGroupId() == -1) {
			member.setIsPartner(0);
		} else if (currentUserMember.getGroupId().equals(member.getGroupId())) {
			member.setIsPartner(1);
		} else {
			member.setIsPartner(0);
		}

		// 3、如果成员在某个小组，设置小组名称及组长
		if (member.getGroupId() != -1) {
			GroupEntity group = groupService.getGroupMemberList(member.getGroupId());
			if (group != null) {
				member.setGroupName(group.getName());	// 小组名称
				member.setGroupLeaderPhone(group.getMemberList().get(0).getPhone());	// 设置小组组长电话
			}
		}

		// 检查是否有账号
		if (member.getUserId() != -1) {
			UserEntity userEntity = member.getUserEntity();
			// 判断当前登录用户和查看详情用户是否为好友关系
			FriendsEntity friendsEntity = friendsService.getFriendByUserId(userId, member.getUserEntity().getUserName());
			if (friendsEntity != null) {
				// 为好友关系
				userEntity.setIsFriend(1);
				userEntity.setFriendType(friendsEntity.getFriendType());

				// 获取标签
				List<String> tagNameList = new ArrayList<>();
				tagNameList = tagsService.getTagNameByUserId(member.getUserId(), userId);
				userEntity.setTagList(tagNameList);
			} else {
				// 不是好友
				userEntity.setIsFriend(0);
			}

			// 获取同团记录
			List<String> teamList = new ArrayList<>();
			teamList = teamService.getSameTeamByUserId(member.getUserId(), userId);
			member.getUserEntity().setSameTeamList(teamList);

			// 判断用户是否有密码
			this.getIsPassword(userEntity);

			// 获取用户的证件信息
			this.getCardList(userEntity);

			// 获取用户身份信息
			List<UserRoleEntity> userRoleEntityList = userRoleService.getUserRoleByUserId(userEntity.getId());
			this.getRoleList(userEntity, userRoleEntityList);
			userEntity.setUserInfo(userEntity);
			userEntity.setRole(member.getRole());
			// 如果是领队或者导游，获取擅长线路、擅长语种、自我评价
			//版本  合并不需要判断 用户都可以获取
//			for (UserRoleEntity userRoleEntity : userRoleEntityList) {
//				//if (userRoleEntity.getRoleId() == 1 || userRoleEntity.getRoleId() == 2) {
//				if(userRoleEntity!=null) {
//					userEntity.setRole(userRoleEntity.getRoleId().intValue());
//				}else{
//					userEntity.setRole(3);
//				}
//					break;
////				}else{
////					userEntity.setRole(3);
////				}
//			}
		}

		// 4、设置响应信息
		MemberResponse memberResponse = new MemberResponse();
		memberResponse.setMemberEntity(member);

		Integer teamStatus = teamService.teamStatus(currentUserMember.getTeamId());
		if (teamStatus == null || currentUserMember.getRole() ==null || currentUserMember.getIsAdmin() == null){
			throw new BusinessException(ErrorConstant.NOT_FOUND, "没有发现团队或成员！");
		}
		memberResponse.setRoleStatus(currentUserMember.getRole());
		memberResponse.setAdminStatus(currentUserMember.getIsAdmin());
		memberResponse.setTeamStatus(teamStatus);
		return memberResponse;
	}


	@Override
	public CommonResponse setMarkPhone(Long id, String phone) {
		// 1、根据成员ID，获取成员信息
		MemberEntity member = memberMapper.select(id);
		if (null == member) {
			return CommonResponse.createNotFoundResponse("没有找到对应的成员");
		}
		//更改团队头像地址为用户头像地址
		member = this.changePhotoUrl(member);

		// 2、设置手机号,更新成员信息
		member.setMarkPhone(phone);
		this.updateMember(member);

		// 3、判断是添加手机号还是删除手机号，设置返回码及提示信息
		CommonResponse commonResponse = new CommonResponse();
		if ("".equals(phone)) {
			commonResponse.setRetMsg("成功删除手机号！");
		} else {
			commonResponse.setRetMsg("成功设置手机号！");
		}

		return commonResponse;
	}
	@Override
	public boolean memberPhoneIsRepeat(String phone,Long tId){
		MemberRequest request = new MemberRequest();
		request.setPhone(phone);
		request.settId(tId);
		MemberEntity entity =memberMapper.selectMemberWithConditons(request);
		//更改团队头像地址为用户头像地址
		if(entity!=null) {
			entity = this.changePhotoUrl(entity);
		}
		return entity != null;
	}

	/**
	 * 判断手机号在所有领队或导游中是否存在
	 * @param phone
	 * @param tId
     * @return
     */
	@Override
	public boolean leaderPhoneIsRepeat(String phone,Long tId){
		MemberRequest request = new MemberRequest();
		request.setPhone(phone);
		request.settId(tId);
		request.setRole("eq3");
		List<MemberEntity> entitys =memberMapper.selectLeaderAndGuideWithConditons(request);
		if(CollectionUtils.isEmpty(entitys)){
			entitys = Collections.emptyList();
		}
		//更改多个团队头像地址为用户头像地址
		entitys = this.changePhotoUrlList(entitys);
		return entitys.size() > 0;
	}

	@Override
	public boolean memberPhoneIsRepeat(String phone){
		MemberRequest request = new MemberRequest();
		request.setPhone(phone);
		request.setRole("eq3");
		List<MemberEntity> entity =memberMapper.selectMemberListWithConditons(request);
		//更改多个团队头像地址为用户头像地址
		if(entity!=null) {
			entity = this.changePhotoUrlList(entity);
		}
		return !CollectionUtils.isEmpty(entity);
	}

	@Override
	public UserAuthorityResponse getUserAuthority(String teamId, Long userId) {

		MemberEntity member = this.getMemberWithTeamIdAndUserId(teamId, userId);
		if (member.getId() == null) {
			return UserAuthorityResponse.createNotFoundResponse();
		}

		TeamEntity teamEntity = teamService.getTeamByTXGroupId(teamId);

		UserAuthorityResponse response = new UserAuthorityResponse();
		response.setTeamStatus(teamEntity.getStatus());
		response.setMemberRole(member.getRole());
		response.setIsAdmin(member.getIsAdmin());

		return response;
	}
	@Override
	public List<MemberEntity> getLeaderMembers(Long tId){
		List<MemberEntity> list = memberMapper.getLeaderMembersByLeaderId(tId);
		//更改多个团队头像地址为用户头像地址
		list = this.changePhotoUrlList(list);
		if(CollectionUtils.isEmpty(list)){
			list = Collections.emptyList();
		}
		return list;
	}

	@Override
	public List<Long> getUserIdInTId(Long tId) {
		List<Long> userIds = memberMapper.selectUserIdWithTId(tId);
		return userIds;
	}

	@Override
	public List<MemberEntity> getMemberByTIds(List<Long> tIds) {
		List<MemberEntity> memberEntityList = memberMapper.selectSomeWithTIds(tIds);
		if (memberEntityList.size()>0){
			memberEntityList = this.changePhotoUrlList(memberEntityList);
		}
		return memberEntityList;
	}

	@Override
	public TouristResponse analyzeTourist(List<Long> tIds) {
		List<MemberEntity> memberEntityList = this.getMemberByTIds(tIds);

		List<DataVo> sexDataList = countSexNum(memberEntityList);
		List<DataVo> ageDataList = countAgeNum(memberEntityList);
		List<DataVo> areaDataList = countAreaNum(memberEntityList);

		TouristResponse response = new TouristResponse();
		response.setSexDataList(sexDataList);
		response.setAgeDataList(ageDataList);
		response.setAreaDataList(areaDataList);
		response.setTotalCount(memberEntityList.size());
		return response;
	}

	private List<DataVo> countSexNum(List<MemberEntity> memberEntityList) {
		Map<String, Integer> map = new HashMap<>();

		for (MemberEntity memberEntity : memberEntityList) {
			int sex = memberEntity.getSex();
			if (sex == 1) {
				map = this.count("男", map);
			} else {
				map = this.count("女", map);
			}
		}

		List<DataVo> list = this.calculateRate(map, memberEntityList.size());
		return list;
	}

	private List<DataVo> countAgeNum(List<MemberEntity> memberEntityList) {
		Map<String, Integer> map = new HashMap<>();

		for (MemberEntity memberEntity : memberEntityList) {
			if (StringUtils.isEmpty(memberEntity.getBirthday())) {
				continue;
			}

			int age = 0;
			try {
				Date birthday = DateUtils.parseDate(memberEntity.getBirthday(), DateUtils.DATE_FORMAT_DATEONLY);
				age = DateUtils.getAge(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (age > 0 && age <= 10) {
				map = this.count("0~10岁", map);
			} else if (age > 10 && age <= 20) {
				map = this.count("10~20岁", map);
			} else if (age > 20 && age <= 30) {
				map = this.count("20~30岁", map);
			} else if (age > 30 && age <= 40) {
				map = this.count("30~40岁", map);
			} else if (age > 40 && age <= 50) {
				map = this.count("40~50岁", map);
			} else if (age > 50 && age <= 60) {
				map = this.count("50~60岁", map);
			} else if (age > 60) {
				map = this.count("60岁以上", map);
			}

		}

		List<DataVo> list = this.calculateRate(map, memberEntityList.size());
		return list;
	}

	private List<DataVo> countAreaNum(List<MemberEntity> memberEntityList) {
		Map<String, Integer> map = new HashMap<>();
		for (MemberEntity memberEntity : memberEntityList) {
			if (StringUtils.isEmpty(memberEntity.getProvince())) {
				continue;
			}

			map = this.count(memberEntity.getProvince(), map);

		}
		List<DataVo> list = this.calculateRate(map, memberEntityList.size());
		return list;
	}

	private Map<String, Integer> count(String key, Map<String, Integer> map) {
		if (map.containsKey(key)) {
			int count = map.get(key) + 1;
			map.put(key, count);
		} else {
			map.put(key, 1);
		}

		return map;
	}

	private List<DataVo> calculateRate(Map<String, Integer> map, int total) {
		List<DataVo> list = new ArrayList<>();
		for (String key : map.keySet()) {
			DataVo dataVo = new DataVo();
			dataVo.setDataName(key);
			dataVo.setDataCount(map.get(key));
			DecimalFormat df = new DecimalFormat("#.00");
			double rate = map.get(key) * 1.0 / total * 100;
			dataVo.setDataRate(Double.valueOf(df.format(rate)));

			list.add(dataVo);
		}

		return list;
	}


	//根据userId获取团队ID集合
	public List<Long> getTeamIds(Long userId){
      return  memberMapper.getTeamIds(userId);
	}

	@Override
	public int getMemberCountInTravel(Long travelId, Integer status) {
		MemberRequest request = new MemberRequest();
		request.setTravelId(travelId);
		request.setTeamStatus(status);
		if(status ==1 ){
			request.setStartDate(new Date());
		}
		int count = memberMapper.getMemberCountByStatus(request);
		return count;
	}




	/**
	 * 根据手机号获取省份
	 * @param phone
	 * @return
     */
	private String getProvince(String phone) {
		if (phone.startsWith("+86")) {
			phone = phone.substring(3);
		}

		String taobaoUrl = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=" + phone;
		String result = restTemplate.getForObject(taobaoUrl, String.class);
		result = result.substring(result.indexOf('{') + 1, result.indexOf('}'));
		String[] array = result.split(",");

		String province = "";
		for (int i = 0; i < array.length; i++) {
			String attr = array[i].trim();

			if (attr.startsWith("province")) {
				province = attr.substring(attr.indexOf('\'') + 1, attr.lastIndexOf('\''));
				break;
			}
		}

		return province;
	}

	/**
	 * 设置团队头像地址为用户头像地址 lizhen
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

	//获取没有电话号码的团员
	public MemberListResponse getNoPhoneMembers(Long teamId){
		MemberListResponse response = new MemberListResponse();
		List<MemberEntity> memberEntityList =memberMapper.getNoPhoneMembers(teamId);
		if (memberEntityList.size()>0){
			memberEntityList = this.changePhotoUrlList(memberEntityList);
		}
		response.setMemberList(memberEntityList);
        return  response;
	}

	//通过扫描二维码快速入团
	public CommonResponse addMemberByQR(MemberAddRequest request){
		MemberEntity memberEntity = new MemberEntity();
		UserEntity userEntity =request.getUser();
		//游客在团队中手机号是否重复
		if (userEntity.getPhone() != null && userEntity.getPhone()!="".toString() ){
			boolean   phoneIsRepeat = memberPhoneIsRepeat(QueryUtils.getPhone(userEntity.getPhone()),request.gettId());
			if(phoneIsRepeat){
				return CommonResponse.createNotFoundResponse("手机号已存在");
			}
		}
		memberEntity.setPhone(userEntity.getPhone());
		memberEntity.settId(request.gettId());
		memberEntity.setTeamId(request.getTeamId());
		memberEntity.setRealName(request.getRealName());
		memberEntity.setSex(userEntity.getSex());
		memberEntity.setPhotoUrl(userEntity.getPhotoUrl());
		memberEntity.setRole(3);
		memberEntity.setIsNewJoin(1);
		memberEntity.setJoinStatus(1);
		memberEntity.setType(1);
		memberEntity.setUserId(userEntity.getId());
		memberEntity.setGroupId(-1l);
		memberEntity.setIsLeader(0);
		memberEntity.setIsAdmin(0);
		memberEntity.setCreateTime(new Date());
		memberEntity.setVersion(0l);
		// 根据手机号获取归属地（省份）
		if (!StringUtils.isEmpty(userEntity.getPhone())) {
			String province = getProvince(userEntity.getPhone());
			memberEntity.setProvince(province);
		}
		memberMapper.insert(memberEntity);
		TeamEntity teamEntity = teamService.selectById(request.gettId());
		if (teamEntity != null){
			Integer totalCount = teamEntity.getTotalCount();
			totalCount = totalCount + 1;
			teamEntity.setTotalCount(totalCount);
			teamService.updateTeam(teamEntity);
		}
		MemberEntity member = getMember(memberEntity.getId());
		//加入腾迅云群组，并加好友
		scheduleService.addTXGroup(member, userEntity, member.getTeamId());
		//发送欢迎加入信息
		userService.sendInvitationMessage(memberEntity,request.getTeamId());
		return new CommonResponse();
	}

	//通过扫描二维码快速入团--选自己名字加入
	public CommonResponse updateMemberByQR(MemberAddRequest request){
		MemberEntity memberEntity = getMember(request.getId());
		if (null == memberEntity) {
			return CommonResponse.createNotFoundResponse("没有找到对应的成员");
		}
		UserEntity userEntity =request.getUser();
		//游客在团队中手机号是否重复
		if (userEntity.getPhone() != null && userEntity.getPhone()!="".toString() ){
			boolean   phoneIsRepeat = memberPhoneIsRepeat(QueryUtils.getPhone(userEntity.getPhone()),request.gettId());
			if(phoneIsRepeat){
				return CommonResponse.createNotFoundResponse("手机号已存在");
			}
		}
		memberEntity.setPhone(userEntity.getPhone());
		memberEntity.setTeamId(request.getTeamId());
		memberEntity.setPhotoUrl(userEntity.getPhotoUrl());
		memberEntity.setIsNewJoin(0);
		memberEntity.setJoinStatus(1);
		memberEntity.setUserId(userEntity.getId());
		// 根据手机号获取归属地（省份）
		if (!StringUtils.isEmpty(userEntity.getPhone())) {
			String province = getProvince(userEntity.getPhone());
			memberEntity.setProvince(province);
		}
		updateMember(memberEntity);
		TeamEntity teamEntity = teamService.selectById(request.gettId());
		if (teamEntity != null){
			Integer totalCount = teamEntity.getTotalCount();
			totalCount = totalCount + 1;
			teamEntity.setTotalCount(totalCount);
			teamService.updateTeam(teamEntity);
		}
		MemberEntity member = getMember(memberEntity.getId());
		//加入腾迅云群组，并加好友
		scheduleService.addTXGroup(member, userEntity, member.getTeamId());
		//发送欢迎加入信息
		userService.sendInvitationMessage(memberEntity,request.getTeamId());
		return new CommonResponse();
	}

	//根据成员ID移除团队
	public CommonResponse deleteMemberById(Long id, UserEntity user){
		MemberEntity member = getMember(id);
		if (null == member) {
			return CommonResponse.createNotFoundResponse("没有找到对应的成员");
		}
		TeamEntity teamEntity =teamService.getTeamById(member.gettId());
		if (null == teamEntity) {
			return MemberListResponse.createNotFoundResponse("没有找到对应的团");
		}
		String teamId=teamEntity.getTxGroupid();
		//判断用户是否是组长是的话解散组
		if(member.getRole()!=null&&member.getRole()==3&&member.getIsLeader()!=null&&member.getIsLeader()==1&&member.getGroupId()!=null){
			groupService.delGroup(member.getGroupId());
		}
		//1将用户从聊天群移除
		if(!StringUtils.isEmpty(member.getTeamId()) && member.getUserId()!=null) {
			DeleteGroupMemberRequest delMemberRequest = new DeleteGroupMemberRequest();
			delMemberRequest.setGroupId(member.getTeamId());
			delMemberRequest.setSilence(1);
			List<String> accountList = new ArrayList<String>();
			UserEntity userEntity=userService.getUserById(member.getUserId());
			accountList.add(userEntity.getUserName());
			delMemberRequest.setMemberToDelAccount(accountList);
			timRestAPI.deleteGroupMember(delMemberRequest);
		}
        //判断是删除新加入的还是 旅行社端录入的用户
		//是否为新加入（即为自己通过扫描填写资料加入）【0：否；1：是】
        if(member.getIsNewJoin()!=null&&member.getIsNewJoin()==1){
			//对新加入的 成员 判断是否是组员  是的话判断 移除后 组的数量如果只有组长了 就解散组
			if(member.getRole()!=null&&member.getRole()==3&&member.getIsLeader()!=null&&member.getIsLeader()==0&&member.getGroupId()!=null&&member.getGroupId()!=-1){
				GroupEntity groupEntity =groupService.getGroup(member.getGroupId());
				if (null == groupEntity) {
					return CommonResponse.createNotFoundResponse("没有找到对应的组");
				}
				if(groupEntity.getTotalCount()!=null&&(groupEntity.getTotalCount()-1)>1){
					groupEntity.setTotalCount(groupEntity.getTotalCount()-1);
					// 生成小组头像
					List<MemberEntity> memberLsit = getGroupMembertList(member.getGroupId());
					String photoUrl = this.createPhotoUrl(memberLsit);
					groupEntity.setPhotoUrl(photoUrl);
					groupService.updateGroup(groupEntity);
				}else{
					groupService.delGroup(member.getGroupId());
				}
			}
			//2将用户从团里移除
         memberMapper.delete(id);
		}else{
			MemberEntity member1 = getMember(id);
			member1.setPhone("");
			member1.setTeamId("");
			member1.setJoinStatus(0);
			member1.setUserId(-1l);
			member1.setProvince(null);
			updateMember(member1);

		}
		Integer totalCount = teamEntity.getTotalCount();
		if (totalCount > 0){
			totalCount = totalCount - 1;
			teamEntity.setTotalCount(totalCount);
		}
		teamService.updateTeam(teamEntity);

		//如果成员已经分房则从对应房间删除成员，如果该房间只有该成员一人，则删除对应房间
		RoomDetailEntity roomDetail = roomDetailService.getRoomDetail(member.getId());
		if (roomDetail != null&& null!=roomDetail.getId()){
			RoomEntity roomEntity = roomService.selectRoom(roomDetail.getRoomId());
			if (roomEntity.getRoomDetailList().size() == 1){
				roomDetailService.deleteRoomDetail(roomDetail);
				roomService.deleteRoom(roomEntity);
			}else if (roomEntity.getRoomDetailList().size() > 1){
				roomDetailService.deleteRoomDetail(roomDetail);
			}
		}

		//如果已向该成员发送集合和通知则删除对应数据
		this.deleteNoticeDetail(member,1);
		this.deleteNoticeDetail(member,2);

		//添加日志
		LogManage log=new LogManage();
		log.setAccountType(HelpConstant.LOGZHLX_LD);
		log.setOperateModel(HelpConstant.LOGCZMK_TDXC);
		log.setOperateTime(new Date());
		log.setOperateType("移除成员");
		String name=user.getRealName();
		log.setName(name);
		log.setUserId(user.getId());
		StringBuffer str = new StringBuffer("");
		str.append(name).append("将团:");
		str.append(scheduleService.getSchedule(teamId).getName()).append(",中的");
		str.append(member.getRealName()).append("进行了移除操作");
		log.setOperateContent(str.toString());
		logManageService.add(log);
		return new CommonResponse();
	}


	public void deleteNoticeDetail(MemberEntity member, Integer type){
		List<NoticeDetailEntity> noticeDetailEntityList = noticeDetailService.getNoticeDetailByMemberId(member.getId(), type);
		if (noticeDetailEntityList != null){
			for (NoticeDetailEntity noticeDetailOne:noticeDetailEntityList) {
				if (noticeDetailOne != null && noticeDetailOne.getId() != null){
					int delete = noticeDetailService.delete(noticeDetailOne.getId());
					if (delete > 0){
						//更新集合或通知确认和未确认人数
						NoticeEntity notice = noticeService.getNotice(noticeDetailOne.getNoticeId());
						if (notice != null){
							//如果为未确认
							if (noticeDetailOne.getActiveStatus() == 0){
								Integer notsureCount = notice.getNotsureCount();
								if (notsureCount > 0){
									notice.setNotsureCount(notsureCount-1);
								}
							}else if (noticeDetailOne.getActiveStatus() == 1){//如果为已确认
								Integer sureCount = notice.getSureCount();
								if (sureCount > 0){
									notice.setSureCount(sureCount-1);
								}
							}
							noticeService.updateNotice(notice);
						}
					}
				}
			}
		}
	}


	@Override
	public List<MemberEntity> getByRoomId(Long roomId) {
		List<MemberEntity> memberEntityList = memberMapper.getByRoomId(roomId);
		if (memberEntityList.size()>0){
			memberEntityList = this.changePhotoUrlList(memberEntityList);
		}
		return memberMapper.getByRoomId(roomId);
	}


	/**
	 * 根据小组的所有成员（姓名和性别），生成小组头像
	 * @param memberEntityList
	 * @return
	 * @throws Exception
	 */
	private String createPhotoUrl(List<MemberEntity> memberEntityList) {
		List<String> names = new ArrayList<>();
		List<Integer> sexes = new ArrayList<>();

		for (MemberEntity mem : memberEntityList) {
			names.add(mem.getRealName());
			sexes.add(mem.getSex());
		}

		String photoUrl = null;
		try {
			photoUrl = PhotoHelper.createGroupOrTeamImage(names, sexes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return photoUrl;
	}


	/**
	 * 根据行程状态和行程时间查询成员
	 * @return
     */
	@Override
	public List<MemberEntity> selectMemberByScheduleDate() {
		MemberRequest request = new MemberRequest();
		request.setStartDate(new Date());
		List<MemberEntity> memberEntityList = memberMapper.selectMemberByScheduleDate(request);
		if (memberEntityList.size()>0){
			memberEntityList = this.changePhotoUrlList(memberEntityList);
		}
		return memberEntityList;
	}

	@Override
	public List<MemberEntity> selectMemberByScheduleDetailId(Long sdId, Integer role) {
		MemberRequest request = new MemberRequest();
		request.setSdId(sdId);
		request.setmRole(role);
		List<MemberEntity> memberEntityList = memberMapper.selectMemberByScheduleDetailId(request);
		if (memberEntityList.size()>0){
			memberEntityList = this.changePhotoUrlList(memberEntityList);
		}
		return memberEntityList;
	}

	@Override
	public List<String> selectMemberByTidConditons(MemberRequest request) {
		return memberMapper.selectMemberByTidConditons(request);
	}

	@Override
	public List<MemberEntity> selectMemberByScheduleEnd() {
		MemberRequest request = new MemberRequest();
		//获取前天时间
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,-2);
		Date beforeYesterday = calendar.getTime();
		request.setEndDate(beforeYesterday);
		return memberMapper.selectMemberByScheduleEnd(request);
	}

}
