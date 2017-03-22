package com.umessage.letsgo.travel.controller.journey;

import com.umessage.letsgo.domain.vo.journey.response.StopDataResponse;
import com.umessage.letsgo.service.api.journey.IStopDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by wendy on 2016/8/25.
 */
@Controller
@RequestMapping(value = "/stopData")
public class StopDataController {

    @Resource
    private IStopDataService stopDataService;

    /**
     * 初始数据获取
     */
    @RequestMapping(value = "/getInitStopData", method = RequestMethod.GET)
    public String getInitStopData(Long id, Model model) {
        StopDataResponse response = stopDataService.getStopData(id);

        model.addAttribute("response",response);
        return "journey/schedulemap";
    }

}
