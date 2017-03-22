package com.umessage.letsgo.openapi.team;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.team.requset.GroupRequest;
import com.umessage.letsgo.domain.vo.team.respone.GroupResponse;
import com.umessage.letsgo.service.api.team.IGroupService;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "分组接口", description = "关于分组的相关操作，包括新建小组、删除小组、更新小组等接口")
@Controller
@RequestMapping(value = "/api/group")
public class GroupController {
    @Resource
    private IGroupService groupService;

    @RequestMapping(value = "/addGroup", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新建小组【领队端】", notes = "新建小组，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse addGroup(@RequestBody GroupRequest groupRequest) {

        if (StringUtils.isEmpty(groupRequest.getTeamId())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【teamId】不能为空！");
        }
        if (groupRequest.getLeaderId() == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：组长ID【leaderId】不能为空！");
        }
        if (StringUtils.isEmpty(groupRequest.getName())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：小组名称【name】不能为空！");
        }
        if (CollectionUtils.isEmpty(groupRequest.getMemberIds())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：小组组员ID列表【memberIds】不能为空！");
        }

        return groupService.createGroup(groupRequest);

    }

    @RequestMapping(value = "/deleteGroup", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除小组【领队端】", notes = "删除小组，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse deleteGroup(
            @ApiParam(value = "小组ID", required = true) @RequestParam(required = true) Long groupId) {

        if (groupId == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：小组ID【groupId】不能为空！");
        }

        return groupService.delGroup(groupId);

    }

    @RequestMapping(value = "/modifyGroup", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改小组【领队端】", notes = "修改小组，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse modifyGroup(@RequestBody GroupRequest groupRequest) {

        if (groupRequest.getGroupId() == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：小组ID【groupId】不能为空！");
        }
        if (StringUtils.isEmpty(groupRequest.getTeamId())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队ID【teamId】不能为空！");
        }
        if (groupRequest.getLeaderId() == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：组长ID【leaderId】不能为空！");
        }
        if (StringUtils.isEmpty(groupRequest.getName())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：小组名称【name】不能为空！");
        }
        if (CollectionUtils.isEmpty(groupRequest.getMemberIds())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：小组组员ID列表【memberIds】不能为空！");
        }

        return groupService.modifyGroup(groupRequest);

    }
}
