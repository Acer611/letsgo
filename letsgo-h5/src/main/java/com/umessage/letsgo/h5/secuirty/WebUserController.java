package com.umessage.letsgo.h5.secuirty;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.po.security.RewardDetailsEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.request.RewardDetailsRequest;
import com.umessage.letsgo.domain.vo.security.respone.RewardDetailsResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.requset.WaitingRequest;
import com.umessage.letsgo.domain.vo.team.respone.WaitingResponse;
import com.umessage.letsgo.service.api.security.IRewardDetailsService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.system.IMessageService;
import com.umessage.letsgo.service.api.team.IWaitingService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wendy on 2016/8/29.
 */
@Controller
@RequestMapping(value = "/web/user")
public class WebUserController {

    @Resource
    private UserLoginHelper userLoginHelper;
    @Resource
    private IRewardDetailsService rewardDetailsService;
    @Resource
    private IWaitingService waitingService;
    @Resource
    private IMessageService messageService;
    @Resource
    private IUserService userService;

    @RequestMapping(value = "/getInviteCodeAndRewardDetails", method = RequestMethod.GET)
    public String getInviteCodeAndRewardDetails(Model model) {
        // 获取当前登录的用户信息
        UserResponse user = userLoginHelper.getLoginUser();

        RewardDetailsResponse response = new RewardDetailsResponse();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            response.setRetCode(user.getRetCode());
            response.setRetMsg(user.getRetMsg());
        } else {
            // 获取奖励列表
            Map<String, List<RewardDetailsEntity>> rewardMap = rewardDetailsService.getRewardDetailsByUser(user.getUserEntity().getId(), 1, 5);

            response.setInviteCode(user.getUserEntity().getInviteCode());
            response.setInviteCount(user.getUserEntity().getInviteCount());
            response.setTotalRewardAmount(user.getUserEntity().getTotalReward());
            response.setRewardDetailsMap(rewardMap);
            model.addAttribute("response", response);
        }

        return "invitation/invitation";
    }


    @RequestMapping(value = "/getRewardDetailsByAjax", method = RequestMethod.POST)
    @ResponseBody
    public RewardDetailsResponse getRewardDetailsByAjax(@RequestBody RewardDetailsRequest request) {
        // 获取当前登录的用户信息
        UserResponse user = userLoginHelper.getLoginUser();

        RewardDetailsResponse response = new RewardDetailsResponse();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            response.setRetCode(user.getRetCode());
            response.setRetMsg(user.getRetMsg());
        } else {
            // 获取总条数
            int total = rewardDetailsService.getRewardDetailsCount(user.getUserEntity().getId());
            int pageSize = 5;
            int totalSize = total / pageSize;
            if (total % 5 != 0) {
                totalSize = totalSize + 1;
            }

            Map<String, List<RewardDetailsEntity>> rewardMap = new LinkedHashMap<>();
            if (request.getPageNum() <= totalSize) {
                // 获取奖励列表
                rewardMap = rewardDetailsService.getRewardDetailsByUser(user.getUserEntity().getId(), request.getPageNum(), pageSize);
            }

            response.setInviteCode(user.getUserEntity().getInviteCode());
            response.setInviteCount(user.getUserEntity().getInviteCount());
            response.setTotalRewardAmount(user.getUserEntity().getTotalReward());
            response.setRewardDetailsMap(rewardMap);
        }
        return response;
    }

    @RequestMapping(value = "/getMyWaiting", method = RequestMethod.GET)
    public String getMyWaiting(Model model) {
        // 获取当前登录的用户信息
        UserResponse user = userLoginHelper.getLoginUser();

        WaitingResponse response = waitingService.getWaiting(user.getUserEntity().getId());
        model.addAttribute("response", response);

        return "scheduling/scheduling";
    }

    @RequestMapping(value = "/saveMyWaiting", method = RequestMethod.POST)
    @ResponseBody
    public WaitingResponse saveMyWaiting(@RequestBody WaitingRequest request) {
        // 获取当前登录的用户信息
        UserResponse user = userLoginHelper.getLoginUser();

        WaitingResponse response = waitingService.saveWaiting(request.getDates(), request.getStatus(),user.getUserEntity().getId());

        return response;
    }
}
