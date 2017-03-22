package com.umessage.letsgo.core.utils;

import com.umessage.letsgo.core.config.constant.ConfigConstant;

/**
 * 处理国家代号
 * Created by myz on 2016/11/17.
 */
public class CountryCode {

    /*
       excel导入游客时，获取国家代号
     */
    public static String HandleCountryCodeOne(String mobile) {
        String result = null;

        for (int i = 0; i < ConfigConstant.ALL_COUNTRY_CODE.length; i++) {
            String code = ConfigConstant.ALL_COUNTRY_CODE[i];
            if (mobile.startsWith(code)) {
                result = code;
                break;
            }
        }
        return result;
    }

    /*
       钱包,处理国家区号
     */
    public static String HandleCountryCode(String mobile) {
        String result = mobile;

        for (int i = 0; i < ConfigConstant.ALL_COUNTRY_CODE.length; i++) {
            String code = ConfigConstant.ALL_COUNTRY_CODE[i];
            if (mobile.startsWith(code)) {
                result = mobile.substring(code.length());
                break;
            }
        }
        return result;
    }

}
