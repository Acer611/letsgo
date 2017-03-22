package com.umessage.letsgo.h5.secuirty;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.utils.ShaPasswordUtil;
import com.umessage.letsgo.domain.po.security.InvitationCodeEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.system.request.CheckRegisterRequest;
import com.umessage.letsgo.service.api.security.IInvitationCodeService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.system.IMessageService;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by zhajl on 16/9/26.
 */
@Api(value = "H5接口", description = "关于H5页面的相关操作")
@Controller
@RequestMapping(value = "/h5/register")
public class WebRegisterController {

    @Resource
    private IUserService userService;

    @Resource
    private IInvitationCodeService invitationCodeService;

    @Resource
    private IMessageService messageService;

    @RequestMapping(value = "/getInviteCode", method = RequestMethod.GET)
    @ApiOperation(value = "获取邀请码接口【H5页面】", notes = "获取邀请码接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public String getInviteCode(String inviteCode, Model model) {
        if (StringUtils.isEmpty(inviteCode)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：邀请码【inviteCode】不能为空！");
        }
        String checkcode = ShaPasswordUtil.encodePassword(inviteCode, "inviteCode");

        //获取邀请码
        InvitationCodeEntity invitationCodeEntity = invitationCodeService.select(inviteCode);
        if (null == invitationCodeEntity){
            throw new BusinessException(ErrorConstant.NOT_FOUND, "你的邀请码不正确!");
        }

        model.addAttribute("inviteCode",inviteCode);
        model.addAttribute("checkcode",checkcode);
        return "inviteUse/invite";
    }

    @RequestMapping(value = "/verifyInviteCodeAndUserRegister", method = RequestMethod.POST)
    @ApiOperation(value = "用户通过邀请的分享页面进行校验和注册接口【H5页面】", notes = "用户通过邀请的分享页面进行校验和注册接口")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse verifyInviteCodeAndUserRegister(@ApiParam(value = "1.captcha：验证码；2.captchaType：验证码作用范围【1：登录；2：设置密码；3：注册】；3.checkcode：校验码；4.invitationCode：邀请码；5.name：姓名；6.password：密码；7.phone：手机号；8.sex：性别", required = false) @RequestBody CheckRegisterRequest checkRegisterRequest) {

        if (StringUtils.isEmpty(checkRegisterRequest.getPhone())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：手机号【phone】不能为空！");
        }

        if (StringUtils.isEmpty(checkRegisterRequest.getInvitationCode())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：邀请码【invitationCode】不能为空！");
        }

        if (StringUtils.isEmpty(checkRegisterRequest.getCheckcode())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：校验码【checkcode】不能为空！");
        }

        if (StringUtils.isEmpty(checkRegisterRequest.getCaptcha())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：验证码【captcha】不能为空！");
        }

        if (StringUtils.isEmpty(checkRegisterRequest.getName())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：姓名【name】不能为空！");
        }

        CommonResponse commonResponse = userService.checkInviteCodeAndVerificationCode(checkRegisterRequest);
        //如果校验成功
        if (commonResponse.getRetCode()==ErrorConstant.SUCCESS){

            commonResponse = userService.registerUser(checkRegisterRequest);
        }

        return commonResponse;
    }


    @RequestMapping(value = "/sendValidCode", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "发送验证码短信【H5页面】", notes = "发送验证码短信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse sendValidCode(
            @ApiParam(value = "校验码", required = true) @RequestParam(value = "checkcode") String checkcode,
            @ApiParam(value = "邀请码", required = true) @RequestParam(value = "inviteCode") String inviteCode,
            @ApiParam(value = "手机号", required = true) @RequestParam(value = "phone") String phone,
            @ApiParam(value = "验证码的作用范围【1：登录；2：设置密码；3：注册】，非必填字段", required = false) @RequestParam(value = "scope", required = false) Integer scope,
            HttpServletRequest req
    ) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(checkcode)){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "校验码不能为空！");
        }

        if (StringUtils.isEmpty(inviteCode)){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "邀请码不能为空！");
        }

        String code = ShaPasswordUtil.encodePassword(inviteCode, "inviteCode");

        if (!checkcode.equals(code)){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "校验码不正确，不予以发送短信！");
        }

        if (org.apache.commons.lang.StringUtils.isBlank(phone)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "接收人手机号不能为空！");
        }

       /*
        String client = req.getParameter("client_id");
        // 验证用户是游客端还是领队端
        if ((scope !=null && scope !=3) && !userService.checkUserRole(client, phone)) {
            return CommonResponse.createUnauthorizedUser("用户身份不符，不予以发送短信！");
        }
        */

        return messageService.sendCheckCodeMessage(phone, scope);
    }


    @RequestMapping(value = "/sendPhoneValidCode", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "发送验证码短信通用方法", notes = "发送验证码短信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse sendPhoneValidCode(
            @ApiParam(value = "手机号", required = true) @RequestParam(value = "phone") String phone,
            @ApiParam(value = "验证码的作用范围【1：登录；2：设置密码；3：注册；4：第三账户发送验证码】，非必填字段", required = true) @RequestParam(value = "scope") Integer scope,
            HttpServletRequest req
    ) throws UnsupportedEncodingException {

        if (org.apache.commons.lang.StringUtils.isBlank(phone)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "接收人手机号不能为空！");
        }

        if (scope == null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "验证码的作用范围不能为空！");
        }

        return messageService.sendCheckCodeMessage(phone, scope);
    }


}
