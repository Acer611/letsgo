package com.umessage.letsgo.service.api.team;

import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity;
import com.umessage.letsgo.domain.po.team.GroupEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.team.requset.GuideMemberRequest;
import com.umessage.letsgo.domain.vo.team.requset.TeamRequest;
import com.umessage.letsgo.domain.vo.team.respone.*;

import java.util.List;

public interface ITeamService {
	// 新增团队
	int addTeam(TeamEntity teamEntity);
	
	// 删除团队
	int deleteTeam(TeamEntity teamEntity);
	
	// 更新团队
	int updateTeam(TeamEntity teamEntity);

	/**
	 * 通过行程id查询团队
	 */
	TeamEntity getTeamById(Long id);

	TeamEntity selectByScheduleId(Long id);

	TeamEntity getTeamByTXGroupId(String txGroupId);

	List<TeamEntity> getTeamEntityList(Long userId);

	// 获取团队列表
	TeamListResponse getTeamList(Long userId, Integer flag);
	
	// 获取团队详情
	TeamRespone getTeamDetail(String txGroupId);
	
	// 获取团队的全部成员
	TeamMemberResponse getTeamMemberList(String txGroupId);

	// 添加导游
	CommonResponse addTourGuide(GuideMemberRequest guide);

	// 获取所有 小组集合和对应成员 通过团队id。
	public List<GroupEntity> fetchGroupDetailListByTeamId(Long teamId);
	// 获取所有 不在小组中的成员的集合
	public List<MemberEntity> fetchNotInGroupMemberListByTeamId(String teamId);
	public List<MemberEntity> fetchNotInGroupMemberListByTeamId(Long teamId);

	public List<MemberEntity> fetchLeaderAndMemberListByTeamId(Long teamId);

	public TeamRespone getLatestTeam(Long userId, String teamId);

	// 获取需要变更为出行中和已出行的团队
	public List<TeamEntity> getTripStageTeam();

	//获取出行结束的团队
	public List<TeamEntity> getFinishedTeam();

	//发放奖励
	public boolean issueReward(TeamEntity teamEntity);

	// 处理出行状态的变更
	public boolean processTripStage(TeamEntity teamEntity);

	public List<TeamEntity> getSendMessageTeam();

	// 获取行程出发地的本地时间(小时)
	int getScheduleStartHours(ScheduleDetailEntity scheduleDetail);

	// 获取行程目的地的本地时间(小时)
	public int getScheduleDestinationHours(ScheduleDetailEntity scheduleDetail);

	// 向团队发送自定义的行程信息
	void sendScheduleMessage(TeamEntity teamEntity, ScheduleDetailEntity scheduleDetail);

	// 向团队发送点评信息
	void sendCommnetMessage(TeamEntity teamEntity);

	// 获取需要发送点评的团队
	public List<TeamEntity> getSendCommnetTeam();

	TeamInfoResponse getTeamInfo(String teamId, Long userId);

	// 获取团队出行状态及当前用户的身份和管理权，返回一个标识，用于判断全局的通知集合公告的权限
	int isOperate(String teamId, Long userId, Integer model);

	Integer teamStatus(String teamId);	// 获取团队出行状态

	List<Integer> roleStatus(String teamId, Long userId);	// 获取当前用户的指定团队的身份状态及管理状态

	boolean teamNumIsRepeat(String teamNum, Long travelId);

	AnalyticalDataResponse getTeamAnalyticalData(String teamId, Long userId);

	CommonResponse saveProfession(String teamId,Long userId, String proCode, String proName);

	List<String> getSameTeamByUserId(Long firstUserId, Long secondUserId);

	//判断团号是否存在
	boolean hasTeamNum(String teamNum);

	TeamEntity selectById(Long id);

	/**
	 * 查询用户的角色以及是否发放问卷
	 * @param teamId
	 * @param userId
	 * @return
	 */
	 TeamRoleResponse getUserRoleAndSurvey(String teamId, Long userId);

	TeamPageRespone selectTeamListByCountryN(TeamRequest request);
}
