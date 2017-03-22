package com.weixin.handler;

import com.umessage.letsgo.service.api.security.IWxInfoService;
import com.weixin.service.ICoreService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by gaofei on 2016/11/23.
 */
@Component
public class UnSubscribeHandler  extends AbstractHandler {
    @Autowired
    protected WxMpConfigStorage configStorage;
    @Autowired
    protected WxMpService wxMpService;
    @Autowired
    protected ICoreService coreService;
    @Resource
    protected IWxInfoService wxService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        logger.info("进入取消关注事件" );
        WxMpUser wxMpUser = coreService.getUserInfo(wxMessage.getFromUser(), "zh_CN");
        String unionId = wxMpUser.getUnionId();
        String openID = wxMpUser.getOpenId();


        //取消关注（根据openID 把这条记录修改这条记录的状态）
        logger.info("执行取消关注的操作" );
        wxService.unSubscribe(openID);
        WxMpXmlOutTextMessage m = new WxMpXmlOutTextMessage();
        logger.info("subscribeMessageHandler" + m.getContent());
        return m;
    }
}
