package com.umessage.letsgo.travel.controller.team;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.po.team.EventRecordEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.requset.EventRecordRequest;
import com.umessage.letsgo.domain.vo.team.requset.GuideMemberRequest;
import com.umessage.letsgo.domain.vo.team.requset.LeaderRequest;
import com.umessage.letsgo.domain.vo.team.respone.*;
import com.umessage.letsgo.service.api.team.IEventRecordService;
import com.umessage.letsgo.service.api.team.ILeaderService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "事件接口", description = "关于事件的相关操作，包括获取事件列表")
@Controller
@RequestMapping(value = "/event")
public class EventRecordController {
    @Resource
    private IEventRecordService eventRecordService;
    @Resource
    private ILeaderService leaderService;
    @Resource
    private ITeamService teamService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;
    @Resource
    private ITravelAgencyService travelService;

    @RequestMapping("getEventList")
    public String getEventList(EventRecordRequest request, Model model) {
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        if(travel == null || travel.getId() == null) return "redirect:/login";
        request.setTravelId(travel.getId());
        EventRecordPageResponse response= eventRecordService.getEventList(request);
        model.addAttribute("response",response);
        return "team/eventList";
    }

}
