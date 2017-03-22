package com.weixin.handler;

import com.umessage.letsgo.domain.po.security.ThirdPartyAccountEntity;
import com.umessage.letsgo.service.api.security.IThirdPartyAccountService;
import com.umessage.letsgo.service.common.constant.WxConstant;
import com.weixin.service.ICoreService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
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
 * Created by gaofei on 2016/12/6.
 */

@Component
public class ViewHandler  extends AbstractHandler{

    @Autowired
    private ICoreService coreService;

    @Resource
    private IThirdPartyAccountService thirdPartyAccountService;


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        WxMpUser wxMpUser = coreService.getUserInfo(wxMessage.getFromUser(), "zh_CN");
        String unionId = wxMpUser.getUnionId();
        String eventKey = wxMessage.getEventKey();
        WxMpXmlOutTextMessage m = new WxMpXmlOutTextMessage();
        if(eventKey.contains(WxConstant.BINDING_LINK)){
           ThirdPartyAccountEntity thirdPartyAccountEntity = thirdPartyAccountService.selectThirdPartyAccountByUnionid(unionId);
            if(null!=thirdPartyAccountEntity){
                m = WxMpXmlOutMessage.TEXT()
                        .content("尊敬的" + wxMpUser.getNickname() + "，您好！ 您已经是绑定用户了！！")
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .build();
            }

        }
        return m;
    }
}
