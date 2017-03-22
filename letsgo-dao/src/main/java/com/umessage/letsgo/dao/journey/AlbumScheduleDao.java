/*
 * GroupDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.journey;

import com.umessage.letsgo.domain.po.journey.AlbumScheduleEntity;

import java.util.List;

/**
 * 每日行程图片信息处理类
 */
public interface AlbumScheduleDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param albumScheduleEntity
     * @return int
     */
    int insert(AlbumScheduleEntity albumScheduleEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.journey.AlbumScheduleEntity
     */
    AlbumScheduleEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.journey.AlbumScheduleEntity>
     */
    List<AlbumScheduleEntity> selectAll();

    /**
     * Description: 
     * @param albumScheduleEntity
     * @return int
     */
    int update(AlbumScheduleEntity albumScheduleEntity);

    /**
     * 批量插入每日行程景点关联关系数据
     * @param albumScheduleEntities
     */
    void batchInsert(List<AlbumScheduleEntity> albumScheduleEntities);

    /**
     * 根据不同类型参数删除照片
     * @param albumScheduleEntity
     * @return
     */
    int deleteByContents(AlbumScheduleEntity albumScheduleEntity);

    /**
     * 根据每日行程id查询照片
     * @param scheduleDetailId
     * @return
     */
    List<AlbumScheduleEntity> selectAlbumsByScheduleDetailId(Long scheduleDetailId);

    /**
     * 根据行程明细ID object id 和type 查找图片信息
     * @param request
     * @return
     */
    List<AlbumScheduleEntity> getAlbumSchedule(AlbumScheduleEntity request);

    /**
     * 根据行程id查询图片
     * @param request
     * @return
     */
    List<AlbumScheduleEntity> getAlbumScheduleByScheduleId(AlbumScheduleEntity request);

}