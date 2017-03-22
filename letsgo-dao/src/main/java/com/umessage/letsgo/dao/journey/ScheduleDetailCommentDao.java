/*
 * ScheduleDetailCommentDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-09-20 Created by Administrator
 */
package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.ScheduleDetailCommentEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailIdUserIdEntity;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleDetailCommentRequest;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ScheduleDetailCommentDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param scheduleDetailCommentEntity
     * @return int
     */
    int insert(ScheduleDetailCommentEntity scheduleDetailCommentEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.ScheduleDetailCommentEntity
     */
    ScheduleDetailCommentEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.ScheduleDetailCommentEntity>
     */
    List<ScheduleDetailCommentEntity> selectAll();

    /**
     * Description: 
     * @param scheduleDetailCommentEntity
     * @return int
     */
    int update(ScheduleDetailCommentEntity scheduleDetailCommentEntity);

    ScheduleDetailCommentEntity selectComment(@Param("scheduleDetailId") Long scheduleDetailId,@Param("userId") Long userId);

    ScheduleDetailCommentEntity selectCommentByUserIdAndScheduleDetailId(ScheduleDetailIdUserIdEntity scheduleDetailIdUserIdEntity);
}