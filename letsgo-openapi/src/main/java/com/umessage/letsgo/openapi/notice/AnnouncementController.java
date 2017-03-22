package com.umessage.letsgo.openapi.notice;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.notice.request.AnnouncementRequest;
import com.umessage.letsgo.domain.vo.notice.request.DetailParamRequest;
import com.umessage.letsgo.domain.vo.notice.respone.AnnouncementResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.notice.INoticeService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import com.umessage.letsgo.service.api.notice.IAnnouncementService;
import com.umessage.letsgo.service.api.team.ITeamService;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "公告接口", description = "关于公告的相关操作，包括新建公告，获取公告列表的接口")
@Controller
@RequestMapping(value = "/api/announcement")
public class AnnouncementController {
	@Resource
	private IAnnouncementService announcementService;
	@Resource
	private UserLoginHelper oauth2LoginHelper;
	@Resource
	private INoticeService noticeService;

	@RequestMapping(value = "/addAnnouncement", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "新建公告【领队端】", notes = "新建公告,需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。" )
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query") })
	public CommonResponse addAnnouncement(
			@RequestBody AnnouncementRequest announcementRequest) throws Exception {

		if (announcementRequest.getTeamId() == null) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:团队ID【teamId】不能为空！");
		}
		if (StringUtils.isEmpty(announcementRequest.getTitle())) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:标题【title】不能为空！");
		}
		if (StringUtils.isEmpty(announcementRequest.getContent())) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:内容【content】不能为空！");
	}
		// 1、获取当前登录的用户信息
		UserResponse user = oauth2LoginHelper.getLoginUser();
		if (user.getRetCode() != ErrorConstant.SUCCESS){
			return user;
		}
		announcementRequest.setUserId(user.getUserEntity().getId());

		return announcementService.saveAnnouncement(announcementRequest);

	}

	@RequestMapping(value = "/getAnnouncementList", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取公告列表【领队端、游客端】", notes = "获取公告列表，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。当查看团队中公告列表时，必须传入teamId，否则获取的还是全局公告列表")
	@ApiImplicitParams( {
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query") })
	public AnnouncementResponse getAnnouncementList(
			@ApiParam(value = "团队ID") @RequestParam(required=false) String teamId,
			@ApiParam(value = "已读上报字段，1:已读，0:未读") @RequestParam(required = false) Integer markRead) {

		// 1、获取当前登录的用户信息
		UserResponse user = oauth2LoginHelper.getLoginUser();
		if (user.getRetCode() != ErrorConstant.SUCCESS){
			return AnnouncementResponse.createUserNotLoginResponse();
		}

		AnnouncementResponse response =announcementService.getAnnouncementList(teamId, user.getUserEntity().getId());
		if (markRead !=null && markRead == 1) {
			noticeService.markReads(teamId, user.getUserEntity(), 3);
		}
		return response;
	}
}
