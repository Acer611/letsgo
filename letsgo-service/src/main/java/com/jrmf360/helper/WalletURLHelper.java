package com.jrmf360.helper;

import com.jrmf360.constant.WalletConstant;
import com.jrmf360.utils.SignUtil;
import com.jrmf360.vo.request.WalletCommonRequest;
import org.joda.time.DateTime;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhajl on 16/9/23.
 */
public class WalletURLHelper {

    public static String getRestURL(String servicename, WalletCommonRequest request) throws Exception{
        String url = WalletConstant.WALLET_REST_URL;
        url = url.replaceFirst("\\$servicename", servicename);
        Map<String, String> params = new HashMap<String, String>();
        try {
            params = Bean2Map(request, "seckey");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new Exception("转换参数对象失败");
        } catch (IntrospectionException e) {
            e.printStackTrace();
            throw new Exception("转换参数对象失败");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new Exception("转换参数对象失败");
        }

        int count = 0;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (count == 0){
                url = url + "?" + entry.getKey() + "=" + entry.getValue();
            } else {
                url = url + "&" + entry.getKey() + "=" + entry.getValue();
            }
            count++;
        }
        
        return url;
    }

    /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map<String, String> Bean2Map (Object bean, String ignoreParam)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map<String, String> returnMap = new HashMap<String, String>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            // 钱包该字段为大写,转换为大写
            if("valiCode".equals(propertyName)){
                propertyName="ValiCode";
            }
            if (!propertyName.equals("class") && !propertyName.equals(ignoreParam)) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result.toString());
                }
            }
        }
        return returnMap;
    }


    public static WalletCommonRequest SetCommonRequest(WalletCommonRequest request) throws Exception{
        DateTime now =new DateTime();
        request.setPartnerId(WalletConstant.WALLET_PARTNER_ID);
        request.setSeckey(WalletConstant.WALLET_KEY);
        request.setTimeStamp(now.toString("yyyyMMddHHmmss") );
        try {
            String sign = calcSign(request);
            request.setSign(sign);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new Exception("计算签名失败");
        } catch (IntrospectionException e) {
            e.printStackTrace();
            throw new Exception("计算签名失败");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new Exception("计算签名失败");
        }
        return request;
    }

//    public static String HandleCountryCode(String mobile) {
//        String result = mobile;
//
//        for (int i = 0; i < WalletConstant.ALL_COUNTRY_CODE.length; i++) {
//            String code = WalletConstant.ALL_COUNTRY_CODE[i];
//            if (mobile.startsWith(code)) {
//                result = mobile.substring(code.length());
//                break;
//            }
//        }
//        return result;
//    }

    private static String calcSign(WalletCommonRequest request) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        Map<String, String> params = Bean2Map(request, "sign");
        String sign = SignUtil.sign(params);
        return  sign;
    }

}
