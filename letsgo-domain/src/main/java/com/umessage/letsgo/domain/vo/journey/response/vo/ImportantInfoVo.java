package com.umessage.letsgo.domain.vo.journey.response.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by ZhaoYidong on 2016/6/7.
 */
public class ImportantInfoVo implements Serializable {

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



    public String getWeatherSituation() {
        return weatherSituation;
    }

    public void setWeatherSituation(String weatherSituation) {
        this.weatherSituation = weatherSituation;
    }

    public String getDressCode() {
        return dressCode;
    }

    public void setDressCode(String dressCode) {
        this.dressCode = dressCode;
    }

    public String getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(String timeInfo) {
        this.timeInfo = timeInfo;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getFoods() {
        return foods;
    }

    public void setFoods(String foods) {
        this.foods = foods;
    }

    public String getEssentialItem() {
        return essentialItem;
    }

    public void setEssentialItem(String essentialItem) {
        this.essentialItem = essentialItem;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTelephoneCommunication() {
        return telephoneCommunication;
    }

    public void setTelephoneCommunication(String telephoneCommunication) {
        this.telephoneCommunication = telephoneCommunication;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getCustomsForbid() {
        return customsForbid;
    }

    public void setCustomsForbid(String customsForbid) {
        this.customsForbid = customsForbid;
    }

    public String getWaterActivitiesNote() {
        return waterActivitiesNote;
    }

    public void setWaterActivitiesNote(String waterActivitiesNote) {
        this.waterActivitiesNote = waterActivitiesNote;
    }

    public String getTipCase() {
        return tipCase;
    }

    public void setTipCase(String tipCase) {
        this.tipCase = tipCase;
    }

    public String getCustoms() {
        return customs;
    }

    public void setCustoms(String customs) {
        this.customs = customs;
    }

    public String getChinaNotic() {
        return chinaNotic;
    }

    public void setChinaNotic(String chinaNotic) {
        this.chinaNotic = chinaNotic;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public String getNewContant() {
        return newContant;
    }

    public void setNewContant(String newContant) {
        this.newContant = newContant;
    }
}
