package com.umessage.letsgo.manager.controller.journey;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.po.journey.PromptInfoEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.system.RegionEntity;
import com.umessage.letsgo.domain.po.system.RegionEntityPo;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleRequest;
import com.umessage.letsgo.domain.vo.journey.response.PromptInfoResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleListResponse;
import com.umessage.letsgo.domain.vo.journey.response.SchedulePageResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.journey.IPromptInfoService;
import com.umessage.letsgo.service.api.journey.IScheduleDetailsService;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.system.IRegionService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import com.umessage.letsgo.service.common.constant.Constant;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 2016/9/12.
 */
@Controller
@RequestMapping(value = "/schedule")
public class ScheduleController {

    @Resource
    private IScheduleService scheduleService;
    @Resource
    private ITravelAgencyService travelService;
    @Resource
    private IPromptInfoService promptService;
    @Resource
    private IScheduleDetailsService scheduleDetailsService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;

    @Resource
    private IRegionService regionService;

    @RequestMapping(value = "main", method = RequestMethod.GET)
    public String mainPage(ScheduleRequest request, Model model) {
        UserResponse userResponse = oauth2LoginHelper.getLoginUser();
        if (userResponse.getRetCode() != ErrorConstant.SUCCESS) {
            return "redirect:/login";
        }
        //公司后台 账户的ID
        request.setTravelId(userResponse.getUserEntity().getId());
        return "journey/scheduleList";
    }

    //参数不能为空，否则抛出异常
    private void paramNotNull(ScheduleRequest scheduleRequest){
        if(scheduleRequest == null){
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：行程请求对象不能为空！");
        }
    }

    /**
     * 点击创建新行程操作
     * @return
     */
    @RequestMapping("scheduleInit")
    public String scheduleInit(ScheduleRequest request, Model model){
        paramNotNull(request);
        UserResponse userResponse = oauth2LoginHelper.getLoginUser();
        if (userResponse.getRetCode() != ErrorConstant.SUCCESS) {
            return "redirect:/login";
        }
        //公司后台 账户的ID
        request.setTravelId(userResponse.getUserEntity().getId());
        SchedulePageResponse response = scheduleService.getSchedulesByUser(request);
        model.addAttribute("list",response.getScheduleList());
        return "journey/scheduleInit";
    }

    private void scheduleShow(long id,Model model){
        ScheduleEntity scheduleEntity = scheduleService.getScheduleByUser(id);
        // 获取行程资料，封装成一个List，传入行程实体中
        if (!StringUtils.isEmpty(scheduleEntity.getProcessPhotosUrl())) {
            List<String> photoUrlList = new ArrayList<>();
            String urls[] = scheduleEntity.getProcessPhotosUrl().split(";");
            for (String url : urls) {
                photoUrlList.add(url);
            }
            scheduleEntity.setProcessPhotosUrls(photoUrlList);
        }

        PromptInfoEntity promptEntity = promptService.getByScheduleId(id);
        model.addAttribute("scheduleEntity",scheduleEntity);
        model.addAttribute("promptEntity",promptEntity);
    }

    /**
     * 选择创建行程方式
     * 已有行程或新行程;已有行程id
     * @return
     */
    @RequestMapping("scheduleInitChoose")
    public String scheduleInitChoose(ScheduleRequest request,Model model){
        paramNotNull(request);
        if(Constant.UPDATE_TEMPLATE.equals(request.getTravelTemplate())){
            if(Constant.UPDATE_TEMPLATE_More.equals(request.getClickMore())){
                return search(request,model);
            }
            scheduleShow(request.getId(),model);
        }
        return "journey/scheduleInitTemplate";
    }

    /**
     * 行程列表
     * @return
     */
    @RequestMapping("search")
    public String search(ScheduleRequest request, Model model) {
        paramNotNull(request);
        UserResponse userResponse = oauth2LoginHelper.getLoginUser();
        if (userResponse.getRetCode() != ErrorConstant.SUCCESS) {
            return "redirect:/login";
        }
        //公司后台 账户的ID
        request.setTravelId(userResponse.getUserEntity().getId());

        ScheduleListResponse response=scheduleService.searchScheduleListByUser(request);
        model.addAttribute("response",response);
        if(Constant.MAIN_PAGE_MORE.equals(request.getClickMore()) ||
                Constant.UPDATE_TEMPLATE_More.equals(request.getClickMore())){
            model.addAttribute("travelTemplate",request.getTravelTemplate());
            return "journey/moreScheduleList";
        }
        return "journey/scheduleLists";
    }

    /**
     * 预览行程
     */
    @RequestMapping("schedulePreview")
    public String schedulePreview(Long id,Model model){
        scheduleShow(id,model);
        return "journey/schedulePreview";
    }


    /**
     * 编辑行程
     */
    @RequestMapping("scheduleEdit")
    public String scheduleEdit(Long id,Model model){
        scheduleShow(id,model);
        return "journey/scheduleEdit";
    }


    /**
     * 保存行程
     */
    @RequestMapping(value = "scheduleSave", method = RequestMethod.POST)
    @ResponseBody
    public ScheduleResponse scheduleSave(ScheduleEntity schedule){
        return scheduleService.scheduleSave(schedule);//返回到当前页。
    }
    /**
     * 更新行程
     */
    @RequestMapping(value = "scheduleUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ScheduleResponse scheduleUpdate(ScheduleEntity schedule){
        return scheduleService.scheduleUpdate(schedule);//返回到当前页。
    }


    /**
     * 保存每日行程
     */
    @RequestMapping(value = "scheduleDetailSave", method = RequestMethod.POST)
    @ResponseBody
    public ScheduleResponse scheduleDetailSave(ScheduleDetailEntity scheduleDetail){
        return scheduleDetailsService.saveScheduleDeails(scheduleDetail);//返回到当前页。
    }
    /**
     * 更新每日行程
     */
    @RequestMapping(value = "scheduleDetailUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ScheduleResponse scheduleDetailUpdate(ScheduleDetailEntity scheduleDetail){

        return scheduleDetailsService.updateDetails(scheduleDetail);//返回到当前页。
    }

    /**
     * 提示信息保存
     */
    @RequestMapping(value = "promptInfoSave", method = RequestMethod.POST)
    @ResponseBody
    public PromptInfoResponse promptInfoSave(PromptInfoEntity promptInfo){
        //TODO
     /*   if(promptInfo != null){
            promptService.addPromptInfo(promptInfo);
        }*/
        return new PromptInfoResponse(promptInfo);//返回当前页
    }

    /**
     * 提示信息更新
     */
    @RequestMapping(value = "promptInfoUpdate", method = RequestMethod.POST)
    @ResponseBody
    public PromptInfoResponse promptInfoUpdate(PromptInfoEntity promptInfo){
        if(promptInfo != null){
            promptService.updatePromptInfo(promptInfo);
        }
        return new PromptInfoResponse(promptInfo);//返回当前页
    }

    /**
     * 确认创建行程
     */
    @RequestMapping(value={"/confirmSchedule"},method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse confirmSchedule(Long id){
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setId(id);
        //发布状态：1全部-2已发布-3未发布-4待确认
        scheduleEntity.setProcessStatus(4);
        scheduleService.updateScheduleProcessStatus(scheduleEntity);
        return new CommonResponse();//加入修改

    }

    //模糊匹配城市与国家
    @RequestMapping("/getCityByContent")
    @ResponseBody
    public List<RegionEntityPo> getCityByContent(@RequestParam String countryName){
        List<RegionEntityPo> cityByList = regionService.getCountryByCity(countryName);
        return cityByList;
    }

}
