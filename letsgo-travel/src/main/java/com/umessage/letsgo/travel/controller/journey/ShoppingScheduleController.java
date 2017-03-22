package com.umessage.letsgo.travel.controller.journey;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.journey.response.ShoppingScheduleResponse;
import com.umessage.letsgo.service.api.journey.IShoppingScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by zengguoqing on 2016/9/1.
 */
@Controller
@RequestMapping(value = "/shoppingschedule")
public class ShoppingScheduleController {
    /**
     * 初始数据获取
     */
    @Resource
    private IShoppingScheduleService shoppingScheduleService;

    /**
     * 根据id删除酒店
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteShoppingSchedule")
    @ResponseBody
    public ShoppingScheduleResponse deleteShoppingSchedule(@RequestParam Long id) {
        if (id == null){
            ShoppingScheduleResponse.createEmptyParameterResponse("id不能为空");
        }
        ShoppingScheduleResponse shoppingScheduleResponse = new ShoppingScheduleResponse();
        try {
            shoppingScheduleService.delete(id);
        }catch (Exception e){
            shoppingScheduleResponse.setRetCode(ErrorConstant.INTERNAL_SERVER_ERROR);
            shoppingScheduleResponse.setRetMsg("删除失败");
        }
        return shoppingScheduleResponse;
    }

}
