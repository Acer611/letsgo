package com.umessage.letsgo.service.impl.team;

import com.umessage.letsgo.core.config.constant.ConfigConstant;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.dao.team.OnlookersDetailsDao;
import com.umessage.letsgo.domain.po.notice.NoticeDetailEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.OnlookersDetailsEntity;
import com.umessage.letsgo.domain.po.team.OnlookersEntity;
import com.umessage.letsgo.domain.po.team.OnlookersUserEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.system.request.MessageRequest;
import com.umessage.letsgo.domain.vo.team.requset.InviteWatchRequest;
import com.umessage.letsgo.domain.vo.team.requset.OnlookersDetailsRequest;
import com.umessage.letsgo.domain.vo.team.requset.OnlookersUserRequest;
import com.umessage.letsgo.domain.vo.team.requset.ReplyLikeRequest;
import com.umessage.letsgo.domain.vo.team.respone.OnlookersDetailsInfo;
import com.umessage.letsgo.domain.vo.team.respone.RedPointResponse;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.notice.INoticeDetailService;
import com.umessage.letsgo.service.api.security.IUserRoleService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.system.IMessageService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.IOnlookersDetailsService;
import com.umessage.letsgo.service.api.team.IOnlookersService;
import com.umessage.letsgo.service.api.team.IOnlookersUserService;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;


/**
 * Created by zengguoqing on 2016/9/5.
 */
@Service
public class OnlookersDetailsServiceImpl implements IOnlookersDetailsService {
    @Resource
    private OnlookersDetailsDao onlookersDetailsDao;
    @Resource
    private IMessageService messageService;
    @Resource
    private IOnlookersUserService onlookersUserService;
    @Resource
    private IUserService userService;
    @Resource
    private IUserRoleService userRoleService;
    @Resource
    private UserLoginHelper oauth2LoginHelper;
    @Resource
    private INoticeDetailService noticeDetailService;
    @Resource
    private IScheduleService scheduleService;
    @Resource
    private IMemberService memberService;
    @Resource
    private IOnlookersService onlookersService;



    //获取查看最新回复点赞
    @Override
    public List<OnlookersDetailsInfo> getLastReply(Long userId, Long scheduleId) {
        OnlookersDetailsEntity onlookersDetailsEntity=new OnlookersDetailsEntity();
        onlookersDetailsEntity.setByReplyId(userId);
        onlookersDetailsEntity.setScheduleId(scheduleId);
        return onlookersDetailsDao.getLastReply(onlookersDetailsEntity);
    }

