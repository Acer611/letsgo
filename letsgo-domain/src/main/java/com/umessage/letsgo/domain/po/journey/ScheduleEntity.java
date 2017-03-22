/*
 * ScheduleEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-31 Created by ZhaoYidong
 */
package com.umessage.letsgo.domain.po.journey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleResponse;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ScheduleEntity  implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 团队ID
     */
    @ApiModelProperty(value="团队ID")
    private Long teamId;
    /**
     * 行程名称
     */
    @ApiModelProperty(value="行程名称")
    private String name;
    /**
     * 出发地
     */
    @ApiModelProperty(value="出发地")
    private String startPlace;
    /**
     * 开始日期
     */
    @ApiModelProperty(value="开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 结束日期
     */
    @ApiModelProperty(value="结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    /**
     * 集合时间
     */
    @ApiModelProperty(value="集合时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date collectionTime;
    /**
     * 集合地点
     */
    @ApiModelProperty(value="集合地点")
    private String collectionPlace;
    /**
     * 领队id
     */
    @ApiModelProperty(value="领队id")
    private Long leaderId;
    /**
     * 行程特色
     */
    @ApiModelProperty(value="行程特色")
    private String feature;
    /**
     * 行程特色图片
     */
    @ApiModelProperty(value="行程特色图片")
    private String featurePhoto;
    /**
     * 紧急联系人
     */
    @ApiModelProperty(value="紧急联系人")
    private String emergencyContact;
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
    /**
     * 发布状态：1全部-2已发布-3未发布-4待确认
     */
    @ApiModelProperty(value="发布状态：1全部-2已发布-3未发布-4待确认")
    private Integer processStatus;

    @ApiModelProperty(value = "行程资料")
    private String processPhotosUrl;

    @ApiModelProperty(value = "行程资料集合")
    private List<String> processPhotosUrls;

    /**
     * 目的地国家的名称
     */
    @ApiModelProperty(value="目的地国家的名称")
    private String endPlace;

    //以下为临时字段：
    @ApiModelProperty(value ="每日行程集合")
    private List<ScheduleDetailEntity> scheduleDetailList;

    @ApiModelProperty(value="领队姓名")
    private String leadName;

    @ApiModelProperty(value="性别【1：男性；2：女性】")
    private Integer sex;

    @ApiModelProperty(value="手机号码")
    private String phone;

    @ApiModelProperty(value="团号")
    private String teamNum;

    @ApiModelProperty(value="出行状态,1：出行中；2：即将出行；3：已出行")
    private Integer status;

    @ApiModelProperty(value="团队总人数")
    private Integer totalCount;

    @ApiModelProperty(value="公告未读数量")
    private Integer annUnreadCount;

    @ApiModelProperty(value="通知未读数量")
    private Integer noticeUnreadCount;

    @ApiModelProperty(value="集合未读数量")
    private Integer gatherUnreadCount;

    @ApiModelProperty(value="背景图片")
    private String backgroundPhotoUrl;
    /**
     * 腾讯群ID
     */
    @ApiModelProperty(value="腾讯群ID")
    private String txGroupId;

    @ApiModelProperty(value="围观未读数量")
    private Integer watchUnreadCount;

    @ApiModelProperty(value="其他说明信息列表")
    private List<ScheduleDescEntity> scheduleDescEntities;

    private HotelScheduleEntity hotelSchedule;

    @ApiModelProperty(value="每日行程购物场所关联关系实体")
    private List<ShoppingScheduleEntity> shoppingSchedules;

    @ApiModelProperty(value="每日行程自费项目关联关系实体")
    private List<OwnExpenseScheduleEntity> ownExpenseSchedules;

    // 当前行程的第几天
    @JsonIgnore
    private long dimDD;
    // 行程总天数
    private long totalDays;
    // 行程中国家的纬度
    @JsonIgnore
    private double lat;
    // 行程中国家的经度
    @JsonIgnore
    private double lng;

    /**
     * 1为旅行社端的行程，2为领队自己创建的行程（公司后台的）
     */
    @ApiModelProperty(value="1为旅行社端的行程，2为领队自己创建的行程（公司后台的）")
    private Integer type;

    /**
     * 创建者ID
     */
    @ApiModelProperty(value="创建者ID")
    private long userId;

    /**
     * 逻辑删除字段：0正常  1删除
     */
    @ApiModelProperty(value="逻辑删除字段：0正常  1删除")
    private Integer del;

    /**
     * 是否围观行程：0正常行程  1围观行程
     */
    @ApiModelProperty(value="是否围观行程：0正常行程  1围观行程")
    private Integer onlookerType;

    //临时字段
    //被围观者ID
    private long onlookerUserId;


    /**
     * 是否为管理员【1：是；0：否】
     */
    @ApiModelProperty(value="是否为管理员【1：是；0：否】")
    private Integer isAdmin;

    /**
     * 身份【1：领队；2：导游；3：游客】
     */
    @ApiModelProperty(value="身份【1：领队；2：导游；3：游客】")
    private Integer role;

    @ApiModelProperty(value="行程监控 国家")
    private String country1;

    @ApiModelProperty(value="行程监控 团队数")
    private Integer tcount;


    //团队头像URL
    @ApiModelProperty(value="团队头像URL")
    private String tPhotoUrl;


    public Integer getTcount() {
        return tcount;
    }

    public void setTcount(Integer tcount) {
        this.tcount = tcount;
    }

    public String getCountry1() {
        return country1;
    }

    public void setCountry1(String country1) {
        this.country1 = country1;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public long getOnlookerUserId() {
        return onlookerUserId;
    }

    public void setOnlookerUserId(long onlookerUserId) {
        this.onlookerUserId = onlookerUserId;
    }

    /**
     *必填字段数据验证
     */
    public ScheduleResponse dataVerify() {
        ScheduleResponse verifyResponse = new ScheduleResponse();
        verifyResponse.setScheduleEntity(this);
        verifyResponse.setRetCode(ErrorConstant.INVALID_PARAMETER);
        if(teamNum == null || StringUtils.isEmpty(teamNum.trim())){
            verifyResponse.setRetMsg("团号不能为空");
            return verifyResponse;
        }
        if (teamNum.length() > 30) {
            verifyResponse.setRetMsg("团号(" + teamNum + ")长度不能超过30个字符");
            return verifyResponse;
        }
        if (name == null || StringUtils.isEmpty(name.trim())) {
            verifyResponse.setRetMsg("行程名称不能为空");
            return verifyResponse;
        }
        if (name.length() > 40) {
            verifyResponse.setRetMsg("行程名称(" + name + ")长度不能超过40个字符");
            return verifyResponse;
        }
        if (startPlace == null || StringUtils.isEmpty(startPlace.trim())) {
            verifyResponse.setRetMsg("出发地不能为空");
            return verifyResponse;
        }
        if (endPlace == null || StringUtils.isEmpty(endPlace.trim())) {
            verifyResponse.setRetMsg("mo地不能为空");
            return verifyResponse;
        }
        if (startPlace.length() > 60) {
            verifyResponse.setRetMsg("出发地(" + startPlace + ")长度不能超过60个字符");
            return verifyResponse;
        }
        if (startDate == null || endDate == null) {
            verifyResponse.setRetMsg("开始日期或结束日期不能为空");
            return verifyResponse;
        }

        /*
        if (StringUtils.isEmpty(leadName) || StringUtils.isEmpty(phone) || leaderId == null) {
            verifyResponse.setRetMsg("请选择领队");
            return verifyResponse;
        }
        */

        teamNum = teamNum.trim();
        name = name.trim();
        startPlace = startPlace.trim();

        verifyResponse.setRetCode(0);
        return verifyResponse;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getWatchUnreadCount() {
        return watchUnreadCount;
    }

    public void setWatchUnreadCount(Integer watchUnreadCount) {
        this.watchUnreadCount = watchUnreadCount;
    }

    public String getBackgroundPhotoUrl() {
        return backgroundPhotoUrl;
    }

    public void setBackgroundPhotoUrl(String backgroundPhotoUrl) {
        this.backgroundPhotoUrl = backgroundPhotoUrl;
    }

    public Integer getAnnUnreadCount() {
        return annUnreadCount;
    }

    public void setAnnUnreadCount(Integer annUnreadCount) {
        this.annUnreadCount = annUnreadCount;
    }

    public Integer getNoticeUnreadCount() {
        return noticeUnreadCount;
    }

    public void setNoticeUnreadCount(Integer noticeUnreadCount) {
        this.noticeUnreadCount = noticeUnreadCount;
    }

    public Integer getGatherUnreadCount() {
        return gatherUnreadCount;
    }

    public void setGatherUnreadCount(Integer gatherUnreadCount) {
        this.gatherUnreadCount = gatherUnreadCount;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public List<String> getProcessPhotosUrls() {
        return processPhotosUrls;
    }

    public void setProcessPhotosUrls(List<String> processPhotosUrls) {
        this.processPhotosUrls = processPhotosUrls;
    }

    public String getProcessPhotosUrl() {
        return processPhotosUrl;
    }

    public void setProcessPhotosUrl(String processPhotosUrl) {
        this.processPhotosUrl = processPhotosUrl;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace == null ? null : startPlace.trim();
    }

    public Date getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(Date collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getCollectionPlace() {
        return collectionPlace;
    }

    public void setCollectionPlace(String collectionPlace) {
        this.collectionPlace = collectionPlace == null ? null : collectionPlace.trim();
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature == null ? null : feature.trim();
    }

    public String getFeaturePhoto() {
        return featurePhoto;
    }

    public void setFeaturePhoto(String featurePhoto) {
        this.featurePhoto = featurePhoto == null ? null : featurePhoto.trim();
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact == null ? null : emergencyContact.trim();
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<ScheduleDetailEntity> getScheduleDetailList() {
        return scheduleDetailList;
    }

    public void setScheduleDetailList(List<ScheduleDetailEntity> scheduleDetailList) {
        this.scheduleDetailList = scheduleDetailList;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
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

    public String getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(String teamNum) {
        this.teamNum = teamNum;
    }

    public String getTxGroupId() {
        return txGroupId;
    }

    public void setTxGroupId(String txGroupId) {
        this.txGroupId = txGroupId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public long getDimDD() {
        return dimDD;
    }

    public void setDimDD(long dimDD) {
        this.dimDD = dimDD;
    }

    public long getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(long totalDays) {
        this.totalDays = totalDays;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Integer getOnlookerType() {
        return onlookerType;
    }

    public void setOnlookerType(Integer onlookerType) {
        this.onlookerType = onlookerType;
    }

    public String gettPhotoUrl() {
        return tPhotoUrl;
    }

    public void settPhotoUrl(String tPhotoUrl) {
        this.tPhotoUrl = tPhotoUrl;
    }

    public List<ScheduleDescEntity> getScheduleDescEntities() {
        return scheduleDescEntities;
    }

    public void setScheduleDescEntities(List<ScheduleDescEntity> scheduleDescEntities) {
        this.scheduleDescEntities = scheduleDescEntities;
    }

    public List<ShoppingScheduleEntity> getShoppingSchedules() {
        return shoppingSchedules;
    }

    public void setShoppingSchedules(List<ShoppingScheduleEntity> shoppingSchedules) {
        this.shoppingSchedules = shoppingSchedules;
    }

    public List<OwnExpenseScheduleEntity> getOwnExpenseSchedules() {
        return ownExpenseSchedules;
    }

    public void setOwnExpenseSchedules(List<OwnExpenseScheduleEntity> ownExpenseSchedules) {
        this.ownExpenseSchedules = ownExpenseSchedules;
    }

    public HotelScheduleEntity getHotelSchedule() {
        return hotelSchedule;
    }

    public void setHotelSchedule(HotelScheduleEntity hotelSchedule) {
        this.hotelSchedule = hotelSchedule;
    }

    @Override
    public String toString() {
        return "ScheduleEntity{" +
                "id=" + id +
                ", teamId=" + teamId +
                ", name='" + name + '\'' +
                ", startPlace='" + startPlace + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", collectionTime=" + collectionTime +
                ", collectionPlace='" + collectionPlace + '\'' +
                ", leaderId=" + leaderId +
                ", feature='" + feature + '\'' +
                ", featurePhoto='" + featurePhoto + '\'' +
                ", emergencyContact='" + emergencyContact + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", version=" + version +
                ", processStatus=" + processStatus +
                ", processPhotosUrl='" + processPhotosUrl + '\'' +
                ", processPhotosUrls=" + processPhotosUrls +
                ", endPlace='" + endPlace + '\'' +
                ", scheduleDetailList=" + scheduleDetailList +
                ", leadName='" + leadName + '\'' +
                ", sex=" + sex +
                ", phone='" + phone + '\'' +
                ", teamNum='" + teamNum + '\'' +
                ", status=" + status +
                ", totalCount=" + totalCount +
                ", annUnreadCount=" + annUnreadCount +
                ", noticeUnreadCount=" + noticeUnreadCount +
                ", gatherUnreadCount=" + gatherUnreadCount +
                ", backgroundPhotoUrl='" + backgroundPhotoUrl + '\'' +
                ", txGroupId='" + txGroupId + '\'' +
                ", watchUnreadCount=" + watchUnreadCount +
                ", dimDD=" + dimDD +
                ", totalDays=" + totalDays +
                ", lat=" + lat +
                ", lng=" + lng +
                ", type=" + type +
                ", userId=" + userId +
                ", del=" + del +
                ", onlookerType=" + onlookerType +
                ", onlookerUserId=" + onlookerUserId +
                ", isAdmin=" + isAdmin +
                ", role=" + role +
                ", country1='" + country1 + '\'' +
                ", tcount=" + tcount +
                '}';
    }
}