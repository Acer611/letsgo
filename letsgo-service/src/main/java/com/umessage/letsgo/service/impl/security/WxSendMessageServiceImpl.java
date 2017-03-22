package com.umessage.letsgo.service.impl.security;

import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.core.utils.ELUtil;
import com.umessage.letsgo.domain.po.activity.RoomEntity;
import com.umessage.letsgo.domain.po.journey.HotelScheduleEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.notice.AnnouncementEntity;
import com.umessage.letsgo.domain.po.notice.NoticeEntity;
import com.umessage.letsgo.domain.po.security.WxInfoEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.activity.request.RoomRequest;
import com.umessage.letsgo.service.api.activity.IRoomService;
import com.umessage.letsgo.service.api.journey.IHotelScheduleService;
import com.umessage.letsgo.service.api.journey.IScheduleDetailsService;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.security.IWxInfoService;
import com.umessage.letsgo.service.api.security.IWxSendMessageService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.common.constant.Constant;
import com.umessage.letsgo.service.common.constant.WxConstant;
import com.weixin.service.ITemplateMessage;
import com.weixin.util.TemplateData;
import com.weixin.util.TemplateMessageRequest;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WxSendMessageServiceImpl implements IWxSendMessageService {
    Logger logger = Logger.getLogger(WxSendMessageServiceImpl.class);
    @Resource
    private ITemplateMessage templateMessageService;
    @Resource
    private IScheduleService scheduleService;
    @Resource
    private IWxInfoService wxInfoService;
    @Resource
    protected WxMpService wxMpService;
    @Resource
    private IMemberService memberService;
    @Resource
    private ITeamService teamService;
    @Resource
    private IRoomService roomService;
    @Resource
    private IScheduleDetailsService scheduleDetailsService;
    @Resource
    private IHotelScheduleService hotelScheduleService;


    /**
     * 发送行程详情变更提醒
     * @param memberEntity
     * @param scheduleDetailEntity
     */
    @Override
    public void sendChangeNoticeTemplateMessage(MemberEntity memberEntity, ScheduleDetailEntity scheduleDetailEntity, MemberEntity memberLeaderEntity, String changeContent){
        if (memberEntity != null && memberLeaderEntity != null){
            //获取用户ID
            Long userId = memberEntity.getUserId();
            if (userId != null){
                //获取APPID
                String appId = wxMpService.getWxMpConfigStorage().getAppId();
                //关联查询openId
                WxInfoEntity wxInfoEntity = wxInfoService.selectWxInfoByUserId(userId, appId);
                if (wxInfoEntity != null){
                    TemplateMessageRequest request = new TemplateMessageRequest();
                    request.setOpenid(wxInfoEntity.getOpenid());//openid
                    String urlEncodeTeamId = ELUtil.getUrlEncodeTeamId(memberEntity.getTeamId());
                    request.setUrl(Constant.H5_WECHAT_URL+"getScheduleShow?flag=preview&teamId="+urlEncodeTeamId+"&scheduleDetaildId="+scheduleDetailEntity.getId());//跳转路径
                    logger.info("跳转路径：" + request.getUrl());
                    Map<String,TemplateData> map = new HashMap<>();//模板内容

                    TemplateData first = new TemplateData();
                    first.setValue("尊敬的"+memberEntity.getRealName()+"您好，领队"+memberLeaderEntity.getRealName()+"修改了行程，请及时查看"+"\n");
                    map.put("first",first);

                    //线路名称
                    String name = memberEntity.gettName();
                    if (!StringUtils.isEmpty(name)){
                        name = ELUtil.substring(name, 15);
                    }
                    TemplateData keyword1 = new TemplateData();
                    keyword1.setValue(name);
                    map.put("keyword1",keyword1);

                    //行程日期
                    TemplateData keyword2 = new TemplateData();
                    keyword2.setValue("第"+scheduleDetailEntity.getDayNum()+"天"+"（"+DateUtils.toString(scheduleDetailEntity.getScheduleDate(), "yyyy年MM月dd日")+")");
                    map.put("keyword2",keyword2);

                    TemplateData remark = new TemplateData();
                    remark.setValue("\n"+changeContent);
                    map.put("remark",remark);

                    request.setData(map);
                    templateMessageService.sendChangeNoticeTemplate(request);
                }
            }
        }
    }



    /**
     * 发送用户未绑定微信模板消息
     * @param wxNickName
     */
    @Override
    public void sendBindNoticeTemplateMessage(String openID,String wxNickName){
        TemplateMessageRequest request = new TemplateMessageRequest();
        request.setOpenid(openID);
        request.setUrl(Constant.H5_WECHAT_URL+WxConstant.BINDING_LINK);//跳转路径
        logger.info("跳转路径：" + request.getUrl());
        Map<String,TemplateData> map = new HashMap<>();//模板内容

        //微信号
        TemplateData keyword1 = new TemplateData();
        keyword1.setValue(wxNickName);
        map.put("keyword1",keyword1);

        //绑定状态
        TemplateData keyword2 = new TemplateData();
        keyword2.setValue("未绑定");
        map.put("keyword2",keyword2);

        TemplateData remark = new TemplateData();
        remark.setValue("\n"+"点击验证用户信息。验证用户信息后可以接收到集合、通知、公告、分房和行程提醒消息；并且只有验证后，方可保留已出行行程");
        map.put("remark",remark);

        request.setData(map);
        templateMessageService.sendBindNoticeTemplate(request);
    }


    /**
     * 发送微信扫描入团模板消息
     * @param teamId
     */
    @Override
    public void sendJoinTeamTemplateMessage( String openID,Long teamId,String txGroupid){
        if (teamId != null){
            //查询行程
            ScheduleEntity scheduleEntity = scheduleService.getScheduleByTid(teamId);
            if (scheduleEntity != null){
                TemplateMessageRequest request = new TemplateMessageRequest();
                request.setOpenid(openID);
                request.setUrl(Constant.H5_WECHAT_URL+"getScheduleShow?flag=introduce&teamId="+ ELUtil.getUrlEncodeTeamId(txGroupid));//跳转路径
                logger.info("跳转路径：" + request.getUrl());
                Map<String,TemplateData> map = new HashMap<>();//模板内容

                TemplateData first = new TemplateData();
                first.setValue("欢迎加入团队"+"\n");
                map.put("first",first);

                //行程名称
                TemplateData keyword1 = new TemplateData();
                keyword1.setValue(scheduleEntity.getName());
                map.put("keyword1",keyword1);

                //出行时间
                TemplateData keyword2 = new TemplateData();
                keyword2.setValue(DateUtils.toString(scheduleEntity.getStartDate(), "yyyy年MM月dd日")+"至"+DateUtils.toString(scheduleEntity.getEndDate(), "yyyy年MM月dd日"));
                map.put("keyword2",keyword2);

                //出行国家
                TemplateData keyword3 = new TemplateData();
                keyword3.setValue(this.getPlace(scheduleEntity.getEndPlace()));
                map.put("keyword3",keyword3);

                TemplateData remark = new TemplateData();
                remark.setValue("\n"+"点击查看详细行程");
                map.put("remark",remark);

                request.setData(map);
                templateMessageService.sendScheduleRemindTemplate(request);
            }
        }
    }

    //替换分隔符
    public String getPlace(String endPlace){
        String sreEndPlace = endPlace;
        if(!StringUtils.isEmpty(endPlace)){
            if (endPlace.indexOf("；") != -1){
                sreEndPlace = endPlace.replace("、", "；");
            }else if (endPlace.indexOf(";") != -1){
                sreEndPlace = endPlace.replace("、", ";");
            }else if (endPlace.indexOf(" ") != -1){
                sreEndPlace = endPlace.replace("、", " ");
            }else if (endPlace.indexOf(",") != -1){
                sreEndPlace = endPlace.replace("、", ",");
            }
        }
        return sreEndPlace;
    }



    /**
     * 发送微信扫描无法加入模板消息
     * @param teamId
     */
    @Override
    public void sendNotJoinTeamTemplateMessage(String openID, Long teamId){
        if (teamId != null){
            //查询行程
            ScheduleEntity scheduleEntity = scheduleService.getScheduleByTid(teamId);
            if (scheduleEntity != null){
                TemplateMessageRequest request = new TemplateMessageRequest();
                request.setOpenid(openID);
                request.setUrl("");//跳转路径
                Map<String,TemplateData> map = new HashMap<>();//模板内容

                TemplateData first = new TemplateData();
                first.setValue("无法加入"+"\n");
                map.put("first",first);

                TemplateData keyword1 = new TemplateData();
                keyword1.setValue(scheduleEntity.getName());
                map.put("keyword1",keyword1);

                TemplateData keyword2 = new TemplateData();
                keyword2.setValue("无法加入");
                map.put("keyword2",keyword2);

                TemplateData keyword3 = new TemplateData();
                keyword3.setValue("团队已出行，无法加入此团队");
                map.put("keyword3",keyword3);

                TemplateData remark = new TemplateData();
                remark.setValue("\n"+"如有疑问，可与团队领队沟通");
                map.put("remark",remark);

                request.setData(map);
                templateMessageService.sendNoticeTemplate(request);
            }
        }
    }


    /**
     * 发送每日点评模板消息
     * @param memberEntity
     */
    public void sendCommentNoticeTemplateMessage(MemberEntity memberEntity){
        if (memberEntity != null){
            //获取用户ID
            Long userId = memberEntity.getUserId();
            if (userId != null){
                //获取APPID
                String appId = wxMpService.getWxMpConfigStorage().getAppId();
                //关联查询openId
                WxInfoEntity wxInfoEntity = wxInfoService.selectWxInfoByUserId(userId, appId);
                if (wxInfoEntity != null){
                    TemplateMessageRequest request = new TemplateMessageRequest();
                    request.setOpenid(wxInfoEntity.getOpenid());//openid
                    String urlEncodeTeamId = ELUtil.getUrlEncodeTeamId(memberEntity.getTeamId());
                    long timeInMillis = Calendar.getInstance().getTimeInMillis();
                    //request.setUrl(WxConstant.EVERYDAYCOMMENT+"?teamId="+urlEncodeTeamId+"&userID="+memberEntity.getUserId()+"&timeSt="+timeInMillis);//跳转路径
                    request.setUrl(Constant.H5_WECHAT_URL+"showJournarComment?teamId="+urlEncodeTeamId+"&userID="+memberEntity.getUserId());//跳转路径
                    logger.info("跳转路径：" + Constant.H5_WECHAT_URL+"showJournarComment?teamId="+urlEncodeTeamId+"&userID="+memberEntity.getUserId());
                    Map<String,TemplateData> map = new HashMap<>();//模板内容

                    TemplateData first = new TemplateData();
                    first.setValue("尊敬的"+memberEntity.getRealName()+"您好，美好的一天即将结束，跟上小咖邀请您做个评价"+"\n");
                    map.put("first",first);

                    //行程消息:团队名称
                    String name = memberEntity.gettName();
                    if (!StringUtils.isEmpty(name)){
                        name = ELUtil.substring(name, 15);
                    }
                    TemplateData keyword1 = new TemplateData();
                    keyword1.setValue(name);
                    map.put("keyword1",keyword1);

                    //出发时间
                    TemplateData keyword2 = new TemplateData();
                    keyword2.setValue(DateUtils.toString(new Date(), "yyyy年MM月dd日"));
                    map.put("keyword2",keyword2);

                    TemplateData remark = new TemplateData();
                    remark.setValue("\n"+"点击评价当日行程");
                    map.put("remark",remark);

                    request.setData(map);
                    templateMessageService.sendCommentNoticeTemplate(request);
                }
            }
        }
    }


    /**
     * 发送次日天气模板消息
     * @param memberEntity
     * @param scheduleDetailEntity
     */
    public void sendWeatherRemindTemplateMessage(MemberEntity memberEntity, ScheduleDetailEntity scheduleDetailEntity){
        if (memberEntity != null){
            //获取用户ID
            Long userId = memberEntity.getUserId();
            if (userId != null){
                //获取APPID
                String appId = wxMpService.getWxMpConfigStorage().getAppId();
                //关联查询openId
                WxInfoEntity wxInfoEntity = wxInfoService.selectWxInfoByUserId(userId, appId);
                if (wxInfoEntity != null){
                    TemplateMessageRequest request = new TemplateMessageRequest();
                    request.setOpenid(wxInfoEntity.getOpenid());//openid
                    request.setUrl("http://t.cn/RMRibkz");//跳转路径
                    Map<String,TemplateData> map = new HashMap<>();//模板内容

                    TemplateData first = new TemplateData();
                    first.setValue("尊敬的"+memberEntity.getRealName()+"您好，跟上小咖提醒您明日("+DateUtils.toString(scheduleDetailEntity.getScheduleDate(), "yyyy年MM月dd日")+")天气"+"\n");
                    map.put("first",first);

                    //如果开始地没有，则开始地数据其实为目的地
                    String destination1 = scheduleDetailEntity.getDestination1();
                    if (StringUtils.isEmpty(destination1)){
                        //出发地天气
                        TemplateData keyword1 = new TemplateData();
                        keyword1.setValue("暂无数据");
                        map.put("keyword1",keyword1);

                        //目的地天气
                        TemplateData keyword2 = new TemplateData();
                        keyword2.setValue(scheduleDetailEntity.getStartPlace()+"，"+scheduleDetailEntity.getStartPlaceWeatherDescn());
                        map.put("keyword2",keyword2);
                    }else {
                        //出发地天气
                        TemplateData keyword1 = new TemplateData();
                        keyword1.setValue(scheduleDetailEntity.getStartPlace()+"，"+scheduleDetailEntity.getStartPlaceWeatherDescn());
                        map.put("keyword1",keyword1);

                        //目的地天气
                        TemplateData keyword2 = new TemplateData();
                        keyword2.setValue(scheduleDetailEntity.getDestination1()+"，"+scheduleDetailEntity.getFirstDayWeatherDescn());
                        map.put("keyword2",keyword2);
                    }

                    //温馨提醒
                    TemplateData keyword3 = new TemplateData();
                    keyword3.setValue("建议您根据天气及时增减衣物");
                    map.put("keyword3",keyword3);

                    TemplateData remark = new TemplateData();
                    remark.setValue("\n"+"点击下载跟上App使用更丰富的功能");
                    map.put("remark",remark);

                    request.setData(map);
                    templateMessageService.sendWeatherRemindTemplate(request);
                }
            }
        }
    }


    /**
     * 发送次日行程模板消息
     * @param memberEntity
     * @param scheduleDetailEntity
     */
    public void sendScheduleRemindTemplateMessage(MemberEntity memberEntity, ScheduleDetailEntity scheduleDetailEntity){
        if (memberEntity != null){
            //获取用户ID
            Long userId = memberEntity.getUserId();
            if (userId != null){
                //获取APPID
                String appId = wxMpService.getWxMpConfigStorage().getAppId();
                //关联查询openId
                WxInfoEntity wxInfoEntity = wxInfoService.selectWxInfoByUserId(userId, appId);
                if (wxInfoEntity != null){
                    TemplateMessageRequest request = new TemplateMessageRequest();
                    request.setOpenid(wxInfoEntity.getOpenid());//openid
                    String urlEncodeTeamId = ELUtil.getUrlEncodeTeamId(memberEntity.getTeamId());
                    request.setUrl(Constant.H5_WECHAT_URL+"getScheduleShow?flag=preview&teamId="+urlEncodeTeamId+"&scheduleDetaildId="+scheduleDetailEntity.getId());//跳转路径
                    logger.info("跳转路径：" + request.getUrl());
                    Map<String,TemplateData> map = new HashMap<>();//模板内容

                    TemplateData first = new TemplateData();
                    first.setValue("尊敬的"+memberEntity.getRealName()+"您好，跟上小咖提醒您明日("+DateUtils.toString(scheduleDetailEntity.getScheduleDate(), "yyyy年MM月dd日")+")行程"+"\n");
                    map.put("first",first);

                    //行程安排
                    TemplateData keyword1 = new TemplateData();
                    keyword1.setValue(this.getDestination(scheduleDetailEntity));
                    map.put("keyword1",keyword1);

                    //出行时间
                    TemplateData keyword2 = new TemplateData();
                    keyword2.setValue(DateUtils.toString(scheduleDetailEntity.getScheduleDate(), "yyyy年MM月dd日"));
                    map.put("keyword2",keyword2);

                    //出行国家
                    TemplateData keyword3 = new TemplateData();
                    keyword3.setValue(scheduleDetailEntity.getCountry1());
                    map.put("keyword3",keyword3);

                    TemplateData remark = new TemplateData();
                    remark.setValue("\n"+"点击查看详细行程");
                    map.put("remark",remark);

                    request.setData(map);
                    templateMessageService.sendScheduleRemindTemplate(request);
                }
            }
        }
    }


    private String getDestination(ScheduleDetailEntity scheduleDetailEntity){
        String destination = "";
        String startPlace = scheduleDetailEntity.getStartPlace();
        String destination4 = scheduleDetailEntity.getDestination4();
        String destination3 = scheduleDetailEntity.getDestination3();
        String destination2 = scheduleDetailEntity.getDestination2();
        String destination1 = scheduleDetailEntity.getDestination1();
        if (!StringUtils.isEmpty(startPlace)) {
            destination = startPlace;
        } else if (!StringUtils.isEmpty(destination1)) {
            destination = destination + "-" + destination1;
        } else if (!StringUtils.isEmpty(destination2)) {
            destination = destination + "-" + destination2;
        } else if (!StringUtils.isEmpty(destination3)) {
            destination = destination + "-" + destination3;
        } else if (!StringUtils.isEmpty(destination4)) {
            destination = destination + "-" + destination4;
        }
        return destination;
    }



    /**
     * 发送 满意度调查 通知消息
     * @param memberEntity
     * @param teamEntity
     */
    public void sendCommentNoticeTemplateMessage(MemberEntity memberEntity, TeamEntity teamEntity, ScheduleEntity scheduleEntity){
        if (memberEntity != null){
            //获取用户ID
            Long userId = memberEntity.getUserId();
            if (userId != null){
                //获取APPID
                String appId = wxMpService.getWxMpConfigStorage().getAppId();
                //关联查询openId
                WxInfoEntity wxInfoEntity = wxInfoService.selectWxInfoByUserId(userId, appId);
                if (wxInfoEntity != null){
                    TemplateMessageRequest request = new TemplateMessageRequest();
                    request.setOpenid(wxInfoEntity.getOpenid());//openid
                    String urlEncodeTeamId = ELUtil.getUrlEncodeTeamId(memberEntity.getTeamId());
                    request.setUrl(Constant.H5_WECHAT_URL+"getSurvey?txGroupId="+urlEncodeTeamId+"&userID="+memberEntity.getUserId());//跳转路径
                    logger.info("跳转路径：" + Constant.H5_WECHAT_URL+"getSurvey?txGroupId="+urlEncodeTeamId+"&userID="+memberEntity.getUserId());
                    Map<String,TemplateData> map = new HashMap<>();//模板内容

                    TemplateData first = new TemplateData();
                    first.setValue("尊敬的"+memberEntity.getRealName()+"您好，美好的行程已接近尾声，诚邀您参加满意度调查"+"\n");
                    map.put("first",first);

                    //行程信息
                    String name = teamEntity.getName();
                    if (!StringUtils.isEmpty(name)){
                        name = ELUtil.substring(name, 15);
                    }
                    TemplateData keyword1 = new TemplateData();
                    keyword1.setValue(name);
                    map.put("keyword1",keyword1);

                    //出行时间
                    TemplateData keyword2 = new TemplateData();
                    keyword2.setValue(DateUtils.toString(scheduleEntity.getStartDate(), "yyyy年MM月dd日")+"至"+DateUtils.toString(scheduleEntity.getEndDate(), "yyyy年MM月dd日"));
                    map.put("keyword2",keyword2);

                    TemplateData remark = new TemplateData();
                    remark.setValue("\n"+"点击参与满意度调查");
                    map.put("remark",remark);

                    request.setData(map);
                    templateMessageService.sendCommentNoticeTemplate(request);
                }
            }
        }
    }



    /**
     * 发送通知模板消息
     * @param memberList
     * @param notice
     */
    public void sendNoticeTemplateMessage(List<MemberEntity> memberList, NoticeEntity notice, MemberEntity sendMemberEntity, String teamName){
        //获取发送人
        if (sendMemberEntity != null){
            for (MemberEntity memberEntity : memberList) {
                Long userId = memberEntity.getUserId();
                if (userId != null){
                    //关联查询openId
                    String appId = wxMpService.getWxMpConfigStorage().getAppId();
                    WxInfoEntity wxInfoEntity = wxInfoService.selectWxInfoByUserId(userId, appId);
                    if (wxInfoEntity != null){
                        TemplateMessageRequest request = new TemplateMessageRequest();
                        request.setOpenid(wxInfoEntity.getOpenid());//openid

                        // 设置跳转路径, 如果是语音通知，则跳转到APP；如果不是语音通知，直接显示
                        if(!StringUtils.isEmpty(notice.getVideoUrl())){
                            request.setUrl("http://t.cn/RMRibkz");
                        } else if(!StringUtils.isEmpty(notice.getContent())) {
                            request.setUrl(Constant.H5_WECHAT_URL+"getNoticeMessage?ID="+notice.getId()+"&userId="+memberEntity.getUserId());
                        } else {
                            request.setUrl("");
                        }

                        logger.info("跳转路径：" + request.getUrl());
                        Map<String,TemplateData> map = new HashMap<>();//模板内容

                        TemplateData first = new TemplateData();
                        first.setValue("尊敬的"+memberEntity.getRealName()+"您好，"+this.getRoleName(sendMemberEntity.getRole())+sendMemberEntity.getRealName()+"特别提醒您"+"\n");
                        map.put("first",first);

                        TemplateData keyword1 = new TemplateData();
                        keyword1.setValue(teamName);
                        map.put("keyword1",keyword1);

                        TemplateData keyword2 = new TemplateData();
                        keyword2.setValue("通知");
                        map.put("keyword2",keyword2);

                        //如果没有通知内容则为语音
                        String content = notice.getContent();
                        // 设置通知内容
                        if(!StringUtils.isEmpty(notice.getVideoUrl())){
                            content = "【语音】内容，请下载跟上APP查看";
                        } else if(!StringUtils.isEmpty(notice.getContent())) {
                            content = ELUtil.substring(content, 150);
                        } else {
                            content = "";
                        }

                        TemplateData keyword3 = new TemplateData();
                        keyword3.setValue(content);
                        map.put("keyword3",keyword3);

                        TemplateData remark = new TemplateData();

                        // 设置备注
                        if(!StringUtils.isEmpty(notice.getVideoUrl())){
                            remark.setValue("\n"+"点击下载跟上App查看");
                        } else if(!StringUtils.isEmpty(notice.getContent())) {
                            remark.setValue("\n"+"点击确认收到并查看详情");
                        } else {
                            remark.setValue("");
                        }

                        map.put("remark",remark);

                        request.setData(map);
                        templateMessageService.sendNoticeTemplate(request);
                    }
                }
            }
        }
    }


    /**
     * 发送集合模板消息
     * @param memberList
     * @param notice
     */
    public void sendGatherTemplateMessage(List<MemberEntity> memberList, NoticeEntity notice, MemberEntity sendMemberEntity, String teamName){
        //获取发送人
        if (sendMemberEntity != null){
            for (MemberEntity memberEntity : memberList) {
                Long userId = memberEntity.getUserId();
                if (userId != null){
                    //关联查询openId
                    String appId = wxMpService.getWxMpConfigStorage().getAppId();
                    WxInfoEntity wxInfoEntity = wxInfoService.selectWxInfoByUserId(userId, appId);
                    if (wxInfoEntity != null){
                        TemplateMessageRequest request = new TemplateMessageRequest();
                        request.setOpenid(wxInfoEntity.getOpenid());//openid

                        // 设置跳转路径, 如果是语音通知，则跳转到APP；如果不是语音通知，直接显示
                        if(!StringUtils.isEmpty(notice.getVideoUrl())){
                            request.setUrl("http://t.cn/RMRibkz");
                        } else if(!StringUtils.isEmpty(notice.getContent())) {
                            request.setUrl(Constant.H5_WECHAT_URL+"getGatherMessage?noticeId="+notice.getId()+"&userId="+memberEntity.getUserId());//跳转路径
                        } else {
                            request.setUrl("");
                        }

                        logger.info("跳转路径：" + request.getUrl());
                        Map<String,TemplateData> map = new HashMap<>();//模板内容

                        TemplateData first = new TemplateData();
                        first.setValue(teamName+"集合通知"+"\n");
                        map.put("first",first);

                        //集合时间
                        TemplateData keyword1 = new TemplateData();
                        keyword1.setValue(notice.getTimezone()+DateUtils.toString(notice.getTime(), "MM月dd日 HH:mm"));
                        map.put("keyword1",keyword1);

                        //集合地点
                        TemplateData keyword2 = new TemplateData();
                        keyword2.setValue(notice.getLocation());
                        map.put("keyword2",keyword2);

                        //发送人姓名+发送人电话
                        TemplateData keyword3 = new TemplateData();
                        keyword3.setValue(sendMemberEntity.getRealName()+sendMemberEntity.getPhone());
                        map.put("keyword3",keyword3);

                        TemplateData remark = new TemplateData();

                        // 设置备注
                        if(!StringUtils.isEmpty(notice.getVideoUrl())){
                            remark.setValue("\n"+"集合内容为语音消息，点击下载跟上App查看");
                        } else if(!StringUtils.isEmpty(notice.getContent())) {
                            remark.setValue("\n"+"请准时到达，点击确认收到并查看详情");
                        } else {
                            remark.setValue("");
                        }

                        map.put("remark",remark);

                        request.setData(map);
                        templateMessageService.sendGatherNoticeTemplate(request);
                    }
                }
            }
        }
    }



    /**
     * 获取角色名称
     * @param role
     * @return
     */
    private String getRoleName(Integer role){
        String roleName = "";
        if (1 == role){
            roleName = "领队";
        }else if (2 == role){
            roleName = "导游";
        }else if (3 == role){
            roleName = "游客";
        }
        return roleName;
    }


    /**
     * 发送集合提醒
     * @param memberEntity
     * @param notice
     */
    public void sendGatherRemindTemplateMessage(MemberEntity memberEntity, NoticeEntity notice){
        //获取发送人
        MemberEntity sendMemberEntity = null;
        if (notice != null){
            sendMemberEntity = memberService.getMemberWithTeamIdAndUserId(notice.getTeamId(), notice.getUserId());
        }
        //获取团队
        TeamEntity teamEntity = null;
        if (memberEntity != null){
            teamEntity = teamService.selectById(memberEntity.gettId());
            //获取用户ID
            Long userId = memberEntity.getUserId();
            if (userId != null){
                //获取APPID
                String appId = wxMpService.getWxMpConfigStorage().getAppId();
                //关联查询openId
                WxInfoEntity wxInfoEntity = wxInfoService.selectWxInfoByUserId(userId, appId);
                if (wxInfoEntity != null){
                    TemplateMessageRequest request = new TemplateMessageRequest();
                    request.setOpenid(wxInfoEntity.getOpenid());//openid
                    request.setUrl(Constant.H5_WECHAT_URL+"getGatherLocation?ID="+notice.getId());//跳转路径
                    logger.info("跳转路径：" + request.getUrl());
                    Map<String,TemplateData> map = new HashMap<>();//模板内容

                    TemplateData first = new TemplateData();
                    first.setValue(this.getRoleName(sendMemberEntity.getRole())+sendMemberEntity.getRealName()+"温馨提醒您，团队即将集合"+"\n");
                    map.put("first",first);

                    //团队名称
                    String name = teamEntity.getName();
                    if (!StringUtils.isEmpty(name)){
                        name = ELUtil.substring(name, 15);
                    }
                    TemplateData keyword1 = new TemplateData();
                    keyword1.setValue(name);
                    map.put("keyword1",keyword1);

                    //提醒类型
                    TemplateData keyword2 = new TemplateData();
                    keyword2.setValue("集合提醒");
                    map.put("keyword2",keyword2);

                    //提醒内容
                    TemplateData keyword3 = new TemplateData();
                    keyword3.setValue("请于"+notice.getFirstRemind()+"分钟后到"+notice.getLocation()+"集合");
                    map.put("keyword3",keyword3);

                    TemplateData remark = new TemplateData();
                    remark.setValue("\n"+"请您抓紧时间，以免耽误行程，点击查看地图");
                    map.put("remark",remark);

                    request.setData(map);
                    templateMessageService.sendNoticeTemplate(request);
                }
            }
        }
    }


    /**
     * 发送入住通知
     * @param roomRequests
     */
    public void sendCheckInNoticeTemplateMessage(List<RoomRequest> roomRequests){
        if (roomRequests.size() > 0){
            String teamId = roomRequests.get(0).getTeamId();
            List<MemberEntity> memberEntityList =  null;
            ScheduleDetailEntity scheduleDetailEntity = null;
            if (!StringUtils.isEmpty(teamId)){
                memberEntityList = memberService.getTouristList(teamId);
                List<ScheduleDetailEntity> scheduleDetails = scheduleDetailsService.getScheduleDetails(teamId, null);
                //查询行程里出行时间和发送时间最近的行程详细，以判断发送入住通知的酒店是哪个行程详细的
                SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
                for (ScheduleDetailEntity scheduleDetail:scheduleDetails) {
                    try {
                        Date d = new Date();
                        String newFormat = simpleDateFormat.format(d);
                        Date currentDateFormat = simpleDateFormat.parse(newFormat);
                        long time = currentDateFormat.getTime();
                        Date scheduleDate = scheduleDetail.getScheduleDate();
                        String scheduleDateString = simpleDateFormat.format(scheduleDate);
                        Date scheduleDateFormat = simpleDateFormat.parse(scheduleDateString);
                        long scheduleTime = scheduleDateFormat.getTime();
                        if(scheduleTime >= time){
                            scheduleDetailEntity = scheduleDetail;
                            break;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            for (MemberEntity memberEntity : memberEntityList) {
                //根据成员查询房间
                RoomEntity roomEntity = roomService.selectByMemberId(memberEntity.getId());
                if (roomEntity != null){
                    //查询与该游客同房的所有游客
                    List<MemberEntity> roomMember = memberService.getByRoomId(roomEntity.getId());
                    //获取用户ID
                    Long userId = memberEntity.getUserId();
                    if (userId != null){
                        //获取APPID
                        String appId = wxMpService.getWxMpConfigStorage().getAppId();
                        //关联查询openId
                        WxInfoEntity wxInfoEntity = wxInfoService.selectWxInfoByUserId(userId, appId);
                        if (wxInfoEntity != null){
                            TemplateMessageRequest request = new TemplateMessageRequest();
                            request.setOpenid(wxInfoEntity.getOpenid());//openid
                            request.setUrl(WxConstant.ROOMNOTICE);//跳转路径
                            logger.info("跳转路径：" + request.getUrl());
                            Map<String,TemplateData> map = new HashMap<>();//模板内容

                            TemplateData first = new TemplateData();
                            first.setValue("尊敬的"+memberEntity.getRealName()+"您好，请查看"+this.getHotel(scheduleDetailEntity)+"入住信息"+"\n");
                            map.put("first",first);

                            //房间号码
                            String roomNum = roomEntity.getRoomNum();
                            if (org.springframework.util.StringUtils.isEmpty(roomNum)){
                                roomNum = "暂未分配";
                            }
                            TemplateData keyword1 = new TemplateData();
                            keyword1.setValue(roomNum);
                            map.put("keyword1",keyword1);

                            //入住时间
                            TemplateData keyword2 = new TemplateData();
                            keyword2.setValue(DateUtils.toString(new Date(), "yyyy年MM月dd日"));
                            map.put("keyword2",keyword2);

                            //提示信息
                            String prompts = "";
                            if (this.isMemberInRoom(roomMember, memberEntity)){
                                prompts = "下载跟上APP可查看同组人员入住情况";
                            }
                            TemplateData keyword3 = new TemplateData();
                            keyword3.setValue("");
                            map.put("keyword3",keyword3);

                            //房间人员
                            TemplateData remark = new TemplateData();
                            remark.setValue("\n"+"该房间入住人包括："+this.getMemberName(roomMember)+prompts);
                            map.put("remark",remark);

                            request.setData(map);
                            templateMessageService.sendCheckInNoticeTemplate(request);
                        }
                    }
                }
            }
        }
    }


    //查询同组的人员是否全在该间房中，不全在该房间返回true，全在为false
    public Boolean isMemberInRoom(List<MemberEntity> roomMember, MemberEntity memberEntity){
        Boolean isIn = false;
        //查询同组人员
        Long groupId = memberEntity.getGroupId();
        if (groupId != null && groupId != -1){
            //按分组查询成员
            List<MemberEntity> groupMembertList = memberService.getGroupMembertList(groupId);
            if (!groupMembertList.containsAll(roomMember)){
                isIn = true;
            }
        }
        return  isIn;
    }


    public String getMemberName(List<MemberEntity> roomMembers){
        String memberName = "";
        for (int i = 0; i < roomMembers.size(); i++) {
            MemberEntity memberEntity = roomMembers.get(i);
            String realName = memberEntity.getRealName();
            if(!StringUtils.isEmpty(realName)){
                if (i < roomMembers.size() - 1){
                    memberName = memberName + realName + "、";
                }else {
                    memberName = memberName + realName + "。";
                }
            }
        }
        return memberName;
    }


    public String getHotel(ScheduleDetailEntity scheduleDetailEntity){
        String hotel = "";
        if (scheduleDetailEntity != null){
            HotelScheduleEntity hotelScheduleEntity = hotelScheduleService.selectByScheduleDetailId(scheduleDetailEntity.getId());
            if (hotelScheduleEntity != null){
                String hotelName = hotelScheduleEntity.getHotelName();
                if (StringUtils.isEmpty(hotelName)){
                    String hotelInput = scheduleDetailEntity.getHotelInput();
                    hotel = hotelInput;
                }else {
                    hotel = hotelName;
                }
            }
        }
        return hotel;
    }


    /**
     * 发送公告模板消息
     * @param memberEntityList
     * @param announcement
     * @param member
     */
    public void sendAnnouncementTemplateMessage(List<MemberEntity> memberEntityList, AnnouncementEntity announcement, MemberEntity member){
        //获取团队
        String teamName = this.getTeamName(memberEntityList);
        //判断发送人
        if (member != null){
            for (MemberEntity memberEntity : memberEntityList) {
                Long userId = memberEntity.getUserId();
                if (userId != null){
                    //关联查询WxInfo的openId
                    String appId = wxMpService.getWxMpConfigStorage().getAppId();
                    WxInfoEntity wxInfoEntity = wxInfoService.selectWxInfoByUserId(userId, appId);
                    if (wxInfoEntity != null){
                        TemplateMessageRequest request = new TemplateMessageRequest();
                        request.setOpenid(wxInfoEntity.getOpenid());//openid
                        request.setUrl(Constant.H5_WECHAT_URL+"getAnnouncementMessage?ID="+announcement.getId());//跳转路径
                        logger.info("跳转路径：" + request.getUrl());
                        Map<String,TemplateData> map = new HashMap<>();//模板内容

                        TemplateData first = new TemplateData();
                        first.setValue("尊敬的全体客人大家好，"+this.getRoleName(member.getRole())+member.getRealName()+"温馨提醒大家"+"\n");
                        map.put("first",first);

                        if (!StringUtils.isEmpty(teamName)){
                            teamName = ELUtil.substring(teamName, 15);
                        }
                        TemplateData keyword1 = new TemplateData();
                        keyword1.setValue(teamName);
                        map.put("keyword1",keyword1);

                        TemplateData keyword2 = new TemplateData();
                        keyword2.setValue("公告");
                        map.put("keyword2",keyword2);

                        String content = announcement.getContent();
                        if (!StringUtils.isEmpty(content)){
                            content = ELUtil.substring(content, 150);
                        }
                        TemplateData keyword3 = new TemplateData();
                        keyword3.setValue(content);
                        map.put("keyword3",keyword3);

                        TemplateData remark = new TemplateData();
                        remark.setValue("\n"+"点击查看详情");
                        map.put("remark",remark);

                        request.setData(map);
                        templateMessageService.sendNoticeTemplate(request);
                    }
                }
            }
        }
    }



    private String getTeamName(List<MemberEntity> memberEntityList){
        String teamName = "";
        if (memberEntityList.size() > 0){
            MemberEntity memberEntity1 = memberEntityList.get(0);
            Long tLong = memberEntity1.gettId();
            TeamEntity teamEntity = teamService.selectById(tLong);
            if (teamEntity != null){
                String name = teamEntity.getName();
                teamName = name;
            }
        }
        return teamName;
    }


}
