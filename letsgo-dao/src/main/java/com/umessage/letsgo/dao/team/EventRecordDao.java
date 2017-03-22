/*
 * TeamDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-11 Created by Administrator
 */
package com.umessage.letsgo.dao.team;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.team.EventRecordEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleRequest;
import com.umessage.letsgo.domain.vo.team.requset.EventRecordRequest;

import java.util.List;

public interface EventRecordDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param eventRecordEntity
     * @return int
     */
    int insert(EventRecordEntity eventRecordEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.team.EventRecordEntity
     */
    EventRecordEntity select(Long id);

    /**
     * 通过行程id查询团队
     */
    EventRecordEntity selectById(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.team.EventRecordEntity>
     */
    List<EventRecordEntity> selectAll();

    /**
     * Description: 
     * @param eventRecordEntity
     * @return int
     */
    int update(EventRecordEntity eventRecordEntity);


    /**
     * 获取事件列表
     * @return
     */
    List<EventRecordEntity> getEventRecordList(EventRecordRequest request);

    /**
     * 根据领队ID集合获取事件列表
     * @return
     */
    Page<EventRecordEntity> getEventList(EventRecordRequest request);

}