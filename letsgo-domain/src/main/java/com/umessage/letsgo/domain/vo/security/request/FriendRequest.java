package com.umessage.letsgo.domain.vo.security.request;

/**
 * Created by zhajl on 16/9/24.
 */
public class FriendRequest {

    private Long userId;

    private String txUserId;

    private Long friendUserId;

    private String friendTxUserId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTxUserId() {
        return txUserId;
    }

    public void setTxUserId(String txUserId) {
        this.txUserId = txUserId;
    }

    public Long getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(Long friendUserId) {
        this.friendUserId = friendUserId;
    }

    public String getFriendTxUserId() {
        return friendTxUserId;
    }

    public void setFriendTxUserId(String friendTxUserId) {
        this.friendTxUserId = friendTxUserId;
    }

}
