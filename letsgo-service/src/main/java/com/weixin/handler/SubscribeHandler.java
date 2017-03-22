package com.weixin.handler;

import com.umessage.letsgo.domain.po.security.ThirdPartyAccountEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.security.WxInfoEntity;
import com.umessage.letsgo.domain.po.security.WxTeamEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.security.*;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.weixin.service.ICoreService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 用户关注微信公众号的Handler
 * created by gaofei on 2016/11/22
 *
 */
@Component
public class SubscribeHandler extends AbstractHandler {

    private static Logger logger = Logger.getLogger(SubscribeHandler.class);
    @Autowired
    protected WxMpConfigStorage configStorage;
    @Autowired
    protected WxMpService wxMpService;
    @Autowired
    protected ICoreService coreService;
    @Resource
    protected IWxInfoService wxInfoService;
    @Resource
    protected IThirdPartyAccountService thirdPartyAccountService;
    @Resource
    protected IScheduleService scheduleService;
    @Resource
    protected IUserService userService;
    @Resource
    protected IWxTeamService wxTeamService;
    @Resource
    protected ITeamService teamService;
    @Resource
   protected  IWxSendMessageService wxSendMessageService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        logger.info("进入关注事件的SubscribeHandler()");
        WxMpUser wxMpUser = coreService.getUserInfo(wxMessage.getFromUser(), "zh_CN");
        String unionId = wxMpUser.getUnionId();
        //微信应用的appid
        String appID = wxMpService.getWxMpConfigStorage().getAppId();
        //检查用户是否是关注用户
        logger.info("检查当前用户是否是关注的用户...");
        boolean flag = null!=wxInfoService.selectWeixinInfoByOpenIDAndUnionID(unionId,wxMessage.getFromUser()) ? true : false;
        logger.info("当前用户是否是关注用户： " + flag);
       // boolean flag = wxInfoService.checkUserByOpenID(wxMessage.getFromUser());
        WxMpXmlOutTextMessage m = new WxMpXmlOutTextMessage();
        if(!"" .equals(wxMessage.getEventKey()) || null != wxMessage.getTicket()){
            String teamID = wxMessage.getEventKey();
            if(teamID.contains("_")){
                teamID = teamID.substring(teamID.indexOf("_")+1,teamID.length());
            }
           //扫团二维码事件
            logger.info("进入扫团二维码关注事件" );
            m = this.handleTicketSubsEvent(unionId,wxMpUser,wxMessage,flag,appID,teamID, m);

       }else{
           //普通关注公众号事件
            logger.info("进入普通关注事件" );
          m = this.handleSubscribeEvent(unionId,wxMpUser,flag,appID,m , wxMessage);
       }

