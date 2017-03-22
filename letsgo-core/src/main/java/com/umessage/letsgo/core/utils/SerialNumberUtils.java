package com.umessage.letsgo.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 生成流水号工具类
 * @author Element
 *
 */
public class
SerialNumberUtils {
	
	/**
	 * 生成流水号
	 * @return
	 */
	public static String generate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String str = format.format(new Date());
		return "D" + str;
	}
	
	/**
	 * 根据条件生成流水号
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	public static String generate(String prefix, String suffix) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String str = format.format(new Date());
		return prefix + str + suffix;
	}
	
	//创建随机字符串  
    public static String generateString(int length) {
		//int length = 16;
	    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    StringBuffer sb = new StringBuffer();
	    Random random = new Random();
	    for (int i = 0; i < length; i++) {
	    	sb.append(chars.charAt(random.nextInt(chars.length())));
	    }
	    return sb.toString();
    }
	
	
    public static void main(String[] args) {
    	System.out.println(generate());
    	System.out.println("+++++++++++++++");
    	System.out.println(generate("D","L"));
	}

}
