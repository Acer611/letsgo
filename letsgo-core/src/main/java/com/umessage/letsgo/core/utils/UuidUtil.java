package com.umessage.letsgo.core.utils;

import java.util.UUID;

/**
 * Created by ZhaoYidong on 2016/12/19.
 */
public class UuidUtil {
    public static String getUuidStr(){
        String uuids = UUID.randomUUID().toString();
        uuids = uuids.replaceAll("-", "");
        return uuids;
    }
}
