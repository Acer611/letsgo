/*
 * TravelNotesDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-10-09 Created by Administrator
 */
package com.umessage.letsgo.dao.journey;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.umessage.letsgo.domain.po.journey.TravelNotesEntity;
import com.umessage.letsgo.domain.vo.journey.response.TravelNotesDetailCommentResponse;

public interface TravelNotesDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param travelNotesEntity
     * @return int
     */
    int insert(TravelNotesEntity travelNotesEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.TravelNotesEntity
     */
    TravelNotesEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.TravelNotesEntity>
     */
    List<TravelNotesEntity> selectAll();

    /**
     * Description: 
     * @param travelNotesEntity
     * @return int
     */
    int update(TravelNotesEntity travelNotesEntity);

	TravelNotesEntity selectByUserIdAndTxGroupId(@Param("userId") long userId,@Param("txGroupId") String txGroupId);

	TravelNotesDetailCommentResponse selectTravelNotesComment(@Param("travelNotesId") Long travelNotesId);
	

}