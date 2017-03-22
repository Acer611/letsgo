/*
 * RewardDetailsDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-08-30 Created by wendy
 */
package com.umessage.letsgo.dao.security;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.security.RewardDetailsEntity;
import com.umessage.letsgo.domain.vo.security.request.RewardDetailsRequest;

import java.util.List;

public interface RewardDetailsDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param rewardDetailsEntity
     * @return int
     */
    int insert(RewardDetailsEntity rewardDetailsEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.security.RewardDetailsEntity
     */
    RewardDetailsEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.security.RewardDetailsEntity>
     */
    List<RewardDetailsEntity> selectAll();

    /**
     * Description: 
     * @param rewardDetailsEntity
     * @return int
     */
    int update(RewardDetailsEntity rewardDetailsEntity);

    Page<RewardDetailsEntity> selectWithUserId(Long userId);

    int selectCountWithUserId(Long userId);

    List<RewardDetailsEntity> selectInviteRewardByUserId(RewardDetailsRequest request);
}