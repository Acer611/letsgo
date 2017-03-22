/*
 * SocialAccountEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-13 Created by Administrator
 */
package com.umessage.letsgo.domain.po.security;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class SocialAccountEntity  implements Serializable {

    private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
    private Long userId;
    /**
     * 第三方账号【微信、QQ、新浪等】
     */
    @ApiModelProperty(value="第三方账号【微信、QQ、新浪等】")
    private String account;
    /**
     * 账号密码
     */
    @ApiModelProperty(value="账号密码")
    private String accountPassword;
    /**
     * 账号类型
     */
    @ApiModelProperty(value="账号类型")
    private Integer accountType;
    /**
     * 账号openid
     */
    @ApiModelProperty(value="账号openid")
    private String accountOpenid;
    /**
     * openid时间戳
     */
    @ApiModelProperty(value="openid时间戳")
    private Integer openidTimestamp;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",dataType = "java.lang.Long")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间",dataType = "java.lang.Long")
    private Date updateTime;
    /**
     * 版本号
     */
    @ApiModelProperty(value="版本号")
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword == null ? null : accountPassword.trim();
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getAccountOpenid() {
        return accountOpenid;
    }

    public void setAccountOpenid(String accountOpenid) {
        this.accountOpenid = accountOpenid == null ? null : accountOpenid.trim();
    }

    public Integer getOpenidTimestamp() {
        return openidTimestamp;
    }

    public void setOpenidTimestamp(Integer openidTimestamp) {
        this.openidTimestamp = openidTimestamp;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}