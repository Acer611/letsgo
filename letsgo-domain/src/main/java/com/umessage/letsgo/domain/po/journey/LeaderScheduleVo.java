package com.umessage.letsgo.domain.po.journey;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by pw on 2016/8/22.
 */
public class LeaderScheduleVo implements Comparable{
    /**
     * 行程Id
     */
    @ApiModelProperty(value="行程Id")
    private Long scheduleId;
    /**
     * 行程名称
     */
    @ApiModelProperty(value="行程名称")
    private String name;
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
     * 已完成天数
     */
    @ApiModelProperty(value="已完成天数")
    private Integer finishDays;
    /**
     * 行程总天数
     */
    @ApiModelProperty(value="行程总天数")
    private Integer totalDays;
    /**
     * 腾讯云GroupId
     */
    @ApiModelProperty(value="腾讯云GroupId")
    private String txGroupId;
    /**
     * 距离团队出行剩余天数
     */
    @ApiModelProperty(value="距离团队出行剩余天数")
    private Integer days;
    /**
     * 行程创建的状态：2已发布-3未发布-4待确认
     */
    @ApiModelProperty(value="行程创建的状态：2已发布-3未发布-4待确认")
    private Integer processStatus;
    /**
     * 行程特色图片
     */
    @ApiModelProperty(value="行程背景图片")
    private String featurePhoto;
    /**
     * 通知未读数
     */
    @ApiModelProperty(value="通知未读数")
    private Integer noticeUnread;
    /**
     * 集合未读数
     */
    @ApiModelProperty(value="集合未读数")
    private Integer gatherUnread;
    /**
     * 公告未读数
     */
    @ApiModelProperty(value="公告未读数")
    private Integer announcementUnread;
    /**
     * 围观未读数量
     */
    @ApiModelProperty(value="围观未读数量")
    private Integer watchUnreadCount;
    /**
     * 团的状态：1：出行中；2：即将出行；3：已出行
     */
    @ApiModelProperty(value="团的状态：1：出行中；2：即将出行；3：已出行")
    private Integer status;
    /**
     * 被围观者ID
     */
    @ApiModelProperty(value="被围观者ID")
    private long onlookerUserId;
    /**
     * 是否围观行程：0正常行程  1围观行程
     */
    @ApiModelProperty(value="是否围观行程：0正常行程  1围观行程")
    private Integer onlookerType;

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
    /**
     * 已经结束了多少天
     */
    @ApiModelProperty(value="已经结束了多少天")
    private Integer endDays;


    /**
     * 二维码URL
     */
    @ApiModelProperty(value="二维码URL")
    private String qrURL;

    /**
     * 二维码类型：t：快速入团
     */
    @ApiModelProperty(value="二维码类型：t：快速入团")
    private String qrType;

    /**
     * 二维码参数：腾讯组ID
     */
    @ApiModelProperty(value="二维码参数：腾讯组ID")
    private String qrParam;
    /**
     * 微信公众号二维码带团ID参数eventKey
     */
    @ApiModelProperty(value="微信公众号二维码带团ID参数")
    private String qrCode;

    //团队头像URL
    @ApiModelProperty(value="团队头像URL")
    private String tPhotoUrl;

    public Integer getEndDays() {
        return endDays;
    }

    public void setEndDays(Integer endDays) {
        this.endDays = endDays;
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

    public Integer getOnlookerType() {
        return onlookerType;
    }

    public void setOnlookerType(Integer onlookerType) {
        this.onlookerType = onlookerType;
    }

    public long getOnlookerUserId() {
        return onlookerUserId;
    }

    public void setOnlookerUserId(long onlookerUserId) {
        this.onlookerUserId = onlookerUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWatchUnreadCount() {
        return watchUnreadCount;
    }

    public void setWatchUnreadCount(Integer watchUnreadCount) {
        this.watchUnreadCount = watchUnreadCount;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getFinishDays() {
        return finishDays;
    }

    public void setFinishDays(Integer finishDays) {
        this.finishDays = finishDays;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public String getTxGroupId() {
        return txGroupId;
    }

    public void setTxGroupId(String txGroupId) {
        this.txGroupId = txGroupId;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public String getFeaturePhoto() {
        return featurePhoto;
    }

    public void setFeaturePhoto(String featurePhoto) {
        this.featurePhoto = featurePhoto;
    }

    public Integer getNoticeUnread() {
        return noticeUnread;
    }

    public void setNoticeUnread(Integer noticeUnread) {
        this.noticeUnread = noticeUnread;
    }

    public Integer getGatherUnread() {
        return gatherUnread;
    }

    public void setGatherUnread(Integer gatherUnread) {
        this.gatherUnread = gatherUnread;
    }

    public Integer getAnnouncementUnread() {
        return announcementUnread;
    }

    public void setAnnouncementUnread(Integer announcementUnread) {
        this.announcementUnread = announcementUnread;
    }

    public String getQrURL() {
        return qrURL;
    }

    public void setQrURL(String qrURL) {
        this.qrURL = qrURL;
    }

    public String getQrType() {
        return qrType;
    }

    public void setQrType(String qrType) {
        this.qrType = qrType;
    }

    public String getQrParam() {
        return qrParam;
    }

    public void setQrParam(String qrParam) {
        this.qrParam = qrParam;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String gettPhotoUrl() {
        return tPhotoUrl;
    }

    public void settPhotoUrl(String tPhotoUrl) {
        this.tPhotoUrl = tPhotoUrl;
    }

    @Override
    public int compareTo(Object o)
    {
        LeaderScheduleVo sdto = (LeaderScheduleVo)o;
        Date otherAge = sdto.getEndDate();
        return this.endDate.compareTo(otherAge);
    }
//    List<LeaderScheduleVo> studentList = new LeaderScheduleVo();
//    Collections.sort(studentList);  //按照endDate升序
//    Collections.reverse(studentList);  //按照endDate降序
}
