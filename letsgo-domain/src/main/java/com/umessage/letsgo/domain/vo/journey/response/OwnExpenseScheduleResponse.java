package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.OwnExpenseScheduleEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/29.
 */
public class OwnExpenseScheduleResponse extends CommonResponse{
    private List<OwnExpenseScheduleEntity> ownExpenseScheduleList;


    public static OwnExpenseScheduleResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends OwnExpenseScheduleResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        OwnExpenseScheduleResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public static OwnExpenseScheduleResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends OwnExpenseScheduleResponse {

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

    public static OwnExpenseScheduleResponse createEmptyParameterResponse(String retMsg){
        class EmptyParameterResponse extends OwnExpenseScheduleResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }
        }

        OwnExpenseScheduleResponse response = new EmptyParameterResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public List<OwnExpenseScheduleEntity> getOwnExpenseScheduleList() {
        return ownExpenseScheduleList;
    }

    public void setOwnExpenseScheduleList(List<OwnExpenseScheduleEntity> ownExpenseScheduleList) {
        this.ownExpenseScheduleList = ownExpenseScheduleList;
    }
}
