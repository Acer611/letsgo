package com.umessage.letsgo.openapi.secuirty;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleUnreadResponse;
import com.umessage.letsgo.domain.vo.notice.request.InformationRequest;
import com.umessage.letsgo.domain.vo.notice.respone.InformationResponse;
import com.umessage.letsgo.domain.vo.security.request.DeviceRequest;
import com.umessage.letsgo.domain.vo.security.respone.*;
import com.umessage.letsgo.domain.vo.system.request.UnreadAndUnconfirmedRequest;
import com.umessage.letsgo.domain.vo.system.respone.RateResult;
import com.umessage.letsgo.domain.vo.system.respone.UnreadAndUnconfirmedResponse;
import com.umessage.letsgo.domain.vo.system.respone.YahooRateResponse;
import com.umessage.letsgo.service.api.notice.INoticeDetailService;
import com.umessage.letsgo.service.api.security.ITagsService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.security.IUserTagService;
import com.umessage.letsgo.service.api.system.IMessageService;
import com.umessage.letsgo.service.api.system.IYahooRateService;
import com.umessage.letsgo.service.api.team.ILeaderService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;

import io.swagger.annotations.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Api(value = "用户接口", description = "关于用户的相关操作，包括获取用户详情、获取重要消息、更新用户设备、设置修改密码等接口")
@Controller
@RequestMapping("/api/user")
public class UserController {
	@Resource
	private IUserService userServiceImpl;
	@Resource
	private IMessageService messageServiceImpl;
	@Resource
	private UserLoginHelper oauth2LoginHelper;
	@Resource
	private IYahooRateService yahooRateService;
	@Resource
	private IMemberService memberService;
	@Resource
	private INoticeDetailService noticeDetailService;

	@Resource
	private ILeaderService leaderService;
	@Resource
	private IUserTagService userTagService;
	@Resource
	private ITagsService tagsService;
	/**
	 * 获取用户的详情
	 * @return
     */
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取当前登录用户的详情【领队端、游客端】", notes = "获取当前登录用户的详情，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	public UserResponse getUserInfo() {
		return oauth2LoginHelper.getLoginUser();
	}

