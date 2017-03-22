package com.umessage.letsgo.h5.weixin;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.utils.ELUtil;
import com.umessage.letsgo.core.utils.HttpServletUtil;
import com.umessage.letsgo.core.utils.IDUtil;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailScenicEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.journey.SurveyEntity;
import com.umessage.letsgo.domain.po.notice.AnnouncementEntity;
import com.umessage.letsgo.domain.po.notice.LastMessageVo;
import com.umessage.letsgo.domain.po.notice.NoticeEntity;
import com.umessage.letsgo.domain.po.security.WxTeamEntity;
import com.umessage.letsgo.domain.po.system.SpotEntity;
import com.umessage.letsgo.domain.po.team.HotelEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleDetailCommentRequest;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleShowRequest;
import com.umessage.letsgo.domain.vo.journey.request.SurveyAnswerRequest;
import com.umessage.letsgo.domain.vo.journey.request.SurveyRequest;
import com.umessage.letsgo.domain.vo.journey.response.DetailCommentResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleDetailResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleResponse;
import com.umessage.letsgo.domain.vo.journey.response.SurveyQuestionResponse;
import com.umessage.letsgo.domain.vo.notice.request.DetailRequest;
import com.umessage.letsgo.domain.vo.notice.respone.NoticeRespone;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.requset.WebMemberRequest;
import com.umessage.letsgo.service.api.journey.*;
import com.umessage.letsgo.service.api.notice.IAnnouncementService;
import com.umessage.letsgo.service.api.notice.INoticeDetailService;
import com.umessage.letsgo.service.api.notice.INoticeService;
import com.umessage.letsgo.service.api.notice.IWebNoticeService;
import com.umessage.letsgo.service.api.security.IThirdPartyAccountService;
import com.umessage.letsgo.service.api.security.IWxTeamService;
import com.umessage.letsgo.service.api.system.ISpotService;
import com.umessage.letsgo.service.api.team.IHotelService;
import com.umessage.letsgo.service.common.helper.QCloudHelper;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import com.umessage.letsgo.service.common.security.WxUser;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by myz on 2016/11/23.
 */
@Controller
@RequestMapping(value = "/wechat")
public class WeChatController {

    private Logger logger = Logger.getLogger(WeChatController.class);

    @Resource
    private IScheduleService ScheduleServiceImpl;

    @Resource
    private IWxTeamService wxTeamServiceImpl;

    @Resource
    private IWebScheduleService webScheduleService;

    @Resource
    private ISurveyService surveyService;

    @Resource
    private INoticeService noticeService;

    @Resource
    private INoticeDetailService noticeDetailService;

    @Resource
    private IAnnouncementService announcementService;

    @Resource
    private UserLoginHelper userLoginHelper;

    @Resource
    private IScheduleService scheduleService;

    @Resource
    private IScheduleDetailsService scheduleDetailsService;

    @Resource
    private IScheduleDetailCommentService scheduleDetailCommentService;

    @Resource
    private ISurveyDetailService surveyDetailService;

    @Resource
    private IWebNoticeService webNoticeService;

    @Resource
    protected WxMpService wxMpService;

    @Resource
    private IHotelService hotelService;

    @Resource
    private ISpotService spotService;

    @Resource
    private  IThirdPartyAccountService thirdPartyAccountService;

    @Resource
    private IScheduleDetailScenicService scheduleDetailScenicService;

