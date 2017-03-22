package com.umessage.letsgo.openapi.secuirty;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.system.request.LasterVerRequest;
import com.umessage.letsgo.domain.vo.system.respone.LasterVerResponse;
import com.umessage.letsgo.domain.vo.system.respone.NationDictionaryResponse;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.system.IDataVerService;
import com.umessage.letsgo.service.api.system.IMessageService;
import com.umessage.letsgo.service.api.system.INationDictionaryService;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Api(value = "系统接口", description = "关于系统的相关操作，包括获取更新基础数据、发送验证码短信的接口")
@Controller
@RequestMapping(value = "/api/system")
public class SystemController {
	@Resource
	private IMessageService messageService;
	@Resource
	private IDataVerService dataVerService;
	@Resource
	private INationDictionaryService nationDictionaryService;
	@Resource
	private IUserService userService;

	@RequestMapping(value = "/getLastestVerInfo", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取最新APP版本及基础数据版本【领队端、游客端】", notes = "获取最新APP版本及基础数据版本，用于APP获取最新应用版本信息、基础数据信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	public LasterVerResponse getLastestVerInfo(@ModelAttribute LasterVerRequest request) {
		if (request == null || StringUtils.isBlank(request.getDeviceType())) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "设备类型不能为空！");
		}

		if (request == null || StringUtils.isBlank(request.getModule())) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "模块参数不能为空！");
		}

		return dataVerService.getLasterVerInfo(request);
	}

	@RequestMapping(value = "/sendValidCode", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "发送验证码短信【领队端、游客端】", notes = "发送验证码短信")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	public CommonResponse sendValidCode(
			@ApiParam(value = "手机号", required = true) @RequestParam(value = "phone") String phone,
			@ApiParam(value = "验证码的作用范围【1：登录；2：设置密码；3：注册；4：第三账户发送验证码】，非必填字段", required = false) @RequestParam(value = "scope", required = false) Integer scope,
			HttpServletRequest req
	) throws UnsupportedEncodingException {
		if (StringUtils.isBlank(phone)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "接收人手机号不能为空！");
		}

		String client = req.getParameter("client_id");
		// 注册验证电话是否已经存在了
		if ((scope !=null && scope ==3) && userService.existPhone(phone)) {
			return CommonResponse.createExistPhoneResponse();
		}
       // 登陆验证用户是游客端还是领队端
		/*
		if ((scope !=null && scope ==1) && !userService.checkUserRole(client, phone)) {
			return CommonResponse.createUnauthorizedUser("用户身份不符，不予以发送短信！");
		}
		*/

		return messageService.sendCheckCodeMessage(phone, scope);
	}

	@RequestMapping(value = "/getAllNationDictionary", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取所有流通货币【游客端】", notes = "获取所有流通货币")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	public NationDictionaryResponse getAllNationDictionary() {

		return nationDictionaryService.getNationDictionaryList();
	}

}