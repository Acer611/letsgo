package com.umessage.letsgo.domain.vo.journey.request;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by pw on 2016/9/12.
 */
public class SurveyRequest implements Serializable {
    /**
     * 问卷id
     */
    private Long surveyId;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 腾讯群组id
     */
    private String txGroupId;

    /**
     * 签名地址
     */
    private String signUrl;
    /**
     * 提交时间
     */
    private Date submitTime;

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }

    public String getTxGroupId() {
        return txGroupId;
    }

    public void setTxGroupId(String txGroupId) {
        this.txGroupId = txGroupId;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
