/*
 * AppVerDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-24 Created by zhajl
 */
package com.umessage.letsgo.dao.system;

import com.umessage.letsgo.domain.po.system.AppVerEntity;
import com.umessage.letsgo.domain.vo.system.request.LasterVerRequest;

import java.util.List;

public interface AppVerDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param appVerEntity
     * @return int
     */
    int insert(AppVerEntity appVerEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.system.AppVerEntity
     */
    AppVerEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.system.AppVerEntity>
     */
    List<AppVerEntity> selectAll();

    /**
     * Description: 
     * @param appVerEntity
     * @return int
     */
    int update(AppVerEntity appVerEntity);

    AppVerEntity selectLasterVerInfo(LasterVerRequest request);
}