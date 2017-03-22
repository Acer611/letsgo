package com.umessage.letsgo.openapi.activity;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.vo.activity.response.CallResponse;
import com.umessage.letsgo.domain.vo.activity.response.LocationRespone;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import com.umessage.letsgo.service.api.activity.ICallDetailService;
import com.umessage.letsgo.service.api.activity.ICallService;
import com.umessage.letsgo.service.api.activity.ILocationService;
import com.umessage.letsgo.service.api.team.ITeamService;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Api(value = "点名接口", description = "关于点名的相关操作，包括创建点名，获取点名详情等接口")
@Controller
@RequestMapping(value = "/api/call")
public class CallController {
    @Resource
    private ICallService callService;
    @Resource
    private ICallDetailService callDetailService;
    @Resource
    private ILocationService locationService;
    @Resource
    private ITeamService teamService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;

    @RequestMapping(value = "/createCall", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "创建点名【领队端】", notes = "创建点名，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CallResponse addCall(
            @ApiParam(value = "团队ID", required = true) @RequestParam(required = true) String teamId,
            @ApiParam(value = "坐标经度", required = false) @RequestParam(required = false) Double lng,
            @ApiParam(value = "坐标纬度", required = false) @RequestParam(required = false) Double lat) throws Exception {

        if (teamId == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【teamId】不能为空！");
        }
        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return CallResponse.createUserNotLoginResponse();
        }
        //2、没有领队位置，手动签到。
        if (lng == null || lat == null) {
            return callService.createManualCall(teamId,user.getUserEntity());
        }
        if(lng == 0 && lat == 0){
            return CallResponse.createInvalidParameterResponse("(0,0)无效经纬度坐标");
        }
        // 3、提交领队用户的位置信息
        LocationRespone locationRespone = locationService.createLocation(user.getUserEntity().getId(), lng, lat);
        return callService.createCall(teamId, user.getUserEntity(),locationRespone.getLocationEntity());

    }

    @RequestMapping(value = "/setSign", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "设置未到/签到【领队端】", notes = "更改点名状态，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。点名状态status可为【0：未签到；1：已签到】")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse setSign(
            @ApiParam(value = "点名明细ID", required = true) @RequestParam(required = true) Long cdid,
            @ApiParam(value = "签到状态:1签到，0未签到", required = true) @RequestParam(required = true) Integer status) {

        if (null == cdid || "".equals(cdid)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：点名明细ID【cdid】不能为空！");
        }

        if (null == status || "".equals(status)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：签到状态【status】不能为空！");
        }

        return callDetailService.setSignStatus(cdid, status);

    }

    @RequestMapping(value = "/finishCall", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "结束/完成点名【领队端】", notes = "结束/完成点名，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse finishCall(
            @ApiParam(value = "点名ID", required = true) @RequestParam(required = true) Long id) {

        if (null == id || "".equals(id)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：点名ID【id】不能为空！");
        }

        return callService.finishCall(id);

    }

    @RequestMapping(value = "/getCallDetailLsit", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取点名详情列表【领队端】", notes = "获取点名详情列表，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CallResponse getCallDetailLsit(
            @ApiParam(value = "点名ID", required = true) @RequestParam(required = true) Long id) {

        if (null == id || "".equals(id)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：点名ID【id】不能为空！");
        }

        return callService.getCallDetailList(id);

    }
}
