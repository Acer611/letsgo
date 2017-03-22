package com.umessage.letsgo.travel.controller.journey;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.po.journey.PromptInfoEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.LineEvaluateRequest;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleRequest;
import com.umessage.letsgo.domain.vo.journey.response.*;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.journey.ILineEvaluateService;
import com.umessage.letsgo.service.api.journey.IPromptInfoService;
import com.umessage.letsgo.service.api.journey.IScheduleDetailsService;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import com.umessage.letsgo.service.api.team.IWebMemberService;
import com.umessage.letsgo.service.common.constant.Constant;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pw on 2016/8/31.
 *
 */
@Api(value = "旅行社端线路评价数据管理接口", description = "关于旅行社端线路评价数据相关操作")
@Controller
@RequestMapping(value = "/lineEvaluate")
public class LineEvaluateController {

    @Resource
    private ITravelAgencyService travelService;
    @Resource
    private UserLoginHelper userLoginHelper;
    @Resource
    private ILineEvaluateService lineEvaluateService;

    //获取线路评价数据

    @RequestMapping(value = "/getLineList", method = RequestMethod.POST)
    public String getLineList(LineEvaluateRequest request, Model model) {
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        if(travel == null || travel.getId() == null) return "redirect:/login";
        request.setTravelId(travel.getId());
        if(request == null || request.getEndDate() == null || request.getStartDate()==null || request.getContinent()==null ||request.getCountry()==null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：请求参数不能为空！");
        }
        LineEvaluateResponse response= lineEvaluateService.getLineList(request);
        model.addAttribute("response",response);
        model.addAttribute("request",request);
        return "statistical/xianluComments";
    }

    //跳转线路评价数据页面
    @RequestMapping("/goLineList")
    public String goLineList(LineEvaluateRequest request, Model model) {
        return "statistical/xianluComment";
    }

}
