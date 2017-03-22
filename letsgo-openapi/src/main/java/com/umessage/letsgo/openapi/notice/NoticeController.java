package com.umessage.letsgo.openapi.notice;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.po.notice.NoticeSignEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.notice.request.*;
import com.umessage.letsgo.domain.vo.notice.respone.*;
import com.umessage.letsgo.domain.vo.security.respone.UserAuthorityResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import com.umessage.letsgo.service.api.notice.INoticeDetailService;
import com.umessage.letsgo.service.api.notice.INoticeReplyService;
import com.umessage.letsgo.service.api.notice.INoticeService;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "通知接口", description = "关于通知的相关操作，包括新建通知，获取通知列表，获取通知确认详情等接口")
@Controller
@RequestMapping(value = "/api/notice")
public class NoticeController {

    private Logger logger = LogManager.getLogger(NoticeController.class.getName());
    @Resource
    private INoticeService noticeService;
    @Resource
    private INoticeDetailService noticeDetailService;
    @Resource
    private INoticeReplyService noticeReplyService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;

    @RequestMapping(value = "/addNotice", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新建通知【领队端】", notes = "新建通知,需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse addNotice(@ApiParam(value = "通知实体", required = true) @RequestBody NoticeRequest noticeRequest) throws Exception {

        if (StringUtils.isEmpty(noticeRequest.getTeamId())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:团队ID【teamId】不能为空！");
        }

        if ( StringUtils.isEmpty(noticeRequest.getContent()) && StringUtils.isEmpty(noticeRequest.getVideoUrl()) ) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:通知内容【content】或者通知语音链接【videoUrl】不能为空！");
        }

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return user;
        }
        noticeRequest.setUserId(user.getUserEntity().getId());
        noticeRequest.setType(2);

