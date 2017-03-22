package com.umessage.letsgo.domain.vo.journey.request;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by pw on 2016/8/31.
 */
public class LineEvaluateRequest implements Serializable {
    /**
     * 旅行社ID
     */
    @ApiModelProperty(value="旅行社ID")
    private Long travelId;
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
     * 开始日期
     */
    @ApiModelProperty(value="开始日期")
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date startDate;
    /**
     * 结束日期
     */
    @ApiModelProperty(value="结束日期")
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date endDate;

    @ApiModelProperty(value="当前页数")
    private int pageNum = 1;
    @ApiModelProperty(value="每页条数")
    private int pageSize = 10;

    public Long getTravelId() {
        return travelId;
    }
    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
