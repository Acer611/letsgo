/*
 * AppVerEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-24 Created by zhajl
 */
package com.umessage.letsgo.domain.po.system;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class AppVerEntity  implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 设备类型
     */
    @ApiModelProperty(value="设备类型")
    private String deviceType;
    /**
     * 模块
     */
    @ApiModelProperty(value="模块")
    private String module;
    /**
     * 版本号
     */
    @ApiModelProperty(value="版本号")
    private String version;
    /**
     * 版本说明
     */
    @ApiModelProperty(value="版本说明")
    private String content;
    /**
     * 下载URL
     */
    @ApiModelProperty(value="下载URL")
    private String updateurl;
    /**
     * 是否强制更新
     */
    @ApiModelProperty(value="是否强制更新")
    private Integer updateforce;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module == null ? null : module.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getUpdateurl() {
        return updateurl;
    }

    public void setUpdateurl(String updateurl) {
        this.updateurl = updateurl == null ? null : updateurl.trim();
    }

    public Integer getUpdateforce() {
        return updateforce;
    }

    public void setUpdateforce(Integer updateforce) {
        this.updateforce = updateforce;
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
}