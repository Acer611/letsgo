package com.umessage.letsgo.openapi.secuirty;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.request.FriendRequest;
import com.umessage.letsgo.domain.vo.security.respone.FriendListResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.security.IFriendsService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * Created by wendy on 2016/8/24.
 */
@Api(value = "好友关系相关接口", description = "关于好友关系的相关操作")
@Controller
@RequestMapping(value = "/api/friends")
public class FriendsController {

    @Resource
    private IFriendsService friendsService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;
    @Resource
    private IUserService userService;

    @RequestMapping(value = "/getAllFriends", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取好友（通讯录）【领队端、游客端】", notes = "获取好友（通讯录），需要用户登录后操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public FriendListResponse getAllFriends(@ApiParam(value = "好友类型【1:游客,2:同行,不填:全部】，非必填字段", required = false) @RequestParam(value="friendType", required = false) Integer friendType) {
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "当前用户登录失效");
        }
        Long userId = user.getUserEntity().getId();
        return friendsService.getAllFriends(userId, friendType);
    }

    @RequestMapping(value = "/saveFriend", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})

    public CommonResponse saveFriend(@RequestParam String friendTxUserId){
        if (StringUtils.isEmpty(friendTxUserId)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：【friendTxUserId】不能为空！");
        }

        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "当前用户登录失效");
        }
        UserEntity userEntity = user.getUserEntity();

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setUserId(userEntity.getId());
        friendRequest.setTxUserId(userEntity.getUserName());
        friendRequest.setFriendTxUserId(friendTxUserId);

        return friendsService.saveFriend(friendRequest);
    }
}
