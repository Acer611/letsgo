package com.umessage.letsgo.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
  public static String md5(String text) {
    MessageDigest msgDigest = null;
    try {
      msgDigest = MessageDigest.getInstance("MD5");
      msgDigest.update(text.getBytes("UTF-8"));
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("System doesn't support MD5 algorithm.");
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("System doesn't support your  EncodingException.");
    }
    byte[] bytes = msgDigest.digest();
    return DecimalUtil.parseByte2Hex(bytes);
  }

  public static String md5(byte[] text) {
    MessageDigest msgDigest = null;
    try {
      msgDigest = MessageDigest.getInstance("MD5");
      msgDigest.update(text);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("System doesn't support MD5 algorithm.");
    }
    byte[] bytes = msgDigest.digest();
    return DecimalUtil.parseByte2Hex(bytes);
  }

  public static String md5GBK(String text) {
    MessageDigest msgDigest = null;
    try {
      msgDigest = MessageDigest.getInstance("MD5");
      msgDigest.update(text.getBytes("GBK"));
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("System doesn't support MD5 algorithm.");

    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("System doesn't support your  EncodingException.");
    }
    byte[] bytes = msgDigest.digest();
    return DecimalUtil.parseByte2Hex(bytes);
  }
  
  public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}
  
  
	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

}