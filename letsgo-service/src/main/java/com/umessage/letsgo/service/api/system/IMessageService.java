package com.umessage.letsgo.service.api.system;

import com.umessage.letsgo.domain.po.system.MessageEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.system.request.MessageRequest;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by Albert on 2015/7/24.
 */
public interface IMessageService {
    int insert(MessageEntity message);

    int batchInsert(List<MessageEntity> payments);

    int update(String phone);

    int delete(Map<String, Object> conditions);

    List<MessageEntity> findList(MessageRequest request);

    int findCount(Map<String, Object> conditions);
    
    CommonResponse sendCheckCodeMessage(String phone, Integer scope) throws UnsupportedEncodingException;
    //给团队指定成员发送短信
    public void sendMessage(TeamEntity teamEntity, List<MemberEntity> memberList) throws UnsupportedEncodingException;
    /**
     *旅行社注册时发送短信验证码
     */
    public CommonResponse sendCodeMessageForTravel(String phone) throws UnsupportedEncodingException;

    public CommonResponse sendInvestmentMessage(String phone, Integer type, MessageRequest request) throws UnsupportedEncodingException;
        
    // 验证手机验证码
	CommonResponse validateMobileCode(String phone, String code, Long userId);
    // 发送邮件
    public CommonResponse sendMailMessage(String title, String content);

    public String sendLeaveaMailMessage(String title, String content);
}
