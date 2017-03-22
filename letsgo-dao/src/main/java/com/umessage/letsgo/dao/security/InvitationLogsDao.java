/*
 * InvitationLogsDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-08-30 Created by wendy
 */
package com.umessage.letsgo.dao.security;

import com.umessage.letsgo.domain.po.security.InvitationLogsEntity;
import java.util.List;

public interface InvitationLogsDao {
    /**
     * Description: 
     * @param invitationLogsEntity
     * @return int
     */
    int insert(InvitationLogsEntity invitationLogsEntity);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.security.InvitationLogsEntity>
     */
    List<InvitationLogsEntity> selectAll();
}