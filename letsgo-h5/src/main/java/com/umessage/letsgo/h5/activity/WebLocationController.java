package com.umessage.letsgo.h5.activity;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.utils.JsonUtils;
import com.umessage.letsgo.domain.po.activity.LocationEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.activity.response.LocationListResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.SignVo;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.activity.IWebLocationService;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Api(value = "H5位置页面", description = "关于H5位置页面的相关操作")
@Controller
@RequestMapping(value = "/web/location")
public class WebLocationController {

    @Resource
    private IWebLocationService webLocationService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;
    @Resource
    private ITeamService teamService;
    @Resource
    private IUserService userService;
    @Resource
    private IScheduleService scheduleService;

    @RequestMapping(value = "/getMemberLocationList", method = RequestMethod.GET)
    public String getMemberLocationList(String teamId, SignVo signVo, Model model) throws Exception {
        if (teamId == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【teamId】不能为空！");
        }
        // 1、获取当前登录的用户信息
        LocationListResponse response = new LocationListResponse();
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            response =LocationListResponse.createUserNotLoginResponse();
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
            model.addAttribute("response",response);
            return "location/memberLocation";
        }

        // 2、获取成员位置列表
        List<LocationEntity> locationList = webLocationService.getLocationList(teamId, user.getUserEntity().getId(),true);
        response.setLocationEntityList(locationList);
        response.setTeamId(getUrlEncodeTeamId(teamId));
        response.setTeamStatus(teamStatus);
        response.setRoleStatus(access.get(0));
        response.setAdminStatus(access.get(1));
        response = webLocationService.getLocationSign(teamId,response,signVo);
        model.addAttribute("response",response);
        return "location/memberLocation";
    }
    private String getUrlEncodeTeamId(String teamId){
        if(StringUtils.isEmpty(teamId)) return null;
        try {
            return URLEncoder.encode(teamId,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return teamId.replaceAll("#","%23");
    }
    @RequestMapping(value = "/refreshMemberLocation", method = RequestMethod.GET)
    public void refreshMemberLocation(String teamId, PrintWriter out) throws Exception {
        if (teamId == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【teamId】不能为空！");
        }

        // 1、获取当前登录的用户信息
        LocationListResponse respone = new LocationListResponse();
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            respone = LocationListResponse.createUserNotLoginResponse();
        }
        // 2、获取成员位置列表
        List<LocationEntity> locationEntities = webLocationService.getLocationList(teamId, user.getUserEntity().getId(),false);
        respone.setLocationEntityList(locationEntities);
        out.print(JsonUtils.obj2json(respone));
    }


    @RequestMapping(value = "/getOnlookerUserLocation", method = RequestMethod.GET)
    public String getOnlookerUserLocation(String userId,Long scheduleId, SignVo signVo, Model model) throws Exception {
        if (userId == null || scheduleId == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：所需参数不能为空！");
        }
        // 1、获取当前登录的用户信息
        LocationListResponse response = new LocationListResponse();
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            response =LocationListResponse.createUserNotLoginResponse();
        }
         //获取被围观者信息
        //根据用户腾讯组id获取用户id
        UserEntity userEntity= userService.selecUserByUserName(userId);
        //根据行程ID获取团组ID
        String teamId =scheduleService.getTxGroupIdByScheduleId(scheduleId);
        // 2、获取成员位置列表
        List<LocationEntity> locationEntitie = new ArrayList<LocationEntity>();
        List<LocationEntity> locationEntities = webLocationService.getOnlookLocation(teamId, userEntity.getId(),true);
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
        response.setLocationEntityList(locationEntitie);
        response.setTeamId(getUrlEncodeTeamId(teamId));
        response.setTeamStatus(status);
        response.setRoleStatus(3);
        response.setAdminStatus(0);
        response = webLocationService.getLocationSign(teamId,response,signVo);
        model.addAttribute("response",response);
        return "onlookers/onlookersLocation";
    }


}
