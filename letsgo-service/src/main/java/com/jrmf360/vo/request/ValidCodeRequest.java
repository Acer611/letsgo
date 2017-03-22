package com.jrmf360.vo.request;

/**
 * Created by zhajl on 16/9/23.
 */
public class ValidCodeRequest extends WalletCommonRequest{

    /**
     * 渠道用户唯一标识
     */
    private String custUid;
    /**
     * 渠道用户手机号
     */
    private String mobileNo;
    /**
     * 验证码类型
     */
    private Integer type;

    public String getCustUid() {
        return custUid;
    }

    public void setCustUid(String custUid) {
        this.custUid = custUid;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
