package com.umessage.letsgo.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class QueryUtils {
	// 判断字符串是否是订单号
	public static boolean isOrderNumber(String query) {
		if(Pattern.matches("[A-Z]{1}[0-9]*", query)){
    		return true;
    	}
		return false;
	}
	// 判断字符串是否是数字
	public static boolean isNumber(String query) {
		if(Pattern.matches("[0-9]*", query)){
    		return true;
    	}
		return false;
	}
	// 判断字符串是否是汉字
	public static boolean isChinese(String query) {
		if(Pattern.matches("[\\u4e00-\\u9fa5]+", query)){
    		return true;
    	}
		return false;
	}
	
	//判断时间 把时间 **时**分**秒
	public static String getTime(long seconds){
		 int d = (int) (seconds/60);
		 int m = (int)(seconds%60);
		 String timeStr = "";
		 if(d>0){
			timeStr = d+"分"+m+"秒";
		 }else{
			timeStr = m+"秒";
		 }
		return timeStr;
	}

	/**
	 * 判断中国的手机号格式是否正确
     */
	public static boolean isChinaPhone(String phone){
		String reg = "^((\\+86)|(86))?1[34578]\\d{9}$";
		return phone.matches(reg);
	}

	/**
	 * 给中国的手机号加上+86
     */
	public static String getPhone(String phone){
		if (phone == null || phone.length()<11) return phone;
		if (isChinaPhone(phone)){
			phone = "+86" + phone.substring(phone.length()-11);
		}
		return phone;
	}

	/**
	 * 名称中只要有中文就去除所有空格
     */
	public static String getChinaName(String realName){
		String reg = "[\\u4E00-\\u9FA5]+";
		Pattern p= Pattern.compile(reg);
		Matcher matcher=p.matcher(realName);
		if (matcher.find()) {
			return realName.replaceAll("\\s","");
		}
		return realName;
	}

	/**
	 * 验证字符串日期类型为"yyyy-MM-dd"
     */
	public static boolean verifyDateType(String dateType){
		String reg = "^\\d{4}-\\d{2}-\\d{2}$";
		return dateType.matches(reg);
	}

	/**
	 * 验证护照
     */
	public static boolean verifyPassPort(String passPort){
		String reg = "^1[45][0-9]{7}|G[0-9]{8}|P[0-9]{7}|S[0-9]{7,8}|D[0-9]+$";
		return passPort.matches(reg);
	}

//	/**
//	 * 验证身份证
//	 */
//	public static boolean verifyCardCode(String cardCode){
//		String reg = "";
//		return cardCode.matches(reg);
//	}


}
