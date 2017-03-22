/*
 * TeamEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-12 Created by Administrator
 */
package com.umessage.letsgo.domain.po.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.core.annotation.dataitem.ItemName;
import com.umessage.letsgo.core.annotation.dataitem.ItemValue;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Catalog(code = "TeamEntity")
public class TeamEntity  implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 旅行社ID
     */
    @ApiModelProperty(value="旅行社ID")
    private Long travelId;
    /**
     * 团号
     */
    @ApiModelProperty(value="团号")
    private String teamNum;
    /**
     * 腾讯云GroupId
     */
    @ApiModelProperty(value="腾讯云GroupId")
    private String txGroupid;

    /**
     * 团队名称
     */
    @ApiModelProperty(value="团队名称")
    private String name;
    /**
     * 团队简介
     */
    @ApiModelProperty(value="团队简介")
    private String intro;
    /**
     * 头像地址
     */
    @ApiModelProperty(value="头像地址")
    private String photoUrl;
    /**
     * 出行状态【1：出行中；2：即将出行；3：已出行】
     */
    @ApiModelProperty(value="出行状态【1：出行中；2：即将出行；3：已出行】")
    @ItemValue(code = "status")
    private Integer status;
    /**
     * 出行状态【1：出行中；2：即将出行；3：已出行】
     */
    @ApiModelProperty(value="出行状态字段的翻译，意思是返回出行状态对应的字符串")
    @ItemName(code = "status")
    private String statusDescn;
    /**
     * 总人数，包括领队、导游及游客的人数
     */
    @ApiModelProperty(value="总人数，包括领队、导游及游客的人数")
    private Integer totalCount;
    /**
     * 领队姓名
     */
    @JsonIgnore
    private String headerName;
    /**
     * 领队电话
     */
    @JsonIgnore
    private String headerPhone;
    /**
     * 国内紧急联系人
     */
    @JsonIgnore
    private String domesticContact;
    /**
     * 国内紧急联系人手机
     */
    @JsonIgnore
    private String domesticPhone;
    /**
     * 国外紧急联系人
     */
    @JsonIgnore
    private String foreignContact;
    /**
     * 国外紧急联系人手机
     */
    @JsonIgnore
    private String foreignPhone;
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
     * 旅行社名称
     */
    @ApiModelProperty(value="旅行社名称")
    private String travelName;
    /**
     * 旅行社简介
     */
    @ApiModelProperty(value="旅行社简介")
    private String desc;
    /**
     * 团队中的全部成员
     */
    @ApiModelProperty(value="团队中的全部成员")
    private List<MemberEntity> members = new ArrayList<>();
    /**
     * 游客人数
     */
    @ApiModelProperty(value="游客人数")
    private Integer touristCount;

    /**
     * 开始日期
     */
    private Date startDate;
    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 问卷状态
     */
    @ApiModelProperty(value="问卷状态，1:无问卷;2：未发放问卷；3：已发放问卷；4：已全部填写")
    private Integer isIssueSurvey;

    private String schName;

    private Integer dimDD;

    private Long jourId;

    /**
     * 微信公众号二维码带团ID参数URLeventKey
     */
    @ApiModelProperty(value="微信公众号二维码带团ID参数URL")
    private String qrCode;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Long getJourId() {
        return jourId;
    }

    public void setJourId(Long jourId) {
        this.jourId = jourId;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public Integer getDimDD() {
        return dimDD;
    }

    public void setDimDD(Integer dimDD) {
        this.dimDD = dimDD;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public String getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(String teamNum) {
        this.teamNum = teamNum;
    }

    public String getTxGroupid() {
        return txGroupid;
    }

    public void setTxGroupid(String txGroupid) {
        this.txGroupid = txGroupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDescn() {
        return statusDescn;
    }

    public void setStatusDescn(String statusDescn) {
        this.statusDescn = statusDescn;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderPhone() {
        return headerPhone;
    }

    public void setHeaderPhone(String headerPhone) {
        this.headerPhone = headerPhone;
    }

    public String getDomesticContact() {
        return domesticContact;
    }

    public void setDomesticContact(String domesticContact) {
        this.domesticContact = domesticContact;
    }

    public String getDomesticPhone() {
        return domesticPhone;
    }

    public void setDomesticPhone(String domesticPhone) {
        this.domesticPhone = domesticPhone;
    }

    public String getForeignContact() {
        return foreignContact;
    }

    public void setForeignContact(String foreignContact) {
        this.foreignContact = foreignContact;
    }

    public String getForeignPhone() {
        return foreignPhone;
    }

    public void setForeignPhone(String foreignPhone) {
        this.foreignPhone = foreignPhone;
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

    public String getTravelName() {
        return travelName;
    }

    public void setTravelName(String travelName) {
        this.travelName = travelName;
    }

    public List<MemberEntity> getMembers() {
        return members;
    }

    public void setMembers(List<MemberEntity> members) {
        this.members = members;
    }

    public Integer getTouristCount() {
        return touristCount;
    }

    public void setTouristCount(Integer touristCount) {
        this.touristCount = touristCount;
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

    @Override
    public String toString() {
        return "TeamEntity{" +
                "id=" + id +
                ", travelId=" + travelId +
                ", teamNum='" + teamNum + '\'' +
                ", txGroupid='" + txGroupid + '\'' +
                ", name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", status=" + status +
                ", statusDescn='" + statusDescn + '\'' +
                ", totalCount=" + totalCount +
                ", headerName='" + headerName + '\'' +
                ", headerPhone='" + headerPhone + '\'' +
                ", domesticContact='" + domesticContact + '\'' +
                ", domesticPhone='" + domesticPhone + '\'' +
                ", foreignContact='" + foreignContact + '\'' +
                ", foreignPhone='" + foreignPhone + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", version=" + version +
                ", travelName='" + travelName + '\'' +
                ", members=" + members +
                ", touristCount=" + touristCount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isIssueSurvey=" + isIssueSurvey +
                '}';
    }

    public Integer getIsIssueSurvey() {
        return isIssueSurvey;
    }

    public void setIsIssueSurvey(Integer isIssueSurvey) {
        this.isIssueSurvey = isIssueSurvey;
    }

}