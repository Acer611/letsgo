package com.umessage.letsgo.domain.vo.team.requset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.core.annotation.dataitem.ItemName;
import com.umessage.letsgo.core.annotation.dataitem.ItemValue;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wendy on 2016/9/8.
 */
public class TeamRequest extends CommonRequest {
    private Long firstUserId;

    private Long secondUserId;


    public Long getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(Long firstUserId) {
        this.firstUserId = firstUserId;
    }

    public Long getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(Long secondUserId) {
        this.secondUserId = secondUserId;
    }

    //行程监控添加

    private String  country1;

    private  String countryId1;

    public String getCountryId1() {
        return countryId1;
    }

    public void setCountryId1(String countryId1) {
        this.countryId1 = countryId1;
    }

    private Date  todayDay;

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
     * 开始日期
     */
    private Date startDate;
    /**
     * 结束日期
     */
    private Date endDate;

    @ApiModelProperty(value = "当前第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "每页条数")
    private int pageSize=10;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getCountry1() {
        return country1;
    }

    public void setCountry1(String country1) {
        this.country1 = country1;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getTodayDay() {
        return todayDay;
    }

    public void setTodayDay(Date todayDay) {
        this.todayDay = todayDay;
    }

    @Override
    public String toString() {
        return "TeamRequest{" +
                "firstUserId=" + firstUserId +
                ", secondUserId=" + secondUserId +
                ", country1='" + country1 + '\'' +
                ", countryId1='" + countryId1 + '\'' +
                ", todayDay=" + todayDay +
                ", id=" + id +
                ", travelId=" + travelId +
                ", teamNum='" + teamNum + '\'' +
                ", txGroupid='" + txGroupid + '\'' +
                ", name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", status=" + status +
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
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
