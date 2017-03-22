package com.umessage.letsgo.openapi.journey;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.CreateScheduleRequest;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleDetailRequest;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleRequest;
import com.umessage.letsgo.domain.vo.journey.response.*;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.requset.MemberRequest;
import com.umessage.letsgo.service.api.journey.IScheduleDetailsService;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.team.ILeaderService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Api(value = "行程接口", description = "关于行程的相关操作")
@Controller
@RequestMapping(value = "/api/schedule")
public class ScheduleController {

    @Resource
    private IScheduleDetailsService scheduleDetailsService;
    @Resource
    private ITeamService teamService;
    @Resource
    private IScheduleService scheduleService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;
    @Resource
    private ILeaderService leaderService;

    @RequestMapping(value = "/getTrafficInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取行程中的交通信息【领队端】", notes = "获取行程中的交通信息，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public TrafficInfoResponse getTrafficInfo(
            @ApiParam(value = "团队ID", required = true) @RequestParam(required = true) String teamId,
            @ApiParam(value = "行程日期:时间戳，Long型", required = true) @RequestParam(required = true) Long date) {

        if (StringUtils.isEmpty(teamId)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【teamId】不能为空！");
        }
        if (date == null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：行程日期【date】不能为空！");
        }

        String dateString = DateUtils.toString(new Date(date),"yyyy-MM-dd");
        if(dateString == null){
            throw new BusinessException(ErrorConstant.INVALID_PARAMETER, "参数：行程日期【date】不能转换为日期！");
        }

        // 获取团队ID
        //TeamEntity teamEntity = teamService.getTeamByTXGroupId(teamId);

        return scheduleDetailsService.getTrafficInfo(teamId, dateString);
    }

    @RequestMapping(value = "getDestinationInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取行程的目的地及时区接口【领队端】", notes = "获取行程的目的地及时区，用于新建通知|集合时，选择时区，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public TimeZoneInfoResponse getDestinationInfo(
            @ApiParam(value = "团队ID", required = true) @RequestParam(required = true) String teamId,
            @ApiParam(value = "纬度", required = false) @RequestParam(required = false) Double lat,
            @ApiParam(value = "经度", required = false) @RequestParam(required = false) Double lng) {
        if (StringUtils.isEmpty(teamId)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【teamId】不能为空！");
        }

        // 获取团队ID
        //TeamEntity teamEntity = teamService.getTeamByTXGroupId(teamId);

        return scheduleDetailsService.getDestinationInfo(teamId, lat, lng);
    }

    @RequestMapping(value = "scheduleDetailUpdate", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "H5更新每日行程【领队端】", notes = "领队在手机端更新H5每日行程，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse scheduleDetailUpdate(@RequestBody ScheduleDetailRequest request){
        if(request == null || request.getId() == null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：每日行程请求或每日行程ID不能为空！");
        }
        return scheduleDetailsService.updateScheduleDetails(request);
    }

    @RequestMapping(value = "getSchedule", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取行程单信息【领队端、游客端】", notes = "获取行程单信息，用于用户进入应用时，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public ScheduleInfoResponse getSchedule(@ApiParam(value = "团队ID", required = false) @RequestParam(required = false) String teamId){

        UserResponse userResponse = oauth2LoginHelper.getLoginUser();
        if (userResponse.getRetCode() != ErrorConstant.SUCCESS) {
            return ScheduleInfoResponse.createUserNotLoginResponse();
        }

        // 获取团队ID
        TeamEntity teamEntity = teamService.getTeamByTXGroupId(teamId);

        return scheduleService.getScheduleEntity(teamEntity.getId(), userResponse.getUserEntity());
    }

    @RequestMapping(value = "updateSchedulePhotos", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "H5更新行程图片【领队端】", notes = "领队在手机端更新行程图片，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse updateSchedulePhotos(@ApiParam(value = "行程ID", required = true) @RequestParam(required = true) Long id,@ApiParam(value = "行程图片", required = true) @RequestParam(required = true) String featurePhoto) throws Exception{
        if(id == null || featurePhoto == null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：每日行程ID不能为空或图片不能为空！");
        }
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setId(id);
        scheduleEntity.setProcessPhotosUrl(featurePhoto);
        return scheduleService.updateSchedulePhotos(scheduleEntity);
    }

    @RequestMapping(value = "createSchedule", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "创建行程接口【领队端】", notes = "创建行程接口【领队端】，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public ScheduleAddResponse createSchedule(@RequestBody CreateScheduleRequest request) throws Exception{
        if(request.getName() == null || request.getTeamNum() == null || request.getStartPlace() == null ||
                request.getStartDate() == null || request.getEndDate() == null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：所需参数不能为空！");
        }
        return scheduleService.createSchedule(request);
    }

    @RequestMapping(value = "getLeaderScheduleList", method = RequestMethod.POST)
    @ApiOperation(value = " 获取用户行程列表", notes = " 获取用户行程列表，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public ScheduleListResponse getLeaderScheduleList(@ApiParam(value = "团队出行状态：1：出行中；2：即将出行；3：已出行", required = false) @RequestParam(required = false) Integer status) throws Exception{
        UserResponse userResponse = oauth2LoginHelper.getLoginUser();
        if (userResponse.getRetCode() != ErrorConstant.SUCCESS) {
            return ScheduleListResponse.createUserNotLoginResponse();
        }
        ScheduleRequest request = new ScheduleRequest();
        request.setStatus(status);
        request.setUserId(userResponse.getUserEntity().getId());
        if(status!= null){//兼容老版本
            return scheduleService.getLeaderScheduleList(request);
        }else{
            return scheduleService.getScheduleList(request);
        }

    }

    @RequestMapping(value = "getEndScheduleList", method = RequestMethod.POST)
    @ApiOperation(value = " 获取用户已结束行程列表", notes = " 获取用户已结束行程列表，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public ScheduleListResponse getEndScheduleList() throws Exception{
        UserResponse userResponse = oauth2LoginHelper.getLoginUser();
        if (userResponse.getRetCode() != ErrorConstant.SUCCESS) {
            return ScheduleListResponse.createUserNotLoginResponse();
        }
        ScheduleRequest request = new ScheduleRequest();
        request.setUserId(userResponse.getUserEntity().getId());
        return scheduleService.getEndScheduleList(request);
    }

    @RequestMapping(value = "confirmSchedule", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = " 确认行程接口【领队端】", notes = "领队在手机端确认行程接口，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse confirmSchedule(
            @ApiParam(value = "团队id", required = true) @RequestBody ScheduleDetailRequest request) throws Exception{
        if(request.getTeamId() == null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队id不能为空！");
        }
        return scheduleService.confirmSchedule(request.getTeamId(),null);
    }

    @RequestMapping(value = "getAllTripList", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = " 获取当前用户（游客）的全部行程列表（包括围观的）【游客端】", notes = "【游客端】全部行程列表，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public UserScheduleResponse getAllTripList(){
        UserResponse userResponse = oauth2LoginHelper.getLoginUser();
        if (userResponse.getRetCode() != ErrorConstant.SUCCESS) {
            return UserScheduleResponse.createUserNotLoginResponse();
        }
        MemberRequest request = new MemberRequest();
        request.setUserId(userResponse.getUserEntity().getId());
        return scheduleService.getAllTripList(request);
    }


}
