package com.umessage.letsgo.domain.vo.journey.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ConsumptionRecordRequest implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1083092507640078436L;

		@ApiModelProperty(value = "消费类型")
	    private Long id;
	
		@ApiModelProperty(value = "消费类型")
	    private Long userId;
	
		@ApiModelProperty(value = "消费类型")
	    private String type;

	    @ApiModelProperty(value = "金额")
	    private String money;

	    @ApiModelProperty(value = "币种")
	    private Integer currency;
	    
	    @ApiModelProperty(value = "币种")
	    private Integer paymentType;
	    
	    @ApiModelProperty(value = "支付日期")
	    private String date;
	    
	    @ApiModelProperty(value = "备注")
	    private String remarks;
	    
	    @ApiModelProperty(value = "图片地址")
	    private String imgUrl;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getMoney() {
			return money;
		}

		public void setMoney(String money) {
			this.money = money;
		}

		public Integer getCurrency() {
			return currency;
		}

		public void setCurrency(Integer currency) {
			this.currency = currency;
		}

		public Integer getPaymentType() {
			return paymentType;
		}

		public void setPaymentType(Integer paymentType) {
			this.paymentType = paymentType;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		public String getImgUrl() {
			return imgUrl;
		}

		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}
}
