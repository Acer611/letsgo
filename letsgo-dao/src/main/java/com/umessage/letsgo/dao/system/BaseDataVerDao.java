/*
 * BaseDataVerDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-24 Created by zhajl
 */
package com.umessage.letsgo.dao.system;

import com.umessage.letsgo.domain.po.system.BaseDataVerEntity;
import com.umessage.letsgo.domain.vo.system.request.LasterVerRequest;

import java.util.List;

public interface BaseDataVerDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param baseDataVerEntity
     * @return int
     */
    int insert(BaseDataVerEntity baseDataVerEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.system.BaseDataVerEntity
     */
    BaseDataVerEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.system.BaseDataVerEntity>
     */
    List<BaseDataVerEntity> selectAll();

    /**
     * Description: 
     * @param baseDataVerEntity
     * @return int
     */
    int update(BaseDataVerEntity baseDataVerEntity);

    BaseDataVerEntity selectLasterVerInfo(LasterVerRequest request);
}