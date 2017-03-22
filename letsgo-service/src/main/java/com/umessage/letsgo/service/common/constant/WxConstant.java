package com.umessage.letsgo.service.common.constant;

/**
 * Created by zengguoqing on 2016/11/21.
 */
public class WxConstant {

    public static String WX_APP_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

    //public static String WX_APP

    //公众号 APP_ID
    public static String APP_ID = "";
    //公众号 APP_SECRET
    public static String  APPSECRET = "";
    //公众号 TOKEN
    public static String TOKEN = "";
    //公众号 AES_KEY
    public static String AESKEY = "";
    //微信支付商户号
    public static String PARTENER = "";
    //微信支付平台商户API密钥
    public static String PARTENER_KEY = "";

    //微信跳转路径
    //分房入住通知
    public static final String ROOMNOTICE = "http://www.igenshang.com/download.html";



    //APP 每日点评
    public static final String APP_EVERYDAY_COMMENT="scheduleDetailComment/showJournarComment?";

    //APP 问卷调查
    public static final String APP_SURVEY_COMMENT="survey/getSurvey?";

    //APP 行程信息
    public static final String APP_SCHEDULE ="schedule/getScheduleShow?";

    //微信绑定的跳转link
    public static final String BINDING_LINK = "getWeChatInfoByOpenID";

}
