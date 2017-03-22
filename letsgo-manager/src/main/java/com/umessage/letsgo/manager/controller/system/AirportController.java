package com.umessage.letsgo.manager.controller.system;

import com.umessage.letsgo.domain.po.system.AirportEntity;
import com.umessage.letsgo.service.api.system.IAirportService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 2016/8/15.
 */
@Controller
public class AirportController {

    @Resource
    private IAirportService airportService;

    @RequestMapping(value = "/getAirportData")
    @ResponseBody
    public List<AirportEntity> getAirportList(@RequestParam String query) {
        List<AirportEntity> airportEntityList = airportService.getAirportList(query);
        if (CollectionUtils.isEmpty(airportEntityList)) {
            return new ArrayList<>();
        }
        return airportEntityList;
    }
}
