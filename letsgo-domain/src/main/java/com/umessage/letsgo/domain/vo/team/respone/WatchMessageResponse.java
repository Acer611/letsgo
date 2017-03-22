package com.umessage.letsgo.domain.vo.team.respone;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.domain.po.team.OnlookersDetailsEntity;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/2.
 */
public class WatchMessageResponse implements Serializable{
    @ApiModelProperty(value = "围观信息id")
    private  Long id;
    @ApiModelProperty(value = "是否系统消息0否1是")
    private  Integer isSystemInfo;
    @ApiModelProperty(value = "围观信息发布时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date onlookersDate;
    @ApiModelProperty(value = "围观信息内容")
    private String onlookersMes;
    @ApiModelProperty(value = "围观信息图片")
    private List<String> onlookersImgList;
    @ApiModelProperty(value = "回复列表")
    private List<ReplyInfo> replyList;
    @ApiModelProperty(value = "点赞列表")
    private List<LikeInfo> likeList;
    @ApiModelProperty(value = "图片宽")
    private String wide;
    @ApiModelProperty(value = "图片高")
    private String height;
    @ApiModelProperty(value = "发布围观用户腾讯id")
    private  String userId;
    @ApiModelProperty(value = "发布围观用户名称")
    private String userName;
    @ApiModelProperty(value = "发布围观用户头像")
    private String userHeadUrl;
    @JsonIgnore
    private String onlookersImg;
    @ApiModelProperty(value="是否点赞:1为点赞  0未点赞")
    private Integer  isLike;

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    public String getOnlookersImg() {
        return onlookersImg;
    }

    public void setOnlookersImg(String onlookersImg) {
        this.onlookersImg = onlookersImg;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsSystemInfo() {
        return isSystemInfo;
    }

    public void setIsSystemInfo(Integer isSystemInfo) {
        this.isSystemInfo = isSystemInfo;
    }

    public Date getOnlookersDate() {
        return onlookersDate;
    }

    public void setOnlookersDate(Date onlookersDate) {
        this.onlookersDate = onlookersDate;
    }

    public String getOnlookersMes() {
        return onlookersMes;
    }

    public void setOnlookersMes(String onlookersMes) {
        this.onlookersMes = onlookersMes;
    }

    public List<String> getOnlookersImgList() {
        return onlookersImgList;
    }

    public void setOnlookersImgList(List<String> onlookersImgList) {
        this.onlookersImgList = onlookersImgList;
    }

    public List<ReplyInfo> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplyInfo> replyList) {
        this.replyList = replyList;
    }

    public List<LikeInfo> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<LikeInfo> likeList) {
        this.likeList = likeList;
    }

    public String getWide() {
        return wide;
    }

    public void setWide(String wide) {
        this.wide = wide;
    }
}
