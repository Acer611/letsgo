package com.jrmf360.vo.request;

/**
 * Created by zhajl on 16/9/23.
 */
public class TransferRequest extends WalletCommonRequest{
    /**
     * 渠道用户唯一标识
     */
    private String custUid;
    /**
     * 转账金额
     */
    private Double transferAmount;
    /**
     * 渠道订单号
     */
    private String custOrderno;
    /**
     * 转账说明
     */
    private String transferDesc;
    /**
     * 渠道用户手机号
     */
    private String custMobile;
    /**
     * 渠道用户昵称
     */
    private String custNickname;
    /**
     * 渠道用户头像URL
     */
    private String custImg;

    public String getCustUid() {
        return custUid;
    }

    public void setCustUid(String custUid) {
        this.custUid = custUid;
    }

    public Double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getCustOrderno() {
        return custOrderno;
    }

    public void setCustOrderno(String custOrderno) {
        this.custOrderno = custOrderno;
    }

    public String getTransferDesc() {
        return transferDesc;
    }

    public void setTransferDesc(String transferDesc) {
        this.transferDesc = transferDesc;
    }

    public String getCustMobile() {
        return custMobile;
    }

    public void setCustMobile(String custMobile) {
        this.custMobile = custMobile;
    }

    public String getCustNickname() {
        return custNickname;
    }

    public void setCustNickname(String custNickname) {
        this.custNickname = custNickname;
    }

    public String getCustImg() {
        return custImg;
    }

    public void setCustImg(String custImg) {
        this.custImg = custImg;
    }
}
