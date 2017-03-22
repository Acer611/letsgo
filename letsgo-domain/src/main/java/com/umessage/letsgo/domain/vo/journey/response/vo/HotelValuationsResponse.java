package com.umessage.letsgo.domain.vo.journey.response.vo;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.HotelEvaluationsEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleInfo;
import com.umessage.letsgo.domain.vo.journey.response.TeamScheduleVo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wendy on 2016/6/15.
 */
public class HotelValuationsResponse implements Serializable {
    private Page<HotelEvaluationsEntity> page;

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getEvaluateCount() {
        return evaluateCount;
    }

    public void setEvaluateCount(Integer evaluateCount) {
        this.evaluateCount = evaluateCount;
    }

    private String totalScore;
    private Integer evaluateCount;
    public Page<HotelEvaluationsEntity> getPage() {
        return page;
    }

    public void setPage(Page<HotelEvaluationsEntity> page) {
        this.page = page;
    }

    public long getTotals() {
        return totals;
    }

    public void setTotals(long totals) {
        this.totals = totals;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    private long totals;
    private int pages;
}
