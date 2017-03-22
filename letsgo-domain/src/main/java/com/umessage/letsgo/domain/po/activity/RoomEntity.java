/*
 * RoomEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-31 Created by Administrator
 */
package com.umessage.letsgo.domain.po.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RoomEntity implements Serializable {

        /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 团队ID
     */
    @ApiModelProperty(value="团队ID")
    @JsonIgnore
    private Long tId;
    /**
     * 腾讯群组ID
     */
    @ApiModelProperty(value="腾讯群组ID")
    private String teamId;
    /**
     * 房间号
     */
    @ApiModelProperty(value="房间号")
    private String roomNum;
    /**
     * 入住人数
     */
    @JsonIgnore
    private Integer count;
    /**
     * 房间类型
     */
    @JsonIgnore
    private Integer roomType;
    /**
     * 房卡数量
     */
    @JsonIgnore
    private Integer totalCardCount;
    /**
     * 领取房卡数量
     */
    @JsonIgnore
    private Integer cardCount;
    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonIgnore
    private Date updateTime;
    /**
     * 版本号
     */
    @JsonIgnore
    private Long version;
    /**
     * 房间明细列表
     */
    @ApiModelProperty(value="房间明细列表")
    @Catalog
    List<RoomDetailEntity> roomDetailList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum == null ? null : roomNum.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    public Integer getTotalCardCount() {
        return totalCardCount;
    }

    public void setTotalCardCount(Integer totalCardCount) {
        this.totalCardCount = totalCardCount;
    }

    public Integer getCardCount() {
        return cardCount;
    }

    public void setCardCount(Integer cardCount) {
        this.cardCount = cardCount;
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

	public List<RoomDetailEntity> getRoomDetailList() {
		return roomDetailList;
	}

	public void setRoomDetailList(List<RoomDetailEntity> roomDetailList) {
		this.roomDetailList = roomDetailList;
	}

    @Override
    public String toString() {
        return "RoomEntity{" +
                "id=" + id +
                ", tId=" + tId +
                ", teamId='" + teamId + '\'' +
                ", roomNum='" + roomNum + '\'' +
                ", count=" + count +
                ", roomType=" + roomType +
                ", totalCardCount=" + totalCardCount +
                ", cardCount=" + cardCount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", version=" + version +
                ", roomDetailList=" + roomDetailList +
                '}';
    }
}