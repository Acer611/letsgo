package com.umessage.letsgo.service.impl.team;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.umessage.letsgo.dao.team.OnlookersDao;
import com.umessage.letsgo.dao.team.OnlookersDetailsDao;
import com.umessage.letsgo.dao.team.OnlookersUserDao;
import com.umessage.letsgo.domain.po.notice.NoticeDetailEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.OnlookersDetailsEntity;
import com.umessage.letsgo.domain.po.team.OnlookersEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.notice.request.DetailParamRequest;
import com.umessage.letsgo.domain.vo.team.requset.OnlookersRequest;
import com.umessage.letsgo.domain.vo.team.requset.OnlookersUserRequest;
import com.umessage.letsgo.domain.vo.team.requset.ReplyLikeRequest;
import com.umessage.letsgo.domain.vo.team.respone.LikeInfo;
import com.umessage.letsgo.domain.vo.team.respone.OnlookersResponse;
import com.umessage.letsgo.domain.vo.team.respone.ReplyInfo;
import com.umessage.letsgo.domain.vo.team.respone.WatchMessageResponse;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.notice.INoticeDetailService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.team.IOnlookersDetailsService;
import com.umessage.letsgo.service.api.team.IOnlookersService;
import com.umessage.letsgo.service.api.team.IOnlookersUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/2.
 */
@Service
public class OnlookersServiceImpl implements IOnlookersService {
    @Resource
    private OnlookersDao onlookersDao;
    @Resource
    private OnlookersDetailsDao onlookersDetailsDao;
    @Resource
    private IUserService userService;
    @Resource
    private IScheduleService scheduleService;
    @Resource
    private IOnlookersUserService onlookersUserService;
    @Resource
    private INoticeDetailService noticeDetailService;
    @Resource
    private OnlookersUserDao onlookersUserDao;
    @Resource
    private IOnlookersDetailsService onlookersDetailsService;

    @Transactional
    @Override
    public CommonResponse sendOnlookers(Long scheduleId,Long userId,String message, String imgUrl,String thumbnailUrl,String wide,String height) {
        CommonResponse commonResponse=new CommonResponse();
        try{
            OnlookersEntity onlookersEntity=new OnlookersEntity();
            onlookersEntity.setCreateTime(new Date());
            onlookersEntity.setContent(message);
            onlookersEntity.setIsSystemInfo(0);
            onlookersEntity.setPhotosUrl(imgUrl);
            onlookersEntity.setUserId(userId);
            onlookersEntity.setThumbnailUrl(thumbnailUrl);
            onlookersEntity.setVersion(0l);
            onlookersEntity.setSendTime(new Date());
            onlookersEntity.setScheduleId(scheduleId);
            onlookersEntity.setHeight(height);
            onlookersEntity.setWide(wide);
            onlookersDao.sendOnlookers(onlookersEntity);
            commonResponse.setRetCode(0);
            commonResponse.setRetMsg("发布围观消息成功");
            //给围观的好友一条提示信息
            OnlookersUserRequest request =new OnlookersUserRequest();
            request.setUserId(userId);
            request.setScheduleId(scheduleId);
            List<Long>  userIds = onlookersUserDao.getMyFriendlist(request);
            if(userIds!=null&&userIds.size()>0){
                for (Long id:userIds){
                    NoticeDetailEntity noticeDetailEntity =new NoticeDetailEntity();
                    noticeDetailEntity.setUserId(id);
                    noticeDetailEntity.setTeamId(scheduleService.getTxGroupIdByScheduleId(scheduleId));
                    noticeDetailEntity.setIsActive(0);
                    //消息分类【1：集合；2：通知；3：公告；4：回复】
                    noticeDetailEntity.setType(4);
                    noticeDetailService.addNoticeDetail(noticeDetailEntity);
                }
            }
        }
        catch (Exception e){
            commonResponse.setRetCode(1);
            commonResponse.setRetMsg("发布围观消息失败");
        }
        return commonResponse;
    }

