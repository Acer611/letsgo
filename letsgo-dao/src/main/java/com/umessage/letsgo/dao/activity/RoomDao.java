/*
 * RoomDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.dao.activity;

import java.util.List;

import com.umessage.letsgo.domain.po.activity.RoomEntity;

public interface RoomDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param roomEntity
     * @return int
     */
    int insert(RoomEntity roomEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.activity.RoomEntity
     */
    RoomEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.activity.RoomEntity>
     */
    List<RoomEntity> selectAll();

    /**
     * Description: 
     * @param roomEntity
     * @return int
     */
    int update(RoomEntity roomEntity);
    
    List<RoomEntity> selectRoomListByTeamId(String teamId);
    
    RoomEntity selectRoomById(Long id);

    List<RoomEntity> selectRoomListByMember(Long memberId);
    List<RoomEntity> selectRoomListByGroup(Long groupId);

    /**
     * 删除团队中的所有房间
     * @param teamId 团队Id
     * @return 删除条数
     */
    int deleteRoomsByTeamId(Long teamId);

    //根据成员ID查询对应的房间
    RoomEntity selectByMemberId(Long memberId);

}