package com.umessage.letsgo.service.impl.message;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.dao.message.IQMessageDao;
import com.umessage.letsgo.dao.team.MemberDao;
import com.umessage.letsgo.dao.team.TeamDao;
import com.umessage.letsgo.domain.po.message.MessageEntity;
import com.umessage.letsgo.domain.po.message.MessageTextEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.message.request.QMessageRequest;
import com.umessage.letsgo.domain.vo.message.response.MessageListResponse;
import com.umessage.letsgo.domain.vo.team.requset.MemberRequest;
import com.umessage.letsgo.service.api.message.IQMessageService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by gaofei on 2017/1/12.
 */
@Service
public class QMessageServiceImpl implements IQMessageService {

    private Logger logger = Logger.getLogger(QMessageServiceImpl.class);

    @Autowired
    private IQMessageDao qMessageDao;
    @Resource
    private MemberDao memberDao;
    @Resource
    private IUserService userService;
    @Resource
    private TeamDao teamDao;
    @Resource
    private IMemberService memberService;
    @Resource
    private ITravelAgencyService travelAgencyService;


    /**
     * 根据腾讯组ID和用户ID获取回复列表信息
     *
     * @param tGroupId
     * @return
     */
    @Override
    public MessageListResponse getMessageListByTGroupId(String tGroupId, Long userId) {
        logger.info("根据腾讯组ID和用户ID获取回复列表信息" + "腾讯组ID为" + tGroupId + "当前用户的Id为" + userId);
        MessageListResponse messageListResponse = new MessageListResponse();
        MessageEntity requestMessageEntity = new MessageEntity();
        requestMessageEntity.settGroupId(tGroupId);
        requestMessageEntity.setUserId(userId);
        logger.info("获取问题反馈列表信息");
        //获取问题反馈列表信息
        MessageEntity messageEntity = new MessageEntity();
        messageEntity = qMessageDao.getMessageListByTgroupId(requestMessageEntity);

        if (null == messageEntity) {
            logger.info("用户未第一次反馈信息，获取相应的成员信息");
            //如果没有获取到列表根据腾讯组ID成员信息
            MemberRequest request = new MemberRequest();
            request.setUserId(userId);
            request.setTeamId(tGroupId);
            MemberEntity memberEntity = memberDao.selectMemberWithConditons(request);
            if (null != memberEntity) {
                messageEntity = new MessageEntity();
                messageEntity.setTeamId(memberEntity.gettId());
                messageEntity.setMemberId(memberEntity.getId());
                messageEntity.settGroupId(tGroupId);
                messageEntity.setHeadUrl(memberEntity.getPhotoUrl());

                logger.info("用户未第一次反馈信息，获取相应的团队信息");
                //根据团ID 获取团信息
                TeamEntity teamEntity = teamDao.select(memberEntity.gettId());
                if (null != teamEntity) {
                    messageEntity.setTeamName(teamEntity.getName());
                    messageEntity.setTeamNum(teamEntity.getTeamNum());
                    messageEntity.setTeavelId(teamEntity.getTravelId());
                    messageListResponse.setTravelId(String.valueOf(teamEntity.getTravelId()));
                }
                messageListResponse.setHasList(0);
            }
        } else {


            messageListResponse.setHeadUrl(messageEntity.getHeadUrl());
            messageListResponse.setTravelHeadUrl(messageEntity.getTravelHeadUrl());
            messageListResponse.setmId(String.valueOf(messageEntity.getID()));
            List<MessageTextEntity> messageTextEntityList = messageEntity.getMessageTextEntityList();
            if (!messageTextEntityList.isEmpty()) {
                messageListResponse.setHasList(1);
            }
            for (MessageTextEntity messageTextEntity : messageTextEntityList) {
                List<String> photoList = new ArrayList<>();
                if (null != messageTextEntity.getPhotoUrl1()) {
                    photoList.add(messageTextEntity.getPhotoUrl1());
                }
                if (null != messageTextEntity.getPhotoUrl2()) {
                    photoList.add(messageTextEntity.getPhotoUrl2());
                }
                if (null != messageTextEntity.getPhotoUrl3()) {
                    photoList.add(messageTextEntity.getPhotoUrl3());
                }
                if (null != messageTextEntity.getPhotoUrl4()) {
                    photoList.add(messageTextEntity.getPhotoUrl4());
                }
                if (null != messageTextEntity.getPhotoUrl5()) {
                    photoList.add(messageTextEntity.getPhotoUrl5());
                }
                if (null != messageTextEntity.getPhotoUrl6()) {
                    photoList.add(messageTextEntity.getPhotoUrl6());
                }
                if (null != messageTextEntity.getPhotoUrl7()) {
                    photoList.add(messageTextEntity.getPhotoUrl7());
                }
                if (null != messageTextEntity.getPhotoUrl8()) {
                    photoList.add(messageTextEntity.getPhotoUrl8());
                }
                if (null != messageTextEntity.getPhotoUrl9()) {
                    photoList.add(messageTextEntity.getPhotoUrl9());
                }

                MemberRequest request = new MemberRequest();
                request.setUserId(userId);
                request.setTeamId(tGroupId);
                MemberEntity memberEntity = memberDao.selectMemberWithConditons(request);
                TravelAgencyEntity travelAgencyEntity = travelAgencyService.getByTravelerId(messageEntity.getTeavelId());
                messageTextEntity.setPhotoList(photoList);
                if (messageTextEntity.getStatus() == 0) {
                    messageTextEntity.setHeadUrl(messageEntity.getHeadUrl());
                    messageTextEntity.setSenderName(memberEntity.getRealName());
                } else {
                    messageTextEntity.setHeadUrl(messageEntity.getTravelHeadUrl());
                    messageTextEntity.setSenderName(travelAgencyEntity.getName());
                }
            }
            messageListResponse.setMessageTextEntityList(messageEntity.getMessageTextEntityList());
        }

        messageListResponse.setRetCode(ErrorConstant.SUCCESS);
        return messageListResponse;
    }

