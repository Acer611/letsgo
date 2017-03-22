package com.qq.tim.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zhajl on 2016/4/29.
 */
public class KeyManager {
    private static Logger logger = Logger.getLogger("keyManger");

    /**
     * 获取私钥，公钥串
     *
     * @param fileName
     * @return
     */
    public static String getKey(String fileName) {
        String key = "";
        BufferedReader reader = null;
        try {
            InputStream is = KeyManager.class.getClassLoader().getResourceAsStream(
                    fileName);
            if (is == null)
                return  key;
            InputStreamReader in = new InputStreamReader(is);
            reader = new BufferedReader(in);
            key = readerToString(reader);
        } catch (Exception e) {
            logger.error("读入私钥,公钥出错.");
            e.printStackTrace();
        } finally {
            if (reader == null)
                return key;
            try {
                reader.close();
            } catch (IOException e) {
                logger.error("读取公钥私钥后，关闭输入流出错。");
                e.printStackTrace();
            }
        }

        return key;
    }

    public static  String readerToString(BufferedReader reader) throws IOException {
        String line = null;
        StringBuffer strBuffer = new StringBuffer();

        while ((line = reader.readLine()) != null)
        {
            strBuffer.append(line);
            strBuffer.append("\n");
        }
        return strBuffer.toString();
    }

    public static void main(String args[]) {
        String priKey = KeyManager.getKey("tls_private_key1");
        System.out.println(priKey);
    }
}
