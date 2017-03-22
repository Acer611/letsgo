package com.weixin.service.impl;


import com.umessage.letsgo.domain.po.security.WxTemplateMessageEntity;
import com.umessage.letsgo.service.api.security.IWxTemplateMessageService;
import com.umessage.letsgo.service.common.constant.Constant;
import com.weixin.service.ITemplateMessage;
import com.weixin.util.TemplateMessageRequest;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by Lizhen on 2016/11/21 0030.
 */
@Service
public class TemplateMessageImpl implements ITemplateMessage {
    private static final Logger logger = Logger.getLogger(TemplateMessageImpl.class);
    // 模板消息字体颜色
    private static final String TEMPLATE_FRONT_COLOR = "#32CD32";
    private static final String TEMPLATE_BLACK_COLOR = "#000000";
    @Autowired
    protected WxMpService wxMpService;
    @Autowired
    protected IWxTemplateMessageService wxTemplateMessageService;


    /**
     * 账号未绑定通知
     * @param request
     */
    @Override
    public void sendBindNoticeTemplate(TemplateMessageRequest request) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setToUser(request.getOpenid());//openid
        templateMessage.setTemplateId(Constant.BINDNOTICE);
        templateMessage.setUrl(request.getUrl());
        templateMessage = this.getTemplateMessageOneRequest(templateMessage, request);
        try {
            wxMpService.getTemplateMsgService()
                    .sendTemplateMsg(templateMessage);
            this.addWxTemplateMessage(request,Constant.BINDNOTICE);
        } catch (WxErrorException e) {
            logger.error(e.getMessage().toString());
        }
    }



    /**
     * 发送变更通知
     * @param request
     */
    @Override
    public void sendChangeNoticeTemplate(TemplateMessageRequest request) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setToUser(request.getOpenid());//openid
        templateMessage.setTemplateId(Constant.CHANGENOTICE);
        templateMessage.setUrl(request.getUrl());
        templateMessage = this.getTemplateMessageRequest(templateMessage, request);
        try {
            wxMpService.getTemplateMsgService()
                    .sendTemplateMsg(templateMessage);
            this.addWxTemplateMessage(request,Constant.CHANGENOTICE);
        } catch (WxErrorException e) {
            logger.error(e.getMessage().toString());
        }
    }




    /**
     * 发送天气提醒
     * @param request
     */
    @Override
    public void sendWeatherRemindTemplate(TemplateMessageRequest request) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setToUser(request.getOpenid());//openid
        templateMessage.setTemplateId(Constant.WEATHERREMIND);
        templateMessage.setUrl(request.getUrl());
        templateMessage = this.getTemplateMessageMoreRequest(templateMessage, request);
        try {
            wxMpService.getTemplateMsgService()
                    .sendTemplateMsg(templateMessage);
            this.addWxTemplateMessage(request,Constant.WEATHERREMIND);
        } catch (WxErrorException e) {
            logger.error(e.getMessage().toString());
        }
    }



    /**
     * 发送通知
     * @param request
     */
    @Override
    public void sendNoticeTemplate(TemplateMessageRequest request) {
        WxMpTemplateMessage noticeTemplate = new WxMpTemplateMessage();
        noticeTemplate.setToUser(request.getOpenid());//openid
        noticeTemplate.setTemplateId(Constant.SENDNOTICE);
        noticeTemplate.setUrl(request.getUrl());
        noticeTemplate = this.getTemplateMessageMoreRequest(noticeTemplate, request);
        try {
            wxMpService.getTemplateMsgService()
                    .sendTemplateMsg(noticeTemplate);
            this.addWxTemplateMessage(request,Constant.SENDNOTICE);
        } catch (WxErrorException e) {
            logger.error(e.getMessage().toString());
        }
    }


    /**
     * 发送集合通知
     * @param request
     */
    @Override
    public void sendGatherNoticeTemplate(TemplateMessageRequest request) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setToUser(request.getOpenid());//openid
        templateMessage.setTemplateId(Constant.GATHERNOTICE);
        templateMessage.setUrl(request.getUrl());
        templateMessage = this.getTemplateMessageMoreRequest(templateMessage, request);
        try {
            wxMpService.getTemplateMsgService()
                    .sendTemplateMsg(templateMessage);
            this.addWxTemplateMessage(request,Constant.GATHERNOTICE);
        } catch (WxErrorException e) {
            logger.error(e.getMessage().toString());
        }
    }



    /**
     * 发送行程安排提醒
     * @param request
     */
    @Override
    public void sendScheduleRemindTemplate(TemplateMessageRequest request) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setToUser(request.getOpenid());//openid
        templateMessage.setTemplateId(Constant.SCHEDULEREMIND);
        templateMessage.setUrl(request.getUrl());
        templateMessage = this.getTemplateMessageMoreRequest(templateMessage, request);
        try {
            wxMpService.getTemplateMsgService()
                    .sendTemplateMsg(templateMessage);
            this.addWxTemplateMessage(request,Constant.SCHEDULEREMIND);
        } catch (WxErrorException e) {
            logger.error(e.getMessage().toString());
        }
    }



    /**
     * 发送入住通知
     * @param request
     */
    @Override
    public void sendCheckInNoticeTemplate(TemplateMessageRequest request) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setToUser(request.getOpenid());//openid
        templateMessage.setTemplateId(Constant.CHECKINNOTICE);
        templateMessage.setUrl(request.getUrl());
        templateMessage = this.getTemplateMessageMoreRequest(templateMessage, request);
        try {
            wxMpService.getTemplateMsgService()
                    .sendTemplateMsg(templateMessage);
            this.addWxTemplateMessage(request,Constant.CHECKINNOTICE);
        } catch (WxErrorException e) {
            logger.error(e.getMessage().toString());
        }
    }



    /**
     * 发送评价通知
     * @param request
     */
    @Override
    public void sendCommentNoticeTemplate(TemplateMessageRequest request) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setToUser(request.getOpenid());//openid
        templateMessage.setTemplateId(Constant.COMMENTNOTICE);
        templateMessage.setUrl(request.getUrl());
        templateMessage = this.getTemplateMessageRequest(templateMessage, request);
        try {
            wxMpService.getTemplateMsgService()
                    .sendTemplateMsg(templateMessage);
            this.addWxTemplateMessage(request,Constant.COMMENTNOTICE);
        } catch (WxErrorException e) {
            logger.error(e.getMessage().toString());
        }
    }



    /**
     * 根据参数获取模板消息
     * @param request
     * @return
     */
    public WxMpTemplateMessage getTemplateMessageOneRequest(WxMpTemplateMessage templateMessage, TemplateMessageRequest request){
        WxMpTemplateData keyword1Data = new WxMpTemplateData("keyword1", request.getData().get("keyword1").getValue(), TEMPLATE_BLACK_COLOR);
        WxMpTemplateData keyword2Data = new WxMpTemplateData("keyword2", request.getData().get("keyword2").getValue(), TEMPLATE_BLACK_COLOR);
        WxMpTemplateData remarkData = new WxMpTemplateData("remark", request.getData().get("remark").getValue(), TEMPLATE_BLACK_COLOR);
        templateMessage.addWxMpTemplateData(keyword1Data);
        templateMessage.addWxMpTemplateData(keyword2Data);
        templateMessage.addWxMpTemplateData(remarkData);
        return templateMessage;
    }


    /**
     * 根据参数获取模板消息
     * @param request
     * @return
     */
    public WxMpTemplateMessage getTemplateMessageRequest(WxMpTemplateMessage templateMessage, TemplateMessageRequest request){
        WxMpTemplateData firstData = new WxMpTemplateData("first", request.getData().get("first").getValue(), TEMPLATE_BLACK_COLOR);
        WxMpTemplateData keyword1Data = new WxMpTemplateData("keyword1", request.getData().get("keyword1").getValue(), TEMPLATE_BLACK_COLOR);
        WxMpTemplateData keyword2Data = new WxMpTemplateData("keyword2", request.getData().get("keyword2").getValue(), TEMPLATE_BLACK_COLOR);
        WxMpTemplateData remarkData = new WxMpTemplateData("remark", request.getData().get("remark").getValue(), TEMPLATE_BLACK_COLOR);
        templateMessage.addWxMpTemplateData(firstData);
        templateMessage.addWxMpTemplateData(keyword1Data);
        templateMessage.addWxMpTemplateData(keyword2Data);
        templateMessage.addWxMpTemplateData(remarkData);
        return templateMessage;
    }


    /**
     * 根据参数获取模板消息
     * @param request
     * @return
     */
    public WxMpTemplateMessage getTemplateMessageMoreRequest(WxMpTemplateMessage templateMessage, TemplateMessageRequest request){
        WxMpTemplateData firstData = new WxMpTemplateData("first", request.getData().get("first").getValue(), TEMPLATE_BLACK_COLOR);
        WxMpTemplateData keyword1Data = new WxMpTemplateData("keyword1", request.getData().get("keyword1").getValue(), TEMPLATE_BLACK_COLOR);
        WxMpTemplateData keyword2Data = new WxMpTemplateData("keyword2", request.getData().get("keyword2").getValue(), TEMPLATE_BLACK_COLOR);
        WxMpTemplateData keyword3Data = new WxMpTemplateData("keyword3", request.getData().get("keyword3").getValue(), TEMPLATE_BLACK_COLOR);
        WxMpTemplateData remarkData = new WxMpTemplateData("remark", request.getData().get("remark").getValue(), TEMPLATE_BLACK_COLOR);
        templateMessage.addWxMpTemplateData(firstData);
        templateMessage.addWxMpTemplateData(keyword1Data);
        templateMessage.addWxMpTemplateData(keyword2Data);
        templateMessage.addWxMpTemplateData(keyword3Data);
        templateMessage.addWxMpTemplateData(remarkData);
        return templateMessage;
    }


    /**
     * 添加模板消息记录
     * @param request
     */
    public void addWxTemplateMessage(TemplateMessageRequest request, String templateId){
        String jsonString = JSONObject.fromObject(request.getData()).toString();
        WxTemplateMessageEntity wxTemplateMessageEntity = new WxTemplateMessageEntity();
        wxTemplateMessageEntity.setOpenid(request.getOpenid());
        wxTemplateMessageEntity.setTemplateId(request.getTemplate_id());
        wxTemplateMessageEntity.setUrl(request.getUrl());
        wxTemplateMessageEntity.setTemplateData(jsonString);
        wxTemplateMessageEntity.setTemplateId(templateId);
        wxTemplateMessageService.insert(wxTemplateMessageEntity);
    }
}
