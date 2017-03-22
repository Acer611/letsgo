package com.umessage.letsgo.openapi.team;

import com.alibaba.fastjson.JSONObject;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.po.team.EventRecordEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.requset.EventRecordRequest;
import com.umessage.letsgo.domain.vo.team.respone.EventRecordResponse;
import com.umessage.letsgo.service.api.team.IEventRecordService;
import com.umessage.letsgo.service.api.team.ILeaderService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@Api(value = "事件接口", description = "关于事件的相关操作，包括获取事件列表")
@Controller
@RequestMapping(value = "/api/event")
public class EventRecordController {
    @Resource
    private IEventRecordService eventRecordService;
    @Resource
    private ILeaderService leaderService;
    @Resource
    private ITeamService teamService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;

    @RequestMapping(value = "/getEventRecordList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取事件记录列表【领队端】", notes = "获取获取事件记录列表，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public EventRecordResponse getEventRecordList(
            @ApiParam(value = "腾迅groupid", required = true) @RequestParam(required = true) String teamId) {
        if (StringUtils.isEmpty(teamId)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数:腾迅groupid不能为空！");
        }
        // 获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return EventRecordResponse.createUserNotLoginResponse();
        }
        EventRecordRequest request =new EventRecordRequest();
        request.setTeamId(teamId);
        request.setCreateBy(user.getUserEntity().getId());
        List<EventRecordEntity> eventRecordList =eventRecordService.getEventRecordList(request);
        TeamEntity teamEntity =teamService.getTeamByTXGroupId(teamId);
        Integer status=null;
        if(teamEntity!=null && teamEntity.getStatus()!=null){
            status=teamEntity.getStatus();
        }
        EventRecordResponse response =new EventRecordResponse();
        response.setEventRecordList(eventRecordList);
        response.setTeamStatus(status);
        return response;
    }

    @RequestMapping(value = "/addEventRecord", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新增事件记录【领队端】", notes = "新增事件记录，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse addEventRecord(
            @RequestBody EventRecordRequest request
            ) {
        if(StringUtils.isEmpty(request.getRecordTime())|| request.getTeamId() == null ){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：所需参数不能为空！");
        }
        // 获取当前登录的用户信息
        UserResponse user = oauth2LoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS){
            return EventRecordResponse.createUserNotLoginResponse();
        }
        //创建人id
        request.setCreateBy(user.getUserEntity().getId());
        return eventRecordService.addEventRecord(request);
    }

}
