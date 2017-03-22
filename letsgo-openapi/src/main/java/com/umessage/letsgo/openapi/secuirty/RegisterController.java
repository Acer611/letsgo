package com.umessage.letsgo.openapi.secuirty;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.system.request.CheckRegisterRequest;
import com.umessage.letsgo.domain.vo.system.request.CheckRequest;
import com.umessage.letsgo.domain.vo.system.request.RegisterRequest;
import com.umessage.letsgo.service.api.security.IUserService;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by wendy on 2016/8/24.
 */
@Api(value = "注册接口", description = "关于注册的相关操作")
@Controller
@RequestMapping(value = "/api/register")
public class RegisterController {

    @Resource
    private IUserService userService;

    @RequestMapping(value = "/verifyInvitationCodeCaptcha", method = RequestMethod.POST)
    @ApiOperation(value = "验证用户的注册信息接口【领队端】", notes = "验证用户的注册信息接口")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse verifyInvitationCodeCaptcha(@RequestBody CheckRequest request) {
        if (StringUtils.isEmpty(request.getPhone())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：手机号【phone】不能为空！");
        }

        if (StringUtils.isEmpty(request.getCaptcha())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：验证码【captcha】不能为空！");
        }

        return userService.checkInviteCodeAndSecurityCode(request);
    }

    @RequestMapping(value = "/leaderRegister", method = RequestMethod.POST)
    @ApiOperation(value = "领队用户的注册接口【领队端】", notes = "领队用户的注册接口")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse leaderRegister(@RequestBody RegisterRequest registerRequest) {

        if (StringUtils.isEmpty(registerRequest.getPhone())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：手机号【phone】不能为空！");
        }

        if (StringUtils.isEmpty(registerRequest.getName())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：姓名【name】不能为空！");
        }

        CommonResponse commonResponse = userService.registerLeader(registerRequest);
        return commonResponse;
    }


    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    @ApiOperation(value = "用户的注册接口", notes = "用户的注册接口")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse userRegister(@ApiParam(value = "phone:手机号,invitationCode:邀请码,name:姓名,password:密码,sex:性别{1:男，2：女}", required = false) @RequestBody RegisterRequest registerRequest) {

        if (StringUtils.isEmpty(registerRequest.getPhone())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：手机号【phone】不能为空！");
        }

        if (StringUtils.isEmpty(registerRequest.getName())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：姓名【name】不能为空！");
        }

        CommonResponse commonResponse = userService.registerUser(registerRequest);
        return commonResponse;
    }

}
