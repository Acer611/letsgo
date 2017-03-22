package com.umessage.letsgo.service.api.message;

import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.message.request.QMessageRequest;
import com.umessage.letsgo.domain.vo.message.response.MessageListResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by gaofei on 2017/1/12.
 */
public interface IQMessageService {
    /**
     * 根据成员ID 获取信息回复列表
     * @param tgroupId
     * @return
     */
    MessageListResponse getMessageListByTGroupId(String tgroupId,Long userId);

    /**
     * 创建回复信息
     * @param messageRequest
     * @return
     */
    CommonResponse postMessage(QMessageRequest messageRequest ,Long userId);

    /**
     * 获取行程反馈问题列表
     * @param travelId
     * @return
     */
    Map getSubjectMessageListByTravelId(int pageStart,int pageSize,int status, String travelId/*,String teamNum*/);

    /**
     * 获取未读的反馈问题条数
     * @param travelId
     * @return
     */
    long getNoReadSubjectNum(String travelId);

    /**
     * 旅行社端 获取问题反馈回复列表
     * @param memberId 成员的id
     * @param mid 反馈问题主题的id
     * @return
     */
    Map getReplyMessageList(String memberId, String mid);

    /**
     * 旅行社端回复问题反馈信息
     * @param mId
     * @param message
     * @param travleId
     * @return
     */
    Map postMessageTravel(String mId, String message, String travleId);
}
