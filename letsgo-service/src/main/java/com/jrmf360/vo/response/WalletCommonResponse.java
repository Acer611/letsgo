package com.jrmf360.vo.response;

import java.io.Serializable;

/**
 * Created by zhajl on 16/9/23.
 */
public class WalletCommonResponse  implements Serializable {

    /**
     * 响应状态
     */
    private String respstat;

    /**
     * 响应描述
     */
    private String respmsg;

    public String getRespstat() {
        return respstat;
    }

    public void setRespstat(String respstat) {
        this.respstat = respstat;
    }

    public String getRespmsg() {
        return respmsg;
    }

    public void setRespmsg(String respmsg) {
        this.respmsg = respmsg;
    }


    public static WalletCommonResponse createBadWalletCommonResponse() {
        class BadWalletCommonResponse extends WalletCommonResponse {

            @Override
            public String getRespstat() {
                return "U0005";
            }

            @Override
            public String getRespmsg() {
                return "手机号未验证通过";
            }
        }

        return new BadWalletCommonResponse();
    }
}
