package com.umessage.letsgo.domain.vo.journey.request;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhp on 2016/9/12.
 */
public class QuestionRequest implements Serializable {
    private Long surveyId;
    private Integer questionType;
    private String title;
    private List<String> list;

    //类型1为景点 2为当地交通 3为住宿 4为用餐 5为服务人员
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }


}
