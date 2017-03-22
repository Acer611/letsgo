package com.umessage.letsgo.domain.po.notice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gaofei on 2017/3/20.
 */
public class NoticeSignEntity  implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Integer ID;
    /**
     * noticeDetailId
     */
    @ApiModelProperty(value="通知明细ID")
    private Long noticeDetailId;
    /**
     * 签名地址
     */
    @ApiModelProperty(value="签名地址")
    private String signUrl;
    /**
     * 签名人成员ID
     */
    @ApiModelProperty(value="签名人成员ID")
    private Long memberId;


    /**
     * 签名人
     */
    @ApiModelProperty(value="签名人名字")
    private String signUserName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",dataType = "java.lang.Long")
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

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Long getNoticeDetailId() {
        return noticeDetailId;
    }

    public void setNoticeDetailId(Long noticeDetailId) {
        this.noticeDetailId = noticeDetailId;
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }

    public String getSignUserName() {
        return signUserName;
    }

    public void setSignUserName(String signUserName) {
        this.signUserName = signUserName;
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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
