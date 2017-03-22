package com.weixin.handler;

import com.umessage.letsgo.domain.po.security.WxInfoEntity;
import com.umessage.letsgo.service.api.security.IWxInfoService;
import com.weixin.service.ICoreService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by gaofei on 2016/12/6.
 */

@Component
public class ScanHandler extends AbstractHandler{

    private static Logger logger = Logger.getLogger(SubscribeHandler.class);
    @Autowired
    protected ICoreService coreService;
    @Resource
    protected IWxInfoService wxInfoService;
    @Resource
    protected SubscribeHandler subscribeHandler;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        logger.info("进入到扫团二维码事件ScanHandler()");
        WxMpUser wxMpUser = coreService.getUserInfo(wxMessage.getFromUser(), "zh_CN");
        String unionId = wxMpUser.getUnionId();
        //微信应用的appid
        String appID = wxMpService.getWxMpConfigStorage().getAppId();
        //检查用户是否是关注用户
        WxInfoEntity wxInfoEntity= wxInfoService.selectWeixinInfoByOpenIDAndUnionID(unionId,wxMessage.getFromUser());
        boolean flag = null != wxInfoEntity ? true : false;
        WxMpXmlOutTextMessage m = new WxMpXmlOutTextMessage();
        if(!"" .equals(wxMessage.getEventKey()) || null != wxMessage.getTicket()){
            String teamID = wxMessage.getEventKey();
            if(teamID.contains("_")){
                teamID = teamID.substring(teamID.indexOf("_")+1,teamID.length());
            }
            logger.info("获取到团ID为：" + teamID);
            //扫团二维码事件
            m = subscribeHandler.handleTicketSubsEvent(unionId,wxMpUser,wxMessage,flag,appID,teamID, m);

        }
        logger.info("扫团二维码事件完成)");
        return m;
    }
}