    /**
     * 创建回复信息
     *
     * @param messageRequest
     * @return
     */
    @Override
    public CommonResponse postMessage(QMessageRequest messageRequest, Long userId) {
        // 1.检查用户是否投诉过（memssage表查看是否存在根据腾讯id）
        //若不存在，根据插入向message表插入一条数据 然后向messageText表插入一条数据
        //若存在向messagetext表插入一条数据
        //更新message表中的updatetime 和status status 改为未读的状态
        CommonResponse commonResponse = new CommonResponse();
        String tGroupId = messageRequest.gettGroupId();
        //检查用户是否投诉过（memssage表查看是否存在根据腾讯id）
        //若不存在，根据插入向message表插入一条数据
        logger.info("检查用户是否反馈过问题");
        MessageEntity requestEntity = new MessageEntity();
        requestEntity.settGroupId(tGroupId);
        requestEntity.setUserId(userId);
        MessageEntity messageEntity = qMessageDao.getSubjectMessageByTGroupId(requestEntity);

        MemberEntity memberEntity = new MemberEntity();
        boolean flag = true;
        Long travelId = null;
        if (null == messageEntity) {
            logger.info("用户未第一次反馈信息");
            flag = false;
            messageEntity = new MessageEntity();
            //根据腾讯组ID成员信息
            MemberRequest request = new MemberRequest();
            request.setUserId(userId);
            request.setTeamId(tGroupId);
            memberEntity = memberDao.selectMemberWithConditons(request);


            if (null != memberEntity) {
                long memberId = memberEntity.getId();
                long tid = memberEntity.gettId();
                String photoUrl = memberEntity.getPhotoUrl();

                messageEntity.setMemberId(memberId);
                messageEntity.settGroupId(tGroupId);
                messageEntity.setTeamId(tid);
                messageEntity.setHeadUrl(photoUrl);
                //根据团ID 获取团信息
                TeamEntity teamEntity = teamDao.select(tid);

                if (null != teamEntity) {
                    String teamName = teamEntity.getName();
                    String teamNum =  teamEntity.getTeamNum();
                    travelId = teamEntity.getTravelId();

                    messageEntity.setTeamName(teamName);
                    messageEntity.setTeamNum(teamNum);
                    messageEntity.setTeavelId(travelId);
                }
                messageEntity.setCreateTime(new Date());
                messageEntity.setUpdateTime(new Date());
                messageEntity.setStatus(0);
                messageEntity.setUserId(userId);
                messageEntity.setTravelHeadUrl("http://letsgoimg-10049120.image.myqcloud.com/static_pic/face/staff.png");
                logger.info("向member表插入数据");
                qMessageDao.insertSubjectMessage(messageEntity);

                //messageEntity.setID(id);
            }

        }
        logger.info("向messagetext表插入一条数据");
        // 向messagetext表插入一条数据
        createMessageTextInfo(messageEntity, memberEntity,messageRequest, userId,0);

        if(flag){
            logger.info("更新message表中的updateTime和status");
            //更新message表中的updateTime和status
            messageEntity.setStatus(0);
            updateSubjectMessage(messageEntity);
        }else{
            logger.info("用户第一次反馈问题，添加自动回复");
            //用户第一次反馈问题，添加自动回复
            postMessageTravel(String.valueOf(messageEntity.getID()),null,String.valueOf(travelId));
        }
        commonResponse.setRetCode(ErrorConstant.SUCCESS);
        commonResponse.setRetMsg("反馈问题已收到！");
        return commonResponse;
    }

