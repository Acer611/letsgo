package com.umessage.letsgo.travel.controller.team;

import com.umessage.letsgo.core.utils.ELUtil;
import com.umessage.letsgo.domain.po.team.ShoppingAgencyEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.team.respone.ShoppingAgencyResponse;
import com.umessage.letsgo.service.api.team.IAlbumAgencyService;
import com.umessage.letsgo.service.api.team.IShoppingAgencyService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/1.
 */
@Controller
@RequestMapping(value = "/shoppingagency")
public class ShoppingAgencyController {
    /**
     * 初始数据获取
     */
    @Resource
    private IShoppingAgencyService shoppingAgencyService;
    @Resource
    private IAlbumAgencyService albumAgencyService;
    @Resource
    private ITravelAgencyService travelService;

    /*
    保存私有购物场所
     */
    @RequestMapping(value = "/saveShoppingAgency", method = RequestMethod.POST)
    @ResponseBody
    public ShoppingAgencyResponse saveShoppingAgency(@RequestBody ShoppingAgencyEntity shoppingAgency) {
        ShoppingAgencyResponse shoppingAgencyResponse = new ShoppingAgencyResponse();
        TravelAgencyEntity travelEntity = travelService.getCurrentTravel();
        if(null == travelEntity){
            return ShoppingAgencyResponse.createUserNotLoginResponse();
        }
        shoppingAgency.setTravelId(travelEntity.getId());
        //修改私有库购物场所
        if (shoppingAgency.getId() != null){
            shoppingAgencyService.update(shoppingAgency);
        }else {
            //检查是否有同名的购物场所
            Boolean sameName = shoppingAgencyService.selectHotelAgencyBySameName(shoppingAgency);
            if (sameName){
                shoppingAgencyService.insert(shoppingAgency);
            }
        }
        shoppingAgencyResponse.setShoppingAgencyEntity(shoppingAgency);
        return shoppingAgencyResponse;
    }

    /**
     * 根据id查询私有酒店
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/getShoppingAgencyById")
    @ResponseBody
    public ShoppingAgencyResponse getShoppingAgencyById(@RequestParam Long id, Model model) {
        ShoppingAgencyResponse shoppingAgencyResponse = new ShoppingAgencyResponse();
        ShoppingAgencyEntity shoppingAgencyEntity = shoppingAgencyService.select(id);
        shoppingAgencyResponse.setShoppingAgencyEntity(shoppingAgencyEntity);
        return shoppingAgencyResponse;
    }

    /**
     * 根据酒店名字和travelid的联想查询
     * @param hotelName
     * @return
     */
    @RequestMapping(value = "/getShoppingAgencyByName", method = RequestMethod.POST)
    @ResponseBody
    public ShoppingAgencyResponse getShoppingAgencyByName(@RequestParam String hotelName, String cities) {
        ShoppingAgencyResponse shoppingAgencyResponse = new ShoppingAgencyResponse();
        if (StringUtils.isEmpty(hotelName)){
            return ShoppingAgencyResponse.createEmptyParameterResponse("酒店名不能为空");
        }
        TravelAgencyEntity travelEntity = travelService.getCurrentTravel();
        if(null == travelEntity){
            return ShoppingAgencyResponse.createUserNotLoginResponse();
        }
        ShoppingAgencyEntity shoppingAgencyEntity = new ShoppingAgencyEntity();
        shoppingAgencyEntity.setShoppingName(hotelName);
        shoppingAgencyEntity.setTravelId(travelEntity.getId());
        List<ShoppingAgencyEntity> shoppingAgencyEntities = shoppingAgencyService.selectShoppingAgencyByName(shoppingAgencyEntity);
        shoppingAgencyResponse.setShoppingAgencyList(shoppingAgencyEntities);
        return shoppingAgencyResponse;
    }

}
