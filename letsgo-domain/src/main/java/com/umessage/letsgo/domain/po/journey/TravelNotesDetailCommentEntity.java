/*
 * TravelNotesDetailCommentEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-10-09 Created by Administrator
 */
package com.umessage.letsgo.domain.po.journey;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

public class TravelNotesDetailCommentEntity {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 游记明细id
     */
    @ApiModelProperty(value="游记明细id")
    private Long travelNotesDetailId;
    /**
     * 点评类型（1酒店 2景点 3其他）
     */
    @ApiModelProperty(value="点评类型（1酒店 2景点 3其他）")
    private Long type;
    /**
     * 点评项id
     */
    @ApiModelProperty(value="点评项id")
    private String typeId;
    /**
     * 点评项名称
     */
    @ApiModelProperty(value="点评项名称")
    private String typeName;
    /**
     * 点评内容
     */
    @ApiModelProperty(value="点评内容")
    private String comment;
    /**
     * 点评项图片
     */
    @ApiModelProperty(value="点评项图片")
    private String typeImgurl;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date updateTime;
    /**
     * 版本
     */
    @ApiModelProperty(value="版本")
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTravelNotesDetailId() {
        return travelNotesDetailId;
    }

    public void setTravelNotesDetailId(Long travelNotesDetailId) {
        this.travelNotesDetailId = travelNotesDetailId;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getTypeImgurl() {
        return typeImgurl;
    }

    public void setTypeImgurl(String typeImgurl) {
        this.typeImgurl = typeImgurl == null ? null : typeImgurl.trim();
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