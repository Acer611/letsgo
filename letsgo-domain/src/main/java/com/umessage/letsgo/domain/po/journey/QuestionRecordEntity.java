package com.umessage.letsgo.domain.po.journey;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zengguoqing on 2016/9/1.
 */
public class QuestionRecordEntity implements Serializable{
    @ApiModelProperty(value="ID")
    private Long id;
    // 团号
    @ApiModelProperty(value="团号")
    private String teamNum;
    // 行程名称
    @ApiModelProperty(value="行程名称")
    private String scheduleName;
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
    //创建时间
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    //模板问题1
    @ApiModelProperty(value="模板问题1")
    private String mouldQuestion1;
    //模板问题2
    @ApiModelProperty(value="模板问题2")
    private String mouldQuestion2;
    //模板问题3
    @ApiModelProperty(value="模板问题3")
    private String mouldQuestion3;
    //模板问题4
    @ApiModelProperty(value="模板问题4")
    private String mouldQuestion4;
    //模板问题5
    @ApiModelProperty(value="模板问题5")
    private String mouldQuestion5;
    //模板问题6
    @ApiModelProperty(value="模板问题6")
    private String mouldQuestion6;
    //自定义问题1
    @ApiModelProperty(value="自定义问题1")
    private String customQuestion1;
    //自定义问题2
    @ApiModelProperty(value="自定义问题2")
    private String customQuestion2;
    //自定义问题3
    @ApiModelProperty(value="自定义问题3")
    private String customQuestion3;
    //自定义问题4
    @ApiModelProperty(value="自定义问题4")
    private String customQuestion4;
    //自定义问题5
    @ApiModelProperty(value="自定义问题5")
    private String customQuestion5;
    //自定义问题6
    @ApiModelProperty(value="自定义问题6")
    private String customQuestion6;
    //自定义问题7
    @ApiModelProperty(value="自定义问题7")
    private String customQuestion7;
    //自定义问题8
    @ApiModelProperty(value="自定义问题8")
    private String customQuestion8;
    //自定义问题9
    @ApiModelProperty(value="自定义问题9")
    private String customQuestion9;
    //自定义问题10
    @ApiModelProperty(value="自定义问题10")
    private String customQuestion10;
    //自定义问题11
    @ApiModelProperty(value="自定义问题11")
    private String customQuestion11;
    //自定义问题12
    @ApiModelProperty(value="模板问题12")
    private String customQuestion12;
    //自定义问题13
    @ApiModelProperty(value="自定义问题13")
    private String customQuestion13;
    //自定义问题14
    @ApiModelProperty(value="自定义问题14")
    private String customQuestion14;
    //自定义问题15
    @ApiModelProperty(value="自定义问题15")
    private String customQuestion15;
    //自定义问题16
    @ApiModelProperty(value="自定义问题16")
    private String customQuestion16;
    //自定义问题17
    @ApiModelProperty(value="自定义问题17")
    private String customQuestion17;
    //自定义问题18
    @ApiModelProperty(value="自定义问题18")
    private String customQuestion18;
    //自定义问题19
    @ApiModelProperty(value="自定义问题19")
    private String customQuestion19;
    //自定义问题20
    @ApiModelProperty(value="自定义问题20")
    private String customQuestion20;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(String teamNum) {
        this.teamNum = teamNum;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMouldQuestion1() {
        return mouldQuestion1;
    }

    public void setMouldQuestion1(String mouldQuestion1) {
        this.mouldQuestion1 = mouldQuestion1;
    }

    public String getMouldQuestion2() {
        return mouldQuestion2;
    }

    public void setMouldQuestion2(String mouldQuestion2) {
        this.mouldQuestion2 = mouldQuestion2;
    }

    public String getMouldQuestion3() {
        return mouldQuestion3;
    }

    public void setMouldQuestion3(String mouldQuestion3) {
        this.mouldQuestion3 = mouldQuestion3;
    }

    public String getMouldQuestion4() {
        return mouldQuestion4;
    }

    public void setMouldQuestion4(String mouldQuestion4) {
        this.mouldQuestion4 = mouldQuestion4;
    }

    public String getMouldQuestion5() {
        return mouldQuestion5;
    }

    public void setMouldQuestion5(String mouldQuestion5) {
        this.mouldQuestion5 = mouldQuestion5;
    }

    public String getMouldQuestion6() {
        return mouldQuestion6;
    }

    public void setMouldQuestion6(String mouldQuestion6) {
        this.mouldQuestion6 = mouldQuestion6;
    }

    public String getCustomQuestion1() {
        return customQuestion1;
    }

    public void setCustomQuestion1(String customQuestion1) {
        this.customQuestion1 = customQuestion1;
    }

    public String getCustomQuestion2() {
        return customQuestion2;
    }

    public void setCustomQuestion2(String customQuestion2) {
        this.customQuestion2 = customQuestion2;
    }

    public String getCustomQuestion3() {
        return customQuestion3;
    }

    public void setCustomQuestion3(String customQuestion3) {
        this.customQuestion3 = customQuestion3;
    }

    public String getCustomQuestion4() {
        return customQuestion4;
    }

    public void setCustomQuestion4(String customQuestion4) {
        this.customQuestion4 = customQuestion4;
    }

    public String getCustomQuestion5() {
        return customQuestion5;
    }

    public void setCustomQuestion5(String customQuestion5) {
        this.customQuestion5 = customQuestion5;
    }

    public String getCustomQuestion6() {
        return customQuestion6;
    }

    public void setCustomQuestion6(String customQuestion6) {
        this.customQuestion6 = customQuestion6;
    }

    public String getCustomQuestion7() {
        return customQuestion7;
    }

    public void setCustomQuestion7(String customQuestion7) {
        this.customQuestion7 = customQuestion7;
    }

    public String getCustomQuestion8() {
        return customQuestion8;
    }

    public void setCustomQuestion8(String customQuestion8) {
        this.customQuestion8 = customQuestion8;
    }

    public String getCustomQuestion9() {
        return customQuestion9;
    }

    public void setCustomQuestion9(String customQuestion9) {
        this.customQuestion9 = customQuestion9;
    }

    public String getCustomQuestion10() {
        return customQuestion10;
    }

    public void setCustomQuestion10(String customQuestion10) {
        this.customQuestion10 = customQuestion10;
    }

    public String getCustomQuestion11() {
        return customQuestion11;
    }

    public void setCustomQuestion11(String customQuestion11) {
        this.customQuestion11 = customQuestion11;
    }

    public String getCustomQuestion12() {
        return customQuestion12;
    }

    public void setCustomQuestion12(String customQuestion12) {
        this.customQuestion12 = customQuestion12;
    }

    public String getCustomQuestion13() {
        return customQuestion13;
    }

    public void setCustomQuestion13(String customQuestion13) {
        this.customQuestion13 = customQuestion13;
    }

    public String getCustomQuestion14() {
        return customQuestion14;
    }

    public void setCustomQuestion14(String customQuestion14) {
        this.customQuestion14 = customQuestion14;
    }

    public String getCustomQuestion15() {
        return customQuestion15;
    }

    public void setCustomQuestion15(String customQuestion15) {
        this.customQuestion15 = customQuestion15;
    }

    public String getCustomQuestion16() {
        return customQuestion16;
    }

    public void setCustomQuestion16(String customQuestion16) {
        this.customQuestion16 = customQuestion16;
    }

    public String getCustomQuestion17() {
        return customQuestion17;
    }

    public void setCustomQuestion17(String customQuestion17) {
        this.customQuestion17 = customQuestion17;
    }

    public String getCustomQuestion18() {
        return customQuestion18;
    }

    public void setCustomQuestion18(String customQuestion18) {
        this.customQuestion18 = customQuestion18;
    }

    public String getCustomQuestion19() {
        return customQuestion19;
    }

    public void setCustomQuestion19(String customQuestion19) {
        this.customQuestion19 = customQuestion19;
    }

    public String getCustomQuestion20() {
        return customQuestion20;
    }

    public void setCustomQuestion20(String customQuestion20) {
        this.customQuestion20 = customQuestion20;
    }
}