	/**
	 * 获取用户的详情
	 * @return
	 */
	@RequestMapping(value = "/getUserInfo2", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取私聊用户的详情【领队端、游客端】", notes = "获取私聊用户的详情，用于私聊中查看成员详情，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	public UserResponse getUserInfo2(
			@ApiParam(value = "腾讯云通讯的成员ID，就是SDK里的peer字段", required = false) @RequestParam(required = false) String identifier) {

		if (StringUtils.isEmpty(identifier)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：用户名【identifier】不能为空！");
		}

		return userServiceImpl.getCurrentUser(identifier);
	}

	/**
	 * 获取私聊用户的详情/通讯录好友详情
	 * @return
	 */
	@RequestMapping(value = "/getUserInfoByIdentifier", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取私聊用户的详情/通讯录好友详情【领队端、游客端】", notes = "获取私聊用户的详情，用于私聊中查看成员详情，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "2.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	public UserResponse getUserInfoByUsername(
			@ApiParam(value = "腾讯云通讯的成员ID，就是SDK里的peer字段", required = false) @RequestParam(required = false) String identifier) {

		if (StringUtils.isEmpty(identifier)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：用户名【identifier】不能为空！");
		}

		UserResponse userResponse = oauth2LoginHelper.getLoginUser();

		return userServiceImpl.getUserByUsername(identifier, userResponse.getUserEntity().getId());
	}

	/**
	 * 验证用户手机短信验证码
	 * 
	 * @return
	 */
	@RequestMapping(value = "/validateMobileCode", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "验证用户手机短信验证码，该接口暂时没有用", notes = "验证用户手机短信验证码，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	public CommonResponse validateMobileCode(@ApiParam(value = "手机号", required = true) @RequestParam String phone,
			@ApiParam(value = "验证码", required = true) @RequestParam String code) {
		UserResponse userResponse = oauth2LoginHelper.getLoginUser();
		if (userResponse.getRetCode() != ErrorConstant.SUCCESS) {
			return userResponse;
		}

		UserEntity userEntity = userResponse.getUserEntity();
		CommonResponse response = messageServiceImpl.validateMobileCode(phone, code, userEntity.getId());
		if (response.getRetCode() == ErrorConstant.SUCCESS) {
			// 成功，则更新用户表中手机号
			userEntity.setPhone(phone);
			userServiceImpl.update(userEntity);
		}
		return response;
	}

	@RequestMapping(value = "/updateUserDevice", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "更新用户设备信息【领队端、游客端】", notes = "更新用户设备信息，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。调用该接口更新用户使用设备信息，返回腾讯云账号信息")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	public TXAccountResponse updateUserDevice(@ApiParam(value = "设备信息", required = true) @RequestBody DeviceRequest request, HttpServletRequest req) {
		/*String client = req.getParameter("client_id");
		String sign = req.getParameter("sign");*/
		// 1、获取当前登录的用户信息
		UserResponse user = oauth2LoginHelper.getLoginUser();
		if (user.getRetCode() != ErrorConstant.SUCCESS){
			return TXAccountResponse.createUserNotLoginResponse();
		}

		TXAccountResponse response = userServiceImpl.updateDevice(request, user.getUserEntity());
		return response;
	}

	@RequestMapping(value = "/setPassword", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "设置登录密码【领队端、游客端】", notes = "设置登录密码")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	public CommonResponse setPassword(
			@ApiParam(value = "密码", required = true) @RequestParam(required = true) String password,
			@ApiParam(value = "验证码", required = true) @RequestParam(required = true) String code) {

		if (StringUtils.isBlank(password)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：密码【password】不能为空！");
		}
		if (StringUtils.isBlank(code)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：验证码【code】不能为空！");
		}

		// 1、获取当前登录的用户信息
		UserResponse user = oauth2LoginHelper.getLoginUser();
		if (user.getRetCode() != ErrorConstant.SUCCESS){
			return user;
		}

		return userServiceImpl.setPassword(user.getUserEntity() , password, code);
	}

	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "修改登录密码【领队端、游客端】", notes = "修改登录密码")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	public CommonResponse modifyPassword(
			@ApiParam(value = "旧密码", required = true) @RequestParam(required = true) String oldPassword,
			@ApiParam(value = "新密码", required = true) @RequestParam(required = true) String newPassword) {

		if (StringUtils.isBlank(oldPassword)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：旧密码【oldPassword】不能为空！");
		}
		if (StringUtils.isBlank(newPassword)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：新密码【newPassword】不能为空！");
		}
		// 1、获取当前登录的用户信息
		UserResponse user = oauth2LoginHelper.getLoginUser();
		if (user.getRetCode() != ErrorConstant.SUCCESS){
			return user;
		}

		return userServiceImpl.modifyPassword(user.getUserEntity(), oldPassword, newPassword);
	}

	@RequestMapping(value = "/getInformationList", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取重要消息接口【领队端、游客端】", notes = "获取重要消息,需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。返回最新一条的通知、集合、公告，及未读/未确认数量。")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	public InformationResponse getInformationList(
			@ApiParam(value = "上一次的最新通知ID", required = false) @RequestParam(required = false) Long lastNoticeId,
			@ApiParam(value = "上一次的最新集合ID", required = false) @RequestParam(required = false) Long lastGather,
			@ApiParam(value = "上一次的最新公告ID", required = false) @RequestParam(required = false) Long lastAnnouncementId) {

		// 1、获取当前登录的用户信息
		UserResponse user = oauth2LoginHelper.getLoginUser();
		if (user.getRetCode() != ErrorConstant.SUCCESS){
			return InformationResponse.createUserNotLoginResponse();
		}

		InformationRequest request = new InformationRequest();
		request.setUserId(user.getUserEntity().getId());
		request.setLastNoticeId(lastNoticeId);
		request.setLastGatherId(lastGather);
		request.setLastAnnouncementId(lastAnnouncementId);
		return userServiceImpl.getInformationResponse(request);
	}

