package com.qq.tim.vo.request;

/**
 * Created by wendy on 2016/6/15.
 */
public class AddSource {
    private final static String AddSource_Type_Service = "AddSource_Type_Service";  // 好友来源：服务端

    private final static String AddSource_Type_Android = "AddSource_Type_Android";  // 好友来源：

    private final static String AddSource_Type_iOS = "AddSource_Type_iOS";  // 好友来源：ios

    public static String getAddSource_Type_Service() {
        return AddSource_Type_Service;
    }

    public static String getAddSource_Type_Android() {
        return AddSource_Type_Android;
    }

    public static String getAddSource_Type_iOS() {
        return AddSource_Type_iOS;
    }
}
