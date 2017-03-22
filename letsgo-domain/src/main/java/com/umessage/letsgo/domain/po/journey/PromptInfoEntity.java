/*
 * PromptInfoEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-06-02 Created by ZhaoYidong
 */
package com.umessage.letsgo.domain.po.journey;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PromptInfoEntity  implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 行程表_ID
     */
    @ApiModelProperty(value="行程表_ID")
    private Long scheduleId;
    /**
     * 天气概况
     */
    @ApiModelProperty(value="天气概况")
    private String weatherSituation;
    /**
     * 着装要求
     */
    @ApiModelProperty(value="着装要求")
    private String dressCode;
    /**
     * 时间
     */
    @ApiModelProperty(value="时间")
    private String timeInfo;
    /**
     * 语言
     */
    @ApiModelProperty(value="语言")
    private String languages;
    /**
     * 电压
     */
    @ApiModelProperty(value="电压")
    private String voltage;
    /**
     * 饮食
     */
    @ApiModelProperty(value="饮食")
    private String foods;
    /**
     * 必备物品
     */
    @ApiModelProperty(value="必备物品")
    private String essentialItem;
    /**
     * 货币
     */
    @ApiModelProperty(value="货币")
    private String currency;
    /**
     * 电话通信
     */
    @ApiModelProperty(value="电话通信")
    private String telephoneCommunication;
    /**
     * 酒店住宿
     */
    @ApiModelProperty(value="酒店住宿")
    private String hotel;
    /**
     * 风俗禁忌
     */
    @ApiModelProperty(value="风俗禁忌")
    private String customsForbid;
    /**
     * 水上活动的注意事项
     */
    @ApiModelProperty(value="水上活动的注意事项")
    private String waterActivitiesNote;
    /**
     * 小费情况
     */
    @ApiModelProperty(value="小费情况")
    private String tipCase;
    /**
     * 海关
     */
    @ApiModelProperty(value="海关")
    private String customs;
    /**
     * 中华人民共和国海关总署公告
     */
    @ApiModelProperty(value="中华人民共和国海关总署公告")
    private String chinaNotic;
    /**
     * 新增标题
     */
    @ApiModelProperty(value="新增标题")
    private String newTitle;
    /**
     * 新增内容
     */
    @ApiModelProperty(value="新增内容")
    private String newContant;
    /**
     * 安全与防范
     */
    @ApiModelProperty(value="安全与防范")
    private String security;
    /**
     * 特别注意
     */
    @ApiModelProperty(value="特别注意")
    private String specialAttention;
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
     * 版本号
     */
    @ApiModelProperty(value="版本号")
    private Long version;
    /**
     * 旅游费用包含
     */
    @ApiModelProperty(value="旅游费用包含")
    private String costInclude;
    /**
     * 旅游费用不包含
     */
    @ApiModelProperty(value="旅游费用不包含")
    private String costNotInclude;
    /**
     * 自费项目介绍
     */
    @ApiModelProperty(value="自费项目介绍")
    private String ownExpenceInfo;
    /**
     * 购物场所
     */
    @ApiModelProperty(value="购物场所")
    private String shoppingPlace;
    /**
     * 地接社信息
     */
    @ApiModelProperty(value="地接社信息")
    private String travelAgencyInfo;
    /**
     * 接团社信息
     */
    @ApiModelProperty(value="接团社信息")
    private String groupClubInfo;

    //新增内容
    @ApiModelProperty(value="每日行程购物场所关联关系实体")
    private List<ShoppingScheduleEntity> shoppingScheduleList;

    @ApiModelProperty(value="每日行程自费项目关联关系实体")
    private List<OwnExpenseScheduleEntity> ownExpenseScheduleList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getWeatherSituation() {
        return weatherSituation;
    }

    public void setWeatherSituation(String weatherSituation) {
        this.weatherSituation = weatherSituation == null ? null : weatherSituation.trim();
    }

    public String getDressCode() {
        return dressCode;
    }

    public void setDressCode(String dressCode) {
        this.dressCode = dressCode == null ? null : dressCode.trim();
    }

    public String getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(String timeInfo) {
        this.timeInfo = timeInfo == null ? null : timeInfo.trim();
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages == null ? null : languages.trim();
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage == null ? null : voltage.trim();
    }

    public String getFoods() {
        return foods;
    }

    public void setFoods(String foods) {
        this.foods = foods == null ? null : foods.trim();
    }

    public String getEssentialItem() {
        return essentialItem;
    }

    public void setEssentialItem(String essentialItem) {
        this.essentialItem = essentialItem == null ? null : essentialItem.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getTelephoneCommunication() {
        return telephoneCommunication;
    }

    public void setTelephoneCommunication(String telephoneCommunication) {
        this.telephoneCommunication = telephoneCommunication == null ? null : telephoneCommunication.trim();
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel == null ? null : hotel.trim();
    }

    public String getCustomsForbid() {
        return customsForbid;
    }

    public void setCustomsForbid(String customsForbid) {
        this.customsForbid = customsForbid == null ? null : customsForbid.trim();
    }

    public String getWaterActivitiesNote() {
        return waterActivitiesNote;
    }

    public void setWaterActivitiesNote(String waterActivitiesNote) {
        this.waterActivitiesNote = waterActivitiesNote == null ? null : waterActivitiesNote.trim();
    }

    public String getTipCase() {
        return tipCase;
    }

    public void setTipCase(String tipCase) {
        this.tipCase = tipCase == null ? null : tipCase.trim();
    }

    public String getCustoms() {
        return customs;
    }

    public void setCustoms(String customs) {
        this.customs = customs == null ? null : customs.trim();
    }

    public String getChinaNotic() {
        return chinaNotic;
    }

    public void setChinaNotic(String chinaNotic) {
        this.chinaNotic = chinaNotic == null ? null : chinaNotic.trim();
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle == null ? null : newTitle.trim();
    }

    public String getNewContant() {
        return newContant;
    }

    public void setNewContant(String newContant) {
        this.newContant = newContant == null ? null : newContant.trim();
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security == null ? null : security.trim();
    }

    public String getSpecialAttention() {
        return specialAttention;
    }

    public void setSpecialAttention(String specialAttention) {
        this.specialAttention = specialAttention == null ? null : specialAttention.trim();
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

    public String getCostInclude() {
        return costInclude;
    }

    public void setCostInclude(String costInclude) {
        this.costInclude = costInclude == null ? null : costInclude.trim();
    }

    public String getCostNotInclude() {
        return costNotInclude;
    }

    public void setCostNotInclude(String costNotInclude) {
        this.costNotInclude = costNotInclude == null ? null : costNotInclude.trim();
    }

    public String getOwnExpenceInfo() {
        return ownExpenceInfo;
    }

    public void setOwnExpenceInfo(String ownExpenceInfo) {
        this.ownExpenceInfo = ownExpenceInfo == null ? null : ownExpenceInfo.trim();
    }

    public String getShoppingPlace() {
        return shoppingPlace;
    }

    public void setShoppingPlace(String shoppingPlace) {
        this.shoppingPlace = shoppingPlace == null ? null : shoppingPlace.trim();
    }

    public String getTravelAgencyInfo() {
        return travelAgencyInfo;
    }

    public void setTravelAgencyInfo(String travelAgencyInfo) {
        this.travelAgencyInfo = travelAgencyInfo == null ? null : travelAgencyInfo.trim();
    }

    public String getGroupClubInfo() {
        return groupClubInfo;
    }

    public void setGroupClubInfo(String groupClubInfo) {
        this.groupClubInfo = groupClubInfo == null ? null : groupClubInfo.trim();
    }

    public List<ShoppingScheduleEntity> getShoppingScheduleList() {
        return shoppingScheduleList;
    }

    public void setShoppingScheduleList(List<ShoppingScheduleEntity> shoppingScheduleList) {
        this.shoppingScheduleList = shoppingScheduleList;
    }

    public List<OwnExpenseScheduleEntity> getOwnExpenseScheduleList() {
        return ownExpenseScheduleList;
    }

    public void setOwnExpenseScheduleList(List<OwnExpenseScheduleEntity> ownExpenseScheduleList) {
        this.ownExpenseScheduleList = ownExpenseScheduleList;
    }
}