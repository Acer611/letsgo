/*
 * InvitationCodeDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-08-24 Created by wendy
 */
package com.umessage.letsgo.dao.security;

import com.umessage.letsgo.domain.po.security.InvitationCodeEntity;
import java.util.List;

public interface InvitationCodeDao {
    /**
     * Description: 
     * @param code
     * @return int
     */
    int delete(String code);

    /**
     * Description: 
     * @param invitationCodeEntity
     * @return int
     */
    int insert(InvitationCodeEntity invitationCodeEntity);

    /**
     * Description: 
     * @param code
     * @return com.umessage.letsgo.domain.po.security.InvitationCodeEntity
     */
    InvitationCodeEntity select(String code);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.security.InvitationCodeEntity>
     */
    List<InvitationCodeEntity> selectAll();

    /**
     * Description: 
     * @param invitationCodeEntity
     * @return int
     */
    int update(InvitationCodeEntity invitationCodeEntity);

    List<InvitationCodeEntity> selectLatest();

    int insertBatch(List<InvitationCodeEntity> invitationCodeEntities);
}