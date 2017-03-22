package com.umessage.letsgo.service.api.security;

import com.umessage.letsgo.domain.po.security.UserTagEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.request.TagsRequest;

/**
 * Created by wendy on 2016/9/8.
 */
public interface IUserTagService {
     int add(UserTagEntity userTagEntity);
     //修改
     int update(UserTagEntity userTagEntity);
    //给好友添加标签
    CommonResponse saveTags(TagsRequest request);

    //通过用户ID 和好友ID 删除
    int deleteUserTagByLabeledUserIdAndUserId(TagsRequest request);

    //通过标签ID  删除
    int deleteUserTagByTagId(Long tagId);
}