    @RequestMapping(value = "/getSchedule", method = RequestMethod.GET)
    public String getSchedule(Model model) {
        logger.info("进入查看行程........");
        UserResponse loginUser = userLoginHelper.getLoginUser();
        logger.info("retCode " + loginUser.getRetCode());
        ScheduleResponse response = null;
        if (loginUser.getUserEntity() != null) {
            logger.info("绑定用户查找行程");
            WebMemberRequest memberRequest = new WebMemberRequest();
            memberRequest.setUserid(loginUser.getUserEntity().getId());
            logger.info("userId:" + loginUser.getUserEntity().getId());
            List<ScheduleEntity> scheduleList = ScheduleServiceImpl.selectScheduleForboundMember(memberRequest);
            for (ScheduleEntity s : scheduleList) {
                String urlEncodeTeamId = ELUtil.getUrlEncodeTeamId(s.getTxGroupId());
                s.setTxGroupId(urlEncodeTeamId);
                logger.info("绑定：行程名称：" + s.getName() + ",行程开始时间：" + s.getStartDate() + ",行程结束时间：" + s.getEndDate() + ",行程状态：" + s.getStatus() + ",腾讯组id：" + s.getTxGroupId());
            }
            //如果返回list只有一个，直接跳转详情页面
            if (scheduleList.size()==1){
                ScheduleEntity scheduleEntity = scheduleList.get(0);
                String txGroupId = scheduleEntity.getTxGroupId();
                return "redirect:/wechat/getScheduleShow?flag=introduce&teamId="+txGroupId+"&scheduleId="+scheduleEntity.getId();
            }
            model.addAttribute("response", scheduleList);
            return "weixin/schedulelist";
        }

        logger.info("查找未绑定用户的行程");
        WxUser wxUser = userLoginHelper.getWxUser();//username：openid ,password :accessToken 关注的时候进行添加s_wx_info表添加数据
        if (null == wxUser) {
            return "redirect:/auth/redirectToLogin?targetUrl=weixin/schedulelist"; //如果都不ok就跳转登陆Controller---redirect:/
        }

        logger.info("unionid:" + wxUser.getUnionId());
        List<WxTeamEntity> wxTeamEntity = wxTeamServiceImpl.selectWxTeamByUnionid(wxUser.getUnionId());
        if (CollectionUtils.isEmpty(wxTeamEntity)) {
            logger.info("用户直接关注公众号");
            List<ScheduleEntity> scheduleEntityList = new ArrayList<>();
            model.addAttribute("response", scheduleEntityList);
            return "weixin/schedulelist";
        }

        logger.info("通过扫码进入的团");
        List<Long> longs = new ArrayList<>();
        for (WxTeamEntity wxTeam : wxTeamEntity) {
            longs.add(wxTeam.getTeamId());
            logger.info("teamId:" + wxTeam.getTeamId());
        }
        for (Long l : longs) {
            logger.info("teamIds:" + l);
        }
        WebMemberRequest memberRequest = new WebMemberRequest();
        memberRequest.setTeamIds(longs);
        List<ScheduleEntity> scheduleEntityList = ScheduleServiceImpl.selectScheduleForUnboundMember(memberRequest);
        for (ScheduleEntity s : scheduleEntityList) {
            String urlEncodeTeamId = ELUtil.getUrlEncodeTeamId(s.getTxGroupId());
            s.setTxGroupId(urlEncodeTeamId);
            logger.info("未绑定：行程名称：" + s.getName() + ",行程开始时间：" + s.getStartDate() + ",行程结束时间：" + s.getEndDate() + ",行程状态：" + s.getStatus() + ",腾讯组id：" + s.getTxGroupId());
        }
        model.addAttribute("response", scheduleEntityList);
        //如果返回list只有一个，直接跳转详情页面
        if (scheduleEntityList.size()==1){
            ScheduleEntity scheduleEntity = scheduleEntityList.get(0);
            String txGroupId = scheduleEntity.getTxGroupId();
            return "redirect:/wechat/getScheduleShow?flag=introduce&teamId="+txGroupId+"&scheduleId="+scheduleEntity.getId();
        }

        return "weixin/schedulelist";
    }

    /**
     * 依据腾讯组id 行程介绍，行程概述，费用信息，购物场所，友情提示，旅行社信息，
     * 次日行程（flag=preview,腾讯组id，行程详细id）
     * 行程变更（flag=preview,腾讯组id，行程详细id）
     * @param showRequest
     * @param model
     * @return
     */
    @RequestMapping(value = "getScheduleShow", method = RequestMethod.GET)
    public String getScheduleShow(@ModelAttribute ScheduleShowRequest showRequest, Model model){
        logger.info("flag:" + showRequest.getFlag() + ",teamId:" + showRequest.getTeamId());
        ScheduleResponse response = null;
        response = webScheduleService.getScheduleShowByTeamId(showRequest.getTeamId(), showRequest.getScheduleDetaildId(), showRequest.getScheduleId(),showRequest.getDescId());
        response.setTeamId(ELUtil.getUrlEncodeTeamId(showRequest.getTeamId()));
        response.setIsEditable("");
        response.setIsWeChat("Wechat");
        model.addAttribute("response", response);

        return getReturnPage(showRequest.getFlag());
    }