	@RequestMapping(value = "/getUnreadAndUnconfirmedCount", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取未读/未确认消息数量接口【领队端、游客端】", notes = "获取未读/未确认消息数量接口，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。返回最新一条的通知、集合、公告，及未读/未确认数量。")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	public UnreadAndUnconfirmedResponse getUnreadAndUnconfirmedCount(
			@ApiParam(value = "需要获取未读/未确认数量的分类") @RequestBody UnreadAndUnconfirmedRequest request) {

		// 获取当前用户
		UserResponse user = oauth2LoginHelper.getLoginUser();
		if (user.getRetCode() != ErrorConstant.SUCCESS){
			return UnreadAndUnconfirmedResponse.createUserNotLoginResponse();
		}

		UnreadAndUnconfirmedResponse response = userServiceImpl.getUnreadCountAndUnconfirmedCount(request.getTypes(), request.getTeamId(), user.getUserEntity().getId());

		return response;
	}

	@RequestMapping(value = "/sendInvitationMessage", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "领队给未加入的成员发送邀请加入短信【领队端】", notes = "领队给未加入的成员发送邀请加入短信，如果成员未加入且没有手机号，需要传入手机号，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	public CommonResponse sendInvitationMessage1 (
			@ApiParam(value = "被邀请人的成员ID", required = true) @RequestParam(required = true) Long memberId,
			@ApiParam(value = "手机号，如果成员没有手机号信息，则需要传入手机号信息【新】", required = false) @RequestParam(required = false) String phone) throws UnsupportedEncodingException {
		if (memberId == null) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：被邀请人的成员ID【memberId】不能为空！");
		}

