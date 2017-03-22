package com.xiaomi.constant;

/**
 * Created by Administrator on 2016/5/14.
 */
public class MiPushConstant {

    public static int SANDBOX = 0;  // 0：测试环境（暂不支持Android）；1：正式环境

    // 领队端参数
    public static String ANDROID_APP_SECRET = "";

    public static String ANDROID_PACKAGE_NAME = "";

    public static String IOS_APP_SECRET = "";

    // 游客端参数
    //public static String T_ANDROID_APP_SECRET = "";

    //public static String T_ANDROID_PACKAGE_NAME = "";

    //public static String T_IOS_APP_SECRET = "";

    // 公共参数
    public final static int PASS_THROUGH_MESSAGE = 1;  // 1表示透传消息
    public final static int NOTIFICATION_MESSAGE = 0;  // 0表示通知栏消息

    public final static int DEFAULT_ALL = -1;
    public final static int DEFAULT_SOUND = 1;     // 使用默认提示音提
    public final static int DEFAULT_VIBRATE = 2;   // 使用默认震动提示
    public final static int DEFAULT_LIGHTS = 4;    // 使用默认led灯光提示
}
