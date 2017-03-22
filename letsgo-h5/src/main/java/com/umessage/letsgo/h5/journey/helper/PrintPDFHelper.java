package com.umessage.letsgo.h5.journey.helper;

import com.umessage.letsgo.core.utils.CreatePdfUtil;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.SurveyQuestionResponse;
import com.umessage.letsgo.service.api.journey.ISurveyService;
import com.umessage.letsgo.service.impl.journey.Helper.HtmlPdfHelper;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaofei on 2017/1/20.
 */
@Service
@EnableAsync
public class PrintPDFHelper {


    private Logger logger = Logger.getLogger(PrintPDFHelper.class);

    @Resource
    private ISurveyService surveyService;

    @Resource
    private HtmlPdfHelper htmlPdfHelper;

    @Autowired
    protected VelocityEngine velocityEngine ;

    @Async("htmlPdfExecutor")
    public CommonResponse printPDF(String surveyDetailId) {
        CommonResponse response = new CommonResponse();
        //获取问卷的信息
        logger.info("获取问卷的信息");
        SurveyQuestionResponse surveyResponse = new SurveyQuestionResponse();
        surveyResponse = surveyService.getSurveyWithSign(Long.parseLong(surveyDetailId));
        //把数据转化为velocity工具类所需要的Map的格式
        Map dataMap =  new HashMap<>();
        dataMap.put("title",surveyResponse.getTitle());
        dataMap.put("questionList",surveyResponse.getQuestionList());
        dataMap.put("confirmStatus",surveyResponse.getConfirmStatus());
        logger.info("把问卷信息转换为Map 完成");

        //生成签名的图片
        logger.info("生成签名的图片");
        String base64Str = CreatePdfUtil.generateBase64Code(surveyResponse.getSignUrl());
        base64Str = "data:image/jpg;base64," + base64Str;
        dataMap.put("signUrl",base64Str);

        //生成签名的图片
        logger.info("生成标题的图片");
        StringBuilder titleImageBuilder  = new StringBuilder(CreatePdfUtil.titleImage1);
        titleImageBuilder.append(CreatePdfUtil.titleImage2).append(CreatePdfUtil.titleImage3);
        titleImageBuilder.append(CreatePdfUtil.titleImage4).append(CreatePdfUtil.titleImage5);
        titleImageBuilder.append(CreatePdfUtil.titleImage6).append(CreatePdfUtil.titleImage7);
        dataMap.put("titleImage",titleImageBuilder.toString());

        //用velocity模板生成html
        logger.info("利用velocity模板生成html源文件");
        String htmlCode = getHtmlCodeByVelocity(dataMap);

        //html转化为PDF
        logger.info("html文件转化为PDF文件");
        htmlPdfHelper.htmlToPDF(htmlCode,surveyDetailId,surveyResponse.getSignUrl());
        response.setRetMsg("转pdf完成, 问卷id:" + surveyDetailId);
        System.out.println("正在打印的问卷的SurveyId 是：" + surveyDetailId);
        return  response;
    }

    public  String getHtmlCodeByVelocity(Map dataMap){
        String velocityPath ="../views/journey/signPdf.vm";
        String htmlCode = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,velocityPath,"utf-8",dataMap);
        return htmlCode;
    }
}