    //获取围观和回复信息【游客端】
    @Override
    public OnlookersResponse getWatch(Long userId, Long scheduleId,Integer type,int pageNum,int pageSize,Long onlookerUserId) {
        //围观小红点去除 未读提醒表为已经读
        String txGroupid=scheduleService.getTxGroupIdByScheduleId(scheduleId);
        DetailParamRequest request =new DetailParamRequest();
        request.setTeamId(txGroupid);
        request.setUserId(userId);
        request.setType(4);
        noticeDetailService.updateUnReads(request);
        //类型:1发布围观者，2围观别人
        //发布围观用户(他是被围观的)-- ownerId--byReplyId
        OnlookersRequest onlookersRequest=new OnlookersRequest();
        OnlookersResponse on=new OnlookersResponse();
        Page<WatchMessageResponse>  response=new Page<WatchMessageResponse>();
        if(pageNum==0){
            pageNum=onlookersRequest.getPageNum();
        }
        if(pageSize==0){
            pageSize=onlookersRequest.getPageSize();
        }
        //发布围观用户腾讯Id
        String txUserId="";
        if(type==1){
            onlookersRequest.setOwnerId(userId);
            onlookersRequest.setScheduleId(scheduleId);
            PageHelper.startPage(pageNum,pageSize);
            Page<WatchMessageResponse> ll=onlookersDao.getWatchOwner(onlookersRequest);
            if(ll!=null && ll.size()>0) {
                for (WatchMessageResponse re : ll) {
                    if (re != null) {
                        List<ReplyInfo> replyInfo = new ArrayList<ReplyInfo>();
                        List<LikeInfo> likeInfo = new ArrayList<LikeInfo>();
                        List<String> onlookersImgList = new ArrayList<String>();
                        if (!StringUtils.isEmpty(re.getOnlookersImg())) {
                            String[] imgs = re.getOnlookersImg().split(";");
                            if (imgs != null && imgs.length > 0) {
                                for (int i = 0; i < imgs.length; i++) {
                                    if (!StringUtils.isEmpty(imgs[i])) {
                                        onlookersImgList.add(imgs[i]);
                                    }
                                }
                                re.setOnlookersImgList(onlookersImgList);
                            }
                        }
                        //判断当前登陆用户是否已经对这条围观点赞
                        ReplyLikeRequest req =new ReplyLikeRequest();
                        req.setuId(userId);
                        req.setOnlookersId(re.getId());
                        if(onlookersDetailsService.isLike(req)){
                            re.setIsLike(1);
                        }else{
                            re.setIsLike(0);
                        }
                        //获取回复点赞
                         onlookersRequest.setOnlookersId(re.getId());
                         List<ReplyInfo> replyInfos =onlookersDao.getWatchOwner2(onlookersRequest);

                            if (replyInfos != null && replyInfos.size() > 0) {
                                for (ReplyInfo reply : replyInfos) {
                                    if (reply != null && reply.getType() != null) {
                                        //1为点赞，2为回复
                                        if (reply.getType() == 1 && reply.getIsLike() == 1) {//1为点赞  0未点赞
                                            LikeInfo like = new LikeInfo();
                                            UserEntity user = userService.getUserById(reply.getReId());
                                            like.setOrtherId(user.getUserName());
                                            like.setName(user.getRealName());
                                            like.setHeadUrl(user.getPhotoUrl());
                                            likeInfo.add(like);
                                        }
                                        if (reply.getType() == 2 || reply.getType() == 3) {
                                            ReplyInfo rel = new ReplyInfo();
                                            rel.setMessage(reply.getMessage());
                                            UserEntity user = userService.getUserById(reply.getByReId());
                                            rel.setByReplyName(user.getRealName());
                                            rel.setByReplyId(user.getUserName());
                                            rel.setCreateTime(reply.getCreateTime());
                                            UserEntity user1 = userService.getUserById(reply.getReId());
                                            rel.setReplyId(user1.getUserName());
                                            rel.setReplyName(user1.getRealName());
                                            rel.setType(reply.getType());
                                            replyInfo.add(rel);
                                        }

                                    }
                                }
                            }
                            re.setLikeList(likeInfo);
                            re.setReplyList(replyInfo);
                            re.setWide(re.getWide());
                            re.setHeight(re.getHeight());
                            UserEntity user = userService.getUserById(userId);
                            re.setUserHeadUrl(user.getPhotoUrl());
                            re.setUserName(user.getRealName());
                            re.setUserId(user.getUserName());
                            response.add(re);
                    }
                }
            }
        }
        //围观用户（围观别人的）-- otherId--replyId
        if(type==2){
            //通过围观用户ID 获取被围观的用户对象
            UserEntity userLooker = userService.getUserById(onlookersUserService.getUserIdByOnlookerUserId(userId,scheduleId));
            txUserId=userLooker.getUserName();
            //通过围观用户ID 获取被围观的用户iD
            onlookersRequest.setOwnerId(onlookerUserId);
            onlookersRequest.setOtherId(userId);
            onlookersRequest.setScheduleId(scheduleId);
            PageHelper.startPage(pageNum,pageSize);
            Page<WatchMessageResponse> ll=onlookersDao.getWatchOther(onlookersRequest);
            if(ll!=null && ll.size()>0){
                for (WatchMessageResponse re:ll) {
                    if(re!=null){
                        List<ReplyInfo> replyInfo = new ArrayList<ReplyInfo>();
                        List<LikeInfo> likeInfo = new ArrayList<LikeInfo>();
                        List<String> onlookersImgList= new ArrayList<String>();
                        if(!StringUtils.isEmpty(re.getOnlookersImg())) {
                            String[] imgs = re.getOnlookersImg().split(";");
                            if (imgs != null && imgs.length > 0) {
                                for (int i = 0; i < imgs.length; i++) {
                                    if (!StringUtils.isEmpty(imgs[i])) {
                                        onlookersImgList.add(imgs[i]);
                                    }
                                }
                                re.setOnlookersImgList(onlookersImgList);
                            }
                        }
                        //判断当前登陆用户是否已经对这条围观点赞
                        ReplyLikeRequest req =new ReplyLikeRequest();
                        req.setuId(userId);
                        req.setOnlookersId(re.getId());
                        if(onlookersDetailsService.isLike(req)){
                            re.setIsLike(1);
                        }else{
                            re.setIsLike(0);
                        }
                        //获取回复点赞
                        onlookersRequest.setOnlookersId(re.getId());
                        List<ReplyInfo> replyInfos =onlookersDao.getWatchOther2(onlookersRequest);
                        if(replyInfos!=null&& replyInfos.size()>0){
                            for (ReplyInfo reply:replyInfos) {
                                if(reply!=null && reply.getType()!=null){
                                    //1为点赞，2为回复
                                    if(reply.getType()==1 && reply.getIsLike()==1){//1为点赞  0未点赞
                                        LikeInfo like = new LikeInfo();
                                        UserEntity user = userService.getUserById(reply.getReId());
                                        like.setOrtherId(user.getUserName());
                                        like.setName(user.getRealName());
                                        like.setHeadUrl(user.getPhotoUrl());
                                        likeInfo.add(like);
                                    }
                                    if (reply.getType() == 2 || reply.getType() == 3) {
                                        ReplyInfo rel = new ReplyInfo();
                                        rel.setMessage(reply.getMessage());
                                        UserEntity user = userService.getUserById(reply.getByReId());
                                        rel.setByReplyName(user.getRealName());
                                        rel.setByReplyId(user.getUserName());
                                        rel.setCreateTime(reply.getCreateTime());
                                        UserEntity user1 = userService.getUserById(reply.getReId());
                                        rel.setReplyId(user1.getUserName());
                                        rel.setReplyName(user1.getRealName());
                                        rel.setType(reply.getType());
                                        replyInfo.add(rel);
                                    }

                                }
                            }
                         }
                        re.setLikeList(likeInfo);
                        re.setReplyList(replyInfo);
                        re.setUserHeadUrl(userLooker.getPhotoUrl());
                        re.setUserName(userLooker.getRealName());
                        re.setUserId(userLooker.getUserName());
                        response.add(re);
                    }
                }
            }
        }
        OnlookersDetailsEntity od=new  OnlookersDetailsEntity();
        od.setScheduleId(scheduleId);
        od.setByReplyId(userId);
        on.setUnRead((onlookersDetailsDao.getLastReply(od)).size());
        on.setWatchMessageResponse(response);
        on.setTxGroupId(txGroupid);
        on.setTxUserId(txUserId);
        return on;
    }

    //通过ID获取围观
    public OnlookersEntity getOnlookersById(Long id){
        return onlookersDao.getOnlookersById(id);
    }
}