    /**
     * 根据旅行社ID获取行程反馈问题列表
     * @param travelId
     * @return
     */
    @Override
    public HashMap getSubjectMessageListByTravelId(int pageStart,int pageSize,int status, String travelId/*,String teamNum*/) {
        logger.info("传入的travelID为" + travelId );

        HashMap resultMap =  new HashMap();
        //设置分页参数

        PageHelper.startPage(pageStart, pageSize);

        MessageEntity requestMessage = new MessageEntity();
        requestMessage.setTeavelId(Long.parseLong(travelId));
        if(status!=-1){
            requestMessage.setStatus(status);
        }
      /*  if(null!=teamNum){
            requestMessage.setTeamNum(teamNum);
        }
*/
        //获取反馈列表信息
        Page<MessageEntity> messageList = qMessageDao.getSubjectMessageByRequest(requestMessage);
        for (MessageEntity messageEntity : messageList){
            long userId = messageEntity.getUserId();
            UserEntity userEntity = userService.getUserById(userId);
            messageEntity.setHeadUrl(userEntity.getPhotoUrl());
            messageEntity.setUserName(userEntity.getRealName());
        }

        //总条数
        long total = messageList.getTotal();
        resultMap.put("messageList",messageList);
        resultMap.put("total",total);
        resultMap.put("start",pageStart);
        resultMap.put("pageNumber",messageList.getPages());
        resultMap.put("status",status);
        return resultMap;
    }

    /**
     * 获取未读的反馈条数
     * @param travelId
     * @return
     */
    @Override
    public long getNoReadSubjectNum(String travelId) {
        long notReadNum = 0;
        logger.info("传入的travelID为" + travelId );

        MessageEntity requestMessage = new MessageEntity();
        requestMessage.setTeavelId(Long.parseLong(travelId));
        requestMessage.setStatus(0);
        logger.info("获取未读信息"  );
        Page<MessageEntity> messageList = qMessageDao.getSubjectMessageByRequest(requestMessage);
        notReadNum = messageList.size();
        return notReadNum;
    }


