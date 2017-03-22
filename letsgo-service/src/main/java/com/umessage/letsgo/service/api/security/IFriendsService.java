package com.umessage.letsgo.service.api.security;

import com.umessage.letsgo.domain.po.security.FriendsEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.request.FriendRequest;
import com.umessage.letsgo.domain.vo.security.respone.FriendListResponse;

/**
 * Created by zengguoqing on 2016/9/6.
 */
public interface IFriendsService {
    CommonResponse saveFriend(FriendRequest friendRequest);

    //CommonResponse saveFriendTwo(FriendsEntity friendsEntity);

    FriendListResponse getAllFriends(Long userId, Integer friendType);

    FriendsEntity getFriendByUserId(Long userId, String friendTXUserId);

}
