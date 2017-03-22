/*
 * TagsDao.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-09-08 Created by wendy
 */
package com.umessage.letsgo.dao.security;

import com.umessage.letsgo.domain.po.security.TagsEntity;
import com.umessage.letsgo.domain.vo.security.request.TagRequest;
import com.umessage.letsgo.domain.vo.security.request.TagsRequest;

import java.util.List;

public interface TagsDao {
    /**
     * Description: 
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description: 
     * @param tagsEntity
     * @return int
     */
    int insert(TagsEntity tagsEntity);

    /**
     * Description: 
     * @param id
     * @return com.umessage.letsgo.domain.po.security.TagsEntity
     */
    TagsEntity select(Long id);

    /**
     * Description: 
     * @return java.util.List<com.umessage.letsgo.domain.po.security.TagsEntity>
     */
    List<TagsEntity> selectAll();

    /**
     * Description: 
     * @param tagsEntity
     * @return int
     */
    int update(TagsEntity tagsEntity);

    // 获取用户标签列表
    List<String> getTagNameByUserId(TagRequest request);

    //根据用户ID获取 标签
    List<TagsEntity> getTags(Long userId);

    //通过标签名称 和用户ID  获取标签对象
    TagsEntity  getTagsByNameAndUserId(TagsRequest request);
}