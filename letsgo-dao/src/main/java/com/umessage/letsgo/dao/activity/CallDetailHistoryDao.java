/*
 * CallDetailHistoryDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.activity;

import com.umessage.letsgo.domain.po.activity.CallDetailHistoryEntity;
import java.util.List;

public interface CallDetailHistoryDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param callDetailHistoryEntity
     * @return int
     */
    int insert(CallDetailHistoryEntity callDetailHistoryEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.activity.CallDetailHistoryEntity
     */
    CallDetailHistoryEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.activity.CallDetailHistoryEntity>
     */
    List<CallDetailHistoryEntity> selectAll();

    /**
     * Description: 
     * @param callDetailHistoryEntity
     * @return int
     */
    int update(CallDetailHistoryEntity callDetailHistoryEntity);
}