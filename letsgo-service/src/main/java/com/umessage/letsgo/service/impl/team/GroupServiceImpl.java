package com.umessage.letsgo.service.impl.team;

import com.umessage.letsgo.dao.team.GroupDao;
import com.umessage.letsgo.domain.po.activity.RoomDetailEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.GroupEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.team.requset.GroupRequest;
import com.umessage.letsgo.domain.vo.team.requset.MemberRequest;
import com.umessage.letsgo.service.api.activity.IRoomDetailService;
import com.umessage.letsgo.service.api.team.IGroupService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.common.helper.PhotoHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class GroupServiceImpl implements IGroupService {
	@Resource
	private GroupDao groupMapper;
	@Resource
	private IMemberService memberService;
	@Resource
	private IRoomDetailService roomDetailService;

	@Override
	public int addGroup(GroupEntity groupEntity) {
		groupEntity.setCreateTime(new Date());
		groupEntity.setVersion(0l);
		return groupMapper.insert(groupEntity);
	}

	@Override
	public int deleteGroup(Long id) {
		return groupMapper.delete(id);
	}

	@Override
	public int updateGroup(GroupEntity groupEntity) {
		groupEntity.setUpdateTime(new Date());
		return groupMapper.update(groupEntity);
	}

	@Override
	public GroupEntity getGroup(Long id) {
		GroupEntity groupEntity = groupMapper.select(id);
		if (groupEntity == null)
			return new GroupEntity();
		return groupEntity;
	}

	@Override
	public List<GroupEntity> getGroupListByTeamId(Long teamId) {
		List<GroupEntity> groupEntitys = new ArrayList<>();
		List<GroupEntity> groupEntityList = groupMapper.selectGroupListWithTeamId(teamId);
		if (CollectionUtils.isEmpty(groupEntityList))
			return Collections.emptyList();
		for (GroupEntity groupEntity:groupEntityList) {
			List<MemberEntity> memberList = groupEntity.getMemberList();
			for (MemberEntity memberEntity:memberList) {
				//更改团队头像地址为用户头像地址
				memberEntity = this.changePhotoUrl(memberEntity);
			}
			groupEntitys.add(groupEntity);
		}
		return groupEntitys;
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
	 * 新建小组
	 * @param groupRequest
	 * @return
     */
	@Override
	public CommonResponse createGroup(GroupRequest groupRequest) {
		// 判断组长是否已加入，如果未加入，则返回错误
		MemberEntity leader = memberService.getMember(groupRequest.getLeaderId());
		if (leader.getId() == null) {
			return CommonResponse.createNotFoundResponse("未找到对应的成员");
		}
		if (leader.getJoinStatus() == 0) {
			return CommonResponse.createNotHasUserResponse();
		}

		// 1、保存小组信息，在小组表中增加一条数据
		GroupEntity group = new GroupEntity();
		group.setTeamId(groupRequest.getTeamId());
		group.setName(groupRequest.getName());
		group.setTotalCount(groupRequest.getMemberIds().size() + 1); // 设置小组人数

		// 生成小组头像
		List<MemberEntity> memberLsit = new ArrayList<>();
		memberLsit.add(leader);

		MemberRequest request = new MemberRequest();
		request.setMemberIds(groupRequest.getMemberIds());
		List<MemberEntity> members = memberService.getMemberList(request);
		for (MemberEntity mem : members) {
			memberLsit.add(mem);
		}

		String photoUrl = this.createPhotoUrl(memberLsit);
		group.setPhotoUrl(photoUrl);
		this.addGroup(group);

		// 2、保存组长信息
		this.saveGroupLeader(groupRequest.getLeaderId(), group.getId());

		// 3、保存小组组员信息
		this.saveGroupMember(groupRequest.getMemberIds(), group.getId());

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setRetMsg("成功保存分组！");
		return commonResponse;
	}

	/**
	 * 删除小组
	 * @param id
	 * @return
     */
	@Override
	public CommonResponse delGroup(Long id) {
		// 1、删除小组
		this.deleteGroup(id);

		// 2、删除小组长及组员
		this.delGroupLeaderAndMember(id);

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setRetMsg("成功删除分组！");
		return commonResponse;
	}

	/**
	 * 修改小组
	 * @param groupRequest
	 * @return
     */
	@Override
	public CommonResponse modifyGroup(GroupRequest groupRequest) {
		// 判断组长是否已加入，如果未加入，则返回错误
		MemberEntity leader = memberService.getMember(groupRequest.getLeaderId());
		if (leader.getJoinStatus() == 0) {
			CommonResponse.createNotHasUserResponse();
		}

		// 1、更新小组信息
		GroupEntity group = this.getGroupMemberList(groupRequest.getGroupId());
		if (group == null) {
			return CommonResponse.createNotFoundResponse("找不到对应的小组");
		}
		group.setName(groupRequest.getName());
		group.setTotalCount(groupRequest.getMemberIds().size() + 1); // 设置小组人数

		// 生成小组头像
		List<MemberEntity> memberLsit = new ArrayList<>();
		memberLsit.add(leader);

		MemberRequest request = new MemberRequest();
		request.setMemberIds(groupRequest.getMemberIds());
		List<MemberEntity> members = memberService.getMemberList(request);
		for (MemberEntity mem : members) {
			memberLsit.add(mem);
		}

		String photoUrl = this.createPhotoUrl(memberLsit);
		group.setPhotoUrl(photoUrl);
		this.updateGroup(group);

		// 2、删除原来的组长及组员
		this.delGroupLeaderAndMember(group.getId());

		// 3、更新新的小组长信息
		this.saveGroupLeader(groupRequest.getLeaderId(), group.getId());

		// 4、更新新的组员信息
		this.saveGroupMember(groupRequest.getMemberIds(), group.getId());

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setRetMsg("成功保存分组！");
		return commonResponse;
	}

	/**
	 * 保存小组的组员信息
	 * @param memberIds
	 * @param groupId
     */
	private void saveGroupMember(List<Long> memberIds, Long groupId) {
		MemberRequest request = new MemberRequest();
		request.setMemberIds(memberIds);
		List<MemberEntity> members = memberService.getMemberList(request);
		for (MemberEntity member : members) {
			member.setGroupId(groupId);
			memberService.updateMember(member);

			this.updateGroupIdInRoom(member.getId(), groupId);
		}
	}
	
	/**
	 * 保存小组的组长信息
	 * @param leaderId
	 * @param groupId
	 *
	 */
	private void saveGroupLeader(Long leaderId, Long groupId) {
		MemberEntity leader = memberService.getMember(leaderId);
		leader.setGroupId(groupId);
		leader.setIsLeader(1);
		memberService.updateMember(leader);

		this.updateGroupIdInRoom(leaderId, groupId);
	}

	/**
	 * 清除小组的组长及组员信息
	 * @param groupId
     */
	private void delGroupLeaderAndMember(Long groupId) {
		List<MemberEntity> members = memberService.getGroupMembertList(groupId);
		
		for (MemberEntity member : members) {
			// 如果是小组长，把成员表的"是否为小组长"属性设置为0
			if (1 == member.getIsLeader()) {
				member.setIsLeader(0);
			}
			member.setGroupId(-1l);
			memberService.updateMember(member);

			this.updateGroupIdInRoom(member.getId(), -1l);
		}
	}

	/**
	 * 更改分房明细里的小组ID
	 * @param memberId
	 * @param groupId
     */
	private void updateGroupIdInRoom(Long memberId, Long groupId) {
		// 更新分房明细中的GroupId
		RoomDetailEntity detailEntity = roomDetailService.getRoomDetail(memberId);
		if (detailEntity.getId() != null) {
			detailEntity.setGroupId(groupId);
			roomDetailService.updateRoomDetail(detailEntity);
		}
	}

	/**
	 * 获取小组信息及小组中组长和所有组员的信息
	 * @param id
	 * @return
     */
	@Override
	public GroupEntity getGroupMemberList(Long id) {
		// 1、获取小组
		GroupEntity group = this.getGroup(id);

		// 2、获取小组组员
		List<MemberEntity> members = memberService.getGroupMembertList(id);
		group.setMemberList(members);

		return group;
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

}
