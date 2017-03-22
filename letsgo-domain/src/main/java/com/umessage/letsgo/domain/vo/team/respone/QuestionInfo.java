package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.domain.vo.journey.response.Optioninfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/8.
 *
 */
public class QuestionInfo implements Serializable{
    public Integer getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Integer isRequired) {
        this.isRequired = isRequired;
    }

    private Integer isRequired;
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Optioninfo> getList() {
        return list;
    }

    public void setList(List<Optioninfo> list) {
        this.list = list;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    private Integer questionType;
    private Long questionId;
    private Long surveyId;
    private String title;
    private List<Optioninfo> list;
   private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
