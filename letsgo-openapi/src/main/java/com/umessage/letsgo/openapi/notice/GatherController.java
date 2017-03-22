package com.umessage.letsgo.openapi.notice;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.notice.request.DetailParamRequest;
import com.umessage.letsgo.domain.vo.notice.request.DetailRequest;
import com.umessage.letsgo.domain.vo.notice.request.NoticeRequest;
import com.umessage.letsgo.domain.vo.notice.request.RemindRequest;
import com.umessage.letsgo.domain.vo.notice.respone.NoticeDetailResponse;
import com.umessage.letsgo.domain.vo.notice.respone.NoticeListResponse;
import com.umessage.letsgo.domain.vo.notice.respone.NoticeReplyResponse;
import com.umessage.letsgo.domain.vo.notice.respone.NoticeRespone;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import com.umessage.letsgo.service.api.notice.INoticeDetailService;
import com.umessage.letsgo.service.api.notice.INoticeReplyService;
import com.umessage.letsgo.service.api.notice.INoticeService;
import com.umessage.letsgo.service.api.team.ITeamService;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "集合接口", description = "关于集合的相关操作，包括新建集合，获取集合列表，获取集合确认详情等接口")
@Controller
@RequestMapping(value = "/api/gather")
public class GatherController {
    @Resource
    private INoticeService noticeService;
    @Resource
    private INoticeDetailService noticeDetailService;
    @Resource
    private INoticeReplyService noticeReplyService;
    @Resource
    private ITeamService teamService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;

    @RequestMapping(value = "/addGather", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新建集合【领队端】", notes = "新建集合,需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse addGather(@RequestBody NoticeRequest noticeRequest) throws Exception {

        if (StringUtils.isEmpty(noticeRequest.getTeamId())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:团队ID【teamId】不能为空！");
        }

        if ( StringUtils.isEmpty(noticeRequest.getContent()) && StringUtils.isEmpty(noticeRequest.getVideoUrl()) ) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:集合内容【content】或者集合语音链接【videoUrl】不能为空！");
        }

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return user;
        }
        noticeRequest.setUserId(user.getUserEntity().getId());
        noticeRequest.setType(1);

        CommonResponse response = noticeService.saveNotice(noticeRequest);
        response.setRetMsg("成功发布一条集合信息！");
        return response;
    }

    @RequestMapping(value = "/getGatherList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取集合列表【领队端、游客端】", notes = "获取集合列表,需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。获取团队中集合列表时，必须传团队ID，否则获取的是全局集合列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public NoticeListResponse getGatherList(
            @ApiParam(value = "团队ID") @RequestParam(required = false) String teamId,
            @ApiParam(value = "已读上报字段,1:已读，0:未读") @RequestParam(required = false) Integer markRead) {

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return NoticeListResponse.createUserNotLoginResponse();
        }

        NoticeListResponse response =noticeService.getNoticeList(teamId, user.getUserEntity(), 1);
        if (markRead != null && markRead == 1) {
            noticeService.markReads(teamId, user.getUserEntity(), 1);
        }
        return response;

    }

    @RequestMapping(value = "/getGatherReplyList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取集合的回复列表【领队端、游客端】", notes = "获取集合的回复列表,需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public NoticeReplyResponse getGatherReplyList(
            @ApiParam(value = "集合ID", required = true) @RequestParam(required = true) Long id) {

        if (id == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:集合ID【id】不能为空！");
        }

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return NoticeReplyResponse.createUserNotLoginResponse();
        }

        return noticeReplyService.getNoticeRelayList(id, user.getUserEntity());
    }

    @RequestMapping(value = "/getGatherComfirmList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取集合确认情况列表【领队端】", notes = "获取集合确认情况列表,需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public NoticeDetailResponse getGatherComfirmList(
            @ApiParam(value = "集合ID", required = true) @RequestParam(required = true) Long id) {

        if (id == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:集合ID【id】不能为空！");
        }

        return noticeDetailService.getNoticeDetilList(id,1);
    }

    @RequestMapping(value = "/comfirmGather", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "确认集合【游客端】", notes = "确认集合,需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse comfirmGather(
            @ApiParam(value = "集合ID", required = true) @RequestParam(required = true) Long noticeId,
            @ApiParam(value = "团队ID", required = true) @RequestParam(required = true) String teamId) {

        if (noticeId == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:集合ID【noticeId】不能为空！");
        }
        if (StringUtils.isEmpty(teamId)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:团队ID【teamId】不能为空！");
        }

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return NoticeRespone.createUserNotLoginResponse();
        }

        DetailRequest detailRequest = new DetailRequest();
        detailRequest.setUserId(user.getUserEntity().getId());
        detailRequest.setNoticeId(noticeId);
        detailRequest.setTeamId(teamId);
        detailRequest.setType(1);
        return noticeDetailService.comfirmNotice(detailRequest);
    }

    @RequestMapping(value = "/replyGather", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "回复集合【领队端、游客端】", notes = "回复集合,需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。回复集合时，必须传入回复内容，换言之，不能是空回复也不能是空字符串")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse replyGather(@RequestBody DetailRequest detailRequest) {

        if (detailRequest.getNoticeId() == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:集合ID【noticeId】不能为空！");
        }
        if (StringUtils.isEmpty(detailRequest.getTeamId())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:团队ID【teamId】不能为空！");
        }
        if (StringUtils.isEmpty(detailRequest.getReply())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:回复内容【content】不能为空！");
        }

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return NoticeRespone.createUserNotLoginResponse();
        }
        detailRequest.setUserId(user.getUserEntity().getId());

        return noticeReplyService.replyNotice(detailRequest);
    }

    @RequestMapping(value = "/remindAgain", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "再次提醒【领队端】", notes = "再次提醒，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。调用该接口，系统会再一次推送消息给未确认的游客")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳，格式为yyyy-mm-dd HH:mm:ss", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse remindAgain(
            @ApiParam(value = "提醒实体", required = true) @RequestBody RemindRequest request) throws Exception {
        if (request.getNoticeId() == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:集合ID【noticeId】不能为空！");
        }
        if (CollectionUtils.isEmpty(request.getMemberIds())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:提醒成员ID列表【memberIds】不能为空！");
        }

        return noticeService.remindNoctice(request.getNoticeId(), request.getMemberIds());
    }
}
