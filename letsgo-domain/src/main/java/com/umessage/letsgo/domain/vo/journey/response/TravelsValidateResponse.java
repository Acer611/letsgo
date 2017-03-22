package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.TravelNotesVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TravelsValidateResponse extends CommonResponse{

	/**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
