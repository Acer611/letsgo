package com.umessage.letsgo.service.impl.journey.Helper;

import com.umessage.letsgo.core.utils.IDUtil;
import com.umessage.letsgo.domain.vo.journey.response.SurveyQuestionResponse;
import com.umessage.letsgo.service.api.journey.ISurveyService;
import com.umessage.letsgo.service.common.constant.Constant;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by myz on 2016/10/19.\
 * Velocity生成html
 */

@Service
public class VelocityHtmlHelper {

    private  Logger logger = LoggerFactory.getLogger(VelocityHtmlHelper.class);

    @Resource
    private ISurveyService surveyService;

    //参数为Long surveyUserId
    public  String velocityCreateHtml(String surveyUserId, String basePath) {

        //file初始化失败 webapp初始
        VelocityEngine ve = new VelocityEngine();

        //Properties p = new Properties();
        //p.setProperty("resource.loader", "file");
        //p.setProperty("file.resource.loader.class", "org.apache.velocity.tools.view.WebappResourceLoader");
        //p.setProperty("file.resource.loader.path", basePath + "/WEB-INF/views/");
        //p.setProperty("velocimacro.library", "VM_global_library.vm");
        ve.setProperty("file.resource.loader.path", basePath + "/WEB-INF/views/");
        //主要作用是控制页面乱码问题
        ve.setProperty("input.encoding","utf-8");
        ve.setProperty("output.encoding","utf-8");
        ve.init();

        //File file = new File( " ../WEB-INF/views/journey/sign.vm" );
        //String absolutePath = file.getAbsolutePath();
        //logger.info("absolutePath"+absolutePath);
        //Template template = ve.getTemplate(absolutePath);
        Template template = ve.getTemplate("journey/sign.vm");
        //初始化上下文
        VelocityContext context = new VelocityContext();
        SurveyQuestionResponse response = new SurveyQuestionResponse();
        response = surveyService.getSurveyWithSign(Long.parseLong(surveyUserId));
        //添加数据到上下文中 查数据依据surveyUserId 把返回值发送到html
        context.put("response", response);
        context.put("STATIC_URL", Constant.API_BASE_URL);
        //生成html页面
        String htmlUrl = null;
        try {
//            String folder = System.getProperty("java.io.tmpdir");
//            htmlUrl = folder +"\\"+ IDUtil.uuid() + ".html";
//            PrintWriter pw = new PrintWriter(htmlUrl);
//            logger.info("htmlUrl"+htmlUrl);
//            template.merge(context, pw);
//            //关闭流
//            pw.close();
            String folder = System.getProperty("java.io.tmpdir");
            htmlUrl = folder +"\\"+ IDUtil.uuid() + ".html";
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(htmlUrl), "UTF-8"));
            logger.info("VelocityHtmlHelper htmlUrl"+htmlUrl);
            template.merge(context, pw);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlUrl;
    }


}

