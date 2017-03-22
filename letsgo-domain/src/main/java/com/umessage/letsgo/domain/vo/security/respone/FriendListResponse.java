package com.umessage.letsgo.domain.vo.security.respone;

import com.umessage.letsgo.domain.po.security.FriendsEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by zengguoqing on 2016/9/6.
 */
public class FriendListResponse extends CommonResponse {
    @ApiModelProperty(value="好友列表")
    private List<FriendsEntity> allFriends;

    public List<FriendsEntity> getAllFriends() {
        return allFriends;
    }

    public void setAllFriends(List<FriendsEntity> allFriends) {
        this.allFriends = allFriends;
    }
}
