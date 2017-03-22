package com.umessage.letsgo.core.utils;


import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ELUtil {
    /**
     * 字符串截取
     * @param source
     * @param len
     * @return
     */
    public static String substring(String source, Integer len){
        if(source.length() > len){
            source = source.substring(0, len) + "...";
        }
        return source;
    }


    /**
     * 字符串加密
     * @param teamId
     * @return
     */
    public static String getUrlEncodeTeamId(String teamId){
        if(StringUtils.isEmpty(teamId)) return null;
        try {
            return URLEncoder.encode(teamId,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return teamId.replaceAll("#","%23");
    }


    /**
     * 字符串分割转换为list
     * @param str
     * @return
     */
    public static List<String> strToList(String str) {
        List<String> list = new ArrayList<>();
        String[] array = str.split(",");
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }

        return list;
    }
}
