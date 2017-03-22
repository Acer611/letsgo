package com.umessage.letsgo.domain.vo.journey.response.vo;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wendy on 2016/8/31.
 */
public class AnswerVo implements Serializable {
    @ApiModelProperty(value="ID")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Long userId;

    // 团号
    @ApiModelProperty(value="团号")
    private String teamNum;

    // 行程名称
    @ApiModelProperty(value="行程名称")
    private String name;

    /**
     * 开始日期
     */
    @ApiModelProperty(value="开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 结束日期
     */
    @ApiModelProperty(value="结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    //游客名称
    @ApiModelProperty(value="游客名称")
    private String realName;

    //游客性别
    @ApiModelProperty(value="游客性别")
    private Integer sex;

    //游客手机号
    @ApiModelProperty(value="游客手机号")
    private String phone;

    /**
     * 问卷id
     */
    @ApiModelProperty(value="问卷id")
    private Long surveyId;

    /**
     * 问题id
     */
    @ApiModelProperty(value="问题id")
    private Long questionId;

    /**
     * 选项id
     * 不是单选题时为空
     */
    @ApiModelProperty(value="选项id")
    private Long questionOptionId;

    /**
     * 问答题回答内容
     * 不是问答题时为空
     */
    @ApiModelProperty(value="问答题回答内容")
    private String content;

    /**
     * 多选题回答内容，ID分号隔开
     * 不是多选题时为空
     */
    @ApiModelProperty(value="多选题回答内容")
    private String moreOption;

    /**
     * --模板答案 加权平均分
     */
    @ApiModelProperty(value="模板答案")
    private String optionAnswer;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 题型，1为单选，2为多选，3为问答，4为模板问题
     */
    @ApiModelProperty(value="题型")
    private Integer questionType;

    /**
     * 问题标题
     */
    @ApiModelProperty(value="问题标题")
    private String title;

    /**
     * 类型1为景点 2为当地交通 3为住宿 4为用餐 5为服务人员 6整体
     */
    @ApiModelProperty(value="类型")
    private Integer type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(String teamNum) {
        this.teamNum = teamNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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

    public String getOptionAnswer() {
        return optionAnswer;
    }

    public void setOptionAnswer(String optionAnswer) {
        this.optionAnswer = optionAnswer;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