    //获取围观小红
    @Override
    public RedPointResponse getWatchRedPoint(OnlookersDetailsRequest request){
            //查看1:是，0否   查找未被查看的
            request.setIsLook(0);
            int num=onlookersDetailsDao.getWatchRedPoint(request);
            RedPointResponse response =new RedPointResponse();
            if(num>0){
                response.setIsRed(1);
            }else {
                response.setIsRed(0);
            }
            return response;
        }
       //回复点赞接口
      public CommonResponse replyLikes(ReplyLikeRequest request){
          //类型：1为点赞，2为回复  3评论
          //是否点赞：1为点赞 0未点赞
          //如果是点赞 先判断是否已经点赞  已经点了 就取消 删掉数据
          // 1、获取当前登录的用户信息
          //根据用户腾讯组id获取用户id
          UserEntity userEntity= userService.selecUserByUserName(request.getUserId());
          if(userEntity==null){
              throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：所需参数不能为空！");
          }
          request.setuId(userEntity.getId());
          if(request.getType()==1){
              //true 已经点赞
            if(isLike(request)){
                //删掉数据
                deleteLike(request);
                return new CommonResponse();
            }
          }
              OnlookersDetailsEntity onlookersDetailsEntity=new OnlookersDetailsEntity();
              Date date = new Date();
              onlookersDetailsEntity.setCreateTime(date);
              onlookersDetailsEntity.setVersion(0L);
              onlookersDetailsEntity.setIsLike(request.getIsLike());

              //根据用户腾讯组id获取用户id
              UserEntity user= userService.selecUserByUserName(request.getByReplyId());
              if(user==null){
                  throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "参数：所需参数不能为空！");
              }
              onlookersDetailsEntity.setByReplyName(user.getRealName());
              onlookersDetailsEntity.setByReplyId(user.getId());
              onlookersDetailsEntity.setContent(request.getContent());
              onlookersDetailsEntity.setImgUrl(request.getImgUrl());
              onlookersDetailsEntity.setOnlookersId(request.getOnlookersId());
              onlookersDetailsEntity.setType(request.getType());
              String  url="";
              OnlookersEntity onlookers=onlookersService.getOnlookersById(request.getOnlookersId());
               if(onlookers!=null && !StringUtils.isEmpty(onlookers.getPhotosUrl())){
                   String [] urls = onlookers.getPhotosUrl().split(";");
                  if (urls!=null&&urls.length>0) {
                      url=urls[0];
                  }
               }
              //获取围观信息的第一张图片
              onlookersDetailsEntity.setImgUrl(url);
              onlookersDetailsEntity.setUserId(userEntity.getId());
              onlookersDetailsEntity.setReplyName(userEntity.getRealName());
              onlookersDetailsEntity.setReplyId(userEntity.getId());
              onlookersDetailsEntity.setScheduleId(request.getScheduleId());
              //默认为未查看
              //判断是不是自己给自己回复 自己给自己点赞 都要显示以查看
             if(request.getUserId().equals(request.getByReplyId())){
                 onlookersDetailsEntity.setIsLook(1);
             }else{
                 onlookersDetailsEntity.setIsLook(0);
             }
             onlookersDetailsDao.insert(onlookersDetailsEntity);
            //被回复和被点赞加提醒
          if(!request.getUserId().equals(request.getByReplyId())){
              NoticeDetailEntity noticeDetailEntity =new NoticeDetailEntity();
              noticeDetailEntity.setUserId(user.getId());
              noticeDetailEntity.setTeamId(scheduleService.getTxGroupIdByScheduleId(request.getScheduleId()));
              noticeDetailEntity.setIsActive(0);
              //消息分类【1：集合；2：通知；3：公告；4：回复】
              noticeDetailEntity.setType(4);
              noticeDetailService.addNoticeDetail(noticeDetailEntity);
          }
         return new CommonResponse();
    }

    //邀请围观接口
    public  CommonResponse inviteWatch(InviteWatchRequest request){
        CommonResponse response=new CommonResponse();
        try {
            UserEntity user = userService.getUserByPhone(request.getMobile());
            int has=0;
            if(user != null) {
                //先判断是不是已经被邀请
                OnlookersUserRequest req = new OnlookersUserRequest();
                req.setScheduleId(request.getScheduleId());
                req.setUserId(request.getUserId());
                req.setOnlookerUserId(user.getId());
                if(onlookersUserService.isHas(req)){
                    response.setRetCode(7);
                    response.setRetMsg("该人员已经被邀请过了");
                    return response;
                }
                //判断用户是否是团里面的人  是的话返回getRetCode  8   提示不能邀请同一个团里面的人
                List<MemberEntity> members =memberService.getMemberListByTeamId(scheduleService.getTxGroupIdByScheduleId(request.getScheduleId()));
                if(members!=null && members.size()>0){
                    for (MemberEntity mem:members) {
                        if(mem.getUserId().equals(user.getId())){
                            has=1;
                            break;
                        }
                    }
                 }

             }
            //根据手机号判断是否被邀请
            if(has==0){
                OnlookersUserRequest req = new OnlookersUserRequest();
                req.setUserId(request.getUserId());
                req.setPhone(request.getMobile());
                if(onlookersUserService.isHasByPhone(req)){
                    response.setRetCode(7);
                    response.setRetMsg("该人员已经被邀请过了");
                    return response;
                }
            }
            if(has==1) {
                response.setRetCode(8);
                response.setRetMsg("不能邀请同一团队的人员");
                return   response;
            }
            MessageRequest messageRequest = new MessageRequest(request.getMobile(),"","","", ConfigConstant.TOURIST_URL);
            messageRequest.setInvitee(userService.getUserById(request.getUserId()).getRealName());
            messageRequest.setTeamName(scheduleService.getSchedule(request.getScheduleId()).getName());
            messageRequest.setDownloadURL(ConfigConstant.TOURIST_URL);
            response  = messageService.sendInvestmentMessage(request.getMobile(),8,messageRequest);
            //发送成功，保存围观好友
            if(response.getRetCode()==0){
                OnlookersUserEntity onus= new OnlookersUserEntity();
                if(user != null) {//判断是否为已有用户
                    onus.setOnlookerUserId(user.getId());
                }else{//新用户则为他创建用户
//                    UserEntity u=new UserEntity();
//                    u.setNickName("游客");
//                    u.setRealName("游客");
//                    u.setUserName(Base32Util.encode(System.currentTimeMillis()+request.getMobile()));
//                    u.setPhone(request.getMobile());
//                    u.setSex(1);
//                    try {
//                        String photoUrl = PhotoHelper.createMemberImage(u.getRealName(), u.getSex());
//                        user.setPhotoUrl(photoUrl);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    u.setUserStatus(1);
//                    userService.create(u);
//                    // 添加用户角色关系
//                    userRoleService.createUserRoleByUserId(u.getId(), Long.valueOf(3));
                    onus.setOnlookerUserId(-1l);

                }
                //已经确认
                onus.setOnlookersStatus(2);
                onus.setScheduleId(request.getScheduleId());
                onus.setUserId(request.getUserId());
                onus.setName(request.getName());
                onus.setPhone(request.getMobile());
                onus.setSex(request.getSex());
                onlookersUserService.add(onus);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response;
    }

    //判断是否已经点赞
    public boolean isLike(ReplyLikeRequest request){
      int i=onlookersDetailsDao.isLike(request);
        if(i>0){
            return true;
        }
        return false;
    }
    //删掉已经点赞的数据
    public int deleteLike(ReplyLikeRequest request){
      return onlookersDetailsDao.deleteLike(request);
    }
    //修改查看状态
    public int updateStatus(OnlookersDetailsEntity onlookersDetailsEntity){
        return onlookersDetailsDao.updateStatus(onlookersDetailsEntity);
    }
}