		return userServiceImpl.sendInvitationMessage(memberId, phone);
	}

	@RequestMapping(value = "/sendInvitationMessage2", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "游客给同组未加入的成员发送邀请加入短信【游客端】", notes = "游客给同组未加入的成员发送邀请加入短信，如果成员未加入且没有手机号，需要传入手机号需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	public CommonResponse sendInvitationMessage2(
			@ApiParam(value = "被邀请人的成员ID", required = true) @RequestParam(value = "memberId") Long memberId,
			@ApiParam(value = "手机号，如果成员没有手机号信息，则需要传入手机号信息【新】", required = false) @RequestParam(required = false) String phone) throws UnsupportedEncodingException {
		if (memberId == null) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：被邀请人的成员ID【memberId】不能为空！");
		}

		// 获取当前登录的用户信息
		UserResponse user = oauth2LoginHelper.getLoginUser();
		if (user.getRetCode() != ErrorConstant.SUCCESS){
			return InformationResponse.createUserNotLoginResponse();
		}

		return userServiceImpl.sendInvitationMessage2(memberId, phone, user.getUserEntity());
	}

	@RequestMapping(value = "/calculationRates", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取换算货币汇率【游客端】", notes = "获取换算货币汇率，返回的汇率值是以美元为准基数的，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	public YahooRateResponse calculationRates(
			@ApiParam(value = "货币三字码") @RequestBody List<String> currencies) {

		List<RateResult> moneys = yahooRateService.calculationRates(currencies);

		YahooRateResponse rateResponse = new YahooRateResponse();
		rateResponse.setRates(moneys);
		return rateResponse;
	}

	@RequestMapping(value = "/getAuthority", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取用户在某个团内的身份，管理权限及这个团队的出行状态【游客端】", notes = "获取用户在某个团内的身份，管理权限及这个团队的出行状态，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	private UserAuthorityResponse getAuthority(
			@ApiParam(value = "团队ID", required = true) @RequestParam(required = true) String teamId) {

		if (StringUtils.isBlank(teamId)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【teamId】不能为空！");
		}

		// 获取当前登录的用户信息
		UserResponse user = oauth2LoginHelper.getLoginUser();
		if (user.getRetCode() != ErrorConstant.SUCCESS){
			return UserAuthorityResponse.createUserNotLoginResponse();
		}

		UserAuthorityResponse response = memberService.getUserAuthority(teamId, user.getUserEntity().getId());
		return response;
	}
	@RequestMapping(value = "/getFrendTxId", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取用户的腾讯id", notes = "获取用户在某个团内的身份，管理权限及这个团队的出行状态，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	private UserResponse getFrendTxId(@RequestParam String phone) {
		// 获取当前登录的用户信息
		UserResponse user = oauth2LoginHelper.getLoginUser();
		if (user.getRetCode() != ErrorConstant.SUCCESS){
			return UserResponse.createUserNotLoginResponse();
		}
		UserResponse response = userServiceImpl.selectByphone(phone);
		return response;
	}
	
	@RequestMapping(value = "/uploadFacePhoto", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "上传用户头像【 领队端、游客端】", notes = "上传用户头像，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	private CommonResponse uploadFacePhoto(
			@ApiParam(value = "头像URL", required = true) @RequestParam(required = true) String photoUrl) {

		if (StringUtils.isBlank(photoUrl)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：头像地址【photoUrl】不能为空！");
		}

		// 获取当前登录的用户信息
		UserResponse user = oauth2LoginHelper.getLoginUser();
		if (user.getRetCode() != ErrorConstant.SUCCESS){
			return UserAuthorityResponse.createUserNotLoginResponse();
		}

		CommonResponse response = userServiceImpl.uploadFacePhoto(user.getUserEntity(),photoUrl);
		return response;
	}

	@RequestMapping(value = "/getScheduleUnread", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "行程主界面小红点统计接口【领队端、游客端】", notes = "行程主界面小红点统计接口")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	public ScheduleUnreadResponse getScheduleUnread() {

		// 获取当前登录的用户信息
		UserResponse user = oauth2LoginHelper.getLoginUser();
		if (user.getRetCode() != ErrorConstant.SUCCESS){
			ScheduleUnreadResponse response = new ScheduleUnreadResponse();
			response.setRetCode(user.getRetCode());
			response.setRetMsg(user.getRetMsg());
			return response;
		}


		ScheduleUnreadResponse response = noticeDetailService.getScheduleUnread(user.getUserEntity().getId());
		return response;
	}


	@RequestMapping(value = "/modifyUser", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "修改领队个人资料【领队端】", notes = "修改领队个人资料，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	private CommonResponse modifyUser(@ApiParam(value = "修改类型：1、自我评价标签 2、擅长语种3、擅长旅游线路", required = true) @RequestParam String type,
			@ApiParam(value = "内容，如果传入的是自我评价标签，需要传入JSON字符串（例如：{\"evaluationTag\":[\"tag1\",\"tag2\",\"tag3\",\"tag4\",\"tag5\"]}）", required = true) @RequestParam String content) {
		CommonResponse commonResponse;
		try {
			UserResponse user = oauth2LoginHelper.getLoginUser();
			Long userId = user.getUserEntity().getId();
			commonResponse = userServiceImpl.updateUserContent(userId,type,content);
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse = new CommonResponse(ErrorConstant.INTERNAL_SERVER_ERROR,"修改领队个人资料失败");
		}
	 	
	 	return commonResponse;
	}

	@RequestMapping(value = "/saveTag", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "保存一个标签下的多个好友【 领队端、游客端】", notes = "保存一个标签下的多个好友，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	private CommonResponse saveTag(
			@ApiParam(value = "标签名称", required = true) @RequestParam(required = true) String tagName, 
			@ApiParam(value = "需要打上该标签用户,多个以;分割", required = true) @RequestParam(required = true) String userIds) {

		if (StringUtils.isBlank(tagName)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：标签名称【tagName】不能为空！");
		}
		if (StringUtils.isBlank(userIds)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：标签用户列表【userIds】不能为空！");
		}

		// 获取当前登录的用户信息
		UserResponse user = oauth2LoginHelper.getLoginUser();
		if (user.getRetCode() != ErrorConstant.SUCCESS){
			return UserAuthorityResponse.createUserNotLoginResponse();
		}

		CommonResponse response = userServiceImpl.saveTag(user.getUserEntity().getId(),tagName,userIds);
		return response;
	}
	
	@RequestMapping(value = "/queryTagList", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取当前用户所有通讯录标签及好友信息【 领队端、游客端】", notes = "获取用户自定义标签，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	private TagListResponse queryTagList() {

		// 获取当前登录的用户信息
		UserResponse user = oauth2LoginHelper.getLoginUser();
		if (user.getRetCode() != ErrorConstant.SUCCESS) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "当前用户登录失效");
		}

		TagListResponse response = userServiceImpl.queryTagList(user.getUserEntity().getId());
		return response;
	}

	@RequestMapping(value = "/updateTag", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "修改标签下的多个好友【 领队端、游客端】", notes = "修改一个标签下的多个好友，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	private CommonResponse updateTag(
			@ApiParam(value = "标签名称", required = true) @RequestParam(required = true) String tagName,
			@ApiParam(value = "需要打上该标签用户,多个以;分割", required = true) @RequestParam(required = true)String userIds,@ApiParam(value = "标签Id", required = true)@RequestParam(required = true)Long tagId) {

		if (StringUtils.isBlank(tagName)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：标签名称【tagName】不能为空！");
		}
		if (StringUtils.isBlank(userIds)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：标签用户列表【userIds】不能为空！");
		}
		if (tagId==null) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：标签Id【tagId】不能为空！");
		}
		// 获取当前登录的用户信息
		UserResponse user = oauth2LoginHelper.getLoginUser();
		if (user.getRetCode() != ErrorConstant.SUCCESS){
			return UserAuthorityResponse.createUserNotLoginResponse();
		}
		CommonResponse response = userServiceImpl.saveTag(user.getUserEntity().getId(),tagName,userIds);
		//修改成功删除旧数据
		if(response.getRetCode()==0) {
			userTagService.deleteUserTagByTagId(tagId);
			tagsService.delete(tagId);
		}
		return response;
	}

	@RequestMapping(value = "/toBindingFirst", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "用户绑定微信接口", notes = "用户绑定微信接口")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	private UserBindingWxResponse toBindingFirst(
			@ApiParam(value = "用户微信openId", required = true) @RequestParam(required = true) String openId,
			@ApiParam(value = "用户微信utoken", required = true) @RequestParam(required = true)String utoken,
			@ApiParam(value = "用户手机号phone", required = true)@RequestParam(required = true)String phone,
	        @ApiParam(value = "验证码code", required = true)@RequestParam(required = true)String code,
			@ApiParam(value = "类型type：0微信；1微博", required = true)@RequestParam(required = true)String type)
	 {
		if (StringUtils.isBlank(openId)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：用户微信openId不能为空！");
		}
		if (StringUtils.isBlank(utoken)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：用户微信utoken不能为空！");
		}
		if (StringUtils.isBlank(phone)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：用户手机号phone不能为空！");
		}
		 if (StringUtils.isBlank(code)) {
			 throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：验证码code不能为空！");
		 }
		 if (StringUtils.isBlank(type)) {
			 throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：类型type不能为空！");
		 }
		 if("0".equals(type)) {
			 return userServiceImpl.toBindingFirst(openId, utoken, phone, code);
		 }else{
			 return null;
		 }
	}

	@RequestMapping(value = "/toBindingSecond", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "用户绑定微信完善资料接口", notes = "用户绑定微信完善资料接口")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	private UserBindingWxResponse toBindingSecond(
			@ApiParam(value = "用户微信openId", required = true) @RequestParam(required = true) String openId,
			@ApiParam(value = "用户微信utoken", required = true) @RequestParam(required = true)String utoken,
			@ApiParam(value = "用户手机号phone", required = true)@RequestParam(required = true)String phone,
			@ApiParam(value = "用户name", required = true) @RequestParam(required = true)String name,
			@ApiParam(value = "用户邀请码invitationCode")@RequestParam(required = false)String invitationCode,
			@ApiParam(value = "用户性别sex：1男；2女", required = true)@RequestParam(required = true)Integer sex,
			@ApiParam(value = "类型type：0微信；1微博", required = true)@RequestParam(required = true)String type)
	{
		if (StringUtils.isBlank(openId)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：用户微信openId不能为空！");
		}
		if (StringUtils.isBlank(utoken)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：用户微信utoken不能为空！");
		}
		if (StringUtils.isBlank(phone)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：用户手机号phone不能为空！");
		}
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：姓名name不能为空！");
		}
		if (sex==null) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：性别sex不能为空！");
		}
		if (StringUtils.isBlank(type)) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：类型type不能为空！");
		}
		if("0".equals(type)) {
			return userServiceImpl.toBindingSecond(openId,utoken,phone,name,sex,invitationCode);
		}else{
			return null;
		}
	}

}
