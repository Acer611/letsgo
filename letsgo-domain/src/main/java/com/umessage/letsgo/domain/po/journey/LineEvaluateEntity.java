package com.umessage.letsgo.domain.po.journey;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by pw on 2016/8/30.
 * 线路评价实体类  j_line_evaluate
 */
public class LineEvaluateEntity {
    /**
     * ID
     */
    @ApiModelProperty(value="ID")
    private Long id;

    /**
     * 开始日期
     */
    @ApiModelProperty(value="开始日期")
    private Date startDate;
    /**
     * 结束日期
     */
    @ApiModelProperty(value="结束日期")
    private Date endDate;

    /**
     * 出团次数
     */
    @ApiModelProperty(value="出团次数")
    private Integer count;

    /**
     * 国家
     */
    @ApiModelProperty(value="国家")
    private String country;

    /**
     * 大洲ID
     */
    @ApiModelProperty(value="大洲ID")
    private String continent;

    /**
     * 评价总人数
     */
    @ApiModelProperty(value="评价总人数")
    private Integer evaluateNum;

    /**
     * 综合评分
     */
    @ApiModelProperty(value="综合评分")
    private Double grade;
    /**
     * 旅行社ID
     */
    @ApiModelProperty(value="旅行社ID")
    private Long travelId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public Integer getEvaluateNum() {
        return evaluateNum;
    }

    public void setEvaluateNum(Integer evaluateNum) {
        this.evaluateNum = evaluateNum;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
