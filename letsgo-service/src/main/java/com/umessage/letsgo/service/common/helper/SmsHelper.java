package com.umessage.letsgo.service.common.helper;

import com.umessage.letsgo.service.common.constant.SmsConstant;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/5/11.
 */
public class SmsHelper {

    public static final String encodeHex(byte[] bytes)
    {
        StringBuffer buff =
                new StringBuffer(bytes.length * 2);
        String b;
        for (int i=0; i<bytes.length ; i++)
        {
            b = Integer.toHexString(bytes[i]);
            // byte是两个字节的,而上面的Integer.toHexString会把字节扩展为4个字节
            buff.append(b.length() > 2 ? b.substring(6,8) : b);
            buff.append(" ");
        }
        return buff.toString();
    }


    private static ResponseEntity<String> postRequest(RestTemplate restTemplate, String url, MultiValueMap<String, String> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        return restTemplate.postForEntity(url, request, String.class);
    }

    public static String sendMessage(RestTemplate restTemplate, String mobile, String context){
        String url = SmsConstant.SMS_URL;
        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
        params.add("dest", mobile);
        params.add("msg", context);

        ResponseEntity<String> response = postRequest(restTemplate, url, params);
        String result = response.getBody();
        return splitResult(result);
    }
    public static String getI18SmsURL(String mobile, String context) {
        String url = SmsConstant.SMS_I18URL;
        url = url.replaceFirst("\\$mobile", mobile);
        url = url.replaceFirst("\\$context", context);
        return url;
    }

    public static String sendI18Message(RestTemplate restTemplate, String mobile, String context) throws UnsupportedEncodingException {
 /*       String msg = getUnicodeBytes(context);

        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
        params.add("dest", mobile);
        params.add("msg", msg);
        String url = SmsConstant.SMS_I18URL;

        ResponseEntity<String> response = postRequest(restTemplate, url, params);
        String result = response.getBody();*/

        byte[] bytesUnicode =  context.getBytes("UTF-16BE");
        String con = bytesToHexString(bytesUnicode);
        String url = getI18SmsURL(mobile,con);
        String result = restTemplate.getForObject(url,String.class);

        if(!result.startsWith("-")){
            return "0";
        }
        else{
            return result;
        }

    }

    private static String getUnicodeBytes(String context) throws UnsupportedEncodingException {
        byte[] bytesUnicode =  context.getBytes("UTF-16BE");
        return bytesToHexString(bytesUnicode);
    }

    public  static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    private static String splitResult(String result){
        Pattern p = Pattern.compile("(.*):(.*)");
        Matcher m = p.matcher(result);
        String retCode = result;
        String msgID = "";
        while (m.find()) {
            retCode = m.group(1);
            msgID = m.group(2);
            System.out.println("code:" + retCode + ",msgID:" + msgID);
        }
        return retCode;
    }

    public static void main(String arg[]){
        String result1 = "0:9645e353-cfcd-405a-a834-a7efebe2f529";
        String result2 = "005:";
        String result3 = "003";
        splitResult(result1);
        splitResult(result2);
        splitResult(result3);
    }
}
