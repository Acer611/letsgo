package com.umessage.letsgo.service.api.team;

import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.LeaderEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.team.requset.LeaderRequest;
import com.umessage.letsgo.domain.vo.team.respone.LeaderResponse;

public interface ILeaderService {
	// 新增领队
	int addLeader(LeaderEntity leaderEntity);
	// 通过用户创建领队
	void createLeaderByUser(UserEntity user, Integer leaderType);

	// 创建并关联领队
	void associateLeaderAndUser(UserEntity user, Integer leaderType);

	// 删除领队
	int deleteLeader(long id);
	// 更新领队
	int updateLeader(LeaderEntity leaderEntity);
	// 获取单个领队
	LeaderEntity getLeader(long id);
	// 获取领队列表
	LeaderResponse getLeaders(LeaderRequest request);
	// 判断领队手机号在某个旅行社中是重复的
	boolean leaderPhoneIsRepeat(String phone,Long travelId,Long tId);

	CommonResponse updateLeaderContent(Long leaderId, String type, String content)throws Exception;

	LeaderEntity getLeaderByUserId(Long userId);

	//根据用户id获取领队ID
	Long getIdByUserId(Long userId);
}
