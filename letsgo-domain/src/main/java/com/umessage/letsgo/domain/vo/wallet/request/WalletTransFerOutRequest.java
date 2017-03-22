package com.umessage.letsgo.domain.vo.wallet.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umessage.letsgo.domain.vo.common.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by fuzhili on 2016/9/28.
 */
public class WalletTransFerOutRequest extends CommonRequest{
    /**
     * 渠道用户唯一标识
     */
    @JsonIgnore
    private String custUid;
    /**
     * 提现金额
     */
    @ApiModelProperty(value="提现金额")
    private Double outMoney = 0.0;
    /**
     * 短信验证码
     */
    @ApiModelProperty(value="短信验证码")
    private String valiCode;
    /**
     * 银行卡号
     */
    @ApiModelProperty(value="银行卡号")
    private String cardNo;
    /**
     * 渠道订单号
     */
    @JsonIgnore
    private String custOrderno;

    public String getCustUid() {
        return custUid;
    }

    public void setCustUid(String custUid) {
        this.custUid = custUid;
    }

    public String getCustOrderno() {
        return custOrderno;
    }

    public void setCustOrderno(String custOrderno) {
        this.custOrderno = custOrderno;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getValiCode() {
        return valiCode;
    }

    public void setValiCode(String valiCode) {
        this.valiCode = valiCode;
    }

    public Double getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(Double outMoney) {
        this.outMoney = outMoney;
    }
}
