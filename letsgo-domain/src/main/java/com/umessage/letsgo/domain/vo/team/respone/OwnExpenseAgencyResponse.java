package com.umessage.letsgo.domain.vo.team.respone;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.OwnExpenseAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

import java.util.List;

/**
 * Created by ZhaoYidong on 2016/6/29.
 */
public class OwnExpenseAgencyResponse extends CommonResponse{
    private List<OwnExpenseAgencyEntity> ownExpenseAgencyList;

    private OwnExpenseAgencyEntity ownExpenseAgencyEntity;

    public static OwnExpenseAgencyResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends OwnExpenseAgencyResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        OwnExpenseAgencyResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public static OwnExpenseAgencyResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends OwnExpenseAgencyResponse {

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

    public static OwnExpenseAgencyResponse createEmptyParameterResponse(String retMsg){
        class EmptyParameterResponse extends OwnExpenseAgencyResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }
        }

        OwnExpenseAgencyResponse response = new EmptyParameterResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public List<OwnExpenseAgencyEntity> getOwnExpenseAgencyList() {
        return ownExpenseAgencyList;
    }

    public void setOwnExpenseAgencyList(List<OwnExpenseAgencyEntity> ownExpenseAgencyList) {
        this.ownExpenseAgencyList = ownExpenseAgencyList;
    }

    public OwnExpenseAgencyEntity getOwnExpenseAgencyEntity() {
        return ownExpenseAgencyEntity;
    }

    public void setOwnExpenseAgencyEntity(OwnExpenseAgencyEntity ownExpenseAgencyEntity) {
        this.ownExpenseAgencyEntity = ownExpenseAgencyEntity;
    }
}
