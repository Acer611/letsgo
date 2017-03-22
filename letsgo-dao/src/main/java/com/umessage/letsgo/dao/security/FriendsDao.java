package com.umessage.letsgo.dao.security;

import com.umessage.letsgo.domain.po.security.FriendsEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zengguoqing on 2016/9/6.
 */
public interface FriendsDao {
    void saveFriend(FriendsEntity friendsEntity);

    List<FriendsEntity> getAllFriends(@Param("userId") Long userId, @Param("friendType") Integer friendType);

    FriendsEntity getFriendByUserId(@Param("userId") Long userId, @Param("friendTXUserId") String friendTXUserId);
}
