package com.umessage.letsgo.domain.vo.journey.response.vo;

import java.util.Date;

/**
 * Created by wendy on 2016/8/31.
 */
public class ScoreVo {
    private Long ID;
    // 团号
    private String teamNum;
    // 行程名称
    private String name;
    //行程日期
    private Date scheduleDate;
    //游客名称
    private String realName;
    //游客性别
    private Integer sex;
    //游客手机号
    private String phone;
    //5为非常满意 4为满意 3为一般 2为不满意 1为非常不满意
    private Integer satisfiedStatus;
    //0为无 1为景点 2为当地交通 3为住宿 4为用餐 5为服务人员 6为其他
    private Integer evaluateOption;
    //evaluateOption对应项的ID,2-1为交通对应的不准时；2-2为当地交通对应的车内环境；2-3为当地交通对应的舒适度；以此类推；
    private String evaluateId;
    //评分内容
    private String scoreOption;
    //评分1-5分
    private Integer score;
    //点评项的子选项信息,多选用;隔开，用餐对应的菜量为4-a;卫生4-b;口味为4-c;环境为4-d;上菜数度为4-e.
    private String evaluateSubInfo;
    //evaluateOption为其他时,该项为输入框填写的内容
    private String evaluateProposal;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
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

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
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

    public Integer getSatisfiedStatus() {
        return satisfiedStatus;
    }

    public void setSatisfiedStatus(Integer satisfiedStatus) {
        this.satisfiedStatus = satisfiedStatus;
    }

    public Integer getEvaluateOption() {
        return evaluateOption;
    }

    public void setEvaluateOption(Integer evaluateOption) {
        this.evaluateOption = evaluateOption;
    }

    public String getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(String evaluateId) {
        this.evaluateId = evaluateId;
    }

    public String getScoreOption() {
        return scoreOption;
    }

    public void setScoreOption(String scoreOption) {
        this.scoreOption = scoreOption;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getEvaluateSubInfo() {
        return evaluateSubInfo;
    }

    public void setEvaluateSubInfo(String evaluateSubInfo) {
        this.evaluateSubInfo = evaluateSubInfo;
    }

    public String getEvaluateProposal() {
        return evaluateProposal;
    }

    public void setEvaluateProposal(String evaluateProposal) {
        this.evaluateProposal = evaluateProposal;
    }
}
