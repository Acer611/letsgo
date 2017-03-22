package com.umessage.letsgo.openapi.activity;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.vo.activity.request.RoomRequest;
import com.umessage.letsgo.domain.vo.activity.response.RoomResponse;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import com.umessage.letsgo.service.api.activity.IRoomService;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "分房接口", description = "关于分房的相关操作，包括保存分房方案，获取分房方案接口")
@Controller
@RequestMapping(value = "/api/room")
public class RoomController {
    @Resource
    private IRoomService roomService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;

    @RequestMapping(value = "/addTeamRoomDetail", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存分房方案【领队端】", notes = "保存分房方案，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse addTeamRoomDetail(@ApiParam(value = "分房方案", required = true) @RequestBody List<RoomRequest> roomRequests) {

        if (CollectionUtils.isEmpty(roomRequests)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:房间请求列表不能为空！");
        }

        return roomService.createRoom(roomRequests);
    }

    @RequestMapping(value = "/getTeamRoomDetail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取团队分房情况【领队端】", notes = "获取团队分房情况，该接口用于领队端，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public RoomResponse getTeamRoomDetail(
            @ApiParam(value = "团队ID", required = true) @RequestParam(required = true) String teamId) {


        if (teamId == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【teamId】不能为空！");
        }

        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return RoomResponse.createUserNotLoginResponse();
        }

        return roomService.getTeamRoomDetailList(teamId, user.getUserEntity().getId());
    }

    @RequestMapping(value = "/getPersonalRoomDetail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取个人分房情况【游客端】", notes = "获取个人分房情况，该接口用于游客端，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public RoomResponse getPersonalRoomDetail(
            @ApiParam(value = "团队ID", required = true) @RequestParam(required = true) String teamId) {

        if (teamId == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【teamId】不能为空！");
        }

        // 1、获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return RoomResponse.createUserNotLoginResponse();
        }

        return roomService.getPersonalRoomDetailList(teamId,user.getUserEntity().getId());
    }
}
