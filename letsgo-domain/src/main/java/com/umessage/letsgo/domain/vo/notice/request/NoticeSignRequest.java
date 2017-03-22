package com.umessage.letsgo.domain.vo.notice.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.*;

/**
 * Created by gaofei on 2017/3/21.
 */
public class NoticeSignRequest extends CommonRequest {

    /**
     * noticeId
     */
    @ApiModelProperty(value="通知ID")
    private Long noticeId;

    /**
     * noticeDetailId
     */
    @ApiModelProperty(value="通知明细ID ")
    private Long noticeDetailId;
    /**
     * 签名地址
     */
    @ApiModelProperty(value="签名地址")
    private List<String> signUrlList;
    /**
     * 腾讯组ID
     */
    @ApiModelProperty(value="腾讯组ID")
    private String teamId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",dataType = "java.lang.Long")
    private Date createTime;


    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getNoticeDetailId() {
        return noticeDetailId;
    }

    public void setNoticeDetailId(Long noticeDetailId) {
        this.noticeDetailId = noticeDetailId;
    }

    public List<String> getSignUrlList() {
        return signUrlList;
    }

    public void setSignUrlList(List<String> signUrlList) {
        this.signUrlList = signUrlList;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
