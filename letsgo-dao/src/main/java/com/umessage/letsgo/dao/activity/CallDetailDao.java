/*
 * CallDetailDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.activity;

import java.util.List;

import com.umessage.letsgo.domain.po.activity.CallDetailEntity;
import com.umessage.letsgo.domain.vo.activity.request.CallDetailRequest;

public interface CallDetailDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param callDetailEntity
     * @return int
     */
    int insert(CallDetailEntity callDetailEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.activity.CallDetailEntity
     */
    CallDetailEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.activity.CallDetailEntity>
     */
    List<CallDetailEntity> selectAll();

    /**
     * Description: 
     * @param callDetailEntity
     * @return int
     */
    int update(CallDetailEntity callDetailEntity);

	List<CallDetailEntity> selectCallDetailListWithConditions(CallDetailRequest request);

    /**
     * 获得已签到的点名详细列表
     * @param request 点名id
     * @return 点名详细列表
     */
    List<CallDetailEntity> selectSignCallDetails(CallDetailRequest request);
}