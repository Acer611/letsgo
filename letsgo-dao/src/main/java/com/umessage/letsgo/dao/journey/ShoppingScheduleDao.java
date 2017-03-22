/*
 * GroupDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.ShoppingScheduleEntity;

import java.util.List;

public interface ShoppingScheduleDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param shoppingScheduleEntity
     * @return int
     */
    int insert(ShoppingScheduleEntity shoppingScheduleEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.ShoppingScheduleEntity
     */
    ShoppingScheduleEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.ShoppingScheduleEntity>
     */
    List<ShoppingScheduleEntity> selectAll();

    /**
     * Description: 
     * @param shoppingScheduleEntity
     * @return int
     */
    int update(ShoppingScheduleEntity shoppingScheduleEntity);

    /**
     * 根据每日行程id查询购物场所
     * @param scheduleDetailId
     * @return
     */
    List<ShoppingScheduleEntity> selectShoppingByScheduleDetailId(Long scheduleDetailId);

    /**
     * 根据行程id查询购物场所
     * @param scheduleId
     * @return
     */
    List<ShoppingScheduleEntity> selectShoppingByScheduleId(Long scheduleId);

    /**
     * 根据每日行程删除
     * @param scheduleDetailId
     * @return
     */
    int deleteShoppingByScheduleDetailId(Long scheduleDetailId);

    /**
     * 根据行程删除
     * @param scheduleId
     * @return
     */
    int deleteShoppingByScheduleId(Long scheduleId);

}