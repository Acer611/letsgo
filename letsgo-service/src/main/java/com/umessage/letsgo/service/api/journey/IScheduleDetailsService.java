package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntityVo;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailIdUserIdEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleDetailRequest;
import com.umessage.letsgo.domain.vo.journey.response.*;

import java.util.List;

public interface IScheduleDetailsService {
	// 新增每日行程
	int addScheduleDetails(ScheduleDetailEntity scheduleDetailsEntity);
	// 删除每日行程
	int deleteScheduleDetails(long id);
	// 更新每日行程
	int updateScheduleDetails(ScheduleDetailEntity scheduleDetailsEntity);

	/**
	 * 旅行社编辑后保存每日行程
	 */
	public ScheduleResponse saveScheduleDeails(ScheduleDetailEntity scheduleDetail);

	/**
	 * 旅行社编辑后更新每日行程
     */
	ScheduleResponse updateDetails(ScheduleDetailEntity scheduleDetail);

	// 手机端编辑更新每日行程
	CommonResponse updateScheduleDetails(ScheduleDetailRequest request);

	// 获取单个每日行程
	ScheduleDetailEntity getScheduleDetails(long id);

	//根据团队id和行程日期，获取交通信息
	TrafficInfoResponse getTrafficInfo(String teamId, String date);

	//根据团队id获取行程详细中的目的地，新建集合时获得时区用
	TimeZoneInfoResponse getDestinationInfo(String teamId, Double lat, Double lng);
	
	//根据团队id获取当日目的地
	CurrentTimeZoneResponse getCurrentZoneInfo(Long teamId);
	
	//根据团队id获取可点评行程信息
	ScheduleDetailResponse getCommentableDetail(String teamId, Long userID);

	//根据出行时间查询行程详细和对应的用户id
	List<ScheduleDetailIdUserIdEntity> selectScheduleDetailByScheduleDate();

	//获取行程详细表的国家
	List<ScheduleDetailEntityVo> selectCountry(ScheduleDetailRequest request);

	//获取行程数
	int selectCountryCount(ScheduleDetailRequest request);

	//行程第几天
	List<ScheduleDetailEntity> selectdayNum(ScheduleDetailRequest request);

	List<ScheduleDetailEntity> getScheduleDetails(Long teamId, String date);

	List<ScheduleDetailEntity> getScheduleDetails(String txGroupId, String date);

	// 获取某个团队中的今天行程
	ScheduleDetailEntity getTodayScheduleDetail(Long teamId);

	// 获取明日行程
	ScheduleDetailEntity getTomorrowScheduleDetail(Long teamId);

	// 获取行程目的地对应的天气
	public ScheduleDetailEntity getWeather(ScheduleDetailEntity scheduleDetail);

	/**
	 * 保存每日行程信息
	 * @param scheduleDetail
	 * @return
     */
	ScheduleResponse saveScheduleDetail(TravelAgencyEntity travelEntity,ScheduleDetailRequest scheduleDetail);


	/**
	 * 自定义城市 城市ID 补录
	 * @param scheduleDetail
	 * @return
	 */
	ScheduleResponse updateDestinationId(ScheduleDetailEntity scheduleDetail);

	/**
	 *
	 * 更新每日行程 (新)
	 *
	 * @param scheduleDetail
	 * @return
     */
	ScheduleResponse updateScheduleDetailDetail(ScheduleDetailRequest scheduleDetail);

	/**
	 * 根据行程id查询行程详细
	 * @param scheduleId
	 * @return
     */
	List<ScheduleDetailEntity> selectScheduleDetailByScheduleId(Long scheduleId);
}
