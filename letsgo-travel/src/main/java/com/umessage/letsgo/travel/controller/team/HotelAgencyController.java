package com.umessage.letsgo.travel.controller.team;

import com.umessage.letsgo.domain.po.team.HotelAgencyEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.team.respone.HotelAgencyResponse;
import com.umessage.letsgo.service.api.team.IAlbumAgencyService;
import com.umessage.letsgo.service.api.team.IHotelAgencyService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/1.
 */
@Controller
@RequestMapping(value = "/hotelagency")
public class HotelAgencyController {

    private Logger logger = LogManager.getLogger(HotelAgencyController.class.getName());
    /**
     * 初始数据获取
     */
    @Resource
    private IHotelAgencyService hotelAgencyService;
    @Resource
    private IAlbumAgencyService albumAgencyService;
    @Resource
    private ITravelAgencyService travelService;

    /*
    保存私有酒店
     */
    @RequestMapping(value = "/saveHotelAgency", method = RequestMethod.POST)
    @ResponseBody
    public HotelAgencyResponse saveHotelAgency(@RequestBody HotelAgencyEntity hotelAgencyEntity) {
        HotelAgencyResponse hotelAgencyResponse = new HotelAgencyResponse();
        TravelAgencyEntity travelEntity = travelService.getCurrentTravel();
        if(null == travelEntity){
            return HotelAgencyResponse.createUserNotLoginResponse();
        }
        hotelAgencyEntity.setTravelId(travelEntity.getId());
        //修改私有库酒店
        if (hotelAgencyEntity.getId() != null){
            hotelAgencyService.update(hotelAgencyEntity);
            albumAgencyService.batchUpdateHotelAgencyAlbum(hotelAgencyEntity);
        }else {
            //检查是否有同名的酒店
            Boolean sameName = hotelAgencyService.selectHotelAgencyBySameName(hotelAgencyEntity);
            if (sameName){
                hotelAgencyService.insert(hotelAgencyEntity);
                albumAgencyService.batchInsertHotelAgencyAlbum(hotelAgencyEntity);
            }
        }
        hotelAgencyResponse.setHotelAgencyEntity(hotelAgencyEntity);
        return hotelAgencyResponse;
    }

    /**
     * 根据id查询私有酒店
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/getHotelAgencyById")
    @ResponseBody
    public HotelAgencyResponse getHotelAgencyById(@RequestParam Long id, Model model) {
        HotelAgencyResponse hotelAgencyResponse = new HotelAgencyResponse();
        HotelAgencyEntity hotelAgencyEntity = hotelAgencyService.selectHotelAndAlbum(id);
        hotelAgencyResponse.setHotelAgencyEntity(hotelAgencyEntity);
        return hotelAgencyResponse;
    }

    /**
     * 根据酒店名字和travelid的联想查询
     * @param hotelName
     * @return
     */
    @RequestMapping(value = "/getHotelAgencyByName", method = RequestMethod.POST)
    @ResponseBody
    public HotelAgencyResponse getHotelAgencyByName(@RequestParam String hotelName) {

        logger.info("输入要联想酒店名字为：" + hotelName);
        HotelAgencyResponse hotelAgencyResponse = new HotelAgencyResponse();
        if (StringUtils.isEmpty(hotelName)){
            return HotelAgencyResponse.createEmptyParameterResponse("酒店名不能为空");
        }
        TravelAgencyEntity travelEntity = travelService.getCurrentTravel();
        if(null == travelEntity){
            return HotelAgencyResponse.createUserNotLoginResponse();
        }
        List<HotelAgencyEntity> hotelAgencyEntities = hotelAgencyService.selectHotelAgencyByName(hotelName, travelEntity.getId());
        hotelAgencyResponse.setHotelAgencyEntityList(hotelAgencyEntities);
        return hotelAgencyResponse;
    }

}
