package com.umessage.letsgo.openapi.wallet;
import com.jrmf360.IStandardWallet;
import com.jrmf360.vo.request.StandardWalletRequest;
import com.jrmf360.vo.response.StandardWalletResponse;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.wallet.request.WalletTransFerOutRequest;
import com.umessage.letsgo.domain.vo.wallet.response.WalletTransFerOutResponse;
import com.umessage.letsgo.domain.vo.wallet.response.WalletValidCodeResponse;
import com.umessage.letsgo.service.api.wallet.IWalletService;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by zengguoqing on 2016/9/14.
 */
@Api(value = "钱包位置页面", description = "关于钱包交易的相关操作")
@Controller
@RequestMapping(value = "/api/wallet")
public class WalletController {
    @Resource
    private IWalletService walletService;
    @Resource
    private IStandardWallet iStandardWallet;

    @RequestMapping(value = "/sendValidCode", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取验证码【钱包】", notes = "获取验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse sendValidCode(@ApiParam(value = "验证码类型【0:充值，1:提现】", required = true) @RequestParam(value = "type") Integer type) {
        WalletValidCodeResponse walletValidCodeResponse = walletService.sendCheckCodeMessage(type);
        return walletValidCodeResponse;
    }

    @RequestMapping(value = "/withdrawCash", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "提现【钱包】", notes = "提现")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
      public CommonResponse withdrawCash(
            @ApiParam(value = "1.cardNo：银行卡号，必填字段; " +
                              "2.outMoney：提现金额，必填字段；" +
                              "3.valiCode：短信验证码，必填字段;", required = false)
                         @RequestBody WalletTransFerOutRequest walletTransFerOutRequest){
        WalletTransFerOutResponse walletTransFerOutResponse = new WalletTransFerOutResponse();
        if (StringUtils.isEmpty(walletTransFerOutRequest.getCardNo())){
            return walletTransFerOutResponse.createNotCardNoResponse();
        }

        if (StringUtils.isEmpty(walletTransFerOutRequest.getOutMoney())){
            return walletTransFerOutResponse.createNotOutMoneyResponse();
        }

        if (StringUtils.isEmpty(walletTransFerOutRequest.getValiCode())){
            return walletTransFerOutResponse.createNotValiCodeResponse();
        }

        walletTransFerOutResponse = walletService.transferOut(walletTransFerOutRequest);
        return walletTransFerOutResponse;
    }
}
