package com.umessage.letsgo.domain.vo.security.respone;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

public class CheckcheckCodeResponse extends CommonResponse {

	public static CheckcheckCodeResponse createCheckCheckCodeResponse(){
		class CheckCheckCodeResponse extends CheckcheckCodeResponse {

			@Override
			public Integer getRetCode() {
				return ErrorConstant.INVALID_PARAMETER;
			}

			@Override
			public String getRetMsg() {
				return "校验码不正确";
			}
		}

		return new CheckcheckCodeResponse();
	}
	
}
