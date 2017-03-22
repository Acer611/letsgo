package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.HotelScheduleEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/29.
 */
public class HotelScheduleResponse extends CommonResponse{
    private List<HotelScheduleEntity> hotelScheduleEntityList;


    public static HotelScheduleResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends HotelScheduleResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        HotelScheduleResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public static HotelScheduleResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends HotelScheduleResponse {

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

    public static HotelScheduleResponse createEmptyParameterResponse(String retMsg){
        class EmptyParameterResponse extends HotelScheduleResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }
        }

        HotelScheduleResponse response = new EmptyParameterResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public List<HotelScheduleEntity> getHotelScheduleEntityList() {
        return hotelScheduleEntityList;
    }

    public void setHotelScheduleEntityList(List<HotelScheduleEntity> hotelScheduleEntityList) {
        this.hotelScheduleEntityList = hotelScheduleEntityList;
    }
}
