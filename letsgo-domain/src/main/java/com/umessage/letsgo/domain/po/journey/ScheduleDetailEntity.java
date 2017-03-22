/*
 * ScheduleDetailEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-31 Created by ZhaoYidong
 */
package com.umessage.letsgo.domain.po.journey;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleResponse;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ScheduleDetailEntity  implements Serializable {

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
     * 第几天
     */
    @ApiModelProperty(value="第几天")
    private Integer dayNum;
    /**
     * 日期
     */
    @ApiModelProperty(value="日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date scheduleDate;

    /**
     * 出发地
     */
    @ApiModelProperty(value="出发地")
    private String startPlace;

    /**
     * 出发地id
     */
    @ApiModelProperty(value="出发地id")
    private String startPlaceId;

    /**
     * 到第一个城市交通工具
     */
    @ApiModelProperty(value="到第一个目的地城市交通工具  1 飞机 2 火车 3 大巴 4 船")
    private Integer traffic1;
    /**
     * 到第二个城市交通工具
     */
    @ApiModelProperty(value="到第二个目的地城市交通工具  1 飞机 2 火车 3 大巴 4 船")
    private Integer traffic2;
    /**
     * 到第三个城市交通工具
     */
    @ApiModelProperty(value="到第三个目的地城市交通工具  1 飞机 2 火车 3 大巴 4 船")
    private Integer traffic3;
    /**
     * 到第四个城市交通工具
     */
    @ApiModelProperty(value="到第四个目的地城市交通工具  1 飞机 2 火车 3 大巴 4 船")
    private Integer traffic4;
    /**
     * 到第五个城市交通工具
     */
    @ApiModelProperty(value="到第五个目的地城市交通工具 1 飞机 2 火车 3 大巴 4 船")
    private Integer traffic5;
    /**
     * 目的地1
     */
    @ApiModelProperty(value="目的地1")
    private String destination1;

    /**
     * 目的地2
     */
    @ApiModelProperty(value="目的地2")
    private String destination2;


    /**
     * 目的地3
     */
    @ApiModelProperty(value="目的地3")
    private String destination3;
    /**
     * 目的地4
     */
    @ApiModelProperty(value="目的地4")
    private String destination4;
    /**
     * 目的地5
     */
    @ApiModelProperty(value="目的地5")
    private String destination5;
    /**
     * 目的地1
     */
    @ApiModelProperty(value="目的地1id")
    private String destinationId1;
    /**
     * 目的地2
     */
    @ApiModelProperty(value="目的地2id")
    private String destinationId2;
    /**
     * 目的地3
     */
    @ApiModelProperty(value="目的地3id")
    private String destinationId3;
    /**
     * 目的地4
     */
    @ApiModelProperty(value="目的地4id")
    private String destinationId4;
    /**
     * 目的地5
     */
    @ApiModelProperty(value="目的地5id")
    private String destinationId5;

    /**
     * 出发地时区
     */
    @ApiModelProperty(value="出发地时区")
    private String startTimeZone;
    /**
     * 出发地时区对应的北京时间
     */
    @ApiModelProperty(value="出发地时区对应的北京时间")
    private Date startTimeZoneDate;
    /**
     * 目的地时区
     */
    @ApiModelProperty(value="目的地时区")
    private String destinationTimeZone;
    /**
     * 目的地时间对应的北京时间
     */
    @ApiModelProperty(value="目的地时间对应的北京时间")
    private Date destinationTimeZoneDate;

    /**
     * 行程图片1
     */
    @ApiModelProperty(value="行程图片1")
    private String schedulePhotosUrl1;
    /**
     * 行程图片2
     */
    @ApiModelProperty(value="行程图片2")
    private String schedulePhotosUrl2;
    /**
     * 行程图片3
     */
    @ApiModelProperty(value="行程图片3")
    private String schedulePhotosUrl3;
    /**
     * 行程概述
     */
    @ApiModelProperty(value="行程概述")
    private String desc;
    /**
     * 酒店是否确认
     */
    @ApiModelProperty(value="酒店是否确认[确认：1，未确认：0]")
    private Integer hotelConfirm;

    @ApiModelProperty(value="国家1Id")
    private String countryId1;

    @ApiModelProperty(value="国家1")
    private String country1;

    @ApiModelProperty(value="国家2Id")
    private String countryId2;

    @ApiModelProperty(value="国家2")
    private String country2;

    @ApiModelProperty(value="国家3Id")
    private String countryId3;

    @ApiModelProperty(value="国家3")
    private String country3;

    @ApiModelProperty(value="国家4Id")
    private String countryId4;

    @ApiModelProperty(value="国家4")
    private String country4;

    @ApiModelProperty(value="国家5Id")
    private String countryId5;

    @ApiModelProperty(value="国家5")
    private String country5;

    /**
     * 酒店名称
     */
    @ApiModelProperty(value="酒店名称")
    private String hotel;
    /**
     * 酒店id
     */
    @ApiModelProperty(value="酒店id")
    private String hotelId;
    /**
     * 酒店不确定后输入的名称
     */
    @ApiModelProperty(value="酒店不确定后输入的名称")
    private String hotelInput;
    /**
     * 是否是同级酒店
     */
    @ApiModelProperty(value="是否是同级酒店[是：1，否：0]")
    private Integer sameLevel;
    /**
     * 航班1
     */
    @ApiModelProperty(value="航班1")
    private String flight1;
    /**
     * 航班2
     */
    @ApiModelProperty(value="航班2")
    private String flight2;
    /**
     * 交通
     */
    @ApiModelProperty(value="交通")
    private String trafficInfo;
    /**
     * 购物
     */
    @ApiModelProperty(value="购物")
    private String shoppInfo;
    /**
     * 餐饮说明
     */
    @ApiModelProperty(value="餐饮说明")
    private String cateringInfo;
    /**
     * 导游id
     */
    @ApiModelProperty(value="导游id")
    private Long leaderId;
    /**
     * 新增条目
     */
    @ApiModelProperty(value="新增条目")
    private String newEntry;
    /**
     * 新增内容
     */
    @ApiModelProperty(value="新增内容")
    private String newContant;
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
    /**
     * 版本号
     */
    @ApiModelProperty(value="版本号")
    private Long version;

    //以下为临时字段：
    @ApiModelProperty(value ="每日行程对应的多个景点")
    private List<ScenicEntity> scenicList;

    @ApiModelProperty(value="导游姓名")
    private String name;

    @ApiModelProperty(value="性别【1：男性；2：女性】")
    private Integer sex;

    @ApiModelProperty(value="手机号码")
    private String phone;

    /**
     * 天气描述
     */
    @ApiModelProperty(value = "天气描述")
    private String weatherDescn;

    @ApiModelProperty(value = "出发地天气描述")
    private String startPlaceWeatherDescn;

    @ApiModelProperty(value = "第一个目的地天气描述")
    private String firstDayWeatherDescn;

    @ApiModelProperty(value = "每日行程对应的景点实体")
    private List<ScheduleDetailScenicEntity> scheduleDetailScenicEntitys;

    @ApiModelProperty(value = "每日行程对应的酒店实体")
    private HotelScheduleEntity hotelSchedule;

    @ApiModelProperty(value="每日行程购物场所关联关系实体")
    private List<ShoppingScheduleEntity> shoppingScheduleList;

    @ApiModelProperty(value="每日行程自费项目关联关系实体")
    private List<OwnExpenseScheduleEntity> ownExpenseScheduleList;


    public String getCountryId4() {
        return countryId4;
    }

    public void setCountryId4(String countryId4) {
        this.countryId4 = countryId4;
    }

    public String getCountryId1() {
        return countryId1;
    }

    public void setCountryId1(String countryId1) {
        this.countryId1 = countryId1;
    }

    public String getCountryId2() {
        return countryId2;
    }

    public void setCountryId2(String countryId2) {
        this.countryId2 = countryId2;
    }

    public String getCountryId3() {
        return countryId3;
    }

    public void setCountryId3(String countryId3) {
        this.countryId3 = countryId3;
    }

    public String getCountry1() {
        return country1;
    }

    public void setCountry1(String country1) {
        this.country1 = country1;
    }

    public String getCountry2() {
        return country2;
    }

    public void setCountry2(String country2) {
        this.country2 = country2;
    }

    public String getCountry3() {
        return country3;
    }

    public void setCountry3(String country3) {
        this.country3 = country3;
    }

    public String getCountry4() {
        return country4;
    }

    public void setCountry4(String country4) {
        this.country4 = country4;
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

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getStartPlaceId() {
        return startPlaceId;
    }

    public void setStartPlaceId(String startPlaceId) {
        this.startPlaceId = startPlaceId;
    }

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

    public Integer getDayNum() {
        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace == null ? null : startPlace.trim();
    }

    public String getDestination1() {
        return destination1;
    }

    public void setDestination1(String destination1) {
        this.destination1 = destination1 == null ? null : destination1.trim();
    }

    public String getDestination2() {
        return destination2;
    }

    public void setDestination2(String destination2) {
        this.destination2 = destination2 == null ? null : destination2.trim();
    }

    public String getDestination3() {
        return destination3;
    }

    public void setDestination3(String destination3) {
        this.destination3 = destination3 == null ? null : destination3.trim();
    }

    public String getDestination4() {
        return destination4;
    }

    public void setDestination4(String destination4) {
        this.destination4 = destination4 == null ? null : destination4.trim();
    }

    public String getStartTimeZone() {
        return startTimeZone;
    }

    public void setStartTimeZone(String startTimeZone) {
        this.startTimeZone = startTimeZone;
    }

    public Date getStartTimeZoneDate() {
        return startTimeZoneDate;
    }

    public void setStartTimeZoneDate(Date startTimeZoneDate) {
        this.startTimeZoneDate = startTimeZoneDate;
    }

    public String getDestinationTimeZone() {
        return destinationTimeZone;
    }

    public void setDestinationTimeZone(String destinationTimeZone) {
        this.destinationTimeZone = destinationTimeZone;
    }

    public Date getDestinationTimeZoneDate() {
        return destinationTimeZoneDate;
    }

    public void setDestinationTimeZoneDate(Date destinationTimeZoneDate) {
        this.destinationTimeZoneDate = destinationTimeZoneDate;
    }

    public String getSchedulePhotosUrl1() {
        return schedulePhotosUrl1;
    }

    public void setSchedulePhotosUrl1(String schedulePhotosUrl1) {
        this.schedulePhotosUrl1 = schedulePhotosUrl1 == null ? null : schedulePhotosUrl1.trim();
    }

    public String getSchedulePhotosUrl2() {
        return schedulePhotosUrl2;
    }

    public void setSchedulePhotosUrl2(String schedulePhotosUrl2) {
        this.schedulePhotosUrl2 = schedulePhotosUrl2 == null ? null : schedulePhotosUrl2.trim();
    }

    public String getSchedulePhotosUrl3() {
        return schedulePhotosUrl3;
    }

    public void setSchedulePhotosUrl3(String schedulePhotosUrl3) {
        this.schedulePhotosUrl3 = schedulePhotosUrl3 == null ? null : schedulePhotosUrl3.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Integer getHotelConfirm() {
        return hotelConfirm;
    }

    public void setHotelConfirm(Integer hotelConfirm) {
        this.hotelConfirm = hotelConfirm;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel == null ? null : hotel.trim();
    }

    public String getHotelInput() {
        return hotelInput;
    }

    public void setHotelInput(String hotelInput) {
        this.hotelInput = hotelInput == null ? null : hotelInput.trim();
    }

    public Integer getSameLevel() {
        return sameLevel;
    }

    public void setSameLevel(Integer sameLevel) {
        this.sameLevel = sameLevel;
    }

    public String getFlight1() {
        return flight1;
    }

    public void setFlight1(String flight1) {
        this.flight1 = flight1 == null ? null : flight1.trim();
    }

    public String getFlight2() {
        return flight2;
    }

    public void setFlight2(String flight2) {
        this.flight2 = flight2 == null ? null : flight2.trim();
    }

    public String getTrafficInfo() {
        return trafficInfo;
    }

    public void setTrafficInfo(String trafficInfo) {
        this.trafficInfo = trafficInfo == null ? null : trafficInfo.trim();
    }

    public String getShoppInfo() {
        return shoppInfo;
    }

    public void setShoppInfo(String shoppInfo) {
        this.shoppInfo = shoppInfo == null ? null : shoppInfo.trim();
    }

    public String getCateringInfo() {
        return cateringInfo;
    }

    public void setCateringInfo(String cateringInfo) {
        this.cateringInfo = cateringInfo == null ? null : cateringInfo.trim();
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
        this.newEntry = newEntry == null ? null : newEntry.trim();
    }

    public String getNewContant() {
        return newContant;
    }

    public void setNewContant(String newContant) {
        this.newContant = newContant == null ? null : newContant.trim();
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

    public List<ScenicEntity> getScenicList() {
        return scenicList;
    }

    public void setScenicList(List<ScenicEntity> scenicList) {
        this.scenicList = scenicList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeatherDescn() {
        return weatherDescn;
    }

    public void setWeatherDescn(String weatherDescn) {
        this.weatherDescn = weatherDescn;
    }

    public String getStartPlaceWeatherDescn() {
        return startPlaceWeatherDescn;
    }

    public void setStartPlaceWeatherDescn(String startPlaceWeatherDescn) {
        this.startPlaceWeatherDescn = startPlaceWeatherDescn;
    }

    public String getFirstDayWeatherDescn() {
        return firstDayWeatherDescn;
    }

    public void setFirstDayWeatherDescn(String firstDayWeatherDescn) {
        this.firstDayWeatherDescn = firstDayWeatherDescn;
    }

    public HotelScheduleEntity getHotelSchedule() {
        return hotelSchedule;
    }

    public void setHotelSchedule(HotelScheduleEntity hotelSchedule) {
        this.hotelSchedule = hotelSchedule;
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

    public Integer getTraffic1() {
        return traffic1;
    }

    public void setTraffic1(Integer traffic1) {
        this.traffic1 = traffic1;
    }

    public Integer getTraffic2() {
        return traffic2;
    }

    public void setTraffic2(Integer traffic2) {
        this.traffic2 = traffic2;
    }

    public Integer getTraffic3() {
        return traffic3;
    }

    public void setTraffic3(Integer traffic3) {
        this.traffic3 = traffic3;
    }

    public Integer getTraffic4() {
        return traffic4;
    }

    public void setTraffic4(Integer traffic4) {
        this.traffic4 = traffic4;
    }

    public Integer getTraffic5() {
        return traffic5;
    }

    public void setTraffic5(Integer traffic5) {
        this.traffic5 = traffic5;
    }

    public String getDestination5() {
        return destination5;
    }

    public void setDestination5(String destination5) {
        this.destination5 = destination5;
    }

    public String getDestinationId5() {
        return destinationId5;
    }

    public void setDestinationId5(String destinationId5) {
        this.destinationId5 = destinationId5;
    }

    public String getCountryId5() {
        return countryId5;
    }

    public void setCountryId5(String countryId5) {
        this.countryId5 = countryId5;
    }

    public String getCountry5() {
        return country5;
    }

    public void setCountry5(String country5) {
        this.country5 = country5;
    }

    public List<ScheduleDetailScenicEntity> getScheduleDetailScenicEntitys() {
        return scheduleDetailScenicEntitys;
    }

    public void setScheduleDetailScenicEntitys(List<ScheduleDetailScenicEntity> scheduleDetailScenicEntitys) {
        this.scheduleDetailScenicEntitys = scheduleDetailScenicEntitys;
    }

    /**
     *必填字段数据验证
     */
    public ScheduleResponse dataVerify() {
        ScheduleResponse verifyResponse = new ScheduleResponse();
        verifyResponse.setScheduleDetailEntity(this);
        verifyResponse.setRetCode(ErrorConstant.INVALID_PARAMETER);
        if (startPlace == null || StringUtils.isEmpty(startPlace.trim())) {
            verifyResponse.setRetMsg("出发地不能为空");
            return verifyResponse;
        }
        if (startPlace.length() > 60) {
            verifyResponse.setRetMsg("出发地(" + startPlace + ")长度不能超过60个字符");
            return verifyResponse;
        }
        if (StringUtils.isEmpty(desc)) {
            verifyResponse.setRetMsg("行程概述不能为空");
            return verifyResponse;
        }
        startPlace = startPlace.trim();
        verifyResponse.setRetCode(0);
        return verifyResponse;
    }

    @Override
    public String toString() {
        return "ScheduleDetailEntity{" +
                "id=" + id +
                ", jourId=" + jourId +
                ", dayNum=" + dayNum +
                ", scheduleDate=" + scheduleDate +
                ", startPlace='" + startPlace + '\'' +
                ", destination1='" + destination1 + '\'' +
                ", destination2='" + destination2 + '\'' +
                ", destination3='" + destination3 + '\'' +
                ", destination4='" + destination4 + '\'' +
                ", destinationId1='" + destinationId1 + '\'' +
                ", destinationId2='" + destinationId2 + '\'' +
                ", destinationId3='" + destinationId3 + '\'' +
                ", destinationId4='" + destinationId4 + '\'' +
                ", schedulePhotosUrl1='" + schedulePhotosUrl1 + '\'' +
                ", schedulePhotosUrl2='" + schedulePhotosUrl2 + '\'' +
                ", schedulePhotosUrl3='" + schedulePhotosUrl3 + '\'' +
                ", desc='" + desc + '\'' +
                ", hotelConfirm=" + hotelConfirm +
                ", countryId1='" + countryId1 + '\'' +
                ", country1='" + country1 + '\'' +
                ", countryId2='" + countryId2 + '\'' +
                ", country2='" + country2 + '\'' +
                ", countryId3='" + countryId3 + '\'' +
                ", country3='" + country3 + '\'' +
                ", countryId4='" + countryId4 + '\'' +
                ", country4='" + country4 + '\'' +
                ", hotel='" + hotel + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", hotelInput='" + hotelInput + '\'' +
                ", sameLevel=" + sameLevel +
                ", flight1='" + flight1 + '\'' +
                ", flight2='" + flight2 + '\'' +
                ", trafficInfo='" + trafficInfo + '\'' +
                ", shoppInfo='" + shoppInfo + '\'' +
                ", cateringInfo='" + cateringInfo + '\'' +
                ", leaderId=" + leaderId +
                ", newEntry='" + newEntry + '\'' +
                ", newContant='" + newContant + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", version=" + version +
                ", scenicList=" + scenicList +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", phone='" + phone + '\'' +
                ", weatherDescn='" + weatherDescn + '\'' +
                '}';
    }
}