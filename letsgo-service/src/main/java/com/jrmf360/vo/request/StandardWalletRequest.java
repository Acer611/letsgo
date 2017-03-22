package com.jrmf360.vo.request;

/**
 * Created by fuzhili on 2016/9/28.
 */
public class StandardWalletRequest extends WalletCommonRequest{
    /**
     * 渠道用户唯一标识
     */
    private String custUid;
    /**
     * 提现金额
     */
    private Double outMoney;
    /**
     * 短信验证码
     */
    private String valiCode;
    /**
     * 银行卡号
     */
    private String cardNo;
    /**
     * 渠道订单号
     */
    private String custOrderno;

    public String getValiCode() {
        return valiCode;
    }

    public void setValiCode(String valiCode) {
        this.valiCode = valiCode;
    }

    public String getCustUid() {
        return custUid;
    }

    public void setCustUid(String custUid) {
        this.custUid = custUid;
    }

    public Double getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(Double outMoney) {
        this.outMoney = outMoney;
    }
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCustOrderno() {
        return custOrderno;
    }

    public void setCustOrderno(String custOrderno) {
        this.custOrderno = custOrderno;
    }

    @Override
    public String toString() {
        return "StandardWalletRequest{" +
                "custUid='" + custUid + '\'' +
                ", outMoney=" + outMoney +
                ", valiCode='" + valiCode + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", custOrderno='" + custOrderno + '\'' +
                '}';
    }
}
