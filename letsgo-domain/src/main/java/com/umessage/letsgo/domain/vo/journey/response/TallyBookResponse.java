package com.umessage.letsgo.domain.vo.journey.response;

import io.swagger.annotations.ApiModelProperty;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.ConsumptionInfoList;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

public class TallyBookResponse extends CommonResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6917420501403018817L;
	@ApiModelProperty(value = "消费列表")
    private Page<ConsumptionInfoList> consumptionList;
    
    public static TallyBookResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends TallyBookResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        TallyBookResponse response = new TallyBookResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public static TallyBookResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends TallyBookResponse {

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

    public static TallyBookResponse createEmptyParameterResponse(String retMsg){
        class EmptyParameterResponse extends TallyBookResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }
        }

        TallyBookResponse response = new EmptyParameterResponse();
        response.setRetMsg(retMsg);
        return response;
    }

	public Page<ConsumptionInfoList> getConsumptionList() {
		return consumptionList;
	}

	public void setConsumptionList(Page<ConsumptionInfoList> consumptionList) {
		this.consumptionList = consumptionList;
	}
}
