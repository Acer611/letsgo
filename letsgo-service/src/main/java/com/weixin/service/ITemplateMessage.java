package com.weixin.service;


import com.weixin.util.TemplateMessageRequest;

/**
 * Created by Lizhen on 2016/11/21 0030.
 */
public interface ITemplateMessage {

    public void sendBindNoticeTemplate(TemplateMessageRequest request);

    public void sendChangeNoticeTemplate(TemplateMessageRequest request);

    public void sendWeatherRemindTemplate(TemplateMessageRequest request);

    public void sendNoticeTemplate(TemplateMessageRequest request);

    public void sendGatherNoticeTemplate(TemplateMessageRequest request);

    public void sendScheduleRemindTemplate(TemplateMessageRequest request);

    public void sendCheckInNoticeTemplate(TemplateMessageRequest request);

    public void sendCommentNoticeTemplate(TemplateMessageRequest request);

}
