package com.umessage.letsgo.openapi.message;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.message.request.QMessageRequest;
import com.umessage.letsgo.domain.vo.message.response.MessageListResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.message.IQMessageService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by gaofei on 2017/1/12.
 */

@Api(value = "问题反馈的接口", description = "关于问题反馈相关操作")
@Controller
@RequestMapping(value = "/api/message")
public class MessageController {
    private Logger logger = Logger.getLogger(MessageController.class);

    @Resource
    private IQMessageService qMessageService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;

    @RequestMapping(value = "/getMessageList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取我的反馈问题列表", notes = "获取反馈信息列表，需要用户登录后才能操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public MessageListResponse getMessageList(@ApiParam(value = "腾讯组ID") @RequestParam(required = true) String tGroupId) {

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return MessageListResponse.createUserNotLoginResponse();
        }
        MessageListResponse messageListResponse = qMessageService.getMessageListByTGroupId(tGroupId,user.getUserEntity().getId());

        return messageListResponse;
    }


    @RequestMapping(value = "/postMessage", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用戶反馈问题", notes = "用戶反馈，需要用户登录后才能操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse postMessage(@ApiParam(value = "信息反馈实体", required = true) @RequestBody QMessageRequest messageRequest) {

        CommonResponse commonResponse = new CommonResponse();
        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return MessageListResponse.createUserNotLoginResponse();
        }

        if(messageRequest.getMessage()==null||null==messageRequest.gettGroupId()){
            commonResponse.setRetCode(-1);
            commonResponse.setRetMsg("反馈消息或腾讯组ID不能为空");
            return commonResponse;
        }

         commonResponse = qMessageService.postMessage(messageRequest,user.getUserEntity().getId());

        return commonResponse;
    }


}
