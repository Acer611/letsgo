package com.umessage.letsgo.service.api.wallet;

import com.jrmf360.vo.request.StandardWalletRequest;
import com.jrmf360.vo.request.TransferRequest;
import com.jrmf360.vo.response.StandardWalletResponse;
import com.jrmf360.vo.response.TransferResponse;
import com.umessage.letsgo.domain.po.security.TradeDetailEntity;
import com.umessage.letsgo.domain.vo.wallet.WalletResponse;
import com.umessage.letsgo.domain.vo.wallet.request.WalletTransFerOutRequest;
import com.umessage.letsgo.domain.vo.wallet.response.WalletTransFerOutResponse;
import com.umessage.letsgo.domain.vo.wallet.response.WalletValidCodeResponse;

/**
 * Created by zengguoqing on 2016/9/14.
 */
public interface IWalletService {
    WalletResponse getCreateAccount(String custUid, String custMobile);

    WalletValidCodeResponse sendCheckCodeMessage(Integer type);

    WalletTransFerOutResponse transferOut(WalletTransFerOutRequest walletTransFerOutRequest);

    StandardWalletRequest StandardWalletSetParames(WalletTransFerOutRequest walletTransFerOutRequest);

    WalletTransFerOutResponse walletTransFerOutSetParames(StandardWalletResponse standardWalletResponse);

    TradeDetailEntity tradeDetailEntitySetParames(StandardWalletResponse standardWalletResponse, WalletTransFerOutRequest walletTransFerOutRequest);

    TransferResponse transferToUser(TransferRequest request);
}
