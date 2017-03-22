package com.umessage.letsgo.travel.controller.journey;

import com.umessage.letsgo.domain.po.journey.ScheduleDetailScenicEntity;
import com.umessage.letsgo.domain.po.team.ScenicAgencyEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScenicResponse;
import com.umessage.letsgo.service.api.journey.IScheduleDetailScenicService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import io.swagger.annotations.Api;
import org.apache.logging.log4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by gaofei on 2017/2/15.
 */
@Api(value = "旅行社端每日行程景点信息管理接口", description = "关于旅行社端每日行程景点的相关操作")
@Controller
@RequestMapping(value = "/schedule/scenic")
public class ScheduleDetailScenicController {

    private Logger logger = LogManager.getLogger(ScheduleDetailScenicController.class.getName());
    @Resource
    private IScheduleDetailScenicService scheduleDetailScenicService;
    @Resource
    private ITravelAgencyService travelService;

    /**
     * 保存或修改每日景点信息到景点私有库
     * @param scenicEntity 景点私有库实体 修改时有参数实体中有ID,插入时没有id
     * @return
     */
    @RequestMapping(value ="/saveScheduleDetailScenic", method = RequestMethod.POST)
    @ResponseBody
    public ScenicResponse saveScheduleDetailScenic(@RequestBody ScenicAgencyEntity scenicEntity){
        TravelAgencyEntity travelEntity = travelService.getCurrentTravel();
        if(null == travelEntity){
            return (ScenicResponse) ScenicResponse.createUserNotLoginResponse();
        }
        if(null == scenicEntity){
            return (ScenicResponse) ScenicResponse.createUserNotLoginResponse();
        }
        ScenicResponse scenicResponse = scheduleDetailScenicService.saveScheduleDetailScenicToTravel(travelEntity,scenicEntity);

        return scenicResponse;
    }

    /**
     * 模糊匹配景点名称，查询景点列表
     * @param name
     * @param cities 城市的id 多个以 “，”分隔
     */
    @RequestMapping(value ="/getScenic")
    @ResponseBody
    public ScenicResponse getScenic(String name, String cities){
        logger.info("进入景点搜索方法......");
        TravelAgencyEntity travelEntity = travelService.getCurrentTravel();
        if(null == travelEntity){
            return (ScenicResponse) ScenicResponse.createUserNotLoginResponse();
        }
        ScenicResponse scenicResponse = scheduleDetailScenicService.searchScenic(name,cities,travelEntity);
        return scenicResponse;
    }


    /**
     * 根据景点ID 获取景点和照片的信息
     * @param scenId
     * @return
     */
    @RequestMapping(value ="/getScenicByScenId")
    @ResponseBody
    public ScheduleDetailScenicEntity getScenic(Long scenId){
        TravelAgencyEntity travelEntity = travelService.getCurrentTravel();
        if(null == travelEntity){
            return null;
        }
        ScheduleDetailScenicEntity scenicEntity = scheduleDetailScenicService.getScenicByScenicId(scenId);
        return scenicEntity;
    }

    /**
     * 根据ID 删除景点私有库的数据信息
     * @param Id
     * @return
     */
    @RequestMapping(value ="/deleteScheduleDetailScenic")
    @ResponseBody
    public ScenicResponse deleteScheduleDetailScenic(Long Id) {
        TravelAgencyEntity travelEntity = travelService.getCurrentTravel();
        if(null == travelEntity){
            return null;
        }
        ScenicResponse response = scheduleDetailScenicService.deleteScheduleDetailScenic(travelEntity.getId(),Id);
        return response;
    }




    ////////////////////////////

    /**
     * 删除每日景点的信息根据景点id(景点每日行程关联关系表)
     * @param id
     * @return
     */
    @RequestMapping(value ="/removeScheduleDetailScenic", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse removeScheduleDetailScenic(Long id){
        TravelAgencyEntity travelEntity = travelService.getCurrentTravel();
        if(null == travelEntity){
            return CommonResponse.createUserNotLoginResponse();
        }
        CommonResponse commonResponse = scheduleDetailScenicService.removeScheduleDetailScenic(id);
        return commonResponse;
    }

    /**
     * 修改每日行程景点关联关系数据
     * @param scheduleDetailScenic
     * @return
     */
    @RequestMapping(value ="/modifyScheduleDetailScenic", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse modifyScheduleDetailScenic(ScheduleDetailScenicEntity scheduleDetailScenic){
        TravelAgencyEntity travelEntity = travelService.getCurrentTravel();
        if(null == travelEntity){
            return CommonResponse.createUserNotLoginResponse();
        }
        CommonResponse commonResponse = scheduleDetailScenicService.modifyScheduleDetailScenic(travelEntity,scheduleDetailScenic);
        return commonResponse;
    }

}
