package com.umessage.letsgo.domain.vo.system.respone;

import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class YahooRateResponse extends CommonResponse {

	/**
	 * 汇率列表
	 */
	@ApiModelProperty(value = "汇率列表，如果没有请求参数，返回默认（人民币，美元，日元，韩元）")
	private List<RateResult> rates = new ArrayList<>();

	public List<RateResult> getRates() {
		return rates;
	}

	public void setRates(List<RateResult> rates) {
		this.rates = rates;
	}
}
