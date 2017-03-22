package com.umessage.letsgo.core.utils;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class ShaPasswordUtil {
	static ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
	
	public static String encodePassword(String rawPass, Object salt) {
		return encoder.encodePassword(rawPass, salt);
	}
	
	public static void main(String args[]) { 
//		String password1 = ShaPasswordUtil.encodePassword("13488786266admin", "admin");
//		System.out.println("admin:"+ password1);
//		String password2 = ShaPasswordUtil.encodePassword("18010481233adviser", "18010481233");
//		System.out.println("18010481233:"+ password2);
//		String password3 = ShaPasswordUtil.encodePassword("123456", "admin");
//		System.out.println("admin:"+ password3);
//		String password4 = ShaPasswordUtil.encodePassword("123456", "18010481233");
//		System.out.println("18010481233:"+ password4);
//		String password5 = ShaPasswordUtil.encodePassword("123456", "13488786266");
//		System.out.println("13488786266:"+ password5);
//		String password6 = ShaPasswordUtil.encodePassword("123456", "13718134996");
//		System.out.println("13718134996:"+ password6);
//		String password7 = ShaPasswordUtil.encodePassword("123456", "18511403672");
//		System.out.println("18511403672:"+ password7);
//		String password8 = ShaPasswordUtil.encodePassword("123456", "18810125484");
//		System.out.println("18810125484:"+ password8);
		String password9 = ShaPasswordUtil.encodePassword("123456", "ge1donrxgy3dombsga2tekzygyytqobyha3dqobyha3a");
		System.out.println("admin:"+ password9);
		
	}
}
