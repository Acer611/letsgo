/*
 * UserDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-20 Created by Administrator
 */
package com.umessage.letsgo.dao.security;

import java.util.List;

import com.umessage.letsgo.domain.vo.system.request.LogManageRequest;
import org.apache.ibatis.annotations.Param;
import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.security.UserEntity;

public interface UserDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param userEntity
     * @return int
     */
    int insert(UserEntity userEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.security.UserEntity
     */
    UserEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.security.UserEntity>
     */
    List<UserEntity> selectAll();

    /**
     * Description: 
     * @param userEntity
     * @return int
     */
    int update(UserEntity userEntity);

	UserEntity selectWithAccount(String loginAccount);

    List<UserEntity> getUsersIsTravel(Integer status);

    UserEntity selectWithInvitationCode(String invitationCode);

	void uploadFacePhoto(@Param(value="userId")Long userId,@Param(value="photoUrl") String photoUrl);

    Long findUserId(String txId);

    UserEntity getUserByInviteCode(String code);

	Page<UserEntity> getSonAccountList(Long travelerId);

    void updateAccount(Long accountId);

    UserEntity selectByphone(String phone);

    UserEntity selectWithAccount1(String username);

    //根据旅行社ID 以及旅行社用户角色 获取用户userID集合
    List<Long> getUserIdsByTravelerId(LogManageRequest request);

    //根据用户ID 获取用户角色
    UserEntity getUserRole(LogManageRequest request);
}