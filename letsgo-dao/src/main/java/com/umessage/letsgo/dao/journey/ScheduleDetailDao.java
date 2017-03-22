/*
 * ScheduleDetailDao.java
 * Copyright(C) 2015-2016 ????
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-31 Created by ZhaoYidong
 */
package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntityVo;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailIdUserIdEntity;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleDetailRequest;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ScheduleDetailDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param scheduleDetailEntity
     * @return int
     */
    int insert(ScheduleDetailEntity scheduleDetailEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity
     */
    ScheduleDetailEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity>
     */
    List<ScheduleDetailEntity> selectAll();

    /**
     * Description: 
     * @param scheduleDetailEntity
     * @return int
     */
    int update(ScheduleDetailEntity scheduleDetailEntity);

    List<ScheduleDetailEntity> selectByTeamId(@Param(value="teamId")Long teamId, @Param(value="txGroupId")String txGroupId, @Param(value="date")String date);

	List<ScheduleDetailEntity> selectScheduleDetailByScheId(@Param(value="scheduleId")String scheduleId,@Param(value="date")String currentDate);

	List<ScheduleDetailEntity> selectScheduleDetailByScheduleId(@Param(value="scheduleId")Long scheduleId);

    List<ScheduleDetailIdUserIdEntity> selectScheduleDetailByScheduleDate(@Param(value="scheduleDate")Date scheduleDate);

    //行程第几天
    List<ScheduleDetailEntity> selectdayNum(ScheduleDetailRequest request);


    List<ScheduleDetailEntityVo> selectCountry(ScheduleDetailRequest request);

    int selectCountryCount(ScheduleDetailRequest request);

    /**
     * 自定义城市 城市ID 补录
     * @param scheduleDetail
     * @return
     */
    int updateDestinationId(ScheduleDetailEntity scheduleDetail);
}