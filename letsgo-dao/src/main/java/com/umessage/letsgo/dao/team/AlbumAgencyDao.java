/*
 * GroupDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.team;

import com.umessage.letsgo.domain.po.team.AlbumAgencyEntity;

import java.util.List;

public interface AlbumAgencyDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param albumAgencyEntity
     * @return int
     */
    int insert(AlbumAgencyEntity albumAgencyEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.team.AlbumAgencyEntity
     */
    AlbumAgencyEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.team.AlbumAgencyEntity>
     */
    List<AlbumAgencyEntity> selectAll();

    /**
     * Description: 
     * @param albumAgencyEntity
     * @return int
     */
    int update(AlbumAgencyEntity albumAgencyEntity);


    /**
     * 旅行社私有照片库照片批量插入
     * @param albumAgencyEntities
     */
    void batchInsert(List<AlbumAgencyEntity> albumAgencyEntities);

    /**
     * 根据旅行社ID object id 和type 删除照片私有库数据
     * @param albumAgencyRequest
     */
    void deleteByAlbumAgencyEntity(AlbumAgencyEntity albumAgencyRequest);

    /**
     * 根据旅行社ID object id 和type 查询照片私有库数据
     * @param albumAgencyEntity
     * @return
     */
    List<AlbumAgencyEntity> selectByAlbumAgencyEntity(AlbumAgencyEntity albumAgencyEntity);

    /**
     * 根据旅行社ID object id 和type 删除照片私有库数据
     * 假删除 删除时 把status改为deleted  方便数据同步时 同步到索引库
     * @param albumAgencyRequest
     */
    void deleteAlbum(AlbumAgencyEntity albumAgencyRequest);
}