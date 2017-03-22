package com.umessage.letsgo.dao.message;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.message.MessageEntity;
import com.umessage.letsgo.domain.po.message.MessageTextEntity;

import java.util.List;

/**
 * Created by gaofei on 2017/1/12.
 */
public interface IQMessageDao {


    /**
     * 根据成员的ID 获取回复信息列表
     * @param requestMessageEntity
     * @return
     */
   MessageEntity getMessageListByTgroupId(MessageEntity requestMessageEntity);

    /**
     * 获取主题问题反馈信息
     * @param messageEntity
     * @return
     */
    MessageEntity getSubjectMessageByTGroupId(MessageEntity messageEntity);

    /**
     * 向message表中插入数据
     * @param messageEntity
     * @return
     */
    long insertSubjectMessage(MessageEntity messageEntity);

    /**
     * 向反馈回复表中插入数据
     * @param messageTextEntity
     */
    void insertMessageText(MessageTextEntity messageTextEntity);

    /**
     * 修改状态信息和更新时间
     * @param messageEntity
     */
    void updateSubjectMessage(MessageEntity messageEntity);



   /**
    * 获取行程问题反馈列表
    * @param requestMessage
    * @return
       */
    Page<MessageEntity> getSubjectMessageByRequest(MessageEntity requestMessage);


    /**
     * 根据请求获取messageText的列表信息
     * @param requestMessageEntity
     * @return
     */
    MessageEntity getMessageTextListByRequest(MessageEntity requestMessageEntity);

    /**
     * 根据ID 获取问题反馈主题的信息
     * @param mId
     * @return
     */
    MessageEntity getSubjectMessageById(String mId);

    /**
     * 修改message的状态
     * @param messageEntity
     */
  void updateSubjectMessageStatus(MessageEntity messageEntity);
}
