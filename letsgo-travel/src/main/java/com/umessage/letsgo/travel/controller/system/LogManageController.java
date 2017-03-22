package com.umessage.letsgo.travel.controller.system;

import com.umessage.letsgo.domain.po.system.RegionEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.system.request.LogManageRequest;
import com.umessage.letsgo.domain.vo.system.respone.LogManageResponse;
import com.umessage.letsgo.domain.vo.team.requset.EventRecordRequest;
import com.umessage.letsgo.domain.vo.team.respone.EventRecordPageResponse;
import com.umessage.letsgo.service.api.system.ILogManageService;
import com.umessage.letsgo.service.api.system.IRegionService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by pw on 2016/9/9.
 */
@Controller
@RequestMapping("/logManage")
public class LogManageController {
    @Resource
    private ILogManageService logManageService;
    @Resource
    private ITravelAgencyService travelService;

    /**
     * 日志管理
     */
    @RequestMapping("getLogList")
    public String getLogList(LogManageRequest request, Model model) {
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        if(travel == null || travel.getId() == null) return "redirect:/login";
        request.setUserId(travel.getUserId());
        request.setTravelId(travel.getId());
        LogManageResponse response= logManageService.getLogList(request);
        model.addAttribute("response",response);
        model.addAttribute("request",request);
        return "LogManagement/LogManagements";
    }
    @RequestMapping("goLogList")
    public String goLogList(LogManageRequest request, Model model) {
        return "LogManagement/LogManagement";
    }
}
