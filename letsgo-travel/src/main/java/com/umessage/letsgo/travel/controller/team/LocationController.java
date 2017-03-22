package com.umessage.letsgo.travel.controller.team;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.vo.activity.response.LocationTrackResponse;
import com.umessage.letsgo.service.api.activity.ILocationHistoryService;
import com.umessage.letsgo.service.api.activity.IWebLocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by zhajl on 17/2/7.
 */

@Controller
@RequestMapping(value = "/location")
public class LocationController {
    @Resource
    private ILocationHistoryService locationHistoryService;

    @RequestMapping(value = "/getMemberTrack", method = RequestMethod.GET)
    public String getMemberTrack(Long tId, Long userId, Model model) throws Exception {

        LocationTrackResponse response = locationHistoryService.getMemberTrack(tId, userId);
        if (response.getRetCode() == ErrorConstant.NOT_FOUND){
            throw new BusinessException(response.getRetCode(), response.getRetMsg());
        }

        model.addAttribute("locationTrack", response);
        return "team/memberTrack";
    }
}
