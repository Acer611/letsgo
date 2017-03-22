/*
 * GroupDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.team;

import com.umessage.letsgo.domain.po.team.HotelAgencyEntity;

import java.util.List;

public interface HotelAgencyDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param hotelAgencyEntity
     * @return int
     */
    int insert(HotelAgencyEntity hotelAgencyEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.team.HotelAgencyEntity
     */
    HotelAgencyEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.team.HotelAgencyEntity>
     */
    List<HotelAgencyEntity> selectAll();

    /**
     * Description: 
     * @param hotelAgencyEntity
     * @return int
     */
    int update(HotelAgencyEntity hotelAgencyEntity);

    /**
     * 根据私有酒店名称模糊查询
     * @param hotelAgencyEntity
     * @return
     */
    List<HotelAgencyEntity> selectHotelAgencyByName(HotelAgencyEntity hotelAgencyEntity);

    /**
     * 查询名字一样的酒店
     * @param hotelAgencyEntity
     * @return
     */
    HotelAgencyEntity selectHotelAgencyBySameName(HotelAgencyEntity hotelAgencyEntity);

    /**
     * 查询酒店及相册
     * @param id
     * @return
     */
    HotelAgencyEntity selectHotelAndAlbum(Long id);

}