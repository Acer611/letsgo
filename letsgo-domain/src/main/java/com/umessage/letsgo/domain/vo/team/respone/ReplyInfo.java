package com.umessage.letsgo.domain.vo.team.respone;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zengguoqing on 2016/9/8.
 */
public class ReplyInfo implements Serializable{
    @ApiModelProperty(value = "回复信息内容")
    private String message;
    @ApiModelProperty(value = "回复信息时间")
    private Date createTime;
    @ApiModelProperty(value = "回复腾讯云id")
    private String replyId;
    @ApiModelProperty(value = "回复用户名称")
    private String replyName;
    @ApiModelProperty(value = "被回复腾讯云id")
    private String byReplyId;
    @ApiModelProperty(value = "被回复用户名称")
    private String byReplyName;
    /**
     * 1为点赞，2为回复
     */
    @ApiModelProperty(value="1为点赞，2为回复，3评论")
    private Integer type;

    @ApiModelProperty(value="是否点赞:1为点赞  0未点赞")
    @JsonIgnore
    private Integer  isLike;
    //回复id
    @JsonIgnore
    private Long  reId;
    //被回复id
    @JsonIgnore
    private Long  byReId;
    @JsonIgnore
    private Long  id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getByReplyId() {
        return byReplyId;
    }

    public void setByReplyId(String byReplyId) {
        this.byReplyId = byReplyId;
    }

    public Long getReId() {
        return reId;
    }

    public void setReId(Long reId) {
        this.reId = reId;
    }

    public Long getByReId() {
        return byReId;
    }

    public void setByReId(Long byReId) {
        this.byReId = byReId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }
}
