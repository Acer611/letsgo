package com.umessage.letsgo.domain.po.security;

import com.umessage.letsgo.core.annotation.dataitem.Catalog;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2016/9/28.
 */
@Catalog(code = "TradeDetailEntity")
public class TradeDetailEntity implements Serializable {
    private Long id;
    /**
     * 交易订单号
     */
    @ApiModelProperty(value="交易订单号")
    private String tradeOrderno;
    /**
     * 交易类型，1提现，2转账
     */
    @ApiModelProperty(value="交易类型，【1：提现；2：转账】")
    private Integer tradeType;
    /**
     * 交易名称
     */
    @ApiModelProperty(value="交易名称")
    private String tradeName;
    /**
     * 交易金额
     */
    @ApiModelProperty(value="交易金额")
    private Double tradeAmount;
    /**
     * 交易日期
     */
    @ApiModelProperty(value="交易日期",dataType ="java.lang.Long")
    private Date tradeDate;
    /**
     * 交易状态(0交易成功,1交易失败
     */
    @ApiModelProperty(value="交易状态【1交易成功,2交易失败】")
    private Integer tradeStatus;
    /**
     * 银行卡号
     */
    @ApiModelProperty(value="银行卡号")
    private String cardno;
    /**
     * 金融魔方订单号
     */
    @ApiModelProperty(value="金融魔方订单号")
    private String jrmfOrderno;
    /**
     * 预计到账日期
     */
    @ApiModelProperty(value="预计到账日期",dataType ="java.lang.Long")
    private Date arriveDate;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",dataType ="java.lang.Long")
    private Date createTime;
    /**
     * 版本号
     */
    @ApiModelProperty(value="版本号")
    private String version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTradeOrderno() {
        return tradeOrderno;
    }

    public void setTradeOrderno(String tradeOrderno) {
        this.tradeOrderno = tradeOrderno;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public Double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getJrmfOrderno() {
        return jrmfOrderno;
    }

    public void setJrmfOrderno(String jrmfOrderno) {
        this.jrmfOrderno = jrmfOrderno;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
