package com.umessage.letsgo.openapi.secuirty;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.EventRecordEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.request.TagsRequest;
import com.umessage.letsgo.domain.vo.security.respone.TagsResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.requset.EventRecordRequest;
import com.umessage.letsgo.domain.vo.team.respone.EventRecordResponse;
import com.umessage.letsgo.service.api.security.ITagsService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.security.IUserTagService;
import com.umessage.letsgo.service.api.team.IEventRecordService;
import com.umessage.letsgo.service.api.team.ILeaderService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "标签接口", description = "关于标签接口的相关操作")
@Controller
@RequestMapping(value = "/api/tags")
public class TagsController {
    @Resource
    private UserLoginHelper oauth2LoginHelper;
    @Resource
    private ITagsService tagsService;
    @Resource
    private IUserService userService;
    @Resource
    private IUserTagService userTagService;
    @RequestMapping(value = "/getTags", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取当前用户的所有通讯录标签【 领队端、游客端】", notes = "获取当前用户的所有通讯录标签，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public TagsResponse getTags() {
        // 获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return TagsResponse.createUserNotLoginResponse();
        }
        return tagsService.getTags(user.getUserEntity().getId());
    }

    @RequestMapping(value = "/saveTags", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存一个好友的多个通讯录标签【 领队端、游客端】", notes = "保存一个好友的多个通讯录标签，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse saveTags(
            @RequestParam(required = true) String name, @RequestParam(required = true) String labeledUserId
            ) {
//        if(StringUtils.isEmpty(name) ){
//            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：标签名称【name】不能为空！");
//        }
        if(StringUtils.isEmpty(labeledUserId) ){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：标签名称【labeledUserId】不能为空！");
        }

        // 获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return CommonResponse.createUserNotLoginResponse();
        }
        TagsRequest request=new TagsRequest();
        request.setUserId(user.getUserEntity().getId());
        request.setName(name);
        //根据用户腾讯组id获取用户id
        UserEntity userEntity= userService.selecUserByUserName(labeledUserId);
        if(userEntity==null){
            throw new BusinessException(ErrorConstant.NOT_FOUND, "被标签用户不存在！");
        }
        request.setLabeledUserId(userEntity.getId());
        return userTagService.saveTags(request);
    }

}
