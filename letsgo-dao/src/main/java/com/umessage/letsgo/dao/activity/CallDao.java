/*
 * CallDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.activity;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.activity.CallEntity;
import com.umessage.letsgo.domain.vo.activity.request.CallRequest;

import java.util.List;

public interface CallDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param callEntity
     * @return int
     */
    int insert(CallEntity callEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.activity.CallEntity
     */
    CallEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.activity.CallEntity>
     */
    Page<CallEntity> selectAll();

    /**
     * Description: 
     * @param callEntity
     * @return int
     */
    int update(CallEntity callEntity);

    List<CallEntity> selectCallListWithConditons(CallRequest request);

    CallEntity selectCallWithIdAndStatus(CallRequest callRequest);
}