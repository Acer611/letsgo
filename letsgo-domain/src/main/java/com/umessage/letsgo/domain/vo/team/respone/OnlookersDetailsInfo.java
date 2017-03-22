package com.umessage.letsgo.domain.vo.team.respone;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zengguoqing on 2016/9/6.
 */
public class OnlookersDetailsInfo implements Serializable {
    //回复人昵称
    private String name;
    //回复时间
    private Date date;
    //回复信息
    private String message;
    //回复人头像链接
    private String facePhotoUrl;
    //回复围观信息的第一张图片URL
    private String imgUrl;
    //回复人ID
    private Long replyId;
    //1点赞 ；2回复
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFacePhotoUrl() {
        return facePhotoUrl;
    }

    public void setFacePhotoUrl(String facePhotoUrl) {
        this.facePhotoUrl = facePhotoUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


}
