package com.umessage.letsgo.travel.controller.system;

import com.umessage.letsgo.domain.po.system.RegionEntity;
import com.umessage.letsgo.service.api.system.IRegionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wendy on 2016/8/15.
 */
@Controller
@RequestMapping("/region")
public class RegionController {
    @Resource
    private IRegionService regionService;
    @RequestMapping(value={"/getRegion"},method= RequestMethod.GET)
    @ResponseBody
    public List<RegionEntity> getRegionPlace(@RequestParam String alias){
        List<RegionEntity> list=regionService.getRegionList("%"+alias+"%");
        return list;
    }
    @RequestMapping(value={"/getCountry"},method= RequestMethod.GET)
    @ResponseBody
    public List<RegionEntity> getCountry(@RequestParam String delta){
        List<RegionEntity> list=regionService.getCountryByDelta(delta);
        return list;
    }
}