        return noticeService.saveNotice(noticeRequest);
    }

    @RequestMapping(value = "/getNoticeList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取通知列表【领队端、游客端】", notes = "获取通知列表,需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。获取团内通知时，必须传团队ID，获取全局通知时，必须传用户ID")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public NoticeListResponse getNoticeList(
            @ApiParam(value = "团队ID") @RequestParam(required = false) String teamId,
            @ApiParam(value = "已读上报字段，1:已读，0:未读") @RequestParam(required = false) Integer markRead) {

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return NoticeListResponse.createUserNotLoginResponse();
        }

        //NoticeListResponse response = noticeService.getNoticeList(teamId, user.getUserEntity(), 2);
        logger.info("调用 openApi 获取通知列表方法 getNoticeList()...");
        NoticeListResponse messageResponse = noticeService.getNoticeMessages(teamId, user.getUserEntity(), 2);
        if (markRead != null && markRead == 1) {
            noticeService.markReads(teamId, user.getUserEntity(), 2);
        }
       return messageResponse;
    }

    @RequestMapping(value = "/getNoticeReplyList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取通知详情及回复列表【领队端、游客端】", notes = "获取通知详情及回复列表,需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public NoticeReplyResponse getNoticeReplyList(
            @ApiParam(value = "通知ID", required = true) @RequestParam(required = true) Long id) {

        if (id == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:通知ID【id】不能为空！");
        }

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return NoticeReplyResponse.createUserNotLoginResponse();
        }

        return noticeReplyService.getNoticeRelayList(id, user.getUserEntity());
    }

    @RequestMapping(value = "/getNoticeComfirmList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取通知确认详情列表【领队端】", notes = "获取通知确认详情列表,需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public NoticeDetailResponse getNoticeComfirmList(
            @ApiParam(value = "通知ID", required = true) @RequestParam(required = true) Long id) {

        if (id == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:通知ID【id】不能为空！");
        }

        return noticeDetailService.getNoticeDetilList(id,2);
    }

    @RequestMapping(value = "/comfirmNotice", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "确认通知【游客端】", notes = "确认通知,需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse comfirmNotice(
            @ApiParam(value = "通知ID", required = true) @RequestParam(required = true) Long noticeId,
            @ApiParam(value = "团队ID", required = true) @RequestParam(required = true) String teamId) {

        if (noticeId == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:通知ID【noticeId】不能为空！");
        }
        if (StringUtils.isEmpty(teamId)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:团队ID【teamId】不能为空！");
        }

        // 获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return NoticeRespone.createUserNotLoginResponse();
        }

        DetailRequest detailRequest = new DetailRequest();
        detailRequest.setUserId(user.getUserEntity().getId());
        detailRequest.setNoticeId(noticeId);
        detailRequest.setTeamId(teamId);
        detailRequest.setType(2);
        return noticeDetailService.comfirmNotice(detailRequest);
    }

    @RequestMapping(value = "/replyNotice", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "回复通知【领队端、游客端】", notes = "回复通知,需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。回复通知时，必须传入回复内容，换言之，不能是空回复也不能是空字符串")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse replyNotice(@ApiParam(value = "回复实体", required = true) @RequestBody DetailRequest detailRequest) {

        if (detailRequest.getNoticeId() == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:通知ID【noticeId】不能为空！");
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
    @ApiOperation(value = "再次提醒【领队端、游客端】", notes = "再次提醒，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。调用该接口，系统会再一次推送消息给未确认的游客")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳，格式为yyyy-mm-dd HH:mm:ss", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse remindAgain(@ApiParam(value = "提醒实体", required = true) @RequestBody RemindRequest request) throws Exception {
        if (request.getNoticeId() == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:集合ID【noticeId】不能为空！");
        }
        if (CollectionUtils.isEmpty(request.getMemberIds())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:提醒成员ID列表【memberIds】不能为空！");
        }

        return noticeService.remindNoctice(request.getNoticeId(), request.getMemberIds());
    }

    @RequestMapping(value = "/getLastMessage", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取用户最新消息【领队端和游客端】", notes = "获取用户最新消息【领队端和游客端】，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams( {
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    private LastMessageResponse getLastMessage(
            @ApiParam(value = "团队腾迅groupid", required = true) @RequestParam(required = true) String teamId) {

        if (org.apache.commons.lang.StringUtils.isBlank(teamId)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队腾迅groupid不能为空！");
        }
        // 获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return LastMessageResponse.createUserNotLoginResponse();
        }
        NoticeRequest request =new NoticeRequest();
        request.setTeamId(teamId);
        request.setUserId(user.getUserEntity().getId());
        LastMessageResponse response = noticeService.getLastMessage(request);

        return response;
    }


    @RequestMapping(value = "/getNoticeDetail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取通知签名详情【领队端、游客端】", notes = "获取通知列表,需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。获取团内通知时，必须传团队ID，获取全局通知时，必须传用户ID")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public NoticeSignDetailResponse getNoticeDetail(
            @ApiParam(value = "团队ID") @RequestParam(required = true) String teamId,
            @ApiParam(value = "noticeID ") @RequestParam(required = true) Long noticeId,
            @ApiParam(value = "roleStatus 当前用户在当前团队的身份标识【值为1，代表领队；值为2，代表导游；值3，代表游客】 ") @RequestParam(required = true) Integer roleStatus) {

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return NoticeSignDetailResponse.createUserNotLoginResponse();
        }
        logger.info("进入获取通知签名明细的方法...");
        NoticeSignDetailResponse response = noticeDetailService.getNoticeSignInfo(teamId,noticeId,roleStatus,user.getUserEntity());
        return response;
    }


    @RequestMapping(value = "/saveSignImages", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取通知签名详情【领队端、游客端】", notes = "获取通知列表,需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。获取团内通知时，必须传团队ID，获取全局通知时，必须传用户ID")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse saveSignImages(@ApiParam(value = "签名实体", required = true) @RequestBody NoticeSignRequest noticeSignRequest) {

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return CommonResponse.createUserNotLoginResponse();
        }
        logger.info("进入保存签名图片的方法...");
        CommonResponse response = noticeDetailService.saveSignImages(noticeSignRequest,user.getUserEntity());
        return response;
    }

}
