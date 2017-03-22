/*
 * RewardDetailsEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-08-30 Created by wendy
 */
package com.umessage.letsgo.domain.po.security;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

public class RewardDetailsEntity {

    private Long id;
    /**
     * 奖励人
     */
    @ApiModelProperty(value="奖励人")
    private Long userId;
    /**
     * 奖励金额
     */
    @ApiModelProperty(value="奖励金额")
    private Double amount;
    /**
     * 奖励描述
     */
    @ApiModelProperty(value="奖励描述")
    private String descn;
    /**
     * 是否入账【0：未入账；1：已入账】
     */
    @ApiModelProperty(value="是否入账【0：未入账；1：已入账】")
    private Integer isInto;
    private Date createTime;
    /**
     * 奖励来源（用户ID）
     */
    @ApiModelProperty(value="奖励来源（用户ID）")
    private Long fromId;
    /**
     * 奖励类型【1：邀请奖励；2：带团奖励；3：分享奖励】
     */
    @ApiModelProperty(value="奖励类型【1：邀请奖励；2：带团奖励；3：分享奖励】")
    private Integer rewardType;
    /**
     * 团队ID
     */
    @ApiModelProperty(value="团队ID")
    private Long teamId;
    /**
     * 带团奖励对象 1：邀请人，2：被邀请人
     */
    @ApiModelProperty(value="带团奖励对象 1：邀请人，2：被邀请人")
    private Integer rewardFrom;

    private String phone;
    private String realName;
    private String photoUrl;

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn == null ? null : descn.trim();
    }

    public Integer getIsInto() {
        return isInto;
    }

    public void setIsInto(Integer isInto) {
        this.isInto = isInto;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Integer getRewardFrom() {
        return rewardFrom;
    }

    public void setRewardFrom(Integer rewardFrom) {
        this.rewardFrom = rewardFrom;
    }
}