package com.umessage.letsgo.h5.team;

import com.umessage.letsgo.domain.vo.journey.response.vo.TravelAgencyInfoVo;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * Created by LiZhen on 2016/9/26.
 */
@Api(value = "web团队接口", description = "关于团队的相关操作，包括获取团队列表、获取团队详情、添加导游等接口")
@Controller
@RequestMapping(value = "/web/team")
public class WebTeamController {
    @Resource
    private IScheduleService iScheduleService;

    @RequestMapping(value = "/getTravelAgencyInfo", method = RequestMethod.GET)
    @ApiOperation(value = "根据腾迅云id获取接团社信息和地接社信息", notes = "根据腾迅云id获取接团社信息和地接社信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public String  getTravelAgencyInfo(@ApiParam(value = "腾迅云id", required = true) @RequestParam(required = true) String teamId, Model model) {
        // 根据腾迅云id获取接团社信息和地接社信息
        TravelAgencyInfoVo travelAgencyInfoVo = iScheduleService.getTravelAgencyInfoVoByTeamId(teamId);
        model.addAttribute("response", travelAgencyInfoVo);
        return "travelAgency/travelAgency";
    }
}
