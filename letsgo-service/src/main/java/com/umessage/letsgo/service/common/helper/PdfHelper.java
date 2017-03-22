package com.umessage.letsgo.service.common.helper;


import com.umessage.letsgo.core.utils.IDUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by myz on 2016/10/14.
 * savePdfCloudFile 保存pdf到腾讯云服务器上
 * saveThumbnailCloudFile 保存pdf缩略图到腾讯云服务器上
 */
public class PdfHelper {

    private static Logger logger = Logger.getLogger(PdfHelper.class);
    /**
     * 保存pdf到腾讯云存储服务器上
     * dest pdf路径
     */
    public static String savePdfCloudFile(String dest,String destN) throws Exception {

        //上传到腾讯云位置
//        String filePath ="/survey/pdf/"+IDUtil.uuid()+".pdf";
        String filePath ="/survey/pdf/"+destN+".pdf";
        logger.info("pdf上传到腾讯云的filePath："+filePath);
        //读取pdf
        InputStream inputStream = new FileInputStream(new File(dest));
        String pdfUrl = new QCloudHelper().fileUpload(filePath, inputStream);
        logger.info("pdf上传腾讯云返回url："+pdfUrl);
        return pdfUrl;
    }
    /**
     * 保存pdf的缩略图到腾讯云存储服务器上
     * dest pdf缩略图路径
     */
    public static String saveThumbnailCloudFile(String thumbnailUrl) throws Exception {

        //上传到腾讯云位置
        String filePath ="/survey/thumbnail/"+IDUtil.uuid()+".png";
        logger.info("pdf缩略图上传到腾讯云位置："+filePath);
        //读取pdf缩略图
        InputStream inputStream = new FileInputStream(thumbnailUrl);
        String pdfUrl = new QCloudHelper().fileUpload(filePath, inputStream);
        logger.info("pdf缩略图腾讯云返回url："+pdfUrl);
        return pdfUrl;
    }

    /**
     * 下载pdf到本地
     * 参数url pdf腾讯云地址
     */
//    public void downLoad(String filePath, HttpServletResponse response, boolean isOnLine) throws Exception {
//            File f = new File(filePath);
//            if (!f.exists()) {
//                response.sendError(404, "File not found!");
//                return;
//            }
//            BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
//            byte[] buf = new byte[1024];
//            int len = 0;
//
//            response.reset(); // 非常重要
//            if (isOnLine) { // 在线打开方式
//                URL u = new URL("file:///" + filePath);
//                response.setContentType(u.openConnection().getContentType());
//                response.setHeader("Content-Disposition", "inline; filename=" + f.getName());
//                // 文件名应该编码成UTF-8
//            } else { // 纯下载方式
//                response.setContentType("application/x-msdownload");
//                response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
//            }
//            OutputStream out = response.getOutputStream();
//            while ((len = br.read(buf)) > 0)
//                out.write(buf, 0, len);
//            br.close();
//            out.close();
//        }


}
