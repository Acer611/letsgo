package com.umessage.letsgo.domain.po.journey;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/1.
 */
public class QuestionEntity implements Serializable{
    private  Long id;
    private Long surveyId;
    private Integer questionType;
    private String title;
    private String description;
    private Integer isRequired;
    private Date createTime;
    private Date updateTime;
    private Long version;
    //类型1为景点 2为当地交通 3为住宿 4为用餐 5为服务人员 6为整体
    private Integer type;

    //临时字段
    /**
     * 问题选项 问答题无选项
     */
    private List<QuestionOptionEntity> optionList;

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
     * 多选题回答内容，ID分号隔开
     * 不是多选题时为空
     */
    private List<String>  moreOptionList;

    /**
     * 答案id
     *
     */
    private Long answerId;

    /**
     * 选项内容，多选以分号隔开
     */
    private String optionAnswer;

    public List<String> getMoreOptionList() {
        List<String>  moreOptionList = new ArrayList<String>();
        if(StringUtils.isEmpty(this.moreOption)){
            return null;
        }
        String [] strs=  this.moreOption.split(";");
        if(strs!=null && strs.length>0){
            for (int i = 0; i <strs.length ; i++) {
                if(!StringUtils.isEmpty(strs[i])){
                    moreOptionList.add(strs[i]);
                }
            }
        }
        return moreOptionList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setMoreOptionList(List<String> moreOptionList) {
        this.moreOptionList = moreOptionList;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
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

    public String getDescription() {
        return description;
    }

    public Integer getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Integer isRequired) {
        this.isRequired = isRequired;
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

    public List<QuestionOptionEntity> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<QuestionOptionEntity> optionList) {
        this.optionList = optionList;
    }

    public String getOptionAnswer() {
        return optionAnswer;
    }

    public void setOptionAnswer(String optionAnswer) {
        this.optionAnswer = optionAnswer;
    }
}
