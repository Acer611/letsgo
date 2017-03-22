package com.umessage.letsgo.h5.weixin;

import com.weixin.service.ICoreService;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by gaofei on 2016/11/23
 */
@Controller
@RequestMapping(value = "/weixin")
public class CoreController extends GenericController {

    @Resource
    protected WxMpConfigStorage configStorage;
    @Resource
    protected WxMpService wxMpService;
    @Resource
    protected ICoreService coreService;

    /**
     * 微信公众号webservice主服务接口，提供与微信服务器的信息交互
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/core")
    public void wechatCore(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("进入微信回调的入口方法core.....");
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String signature = request.getParameter("signature");
        String nonce = request.getParameter("nonce");
        String timestamp = request.getParameter("timestamp");


        if (!this.wxMpService.checkSignature(timestamp, nonce, signature)) {
            // 消息签名不正确，说明不是公众平台发过来的消息
            response.getWriter().println("非法请求");
            return;
        }

        String echoStr = request.getParameter("echostr");
        if (StringUtils.isNotBlank(echoStr)) {
            // 说明是一个仅仅用来验证的请求，回显echostr
            String echoStrOut = String.copyValueOf(echoStr.toCharArray());
            response.getWriter().println(echoStrOut);
            return;
        }

        String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type"))
            ? "raw"
            : request.getParameter("encrypt_type");

        if ("raw".equals(encryptType)) {
            logger.info("进入明文模式.....");
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
            WxMpXmlOutMessage outMessage = this.coreService.route(inMessage);
            //response.getWriter().write(outMessage.toXml());
            return;
        }

        if ("aes".equals(encryptType)) {
            logger.info("进入加密模式.....");
            // 是aes加密的消息
            String msgSignature = request.getParameter("msg_signature");
            logger.info("加密参数提timestamp: "+ timestamp + ",   nonce: " + nonce + ",   msgSinature: "+ msgSignature );

            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(
                    request.getInputStream(), this.configStorage, timestamp, nonce,
                    msgSignature);
            this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = this.coreService.route(inMessage);
            this.logger.info(response.toString());
           // response.getWriter()
            //        .write(outMessage.toEncryptedXml(this.configStorage));
            return;
        }

        response.getWriter().println("不可识别的加密类型");
        return;
    }


}
