/*
 * ScenicDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-31 Created by ZhaoYidong
 */
package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.ScenicEntity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ScenicDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     *删除每日行程中的景点。
     */
    int deleteByScheduleDetailId(Long scheduleDetailId);

    /**
     * Description: 
     * @param scenicEntity
     * @return int
     */
    int insert(ScenicEntity scenicEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.ScenicEntity
     */
    ScenicEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.ScenicEntity>
     */
    List<ScenicEntity> selectAll();

    /**
     * Description: 
     * @param scenicEntity
     * @return int
     */
    int update(ScenicEntity scenicEntity);

	List<ScenicEntity> getByScheduleDetailId(Long scheduleDetailId);
}