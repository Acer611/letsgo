package com.umessage.letsgo.service.impl.wallet;

import com.jrmf360.IStandardWallet;
import com.jrmf360.vo.request.StandardWalletRequest;
import com.jrmf360.vo.request.TransferRequest;
import com.jrmf360.vo.request.ValidCodeRequest;
import com.jrmf360.vo.response.StandardWalletResponse;
import com.jrmf360.vo.response.TransferResponse;
import com.jrmf360.vo.response.WalletCommonResponse;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.dao.security.TradeDetailDao;
import com.umessage.letsgo.domain.po.security.TradeDetailEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.wallet.WalletResponse;
import com.umessage.letsgo.domain.vo.wallet.request.WalletTransFerOutRequest;
import com.umessage.letsgo.domain.vo.wallet.response.WalletTransFerOutResponse;
import com.umessage.letsgo.domain.vo.wallet.response.WalletValidCodeResponse;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.wallet.IWalletService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Created by zengguoqing on 2016/9/14.
 */
@Service
public class WalletServiceImpl implements IWalletService {
    private static final Logger logger = Logger.getLogger(WalletServiceImpl.class);
    @Resource
    private IStandardWallet iStandardWallet;
    @Resource
    private UserLoginHelper oauth2LoginHelper;
    @Resource
    private TradeDetailDao tradeDetailDao;
    @Resource
    private IUserService userService;

    @Override
    public WalletResponse getCreateAccount(String custUid, String custMobile) {
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.postForObject("http://api-test.jrmf360.com/h5/v1/standardWallet/openWallet.shtml",null,null);
        return null;
    }

    /**
     * 发送短信
     * @param type
     * @return
     */
    @Override
    public WalletValidCodeResponse sendCheckCodeMessage(Integer type) {
        ValidCodeRequest validCodeRequest = new ValidCodeRequest();
        WalletValidCodeResponse response = new WalletValidCodeResponse();
        if (null == type){
            return response.createNotTypeResponse();
        }
        //获取当前登录用户
        UserResponse loginUser = oauth2LoginHelper.getLoginUser();
        UserEntity userEntity = null;
        if (null != loginUser){
            userEntity = loginUser.getUserEntity();
            Long id = userEntity.getId();
            String sid = Long.toString(id);
            validCodeRequest.setCustUid(sid);
            validCodeRequest.setMobileNo(userEntity.getPhone());
            response.setMobileNo(userEntity.getPhone());
        }
        validCodeRequest.setType(type);
        WalletCommonResponse walletCommonResponse = iStandardWallet.valiCode(validCodeRequest);
        if (!walletCommonResponse.getRespstat().equals("0000")){
            response.setRetCode(ErrorConstant.SMS_SEND_FAIL);
        }
        response.setRetMsg(walletCommonResponse.getRespmsg());

        return response;
    }


    /**
     * 用户提现
     * @param walletTransFerOutRequest
     * @return
     */
    @Override
    public WalletTransFerOutResponse transferOut(WalletTransFerOutRequest walletTransFerOutRequest){
        StandardWalletRequest standardWalletRequest = StandardWalletSetParames(walletTransFerOutRequest);
        logger.info("用户提现请求参数\n"+ standardWalletRequest.toString());
        //如果转出金额大于用户实际金额,提示用户余额不足
        if (compareReward(standardWalletRequest)){
            return WalletTransFerOutResponse.createBalanceNotEnoughResponse();
        }
        StandardWalletResponse standardWalletResponse = iStandardWallet.transferOut(standardWalletRequest);
        String respstat = standardWalletResponse.getRespstat();
        if ("0000".equals(respstat)){
            //提现成功减少用户余额
            this.subtractReward(standardWalletRequest);
        }
        WalletTransFerOutResponse walletTransFerOutResponse = walletTransFerOutSetParames(standardWalletResponse);

        //为交易流水明细实体赋值
        TradeDetailEntity tradeDetailEntity = tradeDetailEntitySetParames(standardWalletResponse,walletTransFerOutRequest);
        //插入到交易流水明细表
        tradeDetailDao.insert(tradeDetailEntity);
        return walletTransFerOutResponse;
    }

    //判断余额是否足够
    public boolean compareReward(StandardWalletRequest standardWalletRequest){
        //提现金额
        Double outMoney = standardWalletRequest.getOutMoney();
        BigDecimal data1 = new BigDecimal(outMoney);

        String custUid = standardWalletRequest.getCustUid();
        Long userId = Long.valueOf(custUid);
        UserEntity userEntity = userService.getUserById(userId);
        BigDecimal data2 = null;
        if (userEntity != null){
            //奖励金额
            Double totalReward = userEntity.getTotalReward();
            data2 = new BigDecimal(totalReward);
        }
        //如果转出金额大于用户实际金额
        if (data1.compareTo(data2)==1){
            return true;
        }else {
            return false;
        }
    }

    //减少用户余额
    public void subtractReward(StandardWalletRequest standardWalletRequest){
        //提现金额
        Double outMoney = standardWalletRequest.getOutMoney();
        BigDecimal data1 = new BigDecimal(outMoney);

        String custUid = standardWalletRequest.getCustUid();
        Long userId = Long.valueOf(custUid);
        UserEntity userEntity = userService.getUserById(userId);
        BigDecimal data2 = null;
        if (userEntity != null){
            //奖励金额
            Double totalReward = userEntity.getTotalReward();
            data2 = new BigDecimal(totalReward);
        }
        //用户奖励金额减去提现金额
        double subtract = data2.subtract(data1).doubleValue();
        userEntity.setTotalReward(subtract);
        userService.update(userEntity);
    }

