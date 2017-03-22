package com.huawei.constant;

/**
 * Created by pw on 2016/11/1.
 */
public class HuaweiPushConstant {

    public static int SANDBOX = 0;  // 0：测试环境（暂不支持Android）；1：正式环境

    // 用户参数
    public static String HW_ANDROID_APP_SECRET = "";

    public static String HW_ANDROID_PACKAGE_NAME = "";

    public static String HW_SDK_APP_ID  = "";

    // 公共参数
    public final static int PASS_THROUGH_MESSAGE = 1;  // 1表示透传消息
    public final static int NOTIFICATION_MESSAGE = 0;  // 0表示通知栏消息

    public final static int DEFAULT_ALL = -1;
    public final static int DEFAULT_SOUND = 1;     // 使用默认提示音提
    public final static int DEFAULT_VIBRATE = 2;   // 使用默认震动提示
    public final static int DEFAULT_LIGHTS = 4;    // 使用默认led灯光提示

}
