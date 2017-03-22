package com.umessage.letsgo.service.api.security;

import com.umessage.letsgo.domain.po.security.TagsEntity;
import com.umessage.letsgo.domain.vo.security.request.TagsRequest;
import com.umessage.letsgo.domain.vo.security.respone.TagsResponse;

import java.util.List;

/**
 * Created by wendy on 2016/9/8.
 */
public interface ITagsService {
    int add(TagsEntity tagsEntity);

    int delete(Long id);

    List<String> getTagNameByUserId(Long viewUserId, Long currentLoginUserId);

    //根据用户ID获取 标签
    TagsResponse getTags(Long userId);

    //通过标签名称 和用户ID  获取标签对象
    TagsEntity  getTagsByNameAndUserId(TagsRequest request);
}
