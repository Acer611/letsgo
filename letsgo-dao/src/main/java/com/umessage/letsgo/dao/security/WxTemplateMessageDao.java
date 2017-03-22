/*
 * RoleDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by lizhen
 */
package com.umessage.letsgo.dao.security;


import com.umessage.letsgo.domain.po.security.WxTemplateMessageEntity;

import java.util.List;

public interface WxTemplateMessageDao {
    /**
     * Description:
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description:
     * @param wxTemplateMessageEntity
     * @return int
     */
    int insert(WxTemplateMessageEntity wxTemplateMessageEntity);

    /**
     * @param id
     * @return
     */
    WxTemplateMessageEntity select(Long id);

    /**
     * @return
     */
    List<WxTemplateMessageEntity> selectAll();

    /**
     * Description:
     * @param wxTemplateMessageEntity
     * @return int
     */
    int update(WxTemplateMessageEntity wxTemplateMessageEntity);
}