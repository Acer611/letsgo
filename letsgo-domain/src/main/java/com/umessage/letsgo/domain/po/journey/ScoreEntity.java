/*
 * ScoreEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-08-25 Created by Administrator
 */
package com.umessage.letsgo.domain.po.journey;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class ScoreEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4458383165357579051L;
	/**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 每日行程点评id
     */
    @ApiModelProperty(value="每日行程点评id")
    private Long scheduleDetailCommentId;
    /**
     * 1为景点 2为当地交通 3为住宿 4为用餐 5为服务人员
     */
    @ApiModelProperty(value="0为无 1为景点 2为当地交通 3为住宿 4为用餐 5为服务人员 6为其他")
    private Integer evaluateOption;

    /**
     * evaluateOption为其他时,该项为输入框填写的内容
     */
    @ApiModelProperty(value="evaluateOption为其他时,该项为输入框填写的内容")
    private String evaluateProposal;
    /**
     * evaluateOption对应项的ID,2-1为交通对应的不准时；2-2为当地交通对应的车内环境；2-3为当地交通对应的舒适度；以此类推；
     */
    @ApiModelProperty(value="evaluateOption对应项的ID")
    private String evaluateId;
    /**
     * 点评项的子选项信息,多选用;隔开，用餐对应的菜量为4-a;卫生4-b;口味为4-c;环境为4-d;上菜数度为4-e.
     */
    @ApiModelProperty(value="点评项的子选项信息,如果多选为分号隔开")
    private String evaluateSubInfo;
    /**
     * 对什么做的评分
     */
    @ApiModelProperty(value="对什么做的评分")
    private String scoreOption;
    /**
     * 评分1-5分
     */
    @ApiModelProperty(value="评分1-5分")
    private Integer score;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    /**
     * 版本
     */
    @ApiModelProperty(value="版本")
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScheduleDetailCommentId() {
        return scheduleDetailCommentId;
    }

    public void setScheduleDetailCommentId(Long scheduleDetailCommentId) {
        this.scheduleDetailCommentId = scheduleDetailCommentId;
    }

    public Integer getEvaluateOption() {
        return evaluateOption;
    }

    public void setEvaluateOption(Integer evaluateOption) {
        this.evaluateOption = evaluateOption;
    }

    public String getScoreOption() {
        return scoreOption;
    }

    public void setScoreOption(String scoreOption) {
        this.scoreOption = scoreOption == null ? null : scoreOption.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

	public String getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(String evaluateId) {
		this.evaluateId = evaluateId;
	}

    public String getEvaluateProposal() {
        return evaluateProposal;
    }

    public void setEvaluateProposal(String evaluateProposal) {
        this.evaluateProposal = evaluateProposal;
    }

    public String getEvaluateSubInfo() {
        return evaluateSubInfo;
    }

    public void setEvaluateSubInfo(String evaluateSubInfo) {
        this.evaluateSubInfo = evaluateSubInfo;
    }
}