package com.umessage.letsgo.domain.po.journey;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/1.
 */
public class SurveyEntity implements Serializable {

    private Long travelId;
    private Long id;
    private  String title;
    private Integer surveyStatus;
    private String description;
    private Date createTime;
    private Date updateTime;
    private Long version;
    private Long teamId;
    private List<SurveyDetailEntity> surveyDetailEntityList;

    private List<QuestionEntity> questionEntityList;

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public Integer getSurveyStatus() {
        return surveyStatus;
    }

    public void setSurveyStatus(Integer surveyStatus) {
        this.surveyStatus = surveyStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public List<SurveyDetailEntity> getSurveyDetailEntityList() {
        return surveyDetailEntityList;
    }

    public void setSurveyDetailEntityList(List<SurveyDetailEntity> surveyDetailEntityList) {
        this.surveyDetailEntityList = surveyDetailEntityList;
    }

    public List<QuestionEntity> getQuestionEntityList() {
        return questionEntityList;
    }

    public void setQuestionEntityList(List<QuestionEntity> questionEntityList) {
        this.questionEntityList = questionEntityList;
    }
}
