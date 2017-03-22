package com.umessage.letsgo.domain.vo.team.requset;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/18.
 */
public class TeamAblumRequest implements Serializable{
    private String  teamId;

    private Date startDate;

    private Date endDate;

    //包含时间-lizhen
    private List<Date> inDates;

    private Integer startIndex;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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

    public List<Date> getInDates() {
        return inDates;
    }

    public void setInDates(List<Date> inDates) {
        this.inDates = inDates;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    private Integer pageNum;
    private Integer pageSize=10;
}
