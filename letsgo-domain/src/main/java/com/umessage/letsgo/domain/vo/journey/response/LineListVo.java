package com.umessage.letsgo.domain.vo.journey.response;

import java.util.Date;

/**
 * Created by pw on 2016/8/31.
 * 线路评价数据vo
 */
public class LineListVo {
    private Long id;
    /**
     * 线路名称
     */
    private String lineName;
    /**
     * 时间区间
     */
    private String date;
    /**
     * 出团次数
     */
    private Integer travelCount;
    /**
     * 评价总人数
     */
    private Integer evaluateCount;
    /**
     * 总评分
     * 综合评分=总评分/评论人数
     */
    private Double totalScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTravelCount() {
        return travelCount;
    }

    public void setTravelCount(Integer travelCount) {
        this.travelCount = travelCount;
    }

    public Integer getEvaluateCount() {
        return evaluateCount;
    }

    public void setEvaluateCount(Integer evaluateCount) {
        this.evaluateCount = evaluateCount;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }
}
