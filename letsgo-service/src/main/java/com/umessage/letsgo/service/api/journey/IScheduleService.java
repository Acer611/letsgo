package com.umessage.letsgo.service.api.journey;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntityVo;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.system.RegionEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.CreateScheduleRequest;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleRequest;
import com.umessage.letsgo.domain.vo.journey.request.TouristRequest;
import com.umessage.letsgo.domain.vo.journey.response.*;
import com.umessage.letsgo.domain.vo.journey.response.vo.TravelAgencyInfoVo;
import com.umessage.letsgo.domain.vo.team.requset.MemberRequest;
import com.umessage.letsgo.domain.vo.team.requset.WebMemberRequest;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * 行程Service接口
 * @author Administrator
 *
 */
public interface IScheduleService {
	// 新增行程
	public int addSchedule(ScheduleEntity scheduleEntity);
	// 保存行程操作
	public ScheduleResponse scheduleSave(ScheduleEntity scheduleEntity);
	// 删除行程
	public int deleteSchedule(long id);
	// 更新行程
	public int updateSchedule(ScheduleEntity scheduleEntity);
	// 更新行程
	public ScheduleResponse scheduleUpdate(ScheduleEntity scheduleEntity);
	// 获取行程列表
	public SchedulePageResponse getSchedules(ScheduleRequest request);
	// 后台获取行程列表
	public SchedulePageResponse getSchedulesByUser(ScheduleRequest request);
	public List<ScheduleEntity> getTravelListByStatus(ScheduleRequest request,Integer status);
	// 获取单个行程
	public ScheduleEntity getSchedule(long id);
	// 后台获取单个行程
	public ScheduleEntity getScheduleByUser(long id);
	// 根据腾迅云id获取行程
	public ScheduleEntity getSchedule(String teamId);
	// 获取行程响应信息
	public ScheduleResponse getScheduleInfo(String teamId);
	//获取领队已经带过或即将带的行程
	public Page<ScheduleEntity> getSchedulesByLeaderAndStatus(Integer status,Long id);
	//后台获取领队已经带过或即将带的行程
	public Page<ScheduleEntity> getSchedulesByUserAndStatus(Integer status,Long id);
	//根据正在出行行程和即将出行行程计算领队的工作日
	public List<Date> calculateLeaderWorkDate(List<ScheduleEntity> travelingList,List<ScheduleEntity> prepareTravelList);
	//确认行程
	public CommonResponse confirmSchedule(Long teamId,Long travelId) throws UnsupportedEncodingException;
	//将已有用户(导游或游客)加入腾迅云群组和导游领队(导游或游客)添加好友关系。
	public void addTXGroup(MemberEntity member, UserEntity user, String teamId);
	//添加好友关系
	//public void addFriend(String username, List<MemberEntity> memberList);
	//获取行程通过成员信息查询
	public List<ScheduleEntity> getSchedulesByMember(WebMemberRequest memberRequest);
	//获取行程通过成员信息查询
	public Page<ScheduleEntity> getSchedulesByMember(Long memberId,int status);

	// 获取用户的行程单
	ScheduleInfoResponse getScheduleEntity(Long teamId, UserEntity user);

	// 获取最近十天的行程目的地
//	List<DestinationEntity> getScheduleCitiesInLatestTenDay();
	List<RegionEntity> getScheduleCitiesInLatestTenDay();

	// 领队端更新行程图片
	CommonResponse updateSchedulePhotos(ScheduleEntity scheduleEntity);

	//领队端创建行程
	ScheduleAddResponse createSchedule(CreateScheduleRequest request);
	ScheduleListResponse searchScheduleList(ScheduleRequest request);
	ScheduleListResponse  searchScheduleListByUser(ScheduleRequest request);

	//领队端查询行程列表
	ScheduleListResponse getLeaderScheduleList(ScheduleRequest request);
	TeamScheduleResponse getSchedulesByTravelId(ScheduleRequest request);
	ScheduleListResponse getScheduleList(ScheduleRequest request);

	//通过团队teamId查询行程
	ScheduleEntity getScheduleByTeamId(Long teamId);

	//修该行程状态
	public int updateScheduleProcessStatus(ScheduleEntity scheduleEntity);

	//获取线路评价数据vo集合
	public	List<ScheduleLineVo> getScheduleList();
	//保存数据到线路统计表
	public boolean setLineEvaluate(ScheduleLineVo vo);


	// 查询需要统计的团队行程的团队ID
	List<Long> getTeamIdsInTeamJourney(TouristRequest request);


	//获取登陆游客的行程列表
	UserScheduleResponse getAllTripList(MemberRequest request);


	ScheduleEntity getTeamName(String teamId);

	List<ScheduleEntity> getScheduleBetweenDateList(Long userId, String startTime, String endTime);

	// 查询用户几个月内的行程（从当前月起计算）
	List<ScheduleEntity> getScheduleInSexMonths(Long userId, Integer months);


	// 获取旅行社的不同行程状态的行程数量
	int getScheduleCountInTravel(Long travelId, Integer status);

	// 获取旅行社的不同行程状态的行程列表
	List<ScheduleDetailEntityVo> getScheduleListInTravel(Long travelId, Integer status);

	int getRemainingDaysByTeamId(String teamId);

	//通过行程ID获取 腾讯组ID
	String getTxGroupIdByScheduleId(Long scheduleId);

	TravelAgencyInfoVo getTravelAgencyInfoVoByTeamId(String teamId);

	//更新行程 将无效的行程逻辑删除
	 int  delSchdule(Long id);
	//通过团ID获取行程明细ID集合
	ScheduleEntity selectBytId(Long teamId);
	//获取用户已经结束的行程列表
	ScheduleListResponse getEndScheduleList(ScheduleRequest request);

	//通过旅行社id和出行状态获取（即将出行）列表
	SchedulePageResponse getJjTripList(ScheduleRequest request);

	//通过旅行社id和出行状态获取（已出行）列表
	SchedulePageResponse getEndTripList(ScheduleRequest request);

	//行程的总天数
	List<ScheduleEntity> selectTotalDayNum(Long id);

	//行程名称
	List<ScheduleEntity> selectScheleName(ScheduleRequest request);

	//根据团队ID获取行程（全部字段）
	ScheduleEntity getScheduleByTid(Long teamId);

	public List<ScheduleEntity> selectScheduleForboundMember(WebMemberRequest memberRequest);

	public List<ScheduleEntity> selectScheduleForUnboundMember(WebMemberRequest memberRequest);

	ScheduleEntity selectScheduleIdByTeamName(String teamNum);


}
