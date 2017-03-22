/*
 * ResourceEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-04-07 Created by zhajl
 */
package com.umessage.letsgo.domain.po.security;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class ResourceEntity  implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 权限名称
     */
    @ApiModelProperty(value="权限名称")
    private String name;
    /**
     * 权限类型【如URL】
     */
    @ApiModelProperty(value="权限类型【如URL】")
    private String resType;
    /**
     * 权限内容【如，url字符串】
     */
    @ApiModelProperty(value="权限内容【如，url字符串】")
    private String resString;
    /**
     * 优先级
     */
    @ApiModelProperty(value="优先级")
    private Integer priority;
    /**
     * 权限模块
     */
    @ApiModelProperty(value="权限模块")
    private String moudle;
    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String descn;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType == null ? null : resType.trim();
    }

    public String getResString() {
        return resString;
    }

    public void setResString(String resString) {
        this.resString = resString == null ? null : resString.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getMoudle() {
        return moudle;
    }

    public void setMoudle(String moudle) {
        this.moudle = moudle == null ? null : moudle.trim();
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn == null ? null : descn.trim();
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