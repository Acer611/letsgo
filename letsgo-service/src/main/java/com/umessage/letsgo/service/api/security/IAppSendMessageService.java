package com.umessage.letsgo.service.api.security;


import com.umessage.letsgo.domain.vo.common.respone.AppMessageResponse;

public interface IAppSendMessageService {
    void sendMessageToJoinInMember(AppMessageResponse response, String toAccount);

}