    /**
     * 旅行社端 获取问题反馈回复列表
     * @param memberId 成员的id
     * @param mid 反馈问题主题的id
     * @return
     */
    @Override
    public Map getReplyMessageList(String memberId, String mid) {
        Map resultMap =  new HashMap<>();
        logger.info("传入的memberId和mid分别为" +  memberId + " :" + mid);
        MessageEntity requestMessageEntity = new MessageEntity();
        requestMessageEntity.setMemberId(Long.parseLong(memberId));
        requestMessageEntity.setID(Long.parseLong(mid));

        logger.info("获取message 和messageText信息"  );
        //获取 message 和messageText信息
        MessageEntity messageEntity = qMessageDao.getMessageTextListByRequest(requestMessageEntity);
        List<MessageTextEntity> messageTextEntityList = messageEntity.getMessageTextEntityList();


       List<MemberEntity>  ledarEntityList = new ArrayList<MemberEntity>();

        logger.info("获取成员的信息"  );
        //获取成员的信息
        MemberEntity memberEntity = memberDao.select(Long.parseLong(memberId));

        TeamEntity teamEntity =  new TeamEntity();
        logger.info("获取团的信息"  );
        //获取团的信息
        if(null!=memberEntity){
            teamEntity = teamDao.select(memberEntity.gettId());
            ledarEntityList = memberService.getLeaderByTId(memberEntity.gettId());
        }

        logger.info("组装返回数据"  );

        for (MessageTextEntity messageTextEntity:  messageTextEntityList) {
            if(messageTextEntity.getStatus()==0){
                if(null != memberEntity){
                    messageTextEntity.setHeadUrl(memberEntity.getPhotoUrl());
                }
            }else{
                messageTextEntity.setHeadUrl(messageEntity.getTravelHeadUrl());
            }
            List<String> photoList = new ArrayList<>();
            if(messageTextEntity.getPhotoUrl1()!=null){
                photoList.add(messageTextEntity.getPhotoUrl1());
            }
            if(messageTextEntity.getPhotoUrl2()!=null){
                photoList.add(messageTextEntity.getPhotoUrl2());
            }
            if(messageTextEntity.getPhotoUrl3()!=null){
                photoList.add(messageTextEntity.getPhotoUrl3());
            }
            if(messageTextEntity.getPhotoUrl4()!=null){
                photoList.add(messageTextEntity.getPhotoUrl4());
            }
            if(messageTextEntity.getPhotoUrl5()!=null){
                photoList.add(messageTextEntity.getPhotoUrl5());
            }
            if(messageTextEntity.getPhotoUrl6()!=null){
                photoList.add(messageTextEntity.getPhotoUrl6());
            }
            if(messageTextEntity.getPhotoUrl7()!=null){
                photoList.add(messageTextEntity.getPhotoUrl7());
            }
            if(messageTextEntity.getPhotoUrl8()!=null){
                photoList.add(messageTextEntity.getPhotoUrl8());
            }
            if(messageTextEntity.getPhotoUrl9()!=null){
                photoList.add(messageTextEntity.getPhotoUrl9());
            }

            if(!photoList.isEmpty()){
                messageTextEntity.setPhotoList(photoList);
            }

        }
        if(!ledarEntityList.isEmpty()){
            resultMap.put("ledarMemberEntity",ledarEntityList.get(0));
        }
        resultMap.put("messageEntity",messageEntity);
        resultMap.put("memberEntity",memberEntity);
        resultMap.put("teamEntity",teamEntity);

        resultMap.put("status",1);
        resultMap.put("message","获取列表成功");

        MessageTextEntity lastMessage = messageTextEntityList.get(messageTextEntityList.size()-1);
        if(lastMessage.getStatus() !=1){
            //修改状态为已读
            updateSubjectMessageStatus(messageEntity.getID(),1);
        }
        return resultMap;
    }

