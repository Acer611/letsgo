package com.umessage.letsgo.domain.vo.journey.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.LeaderScheduleVo;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.*;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoYidong on 2016/5/31.
 *
 */
public class ScheduleListResponse extends CommonResponse {

    @ApiModelProperty(value = "公司后台行程单列表")
    @JsonIgnore
    private Page<ScheduleEntity> scheduleList;
    @ApiModelProperty(value="标识当前用户是否有行程:1：代表有行程；0：代表没有行程")
    private Integer hasSchedule;
    @ApiModelProperty(value = "用户行程列表")
    private Page<LeaderScheduleVo> leaderScheduleVoList;
    @JsonIgnore
    private long totals;
    @JsonIgnore
    private int pages;

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

    public Page<LeaderScheduleVo> getLeaderScheduleVoList() {
        return leaderScheduleVoList;
    }

    public void setLeaderScheduleVoList(Page<LeaderScheduleVo> leaderScheduleVoList) {
        this.leaderScheduleVoList = leaderScheduleVoList;
    }

    public Integer getHasSchedule() {
        return hasSchedule;
    }

    public void setHasSchedule(Integer hasSchedule) {
        this.hasSchedule = hasSchedule;
    }

    public Page<ScheduleEntity> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(Page<ScheduleEntity> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public static ScheduleListResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends ScheduleListResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        ScheduleListResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public static ScheduleListResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends ScheduleListResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.USER_NOT_LOGIN;
            }

            @Override
            public String getRetMsg() {
                return "用户未登录或登录信息过期";
            }
        }

        return new UserNotLoginResponse();
    }

    public static ScheduleListResponse createEmptyParameterResponse(String retMsg){
        class EmptyParameterResponse extends ScheduleListResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }
        }

        ScheduleListResponse response = new EmptyParameterResponse();
        response.setRetMsg(retMsg);
        return response;
    }


}
