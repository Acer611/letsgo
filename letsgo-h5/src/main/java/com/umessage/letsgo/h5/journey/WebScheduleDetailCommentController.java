package com.umessage.letsgo.h5.journey;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleDetailCommentRequest;
import com.umessage.letsgo.domain.vo.journey.response.DetailCommentResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleDetailResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.journey.IScheduleDetailCommentService;
import com.umessage.letsgo.service.api.journey.IScheduleDetailsService;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by zhajl on 16/9/23.
 */
@Api(value = "H5行程点评页面", description = "关于行程点评的相关页面")
@Controller
@RequestMapping(value = "/web/scheduleDetailComment")
public class WebScheduleDetailCommentController {

    @Resource
    private UserLoginHelper oauth2LoginHelper;
    @Resource
    private IScheduleService scheduleService;
    @Resource
    private IScheduleDetailsService scheduleDetailsService;
    @Resource
    private IScheduleDetailCommentService scheduleDetailCommentService;

    @RequestMapping(value = "/showJournarComment", method = RequestMethod.GET)
    public String showJournarComment(@RequestParam String teamId, Model model) {

        if (StringUtils.isEmpty(teamId)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队id【teamId】不能为空！");
        }

        UserResponse userResponse = oauth2LoginHelper.getLoginUser();
        if (userResponse.getRetCode() != ErrorConstant.SUCCESS) {
            throw new BusinessException(ErrorConstant.USER_NOT_LOGIN, "用户未登录或者登录会话已经过期！");
        }
        Long userID = userResponse.getUserEntity().getId();
        ScheduleResponse scheduleResponse = scheduleService.getScheduleInfo(teamId);
        if (scheduleResponse.getRetCode() == ErrorConstant.NOT_FOUND){
            throw new BusinessException(ErrorConstant.NOT_FOUND, "未找到对应行程!");
        }

        ScheduleEntity scheduleEntity = scheduleResponse.getScheduleEntity();
        model.addAttribute("scheduleEntity", scheduleEntity);

        // 是否已经评论, 0表示未评论, 1表示已经评论, 2表示没有可点评行程
        Integer commented = 0;

        // 如果当时间在第一天12点前,不让游客点评
        DateTime startDateTime = new DateTime(scheduleEntity.getStartDate());
        DateTime firstDate = startDateTime.plusHours(12);
        if (firstDate.isAfterNow()){
            commented = 2;
            model.addAttribute("commented", commented);
            return "comment/comment";
        }
        //行程开始的，并且行程结束后的第二天能进行点评,否则不能进行点评
        DateTime endDateTime = new DateTime(scheduleEntity.getEndDate());
        // 行程结束后的第三天不可以点评
        DateTime newDateTime = new DateTime(new Date());
        DateTime endDate = endDateTime.plusHours(36);
        if (endDate.isBefore(newDateTime)){
            //如果有答案则显示答案
            if (scheduleEntity != null){
                List<ScheduleDetailEntity> scheduleDetailList = scheduleEntity.getScheduleDetailList();
                if (scheduleDetailList != null && scheduleDetailList.size() > 0){
                    ScheduleDetailEntity detailEntity = scheduleDetailList.get(scheduleDetailList.size() - 1);
                    // 获取行程明细的答案
                    DetailCommentResponse detailCommentResponseOne = scheduleDetailCommentService.selectCommentDefault(detailEntity.getId(), userID);
                    if (detailCommentResponseOne.getRetCode() == ErrorConstant.SUCCESS) {
                        commented = 1;
                        model.addAttribute("detailCommentResponse", detailCommentResponseOne);
                    }
                }
            }
            model.addAttribute("commented", commented);
            return "comment/comment";
        }
        // 根据teamId获取可点评行程
        ScheduleDetailResponse scheduleDetailResponse = scheduleDetailsService.getCommentableDetail(teamId,userID);
        if (scheduleDetailResponse.getRetCode() == ErrorConstant.NOT_FOUND){
            throw new BusinessException(ErrorConstant.NOT_FOUND, "未找到对应行程!");
        }

        ScheduleDetailEntity scheduleDetailEntity = scheduleDetailResponse.getScheduleDetailEntity();
        model.addAttribute("scheduleDetailEntity", scheduleDetailEntity);

        //如果没有找对对应的每日行程则为没有可以点评的行程，返回结果
        //如果有答案则显示答案
        if (scheduleDetailEntity.getId() == null && scheduleEntity != null){
            List<ScheduleDetailEntity> scheduleDetailList = scheduleEntity.getScheduleDetailList();
            if (scheduleDetailList != null){
                for (int i = scheduleDetailList.size() - 1; i >=0; i--){
                    // 获取行程明细的答案
                    DetailCommentResponse detailCommentResponseOne = scheduleDetailCommentService.selectComment(scheduleDetailList.get(i).getId(), userID);
                    if (detailCommentResponseOne.getRetCode() == ErrorConstant.SUCCESS) {
                        commented = 1;
                        model.addAttribute("detailCommentResponse", detailCommentResponseOne);
                        break;
                    }
                }
            }
        }

        model.addAttribute("commented", commented);

        return "comment/comment";
    }
    @ApiOperation(value = "提交点评", notes = "提交点评，需要用户登录后才能操作，access_token参数为调用/oauth/token返回的value。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ver", value = "API版本号", defaultValue = "1.0", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "client_id", value = "分配给应用的唯一ID", defaultValue = "C8BC3D6EE", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "签名参数", required = true, dataType = "string", paramType = "query")})
    @RequestMapping(value = "/submitJournarComment", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse submitJournarComment(@RequestBody ScheduleDetailCommentRequest scheduleDetailCommentRequest) {

        if (StringUtils.isEmpty(scheduleDetailCommentRequest.getScheduleDetailId())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：行程详细id【scheduleDetailId】不能为空！");
        }
        if (StringUtils.isEmpty(scheduleDetailCommentRequest.getSatisfiedStatus())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：满意度标识【satisfiedStatus】不能为空！");
        }

        UserResponse userResponse = oauth2LoginHelper.getLoginUser();
        if (userResponse.getRetCode() != ErrorConstant.SUCCESS) {
            throw new BusinessException(ErrorConstant.USER_NOT_LOGIN, "用户未登录或者登录会话已经过期！");
        }
        Long userID = userResponse.getUserEntity().getId();
        scheduleDetailCommentRequest.setUserId(userID);

        try {
            scheduleDetailCommentService.submitJournarComment(scheduleDetailCommentRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorConstant.INTERNAL_SERVER_ERROR, "提交点评失败");
        }

        CommonResponse response = new CommonResponse();
        return response;
    }
}
