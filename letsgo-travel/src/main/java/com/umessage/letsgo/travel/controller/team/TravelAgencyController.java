package com.umessage.letsgo.travel.controller.team;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.activity.response.CallResponse;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.TrafficInfoResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.respone.TravelAgencyResponse;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by ZhaoYidong on 2016/6/17.
 */
@Controller
@RequestMapping(value = "/travelAgency")
public class TravelAgencyController {

    @Resource
    private ITravelAgencyService travelAgencyService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;

    @RequestMapping(value = "travelPreview",method = RequestMethod.GET)
    public String travelPreview(String method, Model model){
        UserResponse userResponse =oauth2LoginHelper.getLoginUser();
        if (userResponse.getRetCode() == ErrorConstant.SUCCESS) {
//            TravelAgencyEntity travel =travelAgencyService.getByUserId(userResponse.getUserEntity().getId());
            TravelAgencyEntity travel =travelAgencyService.getByTravelerId(userResponse.getUserEntity().getTravelerId());
            TravelAgencyResponse response = new TravelAgencyResponse(travel);
            model.addAttribute("response",response);
            if ((userResponse.getUserEntity().getRole()!= null && userResponse.getUserEntity().getRole() == 7 ) || (userResponse.getUserEntity().getRole()!= null && userResponse.getUserEntity().getRole() == 6 )){
                return "redirect:/user/userEdit";
            }
        }else {
            model.addAttribute("response", UserResponse.createUserNotLoginResponse());
        }
        if("edit".equals(method)){
            return "team/travelEdit";
        }
        return "team/travelPreview";
    }
    @RequestMapping(value = "travelUpdate",method = RequestMethod.POST)
    public String travelUpdate(TravelAgencyEntity travel, Model model){
        TravelAgencyResponse verifyResponse = travel.dataVerify();
        if(verifyResponse.getRetCode() != 0){
            model.addAttribute("response",verifyResponse);
            return "team/travelEdit";
        }
        TravelAgencyEntity entity = travelAgencyService.updateTravelAgency(travel);
        TravelAgencyResponse response = new TravelAgencyResponse(entity);
        model.addAttribute("response",response);
        return "team/travelPreview";
    }
}
