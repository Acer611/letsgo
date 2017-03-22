package com.qq.tim.util;

import com.qq.tim.constant.TimConstant;
import com.tls.tls_sigature.tls_sigature;
import org.springframework.cache.annotation.Cacheable;

/**
 * Created by zhajl on 2016/4/29.
 */
public class TlsSignUtil {

    @Cacheable(value = "TlsSignCache", key = "#identifier", unless="#result.urlSig == null")
    public static tls_sigature.GenTLSSignatureResult sigature(String identifier) {
        tls_sigature.GenTLSSignatureResult result = new tls_sigature.GenTLSSignatureResult();
        try {
            //Use pemfile keys to test
            String privStr = TimConstant.TLS_PRIVATE_KEY;

            // generate signature
            result = tls_sigature.GenTLSSignatureEx(TimConstant.TIM_SDK_APP_ID, identifier, privStr);
            if (0 == result.urlSig.length()) {
                System.out.println("GenTLSSignatureEx failed: " + result.errMessage);
                return result;
            }

            System.out.println("---\nidentifier:" + identifier + "\ngenerate sig:\n" + result.urlSig + "\n---\n");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String strSigature(String identifier){
        tls_sigature.GenTLSSignatureResult result = sigature(identifier);
        return result.urlSig;
    }

    public static tls_sigature.CheckTLSSignatureResult checkSigature(String identifier, String urlSig){
        tls_sigature.CheckTLSSignatureResult checkResult = new tls_sigature.CheckTLSSignatureResult();
        try{
            //change public pem string to public string
            String pubStr = TimConstant.TLS_PUBLIC_KEY;

            // check signature
            checkResult = tls_sigature.CheckTLSSignatureEx(urlSig, TimConstant.TIM_SDK_APP_ID, identifier, pubStr);
            if(checkResult.verifyResult == false) {
                System.out.println("CheckTLSSignature failed: " + checkResult.errMessage);
                return checkResult;
            }

            System.out.println("\n---\ncheck sig ok -- expire time " + checkResult.expireTime + " -- init time " + checkResult.initTime + "\n---\n");
            return checkResult;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return checkResult;
    }

    public static void main(String[] args) {
        sigature("admin");
        sigature("zhajl");
        sigature("zhaosj");
        sigature("jihx");
        sigature("gaohui");
        sigature("cuixh");
    }
}
