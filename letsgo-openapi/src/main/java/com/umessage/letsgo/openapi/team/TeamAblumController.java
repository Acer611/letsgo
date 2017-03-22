package com.umessage.letsgo.openapi.team;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.TeamAblumEntity;
import com.umessage.letsgo.domain.vo.team.respone.TeamAblumMaps;
import com.umessage.letsgo.domain.vo.team.respone.TeamAblumRes;
import com.umessage.letsgo.domain.vo.team.respone.TeamAblumResponse;
import com.umessage.letsgo.service.api.team.ITeamAblumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(value = "团队接口", description = "关于团队相册的相关操作，包括获取团队相册列表，点击缩略图")
@Controller
@RequestMapping(value = "/api/teamAblum")
public class TeamAblumController {
    @Resource
    private ITeamAblumService teamAblumService;
    @RequestMapping(value = "/getTeamAlbum",method = RequestMethod.POST )
    @ResponseBody
    @ApiOperation(value = "获取团队相册列表【领队端、旅行社端】", notes = "获取团队相册列表，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public TeamAblumResponse getTeamAlbum( @RequestParam String  teamId,@RequestParam Integer pageNum) {
        TeamAblumResponse t=new TeamAblumResponse();
        //按时间分页逻辑
        Date d = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(d);
        ParsePosition pos = new ParsePosition(0);
        Date currentTime = formatter.parse(dateString, pos);

        int num1 = (pageNum-1)*3;

        int num2 = pageNum*3-1;

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(currentTime);
        int startNum = num2 * (-1);
        calendar1.add(Calendar.DATE,startNum);
        Date startDate = calendar1.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(currentTime);
        int endNum = num1 * (-1);
        calendar2.add(Calendar.DATE,endNum);
        Date endDate = calendar2.getTime();

        //按分页获取团队相册
        List<TeamAblumMaps> teamAblumMapsList = teamAblumService.getTeamAblumByTime(teamId, pageNum, startDate, endDate);
        t.setList(teamAblumMapsList);
        return t;
    }


}
