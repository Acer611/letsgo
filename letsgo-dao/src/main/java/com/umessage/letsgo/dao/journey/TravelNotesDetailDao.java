/*
 * TravelNotesDetailDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-10-09 Created by Administrator
 */
package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.TravelNotesDetailEntity;
import com.umessage.letsgo.domain.vo.journey.response.TravelNotesDetailsResponse;
import com.umessage.letsgo.domain.vo.journey.response.TravelsListResponse;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TravelNotesDetailDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param travelNotesDetailEntity
     * @return int
     */
    int insert(TravelNotesDetailEntity travelNotesDetailEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.TravelNotesDetailEntity
     */
    TravelNotesDetailEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.TravelNotesDetailEntity>
     */
    List<TravelNotesDetailEntity> selectAll();

    /**
     * Description: 
     * @param travelNotesDetailEntity
     * @return int
     */
    int update(TravelNotesDetailEntity travelNotesDetailEntity);
    
    TravelsListResponse getTravelsList(@Param("userId") long userId,@Param("txGroupId") String txGroupId);

	TravelNotesDetailsResponse getOneScheduleDetail(@Param("scheduleDetailId") long scheduleDetailId);

}