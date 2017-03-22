package com.umessage.letsgo.h5.journey;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.utils.JsonUtils;
import com.umessage.letsgo.domain.po.journey.*;
import com.umessage.letsgo.domain.po.team.HotelEntity;
import com.umessage.letsgo.domain.po.team.LeaderEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleDetailRequest;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleShowRequest;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.SignVo;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.journey.*;
import com.umessage.letsgo.service.api.system.ISpotService;
import com.umessage.letsgo.service.api.team.IHotelAgencyService;
import com.umessage.letsgo.service.api.team.IHotelService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import com.umessage.letsgo.service.common.constant.SmsConstant;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Api(value = "H5行程页面", description = "关于H5行程的相关页面")
@Controller
@RequestMapping(value = "/web/schedule")
public class WebScheduleController {

    @Resource
    private IScheduleDetailsService scheduleDetailsService;
    @Resource
    private IWebScheduleService webScheduleService;
    @Resource
    private UserLoginHelper userLoginHelper;
    @Resource
    private IHotelService hotelService;
    @Resource
    private ISpotService spotService;
    @Resource
    private ISurveyService surveyService;
    @Resource
    private IScheduleService scheduleService;
    @Resource
    private IMemberService memberService;
    @Resource
    private IScheduleDetailScenicService scheduleDetailScenicService;
    @Resource
    private ITravelAgencyService travelService;
    @Resource
    private IHotelAgencyService hotelAgencyService;
    @Resource
    private IHotelScheduleService hotelScheduleService;
    @Resource
    private IAlbumScheduleService albumScheduleService;
    @Resource
    private IScheduleDescService scheduleDescService;

