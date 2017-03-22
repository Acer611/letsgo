package com.umessage.letsgo.domain.po.journey;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

public class TallyBookEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2531629206898860618L;
	
	 /**
     * ID
     */
	@JsonIgnore
    @ApiModelProperty(value="ID")
    private Long id;

	@JsonIgnore
	@ApiModelProperty(value = "用户id")
    private Long userId;
	
	@ApiModelProperty(value = "消费类型0为餐饮1为交通2为购物3为娱乐4为门票5为住宿6为医疗7为美容8为保险9为其他")
    private String type;

    @ApiModelProperty(value = "金额")
    private Double money;
    
    @JsonIgnore
    @ApiModelProperty(value = "等价人民币金额")
    private Double equivalentMoney;

    @ApiModelProperty(value = "币种参数值通过查询币种接口获取")
    private String currency;

	@ApiModelProperty(value = "币种名称参数值通过查询币种接口获取")
	private String currencyName;
    
    @ApiModelProperty(value = "支付方式0为现金支付1为银行卡支付2为支付宝支付3为微信支付")
    private Integer paymentType;
    
    @ApiModelProperty(value = "支付日期")
    private String date;
    
    @ApiModelProperty(value = "腾讯群组id")
    private String txGroupId;
    
    @ApiModelProperty(value = "备注")
    private String remarks;
    
    @ApiModelProperty(value = "图片地址")
    private String imgUrl;
    
    @JsonIgnore
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    
    @JsonIgnore
    @ApiModelProperty(value = "修改日期")
    private String updateTime;
    
    @JsonIgnore
    @ApiModelProperty(value = "版本号")
    private String version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getEquivalentMoney() {
		return equivalentMoney;
	}

	public void setEquivalentMoney(Double equivalentMoney) {
		this.equivalentMoney = equivalentMoney;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public String getDate() {
		if(!StringUtils.isEmpty(date)&&date.length()>19){
			date = date.substring(0,19);
		}
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTxGroupId() {
		return txGroupId;
	}

	public void setTxGroupId(String txGroupId) {
		this.txGroupId = txGroupId;
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


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
