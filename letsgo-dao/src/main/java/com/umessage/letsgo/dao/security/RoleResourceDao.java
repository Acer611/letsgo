/*
 * RoleResourceDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.security;

import com.umessage.letsgo.domain.po.security.RoleResourceEntity;
import java.util.List;
import java.util.Map;

public interface RoleResourceDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param roleResourceEntity
     * @return int
     */
    int insert(RoleResourceEntity roleResourceEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.security.RoleResourceEntity
     */
    RoleResourceEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.security.RoleResourceEntity>
     */
    List<RoleResourceEntity> selectAll();

    /**
     * Description: 
     * @param roleResourceEntity
     * @return int
     */
    int update(RoleResourceEntity roleResourceEntity);
    
}