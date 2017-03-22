package com.umessage.letsgo.service.api.team;

import com.umessage.letsgo.domain.po.team.GroupEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.team.requset.GroupRequest;

import java.util.List;

public interface IGroupService {
	// 新增小组
	int addGroup(GroupEntity groupEntity);
	
	// 删除小组
	int deleteGroup(Long id);
	
	// 更新小组
	int updateGroup(GroupEntity groupEntity);
	
	// 获取单个小组
	GroupEntity getGroup(Long id);
	
	// 获取小组列表
	List<GroupEntity> getGroupListByTeamId(Long teamId);
	
	// 创建分组
	CommonResponse createGroup(GroupRequest groupRequest);
	
	// 删除分组
	CommonResponse delGroup(Long id);
	
	// 更新分组
	CommonResponse modifyGroup(GroupRequest groupRequest);
	
	// 获取小组全部组员列表
	GroupEntity getGroupMemberList(Long id);
}
