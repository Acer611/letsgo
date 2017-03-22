package com.umessage.letsgo.travel.controller.message;

import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.message.request.MessageTextRequest;
import com.umessage.letsgo.service.api.message.IQMessageService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaofei on 2017/1/15.
 */

@RequestMapping(value = "/message")
@Controller
public class MessageController {

    private Logger logger = Logger.getLogger(MessageController.class);
    @Resource
    private IQMessageService qMessageService;
    @Resource
    private ITravelAgencyService travelAgencyService;
    /**
     * 获取行程的反馈问题列表
     * ("pageStart")    第几页
     * ("pageSize") 每页显示多少跳
     * ("status") 状态   0 为未读 1 为已读为回复 2 为 已读回复  -1 全部
     * @return
     *  resultMap.put("messageList",messageList); 反馈问题列表信息
     * resultMap.put("total",total);  总条数
     * resultMap.put("start",pageStart); 当前页
     * resultMap.put("pageNumber",messageList.getPages()); 总页数
     */
    @RequestMapping("/getSubjectMessageList")
    public String getMessageList(HttpServletRequest request, Model model) {
        TravelAgencyEntity travelEntity = travelAgencyService.getCurrentTravel();
        if(null==travelEntity){
            return "redirect:/login";
        }
        int pageStart = Integer.parseInt(String.valueOf(request.getParameter("pageStart")) );
        int pageSize = Integer.parseInt(String.valueOf(request.getParameter("pageSize")) );
        int status = Integer.parseInt(String.valueOf(request.getParameter("status")) );
       /* String teamNum = String.valueOf(request.getParameter("teamNum"));*/

        String travelId = String.valueOf(travelEntity.getId());
        Map resultMap = qMessageService.getSubjectMessageListByTravelId(pageStart,pageSize,status,travelId/*,teamNum*/);
        model.addAttribute("result",resultMap);
        return "message/messageList";
    }

    /**
     * 获取未读信息的条数
     * @param request
     * @param model
     * @return  未读的条数
     */
    @RequestMapping("/getNotRedSubjectMessage")
    @ResponseBody
    public long getNotRedSubjectMessage(HttpServletRequest request,  Model model){
        TravelAgencyEntity travelEntity = travelAgencyService.getCurrentTravel();
        if(null==travelEntity){
            return 0L;
        }
        long noReadNum = qMessageService.getNoReadSubjectNum(String.valueOf(travelEntity.getId()));
        model.addAttribute("result",noReadNum);
        return noReadNum;
    }

    /**
     * 获取回复列表信息
     * @param request
     * @param memberId 成员的ID
     * @param mid 反馈问题的ID
     * @param model
     * @return
     */
    @RequestMapping("/getReplyMessageList")
    public String getReplyMessageList(HttpServletRequest request, String memberId,String mid, Model model) {
        Map resultMap = new HashMap<>();
        if(null==memberId || null== mid){
            resultMap.put("resultCode", 0);
            resultMap.put("message","memberId或mid 不能为空");

        }
        resultMap = qMessageService.getReplyMessageList(memberId,mid);

        model.addAttribute("resultMap",resultMap);
        return "message/message";
    }

    /**
     *  旅行社端回复反馈问题
     * @param request
     * @return
     */
    @RequestMapping(value ="/ReplyMessage",method = RequestMethod.POST)
    @ResponseBody
    public Map replyMessageText(@RequestBody MessageTextRequest request, Model model){
        String mId = String.valueOf(request.getMid());
        String travleId = String.valueOf(request.getTravelId());
        String message =  String.valueOf(request.getMessage());
        /*String mId ="1";
        String travleId = "1";
        String message =  "跟上旅行社 的回复 ：显示出纯纯粹粹纯纯粹粹";*/
        Map resultMap = new HashMap<>();
        if(StringUtils.isEmpty(message)|| StringUtils.isEmpty(mId) || mId.equals("null")||"null".equals(message)){
            resultMap.put("resultCode", -1);
            resultMap.put("message","message或mid 不能为空");
            return resultMap;
        }
        resultMap = qMessageService.postMessageTravel(mId,message,travleId);

        return resultMap;
    }




}
