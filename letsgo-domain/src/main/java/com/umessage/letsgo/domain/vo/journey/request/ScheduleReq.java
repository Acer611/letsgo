package com.umessage.letsgo.domain.vo.journey.request;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wendy on 2016/6/16.
 */
public class ScheduleReq implements Serializable {
    /**
     * 客户端回传的ID，上一次获取到的团队行程ID
     */
    private Long teamId;
    /**
     * 团队ID列表
     */
    private List<Long> teamIds;

    /**
     * 查询开始月份
     */
    private String startTime;
    /**
     * 查询结束月份
     */
    private String endTime;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public List<Long> getTeamIds() {
        return teamIds;
    }

    public void setTeamIds(List<Long> teamIds) {
        this.teamIds = teamIds;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
