/*
 * LocationHistoryDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-13 Created by Administrator
 */
package com.umessage.letsgo.dao.activity;

import com.umessage.letsgo.domain.po.activity.LocationHistoryEntity;
import com.umessage.letsgo.domain.vo.activity.request.LocationHistoryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LocationHistoryDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param locationHistoryEntity
     * @return int
     */
    int insert(LocationHistoryEntity locationHistoryEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.activity.LocationHistoryEntity
     */
    LocationHistoryEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.activity.LocationHistoryEntity>
     */
    List<LocationHistoryEntity> selectAll();

    // 获取团队成员的位置轨迹
    List<LocationHistoryEntity> getMemberTrack(@Param("tId") Long tId, @Param("userId") Long userId);

    /**
     * Description: 
     * @param locationHistoryEntity
     * @return int
     */
    int update(LocationHistoryEntity locationHistoryEntity);

    List<LocationHistoryEntity> selectLocationWithUserIdAndDate(LocationHistoryRequest request);

    /**
     * 根据用户ID和创建时间和坐标距离查询用户位置数量
     * @param request
     * @return
     */
    int selectCountWithUserIdAndDate(LocationHistoryRequest request);
}