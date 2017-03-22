package com.umessage.letsgo.manager.controller.system;



import com.umessage.letsgo.service.api.system.IMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Yxy on 2016/11/2.
 */
@Controller
public class SendMailController {

    @Resource
    private IMessageService messageService;

    @RequestMapping(value = "/sendLeaveAMessageMail",method = RequestMethod.GET)
    @ResponseBody
    public void sendLeaveAMessageMail(String callback, String name, String customerEmail, String leaveMessage,
                                      HttpServletResponse httpServletResponse){
        String title = null;
        String content = null;
        String result;
        PrintWriter writer;
        httpServletResponse.setCharacterEncoding("UTF-8");
        if (!StringUtils.isBlank(callback)){
            if (StringUtils.isEmpty(name)||StringUtils.isEmpty(customerEmail)||StringUtils.isEmpty(leaveMessage)){
                result =  "{\"value\":{\"retCode\":1004,\"retMsg\":\"邮件发送失败,请填写完整参数！\"}}";
                result=callback+"("+result+")";
                try {
                    writer = httpServletResponse.getWriter();
                    writer.write(result);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            //拼接邮件内容
            title = "来自首页的客户留言";
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("姓名："+name);
            stringBuffer.append("<br/>");
            stringBuffer.append("客户邮箱："+customerEmail);
            stringBuffer.append("<br/>");
            stringBuffer.append("留言内容:");
            stringBuffer.append("<br/>");
            stringBuffer.append(leaveMessage);
            content = stringBuffer.toString();
        }
        //发送邮件
        messageService.sendLeaveaMailMessage(title,content);
        try {
            writer = httpServletResponse.getWriter();
            result =  "{\"value\":{\"retCode\":0,\"retMsg\":\"邮件发送成功！\"}}";
            result=callback+"("+result+")";
            writer.write(result);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ;
    }
}
