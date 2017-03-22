package com.umessage.letsgo.travel.controller.team;

import com.umessage.letsgo.domain.po.team.OwnExpenseAgencyEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.team.respone.OwnExpenseAgencyResponse;
import com.umessage.letsgo.service.api.team.IAlbumAgencyService;
import com.umessage.letsgo.service.api.team.IOwnExpenseAgencyService;
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
@RequestMapping(value = "/ownexpenseagency")
public class OwnExpenseAgencyController {
    /**
     * 初始数据获取
     */
    @Resource
    private IOwnExpenseAgencyService ownExpenseAgencyService;
    @Resource
    private IAlbumAgencyService albumAgencyService;
    @Resource
    private ITravelAgencyService travelService;

    /*
    保存私有自费项目
     */
    @RequestMapping(value = "/saveOwnExpenseAgency", method = RequestMethod.POST)
    @ResponseBody
    public OwnExpenseAgencyResponse saveOwnExpenseAgency(@RequestBody OwnExpenseAgencyEntity ownExpenseAgency) {
        OwnExpenseAgencyResponse ownExpenseAgencyResponse = new OwnExpenseAgencyResponse();
        TravelAgencyEntity travelEntity = travelService.getCurrentTravel();
        if(null == travelEntity){
            return OwnExpenseAgencyResponse.createUserNotLoginResponse();
        }
        ownExpenseAgency.setTravelId(travelEntity.getId());
        //修改私有库自费项目
        if (ownExpenseAgency.getId() != null){
            ownExpenseAgencyService.update(ownExpenseAgency);
            albumAgencyService.batchUpdateOwnExpenseAgencyAlbum(ownExpenseAgency);
        }else {
            //检查是否有同名的自费项目
            Boolean sameName = ownExpenseAgencyService.selectOwnExpenseBySameName(ownExpenseAgency);
            if (sameName){
                ownExpenseAgencyService.insert(ownExpenseAgency);
                albumAgencyService.batchInsertOwnExpenseAgencyAlbum(ownExpenseAgency);
            }
        }
        ownExpenseAgencyResponse.setOwnExpenseAgencyEntity(ownExpenseAgency);
        return ownExpenseAgencyResponse;
    }

    /**
     * 根据id查询私有自费项目
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/getOwnExpenseAgencyById")
    @ResponseBody
    public OwnExpenseAgencyResponse getOwnExpenseAgencyById(@RequestParam Long id, Model model) {
        OwnExpenseAgencyResponse ownExpenseAgencyResponse = new OwnExpenseAgencyResponse();
        OwnExpenseAgencyEntity ownExpenseAgencyEntity = ownExpenseAgencyService.selectOwnExpenseAndAlbum(id);
        ownExpenseAgencyResponse.setOwnExpenseAgencyEntity(ownExpenseAgencyEntity);
        return ownExpenseAgencyResponse;
    }

    /**
     * 根据自费项目名字和travelid的联想查询
     * @param itemName
     * @return
     */
    @RequestMapping(value = "/getOwnExpenseAgencyByName", method = RequestMethod.POST)
    @ResponseBody
    public OwnExpenseAgencyResponse getOwnExpenseAgencyByName(@RequestParam String itemName, String cities) {
        OwnExpenseAgencyResponse ownExpenseAgencyResponse = new OwnExpenseAgencyResponse();
        if (StringUtils.isEmpty(itemName)){
            return OwnExpenseAgencyResponse.createEmptyParameterResponse("自费项目名不能为空");
        }
        TravelAgencyEntity travelEntity = travelService.getCurrentTravel();
        if(null == travelEntity){
            return OwnExpenseAgencyResponse.createUserNotLoginResponse();
        }
        OwnExpenseAgencyEntity ownExpenseAgencyEntity = new OwnExpenseAgencyEntity();
        ownExpenseAgencyEntity.setItemName(itemName);
        ownExpenseAgencyEntity.setTravelId(travelEntity.getId());
        //ownExpenseAgencyEntity.setCities(ELUtil.strToList(cities));
        List<OwnExpenseAgencyEntity> ownExpenseAgencyEntities = ownExpenseAgencyService.selectOwnExpenseByName(ownExpenseAgencyEntity);
        ownExpenseAgencyResponse.setOwnExpenseAgencyList(ownExpenseAgencyEntities);
        return ownExpenseAgencyResponse;
    }

}
