/*
 * CallHistoryDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.activity;

import com.umessage.letsgo.domain.po.activity.CallHistoryEntity;
import java.util.List;

public interface CallHistoryDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param callHistoryEntity
     * @return int
     */
    int insert(CallHistoryEntity callHistoryEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.activity.CallHistoryEntity
     */
    CallHistoryEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.activity.CallHistoryEntity>
     */
    List<CallHistoryEntity> selectAll();

    /**
     * Description: 
     * @param callHistoryEntity
     * @return int
     */
    int update(CallHistoryEntity callHistoryEntity);
}