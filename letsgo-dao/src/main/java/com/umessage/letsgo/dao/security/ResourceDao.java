/*
 * ResourceDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.security;

import com.umessage.letsgo.domain.po.security.ResourceEntity;
import java.util.List;
import java.util.Map;

public interface ResourceDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param resourceEntity
     * @return int
     */
    int insert(ResourceEntity resourceEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.security.ResourceEntity
     */
    ResourceEntity select(Long id);
    
    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.security.ResourceEntity>
     */
    List<ResourceEntity> selectAll();
    
    /**
     * Description: 
     * @param resourceEntity
     * @return int
     */
    int update(ResourceEntity resourceEntity);
}