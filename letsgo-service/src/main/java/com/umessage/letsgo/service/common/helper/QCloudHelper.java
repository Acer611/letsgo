package com.umessage.letsgo.service.common.helper;

import com.qcloud.cosapi.api.CosCloud;
import com.qcloud.cosapi.file.FileProcess;
import com.qcloud.cosapi.sign.Sign;
import com.umessage.letsgo.core.utils.IDUtil;
import com.umessage.letsgo.core.utils.JsonUtils;
import com.umessage.letsgo.service.common.constant.Constant;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/3.
 */
public class QCloudHelper {


    private static  Logger logger = Logger.getLogger(QCloudHelper.class);
    // 获取COS签名
    public static String getSign() {
        String sign = Sign.appSignature(Constant.CLOUD_APP_ID, Constant.CLOUD_SECRET_ID, Constant.CLOUD_SECRET_KEY, getExpiredTime(), Constant.BUCKET);
        return sign;
    }

    private static long getExpiredTime() {
        long expired = System.currentTimeMillis() / 1000 + 7776000; // 有效时间：90天
        return expired;
    }

    // 创建目录
    public static void createFolder(CosCloud cos,String remotePath) throws Exception {
        // 检查是否存在目录
        String result = cos.getFolderStat(Constant.BUCKET, remotePath);
        Map<String, Object> resultMap = JsonUtils.json2map(result);

        if (resultMap.get("code").equals(-166)) {
            result = cos.createFolder(Constant.BUCKET, remotePath);  // 创建目录
        }

    }

    // 获取目录路径
    private static String getFileDirPath(String path) {
        int index = path.lastIndexOf("/");
        return  path.substring(0, index + 1);
    }

    // 文件上传，使用文件流方式上传
    public static String fileUpload(String filePath, InputStream stream) throws Exception {
        CosCloud cos = new CosCloud(Constant.CLOUD_APP_ID, Constant.CLOUD_SECRET_ID, Constant.CLOUD_SECRET_KEY, 60);
        // 判断是否需要创建目录
        String remotePath = getFileDirPath(filePath);
        createFolder(cos,remotePath);
        // 上传文件
        String result = null;
        try{
            result = cos.uploadFile(Constant.BUCKET, filePath, stream);

        }catch (Exception e){
            throw e;
        }finally {
            FileProcess.closeFileStream(stream,filePath);
            cos.shutdown();
        }
        // 获取URL
        String url = getURL(result);

        return url;
    }

    // 文件上传，使用文件url方式上传
    public static String remoteFileUpload(String filePath, String remoteFileUrl) throws Exception {
        URL url = new URL(remoteFileUrl);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(60*1000);
        httpURLConnection.setReadTimeout(60*1000);
        InputStream inputStream = httpURLConnection.getInputStream();

        // 由于相册上传出现图片不全的bug,转换成ByteArray
        ByteArrayInputStream arrayInputStream = getByteArrayInputStream(inputStream);

        String fileUrl = "";
        try {
            fileUrl = fileUpload(filePath, arrayInputStream);
        } finally {
            inputStream.close();
        }

        return QCloudHelper.changDomain(fileUrl);
    }

    private static ByteArrayInputStream getByteArrayInputStream(InputStream inputStream) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        int length = 0;
        byte[] byteArray = new byte[8192];
        while ((length = bufferedInputStream.read(byteArray)) != -1) {
            arrayOutputStream.write(byteArray, 0, length);
        }
        return new ByteArrayInputStream(arrayOutputStream.toByteArray());
    }


    //文件删除
    public static String deleteFile(String url) throws Exception {
        CosCloud cos = new CosCloud(Constant.CLOUD_APP_ID, Constant.CLOUD_SECRET_ID, Constant.CLOUD_SECRET_KEY);
        String response = null;
        try {
             response =cos.deleteFile(Constant.BUCKET,url);
        }catch (Exception e){
            throw e;
        }finally {
            cos.shutdown();
        }
        return response;
    }

    private static String getURL(String jsonStr) {
        Map<String, Object> resultMap = JsonUtils.json2map(jsonStr);
        Map<String, Object> dataMap = (Map<String, Object>) resultMap.get("data");

        String url = (String) dataMap.get("access_url");

        return url;
    }

    public static String changDomain(String url) {
        String newUrl = url.replaceAll("letsgo-"+ Constant.CLOUD_APP_ID +".file.myqcloud.com", "letsgoimg-"+Constant.CLOUD_APP_ID+".image.myqcloud.com");
        return newUrl;
    }

    public static String changPath(String path){
        return path.replaceAll("[@#]", "");
    }

    public static void main(String args[]) throws Exception {
        Constant.CLOUD_APP_ID = 10042314;
        Constant.CLOUD_SECRET_ID = "AKIDoFmjPPhYGNWnL1h6drlfA7KrPXARei1D";
        Constant.CLOUD_SECRET_KEY = "x937ie4vtIUGaXd8XRQQxdrE7Lmg64Wd";
        Constant.BUCKET = "letsgo";
        String photoUrl = QCloudHelper.remoteFileUpload("/pic/teamAlbum/" + changPath("@TGS#12UQYEEE6") + "/"+ IDUtil.uuid() + ".jpg", "http://p.qpic.cn/opensdk_im/0/202664866630894162_144115199781157445_BEC5FDF0945E010D59A07920809A1DB7/720");
        System.out.println(photoUrl);
    }
}
