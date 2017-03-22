package com.umessage.letsgo.domain.vo.journey.request;

import java.io.Serializable;

/**
 * Created by pw on 2016/9/12.
 */
public class AnswerRequest implements Serializable {
    /**
     * 问卷id
     */
    private Long surveyId;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 团队ID
     */
    private Long teamId;

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

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}
