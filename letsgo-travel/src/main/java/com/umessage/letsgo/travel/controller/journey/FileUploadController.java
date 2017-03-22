package com.umessage.letsgo.travel.controller.journey;

import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import com.umessage.letsgo.service.common.helper.QCloudHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gaofei on 2017/2/14.
 */
@Controller
@RequestMapping(value = "/upload")
public class FileUploadController {

    @Resource
    private ITravelAgencyService travelService;
    /*
    * 图片命名格式
    */
    private static final String DEFAULT_SUB_FOLDER_FORMAT_AUTO = "yyyyMMddHHmmss";

    protected Logger logger = Logger.getLogger(FileUploadController.class);
    /*
    * 上传图片文件夹
    */
    private static final String UPLOAD_PATH = "upload/img/";

    /*
    * 上传图片
    */
    @RequestMapping(value = "/uploadImg")
    public String uplodaImg(@RequestParam("upload") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        TravelAgencyEntity travelEntity = travelService.getCurrentTravel();
        if(null == travelEntity){
            return "redirect:/login";
        }
        String imageUrl = null;
        try {
            String proName = request.getContextPath();
            String path = proName + UPLOAD_PATH;
            PrintWriter out = response.getWriter();
            String CKEditorFuncNum = request.getParameter("CKEditorFuncNum");
            String fileName = file.getOriginalFilename();
            String uploadContentType = file.getContentType();
            String expandedName = "";
            if (uploadContentType.equals("image/pjpeg")
                    || uploadContentType.equals("image/jpeg")) {
// IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg
                expandedName = ".jpg";
            } else if (uploadContentType.equals("image/png")
                    || uploadContentType.equals("image/x-png")) {
// IE6上传的png图片的headimageContentType是"image/x-png"
                expandedName = ".png";
            } else if (uploadContentType.equals("image/gif")) {
                expandedName = ".gif";
            } else if (uploadContentType.equals("image/bmp")) {
                expandedName = ".bmp";
            } else {
                out.println("<script type=\"text/javascript\">");
                out.println("window.parent.CKEDITOR.tools.callFunction("
                        + CKEditorFuncNum + ",'',"
                        + "'文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）');");
                out.println("</script>");
                return "";
            }
            if (file.getSize() > 1024 * 1024 * 2) {
                out.println("<script type=\"text/javascript\">");
                out.println("window.parent.CKEDITOR.tools.callFunction("
                        + CKEditorFuncNum + ",''," + "'文件大小不得大于2M');");
                out.println("</script>");
                return "";
            }
            DateFormat df = new SimpleDateFormat(DEFAULT_SUB_FOLDER_FORMAT_AUTO);
            fileName = df.format(new Date()) + expandedName;
            //file.transferTo(new File(path + "/" + fileName));

            String cloudFilePath = "/travel/" + travelEntity.getId()+"/images/" + fileName ;
            InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
                imageUrl = QCloudHelper.fileUpload(cloudFilePath, inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }

// 返回"图像"选项卡并显示图片 request.getContextPath()为web项目名
            out.println("<script type=\"text/javascript\">");
            out.println("window.parent.CKEDITOR.tools.callFunction("
                    + CKEditorFuncNum + ",'" + imageUrl
                    + "','')");
            out.println("</script>");

            inputStream.close();
            out.close();
            return "";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageUrl;
    }
}