    /**
     * 依据行程id获取行程h5界面
     * @param showRequest
     * @param model
     * @return
     */
    @RequestMapping(value = "getScheduleShowById", method = RequestMethod.GET)
    public String getScheduleShowById(@ModelAttribute ScheduleShowRequest showRequest, Model model){
        ScheduleResponse response = null;
        response = webScheduleService.getScheduleShowByTeamId(showRequest.getTeamId(), showRequest.getScheduleDetaildId(), showRequest.getScheduleId(),showRequest.getDescId());
        SurveyEntity surveyEntity = surveyService.getSurveyByScheduleId(showRequest.getScheduleId());
        response.setSurveyEntity(surveyEntity);
        response.setScheduleId(showRequest.getScheduleId());
        response.setIsEditable("");
        response.setIsWeChat("Wechat");
        List<MemberEntity> touristList = webScheduleService.getTouristList(showRequest.getScheduleId());
        response.setTouristList(touristList);
        model.addAttribute("response", response);
        return getReturnPage(showRequest.getFlag());
    }
    /**
     * 公告信息，公告id
     * @param ID
     * @return  头像 姓名 时间 标题 内容
     */
    @RequestMapping(value = "/getAnnouncementMessage", method = RequestMethod.GET)
    public String getAnnouncementMessage(Long ID,Model model){
        AnnouncementEntity announcementEntity = announcementService.selectWxAnnouncementById(ID);
        LastMessageVo lastMessageVoEntity = new LastMessageVo();
        lastMessageVoEntity.setScheduleName(announcementEntity.getScheduleName());
        lastMessageVoEntity.setPhotoUrl(announcementEntity.getPhotoUrl());
        lastMessageVoEntity.setSenderName(announcementEntity.getSenderName());
        lastMessageVoEntity.setSendTime(announcementEntity.getCreateTime());
        lastMessageVoEntity.setTitle(announcementEntity.getTitle());
        lastMessageVoEntity.setContent(announcementEntity.getContent());
        logger.info("公告id:"+ID+",行程名称：" + lastMessageVoEntity.getScheduleName()
                + ",发布人url:" + lastMessageVoEntity.getPhotoUrl() + ",发布人姓名：" + lastMessageVoEntity.getSenderName()
                + ",发布时间：" + lastMessageVoEntity.getSendTime() + ",发布标题：" + lastMessageVoEntity.getTitle()
                + ",发布内容：" + lastMessageVoEntity.getContent());

        model.addAttribute("response", lastMessageVoEntity);
        return "weixin/announcement";
    }

