package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by wendy on 2016/6/15.
 */
public class ScheduleInfoResponse extends CommonResponse {
    @ApiModelProperty(value = "行程单实体")
    private ScheduleInfo scheduleInfo = new ScheduleInfo();

    public ScheduleInfo getScheduleInfo() {
        return scheduleInfo;
    }

    public void setScheduleInfo(ScheduleInfo scheduleInfo) {
        this.scheduleInfo = scheduleInfo;
    }

    public static ScheduleInfoResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends ScheduleInfoResponse {

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
}
