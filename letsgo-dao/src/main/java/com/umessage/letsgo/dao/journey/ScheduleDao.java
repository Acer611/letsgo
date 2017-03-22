/*
 * ScheduleDao.java
 * Copyright(C) 2015-2016 ????
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-31 Created by ZhaoYidong
 */
package com.umessage.letsgo.dao.journey;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleReq;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleRequest;
import com.umessage.letsgo.domain.vo.journey.request.TouristRequest;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleLineVo;
import com.umessage.letsgo.domain.vo.journey.response.TeamScheduleResponse;
import com.umessage.letsgo.domain.vo.journey.response.TeamScheduleVo;
import com.umessage.letsgo.domain.vo.team.requset.WebMemberRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ScheduleDao {
    /**
     * Description: 
     * @pasram id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param scheduleEntity
     * @return int
     */
    int insert(ScheduleEntity scheduleEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.ScheduleEntity
     */
    ScheduleEntity select(Long id);

    ScheduleEntity selectByUser(Long id);

    /**
     *??????id????
     */
    ScheduleEntity selectByTeamId(String teamId);
    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.ScheduleEntity>
     */
    Page<Long> selectAllPages(ScheduleRequest request);
    Page<ScheduleEntity> selectAll(ScheduleRequest request);

    Page<Long> selectAllPagesByUser(ScheduleRequest request);
    Page<ScheduleEntity> selectAllByUser(ScheduleRequest request);

    /**
     * Description: 
     * @param scheduleEntity
     * @return int
     */
    int update(ScheduleEntity scheduleEntity);

    Page<ScheduleEntity> selectScheduleForMember(WebMemberRequest memberRequest);

//    List<ScheduleEntity> selectScheduleWithTeamIds(String teamId);

    List<ScheduleEntity> selectScheduleBetweenTeamId(ScheduleReq request);

    List<ScheduleEntity> selectScheduleCityBetterDate();

    int updateSchedulePhotos(ScheduleEntity scheduleEntity);

    Page<ScheduleEntity> searchScheduleList(ScheduleRequest request);

    Page<ScheduleEntity> searchScheduleListByUser(ScheduleRequest request);

    Page<ScheduleEntity> getLeaderScheduleList(ScheduleRequest request);
    //获取用户行程列表
    Page<ScheduleEntity> getUserScheduleList(ScheduleRequest request);

    Page<TeamScheduleVo> getSchedulesByTravelId(ScheduleRequest request);

    //获取用户已经结束的行程集合
    Page<ScheduleEntity> getEndScheduleList(ScheduleRequest request);
    //获取最新的用户已经结束的行程
    ScheduleEntity getEndScheduleOne(ScheduleRequest request);

    //通过团队teamId查询行程（非全部字段）
    ScheduleEntity getScheduleByTeamId(Long teamId);

    //修该行程状态
    int updateScheduleProcessStatus(ScheduleEntity scheduleEntity);

     //获取线路评价数据vo集合
     List<ScheduleLineVo> getScheduleList();

    List<Long> selectTeamIdsWithIDAndConditions(TouristRequest request);


    Page<ScheduleEntity> getAllTripList(ScheduleRequest request);

    ScheduleEntity getAllTripListOne(ScheduleRequest request);

    ScheduleEntity getTeamName(String teamId);

    List<ScheduleEntity> selectTeamIdsWithStartTimeAndEndTime(ScheduleReq scheduleReq);

    int getTripCountByStatus(ScheduleRequest request);

    List<ScheduleEntity> getTripListByStatus(ScheduleRequest request);

    int getRemainingDaysByTeamId(String teamId);

    //通过行程ID获取 腾讯组ID
    String getTxGroupIdByScheduleId(Long scheduleId);
    //根据ID删除无效行程
    int delScheduleById(Long id);
    //通过团ID获取行程明细ID集合
    ScheduleEntity selectBytId(Long teamId);

    //获取（即将出行）列表
    Page<ScheduleEntity> getJjTripList(ScheduleRequest request);

    //获取（已出行）列表
    Page<ScheduleEntity> getEndTripList(ScheduleRequest request);

    //正在出行的行程的国家
//    List<ScheduleEntity> selectCountry(ScheduleRequest request);

    //根据正在出行的行程的国家团对数
//   int selectCountryCount(ScheduleRequest request);

    //行程的总天数
    List<ScheduleEntity> selectTotalDayNum(Long id);

    //行程明称
    List<ScheduleEntity> selectScheleName(ScheduleRequest request);

    //根据团队ID获取行程（全部字段）
    ScheduleEntity getScheduleByTid(Long teamId);

    //绑定用户的行程
    Page<ScheduleEntity>  selectScheduleForboundMember(WebMemberRequest memberRequest);
    //未绑定用户的行程
    Page<ScheduleEntity>  selectScheduleForUnboundMember(WebMemberRequest memberRequest);

    ScheduleEntity selectScheduleByTeamName(String name);

    /**
     * 获取行程信息（旅行社端）
     * @param request
     * @return
     */
    Page<ScheduleEntity> selectScheduleForTravel(ScheduleRequest request);
}
