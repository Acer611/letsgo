package com.umessage.letsgo.domain.po.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zengguoqing on 2016/9/6.
 */
public class FriendsEntity implements Serializable {

    /**
     * ID
     */
    @JsonIgnore
    private Long id;

    @JsonIgnore
    private Long userId;

    @JsonIgnore
    private String txUserId;

    @ApiModelProperty(value="朋友用户ID")
    private Long friendUserId;

    @ApiModelProperty(value="朋友腾讯云用户ID")
    private  String friendTxUserId;

    @ApiModelProperty(value="朋友类型, 0表示游客, 1表示同行, 2全部")
    private Integer friendType;

    @ApiModelProperty(value="朋友姓名")
    private String friendName;

    @ApiModelProperty(value="朋友头像")
    private String headUrl;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getFriendType() {
        return friendType;
    }

    public void setFriendType(Integer friendType) {
        this.friendType = friendType;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
