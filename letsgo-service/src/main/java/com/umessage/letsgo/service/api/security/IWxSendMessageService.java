package com.umessage.letsgo.service.api.security;


import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.notice.AnnouncementEntity;
import com.umessage.letsgo.domain.po.notice.NoticeEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.activity.request.RoomRequest;

import java.util.List;

public interface IWxSendMessageService {
    //发送用户未绑定微信模板消息
    void sendBindNoticeTemplateMessage(String openID,String wxNickName);
    //发送微信扫描入团模板消息
    void sendJoinTeamTemplateMessage(String openID,Long teamId, String txGroupid);
    //发送微信扫描无法加入模板消息
    void sendNotJoinTeamTemplateMessage(String openID,Long teamId);
    //发送行程详情变更提醒
    void sendChangeNoticeTemplateMessage(MemberEntity memberEntity, ScheduleDetailEntity scheduleDetailEntity, MemberEntity memberLeaderEntity, String changeContent);
    //发送每日点评模板消息
    void sendCommentNoticeTemplateMessage(MemberEntity memberEntity);
    //发送次日天气模板消息
    void sendWeatherRemindTemplateMessage(MemberEntity memberEntity, ScheduleDetailEntity scheduleDetailEntity);
    //发送次日行程模板消息
    void sendScheduleRemindTemplateMessage(MemberEntity memberEntity, ScheduleDetailEntity scheduleDetailEntity);
    //发送 满意度调查 通知消息
    void sendCommentNoticeTemplateMessage(MemberEntity memberEntity, TeamEntity teamEntity, ScheduleEntity scheduleEntity);
    //发送集合模板消息
    void sendGatherTemplateMessage(List<MemberEntity> memberList, NoticeEntity notice, MemberEntity sendMemberEntity, String teamName);
    //发送通知模板消息
    void sendNoticeTemplateMessage(List<MemberEntity> memberList, NoticeEntity notice, MemberEntity sendMemberEntity, String teamName);
    //发送集合提醒
    void sendGatherRemindTemplateMessage(MemberEntity memberEntity, NoticeEntity notice);
    //发送入住通知
    void sendCheckInNoticeTemplateMessage(List<RoomRequest> roomRequests);
    //发送公告模板消息
    void sendAnnouncementTemplateMessage(List<MemberEntity> memberEntityList, AnnouncementEntity announcement, MemberEntity member);
}
