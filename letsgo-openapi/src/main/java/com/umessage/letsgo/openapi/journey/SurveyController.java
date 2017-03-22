package com.umessage.letsgo.openapi.journey;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.SurveyAnswerRequest;
import com.umessage.letsgo.domain.vo.journey.response.SurveyDownloadResponse;
import com.umessage.letsgo.domain.vo.journey.response.SurveyResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.openapi.journey.helper.PrintPDFHelper;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by pw on 2016/9/12.
 */
@Api(value = "调查问卷接口", description = "关于调查问卷相关操作")
@Controller
@RequestMapping(value = "/api/survey")
public class SurveyController {

    private Logger logger = Logger.getLogger(SurveyController.class);

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

    /**
     * 发放问卷
     * @param teamId
     * @return
     */
    @RequestMapping(value = "/issueSurvey", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "发放问卷【领队端】", notes = "发放问卷，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "2.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse issueSurvey(@RequestParam String teamId) {
        CommonResponse response = surveyService.issueSurvey(teamId);
        return response;
    }

    /**
     * 确认问卷
     * @param surveyUserId
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
         logger.info("ip"+request.getLocalAddr()+",port"+request.getLocalPort());
         */
        String basePath  = Constant.H5_BASE_URL_HTTP;//http://localhost:8080/openapi/
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


    /**
     * 获取问卷未填写问卷及已填写问卷列表
     * @param txGroupid
     * @return
     */
    @RequestMapping(value = "/getSurveyFillStatus", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取问卷未填写问卷及已填写问卷列表【领队端】", notes = "获取问卷未填写问卷及已填写问卷列表，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "2.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public SurveyResponse getSurveyFillStatus(@ApiParam(value = "腾讯群组id", required = true)@RequestParam String txGroupid) {
        if(txGroupid==null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "请求参数不能为空！");
        }
        // 1、获取当前登录的用户信息
        UserResponse user = userLoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return SurveyResponse.createUserNotLoginResponse();
        }

        SurveyResponse response =  surveyService.getAllSurveyStatusById(user.getUserEntity().getId(), txGroupid);
        return response;
    }

    /**
     * 获取问卷未填写问卷及已填写问卷列表
     * @param txGroupid
     * @return
     */
    @RequestMapping(value = "/downloadSurvey", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "下载已确认问卷【领队端】", notes = "下载已确认问卷，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "2.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public SurveyDownloadResponse downloadSurvey(@ApiParam(value = "腾讯群组id", required = true)@RequestParam String txGroupid, HttpServletRequest request) {
        if(txGroupid==null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "请求参数不能为空！");
        }
        // 1、获取当前登录的用户信息
        UserResponse user = userLoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return SurveyDownloadResponse.createUserNotLoginResponse();
        }

        return surveyService.downloadSurvey(user.getUserEntity().getId(), txGroupid);
    }

}
