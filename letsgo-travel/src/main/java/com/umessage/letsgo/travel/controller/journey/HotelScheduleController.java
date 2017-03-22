package com.umessage.letsgo.travel.controller.journey;

import com.alibaba.fastjson.JSONObject;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.HotelScheduleEntity;
import com.umessage.letsgo.domain.vo.journey.response.HotelScheduleResponse;
import com.umessage.letsgo.service.api.journey.IHotelScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by zengguoqing on 2016/9/1.
 */
@Controller
@RequestMapping(value = "/hotelschedule")
public class HotelScheduleController {
    /**
     * 初始数据获取
     */
    @Resource
    private IHotelScheduleService hotelScheduleService;


    /*
    保存酒店
     */
    @RequestMapping(value = "/saveHotelSchedule", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public HotelScheduleResponse saveHotelSchedule(@RequestBody HotelScheduleEntity hotelScheduleEntity) {
        HotelScheduleResponse hotelScheduleResponse = new HotelScheduleResponse();
        try {
            hotelScheduleService.insert(hotelScheduleEntity);
        }catch (Exception e){
            hotelScheduleResponse.setRetCode(ErrorConstant.INTERNAL_SERVER_ERROR);
            hotelScheduleResponse.setRetMsg("添加失败");
        }
        return hotelScheduleResponse;
    }

    /**
     * 更新每日行程
     */
    @RequestMapping(value = "hotelScheduleUpdate", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public JSONObject hotelScheduleUpdate(HotelScheduleEntity hotelScheduleEntity){
        JSONObject js=new JSONObject();
        try {
            hotelScheduleService.update(hotelScheduleEntity);
            js.put("code",0);
            js.put("message","更新成功");
        }catch (Exception e){
            js.put("code",-1);
            js.put("message","更新失败");
        }
        return js;
    }

    /**
     * 根据id查询酒店
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/getHotelScheduleById", method = RequestMethod.GET)
    public String getHotelScheduleById(@RequestParam Long id, Model model) {
        HotelScheduleEntity hotelScheduleEntity = hotelScheduleService.select(id);
        model.addAttribute("hotelScheduleEntity",hotelScheduleEntity);
        return "journey/";
    }


    /**
     * 根据id删除酒店
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteHotelSchedule")
    @ResponseBody
    public HotelScheduleResponse deleteHotelSchedule(@RequestParam Long id) {
        if (id == null){
            HotelScheduleResponse.createEmptyParameterResponse("id不能为空");
        }
        HotelScheduleResponse hotelScheduleResponse = new HotelScheduleResponse();
        try {
            hotelScheduleService.delete(id);
        }catch (Exception e){
            hotelScheduleResponse.setRetCode(ErrorConstant.INTERNAL_SERVER_ERROR);
            hotelScheduleResponse.setRetMsg("删除失败");
        }
        return hotelScheduleResponse;
    }

}
