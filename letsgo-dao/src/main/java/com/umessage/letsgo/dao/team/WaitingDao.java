/*
 * WaitingDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-09-02 Created by wendy
 */
package com.umessage.letsgo.dao.team;

import com.umessage.letsgo.domain.po.team.WaitingEntity;
import com.umessage.letsgo.domain.vo.team.requset.WaitingRequest;

import java.util.List;

public interface WaitingDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param waitingEntity
     * @return int
     */
    int insert(WaitingEntity waitingEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.team.WaitingEntity
     */
    WaitingEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.team.WaitingEntity>
     */
    List<WaitingEntity> selectAll();

    /**
     * Description: 
     * @param waitingEntity
     * @return int
     */
    int update(WaitingEntity waitingEntity);

    WaitingEntity selectWithUserId(Long userId);

    //通过日期判断该用户在该日期是否空闲
    int hasTime(WaitingRequest request);
}