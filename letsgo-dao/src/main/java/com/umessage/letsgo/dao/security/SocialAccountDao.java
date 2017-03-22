/*
 * SocialAccountDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-13 Created by Administrator
 */
package com.umessage.letsgo.dao.security;

import com.umessage.letsgo.domain.po.security.SocialAccountEntity;
import java.util.List;

public interface SocialAccountDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param socialAccountEntity
     * @return int
     */
    int insert(SocialAccountEntity socialAccountEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.security.SocialAccountEntity
     */
    SocialAccountEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.security.SocialAccountEntity>
     */
    List<SocialAccountEntity> selectAll();

    /**
     * Description: 
     * @param socialAccountEntity
     * @return int
     */
    int update(SocialAccountEntity socialAccountEntity);
}