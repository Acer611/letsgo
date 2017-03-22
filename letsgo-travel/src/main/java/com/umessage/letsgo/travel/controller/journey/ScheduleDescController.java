package com.umessage.letsgo.travel.controller.journey;

import com.umessage.letsgo.domain.po.journey.ScheduleDescEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleDescRequest;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleDescResponse;
import com.umessage.letsgo.service.api.journey.IScheduleDescService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by gaofei on 2017/2/20.
 */
@Api(value = "旅行社端其他说明管理接口", description = "关于旅行社端其他说明的相关操作")
@Controller
@RequestMapping(value = "/schedule")
public class ScheduleDescController {

    private Logger logger = Logger.getLogger(ScheduleDescController.class);

    @Resource
    private ITravelAgencyService travelService;
    @Resource
    private IScheduleDescService scheduleDescService;

    /**
     * 添加行程其他说明
     * @param scheduleDescRequest
     * @return
     */
    @RequestMapping(value ="/createScheduleDesc", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse  createScheduleDesc(@RequestBody ScheduleDescRequest scheduleDescRequest){
     /*   scheduleDetailDesc = new ScheduleDescEntity();
        scheduleDetailDesc.setScheduleId(238L);
        scheduleDetailDesc.setTitle("啥子哟");
        scheduleDetailDesc.setContent("大劫案算了");*/

        logger.info("添加行程其他说明信息");
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        if(travel == null || travel.getId() == null) return CommonResponse.createUserNotLoginResponse();

        ScheduleDescResponse response = scheduleDescService.createScheduleDesc(scheduleDescRequest.getScheduleDescs());

        return response;
    }

    /**
     * 根据行程的id 获取行程其他说明信息
     * @param scheduleId
     * @return
     */
    @RequestMapping(value ="/getScheduleDesc", method = RequestMethod.GET)
    @ResponseBody
    public List<ScheduleDescEntity> getScheduleDesc(Long scheduleId){
        logger.info("根据行程的id 获取行程其他说明信息");
        return scheduleDescService.getScheduleDescByScheduleId(scheduleId);
    }

    /**
     * 修改行程其他说明信息
     * @param scheduleDescRequest
     * @return
     */
    @RequestMapping(value ="/updateScheduleDesc", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse  updateScheduleDesc(@RequestBody ScheduleDescRequest scheduleDescRequest){
       /* scheduleDetailDesc = new ScheduleDescEntity();
        scheduleDetailDesc.setScheduleId(238L);
        scheduleDetailDesc.setId(1L);
        scheduleDetailDesc.setTitle("test是多少aaaa");
        scheduleDetailDesc.setContent("fjsjfljslfjslfjls分手了就分了撒浪费");*/

        logger.info("修改行程其他说明信息");
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        if(travel == null || travel.getId() == null) return CommonResponse.createUserNotLoginResponse();
        CommonResponse response = null;
        List<ScheduleDescEntity> scheduleDescs = scheduleDescRequest.getScheduleDescs();
        if (scheduleDescs != null && !scheduleDescs.isEmpty()){
            scheduleDescService.deleteByScheduleId(scheduleDescs.get(0).getScheduleId());
            response = scheduleDescService.createScheduleDesc(scheduleDescs);
        }
        return response;
    }

    /**
     * 根据ID删除行程其他说明信息
     * @param Id
     * @return
     */
    @RequestMapping(value ="/deleteScheduleDesc", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse  deleteScheduleDesc(Long Id){
        logger.info("根据ID删除行程其他说明信息");
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        if(travel == null || travel.getId() == null) return CommonResponse.createUserNotLoginResponse();
        CommonResponse response = scheduleDescService.deleteScheduleDescById(Id);
        return response;
    }

}