    private void updateSubjectMessageStatus(Long id, int status) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setID(id);
        messageEntity.setStatus(status);
        qMessageDao.updateSubjectMessageStatus(messageEntity);
    }

    @Override
    public Map postMessageTravel(String mId, String message, String travleId) {

        Map resultMap = new HashMap<>();

        //获取旅行社的信息
        TravelAgencyEntity travelAgencyEntity = travelAgencyService.getTravelAgency(Long.parseLong(travleId));

        logger.info("向messagetext表插入一条数据");
        MessageTextEntity messageTextEntity = new MessageTextEntity();
        messageTextEntity.setSenderName(travelAgencyEntity.getName());
        messageTextEntity.setPostTime(new Date());
        if(null!=message){
            messageTextEntity.setMessage(message);
        }else{
            messageTextEntity.setMessage("尊敬的贵宾您好，您的反馈我们已经收到，我们会在行程结束后7个工作日内为您处理完毕，请您耐心等待");
        }
        messageTextEntity.setSenderId(Long.parseLong(travleId));
        messageTextEntity.setmId(Long.parseLong(mId));
        messageTextEntity.setStatus(1);
        qMessageDao.insertMessageText(messageTextEntity);

        logger.info("根据ID获取message信息");
        MessageEntity messageEntity = new MessageEntity();
        messageEntity = qMessageDao.getSubjectMessageById(mId);

        logger.info("更新message表中的updateTime和status");
        if(null!=message){
            messageEntity.setStatus(2);
            updateSubjectMessage(messageEntity);
        }

        resultMap.put("resultCode", 0);
        resultMap.put("message","回复反馈问题成功");
        return resultMap;
    }


    // 用户创建回复列表信息
    private void createMessageTextInfo(MessageEntity messageEntity,MemberEntity memberEntity, QMessageRequest messageRequest, Long userId,int status) {
        MessageTextEntity messageTextEntity = new MessageTextEntity();
        if(null==memberEntity.getId()){
            memberEntity = memberDao.select(messageEntity.getMemberId());
        }
        messageTextEntity.setMessage(messageRequest.getMessage());
        messageTextEntity.setSenderId(memberEntity.getId());
        messageTextEntity.setmId(messageEntity.getID());
        messageTextEntity.setSenderName(memberEntity.getRealName());
        messageTextEntity.setStatus(status);
        messageTextEntity.setPostTime(new Date());
        List<String> photoList = messageRequest.getPhotoUrlList();
        List<String> tempphotoList = new ArrayList<>() ;
        for (int i = 0;i < photoList.size(); i++){
            if(i==photoList.size()){
                break;
            }else{
                switch(i){
                    case 0:
                        messageTextEntity.setPhotoUrl1(photoList.get(i));
                        break;
                    case 1:
                        messageTextEntity.setPhotoUrl2(photoList.get(i));
                        break;
                    case 2:
                        messageTextEntity.setPhotoUrl3(photoList.get(i));
                        break;
                    case 3:
                        messageTextEntity.setPhotoUrl4(photoList.get(i));
                        break;
                    case 4:
                        messageTextEntity.setPhotoUrl5(photoList.get(i));
                        break;
                    case 5:
                        messageTextEntity.setPhotoUrl6(photoList.get(i));
                        break;
                    case 6:
                        messageTextEntity.setPhotoUrl7(photoList.get(i));
                        break;
                    case 7:
                        messageTextEntity.setPhotoUrl8(photoList.get(i));
                        break;
                    case 8:
                        messageTextEntity.setPhotoUrl9(photoList.get(i));
                        break;

                    default:
                        break;

                }
            }
        }
        qMessageDao.insertMessageText(messageTextEntity);
    }

    //更新message信息
    private void updateSubjectMessage(MessageEntity messageEntity) {
        messageEntity.setUpdateTime(new Date());
        qMessageDao.updateSubjectMessage(messageEntity);
    }



}
