package com.jrmf360;

import com.jrmf360.vo.request.OpenWalletRequest;
import com.jrmf360.vo.request.StandardWalletRequest;
import com.jrmf360.vo.request.TransferRequest;
import com.jrmf360.vo.request.ValidCodeRequest;
import com.jrmf360.vo.response.StandardWalletResponse;
import com.jrmf360.vo.response.TransferResponse;
import com.jrmf360.vo.response.WalletCommonResponse;
/**
 * Created by zhajl on 16/9/23.
 */
public interface IStandardWallet {

    /**
     * 开通钱包
     * @param request
     * @return
     */
    WalletCommonResponse openWallet(OpenWalletRequest request);

    /**
     * 渠道转账至个人用户
     * @param request
     * @return
     */
    TransferResponse transferToUser(TransferRequest request);
    /**
     * 提现
     * @param request
     *  @return
     */
    StandardWalletResponse transferOut(StandardWalletRequest request);

    /**
     * 获取验证码
     * @param request
     * @return
     */
    WalletCommonResponse valiCode(ValidCodeRequest request);

}
