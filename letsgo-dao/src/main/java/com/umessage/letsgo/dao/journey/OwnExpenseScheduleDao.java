/*
 * GroupDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.OwnExpenseScheduleEntity;

import java.util.List;

public interface OwnExpenseScheduleDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param ownExpenseScheduleEntity
     * @return int
     */
    int insert(OwnExpenseScheduleEntity ownExpenseScheduleEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.OwnExpenseScheduleEntity
     */
    OwnExpenseScheduleEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.OwnExpenseScheduleEntity>
     */
    List<OwnExpenseScheduleEntity> selectAll();

    /**
     * Description: 
     * @param ownExpenseScheduleEntity
     * @return int
     */
    int update(OwnExpenseScheduleEntity ownExpenseScheduleEntity);

    /**
     * 查询自费项目及相册
     * @param id
     * @return
     */
    OwnExpenseScheduleEntity selectOwnExpenseScheduleAndAlbum(Long id);

    /**
     * 根据行程id查询自费项目
     * @param scheduleId
     * @return
     */
    List<OwnExpenseScheduleEntity> selectOwnExpenseScheduleByScheduleId(Long scheduleId);

    /**
     * 根据每日行程id查询自费项目
     * @param scheduleDetailId
     * @return
     */
    List<OwnExpenseScheduleEntity> selectOwnExpenseScheduleByScheduleDetailId(Long scheduleDetailId);

    /**
     * 根据行程id删除自费项目
     * @param scheduleId
     * @return
     */
    int deleteOwnExpenseScheduleByScheduleId(Long scheduleId);

    /**
     * 根据每日行程id删除自费项目
     * @param scheduleDetailId
     * @return
     */
    int deleteOwnExpenseScheduleByScheduleDetailId(Long scheduleDetailId);

}