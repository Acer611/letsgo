package com.umessage.letsgo.domain.vo.journey.response;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.LeaderScheduleVo;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by pw on 2016/9/1.
 *
 */
public class UserScheduleResponse extends CommonResponse {

    @ApiModelProperty(value = "行程单对象集合")
    private Page<ScheduleEntity> scheduleList;
    @ApiModelProperty(value = "总条数")
    private long totals;
    @ApiModelProperty(value = "总页数")
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

    public Page<ScheduleEntity> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(Page<ScheduleEntity> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public static UserScheduleResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends UserScheduleResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        UserScheduleResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public static UserScheduleResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends UserScheduleResponse {

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

    public static UserScheduleResponse createEmptyParameterResponse(String retMsg){
        class EmptyParameterResponse extends UserScheduleResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }
        }

        UserScheduleResponse response = new EmptyParameterResponse();
        response.setRetMsg(retMsg);
        return response;
    }

}
