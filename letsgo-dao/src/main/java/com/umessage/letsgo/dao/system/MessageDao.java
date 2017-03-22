/*
 * MessageDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-04 Created by Administrator
 */
package com.umessage.letsgo.dao.system;

import com.umessage.letsgo.domain.po.system.MessageEntity;
import com.umessage.letsgo.domain.vo.system.request.MessageRequest;

import java.util.List;

public interface MessageDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param messageEntity
     * @return int
     */
    int insert(MessageEntity messageEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.system.MessageEntity
     */
    MessageEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.system.MessageEntity>
     */
    List<MessageEntity> selectAll();

    /**
     * Description: 
     * @param messageEntity
     * @return int
     */
    int update(MessageEntity messageEntity);

    int updateValidWithPhone(String phone);

    List<MessageEntity> selectMessageListWithCondition(MessageRequest request);
}