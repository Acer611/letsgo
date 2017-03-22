package com.umessage.letsgo.h5.journey;

import io.swagger.annotations.Api;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.umessage.letsgo.domain.vo.journey.response.TravelNotesDetailCommentResponse;
import com.umessage.letsgo.service.api.journey.ITravelNotesService;

@Api(value = "H5游记预览", description = "关于游记预览的相关页面")
@Controller
@RequestMapping(value = "/web/travelNotesComment")
public class WebTravelNotesCommentController {

	@Resource
    private ITravelNotesService iTravelNotesService;
	
	 @RequestMapping(value = "/selectTravelNotesComment", method = RequestMethod.GET)
	    public String selectTravelNotesComment(@RequestParam Long travelNotesId, Model model) {

		 	TravelNotesDetailCommentResponse commented = iTravelNotesService.selectTravelNotesComment(travelNotesId);
	        model.addAttribute("commented", commented);

	        return "";
	    }
	
}
