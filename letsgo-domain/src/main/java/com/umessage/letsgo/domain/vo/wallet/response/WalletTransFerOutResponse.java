package com.umessage.letsgo.domain.vo.wallet.response;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by fuzhili on 2016/9/28.
 */
public class WalletTransFerOutResponse extends CommonResponse{
    /**
     * 金融魔方订单号
     */
    @ApiModelProperty(value="金融魔方订单号")
    private String jrmfOrderno;
    /**
     * 提现金额
     */
    @ApiModelProperty(value="提现金额")
    private Double outMoney;
    /**
     * 提现日期
     */
    @ApiModelProperty(value="提现日期")
    private Date outDate;
    /**
     * 预计到账日期
     */
    @ApiModelProperty(value="预计到账日期")
    private Date arriveDate;
    /**
     * 到账银行
     */
    @ApiModelProperty(value="到账银行")
    private String arriveBankName;
    /**
     * 到账卡号
     */
    @ApiModelProperty(value="到账卡号")
    private String arrivecardNo;

    public String getJrmfOrderno() {
        return jrmfOrderno;
    }

    public void setJrmfOrderno(String jrmfOrderno) {
        this.jrmfOrderno = jrmfOrderno;
    }

    public Double getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(Double outMoney) {
        this.outMoney = outMoney;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public String getArriveBankName() {
        return arriveBankName;
    }

    public void setArriveBankName(String arriveBankName) {
        this.arriveBankName = arriveBankName;
    }

    public String getArrivecardNo() {
        return arrivecardNo;
    }

    public void setArrivecardNo(String arrivecardNo) {
        this.arrivecardNo = arrivecardNo;
    }


    public static WalletTransFerOutResponse createNotCustUidResponse(){
        class NotCustUidResponse extends WalletTransFerOutResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }

            @Override
            public String getRetMsg() {
                return "渠道用户唯一标识为空";
            }
        }

        return new NotCustUidResponse();
    }

    public static WalletTransFerOutResponse createNotOutMoneyResponse(){
        class NotOutMoneyResponse extends WalletTransFerOutResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }

            @Override
            public String getRetMsg() {
                return "提现金额不能为空";
            }
        }

        return new NotOutMoneyResponse();
    }
    public static WalletTransFerOutResponse createNotCardNoResponse(){
        class NotCardNoResponse extends WalletTransFerOutResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }

            @Override
            public String getRetMsg() {
                return "银行卡号不能为空";
            }
        }

        return new NotCardNoResponse();
    }
    public static WalletTransFerOutResponse createNotCustOrdernoResponse(){
        class NotCustOrdernoResponse extends WalletTransFerOutResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }

            @Override
            public String getRetMsg() {
                return "渠道订单号不能为空";
            }
        }

        return new NotCustOrdernoResponse();
    }
    public static WalletTransFerOutResponse createNotValiCodeResponse(){
        class NotValiCodeResponse extends WalletTransFerOutResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }

            @Override
            public String getRetMsg() {
                return "短信验证码为空";
            }
        }

        return new NotValiCodeResponse();
    }

    public static WalletTransFerOutResponse createBalanceNotEnoughResponse(){
        class BalanceNotEnoughResponse extends WalletTransFerOutResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.INVALID_PARAMETER;
            }

            @Override
            public String getRetMsg() {
                return "提现金额大于账户余额";
            }
        }

        return new BalanceNotEnoughResponse();
    }
}