    @Override
    public StandardWalletRequest StandardWalletSetParames(WalletTransFerOutRequest walletTransFerOutRequest) {
        StandardWalletRequest standardWalletRequest = new StandardWalletRequest();
        //获取当前登录用户
        UserResponse loginUser = oauth2LoginHelper.getLoginUser();
        UserEntity userEntity = null;
        if (null != loginUser){
            userEntity = loginUser.getUserEntity();
            Long id = userEntity.getId();
            String sid = Long.toString(id);
            standardWalletRequest.setCustUid(sid);
        }
        walletTransFerOutRequest.setCustOrderno(UUID.randomUUID().toString());

        standardWalletRequest.setOutMoney(walletTransFerOutRequest.getOutMoney());
        standardWalletRequest.setCardNo(walletTransFerOutRequest.getCardNo());
        standardWalletRequest.setCustOrderno(walletTransFerOutRequest.getCustOrderno());
        standardWalletRequest.setValiCode(walletTransFerOutRequest.getValiCode());
        return standardWalletRequest;
    }

    @Override
    public WalletTransFerOutResponse walletTransFerOutSetParames(StandardWalletResponse standardWalletResponse){
        String respstat = standardWalletResponse.getRespstat();
        WalletTransFerOutResponse walletTransFerOutResponse = new WalletTransFerOutResponse();
        if (!"0000".equals(respstat)){
            walletTransFerOutResponse.setRetCode(ErrorConstant.INTERNAL_SERVER_ERROR);
        }
        walletTransFerOutResponse.setRetMsg(standardWalletResponse.getRespmsg());
        walletTransFerOutResponse.setJrmfOrderno(standardWalletResponse.getJrmfOrderno());
        walletTransFerOutResponse.setOutMoney(standardWalletResponse.getOutMoney());
        walletTransFerOutResponse.setOutDate(standardWalletResponse.getOutDate());
        walletTransFerOutResponse.setArriveDate(standardWalletResponse.getArriveDate());
        walletTransFerOutResponse.setArriveBankName(standardWalletResponse.getArriveBankName());
        walletTransFerOutResponse.setArrivecardNo(standardWalletResponse.getArrivecardNo());

        return walletTransFerOutResponse;
    }

    @Override
    public TradeDetailEntity tradeDetailEntitySetParames(StandardWalletResponse standardWalletResponse, WalletTransFerOutRequest walletTransFerOutRequest){
        String respstat = standardWalletResponse.getRespstat();
        TradeDetailEntity tradeDetailEntity = new TradeDetailEntity();
        //设置渠道订单号为交易订单号
        tradeDetailEntity.setTradeOrderno(walletTransFerOutRequest.getCustOrderno());
        tradeDetailEntity.setTradeType(2);//1转帐，2提现
        tradeDetailEntity.setTradeName("提现");
        tradeDetailEntity.setTradeDate(standardWalletResponse.getOutDate());
        tradeDetailEntity.setCardno(standardWalletResponse.getArrivecardNo());
        tradeDetailEntity.setJrmfOrderno(standardWalletResponse.getJrmfOrderno());
        tradeDetailEntity.setArriveDate(standardWalletResponse.getArriveDate());
        tradeDetailEntity.setCreateTime(new Date());
        tradeDetailEntity.setVersion("0");
        if ("0000".equals(respstat)){
            logger.info("用户提现交易成功，响应参数\n"+ standardWalletResponse.toString());
            tradeDetailEntity.setTradeStatus(1);//1交易成功,2交易失败
            tradeDetailEntity.setTradeAmount(standardWalletResponse.getOutMoney());
        }else {
            logger.info("用户提现交易失败，响应参数\n"+ standardWalletResponse.toString());
            tradeDetailEntity.setTradeStatus(2);//1交易成功,2交易失败
            tradeDetailEntity.setTradeAmount(walletTransFerOutRequest.getOutMoney());
        }
        return tradeDetailEntity;
    }


    /**
     * 渠道转账至个人用户
     * @param request
     * @return
     */
    @Override
    public TransferResponse transferToUser(TransferRequest request) {
        logger.info("用户提现请求参数\n"+ request.toString());
        TransferResponse transferResponse = iStandardWallet.transferToUser(request);

        //为交易流水明细实体赋值
        TradeDetailEntity tradeDetailEntity = tradeDetailEntitySetTransfer(transferResponse,request);
        //插入到交易流水明细表
        tradeDetailDao.insert(tradeDetailEntity);
        return transferResponse;
    }


    public TradeDetailEntity tradeDetailEntitySetTransfer(TransferResponse transferResponse, TransferRequest request){
        String respstat = transferResponse.getRespstat();
        TradeDetailEntity tradeDetailEntity = new TradeDetailEntity();
        //设置渠道订单号为交易订单号
        tradeDetailEntity.setTradeOrderno(request.getCustOrderno());
        tradeDetailEntity.setTradeType(1);//1转帐，2提现
        tradeDetailEntity.setTradeName("转帐");
        tradeDetailEntity.setTradeDate(new Date());
        tradeDetailEntity.setJrmfOrderno(transferResponse.getJrmfOrderno());
        tradeDetailEntity.setCreateTime(new Date());
        tradeDetailEntity.setVersion("0");
        if ("0000".equals(respstat)){
            logger.info("用户提现交易成功，响应参数\n"+ transferResponse.toString());
            tradeDetailEntity.setTradeStatus(1);//1交易成功,2交易失败
            tradeDetailEntity.setTradeAmount(request.getTransferAmount());
        }else {
            logger.info("用户提现交易失败，响应参数\n"+ transferResponse.toString());
            tradeDetailEntity.setTradeStatus(2);//1交易成功,2交易失败
            tradeDetailEntity.setTradeAmount(request.getTransferAmount());
        }
        return tradeDetailEntity;
    }

}
