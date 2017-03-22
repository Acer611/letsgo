package com.umessage.letsgo.travel.controller.journey;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.vo.journey.response.OwnExpenseScheduleResponse;
import com.umessage.letsgo.service.api.journey.IOwnExpenseScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by zengguoqing on 2016/9/1.
 */
@Controller
@RequestMapping(value = "/ownexpenseschedule")
public class OwnExpenseScheduleController {
    /**
     * 初始数据获取
     */
    @Resource
    private IOwnExpenseScheduleService ownExpenseScheduleService;

    /**
     * 根据id删除酒店
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteOwnExpenseSchedule")
    @ResponseBody
    public OwnExpenseScheduleResponse deleteOwnExpenseSchedule(@RequestParam Long id) {
        if (id == null){
            OwnExpenseScheduleResponse.createEmptyParameterResponse("id不能为空");
        }
        OwnExpenseScheduleResponse ownExpenseScheduleResponse = new OwnExpenseScheduleResponse();
        try {
            ownExpenseScheduleService.delete(id);
        }catch (Exception e){
            ownExpenseScheduleResponse.setRetCode(ErrorConstant.INTERNAL_SERVER_ERROR);
            ownExpenseScheduleResponse.setRetMsg("删除失败");
        }
        return ownExpenseScheduleResponse;
    }

}
