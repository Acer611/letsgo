/*
 * LeaderDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-31 Created by ZhaoYidong
 */
package com.umessage.letsgo.dao.team;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.team.LeaderEntity;
import com.umessage.letsgo.domain.vo.team.requset.LeaderRequest;

import java.util.List;

public interface LeaderDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param leaderEntity
     * @return int
     */
    int insert(LeaderEntity leaderEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.team.LeaderEntity
     */
    LeaderEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.team.LeaderEntity>
     */
    Page<LeaderEntity> selectAll(LeaderRequest request);

    /**
     * Description: 
     * @param leaderEntity
     * @return int
     */
    int update(LeaderEntity leaderEntity);

    LeaderEntity selectByPhoneAndTravelId(LeaderRequest request);
    //查询领队表和团队对应的成员手机号
    List<LeaderEntity> selectByPhoneAndMemberPhone(LeaderRequest request);
    //根据旅行社ID获取用户userID集合
    List<Long> getLeaderIds(Long travelId);

	LeaderEntity selectByUserId(Long userId);

    //根据用户id获取领队ID
    Long getIdByUserId(Long userId);
}