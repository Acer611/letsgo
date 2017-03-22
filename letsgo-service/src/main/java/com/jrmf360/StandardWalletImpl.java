package com.jrmf360;

import com.jrmf360.constant.WalletConstant;
import com.jrmf360.helper.WalletURLHelper;
import com.jrmf360.vo.request.OpenWalletRequest;
import com.jrmf360.vo.request.StandardWalletRequest;
import com.jrmf360.vo.request.TransferRequest;
import com.jrmf360.vo.request.ValidCodeRequest;
import com.jrmf360.vo.response.StandardWalletResponse;
import com.jrmf360.vo.response.TransferResponse;
import com.jrmf360.vo.response.WalletCommonResponse;
import com.umessage.letsgo.core.utils.CountryCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by zhajl on 16/9/23.
 */
@Service
public class StandardWalletImpl implements IStandardWallet {
    @Resource
    private RestTemplate restTemplate;
    //钱包里常见的响应  打开钱包请求
    public WalletCommonResponse openWallet(OpenWalletRequest request) {
        // 处理国家区号
//        String moblie = WalletURLHelper.HandleCountryCode(request.getCustMobile());
        String moblie = CountryCode.HandleCountryCode(request.getCustMobile());
        request.setCustMobile(moblie);
        //钱包里常见的响应
        WalletCommonResponse response = new WalletCommonResponse();
        String url = "";
        try {                                             //钱包通用的请求
            request = (OpenWalletRequest) WalletURLHelper.SetCommonRequest(request);
            url = WalletURLHelper.getRestURL("openWallet", request);
            response = restTemplate.postForObject(url, null, WalletCommonResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespstat("S0001");
            response.setRespmsg(e.getMessage());
            return response;
        }

        return response;
    }


    /**
     * 3.14.渠道转账至个人用户
     * @param request
     * @return
     */
    public TransferResponse transferToUser(TransferRequest request) {
        // 处理国家区号
//        String moblie = WalletURLHelper.HandleCountryCode(request.getCustMobile());
        String moblie = CountryCode.HandleCountryCode(request.getCustMobile());
        request.setCustMobile(moblie);
        //
        TransferResponse response = new TransferResponse();
        String url = "";
        try {
            request = (TransferRequest) WalletURLHelper.SetCommonRequest(request);
            url = WalletURLHelper.getRestURL("transferToUser", request);
            response = restTemplate.postForObject(url, null, TransferResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespstat("S0002");
            response.setRespmsg(e.getMessage());
            return response;
        }

        return response;
    }


    /**
     *3.8.用户提现
     * @param request
     * @return
     */
    public StandardWalletResponse transferOut(StandardWalletRequest request) {
        StandardWalletResponse response = new StandardWalletResponse();
        String url = "";
        try {
            request = (StandardWalletRequest) WalletURLHelper.SetCommonRequest(request);
            url = WalletURLHelper.getRestURL("transferOut", request);
            response = restTemplate.postForObject(url, null, StandardWalletResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespstat("S0001");
            response.setRespmsg(e.getMessage());
            return response;
        }

        return response;
    }

    /**
     * 发送验证码
     * @param request
     * @return
     */
    public WalletCommonResponse valiCode(ValidCodeRequest request) {
        // 处理国家区号
//        String moblie = WalletURLHelper.HandleCountryCode(request.getMobileNo());
        String moblie = CountryCode.HandleCountryCode(request.getMobileNo());
        request.setMobileNo(moblie);
        WalletCommonResponse response = new WalletCommonResponse();
        String url = "";
        try {
            request = (ValidCodeRequest) WalletURLHelper.SetCommonRequest(request);
            url = WalletURLHelper.getRestURL("valiCode", request);
            response = restTemplate.postForObject(url, null, WalletCommonResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespstat("S0001");
            response.setRespmsg(e.getMessage());
            return response;
        }

        return response;
    }


    /**
     * 用户概览
     * @param request
     * @return
     */
    public WalletCommonResponse myAccount(ValidCodeRequest request) {
        WalletCommonResponse response = new WalletCommonResponse();
        String url = "";
        try {
            request = (ValidCodeRequest) WalletURLHelper.SetCommonRequest(request);
            url = WalletURLHelper.getRestURL("myAccount", request);
            response = restTemplate.postForObject(url, null, WalletCommonResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            response.setRespstat("S0001");
            response.setRespmsg(e.getMessage());
            return response;
        }

        return response;
    }


    /*
    public static void main(String[] args) throws UnsupportedEncodingException, Exception {
        WalletConstant.WALLET_REST_URL = "http://api-test.jrmf360.com/api/v1/standardWallet/$servicename.shtml";
        WalletConstant.WALLET_PARTNER_ID = "daoyoubao";
        TransferRequest request = new TransferRequest();
        //OpenWalletRequest request = new OpenWalletRequest();
        //request.setCustUid("fm3dmmjygm3tombzgyzdqmi");
        request.setCustUid("10179");
        request.setCustMobile("+8615510371958");
        request.setTransferAmount("100");
        request.setCustOrderno("66611166662277");
        //request.setCustUid("fm3dmmjtgezdmobwgi3dqmq");
        //request.setCustUid("10149");
        //request.setCustMobile("+8613126862882");

        //request.setCustUid("ge1domjvha2tcnztgaztikzygyytgnbyha2tqnrsgy2a");
        //request.setCustUid("10183");
        //request.setCustMobile("+8613488786266");

        StandardWalletImpl wallet = new StandardWalletImpl();
        wallet.restTemplate = new RestTemplate();
        //开通
        //WalletCommonResponse response = wallet.openWallet(request);
        //转账
        TransferResponse response =wallet.transferToUser(request);
        String date1="2016-10-14";
        String date2="2016-10-31";
        int w=DateUtils.dayBetween(DateUtils.toDate(date1), DateUtils.toDate(date2));
        Date dd=DateUtils.toDate(date1);
        String date =DateUtils.sdfDateOnly.format(DateUtils.addDay(DateUtils.toDate(date2),1));
        System.out.println("====:" + w+"========="+DateUtils.toDate(date1)+"======="+DateUtils.toDate(date2)+"==="+date );
        System.out.println("响应状态:" + response.getRespstat() + ", 响应描述:" + response.getRespmsg());
        System.out.println("响应状态:" + response.getRespstat() + ", 响应描述:" + response.getRespmsg());
    }
    */

    public static void main(String[] args) {
        WalletConstant.WALLET_REST_URL = "http://api.jrmf360.com/api/v1/standardWallet/$servicename.shtml";
        WalletConstant.WALLET_PARTNER_ID = "daoyoubao";
        WalletConstant.WALLET_KEY = "323b686d-0fc0-4261-bda5-31a695a49359";

        ValidCodeRequest validCodeRequest = new ValidCodeRequest();
        validCodeRequest.setCustUid("66696");
        validCodeRequest.setMobileNo("18397096281");
        validCodeRequest.setType(1);
        StandardWalletImpl wallet = new StandardWalletImpl();
        wallet.restTemplate = new RestTemplate();
        WalletCommonResponse response = wallet.valiCode(validCodeRequest);
        System.out.println("响应状态:" + response.getRespstat() + ", 响应描述:" + response.getRespmsg());

        /*
        TransferRequest transferRequestFrom = new TransferRequest();
        transferRequestFrom.setCustUid("66736");
        transferRequestFrom.setTransferAmount(10d);
        transferRequestFrom.setCustOrderno(UUID.randomUUID().toString());
        transferRequestFrom.setTransferDesc("转账");
        transferRequestFrom.setCustMobile("15030837791");
        StandardWalletImpl wallet = new StandardWalletImpl();
        wallet.restTemplate = new RestTemplate();
        WalletCommonResponse response = wallet.transferToUser(transferRequestFrom);
        System.out.println("响应状态:" + response.getRespstat() + ", 响应描述:" + response.getRespmsg());
        */
        /*
        ValidCodeRequest request = new ValidCodeRequest();
        request.setCustUid("66799");
        StandardWalletImpl wallet = new StandardWalletImpl();
        wallet.restTemplate = new RestTemplate();
        WalletCommonResponse response = wallet.myAccount(request);
        System.out.println("响应状态:" + response.getRespstat() + ", 响应描述:" + response.getRespmsg());
        */
        /*
        OpenWalletRequest request = new OpenWalletRequest();
        request.setCustUid("66680");
        request.setCustMobile("+8618373708481");
        request.setCustImg("http://letsgoimg-10042314.image.myqcloud.com/pic/faceUrl/0ED06440646E4FD99222DA6B002FAA7F.png");
        StandardWalletImpl wallet = new StandardWalletImpl();
        wallet.restTemplate = new RestTemplate();

        WalletCommonResponse response = wallet.openWallet(request);
        System.out.println("响应状态:" + response.getRespstat() + ", 响应描述:" + response.getRespmsg());
        */
        /*
        StandardWalletImpl wallet = new StandardWalletImpl();
        wallet.restTemplate = new RestTemplate();
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setCustUid("10261");
        transferRequest.setTransferAmount(1d);
        transferRequest.setCustOrderno(UUID.randomUUID().toString());
        transferRequest.setTransferDesc("转账");
        transferRequest.setCustMobile("+8618800110365");
        TransferResponse transferResponse = wallet.transferToUser(transferRequest);
        System.out.println("响应状态:" + transferResponse.getRespstat() + ", 响应描述:" + transferResponse.getRespmsg());
        */
    }




}
