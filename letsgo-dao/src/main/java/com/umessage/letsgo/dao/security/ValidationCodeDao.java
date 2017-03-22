/*
 * ValidationCodeDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-11 Created by Administrator
 */
package com.umessage.letsgo.dao.security;

import com.umessage.letsgo.domain.po.security.ValidationCodeEntity;
import com.umessage.letsgo.domain.vo.system.request.ValidationCodeRequest;

import java.util.List;

public interface ValidationCodeDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param validationCodeEntity
     * @return int
     */
    int insert(ValidationCodeEntity validationCodeEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.security.ValidationCodeEntity
     */
    ValidationCodeEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.security.ValidationCodeEntity>
     */
    List<ValidationCodeEntity> selectAll();

    /**
     * Description: 
     * @param validationCodeEntity
     * @return int
     */
    int update(ValidationCodeEntity validationCodeEntity);

    ValidationCodeEntity selectLatestWithPhone(String phone);

    ValidationCodeEntity selectLatestWithPhoneAndScope(ValidationCodeRequest request);
}