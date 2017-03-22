package com.umessage.letsgo.domain.vo.team.requset;

import com.umessage.letsgo.domain.vo.common.request.CommonRequest;

import java.util.List;

/**
 * Created by wendy on 2016/9/7.
 */
public class WaitingRequest extends CommonRequest {
    // 设置的日期，需要用数组
    private List<String> dates;
    // 设置排期的状态【1：表示忙碌；2：表示空闲】
    private Integer status;

    //用户Id
    private Long userId;
    //用户Id
    private String time;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
