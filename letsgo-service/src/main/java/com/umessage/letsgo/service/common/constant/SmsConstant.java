package com.umessage.letsgo.service.common.constant;

/**
 * Created by Albert on 2015/7/24.
 */
public class SmsConstant {

    public static String SMS_URL = "";
    public static String SMS_I18URL="";

    public static String IP = "";

    public static String USERNAME = "";

    public static String PASSWORD = "";

    public static final String SMS_TYPE_CODE = "CODE";

    public static final String SMS_TYPE_INVITE = "INVITE";

    public static String CUSTOMER_SERVICE_PHONE = "";

    //发送短信用哪种方式
    public static String  SMS_TYPE = "";
    public static String  I18SMS_TYPE = "";

    public static String  SMS_SMC = "SMC";
    public static String  SMS_MT = "MT";
    public static String  SMS_TX = "TX";

    public static int TX_SMS_APPID = 0;
    public static String  TX_SMS_APPKEY = "";

    // 旅行社导入名单后发送给未加入领队的短信：
    //【跟上】XXX领队您好，您的团（团名称：XXXXXXXXXX，XXXXXX（旅行社））已经集结完毕，赶快下载“跟上-领队"APP和成员见面吧!点击http://t.cn/Rt6hXKb下载。
    public static String SMS_CID_1="UtcFKhiv9FXn";

    // 旅行社导入名单后发送给未加入游客的短信&领队与导游邀请游客加入时发送的短信：
    //【跟上】XXX您好，（团名称：XXXXXXXXXX，XXXXXX（旅行社））领队召集您去“跟上APP”上开会啦！点击http://t.cn/Rt6z00S下载。
    public static String SMS_CID_2="kwvX5sj4hRvG";

    // 游客邀请自己组员加入时发送的短信：
    //【跟上】XXX您好，您的同伴XXX邀请您使用“跟上APP”，（团名称：XXXXXXXXXX，XXXXXX（旅行社））精彩旅程等你加入！点击http://t.cn/Rt6z00S下载。
    public static String SMS_CID_3="78zy3ubaLgTU";

    // 旅行社导入名单后发送给未加入导游的短信&领队邀请导游加入：
    //【跟上】XXX导游您好，您的团（团名称：XXXXXXXXXX，XXXXXX（旅行社））已经集结完毕，赶快下载“跟上-领队APP”和成员见面吧!点击http://t.cn/Rt6hXKb下载。
    public static String SMS_CID_4="jjozGe1b8ePH";

    //旅行社导入名单后，该领队存在用户时，发送给领队的短信。
    //【跟上】XXX领队您好，您的团（团名称：XXXXXXXXXX，XXXXXX（旅行社））已经集结完毕，赶快打开“跟上-领队”APP和成员见面吧!点击http://t.cn/Rt6hXKb下载。
    public static String SMS_CID_5="SMS_CID_5";

    //旅行社导入名单后，该导游存在用户时，发送给导游的短信。
    //【跟上】XXX导游您好，您的团（团名称：XXXXXXXXXX，XXXXXX（旅行社））已经集结完毕，赶快下载“跟上-领队APP”和成员见面吧!点击http://t.cn/Rt6hXKb下载。
    public static String SMS_CID_6="SMS_CID_6";

    //旅行社导入名单后，该游客存在用户时，发送给游客的短信。
    //【跟上】XXX您好，（团名称：XXXXXXXXXX，XXXXXX（旅行社））领队召集您去“跟上-游客APP”上开会啦！点击http://t.cn/Rt6z00S查看。
    public static String SMS_CID_7="SMS_CID_7";

    //【跟上】您好，您在跟上平台上手机验证码为%P%，10分钟内有效，请不要将此验证码透露给任何人。
    public static String CODE_ID = "sgFvCYBYwoPE";

    //游客邀请围观  发送邀请信息
    //【跟上】XXX您好，（团名称：XXXXXXXXXX，XXXXXX（旅行社））领队召集您去“跟上-游客APP”上开会啦！点击http://t.cn/Rt6z00S查看。
    public static String SMS_CID_8="SMS_CID_8";

    public static String getContentById(String smsId){
        if (CODE_ID.equals(smsId)) {
            return "您好，您在跟上平台上手机验证码为%P%，"+Constant.VALID_CODE_TIME+"分钟内有效，请不要将此验证码透露给任何人。";
        }else if (SMS_CID_1.equals(smsId)) {
            return "%P1%领队您好，您的团（团名称：%P4%%P3%）已经集结完毕，赶快下载“跟上APP”和成员见面吧!点击下载%P5%。";
        }else if (SMS_CID_2.equals(smsId)) {
            return "%P1%您好，（团名称：%P4%%P3%）领队召集您去“跟上APP”上开会啦！点击下载%P5%。";
        }else if (SMS_CID_3.equals(smsId)) {
            return "%P1%您好，您的同伴%P2%邀请您使用“跟上APP”，（团名称：%P4%%P3%）精彩旅程等你加入！点击下载%P5%。";
        }else if (SMS_CID_4.equals(smsId)) {
            return "%P1%导游您好，您的团（团名称：%P4%%P3%）已经集结完毕，赶快下载“跟上APP”和成员见面吧!点击下载%P5%。";
        }else if (SMS_CID_5.equals(smsId)) {
            return "%P1%领队您好，您的团（团名称：%P4%%P3%）已经集结完毕，赶快打开“跟上APP”和成员见面吧!点击下载%P5%。";
        }else if (SMS_CID_6.equals(smsId)) {
            return "%P1%导游您好，您的团（团名称：%P4%%P3%）已经集结完毕，赶快打开“跟上APP”和成员见面吧!点击下载%P5%。";
        }else if (SMS_CID_7.equals(smsId)) {
            return "%P1%您好，（团名称：%P4%%P3%）领队召集您去“跟上APP”上开会啦！点击查看%P5%。";
        }else if (SMS_CID_8.equals(smsId)) {
            return "%P1%盛情邀请您围观“%P4%”点击查看%P5%。";
        }else {
            return "不存在该短信模板，请检查。";
        }
    }


    public static String BASE_MAP_URL = "http://restapi.amap.com/v3/assistant/coordinate/convert?locations=";

    public static String MAP_KEY_URL = "&coordsys=gps&key=";

    public static String  MAP_KEY = "";

}
