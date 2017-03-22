package com.umessage.letsgo.core.config.constant;


public class ConfigConstant {

    /**
     * 通知邮件地址
     */
    public static String MAIL_RECIPIENT = "";
	/**
	 * 留言邮件地址
	 */
    public static String MAIL_LEAVE_MESSAGE_RECIPIENT = "";
	/**
	 * 发送邮件地址
	 */
	public static String MAIL_SENDER = "";
	/**
	 * 发送邮件用户名
	 */
	public static String MAIL_USERNAME = "";
	/**
	 * 发送邮件密码
	 */
	public static String MAIL_PASSWORD = "";

	/**
	 * 是否返回正确签名
	 */
	public static String RETURN_APP_SIGN = "true";

	/**
	 * 领队app下载链接
	 */
	public static String LEADER_URL = "";
	/**
	 * 游客app下载链接
	 */
	public static String TOURIST_URL= "";

	 //行程背景图
    //出行中：
	// http://letsgoimg-10042314.image.myqcloud.com/static_pic/background_pic/traving.png
	public static final String XCLB_CXZBJURL="http://letsgoimg-10042314.image.myqcloud.com/static_pic/background_pic/traving.png";
	//已完成：
	//http://letsgoimg-10042314.image.myqcloud.com/static_pic/background_pic/traveled.png
	public static final String XCLB_YWCBJURL="http://letsgoimg-10042314.image.myqcloud.com/static_pic/background_pic/traveled.png";
	//围观：
	//http://letsgoimg-10042314.image.myqcloud.com/static_pic/background_pic/watching.png
	public static final String XCLB_WGBJURL="http://letsgoimg-10042314.image.myqcloud.com/static_pic/background_pic/watching.png";
	//即将出行：
	//http://letsgoimg-10042314.image.myqcloud.com/static_pic/background_pic/1.png
	//http://letsgoimg-10042314.image.myqcloud.com/static_pic/background_pic/2.png
	//http://letsgoimg-10042314.image.myqcloud.com/static_pic/background_pic/3.png
	//http://letsgoimg-10042314.image.myqcloud.com/static_pic/background_pic/4.png
	//http://letsgoimg-10042314.image.myqcloud.com/static_pic/background_pic/5.png
	public static final String XCLB_JJCXBJURL1="http://letsgoimg-10042314.image.myqcloud.com/static_pic/background_pic/1.png";
	public static final String XCLB_JJCXBJURL2="http://letsgoimg-10042314.image.myqcloud.com/static_pic/background_pic/2.png";
	public static final String XCLB_JJCXBJURL3="http://letsgoimg-10042314.image.myqcloud.com/static_pic/background_pic/3.png";
	public static final String XCLB_JJCXBJURL4="http://letsgoimg-10042314.image.myqcloud.com/static_pic/background_pic/4.png";
	public static final String XCLB_JJCXBJURL5="http://letsgoimg-10042314.image.myqcloud.com/static_pic/background_pic/5.png";

	public static final String[] ALL_COUNTRY_CODE = {"+93", "+355", "+213", "+376", "+244", "+54", "+374", "+247", "+61", "+43",
			"+994", "+1242", "+973", "+880", "+375", "+32", "+501", "+229", "+975", "+591", "+387", "+267", "+55",
			"+673", "+359", "+226", "+257", "+855", "+237", "+1", "+34", "+238", "+236", "+235", "+56", "+86", "+57",
			"+269", "+242", "+682", "+506", "+385", "+53", "+357", "+420", "+45", "+246", "+253", "+593", "+20",
			"+503", "+240", "+291", "+372", "+251", "+500", "+298", "+679", "+358", "+33", "+594", "+689", "+241",
			"+220", "+995", "+49", "+233", "+350", "+30", "+299", "+590", "+502", "+224", "+245", "+592", "+509",
			"+504", "+852", "+36", "+354", "+91", "+62", "+98", "+964", "+353", "+972", "+39", "+225", "+1876", "+81",
			"+962", "+7", "+254", "+686", "+850", "+82", "+965", "+996", "+856", "+371", "+961", "+266", "+231",
			"+218", "+370", "+352", "+853", "+389", "+261", "+265", "+60", "+960", "+223", "+356", "+692", "+596",
			"+222", "+230", "+269", "+52", "+691", "+373", "+377", "+976", "+212", "+258", "+95", "+264", "+674",
			"+977", "+31", "+599", "+687", "+64", "+505", "+227", "+234", "+683", "+672", "+47", "+968", "+92", "+680",
			"+507", "+675", "+595", "+51", "+63", "+48", "+689", "+351", "+974", "+262", "+40", "+7", "+250", "+684",
			"+685", "+378", "+239", "+966", "+221", "+248", "+232", "+65", "+421", "+386", "+677", "+252", "+27", "+34",
			"+94", "+290", "+508", "+249", "+597", "+268", "+46", "+41", "+963", "+886", "+7", "+255", "+66", "+228",
			"+690", "+676", "+216", "+90", "+993", "+688", "+44", "+1", "+256", "+380", "+971", "+598", "+998", "+678",
			"+58", "+84", "+967", "+381", "+243", "+260", "+259", "+263"};
	/**
	 * app端生成二维码的URL
	 */
	public static String QR_URL= "";

	/**
	 * 男生头像的的URL
	 */
	public static String MALE_HEAD_URL= "";

	/**
	 * 女生头像的的URL
	 */
	public static String FEMALE_HEAD_URL= "";

	/**
	 * 微信的appkey  用于app授权登陆
	 */
	public static String WX_APP_KEY= "";
}
