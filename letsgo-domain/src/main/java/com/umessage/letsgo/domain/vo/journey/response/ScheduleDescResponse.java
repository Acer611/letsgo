package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.ScheduleDescEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/29.
 */
public class ScheduleDescResponse extends CommonResponse{
    private List<ScheduleDescEntity> scheduleDescs;


    public static ScheduleDescResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends ScheduleDescResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        ScheduleDescResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public static ScheduleDescResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends ScheduleDescResponse {

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

    public static ScheduleDescResponse createEmptyParameterResponse(String retMsg){
        class EmptyParameterResponse extends ScheduleDescResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }
        }

        ScheduleDescResponse response = new EmptyParameterResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public static ScheduleDescResponse createInvalidParameterResponse(String retMsg){
        class EmptyParameterResponse extends ScheduleDescResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.INVALID_PARAMETER;
            }
        }

        ScheduleDescResponse response = new EmptyParameterResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public List<ScheduleDescEntity> getScheduleDescs() {
        return scheduleDescs;
    }

    public void setScheduleDescs(List<ScheduleDescEntity> scheduleDescs) {
        this.scheduleDescs = scheduleDescs;
    }
}
