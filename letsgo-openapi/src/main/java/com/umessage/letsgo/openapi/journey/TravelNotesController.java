package com.umessage.letsgo.openapi.journey;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.TravelNotesDetailRequest;
import com.umessage.letsgo.domain.vo.journey.response.TravelNotesDetailsResponse;
import com.umessage.letsgo.domain.vo.journey.response.TravelsListResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.journey.ITravelNotesService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;

@Api(value = "游记接口", description = "关于游记的相关操作")
@Controller
@RequestMapping(value = "/api/travelNotesComment")
public class TravelNotesController {

	@Resource
    private ITravelNotesService travelNotesService;
	
	@Resource
    private UserLoginHelper userLoginHelper;
	
	 @RequestMapping(value = "/getTravelsList", method = RequestMethod.POST)
	    @ResponseBody
	    @ApiOperation(value = "查询游记名称与封面图片、每日游记填写状态接口【游客端】", notes = "获取游记行程明细列表接口，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
	    @ApiImplicitParams({
	            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
	            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
	            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
	            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	    public TravelsListResponse getTravelsList(@RequestParam String txGroupId) {
		 	UserResponse userResponse =userLoginHelper.getLoginUser();
		 	Long userId = userResponse.getUserEntity().getId();
		 	return travelNotesService.getTravelsList(userId, txGroupId);
	 	}
	 
	 @RequestMapping(value = "/updateTravelNotes", method = RequestMethod.POST)
	    @ResponseBody
	    @ApiOperation(value = "修改游记名称与封面图片【游客端】", notes = "生成或者编辑游记接口，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
	    @ApiImplicitParams({
	            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
	            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
	            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
	            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	    public CommonResponse updateTravelNotes(@RequestParam Long travelNotesId,@RequestParam String scheduleImgurl,@RequestParam String title) {
		 	CommonResponse commonResponse;
		 	if (StringUtils.isEmpty(travelNotesId)) {
	            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：游记ID【travelNotesId】不能为空！");
	        }
		 	try {
		 		commonResponse = travelNotesService.updateTravelNotes(travelNotesId,scheduleImgurl,title);
			} catch (Exception e) {
				e.printStackTrace();
				commonResponse = new CommonResponse(ErrorConstant.INTERNAL_SERVER_ERROR,"生成或者编辑游记失败");
			}
		 	return commonResponse;
	 	}
	 
	 @RequestMapping(value = "/saveOneTravel", method = RequestMethod.POST)
	    @ResponseBody
	    @ApiOperation(value = "保存某天游记点评信息【游客端】", notes = "生成或者编辑游记接口，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
	    @ApiImplicitParams({
	            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
	            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
	            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
	            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	    public CommonResponse saveOneTravel(@RequestBody TravelNotesDetailRequest travelNoteRequest) {
		 	CommonResponse commonResponse;
		 	try {
		 		commonResponse = travelNotesService.saveOneTravel(travelNoteRequest);
			} catch (Exception e) {
				e.printStackTrace();
				commonResponse = new CommonResponse(ErrorConstant.INTERNAL_SERVER_ERROR,"生成或者编辑游记失败");
			}
		 	return commonResponse;
	 	}	
	 
	 @RequestMapping(value = "/getOneScheduleDetail", method = RequestMethod.POST)
	    @ResponseBody
	    @ApiOperation(value = "查看某天游记景点与酒店信息【游客端】", notes = "查看某天游记景点内容和行程游记点评内容，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
	    @ApiImplicitParams({
	            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
	            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
	            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
	            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
	    public TravelNotesDetailsResponse getOneScheduleDetail(@RequestParam long scheduleDetailId) {
		 	return travelNotesService.getOneScheduleDetail(scheduleDetailId);
	 	}	
}
