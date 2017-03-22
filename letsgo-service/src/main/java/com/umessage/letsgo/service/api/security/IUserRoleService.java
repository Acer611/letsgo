package com.umessage.letsgo.service.api.security;

import com.umessage.letsgo.domain.po.security.UserRoleEntity;

import java.util.List;

public interface IUserRoleService {
    int create(UserRoleEntity userRole);
    
    //int deleteByPk(Object pk);
    //int delete(UserRoleEntity userRole);
    //int delete(Map<String, Object> conditions);

    //int update(UserRoleEntity userRole);
    //int updateAll(Map<String, Object> conditions);
    
    //int findCount(Map<String, Object> conditions);
    //UserRoleEntity get(Object id);
    List<UserRoleEntity> findAll(UserRoleEntity conditions);
    //PageInfo<UserRoleEntity> findList(int pageSize, int currentPage, Map<String, Object> conditions);

    //UserRoleEntity getRich(Object id);
    //List<UserRoleEntity> findRichAll(Map<String, Object> conditions);
    //PageInfo<UserRoleEntity> findRichList(int pageSize, int currentPage, Map<String, Object> conditions);
    
    List<UserRoleEntity> getUserRoleByUserId(Long userId);
    
    int createUserRoleByUserId(Long userId, Long roleId);
    
}
