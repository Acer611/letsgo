package com.umessage.letsgo.openapi.team;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.requset.MemberAddRequest;
import com.umessage.letsgo.domain.vo.team.respone.MemberListResponse;
import com.umessage.letsgo.domain.vo.team.respone.MemberResponse;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "成员接口", description = "关于成员的相关操作，包括添加备用手机号，删除备用手机号，设置管理员接口")
@Controller
@RequestMapping(value = "/api/member")
public class MemberController {
    @Resource
    private IMemberService memberService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;
    @Resource
    private ITeamService teamService;

    @RequestMapping(value = "/getTeamMemberDetail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取团队成员详细信息，用于团队成员列表中的查看成员信息【领队端、游客端】", notes = "获取团队成员详细信息，用于团队成员列表中的查看成员信息，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public MemberResponse getTeamMemberDetail(
            @ApiParam(value = "成员ID", required = true) @RequestParam(required = true) Long memberId) {

        if (null == memberId) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：成员ID【memberId】不能为空！");
        }

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return MemberResponse.createUserNotLoginResponse();
        }

        return memberService.getMemberDetail(memberId, user.getUserEntity().getId());
    }

    @RequestMapping(value = "/getTeamMemberDetailByTeamIdAndUserId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据团队id和用户id获取团队成员详细信息，用于公告中的点击头像查看成员信息【领队端、游客端】", notes = "根据团队id和用户id获取团队成员详细信息，用于公告中的点击头像查看成员信息，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public MemberResponse getTeamMemberDetailByTeamIdAndUserId(@ApiParam(value = "腾讯群组ID", required = true) @RequestParam(required = true) String teamId,
            @ApiParam(value = "用户ID", required = true) @RequestParam(required = true) Long userId) {

        if (StringUtils.isEmpty(teamId)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：腾讯群组ID【teamId】不能为空！");
        }

        if (null == userId) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：用户ID【userId】不能为空！");
        }

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return MemberResponse.createUserNotLoginResponse();
        }

        return memberService.getMemberDetailByUserId(teamId, userId, user.getUserEntity().getId());
    }


    @RequestMapping(value = "/getTeamMemberDetailByTeamIdAndUserName", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据团队id和用户名获取团队成员详细信息，用于聊天中的点击头像查看成员信息【领队端、游客端】", notes = "根据团队id和用户名获取团队成员详细信息，用于聊天中的点击头像查看成员信息，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public MemberResponse getTeamMemberDetailByTeamIdAndUserName(@ApiParam(value = "腾讯群组ID", required = true) @RequestParam(required = true) String teamId,
            @ApiParam(value = "用户名", required = true) @RequestParam(required = true) String userName) {

        if (StringUtils.isEmpty(teamId)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：腾讯群组ID【teamId】不能为空！");
        }

        if (StringUtils.isEmpty(userName)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：用户名【userName】不能为空！");
        }

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return MemberResponse.createUserNotLoginResponse();
        }

        return memberService.getMemberDetailByUserName(teamId, userName, user.getUserEntity().getId());
    }


    @RequestMapping(value = "/addMarkPhone", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加备用手机号【领队端】", notes = "添加备用手机号，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse addMarkPone(
            @ApiParam(value = "成员ID", required = true) @RequestParam Long memberId,
            @ApiParam(value = "手机号", required = true) @RequestParam String phone) {

        if (null == memberId) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：成员ID【memberId】不能为空！");
        }

        if (StringUtils.isEmpty(phone)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：手机号【phone】不能为空！");
        }

        return memberService.setMarkPhone(memberId, phone);
    }

    @RequestMapping(value = "/deleteMarkPhone", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除备用手机号【领队端】", notes = "删除备用手机号，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse deleteMarkPhone(
            @ApiParam(value = "成员ID", required = true) @RequestParam Long memberId) {

        if (null == memberId) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：成员ID【memberId】不能为空！");
        }

        return memberService.setMarkPhone(memberId, "");
    }

    @RequestMapping(value = "/setAdministrator", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "设置管理员【领队端】", notes = "设置管理员，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。；属性isAdmin：1表示是管理员；0表示不是管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse setAdministrator(
            @ApiParam(value = "成员ID", required = true) @RequestParam Long memberId,
            @ApiParam(value = "是否设为管理员；1表示设置管理员；0表示取消管理员", required = true) @RequestParam Integer isAdmin) {

        if (null == memberId) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：成员ID【memberId】不能为空！");
        }
        if (null == isAdmin) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：是否设为管理员【isAdmin】不能为空！");
        }

        return memberService.setAdministrator(memberId, isAdmin);
    }

    @RequestMapping(value = "/getNoPhoneMembers", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取没有电话号码的成员", notes = "获取没有电话号码的成员，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。；属性isAdmin：1表示是管理员；0表示不是管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public MemberListResponse getNoPhoneMembers(
            @ApiParam(value = "腾讯组ID", required = true) @RequestParam(required = true) String teamId) {
        if (StringUtils.isEmpty(teamId)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:腾讯组ID不能为空！");
        }
        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return MemberListResponse.createUserNotLoginResponse();
        }
        TeamEntity teamEntity =teamService.getTeamByTXGroupId(teamId);
        if (null == teamEntity) {
            return MemberListResponse.createNotFoundResponse("没有找到对应的团");
        }
        return memberService.getNoPhoneMembers(teamEntity.getId());
    }

    @RequestMapping(value = "/addMemberByQR", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "通过扫描二维码快速入团", notes = "通过扫描二维码快速入团，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。；属性isAdmin：1表示是管理员；0表示不是管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse addMemberByQR(
            @RequestBody MemberAddRequest request
             ) {
        if (request==null || request.getType()==null ||request.getRealName()==null ||request.getTeamId()==null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:所需的参数不能为空！");
        }
        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return CommonResponse.createUserNotLoginResponse();
        }
        TeamEntity teamEntity =teamService.getTeamByTXGroupId(request.getTeamId());
        if (null == teamEntity) {
            return MemberListResponse.createNotFoundResponse("没有找到对应的团");
        }
        request.setUser(user.getUserEntity());
        request.settId(teamEntity.getId());
        //加入类型【0选名字加入；1自己填写名字加入】
        if(request.getType()==1){
            return memberService.addMemberByQR(request);
        }else{
            return memberService.updateMemberByQR(request);
        }
    }

    @RequestMapping(value = "/deleteMemberById", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "移除成员（新加入的成员移除出团；旅行社端添加的移除手机号信息，并置为未加入状态）", notes = "需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。；属性isAdmin：1表示是管理员；0表示不是管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse deleteMemberById(
            @ApiParam(value = "成员ID", required = true) @RequestParam(required = true) Long id
      ) {
        if (id==null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:成员ID的参数不能为空！");
        }
        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return CommonResponse.createUserNotLoginResponse();
        }
            return memberService.deleteMemberById(id,user.getUserEntity());
    }


}
