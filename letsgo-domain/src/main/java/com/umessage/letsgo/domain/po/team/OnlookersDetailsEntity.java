package com.umessage.letsgo.domain.po.team;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zengguoqing on 2016/9/1.
 */
public class OnlookersDetailsEntity implements Serializable{
    private Long id;
    private String imgUrl;
    /**
     * 1为点赞，2为回复
     */
    @ApiModelProperty(value="1为点赞，2为回复")
    private Integer type;

    private Long onlookersId;
    //围观用户（围观别人的）-- otherId--replyId
    private Long replyId;
    /**
     *回复用户  昵称
     */
    private String replyName;
    //发布围观用户(他是被围观的)-- ownerId--byReplyId
    private Long byReplyId;
    /**
     *被回复用户  昵称
     */
    private String byReplyName;
    private String content;
    /**
     * 是否点赞
     * 1为点赞  0未点赞
     *
     */
    @ApiModelProperty(value="是否点赞:1为点赞  0未点赞")
    private Integer  isLike;
    private  Integer isLook;
    private Long       fatherId;
    private Date createTime;
    private  Long    version;
    private Long scheduleId;

    /**
     *用户ID
     */
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getByReplyName() {
        return byReplyName;
    }

    public void setByReplyName(String byReplyName) {
        this.byReplyName = byReplyName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOnlookersId() {
        return onlookersId;
    }

    public void setOnlookersId(Long onlookersId) {
        this.onlookersId = onlookersId;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public Long getByReplyId() {
        return byReplyId;
    }

    public void setByReplyId(Long byReplyId) {
        this.byReplyId = byReplyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public Integer getIsLook() {
        return isLook;
    }

    public void setIsLook(Integer isLook) {
        this.isLook = isLook;
    }

    public Long getFatherId() {
        return fatherId;
    }

    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
