package com.umessage.letsgo.domain.vo.wallet.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by zhajl on 16/9/23.
 */
public class WalletValidCodeResponse extends CommonResponse {
    /**
     * 获取验证码手机号
     */
    @ApiModelProperty(value="获取验证码手机号")
    private String mobileNo;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }


    public static WalletValidCodeResponse createNotTypeResponse(){
        class NotTypeResponse extends WalletValidCodeResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }

            @Override
            public String getRetMsg() {
                return "验证码类型为空";
            }
        }

        return new NotTypeResponse();
    }

}
