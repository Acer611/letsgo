package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.ShoppingScheduleEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/29.
 */
public class ShoppingScheduleResponse extends CommonResponse{
    private List<ShoppingScheduleEntity> shoppingScheduleList;


    public static ShoppingScheduleResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends ShoppingScheduleResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        ShoppingScheduleResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public static ShoppingScheduleResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends ShoppingScheduleResponse {

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

    public static ShoppingScheduleResponse createEmptyParameterResponse(String retMsg){
        class EmptyParameterResponse extends ShoppingScheduleResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }
        }

        ShoppingScheduleResponse response = new EmptyParameterResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public List<ShoppingScheduleEntity> getShoppingScheduleList() {
        return shoppingScheduleList;
    }

    public void setShoppingScheduleList(List<ShoppingScheduleEntity> shoppingScheduleList) {
        this.shoppingScheduleList = shoppingScheduleList;
    }
}
