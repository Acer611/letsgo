package com.umessage.letsgo.domain.po.security;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by pw on 2016/11/23.
 */
public class ThirdPartyAccountEntity implements Serializable {
    //ID
    private Long id;
    //用户ID
    private Long userId;
    //账号类型 0 微信 1 微博
    private Integer accountType;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //微信账号唯一标识ID
    private String unionID;

    //微信用户信息
    private  WxInfoEntity wxInfoEntity;
    //APP user信息
    private UserEntity userEntity;

    public WxInfoEntity getWxInfoEntity() {
        return wxInfoEntity;
    }

    public void setWxInfoEntity(WxInfoEntity wxInfoEntity) {
        this.wxInfoEntity = wxInfoEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUnionID() {
        return unionID;
    }

    public void setUnionID(String unionID) {
        this.unionID = unionID;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }
}
