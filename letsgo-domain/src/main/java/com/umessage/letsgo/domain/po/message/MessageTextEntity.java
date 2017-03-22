package com.umessage.letsgo.domain.po.message;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by gaofei on 2017/1/12.
 */
public class MessageTextEntity implements Serializable{

  /*  id                   bigint not null comment '主键自增',
    m_id                 bigint comment '反馈表的ID',
    sender_id            bigint comment '回复人的ID 旅行社ID 或者memberID',
    message              varchar(512) comment '回复的内容',
    post_time            varchar(512) comment '回复时间',
    status               tinyint comment '0 成员1 旅行社',
    photo_url1           varchar(256),
    photo_url2           varchar(256),
    photo_url3           varchar(256),
    photo_url4           varchar(256),
    photo_url5           varchar(256),
    photo_url6           varchar(256),
    photo_url7           varchar(256) binary,
    photo_url8           varchar(256),
    photo_url9           varchar(256),*/

    //主键自增
    private Long id;
    //反馈表的ID
    private Long mId;
    //回复人的ID 旅行社ID 或者memberID
    private Long senderId;
    //回复人名字
    private String senderName;
    //回复内容
    private String message;
    //回复时间
    private Date postTime;
    //角色类型 0 成员 1 旅行社
    private Integer status;
    //照片地址
    private String photoUrl1;
    //照片地址
    private String photoUrl2;
    //照片地址
    private String photoUrl3;
    //照片地址
    private String photoUrl4;
    //照片地址
    private String photoUrl5;
    //照片地址
    private String photoUrl6;
    //照片地址
    private String photoUrl7;
    //照片地址
    private String photoUrl8;
    //照片地址
    private String photoUrl9;


    private List<String> photoList;
    //头像地址
    private String headUrl;

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPhotoUrl1() {
        return photoUrl1;
    }

    public void setPhotoUrl1(String photoUrl1) {
        this.photoUrl1 = photoUrl1;
    }

    public String getPhotoUrl2() {
        return photoUrl2;
    }

    public void setPhotoUrl2(String photoUrl2) {
        this.photoUrl2 = photoUrl2;
    }

    public String getPhotoUrl3() {
        return photoUrl3;
    }

    public void setPhotoUrl3(String photoUrl3) {
        this.photoUrl3 = photoUrl3;
    }

    public String getPhotoUrl4() {
        return photoUrl4;
    }

    public void setPhotoUrl4(String photoUrl4) {
        this.photoUrl4 = photoUrl4;
    }

    public String getPhotoUrl5() {
        return photoUrl5;
    }

    public void setPhotoUrl5(String photoUrl5) {
        this.photoUrl5 = photoUrl5;
    }

    public String getPhotoUrl6() {
        return photoUrl6;
    }

    public void setPhotoUrl6(String photoUrl6) {
        this.photoUrl6 = photoUrl6;
    }

    public String getPhotoUrl7() {
        return photoUrl7;
    }

    public void setPhotoUrl7(String photoUrl7) {
        this.photoUrl7 = photoUrl7;
    }

    public String getPhotoUrl8() {
        return photoUrl8;
    }

    public void setPhotoUrl8(String photoUrl8) {
        this.photoUrl8 = photoUrl8;
    }

    public String getPhotoUrl9() {
        return photoUrl9;
    }

    public void setPhotoUrl9(String photoUrl9) {
        this.photoUrl9 = photoUrl9;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