        logger.info("subscribeMessageHandler" + m.getContent());
        return m;
    }


    /**
     * 扫描团二维码事件
     * @param unionId
     * @param wxMpUser
     * @param flag
     */
    public WxMpXmlOutTextMessage handleTicketSubsEvent(String unionId, WxMpUser wxMpUser,WxMpXmlMessage wxMessage,boolean flag,String appID,String teamID,WxMpXmlOutTextMessage m) {
        String openID = wxMpUser.getOpenId();
        //判断是否是关注用户，若是未关注用户执行关注动作
        if(!flag){
            logger.info("执行关注公众号向s_wx_info表存数据的动作" );
            wxInfoService.subscribe(unionId,wxMpUser,flag,appID);
        }
        //检查当前团是否已经出行，若出行推送团已出行，无法加入消息
        TeamEntity teamEntity = teamService.selectById(Long.parseLong(teamID));
        String txGroupid = teamEntity.getTxGroupid();

        //出行状态,1：出行中；2：即将出行；3：已出行"
        if(teamEntity.getStatus()==3){

            logger.info("推送团已出行的模板消息" );
            //推送团已经出行的提示(传入openID,teamID)
            wxSendMessageService.sendNotJoinTeamTemplateMessage(openID,Long.parseLong(teamID));
        }else{
            //检查当前微信用户是否是已经绑定的用户
           boolean isBinding = thirdPartyAccountService.checkIsBindingUser(unionId);
            //如果是绑定用户 绑定用户到当前团下
            if(isBinding){
                ThirdPartyAccountEntity thirdParty = thirdPartyAccountService.selectByUnionID(unionId);
                UserEntity userEntity = userService.getUserById(thirdParty.getUserId());
                logger.info("绑定用户绑定团信息" );
                // 绑定团信息
                thirdPartyAccountService.bindingTeamInfo(userEntity,unionId,Long.parseLong(teamID),txGroupid);

                logger.info("绑定成功后推送团行程基本信息" );
                // 推送团队基本信息到微信(传入openID,teamID)
                wxSendMessageService.sendJoinTeamTemplateMessage(openID,Long.parseLong(teamID),txGroupid);

            }else{
                //检查用户是否在当扫描的团中
                boolean isInTeam = false;
                List<WxTeamEntity> wxTeamList =  wxTeamService.selectWxTeamInfoListByUnionid(unionId);
                long tid = Long.parseLong(teamID);
                for (WxTeamEntity wxTeamEntity:wxTeamList) {
                    if(wxTeamEntity.getTeamId()== tid){
                        isInTeam = true;
                        break;
                    }
                }
                //如果在当前团中 推送团信息 否则存入信息到微信团队表
                if(isInTeam){
                    // 推送团队基本信息到微信(传入openID,teamID)
                    wxSendMessageService.sendJoinTeamTemplateMessage(openID,Long.parseLong(teamID),txGroupid);
                }else{
                    WxTeamEntity wxTeamEntity = new WxTeamEntity();
                    wxTeamEntity.setTeamId(Long.parseLong(teamID));
                    wxTeamEntity.setUnionid(unionId);
                    logger.info("向团微信信息表插入数据" );
                    wxTeamService.create(wxTeamEntity);
                    logger.info("推送团行程基本信息的模板消息" );
                    //推送团队基本信息到微信(传入openID,teamID)
                    wxSendMessageService.sendJoinTeamTemplateMessage(openID,Long.parseLong(teamID),txGroupid);
                }

                logger.info("推送绑定可以获取更所功能的模板消息" );
                //推送绑定可以使用更多功能的消息(传入openID,微信昵称)
                wxSendMessageService.sendBindNoticeTemplateMessage(openID,wxMpUser.getNickname());
            }
        }
        return m;
    }

    /**
     * 普通关注公众号事件
     * @param unionId
     * @param wxMpUser
     * @param flag
     */
    private WxMpXmlOutTextMessage handleSubscribeEvent(String unionId, WxMpUser wxMpUser,boolean flag,String appID, WxMpXmlOutTextMessage m,WxMpXmlMessage wxMessage) {
        //wxMpService
        //执行关注动作
        logger.info("执行关注公众号向s_wx_info表存数据的动作" );
        wxInfoService.subscribe(unionId,wxMpUser,flag,appID);

        //检查当前微信账号是否是绑定用户
        boolean isBinding = thirdPartyAccountService.checkIsBindingUser(unionId);
        WxMpKefuMessage wxkefuMessage = new WxMpKefuMessage();
        if(!isBinding){
            logger.info("推送绑定可以获取更所功能的模板消息" );
            //推送绑定能获取更多功能的模板消息
            wxSendMessageService.sendBindNoticeTemplateMessage(wxMpUser.getOpenId(),wxMpUser.getNickname());
         }else{
            //组装客服消息
            wxkefuMessage =  WxMpKefuMessage.TEXT()
                    .content("尊敬的" + wxMpUser.getNickname() + "，您好！ 欢迎回到跟上小咖！！")
                    .toUser(wxMessage.getFromUser())
                    .build();
            /*m = WxMpXmlOutMessage.TEXT()
                    .content("尊敬的" + wxMpUser.getNickname() + "，您好！ 欢迎回到跟上小咖！！")
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser())
                    .build();*/
        }
        logger.info("执行普通关注公众号事件完成");
        return m;
    }


};
