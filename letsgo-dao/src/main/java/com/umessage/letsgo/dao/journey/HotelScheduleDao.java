/*
 * GroupDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.HotelScheduleEntity;

import java.util.List;

/**
 * 每日行程酒店Dao
 */
public interface HotelScheduleDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param hotelScheduleEntity
     * @return int
     */
    int insert(HotelScheduleEntity hotelScheduleEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.HotelScheduleEntity
     */
    HotelScheduleEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.HotelScheduleEntity>
     */
    List<HotelScheduleEntity> selectAll();

    /**
     * Description: 
     * @param hotelScheduleEntity
     * @return int
     */
    int update(HotelScheduleEntity hotelScheduleEntity);

    /**
     * 根据每日行程id查询酒店
     * @param scheduleDetailId
     * @return
     */
    HotelScheduleEntity selectByScheduleDetailId(Long scheduleDetailId);

    /**
     * 根据id查询每日行程酒店及相册
     * @param id
     * @return
     */
    HotelScheduleEntity selectHotelScheduleAndAlbum(Long id);

    /**
     * 根据每日行程id删除酒店
     * @param scheduleDetailId
     * @return
     */
    int deleteByScheduleDetailId(Long scheduleDetailId);

}