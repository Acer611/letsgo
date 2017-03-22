package com.umessage.letsgo.domain.vo.journey.request;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhaoYidong on 2016/5/31.
 *
 */
public class ScheduleRequest implements Serializable {

    //注：目的地搜索属性暂时不加。
    @ApiModelProperty(value = "行程Id")
    private Long id;

    @ApiModelProperty(value = "行程开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @ApiModelProperty(value = "行程结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @ApiModelProperty(value = "行程状态")
    private Integer status;

    @ApiModelProperty(value = "团号")
    private String teamNum;

    @ApiModelProperty(value = "领队名称")
    private String leadName;

    @ApiModelProperty(value = "领队电话")
    private String phone;

    @ApiModelProperty(value = "团队人数")
    private Integer totalcount;

    @ApiModelProperty(value = "领队id")
    private Long leaderId;

    @ApiModelProperty(value = "行程名称")
    private String name;



    @ApiModelProperty(value = "创建行程时选择原有行程修改还是用新的行程模板")
    private String travelTemplate;

    @ApiModelProperty(value = "点击更多操作")
    private String clickMore;

    @ApiModelProperty(value = "当前第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "每页条数")
    private int pageSize=10;

    /**
     * 客户端回传的ID，上一次获取到的团队行程ID
     */
    private Long teamId;
    /**
     * 团队ID列表
     */
    private List<Long> teamIds;
    /**
     * 行程ID列表
     */
    private List<Long> scheduleIds;

    /**
     * 旅行社ID
     */
    private Long travelId;

    /**
     * 查看旅行社自己行程（0：不是，1：是）
     */
    private Integer oneSelf;

    /**
     * 出发地
     */
    @ApiModelProperty(value="出发地")
    private String startPlace;

    /**
     * 发布状态：1全部-2已发布-3未发布-4待确认
     */
    @ApiModelProperty(value="发布状态：1全部-2已发布-3未发布-4待确认")
    private Integer processStatus;

    @ApiModelProperty(value="行程监控 国家")
    private String country1;

    @ApiModelProperty(value="行程监控 国家id")
    private String countryId1;

    private Date yesterday;

    private Date activityDate;

    public String getCountryId1() {
        return countryId1;
    }

    public void setCountryId1(String countryId1) {
        this.countryId1 = countryId1;
    }

    public String getCountry1() {
        return country1;
    }

    public void setCountry1(String country1) {
        this.country1 = country1;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(Integer totalcount) {
        this.totalcount = totalcount;
    }
    /**
     * 用户ID
     */
    private Long userId;


    public ScheduleRequest() {
    }

    public ScheduleRequest(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(String teamNum) {
        this.teamNum = teamNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTravelTemplate() {
        return travelTemplate;
    }

    public void setTravelTemplate(String travelTemplate) {
        this.travelTemplate = travelTemplate;
    }

    public String getClickMore() {
        return clickMore;
    }

    public void setClickMore(String clickMore) {
        this.clickMore = clickMore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public List<Long> getTeamIds() {
        return teamIds;
    }

    public void setTeamIds(List<Long> teamIds) {
        this.teamIds = teamIds;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public List<Long> getScheduleIds() {
        return scheduleIds;
    }

    public void setScheduleIds(List<Long> scheduleIds) {
        this.scheduleIds = scheduleIds;
    }

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public Date getYesterday() {
        return yesterday;
    }

    public void setYesterday(Date yesterday) {
        this.yesterday = yesterday;
    }

    public Integer getOneSelf() {
        return oneSelf;
    }

    public void setOneSelf(Integer oneSelf) {
        this.oneSelf = oneSelf;
    }

    @Override
    public String toString() {
        return "ScheduleRequest{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", teamNum='" + teamNum + '\'' +
                ", leadName='" + leadName + '\'' +
                ", phone='" + phone + '\'' +
                ", totalcount=" + totalcount +
                ", leaderId=" + leaderId +
                ", name='" + name + '\'' +
                ", travelTemplate='" + travelTemplate + '\'' +
                ", clickMore='" + clickMore + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", teamId=" + teamId +
                ", teamIds=" + teamIds +
                ", scheduleIds=" + scheduleIds +
                ", travelId=" + travelId +
                ", startPlace='" + startPlace + '\'' +
                ", processStatus=" + processStatus +
                ", country1='" + country1 + '\'' +
                ", countryId1='" + countryId1 + '\'' +
                ", userId=" + userId +
                '}';
    }
}
