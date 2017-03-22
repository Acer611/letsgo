package com.umessage.letsgo.openapi.team;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.po.team.OnlookersDetailsEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.requset.InviteWatchRequest;

import com.umessage.letsgo.domain.vo.team.requset.OnlookersUserRequest;
import com.umessage.letsgo.domain.vo.team.requset.ReplyLikeRequest;
import com.umessage.letsgo.domain.vo.team.respone.OnlookersDetailsResponse;
import com.umessage.letsgo.domain.vo.team.respone.OnlookersResponse;
import com.umessage.letsgo.domain.vo.team.respone.RedPointResponse;
import com.umessage.letsgo.domain.vo.team.respone.TeamListResponse;

import com.umessage.letsgo.domain.vo.team.respone.*;

import com.umessage.letsgo.service.api.team.IOnlookersDetailsService;
import com.umessage.letsgo.service.api.team.IOnlookersService;
import com.umessage.letsgo.service.api.team.IOnlookersUserService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/2.
 */
@Api(value = "围观接口", description = "关于游客端围观的相关操作，包括发布围观，邀请围观，回复围观信息，查看最新回复，查看围观和回复列表")
@Controller
@RequestMapping(value = "/api/onlookers")
public class OnlookersController {
    @Resource
    private IOnlookersService onlookersService;
    @Resource
    private IOnlookersDetailsService onlookersDetailsService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;
    @Resource
    private IOnlookersUserService onlookersUserService;
    @RequestMapping(value = "/sendWatchMess",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "发布围观信息【游客端】", notes = "发布围观信息，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse sendOnlookers(@ApiParam(value = "行程id", required = true)@RequestParam Long scheduleId,@ApiParam(value = "围观信息", required = true)@RequestParam String message,@ApiParam(value = "图片地址", required = true)@RequestParam String imgUrl,@ApiParam(value = "缩略图地址", required = true)@RequestParam String thumbnailUrl,@ApiParam(value = "图片宽", required = true)@RequestParam String wide,@ApiParam(value = "图片高", required = true)@RequestParam String height){
        if (scheduleId == null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：所需参数不能为空！");
        }
        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return TeamListResponse.createUserNotLoginResponse();
        }
        Long userId=user.getUserEntity().getId();
        return onlookersService.sendOnlookers(scheduleId,userId,message,imgUrl,thumbnailUrl,wide,height);
    }
    @RequestMapping(value = "/getWatchAndReplyMess",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取围观和回复信息【游客端】", notes = "获取围观信息，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public OnlookersResponse getWatchAndReplyMess(@ApiParam(value = "被围观者ID", required = true)@RequestParam Long onlookerUserId,@ApiParam(value = "行程id", required = true)@RequestParam Long scheduleId,@ApiParam(value = "类型:1发布围观者，2围观别人", required = true)@RequestParam Integer type,@ApiParam(value = "当前页数", required = true)@RequestParam Integer pageNum,@ApiParam(value = "每页条数", required = true)@RequestParam Integer pageSize){

        if (scheduleId == null|| type==null){
           throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：所需参数不能为空！");
         }
         // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return OnlookersResponse.createUserNotLoginResponse();
        }
        Long userId=user.getUserEntity().getId();
        return onlookersService.getWatch(userId,scheduleId,type,pageNum,pageSize,onlookerUserId);
    }
    @RequestMapping(value = "/getLastReply",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取最新回复信息【游客端】", notes = "获取回复信息，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public OnlookersDetailsResponse getLastReply(@RequestParam Long scheduleId){
// 1、获取当前登录的用户信息
        OnlookersDetailsResponse on=new OnlookersDetailsResponse();
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return OnlookersDetailsResponse.createUserNotLoginResponse();
        }
        Long userId=user.getUserEntity().getId();
        List<OnlookersDetailsInfo> list= onlookersDetailsService.getLastReply(userId,scheduleId);
        on.setList(list);
        OnlookersDetailsEntity onlookersDetailsEntity=new OnlookersDetailsEntity();
        onlookersDetailsEntity.setByReplyId(userId);
        onlookersDetailsEntity.setScheduleId(scheduleId);
        onlookersDetailsService.updateStatus(onlookersDetailsEntity);
        return on;
    }

//    @RequestMapping(value = "/getWatchRedPoint", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "获取围观小红点【游客端】", notes = "获取围观小红点【游客端】，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
//            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
//    public RedPointResponse getWatchRedPoint(
//            @ApiParam(value = "行程id", required = true) @RequestParam(required = true) Long scheduleId) {
//        if (scheduleId == null){
//            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：行程id不能为空！");
//        }
//        // 1、获取当前登录的用户信息
//        UserResponse user = oauth2LoginHelper.getLoginUser();
//        if (user.getRetCode() != ErrorConstant.SUCCESS){
//            return RedPointResponse.createUserNotLoginResponse();
//        }
//        OnlookersDetailsRequest request =new OnlookersDetailsRequest();
//        request.setScheduleId(scheduleId);
//        request.setByReplyId(user.getUserEntity().getId());
//        return onlookersDetailsService.getWatchRedPoint(request);
//    }


    @RequestMapping(value = "/replyLikes", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "回复点赞【游客端】", notes = "回复点赞接口【游客端】，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse replyLikes(@RequestBody ReplyLikeRequest request) throws Exception{
        if (StringUtils.isEmpty(request.getUserId()) || request.getType()==null || request.getOnlookersId()==null || StringUtils.isEmpty(request.getByReplyId())){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：所需参数不能为空！");
        }
        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return RedPointResponse.createUserNotLoginResponse();
        }
        return onlookersDetailsService.replyLikes(request);
    }


    @RequestMapping(value = "/inviteWatch", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "邀请围观【游客端】", notes = "邀请围观【游客端】，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse inviteWatch(@ApiParam(value = "行程id", required = true)@RequestParam(required = true) Long scheduleId,@ApiParam(value = "手机号", required = true)@RequestParam(required = true) String mobile,@ApiParam(value = "性别：1男；2女", required = true)@RequestParam(required = true) Integer sex,@ApiParam(value = "姓名", required = true)@RequestParam(required = true) String name) {
        if (mobile == null ||scheduleId==null || sex==null || name==null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：所需参数不能为空！");
        }
        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return RedPointResponse.createUserNotLoginResponse();
        }
        InviteWatchRequest request =new InviteWatchRequest();
        request.setMobile(mobile);
        request.setScheduleId(scheduleId);
        request.setUserId(user.getUserEntity().getId());
        request.setSex(sex);
        request.setName(name);
        return onlookersDetailsService.inviteWatch(request);
    }

    @RequestMapping(value = "/getMyFriendlist", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取围观我的好友列表【游客端】", notes = "获取围观我的好友列表【游客端】，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public OnlookersUserListResponse getMyFriendlist(@RequestParam Long scheduleId) {
        if (scheduleId == null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：所需参数不能为空！");
        }
        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return OnlookersUserListResponse.createUserNotLoginResponse();
        }
        OnlookersUserRequest request =new OnlookersUserRequest();
        request.setUserId(user.getUserEntity().getId());
        request.setScheduleId(scheduleId);
        return onlookersUserService.getMyFriendlist(request);
    }


}
