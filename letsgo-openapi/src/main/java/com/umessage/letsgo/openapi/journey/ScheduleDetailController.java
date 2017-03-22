package com.umessage.letsgo.openapi.journey;

import javax.annotation.Resource;

import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.SurveyRequest;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.journey.ISurveyDetailService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.vo.journey.response.CurrentTimeZoneResponse;
import com.umessage.letsgo.service.api.journey.IScheduleDetailsService;

@Api(value = "行程明细接口", description = "关于行程明细的相关操作")
@Controller
@RequestMapping(value = "/api/scheduleDetail")
public class ScheduleDetailController {
	
	 @Resource
	 private IScheduleDetailsService scheduleDetailsService;
    @Resource
    private UserLoginHelper userLoginHelper;
    @Resource
    private ISurveyDetailService surveyDetailService;
    @Resource
    private ITeamService teamService;

	@RequestMapping(value = "/getCurrentTimeZoneInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取今日目的地时区【游客端】", notes = "获取行程中今日目的地时区，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CurrentTimeZoneResponse getCurrentTimeZoneInfo(
            @ApiParam(value = "团队ID", required = true) @RequestParam(required = true) String teamId) {

        if (StringUtils.isEmpty(teamId)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【teamId】不能为空！");
        }

        // 获取团队ID
        TeamEntity teamEntity = teamService.getTeamByTXGroupId(teamId);
        Long id = 0L;
        try {
            id = teamEntity.getId();
        } catch (NumberFormatException e) {
            throw new BusinessException(ErrorConstant.INVALID_PARAMETER, "参数：团队ID【teamId】不合法！");
        }

        return scheduleDetailsService.getCurrentZoneInfo(id);
    }

    /*
	@RequestMapping(value = "/getSpotsList", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取行程中的景点信息【游客端】", notes = "获取行程中的景点信息，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public ScenicInfoResponse getSpotsList(
            @ApiParam(value = "行程ID", required = true) @RequestParam(required = true) Long scheduleId) {

        if (StringUtils.isEmpty(scheduleId)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：行程ID【scheduleId】不能为空！");
        }
      //获取当前日期的string格式
        String currentDate = DateUtils.getReqDate();
        return scheduleDetailsService.getSpotsInfoByScheduleId(scheduleId,currentDate);
    }
    */

    @RequestMapping(value = "/submitSignUrl", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "提交签名【游客端】", notes = "提交签名，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse submitSignUrl(@ApiParam(value = "游客签名地址", required = true)@RequestParam  String signUrl, @ApiParam(value = "腾讯云群组ID", required = true)@RequestParam  String txGroupId) {

        if(StringUtils.isEmpty(signUrl)|| StringUtils.isEmpty(txGroupId)){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "请求参数不能为空！");
        }
        // 1、获取当前登录的用户信息
        UserResponse user = userLoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return CommonResponse.createUserNotLoginResponse();
        }
        SurveyRequest request = new SurveyRequest();
        request.setUserId(user.getUserEntity().getId());
        request.setTxGroupId(txGroupId);
        request.setSignUrl(signUrl);
        CommonResponse response = surveyDetailService.submitSignUrl(request);
        return response;
    }
}
