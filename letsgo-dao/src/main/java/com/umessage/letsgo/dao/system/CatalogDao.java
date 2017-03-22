/*
 * CatalogDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.system;

import com.umessage.letsgo.domain.po.system.CatalogEntity;
import java.util.List;

public interface CatalogDao {
    /**
     * Description: 
     * @param catalogCode
     * @return int
     */
    int delete(String catalogCode);

    /**
     * Description: 
     * @param catalogEntity
     * @return int
     */
    int insert(CatalogEntity catalogEntity);

    /**
     * Description: 
     * @param catalogCode
     * @return com.umessage.letsgo.domain.po.system.CatalogEntity
     */
    CatalogEntity select(String catalogCode);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.system.CatalogEntity>
     */
    List<CatalogEntity> selectAll();

    /**
     * Description: 
     * @param catalogEntity
     * @return int
     */
    int update(CatalogEntity catalogEntity);
}