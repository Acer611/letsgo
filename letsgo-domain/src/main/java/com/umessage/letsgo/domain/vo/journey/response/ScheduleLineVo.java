package com.umessage.letsgo.domain.vo.journey.response;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by pw on 2016/8/30.
 * 线路评价数据vo
 */
public class ScheduleLineVo {
    private Long id;
    /**
     * 国家
     */
    private String countryName;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 旅行社ID
     */
    private Long travelId;
    /**
     * 游客人数=评论人数
     */
    private Integer touristCount;
    /**
     * 总评分
     * 综合评分=总评分/评论人数
     */
    private Double grade;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public Integer getTouristCount() {
        return touristCount;
    }

    public void setTouristCount(Integer touristCount) {
        this.touristCount = touristCount;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
