package com.umessage.letsgo.travel.controller.team;

import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.utils.QueryUtils;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.LeaderEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.requset.LeaderRequest;
import com.umessage.letsgo.domain.vo.team.respone.LeaderResponse;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.team.ILeaderService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import com.umessage.letsgo.service.api.team.IWaitingService;
import com.umessage.letsgo.service.common.constant.Constant;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by ZhaoYidong on 2016/6/1.
 *
 */
@Api(value = "旅行社端行程管理接口", description = "关于旅行社端行程的相关操作")
@Controller
@RequestMapping(value = "/leader")
public class LeaderController {

    @Resource
    private ILeaderService leaderService;
    @Resource
    private ITravelAgencyService travelService;
    @Resource
    private IScheduleService scheduleService;
    @Resource
    private UserLoginHelper userLoginHelper;
    @Resource
    private IMemberService memberService;
    @Resource
    private IWaitingService waitingService;

    /**
     * 领队列表
     */
    @RequestMapping("search")
    public String search(LeaderRequest request, Model model) {
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        if(travel == null || travel.getId() == null) return "redirect:/login";
        request.setTravelId(travel.getId());
        //查询列表，分页
        LeaderResponse response = leaderService.getLeaders(request);
        model.addAttribute("response",response);
        return "leader/leaderList";
    }

    @RequestMapping("searchAjax")
    @ResponseBody
    public LeaderResponse search(LeaderRequest request) {
        TravelAgencyEntity travel = travelService.getCurrentTravel();
        if(travel == null || travel.getId() == null){
            return LeaderResponse.createNotFoundResponse("没有发现旅行社");
        }
        request.setTravelId(travel.getId());
        //查询列表，分页
        return leaderService.getLeaders(request);
    }

    /**
     * 添加领队
     */
    @RequestMapping("leaderInit")
    public String leaderInit(){
        return "leader/leaderInit";
    }
    /**
     *领队保存
     */
    @RequestMapping("leaderSave")
    public String leaderSave(LeaderEntity leader,Model model){
        LeaderResponse leaderResponse = leader.dataVerify();
        if(leaderResponse.getRetCode() != 0){
            model.addAttribute("leaderEntity",leader);
            model.addAttribute("retMsg",leaderResponse.getRetMsg());
            return "leader/leaderInit";
        }
        leaderAdd(leader);
        return "redirect:search";
    }
    /**
     *领队保存
     */
    @RequestMapping("leaderSaveAjax")
    @ResponseBody
    public LeaderResponse leaderSaveAjax(LeaderEntity leader){
        LeaderResponse verifyResponse = leader.dataVerify();
        if(verifyResponse.getRetCode() != 0){
            return verifyResponse;
        }
        LeaderEntity leaderEntity = leaderAdd(leader);
        LeaderResponse response = new LeaderResponse();
        response.setLeader(leaderEntity);
        return verifyResponse;
    }

    private LeaderEntity leaderAdd(LeaderEntity leader){
        if(leader == null) return null;
        UserResponse userResponse = userLoginHelper.getLoginUser();
        UserEntity user = userResponse.getUserEntity();
        if(user == null || user.getId() == null){
            throw new BusinessException(ErrorConstant.USER_NOT_LOGIN,"用户未登录或登录信息过期");
        }
//        TravelAgencyEntity travel = travelService.getByUserId(user.getId());
        TravelAgencyEntity travel = travelService.getByTravelerId(user.getTravelerId());
        leader.setTravelId(travel.getId());
        leaderService.addLeader(leader);
        return leader;
    }

    /**
     * 领队编辑
     */
    @RequestMapping("leaderEdit")
    public String leaderEdit(Long id,Model model){
        LeaderEntity leaderEntity = leaderService.getLeader(id);
        model.addAttribute("leaderEntity",leaderEntity);
        return "leader/leaderEdit";
    }

    /**
     * 领队更新
     */
    @RequestMapping("leaderUpdate")
    public String leaderUpdate(LeaderEntity leader,Model model){
        LeaderResponse verifyResponse = leader.dataVerify();
        if(verifyResponse.getRetCode() != 0){
            model.addAttribute("leaderEntity",leader);
            model.addAttribute("retMsg",verifyResponse.getRetMsg());
            return "leader/leaderEdit";
        }
        leaderService.updateLeader(leader);
        return "redirect:search";
    }

    /**
     * 领队查看
     */
    @RequestMapping("leaderPreview")
    public String leaderPreview(Long id,Model model){
        LeaderEntity leaderEntity = leaderService.getLeader(id);
        Page<ScheduleEntity> traveledList = scheduleService.getSchedulesByLeaderAndStatus(Constant.TRAVELED,id);//已出行
        Page<ScheduleEntity> prepareTravelList = scheduleService.getSchedulesByLeaderAndStatus(Constant.PREPARE_TRAVEL,id);//即将出行
        Page<ScheduleEntity> travelingList = scheduleService.getSchedulesByLeaderAndStatus(Constant.TRAVELING,id);//正在出行
        List<Date> dateList = scheduleService.calculateLeaderWorkDate(travelingList,prepareTravelList);
        //通过用户ID 获取领队的 排期 忙碌时间
        List<String> workList = new ArrayList<String>();
        if(leaderEntity!=null && leaderEntity.getUserId()!=null){
            workList=waitingService.getWorkList(leaderEntity.getUserId());
        }
        model.addAttribute("workList",workList);
        model.addAttribute("leaderEntity",leaderEntity);
        model.addAttribute("traveledList",traveledList);
        model.addAttribute("prepareTravelList",prepareTravelList);
        model.addAttribute("dateList",dateList);
        return "leader/leaderPreview";
    }

    @RequestMapping("leaderPhoneRepeatCheck")
    @ResponseBody
    public Map<String,Boolean> leaderPhoneIsRepeated(String phone,Long tId){
        UserResponse userResponse = userLoginHelper.getLoginUser();
        UserEntity user = userResponse.getUserEntity();
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        if(user == null || user.getId() == null){
            throw new BusinessException(ErrorConstant.USER_NOT_LOGIN,"用户未登录或登录信息过期");
        }
//        TravelAgencyEntity travel = travelService.getByUserId(user.getId());
        TravelAgencyEntity travel = travelService.getByTravelerId(user.getTravelerId());
        boolean isRepeated = leaderService.leaderPhoneIsRepeat(QueryUtils.getPhone(phone),travel.getId(),tId);
//        boolean isRepeatedInAll = memberService.memberPhoneIsRepeat(QueryUtils.getPhone(phone));
//        map.put("valid",isRepeated || isRepeatedInAll);
        map.put("valid",isRepeated);
        return map;
    }

}
