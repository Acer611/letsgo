package com.jrmf360.vo.response;

/**
 * Created by zhajl on 16/9/23.
 */
public class TransferResponse extends WalletCommonResponse {
    /**
     * 金融魔方订单号
     */
    private String jrmfOrderno;

    public String getJrmfOrderno() {
        return jrmfOrderno;
    }

    public void setJrmfOrderno(String jrmfOrderno) {
        this.jrmfOrderno = jrmfOrderno;
    }
}
