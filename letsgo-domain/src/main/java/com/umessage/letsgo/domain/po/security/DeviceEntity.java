/*
 * DeviceEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-17 Created by Administrator
 */
package com.umessage.letsgo.domain.po.security;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class DeviceEntity  implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
    private Long userId;
    /**
     * 设备唯一标识
     */
    @ApiModelProperty(value="设备唯一标识")
    private String deviceUuid;
    /**
     * 设备类别【IOS、Android】
     */
    @ApiModelProperty(value="设备类别【IOS、Android】")
    private String deviceType;
    /**
     * 设备型号
     */
    @ApiModelProperty(value="设备型号")
    private String deviceModel;
    /**
     * 设备版本号
     */
    @ApiModelProperty(value="设备版本号")
    private String deviceVersion;
    /**
     * 小米注册ID//或者华为手机的token
     */
    @ApiModelProperty(value="小米注册ID//或者华为手机的token")
    private String regid;
    /**
     * 小米别名
     */
    @ApiModelProperty(value="小米别名")
    private String alias;
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

    @ApiModelProperty(value="用户名")
    private String userName;
    /**
     * 是否为华为手机：1是；0否
     */
    @ApiModelProperty(value = "是否为华为手机：1是；0否")
    private String isHuaWei;

    public String getIsHuaWei() {
        return isHuaWei;
    }

    public void setIsHuaWei(String isHuaWei) {
        this.isHuaWei = isHuaWei;
    }

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

    public String getDeviceUuid() {
        return deviceUuid;
    }

    public void setDeviceUuid(String deviceUuid) {
        this.deviceUuid = deviceUuid == null ? null : deviceUuid.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel == null ? null : deviceModel.trim();
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion == null ? null : deviceVersion.trim();
    }

    public String getRegid() {
        return regid;
    }

    public void setRegid(String regid) {
        this.regid = regid == null ? null : regid.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}