/*
 * RoomDetailDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-08 Created by Administrator
 */
package com.umessage.letsgo.dao.activity;

import java.util.List;

import com.umessage.letsgo.domain.po.activity.RoomDetailEntity;

public interface RoomDetailDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param roomDetailEntity
     * @return int
     */
    int insert(RoomDetailEntity roomDetailEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.activity.RoomDetailEntity
     */
    RoomDetailEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.activity.RoomDetailEntity>
     */
    List<RoomDetailEntity> selectAll();
    
    /**
     * Description: 
     * @param roomDetailEntity
     * @return int
     */
    int update(RoomDetailEntity roomDetailEntity);

    RoomDetailEntity selectWithMenberId(Long memberId);
}