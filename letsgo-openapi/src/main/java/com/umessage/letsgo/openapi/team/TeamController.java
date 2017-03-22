package com.umessage.letsgo.openapi.team;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.requset.GuideMemberRequest;
import com.umessage.letsgo.domain.vo.team.respone.*;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import com.umessage.letsgo.service.api.team.ITeamService;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "团队接口", description = "关于团队的相关操作，包括获取团队列表、获取团队详情、添加导游等接口")
@Controller
@RequestMapping(value = "/api/team")
public class TeamController {
    @Resource
    private ITeamService teamService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;

    @RequestMapping(value = "/getTeamList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取团队列表【领队端、游客端】", notes = "获取团队列表，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public TeamListResponse getTeamList(
            @ApiParam(value = "标识，如果传入值1，则表示只获取可以操作的团队", required = false) @RequestParam(required = false) Integer flag) {

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return TeamListResponse.createUserNotLoginResponse();
        }

        return teamService.getTeamList(user.getUserEntity().getId(), flag);
    }

    @RequestMapping(value = "/getTeamDetail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取团队详情，用于更多页面【领队端、游客端】", notes = "获取团队详情，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public TeamRespone getTeamDetail(
            @ApiParam(value = "团队ID", required = true) @RequestParam(required = true) String id) {

        if (StringUtils.isEmpty(id)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【id】不能为空！");
        }

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return TeamRespone.createUserNotLoginResponse();
        }

        TeamRespone respone = teamService.getTeamDetail(id);
        List<Integer> roles = teamService.roleStatus(id, user.getUserEntity().getId());
        Integer teamStatus = teamService.teamStatus(id);
        if (teamStatus == null || roles.get(0) ==null || roles.get(1) == null){
            throw new BusinessException(ErrorConstant.NOT_FOUND, "没有发现团队或成员！");
        }
        respone.setRoleStatus(roles.get(0));
        respone.setAdminStatus(roles.get(1));
        respone.setTeamStatus(teamStatus);

        return respone;
    }

    @RequestMapping(value = "/getTeamMemberList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取团队成员列表，用于团队成员页面，选择人页面，进入分组后的页面以及添加组员页面【领队端、游客端】", notes = "获取团队成员列表，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public TeamMemberResponse getTeamMemberList(
            @ApiParam(value = "团队ID", required = true) @RequestParam(required = true) String id) {

        if (StringUtils.isEmpty(id)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【id】不能为空！");
        }

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return TeamMemberResponse.createUserNotLoginResponse();
        }

        TeamMemberResponse response = teamService.getTeamMemberList(id);
        List<Integer> roles = teamService.roleStatus(id, user.getUserEntity().getId());
        Integer teamStatus = teamService.teamStatus(id);
        if (teamStatus == null || roles.get(0) ==null || roles.get(1) == null){
            throw new BusinessException(ErrorConstant.NOT_FOUND, "没有发现团队或成员！");
        }
        response.setRoleStatus(roles.get(0));
        response.setAdminStatus(roles.get(1));
        response.setTeamStatus(teamStatus);

        return response;

    }

    @RequestMapping(value = "/addGuide", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加导游【领队端】", notes = "添加导游，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse addGuide(@RequestBody GuideMemberRequest guide) {

        if (StringUtils.isEmpty(guide.getTeamId())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【teamId】不能为空！");
        }
        if (StringUtils.isEmpty(guide.getRealName())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：姓名【realName】不能为空！");
        }
        if (null == guide.getSex()) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：性别【sex】不能为空！");
        }
        if (StringUtils.isEmpty(guide.getPhone())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：手机号【phone】不能为空！");
        }

        return teamService.addTourGuide(guide);
    }

    @RequestMapping(value = "/getLatestTeam", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取出行中的团队或者最近即将出行的团队，用于新建通知、新建集合等模块【领队端】", notes = "获取出行中的团队或者最近即将出行的团队，用于新建通知、新建集合等模块，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public TeamRespone getLatestTeam(
            @ApiParam(value = "团队ID", required = false) @RequestParam(required = false) String id) {

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return TeamRespone.createUserNotLoginResponse();
        }

        return teamService.getLatestTeam(user.getUserEntity().getId(), id);
    }

    @RequestMapping(value = "/getTeamInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取团队中的领队和有管理权限的导游【游客端】", notes = "获取团队中的领队和有管理权限的导游，用于对领队说的模块，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public TeamInfoResponse getTeamInfo(
            @ApiParam(value = "团队ID", required = true) @RequestParam(required = true) String id) {

        if (StringUtils.isEmpty(id)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【id】不能为空！");
        }

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return TeamInfoResponse.createUserNotLoginResponse();
        }

        TeamInfoResponse response = teamService.getTeamInfo(id , user.getUserEntity().getId());
        return response;

    }

    @RequestMapping(value = "/getTeamAnalyticalData", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取行程团队中的成员分析数据【游客端】", notes = "获取行程团队中的成员分析数据，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public AnalyticalDataResponse getTeamAnalyticalData(
            @ApiParam(value = "团队ID", required = true) @RequestParam(required = true) String teamId) {

        if (StringUtils.isEmpty(teamId)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【id】不能为空！");
        }

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return AnalyticalDataResponse.createUserNotLoginResponse();
        }

        AnalyticalDataResponse response = teamService.getTeamAnalyticalData(teamId, user.getUserEntity().getId());
        return response;

    }
    
    @RequestMapping(value = "/saveProfession", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "保存职业【游客端】", notes = "保存职业数据，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse saveProfession(
            @ApiParam(value = "团队ID", required = true) @RequestParam(required = true) String teamId,
            @ApiParam(value = "职业代号, 0互联网, 1教育，2医疗，3工人，4教师，5其他", required = true) @RequestParam(required = true) String proCode,
            @ApiParam(value = "职业名称", required = true) @RequestParam(required = true) String proName) {

        if (StringUtils.isEmpty(teamId) || StringUtils.isEmpty(proCode) || StringUtils.isEmpty(proName)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【id】或职业代号【proCode】或职业名称【proName】不能为空！");
        }
     // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return TeamInfoResponse.createUserNotLoginResponse();
        }
        CommonResponse response = teamService.saveProfession(teamId, user.getUserEntity().getId(), proCode,proName);
        return response;

    }

    @RequestMapping(value = "/getUserRoleAndSurvey", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "聊天下拉页面获取用户的角色和是否已经发放问卷", notes = "聊天下拉页面获取用户的角色和是否已经发放问卷，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public TeamRoleResponse getUserRoleAndSurvey(
            @ApiParam(value = "腾讯组团Id", required = true) @RequestParam(required = true) String teamId) {
        if (StringUtils.isEmpty(teamId)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【id】不能为空！");
        }
        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return TeamRoleResponse.createUserNotLoginResponse();
        }
        return teamService.getUserRoleAndSurvey(teamId, user.getUserEntity().getId());

    }

}
