/*
 * UserTagDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-09-08 Created by wendy
 */
package com.umessage.letsgo.dao.security;

import com.umessage.letsgo.domain.po.security.TagsResultEntity;
import com.umessage.letsgo.domain.po.security.UserTagEntity;
import com.umessage.letsgo.domain.vo.security.request.TagsRequest;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserTagDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param userTagEntity
     * @return int
     */
    int insert(UserTagEntity userTagEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.security.UserTagEntity
     */
    UserTagEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.security.UserTagEntity>
     */
    List<UserTagEntity> selectAll();

    /**
     * Description: 
     * @param userTagEntity
     * @return int
     */
    int update(UserTagEntity userTagEntity);
    
    List<TagsResultEntity> getTagListByUserId(@Param(value="userId") Long userId);

    //通过用户ID 和好友ID 删除
    int deleteUserTagByLabeledUserIdAndUserId(TagsRequest request);

    //通过标签ID  删除
    int deleteUserTagByTagId(TagsRequest request);

}
