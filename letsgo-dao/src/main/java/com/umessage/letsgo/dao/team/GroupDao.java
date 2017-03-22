/*
 * GroupDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.team;

import java.util.List;

import com.umessage.letsgo.domain.po.team.GroupEntity;

public interface GroupDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param groupEntity
     * @return int
     */
    int insert(GroupEntity groupEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.team.GroupEntity
     */
    GroupEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.team.GroupEntity>
     */
    List<GroupEntity> selectAll();

    /**
     * Description: 
     * @param groupEntity
     * @return int
     */
    int update(GroupEntity groupEntity);
    
    List<GroupEntity> selectGroupListWithTeamId(Long teamId);
}