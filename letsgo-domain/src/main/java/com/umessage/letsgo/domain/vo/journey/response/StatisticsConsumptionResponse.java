package com.umessage.letsgo.domain.vo.journey.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.StaticsConsumptionInfo;
import com.umessage.letsgo.domain.po.journey.StaticsConsumptionInfoList;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;

public class StatisticsConsumptionResponse extends CommonResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5681678632271986261L;
	@ApiModelProperty(value = "统计列表")
    private List<StaticsConsumptionInfo> consumptionList;
    
    public static StatisticsConsumptionResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends StatisticsConsumptionResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        StatisticsConsumptionResponse response = new StatisticsConsumptionResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public static StatisticsConsumptionResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends StatisticsConsumptionResponse {

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

    public static StatisticsConsumptionResponse createEmptyParameterResponse(String retMsg){
        class EmptyParameterResponse extends StatisticsConsumptionResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }
        }

        StatisticsConsumptionResponse response = new EmptyParameterResponse();
        response.setRetMsg(retMsg);
        return response;
    }

	public List<StaticsConsumptionInfo> getConsumptionList() {
		return consumptionList;
	}

	public void setConsumptionList(List<StaticsConsumptionInfo> consumptionList) {
		this.consumptionList = consumptionList;
	}

}
