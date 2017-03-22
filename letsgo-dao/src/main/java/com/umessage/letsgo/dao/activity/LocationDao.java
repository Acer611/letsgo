/*
 * LocationDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-13 Created by Administrator
 */
package com.umessage.letsgo.dao.activity;

import com.umessage.letsgo.domain.po.activity.LocationEntity;
import com.umessage.letsgo.domain.vo.activity.request.LocationRequest;

import java.util.List;

public interface LocationDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);
    int deleteByUserId(Long userId);
    /**
     * Description: 
     * @param locationEntity
     * @return int
     */
    int insert(LocationEntity locationEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.activity.LocationEntity
     */
    LocationEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.activity.LocationEntity>
     */
    List<LocationEntity> selectAll();

    /**
     * Description: 
     * @param locationEntity
     * @return int
     */
    int update(LocationEntity locationEntity);

    LocationEntity selectLocationWithUserId(Long userId);

    List<LocationEntity> selectLocationListWithUserIds(List<Long> userIds);

    List<LocationEntity> selectLocationsByTeamId(LocationRequest locationRequest);
}