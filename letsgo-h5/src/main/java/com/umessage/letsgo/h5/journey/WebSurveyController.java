package com.umessage.letsgo.h5.journey;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.SurveyAnswerRequest;
import com.umessage.letsgo.domain.vo.journey.request.SurveyRequest;
import com.umessage.letsgo.domain.vo.journey.response.SurveyQuestionResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.h5.journey.helper.PrintPDFHelper;
import com.umessage.letsgo.service.api.journey.ISurveyDetailService;
import com.umessage.letsgo.service.api.journey.ISurveyService;
import com.umessage.letsgo.service.common.constant.Constant;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import com.umessage.letsgo.service.impl.journey.Helper.HtmlPdfHelper;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Api(value = "H5调查问卷页面", description = "关于调查问卷相关页面")
@Controller
@RequestMapping(value = "/web/survey")
public class WebSurveyController {

    private Logger logger = Logger.getLogger(WebSurveyController.class);
    @Resource
    private UserLoginHelper userLoginHelper;
    @Resource
    private ISurveyService surveyService;
    @Resource
    private ISurveyDetailService surveyDetailService;
    @Autowired
    protected VelocityEngine velocityEngine ;
    @Resource
    private HtmlPdfHelper htmlPdfHelper;
    @Resource
    private PrintPDFHelper printPDFHelper;

    @RequestMapping(value = "/getSurvey", method = RequestMethod.GET)
    @ApiOperation(value = "获得调查问卷【游客端】", notes = "获得调查问卷，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public String  getSurvey(@ApiParam(value = "腾讯群组id", required = true) @RequestParam(required = true) String txGroupId, Model model) {

        if(StringUtils.isEmpty(txGroupId)){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：腾讯群组id不能为空！");
        }
        // 1、获取当前登录的用户信息
        UserResponse user = userLoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return null;
        }
        SurveyRequest request=new SurveyRequest();
        request.setTxGroupId(txGroupId);
        request.setUserId(user.getUserEntity().getId());
        SurveyQuestionResponse response = surveyService.getSurvey(request);
        model.addAttribute("response", response);
        return "journey/surveyuser";
    }

    /**
     * 获取游客填写的问卷及签名
     * @param surveyUserId
     * @param model
     * @return
     */
    @RequestMapping(value = "/getSurveyWithSign", method = RequestMethod.GET)
    @ApiOperation(value = "获取游客填写的问卷及签名【领队端】", notes = "获取游客填写的问卷及签名，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    public String getSurveyWithSign(@RequestParam  Long surveyUserId, Model model) {
       // 1、获取当前登录的用户信息
        UserResponse user = userLoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return null;
        }
        SurveyQuestionResponse response = surveyService.getSurveyWithSign(surveyUserId);
        model.addAttribute("response", response);
        return "journey/sign";
    }
    /**
     * 确认问卷
     * @param
     * @return
     */
    @RequestMapping(value = "/confirmSurvey", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "确认问卷【领队端】", notes = "确认问卷，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "2.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse confirmSurvey(@ApiParam(value = "确认问卷ID多个分号给开", required = true)@RequestParam  String surveyUserId, HttpServletRequest request) {
        if(surveyUserId==null ){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "请求参数不能为空！");
        }
       /*
        String path = request.getContextPath();
        String basePath  = request.getScheme() +"://"+ request.getLocalAddr() +":"+ request.getLocalPort() + path + "/";//http://localhost:8080/openapi/
        logger.info("ip"+request.getLocalAddr()+",port"+request.getLocalPort()+",basePath"+basePath);
        String basePath  = Constant.H5_BASE_URL;
        */
        String basePath  = Constant.H5_BASE_URL_HTTP;
        logger.info("basePath *"+basePath);
        String [] ids = surveyUserId.split(";");
        if(ids == null || ids.length <= 0){
            return  CommonResponse.createInvalidParameterResponse("请求参数[surveyUserId]不合法");
        }

        CommonResponse response = new CommonResponse();
        //批量确认survey
        response =  surveyDetailService.batchConfirmSurvey(ids);
        if (response.getRetCode() != ErrorConstant.SUCCESS){
            response.setRetCode(response.getRetCode());
            response.setRetMsg("问卷确认失败");
        }
        for (int i = 0; i <ids.length ; i++) {
            //转PDF操作
            printPDFHelper.printPDF(ids[i]);

        }
        return response;
    }


    @RequestMapping(value = "/submitSurvey", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "提交调查问卷【游客端】", notes = "提交调查问卷，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse submitSurvey(@RequestBody SurveyAnswerRequest request) {

        if(request==null || request.getAnswerList()==null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "请求参数不能为空！");
        }
        // 1、获取当前登录的用户信息
        UserResponse user = userLoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return CommonResponse.createUserNotLoginResponse();
        }
        CommonResponse response = surveyService.submitSurvey(request);
        return response;
    }
}
