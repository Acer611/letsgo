/*
 * GroupDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.team;

import com.umessage.letsgo.domain.po.journey.ScheduleDetailScenicEntity;
import com.umessage.letsgo.domain.po.team.ScenicAgencyEntity;

import java.util.List;

/**
 * 景点私有库dao层处理类
 */
public interface ScenicAgencyDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param scenicAgencyEntity
     * @return int
     */
    int insert(ScenicAgencyEntity scenicAgencyEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.team.ScenicAgencyEntity
     */
    ScenicAgencyEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.team.ScenicAgencyEntity>
     */
    List<ScenicAgencyEntity> selectAll();

    /**
     * Description: 
     * @param scenicAgencyEntity
     * @return int
     */
    int update(ScenicAgencyEntity scenicAgencyEntity);

    /**
     * 根据景点名称，旅行社id,景点城市查找景点私有库信息
     * @param scenicEntity
     * @return
     */
    ScenicAgencyEntity selectScheduleDetailScenic(ScenicAgencyEntity scenicEntity);

    /**
     * 模糊匹配景点名称，查询景点列表
     * @param scenicAgencyEntity
     * @return
     */
    List<ScenicAgencyEntity> selectLike(ScenicAgencyEntity scenicAgencyEntity);
}