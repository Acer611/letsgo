package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.ShoppingAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/29.
 */
public class ShoppingAgencyResponse extends CommonResponse{
    private List<ShoppingAgencyEntity> shoppingAgencyList;

    private ShoppingAgencyEntity shoppingAgencyEntity;

    public static ShoppingAgencyResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends ShoppingAgencyResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        ShoppingAgencyResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public static ShoppingAgencyResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends ShoppingAgencyResponse {

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

    public static ShoppingAgencyResponse createEmptyParameterResponse(String retMsg){
        class EmptyParameterResponse extends ShoppingAgencyResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }
        }

        ShoppingAgencyResponse response = new EmptyParameterResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public List<ShoppingAgencyEntity> getShoppingAgencyList() {
        return shoppingAgencyList;
    }

    public void setShoppingAgencyList(List<ShoppingAgencyEntity> shoppingAgencyList) {
        this.shoppingAgencyList = shoppingAgencyList;
    }

    public ShoppingAgencyEntity getShoppingAgencyEntity() {
        return shoppingAgencyEntity;
    }

    public void setShoppingAgencyEntity(ShoppingAgencyEntity shoppingAgencyEntity) {
        this.shoppingAgencyEntity = shoppingAgencyEntity;
    }
}
