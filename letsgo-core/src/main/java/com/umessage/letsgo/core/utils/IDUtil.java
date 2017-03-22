package com.umessage.letsgo.core.utils;

import java.util.UUID;

public class IDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
}
