package com.umessage.letsgo.domain.vo.team.requset;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/1.
 */
public class LeaderRequest implements Serializable {

    @ApiModelProperty(value = "领队id")
    private Long id;

    @ApiModelProperty(value="领队姓名")
    private String name;

    @ApiModelProperty(value="类别[1:领队；2:导游；3:导游兼领队]")
    private Integer leaderType;

    @ApiModelProperty(value="状态「1:专职；2兼职；3:离职」")
    private Integer leaderStatus;

    @ApiModelProperty(value="性别【1：男性；2：女性】")
    private Integer sex;

    @ApiModelProperty(value="带团年数区间：1-3")
    private String interval;

    @ApiModelProperty(value="带团开始日期")
    private Date startDate;

    @ApiModelProperty(value="带团结束日期")
    private Date endDate;

    @ApiModelProperty(value="是否是ajax操作:1是0否")
    private Integer isAjax = 0;

    @ApiModelProperty(value="当前页数")
    private int pageNum = 1;
    @ApiModelProperty(value="每页条数")
    private int pageSize = 10;

    @ApiModelProperty(value="电话")
    private String phone;

    @ApiModelProperty(value="旅行社id")
    private Long travelId;

    @ApiModelProperty(value="当前工作状态：0所有 ；1空闲 ；2忙碌")
    private Integer state;
    //当前系统日期yyyy-MM-dd
    private String nowDate;
    @ApiModelProperty(value="开始时间")
    private String startTime;
    @ApiModelProperty(value="结束时间")
    private String endTime;

    @ApiModelProperty(value="团队手机号")
    private List<String> phones;

    @ApiModelProperty(value="团队id")
    private Long tId;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNowDate() {
        return nowDate;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }

    public Long getId() {
        return id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLeaderType() {
        return leaderType;
    }

    public void setLeaderType(Integer leaderType) {
        this.leaderType = leaderType;
    }

    public Integer getLeaderStatus() {
        return leaderStatus;
    }

    public void setLeaderStatus(Integer leaderStatus) {
        this.leaderStatus = leaderStatus;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getIsAjax() {
        return isAjax;
    }

    public void setIsAjax(Integer isAjax) {
        this.isAjax = isAjax;
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

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "LeaderRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", leaderType=" + leaderType +
                ", leaderStatus=" + leaderStatus +
                ", sex=" + sex +
                ", interval='" + interval + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isAjax=" + isAjax +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", phone='" + phone + '\'' +
                ", travelId=" + travelId +
                '}';
    }
}