    @RequestMapping(value = "getScheduleShow", method = RequestMethod.GET)
    public String getScheduleShow(ScheduleShowRequest showRequest, SignVo signVo, Model model){
        // 1、获取当前登录的用户信息
        UserResponse user = userLoginHelper.getLoginUser();
        ScheduleResponse response = null;
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            response = ScheduleResponse.createUserNotLoginResponse();
        }else {
            response = webScheduleService.getScheduleShowByTeamId(showRequest.getTeamId(), showRequest.getScheduleDetaildId(),showRequest.getScheduleId(),showRequest.getDescId());
            response = webScheduleService.getScheduleSigns(showRequest,response,signVo);

            response.setTeamId(getUrlEncodeTeamId(showRequest.getTeamId()));
//            response.setIsEditable(isEditable());
            response.setIsEditable(isEditableOne(showRequest.getTeamId(),user.getUserEntity().getId()));
            model.addAttribute("response", response);
        }
        return getReturnPage(showRequest.getFlag());
    }
    @RequestMapping(value = "getScheduleShowById", method = RequestMethod.GET)
    public String getScheduleShowById(ScheduleShowRequest showRequest, SignVo signVo, Model model){
        // 1、获取当前登录的用户信息
        UserResponse user = userLoginHelper.getLoginUser();
        ScheduleResponse response = null;
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            response = ScheduleResponse.createUserNotLoginResponse();
        }else {
            response = webScheduleService.getScheduleShowByTeamId(showRequest.getTeamId(), showRequest.getScheduleDetaildId(),showRequest.getScheduleId(),showRequest.getDescId());
            response = webScheduleService.getScheduleSigns(showRequest,response,signVo);


            SurveyEntity surveyEntity = surveyService.getSurveyByScheduleId(showRequest.getScheduleId());
            response.setSurveyEntity(surveyEntity);
            response.setScheduleId(showRequest.getScheduleId());
            //response.setTeamId(getUrlEncodeTeamId(showRequest.getTeamId()));
            response.setIsEditable(isEditable());
            //获取游客列表
            List<MemberEntity> touristList=webScheduleService.getTouristList(showRequest.getScheduleId());
            response.setTouristList(touristList);
            model.addAttribute("response", response);
        }
        return getReturnPage(showRequest.getFlag());
    }


    @RequestMapping(value = "scheduleDetailUpdate", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "H5更新每日行程【领队端】", notes = "领队在手机端更新H5每日行程，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse scheduleDetailUpdate(@RequestBody ScheduleDetailRequest request){
        if(request == null || request.getId() == null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：每日行程请求或每日行程ID不能为空！");
        }
        return scheduleDetailsService.updateScheduleDetails(request);
    }
    private String getUrlEncodeTeamId(String teamId){
        if(StringUtils.isEmpty(teamId)) return null;
        try {
            return URLEncoder.encode(teamId,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return teamId.replaceAll("#","%23");
    }
    private String isEditable(){
        UserDetails userDetails = userLoginHelper.getUserLoginFromContext();
        if(userDetails != null){
            Collection<? extends GrantedAuthority> list = userDetails.getAuthorities();
            for (GrantedAuthority authority: list){
                if("ROLE_CAPTAIN".equals(authority.getAuthority())){
                    return "edit";
                }
            }
        }
        return "";
    }
    //领队可修改行程 游客不可修改行程
    private String isEditableOne(String teamId,Long userId){
        MemberEntity memberEntity = memberService.getMemberRole(teamId, userId);
        if (memberEntity.getIsAdmin() == 1){
             return "edit";
        }
        return "";
    }


    private String getReturnPage(String flag){
        if("introduce".equals(flag)){
            return "journey/scheduleIntroduce";
        }
        if("edit".equals(flag)){
            return "journey/scheduleDetailEdit";
        }
        if("preview".equals(flag)){
            return "journey/scheduleDetailPreview";
        }
        if("overView".equals(flag)){
            return "journey/scheduleOverView";
            //return "redirect:http://mediaqueriestest.com/";
        }
        if("cost".equals(flag)){
            return "journey/ownExpense";
            //return "redirect:http://eeandrew.github.io/demos/gestures/index.html";
        }
        if("shop".equals(flag)){
            return "journey/shopping";
        }
        if("prompt".equals(flag)){
            return "journey/promptInfo";
            //return "redirect:http://css3test.com/";
        }
        if("travelAgency".equals(flag)){
            return "journey/travelAgency";
        }

        if("survey".equals(flag)){
            return "journey/survey";
        }
        if("tourist".equals(flag)){
            return "journey/touristList";
        }
        if("scenic".equals(flag)){
            return "";
        }
        if("scheduleDesc".equals(flag)){
            return "journey/desc";
        }
        return "journey/scheduleIntroduce";
    }

    @RequestMapping(value = "getScheduleGuides", method = RequestMethod.GET)
    public void getScheduleGuides(String teamId, PrintWriter out){
        if(teamId != null){
            List<LeaderEntity> list = webScheduleService.getScheduleGuides(teamId);
            out.print(JsonUtils.obj2json(list));
        }
    }
    /*

   */
    @RequestMapping(value = "/selectOneHotel", method = RequestMethod.POST)
    @ResponseBody
    public HotelEntity selectHotel(@RequestBody ScheduleShowRequest request ) {
        HotelEntity hotelEntity=hotelService.selectById(request.getHotelId());
        return hotelEntity;

    }
    /*

  */
/*    @RequestMapping(value = "/selectOneSpot", method = RequestMethod.POST)
    @ResponseBody
    public SpotEntity selectOneSpot(@RequestBody ScheduleShowRequest request ) {
        SpotEntity spotEntity=spotService.selectOneSpot(request.getMafengId());
        return spotEntity;

    }*/
    @RequestMapping(value = "confirmSchedule", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = " 确认行程接口【领队端】", notes = "领队在手机端确认行程接口，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    public CommonResponse confirmSchedule(
            @ApiParam(value = "团队id", required = true) @RequestBody ScheduleDetailRequest request) throws Exception{
        if(request.getTeamId() == null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队id不能为空！");
        }
        return scheduleService.confirmSchedule(request.getTeamId(),null);
    }
    /**
     * 获取公司客服电话
     * @return
     */
    @RequestMapping(value = "/getCustomerServicePhone", method = RequestMethod.GET)
    @ResponseBody
    public String getCustomerServicePhone() {
        return SmsConstant.CUSTOMER_SERVICE_PHONE;
    }


    /**
     * 根据id查询其他说明
     * @param showRequest
     * @param descId
     * @param model
     * @return
     */
    @RequestMapping(value ="/getScheduleDescById",method = RequestMethod.GET)
    public String getScheduleDescById(ScheduleShowRequest showRequest,Long descId,  Model model){
        ScheduleDescEntity scheduleDesc = scheduleDescService.select(descId);
        model.addAttribute("scheduleDesc",scheduleDesc);
        return getReturnPage(showRequest.getFlag());
    }

}
