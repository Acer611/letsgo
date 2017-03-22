package com.umessage.letsgo.openapi.activity;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.po.activity.LocationEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.activity.response.LocationListResponse;
import com.umessage.letsgo.domain.vo.activity.response.LocationRespone;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.activity.ICallService;
import com.umessage.letsgo.service.api.activity.ILocationService;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Api(value = "位置接口", description = "关于成员位置的相关操作")
@Controller
@RequestMapping(value = "/api/location")
public class LocationController {
    @Resource
    private ILocationService locationService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;
    @Resource
    private ICallService callService;
    @Resource
    private ITeamService teamService;
    @Resource
    private IUserService userService;
    @Resource
    private IScheduleService scheduleService;

    @RequestMapping(value = "/getMemberLocationList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取团队成员位置信息【领队端、游客端】", notes = "获取团队成员位置信息，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。可以是获取所有游客的，也可以是获取领队和导游的")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public LocationListResponse getMemberLocationList(
            @ApiParam(value = "团队ID", required = true) @RequestParam(required = true) String teamId) throws Exception {
        if (teamId == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【teamId】不能为空！");
        }

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return LocationListResponse.createUserNotLoginResponse();
        }

        List<Integer> access = teamService.roleStatus(teamId, user.getUserEntity().getId());
        Integer teamStatus = teamService.teamStatus(teamId);
        if (teamStatus == null || access.get(0) ==null || access.get(1) == null){
            throw new BusinessException(ErrorConstant.NOT_FOUND, "没有发现团队或成员！");
        }
        if (teamStatus != 1 || (access.get(0) == 2 && access.get(1) == 0)) {
            LocationListResponse locationListResponse = new LocationListResponse();
            locationListResponse.setTeamStatus(teamStatus);
            locationListResponse.setRoleStatus(access.get(0));
            locationListResponse.setAdminStatus(access.get(1));
            return locationListResponse;
        }

        // 2、获取成员位置列表
        List<LocationEntity> locationEntities = locationService.getLocationList(teamId, user.getUserEntity().getId(),true);
        LocationListResponse respone = new LocationListResponse();
        respone.setLocationEntityList(locationEntities);
        respone.setTeamStatus(teamStatus);
        respone.setRoleStatus(access.get(0));
        respone.setAdminStatus(access.get(1));
        return respone;
    }

    @RequestMapping(value = "/refreshMemberLocation", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "刷新位置信息【领队端、游客端】", notes = "刷新位置信息，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public LocationListResponse refreshMemberLocation(
            @ApiParam(value = "团队ID", required = true) @RequestParam(required = true) String teamId) throws Exception {
        if (teamId == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【teamId】不能为空！");
        }

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return LocationListResponse.createUserNotLoginResponse();
        }
        // 2、获取成员位置列表
        List<LocationEntity> locationEntities = locationService.getLocationList(teamId, user.getUserEntity().getId(),true);
        LocationListResponse respone = new LocationListResponse();
        respone.setLocationEntityList(locationEntities);
        return respone;
    }

    @RequestMapping(value = "/submitLocation", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "提交位置信息【领队端、游客端】", notes = "提交位置信息，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public LocationRespone submitLocation(
            @ApiParam(value = "用户坐标经度", required = true) @RequestParam(required = true) Double lng,
            @ApiParam(value = "用户坐标纬度", required = true) @RequestParam(required = true) Double lat,
            @ApiParam(value = "判断是点名还是正常位置上传的字符串,点名：flag=call,正常上传：flag=common", required = true) @RequestParam(required = true) String flag) {

        if (lng == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：用户坐标经度【lng】不能为空！");
        }
        if (lat == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：用户坐标纬度【lat】不能为空！");
        }
        if(lng == 0 && lat == 0){
            return LocationRespone.createInvalidParameterResponse("(0,0)无效经纬度坐标");
        }
        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return LocationRespone.createUserNotLoginResponse();
        }

        LocationRespone locationRespone = locationService.createLocation(user.getUserEntity().getId(), lng, lat);
        if("call".equals(flag)){
            CommonResponse response = callService.updateCallDetailList(user.getUserEntity(),locationRespone.getLocationEntity());
//            return callService.updateCallDetailList(user.getUserEntity(),locationRespone.getLocationEntity());
            LocationRespone locationRespone1 = new LocationRespone();
            locationRespone1.setRetCode(response.getRetCode());
            locationRespone1.setRetMsg(response.getRetMsg());
            return locationRespone1;
        }
        return  locationRespone ;
    }

    @RequestMapping(value = "/getOnlookerUserLocation", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取围观位置信息【游客端】", notes = "获取围观位置信息，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。可以是获取所有游客的，也可以是获取领队和导游的")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public LocationListResponse getOnlookerUserLocation(
            @ApiParam(value = "用户腾讯组ID", required = true) @RequestParam(required = true) String userId,@ApiParam(value = "行程ID", required = true) @RequestParam(required = true) Long scheduleId) throws Exception {
        if (userId == null || scheduleId==null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：所需参数不能为空！");
        }

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return LocationListResponse.createUserNotLoginResponse();
        }
        //获取被围观者信息
        //根据用户腾讯组id获取用户id
        UserEntity userEntity= userService.selecUserByUserName(userId);
        //根据行程ID获取团组ID
        String teamId =scheduleService.getTxGroupIdByScheduleId(scheduleId);
        // 2、获取成员位置列表
        List<LocationEntity> locationEntitie = new ArrayList<LocationEntity>();
        List<LocationEntity> locationEntities = locationService.getOnlookLocation(teamId, userEntity.getId(),true);
        if(locationEntities!=null && locationEntities.size()>0){
            for (LocationEntity location:locationEntities) {
                if(location.getUserId().equals(userEntity.getId())){
                    locationEntitie.add(location);
                    break;
                }
            }
        }
        TeamEntity teamEntity =teamService.getTeamByTXGroupId(teamId);
        Integer status=null;
        if(teamEntity!=null && teamEntity.getStatus()!=null){
            status=teamEntity.getStatus();
        }
        LocationListResponse respone = new LocationListResponse();
        respone.setLocationEntityList(locationEntitie);
        respone.setTeamStatus(status);
        respone.setRoleStatus(3);
        respone.setAdminStatus(0);
        return respone;
    }

}