    /**
     * 集合信息，集合id，用户id，1 为已确认
     * @param noticeId
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping(value = "/getGatherMessage", method = RequestMethod.GET)
    public String getGatherMessage(Long noticeId,Long userId,Model model,HttpServletRequest request){
        NoticeEntity notice = noticeService.getNotice(noticeId);
        DetailRequest detailRequest = new DetailRequest();
        detailRequest.setUserId(userId);
        detailRequest.setNoticeId(noticeId);
        detailRequest.setTeamId(notice.getTeamId());
        detailRequest.setType(1);
        CommonResponse commonResponse = noticeDetailService.comfirmNotice(detailRequest);
        if (commonResponse.getRetCode() == ErrorConstant.SUCCESS) {

            NoticeEntity noticeEntity = noticeService.getWxGatherById(noticeId, userId);
            LastMessageVo lastMessageVoEntity = new LastMessageVo();
            lastMessageVoEntity.setId(noticeId);
            lastMessageVoEntity.setScheduleName(noticeEntity.getScheduleName());
            if (null != noticeEntity.getContent()) {
                lastMessageVoEntity.setContent(noticeEntity.getContent());
            }
            if (null != noticeEntity.getVideoUrl()) {
                lastMessageVoEntity.setVideoUrl(noticeEntity.getVideoUrl());
            }
            lastMessageVoEntity.setTime(noticeEntity.getTime());
            lastMessageVoEntity.setTimezone(noticeEntity.getTimezone());
            lastMessageVoEntity.setLocation(noticeEntity.getLocation());
            lastMessageVoEntity.setTraffic(noticeEntity.getTraffic());
            lastMessageVoEntity.setActiveStatus(noticeEntity.getActiveStatus());
            lastMessageVoEntity.setPhotoUrl(noticeEntity.getSenderPhotoUrl());
            lastMessageVoEntity.setSenderName(noticeEntity.getSenderName());
            lastMessageVoEntity.setSendTime(noticeEntity.getCreateTime());
            logger.info("集合id:" + lastMessageVoEntity.getId() + ",行程名称：" + lastMessageVoEntity.getScheduleName()
                    + ",集合内容：" + lastMessageVoEntity.getContent() + ",集合语音地址：" + lastMessageVoEntity.getVideoUrl()
                    + ",集合时间：" + lastMessageVoEntity.getTime() + ",时区：" + lastMessageVoEntity.getTimezone()
                    + ",集合地点：" + lastMessageVoEntity.getLocation() + ",交通信息：" + lastMessageVoEntity.getTraffic()
                    + ",确认状态：" + lastMessageVoEntity.getActiveStatus() + ",发布人头像：" + lastMessageVoEntity.getPhotoUrl()
                    + ",发布人姓名：" + lastMessageVoEntity.getSenderName() + ",发布时间：" + lastMessageVoEntity.getSendTime());

            String url = HttpServletUtil.getCompleteUrl(request);
            logger.info("url:"+url);
            WxJsapiSignature jsapiSignature = null;
            try {
                jsapiSignature = wxMpService.createJsapiSignature(url);
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
            logger.info("appid:"+jsapiSignature.getAppid()+",noncestr:"+jsapiSignature.getNoncestr()
                    +",timestamp:"+jsapiSignature.getTimestamp()+",signature:"+jsapiSignature.getSignature());
            model.addAttribute("response", lastMessageVoEntity);
            model.addAttribute("jsapiSignature", jsapiSignature);
        }
        return "weixin/collection";
    }

    /**通知信息，通知ID,用户id,1 为已确认
     * @param ID
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping(value = "/getNoticeMessage", method = RequestMethod.GET)
    public String getNoticeMessage(Long ID,Long userId,Model model){
        NoticeEntity notice = noticeService.getNotice(ID);
        DetailRequest detailRequest = new DetailRequest();
        detailRequest.setUserId(userId);
        detailRequest.setNoticeId(ID);
        detailRequest.setTeamId(notice.getTeamId());
        detailRequest.setType(2);
        CommonResponse commonResponse = noticeDetailService.comfirmNotice(detailRequest);
        if (commonResponse.getRetCode() == ErrorConstant.SUCCESS) {
            NoticeEntity noticeEntity = noticeService.getWxGatherById(ID, userId);
            LastMessageVo lastMessageVoEntity = new LastMessageVo();
            lastMessageVoEntity.setScheduleName(noticeEntity.getScheduleName());
            if (null != noticeEntity.getContent()) {
                lastMessageVoEntity.setContent(noticeEntity.getContent());
            }
            if (null != noticeEntity.getVideoUrl()) {
                lastMessageVoEntity.setVideoUrl(noticeEntity.getVideoUrl());
            }
            lastMessageVoEntity.setActiveStatus(noticeEntity.getActiveStatus());
            lastMessageVoEntity.setPhotoUrl(noticeEntity.getSenderPhotoUrl());
            lastMessageVoEntity.setSenderName(noticeEntity.getSenderName());
            lastMessageVoEntity.setSendTime(noticeEntity.getCreateTime());
            logger.info("通知id:"+ID+",行程名称：" + lastMessageVoEntity.getScheduleName()
                    + ",通知内容：" + lastMessageVoEntity.getContent() + ",通知语音：" + lastMessageVoEntity.getVideoUrl()
                    + ",确认 状态：" + lastMessageVoEntity.getActiveStatus() + ",发送人头像：" + lastMessageVoEntity.getPhotoUrl()
                    + ",发送人姓名：" + lastMessageVoEntity.getSenderName() + ",发送时间：" + lastMessageVoEntity.getSendTime());
            model.addAttribute("response", lastMessageVoEntity);
        }
        return "weixin/notice";
    }

    /**
     * 集合位置
     * @param ID
     * @param model
     * @return
     */
    @RequestMapping(value = "/getGatherLocation", method = RequestMethod.GET)
    public String getGatherLocation(Long ID,Model model,HttpServletRequest request){
        logger.info("集合id：" + ID);
        NoticeRespone response = webNoticeService.getNotice(ID);
        Double lng = response.getNoticeEntity().getLongitude();
        Double lat = response.getNoticeEntity().getLatitude();
        logger.info("经纬度：" + lng + ",--," + lat);
        String url = HttpServletUtil.getCompleteUrl(request);
        logger.info("url:"+url);
        WxJsapiSignature jsapiSignature = null;
        try {
            jsapiSignature = wxMpService.createJsapiSignature(url);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        logger.info("appid:"+jsapiSignature.getAppid()+",noncestr:"+jsapiSignature.getNoncestr()
                +",timestamp:"+jsapiSignature.getTimestamp()+",signature:"+jsapiSignature.getSignature());
        response.setIsWeChat("Wechat");
        model.addAttribute("response", response);
        model.addAttribute("lng", lng);
        model.addAttribute("lat", lat);
        model.addAttribute("jsapiSignature", jsapiSignature);
        return "notice/noticeShow";
    }

    /**
     * 每日点评
     * @param teamId 腾讯组id
     * @param userID
     * @param model
     * @return
     */
    @RequestMapping(value = "/showJournarComment", method = RequestMethod.GET)
    public String showJournarComment(@RequestParam String teamId,Long userID,Model model){
        logger.info("每日点评 teamId:" + teamId + ",userID:" + userID);
        if (StringUtils.isEmpty(teamId)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：团队id【teamId】不能为空！");
        }
        ScheduleResponse scheduleResponse = scheduleService.getScheduleInfo(teamId);
        if (scheduleResponse.getRetCode() == ErrorConstant.NOT_FOUND) {
            throw new BusinessException(ErrorConstant.NOT_FOUND, "未找到对应行程!");
        }
        ScheduleEntity scheduleEntity = scheduleResponse.getScheduleEntity();
        model.addAttribute("scheduleEntity", scheduleEntity);
        // 是否已经评论, 0表示未评论, 1表示已经评论, 2表示没有可点评行程
        Integer commented = 0;
        // 如果当时间在第一天12点前,不让游客点评
        DateTime startDateTime = new DateTime(scheduleEntity.getStartDate());
        DateTime firstDate = startDateTime.plusHours(12);
        if (firstDate.isAfterNow()) {
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
            model.addAttribute("isWeChat", "Wechat");
            model.addAttribute("commented", commented);
            return "comment/comment";
        }
        // 根据teamId获取可点评行程
        ScheduleDetailResponse scheduleDetailResponse = scheduleDetailsService.getCommentableDetail(teamId,userID);
        if (scheduleDetailResponse.getRetCode() == ErrorConstant.NOT_FOUND) {
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
        model.addAttribute("isWeChat", "Wechat");
        model.addAttribute("commented", commented);
        return "comment/comment";
    }

    /**
     * 提交点评
     * @param model
     * @return
     */
    @RequestMapping(value = "/submitJournarComment", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse submitJournarComment(@RequestBody ScheduleDetailCommentRequest scheduleDetailCommentRequest,Model model) {

        if (org.springframework.util.StringUtils.isEmpty(scheduleDetailCommentRequest.getScheduleDetailId())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：行程详细id【scheduleDetailId】不能为空！");
        }
        if (org.springframework.util.StringUtils.isEmpty(scheduleDetailCommentRequest.getSatisfiedStatus())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：满意度标识【satisfiedStatus】不能为空！");
        }

        UserResponse userResponse = userLoginHelper.getLoginUser();
        if (userResponse.getRetCode() != ErrorConstant.SUCCESS) {
            throw new BusinessException(ErrorConstant.USER_NOT_LOGIN, "用户未登录或者登录会话已经过期！");
        }
        logger.info("提交点评 行程详细id:"+scheduleDetailCommentRequest.getScheduleDetailId()+",满意度标识:"+scheduleDetailCommentRequest.getSatisfiedStatus()+",user_id:"+userResponse.getUserEntity().getId());
        //Long.parseLong(String.valueOf(66753))
        scheduleDetailCommentRequest.setUserId(userResponse.getUserEntity().getId());
        try {
            scheduleDetailCommentService.submitJournarComment(scheduleDetailCommentRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorConstant.INTERNAL_SERVER_ERROR, "提交点评失败");
        }

        CommonResponse response = new CommonResponse();
        return response;
    }

    /**
     * 问卷填写
     * @param txGroupId
     * @param model
     * @return
     */
    @RequestMapping(value = "/getSurvey", method = RequestMethod.GET)
    public String  getSurvey(String txGroupId,Long userID, Model model) {

        logger.info("问卷填写 txGroupId:"+txGroupId+",userID:"+userID);
        if (org.springframework.util.StringUtils.isEmpty(txGroupId)) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：腾讯群组id不能为空！");
        }

        SurveyRequest request = new SurveyRequest();
        request.setTxGroupId(txGroupId);
        request.setUserId(userID);
        SurveyQuestionResponse response = surveyService.getSurvey(request);
        model.addAttribute("isWeChat", "Wechat");
        model.addAttribute("response", response);
        model.addAttribute("txGroupId", ELUtil.getUrlEncodeTeamId(txGroupId));
        return "journey/surveyuser";
    }

    /**
     * 进入签名页面
     * @return
     */
    @RequestMapping(value = "/getSign", method = RequestMethod.GET)
    public String getSign(String txGroupId,Model model){
        logger.info("进入签名页面 txGroupId:"+txGroupId);
        model.addAttribute("txGroupId",txGroupId);
        return "weixin/signature";
    }

    /**
     * 保存游客签名
     * @return
     */
    @RequestMapping(value = "/submitSignUrl", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse submitSignUrl(@RequestBody SurveyAnswerRequest request,Model model,HttpServletRequest req) {
        logger.info("游客签名Base64数据:"+request.getImg64Str());
        if (org.springframework.util.StringUtils.isEmpty(request.getImg64Str()) || org.springframework.util.StringUtils.isEmpty(request.getTxGroupId())) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "请求参数不能为空！");
        }
        BASE64Decoder decoder = new BASE64Decoder();
        String imgFilePath = null;
        String imgName = IDUtil.uuid();
        try {
            byte[] b = decoder.decodeBuffer(StringUtils.substringAfter(request.getImg64Str(),"base64,"));
            String s = StringUtils.substringAfter(request.getImg64Str(), "base64,");
            logger.info("base64数据："+s);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据  
                    b[i] += 256;
                }
            }
            String folder=System.getProperty("java.io.tmpdir");
            imgFilePath = folder+"//"+ imgName+".png";
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            logger.info("图片ok!imgFilePath:"+imgFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String filePath = "/"+imgName+".png";
        String signCloudUrl = null;
        try {
            InputStream inputStream = new FileInputStream(imgFilePath);
            logger.info("imgFilePath:"+imgFilePath);
            signCloudUrl = new QCloudHelper().fileUpload(filePath, inputStream);
            logger.info("filePath:"+filePath);
            logger.info("上传腾讯云ok!"+signCloudUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 1、获取当前登录的用户信息
        UserResponse user = userLoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return CommonResponse.createUserNotLoginResponse();
        }
        SurveyRequest surveyRequest = new SurveyRequest();
        surveyRequest.setUserId(user.getUserEntity().getId());
        surveyRequest.setTxGroupId(request.getTxGroupId());
        surveyRequest.setSignUrl(signCloudUrl);
        CommonResponse response = surveyDetailService.submitSignUrl(surveyRequest);
        return response;
    }

    /**
     * 提交问卷
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/submitSurvey", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse submitSurvey(@RequestBody SurveyAnswerRequest request,Model model) {
        logger.info("提交问卷");
        if (request == null || request.getAnswerList() == null) {
            throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "请求参数不能为空！");
        }
        UserResponse user = userLoginHelper.getLoginUser();
        if (user.getRetCode() != ErrorConstant.SUCCESS) {
            return CommonResponse.createUserNotLoginResponse();
        }
        CommonResponse response = surveyService.submitSurvey(request);
        return response;
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
    @RequestMapping(value = "/selectOneSpot", method = RequestMethod.POST)
    @ResponseBody
    public SpotEntity selectOneSpot(@RequestBody ScheduleShowRequest request ) {
        SpotEntity spotEntity=spotService.selectOneSpot(request.getMafengId());
        return spotEntity;

    }

    /**
     * 根据景点ID 获取景点和照片的信息
     * @param scenId
     * @return
     */
    @RequestMapping(value ="/getScenicByScenId")
    public String getScenic(ScheduleShowRequest showRequest,Long scenId, Model model){
        ScheduleDetailScenicEntity scenicEntity = scheduleDetailScenicService.getScenicByScenicId(scenId);
        model.addAttribute("scenicEntity",scenicEntity);
        return getReturnPage(showRequest.getFlag());
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
        }
        if("cost".equals(flag)){
            return "journey/costInfo";
        }
        if("shop".equals(flag)){
            return "journey/shopInfo";
        }
        if("prompt".equals(flag)){
            return "journey/promptInfo";
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
        return "journey/scheduleIntroduce";
    }

    /**
     * 获取通知签名的详细信息
     * @param teamId
     * @param noticeId
     * @param userId
     * @return
     */
    @RequestMapping(value ="/getNoticSignMessages")
    public String getNoticSignMessage(String teamId, Long noticeId, Long userId, Model model){

        //teamId,noticeId,roleStatus,user.getUserEntity()
        //ScheduleDetailScenicEntity scenicEntity = scheduleDetailScenicService.getScenicByScenicId(scenId);
        //model.addAttribute("scenicEntity",scenicEntity);
        return null;
    }

}
