package com.umessage.letsgo.service.impl.security;

import com.qq.tim.ITimRestAPI;
import com.qq.tim.vo.request.MsgBody;
import com.qq.tim.vo.request.MsgContent;
import com.qq.tim.vo.request.MsgType;
import com.qq.tim.vo.request.SendMsgRequest;
import com.umessage.letsgo.core.utils.JsonUtils;
import com.umessage.letsgo.domain.vo.common.respone.AppMessageResponse;
import com.umessage.letsgo.service.api.security.IAppSendMessageService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppSendMessageServiceImpl implements IAppSendMessageService {
    Logger logger = Logger.getLogger(AppSendMessageServiceImpl.class);

    @Resource
    private ITimRestAPI timRestAPI;


    /**
     * 给团队内已加入的成员发送系统信息（每日点评）
     * @param response
     * @param toAccount
     */
    @Override
    public void sendMessageToJoinInMember(AppMessageResponse response, String toAccount) {
        if (!StringUtils.isEmpty(toAccount)){
            List<MsgBody> bodyList = new ArrayList<>();
            MsgContent content = new MsgContent();
            content.setData(JsonUtils.obj2json(response));
            content.setDesc(response.getDesc());

            logger.info("团员名字为："+toAccount);

            MsgBody body = new MsgBody();
            body.setMsgType(MsgType.getTIMCustomElem());
            body.setMsgContent(content);

            bodyList.add(body);
            String fromAccount = "letsgo@gsxe";	// 指定发送账号，跟上小二
            // 使用IM 发送明日行程消息
            int random = (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            SendMsgRequest request = new SendMsgRequest(fromAccount, toAccount, random, timestamp, bodyList);
            timRestAPI.sendmsg(request);
            logger.info("random"+random+",timestamp"+timestamp);
            logger.info("消息发送方账号"+fromAccount+",消息接收方账号"+toAccount);
            for (int i =0 ;i<bodyList.size();i++){
                logger.info("消息内容为："+bodyList.get(i));
            }
        }
    }

}
