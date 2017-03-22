package com.qq.tim.helper;

import com.qq.tim.constant.TimConstant;
import com.qq.tim.util.TlsSignUtil;

/**
 * Created by Administrator on 2016/4/29.
 */
public class TimRestURLHelper {

    public static String getRestURL(String servicename, String command) {
        String url = TimConstant.TIM_REST_URL;
        url = url.replaceFirst("\\$servicename", servicename);
        url = url.replaceFirst("\\$command", command);
        url = url.replaceFirst("\\$sdkappid", Integer.toString(TimConstant.TIM_SDK_APP_ID));
        url = url.replaceFirst("\\$identifier", getAdminIdentifier());
        url = url.replaceFirst("\\$usersig", getAdminSig());
        return url;
    }

    public static String getAdminIdentifier() {
        return TimConstant.TIM_ADMIN_IDENTIFIER;
    }

    public static String getAdminSig() {
        String adminIdentifier = getAdminIdentifier();
        return TlsSignUtil.strSigature(adminIdentifier);
    }

    public static void main(String[] args) {
        System.out.println(TlsSignUtil.strSigature("letsgo@gsxe"));
    }

}
