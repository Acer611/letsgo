package com.umessage.letsgo.travel.controller.journey;

import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.journey.request.TouristRequest;
import com.umessage.letsgo.domain.vo.journey.response.TouristResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.DataVo;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 2016/8/31.
 */
@Controller
@RequestMapping(value = "/data")
public class StatisticalDataController {
    @Resource
    private IScheduleService scheduleService;
    @Resource
    private IMemberService memberService;
    @Resource
    private ITravelAgencyService travelAgencyService;

    /**
     * 获取游客统计数据
     * @param model
     * @return
     */
    @RequestMapping(value = "/searchTouristData", method = RequestMethod.GET)
    public String getTouristData(String continent, String destination, String startTime, String endTime, Model model) {

        TravelAgencyEntity travel = travelAgencyService.getCurrentTravel();
        TouristRequest request = new TouristRequest();
        request.setContinent(continent);
        request.setDestination(destination);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        request.setTravelId(travel.getId());

        // 获取行程团队的团队ID列表
        List<Long> teamIds = scheduleService.getTeamIdsInTeamJourney(request);

        TouristResponse response = null;
        if (!CollectionUtils.isEmpty(teamIds)) {
            response = memberService.analyzeTourist(teamIds);
        } else {
            response = new TouristResponse();
            response.setAreaDataList(new ArrayList<DataVo>());
            response.setAgeDataList(new ArrayList<DataVo>());
            response.setSexDataList(new ArrayList<DataVo>());
        }
        model.addAttribute("request",request);
        model.addAttribute("response", response);
        return "statistical/touristData";
    }
}
