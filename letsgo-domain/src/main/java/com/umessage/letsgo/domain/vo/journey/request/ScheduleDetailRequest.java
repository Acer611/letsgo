package com.umessage.letsgo.domain.vo.journey.request;

import com.umessage.letsgo.domain.po.journey.*;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.*;

/**
 * Created by ZhaoYidong on 2016/5/16.
 */
public class ScheduleDetailRequest implements Serializable {
    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 行程ID
     */
    @ApiModelProperty(value="行程ID")
    private Long jourId;

    /**
     * 行程详细日期
     */
    @ApiModelProperty(value="日期")
    private String date;
    /**
     * 团队Id
     */
    @ApiModelProperty(value ="团队id")
    private Long teamId;

    @ApiModelProperty(value="交通")
    private String trafficInfo;

    @ApiModelProperty(value="导游id")
    private Long leaderId;

    @ApiModelProperty(value="新增条目")
    private String newEntry;

    @ApiModelProperty(value="新增内容")
    private String newContant;

    //在手机端编辑酒店时，都视为酒店名称不能确定的情况。
    @ApiModelProperty(value="酒店不确定后输入的名称")
    private String hotelId;
    //在手机端编辑酒店时，都视为酒店名称不能确定的情况。
    @ApiModelProperty(value="酒店不确定后输入的名称")
    private String destinationId1;
    @ApiModelProperty(value="酒店不确定后输入的名称")
    private String destinationId2;

    @ApiModelProperty(value="行程监控 国家")
    private  String country1;

    @ApiModelProperty(value="行程监控 国家Id")
    private  String countryId1;

    @ApiModelProperty(value="行程监控 日期")
    private Date scheduleDate;



    @ApiModelProperty(value="每日行程实体信息")
    private ScheduleDetailEntity scheduleDetailEntity;

    @ApiModelProperty(value="每日行程景点关联关系实体")
    private List<ScheduleDetailScenicEntity> scheduleDetailScenicEntitys;

    @ApiModelProperty(value="每日行程酒店关联关系实体")
    private HotelScheduleEntity hotelScheduleEntity;

    @ApiModelProperty(value="每日行程购物场所关联关系实体")
    private List<ShoppingScheduleEntity> shoppingScheduleList;

    @ApiModelProperty(value="每日行程自费项目关联关系实体")
    private List<OwnExpenseScheduleEntity> ownExpenseScheduleList;

    /**
     * 旅行社ID
     */
    private Long travelId;

    @ApiModelProperty(value = "行程状态")
    private Integer status;

    public String getCountryId1() {
        return countryId1;
    }

    public void setCountryId1(String countryId1) {
        this.countryId1 = countryId1;
    }

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCountry1() {
        return country1;
    }

    public void setCountry1(String country1) {
        this.country1 = country1;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getDestinationId1() {
        return destinationId1;
    }

    public void setDestinationId1(String destinationId1) {
        this.destinationId1 = destinationId1;
    }

    public String getDestinationId2() {
        return destinationId2;
    }

    public void setDestinationId2(String destinationId2) {
        this.destinationId2 = destinationId2;
    }

    public String getDestinationId3() {
        return destinationId3;
    }

    public void setDestinationId3(String destinationId3) {
        this.destinationId3 = destinationId3;
    }

    public String getDestinationId4() {
        return destinationId4;
    }

    public void setDestinationId4(String destinationId4) {
        this.destinationId4 = destinationId4;
    }

    @ApiModelProperty(value="酒店不确定后输入的名称")
    private String destinationId3;
    @ApiModelProperty(value="酒店不确定后输入的名称")
    private String destinationId4;
    //在手机端编辑酒店时，都视为酒店名称不能确定的情况。
    @ApiModelProperty(value="酒店不确定后输入的名称")
    private String hotelInput;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJourId() {
        return jourId;
    }

    public void setJourId(Long jourId) {
        this.jourId = jourId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTrafficInfo() {
        return trafficInfo;
    }

    public void setTrafficInfo(String trafficInfo) {
        this.trafficInfo = trafficInfo;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public String getNewEntry() {
        return newEntry;
    }

    public void setNewEntry(String newEntry) {
        this.newEntry = newEntry;
    }

    public String getNewContant() {
        return newContant;
    }

    public void setNewContant(String newContant) {
        this.newContant = newContant;
    }

    public String getHotelInput() {
        return hotelInput;
    }

    public void setHotelInput(String hotelInput) {
        this.hotelInput = hotelInput;
    }

    public List<ScheduleDetailScenicEntity> getScheduleDetailScenicEntitys() {
        return scheduleDetailScenicEntitys;
    }

    public void setScheduleDetailScenicEntitys(List<ScheduleDetailScenicEntity> scheduleDetailScenicEntitys) {
        this.scheduleDetailScenicEntitys = scheduleDetailScenicEntitys;
    }

    public ScheduleDetailEntity getScheduleDetailEntity() {
        return scheduleDetailEntity;
    }

    public void setScheduleDetailEntity(ScheduleDetailEntity scheduleDetailEntity) {
        this.scheduleDetailEntity = scheduleDetailEntity;
    }

    public HotelScheduleEntity getHotelScheduleEntity() {
        return hotelScheduleEntity;
    }

    public void setHotelScheduleEntity(HotelScheduleEntity hotelScheduleEntity) {
        this.hotelScheduleEntity = hotelScheduleEntity;
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

    @Override
    public String toString() {
        return "ScheduleDetailRequest{" +
                "id=" + id +
                ", jourId=" + jourId +
                ", date='" + date + '\'' +
                ", teamId=" + teamId +
                ", trafficInfo='" + trafficInfo + '\'' +
                ", leaderId=" + leaderId +
                ", newEntry='" + newEntry + '\'' +
                ", newContant='" + newContant + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", destinationId1='" + destinationId1 + '\'' +
                ", destinationId2='" + destinationId2 + '\'' +
                ", country1='" + country1 + '\'' +
                ", countryId1='" + countryId1 + '\'' +
                ", scheduleDate=" + scheduleDate +
                ", travelId=" + travelId +
                ", status=" + status +
                ", destinationId3='" + destinationId3 + '\'' +
                ", destinationId4='" + destinationId4 + '\'' +
                ", hotelInput='" + hotelInput + '\'' +
                '}';
    }
}
