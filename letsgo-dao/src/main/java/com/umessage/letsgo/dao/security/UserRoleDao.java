/*
 * UserRoleDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.security;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.umessage.letsgo.domain.po.security.UserRoleEntity;

public interface UserRoleDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param userRoleEntity
     * @return int
     */
    int insert(UserRoleEntity userRoleEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.security.UserRoleEntity
     */
    UserRoleEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.security.UserRoleEntity>
     */
    List<UserRoleEntity> selectAll();
    
    /**
     * Description: 
     * @param userRoleEntity
     * @return int
     */
    int update(UserRoleEntity userRoleEntity);
    
    List<UserRoleEntity> selectUserRoleWithConditions(UserRoleEntity userRoleEntity);
    
    int updateByUserId(@Param("userId") Long userId,@Param("roleId") Long roleId);
}