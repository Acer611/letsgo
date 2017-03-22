/*
 * DataItemDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.system;

import com.umessage.letsgo.domain.po.system.DataItemEntity;
import java.util.List;

public interface DataItemDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param dataItemEntity
     * @return int
     */
    int insert(DataItemEntity dataItemEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.system.DataItemEntity
     */
    DataItemEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.system.DataItemEntity>
     */
    List<DataItemEntity> selectAll();

    /**
     * Description: 
     * @param dataItemEntity
     * @return int
     */
    int update(DataItemEntity dataItemEntity);
}