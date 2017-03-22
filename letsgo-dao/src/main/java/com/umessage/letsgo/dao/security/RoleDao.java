/*
 * RoleDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.security;

import com.umessage.letsgo.domain.po.security.RoleEntity;
import java.util.List;
import java.util.Map;

public interface RoleDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param roleEntity
     * @return int
     */
    int insert(RoleEntity roleEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.security.RoleEntity
     */
    RoleEntity select(Long id);
    
    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.security.RoleEntity>
     */
    List<RoleEntity> selectAll();
    
    /**
     * Description: 
     * @param roleEntity
     * @return int
     */
    int update(RoleEntity roleEntity);
    
    List<RoleEntity> selectRoleWithName(String rolename);
    
}