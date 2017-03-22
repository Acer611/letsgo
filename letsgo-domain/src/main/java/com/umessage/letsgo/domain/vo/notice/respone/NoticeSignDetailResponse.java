package com.umessage.letsgo.domain.vo.notice.respone;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.notice.NoticeSignEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.*;

/**
 * Created by gaofei on 2017/3/20.
 */
public class NoticeSignDetailResponse extends CommonResponse {

    /**
     * 通知ID
     */
    @ApiModelProperty(value="通知ID")
    private Long noticeId;

    /**
     * 通知详情ID
     */
    @ApiModelProperty(value="ID")
    private Long id;

    /*
     *腾讯群组ID
     */
    @ApiModelProperty(value="腾讯群组ID")
    private String teamId;
    /**
     * 发送人用户ID
     */
    @ApiModelProperty(value="发送人用户ID")
    private Long userId;
    /**
     * 发送人
     */
    @ApiModelProperty(value="发送人")
    private Long senderId;
    /**
     * 发送人姓名
     */
    @ApiModelProperty(value="发送人姓名")
    private String senderName;
    /**
     * 内容
     */
    @ApiModelProperty(value="内容")
    private String content;
    /**
     * 签名人数
     */
    @ApiModelProperty(value="签名人数")
    private Integer signCount;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",dataType = "java.lang.Long")
    private Date createTime;

    /**
     * 签名实体信息
     */
    @ApiModelProperty(value="签名实体信息")
    private List<NoticeSignEntity> noticeSignEntityList;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSignCount() {
        return signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<NoticeSignEntity> getNoticeSignEntityList() {
        return noticeSignEntityList;
    }

    public void setNoticeSignEntityList(List<NoticeSignEntity> noticeSignEntityList) {
        this.noticeSignEntityList = noticeSignEntityList;
    }

    public static NoticeSignDetailResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends NoticeSignDetailResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.USER_NOT_LOGIN;
            }

            @Override
            public String getRetMsg() {
                return "用户未登录或登录信息过期";
            }
        }

        return new UserNotLoginResponse();
    }
}
