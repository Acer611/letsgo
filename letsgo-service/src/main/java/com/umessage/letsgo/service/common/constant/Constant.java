package com.umessage.letsgo.service.common.constant;


public class Constant{

    public static String MAIL_RECIPIENT = "";

    public static final int VALID_CODE_TIME = 10;    // 短信验证码有效时间

    public static int INTERVAL_TIME = 10;   // 位置上传间隔时间

    public static int SING_TIME = 10;   // 签到10分钟以内有效
    public static double SING_DISTANCE = 0.1;   //签到距离(单位：km)：100米以内
    public static int SING_DISTANCE_NONE= -1;   //没有上传位置，无签到距离。

    public static int SING_STATUS_TRUE = 1; //签到状态：已到
    public static int SING_STATUS_FALSE = 0;//签到状态：未到

    public static int TRAVELING = 1;//出行状态：出行中
    public static int PREPARE_TRAVEL = 2;//出行状态：即将出行
    public static int TRAVELED = 3;//出行状态：已出行

    public static String UPDATE_TEMPLATE = "updateTemplate";//使用原有行程更新成新的行程。
    public static String common_TEMPLATE = "commonTemplate";//使用原有行程更新成新的行程。


    public static String UPDATE_TEMPLATE_More = "updateTemplateMore";//使用原有行程更新成新的行程的更多操作
    public static String MAIN_PAGE_MORE = "mainPageMore";//使用原有行程更新成新的行程的更多操作

    public static int pageNum = 1;  //当前页数
    public static int pageSize = 10;//每页条数

    //签名
    public static String CLIENT_ID = "client_id";
    public static String CLIENT_SECRET = "client_secret";
    public static String CLIENT_VER = "ver";
    public static String CLIENT_SIGN = "sign";
    public static String TIMESTAMP = "timestamp";
    public static String ACCESS_TOKEN = "access_token";


    public static String PHOTO_URL="/pic/photoUrl/";//腾迅云图片上传地址
    public static String FACE_URL="/pic/faceUrl/";//腾迅云头像上传地址


    public static int CLOUD_APP_ID = 0;
    public static String CLOUD_SECRET_ID = "";
    public static String CLOUD_SECRET_KEY = "";
    public static String BUCKET = "";

    public static String API_BASE_URL = "";
    public static String LOGIN_BASE_URL = "";
    public static String WEB_BASE_URL = "";
    public static String API_REGISTER_GETINVITECODE_URL = "";

    public static String WEATHER_REMIND_HOURS = "";

    public static String COMMENT_REMIND_HOURS = "";

    //问卷调查确认 html转pdf时的html的ip以及port
    public static String H5_BASE_URL = "";

    public static String H5_WECHAT_URL = "";

    public static String H5_BASE_URL_HTTP = "";


    //腾讯云通信支持的标配资料字段
    /**
     * 标配资料字段：昵称
     */
    public static final String Tag_Profile_IM_Nick = "Tag_Profile_IM_Nick";

    /**
     * 标配资料字段：头像URL
     */
    public static final String Tag_Profile_IM_Image = "Tag_Profile_IM_Image";

    /**
     * 标配资料字段：加好友验证方式
     */
    public static final String Tag_Profile_IM_AllowType = "Tag_Profile_IM_AllowType";

    /**
     * 标配资料字段：个性签名
     */
    public static final String Tag_Profile_IM_SelfSignature = "Tag_Profile_IM_SelfSignature";


    //**微信模板消息**
    //账号未绑定通知
    public static String BINDNOTICE = "";
    //行程变更通知
    public static String CHANGENOTICE = "";
    //天气提醒
    public static String WEATHERREMIND = "";
    //通知
    public static String SENDNOTICE = "";
    //集合通知
    public static String GATHERNOTICE = "";
    //行程安排提醒
    public static String SCHEDULEREMIND = "";
    //入住通知
    public static String CHECKINNOTICE = "";
    //评价通知
    public static String COMMENTNOTICE = "";



    /* Config    Elasticsearch*/
    public static String ELASTIC_SEARCH_CLUSTER_VALUE = "";
    public static String ELASTIC_SEARCH_CLUSTER_NAME = "cluster.name";

    public static String ELASTIC_SEARCH_NODE_NAME_VALUE = "";
    public static String ELASTIC_SEARCH_ADDRESS_VALUE = "";

    public static String ELASTIC_SEARCH_PORT = "";

}
