package com.umessage.letsgo.travel.controller.system;

import com.umessage.letsgo.domain.po.system.SpotEntity;
import com.umessage.letsgo.service.api.system.ISpotService;
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
public class SpotController {
    @Resource
    private ISpotService spotService;

    @RequestMapping(value = "/getSpotData")
    @ResponseBody
    public List<SpotEntity> getSpotListLikeQuery(@RequestParam String query, @RequestParam String cities) {
        List<SpotEntity> spotEntityList = spotService.getSpotList(query, cities);
        if (CollectionUtils.isEmpty(spotEntityList)) {
            return new ArrayList<>();
        }
        return spotEntityList;
    }
}
