package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.HotelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/29.
 */
public class HotelAgencyResponse extends CommonResponse{
    private List<HotelAgencyEntity> hotelAgencyEntityList;

    private HotelAgencyEntity hotelAgencyEntity;

    public static HotelAgencyResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends HotelAgencyResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        HotelAgencyResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public static HotelAgencyResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends HotelAgencyResponse {

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

    public static HotelAgencyResponse createEmptyParameterResponse(String retMsg){
        class EmptyParameterResponse extends HotelAgencyResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }
        }

        HotelAgencyResponse response = new EmptyParameterResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public List<HotelAgencyEntity> getHotelAgencyEntityList() {
        return hotelAgencyEntityList;
    }

    public void setHotelAgencyEntityList(List<HotelAgencyEntity> hotelAgencyEntityList) {
        this.hotelAgencyEntityList = hotelAgencyEntityList;
    }

    public HotelAgencyEntity getHotelAgencyEntity() {
        return hotelAgencyEntity;
    }

    public void setHotelAgencyEntity(HotelAgencyEntity hotelAgencyEntity) {
        this.hotelAgencyEntity = hotelAgencyEntity;
    }
}
