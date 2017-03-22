package com.umessage.letsgo.domain.vo.wallet;

import java.io.Serializable;

/**
 * Created by zengguoqing on 2016/9/14.
 */
public class WalletResponse implements Serializable{
    public Integer getRespstat() {
        return respstat;
    }

    public void setRespstat(Integer respstat) {
        this.respstat = respstat;
    }

    public String getRespmsg() {
        return respmsg;
    }

    public void setRespmsg(String respmsg) {
        this.respmsg = respmsg;
    }

    private Integer respstat;
    private String respmsg;
}
