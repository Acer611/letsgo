package com.umessage.letsgo.openapi.journey;

import com.umessage.letsgo.domain.po.journey.ScenicEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailCommentEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.*;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.journey.IScheduleDetailsService;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleDetailCommentRequest;
import com.umessage.letsgo.service.api.journey.IScheduleDetailCommentService;

import java.util.List;

@Api(value = "点评接口", description = "关于行程的相关操作")
@Controller
@RequestMapping(value = "/api/scheduleDetailComment")
public class ScheduleDetailCommentController {

	@Resource
	private UserLoginHelper oauth2LoginHelper;
	@Resource
	private IScheduleDetailCommentService scheduleDetailCommentService;


	@ApiOperation(value = "提交点评", notes = "提交点评，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	@RequestMapping(value = "/submitJournarComment", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse submitJournarComment(@RequestBody  ScheduleDetailCommentRequest scheduleDetailCommentRequest) {

		if (StringUtils.isEmpty(scheduleDetailCommentRequest.getScheduleDetailId())) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：行程详细id【scheduleDetailId】不能为空！");
		}
		if (StringUtils.isEmpty(scheduleDetailCommentRequest.getSatisfiedStatus())) {
			throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：满意度标识【satisfiedStatus】不能为空！");
		}

		UserResponse userResponse = oauth2LoginHelper.getLoginUser();
		if (userResponse.getRetCode() != ErrorConstant.SUCCESS) {
			throw new BusinessException(ErrorConstant.USER_NOT_LOGIN, "用户未登录或者登录会话已经过期！");
		}
		Long userID = userResponse.getUserEntity().getId();
		scheduleDetailCommentRequest.setUserId(userID);

		try {
			scheduleDetailCommentService.submitJournarComment(scheduleDetailCommentRequest);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorConstant.INTERNAL_SERVER_ERROR, "提交点评失败");
		}

		CommonResponse response = new CommonResponse();
		return response;
	}

}
