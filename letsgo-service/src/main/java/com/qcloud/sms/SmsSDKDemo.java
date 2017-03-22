package com.qcloud.sms;

import java.util.ArrayList;

import com.qcloud.sms.SmsMultiSender;
import com.qcloud.sms.SmsMultiSenderResult;
import com.qcloud.sms.SmsSingleSender;
import com.qcloud.sms.SmsSingleSenderResult;

public class SmsSDKDemo {
    public static void main(String[] args) {
    	try {
    		// 请根据实际 appid 和 appkey 进行开发，以下只作为演示 sdk 使用
    		int appid = 1400011509;
    		String appkey = "788baa6932d18758c1ddac9f3db79d2c";
    		
    		String phoneNumber1 = "18301403656";
    		int tmplId = 9576;

    		// 初始化单发
	    	SmsSingleSender singleSender = new SmsSingleSender(appid, appkey);
	    	SmsSingleSenderResult singleSenderResult;
	
	    	// 普通单发
	    	//singleSenderResult = singleSender.send(0, "86", phoneNumber1, "您好，您在跟上平台上手机验证码为6432，10分钟内有效，请不要将此验证码透露给任何人。", "", "");
			//singleSenderResult = singleSender.send(0, "86", phoneNumber1, "张三盛情邀请您围观“普吉岛6日行”点击查看http://t.cn/RMRibkz。", "", "");
			//singleSenderResult = singleSender.send(0, "86", phoneNumber1, "李四您好，（团名称：普吉岛6日行, 跟上旅行社）领队召集您去“跟上APP”上开会啦！点击下载http://t.cn/RMRibkz。", "", "");
			singleSenderResult = singleSender.send(0, "86", phoneNumber1, "王一力您好，（团名称：金牌德法瑞意：雪朗峰 &南法 &凡尔赛宫 &塞纳河游船 &TGV &楚格小镇，众信旅游集团（旅行社））领队召集您去“跟上APP”上开会啦！点击查看http://t.cn/RMRibkz。", "", "");
			System.out.println(singleSenderResult);
	
	    	// 指定模板单发
	    	// 假设短信模板内容为：测试短信，{1}，{2}，{3}，上学。
			/*
	    	ArrayList<String> params = new ArrayList<>();
	    	params.add("张三");
	    	params.add("普吉岛六日行");
	    	params.add("跟上旅行社");
			params.add("http://t.cn/RMRibkz");
	    	singleSenderResult = singleSender.sendWithParam("86", phoneNumber1, tmplId, params, "", "", "");
	    	System.out.println(singleSenderResult);
	    	*/

    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
