package com.umessage.letsgo.domain.vo.journey.request;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pw on 2016/9/7.
 */
public class SurveyDetailRequest implements Serializable {
    /**
     * 问卷id
     */
    private Long surveyId;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 问题id
     */
    private Long questionId;
    /**
     * 确认状态1:是，0:否
     */
    private Integer confirmStatus;
    /**
     * 腾讯组ID
     */
    private String txgroupId;

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getTxgroupId() {
        return txgroupId;
    }

    public void setTxgroupId(String txgroupId) {
        this.txgroupId = txgroupId;
    }

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
