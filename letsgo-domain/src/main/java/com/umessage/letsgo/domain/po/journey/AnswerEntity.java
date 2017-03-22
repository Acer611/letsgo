package com.umessage.letsgo.domain.po.journey;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zengguoqing on 2016/9/1.
 */
public class AnswerEntity implements Serializable{

    private Long id;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    @JsonIgnore
    private Long version;
    /**
     * 问卷id
     */
    private Long surveyId;
    /**
     * 用户id
     */
    @JsonIgnore
    private Long userId;

    /**
     * 问题id
     */
    private Long questionId;

    /**
     * 选项id
     * 不是单选题时为空
     */
    private Long questionOptionId;

    /**
     * 问答题回答内容
     * 不是问答题时为空
     */
    private String content;

    /**
     * 多选题回答内容，ID分号隔开
     * 不是多选题时为空
     */
    private String moreOption;

    /**
     * --模板答案 加权平均分
     */
    private String optionAnswer;

    /**
     * 类型1为景点 2为当地交通 3为住宿 4为用餐 5为服务人员 6整体
     */
    private Integer type;

    public String getOptionAnswer() {
        return optionAnswer;
    }

    public void setOptionAnswer(String optionAnswer) {
        this.optionAnswer = optionAnswer;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(Long questionOptionId) {
        this.questionOptionId = questionOptionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMoreOption() {
        return moreOption;
    }

    public void setMoreOption(String moreOption) {
        this.moreOption = moreOption;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
