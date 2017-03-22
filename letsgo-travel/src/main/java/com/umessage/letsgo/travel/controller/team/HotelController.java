package com.umessage.letsgo.travel.controller.team;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.po.team.HotelEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.journey.request.HotelValuationsRequest;
import com.umessage.letsgo.domain.vo.journey.response.vo.HotelValuationsResponse;
import com.umessage.letsgo.domain.vo.system.request.HotelRequest;
import com.umessage.letsgo.service.api.team.IHotelService;
import com.umessage.letsgo.service.api.team.IHotelValuationsService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/hotel")
public class HotelController {
    @Resource
    private IHotelService hotelService;
    @Resource
    private ITravelAgencyService travelService;
    @Resource
    private IHotelValuationsService hotelValuationsService;
    @RequestMapping(value = "/getHotelMessage", method = RequestMethod.GET)
    @ResponseBody
    public List<HotelEntity> getHotel(@RequestParam String hotelChineseName,@RequestParam String areaid) {

        if (StringUtils.isEmpty(hotelChineseName)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：酒店输入内容不能为空！");
        }
        HotelRequest hotel=new HotelRequest();
        hotel.setCity(areaid);
      System.out.print("======"+hotel.getCity());
        hotel.setHotelChineseName("%"+hotelChineseName+"%");
        return hotelService.getHotel(hotel);

    }
    @RequestMapping(value = "/selectHotelOne", method = RequestMethod.GET)
    public String selectHotelOne(Model model) {

        return "statistical/hotelComment";

    }
    @RequestMapping(value = "/selectHotel", method = RequestMethod.POST)
    public String selectHotel(HotelValuationsRequest hotelValuationsRequest , Model model) {
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        if(travel == null || travel.getId() == null) return "redirect:/login";
        hotelValuationsRequest.setTravelId(travel.getId());
        HotelValuationsResponse hotelValuationsResponse=  hotelValuationsService.selectHotel(hotelValuationsRequest);
        model.addAttribute("hotelValuationsResponse",hotelValuationsResponse);
        model.addAttribute("hotelValuationsRequest",hotelValuationsRequest);
        return "statistical/hotelComments";

    }
    /*

     */
    @RequestMapping(value = "/selectOneHotel", method = RequestMethod.GET)
    public String selectHotel(@RequestParam String hotelId , Model model) {
         HotelEntity hotelEntity=hotelService.selectById(hotelId);
        model.addAttribute("hotelEntity",hotelEntity);
        return "statistical/hotelComments";

    }
    public static void main(String [] args){
        HotelController h=new HotelController();
            System.out.println("========="+h.getHotel("联欧",null));
    }

}
