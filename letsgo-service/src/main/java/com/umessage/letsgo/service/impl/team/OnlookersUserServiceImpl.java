package com.umessage.letsgo.service.impl.team;

import com.github.pagehelper.Page;
import com.umessage.letsgo.dao.team.OnlookersUserDao;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.OnlookersUserEntity;
import com.umessage.letsgo.domain.vo.team.requset.OnlookersUserRequest;
import com.umessage.letsgo.domain.vo.team.respone.OnlookersUser;
import com.umessage.letsgo.domain.vo.team.respone.OnlookersUserListResponse;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.team.IOnlookersUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by pw on 2016/9/6.
 */
@Service
public class OnlookersUserServiceImpl implements IOnlookersUserService {
    @Resource
    private OnlookersUserDao onlookersUserDao;
    @Resource
    private IUserService userService;

    @Override
    public int add(OnlookersUserEntity onlookersUserEntity) {
        Date date =new Date();
        onlookersUserEntity.setUpdateTime(date);
        onlookersUserEntity.setCreateTime(date);
        onlookersUserEntity.setVersion(0L);
        return onlookersUserDao.insert(onlookersUserEntity);
    }

    //获取围观好友列表
    public OnlookersUserListResponse getMyFriendlist(OnlookersUserRequest request){
        //围观好友ID集合
         List<Long>  userIds = onlookersUserDao.getMyFriendlist(request);
        OnlookersUserListResponse response = new OnlookersUserListResponse();
        if(userIds==null&&userIds.size()<=0){
            return response;
        }
        List<OnlookersUser> friendList =new ArrayList<OnlookersUser>();
        for (Long id:userIds) {
            if (id!=-1) {
                UserEntity user = userService.getUserById(id);
                if (user != null && !"游客".equals(user.getNickName())) {
                    OnlookersUser vo = new OnlookersUser();
                    vo.setUserId(user.getUserName());
                    vo.setName(user.getNickName());
                    vo.setRealName(user.getRealName());
                    vo.setPhotoUrl(user.getPhotoUrl());
                    friendList.add(vo);
                }
            }
        }
        response.setFriendList(friendList);
        return response;
     }
    //通过围观用户ID 获取被围观的用户iD
    public  Long getUserIdByOnlookerUserId(Long onlookerUserId,Long scheduleId){
        OnlookersUserRequest request =new OnlookersUserRequest();
        request.setUserId(onlookerUserId);
        request.setScheduleId(scheduleId);
        return  onlookersUserDao.getUserIdByOnlookerUserId(request);
    }
    //通过围观用户ID 获取围观的行程ID集合
    public List<OnlookersUserEntity>  getScheduleIdByOnlookerUserId(Long onlookerUserId){
        OnlookersUserRequest request =new OnlookersUserRequest();
        request.setOnlookerUserId(onlookerUserId);
        return onlookersUserDao.getScheduleIdByOnlookerUserId(request);
    }

    //是否已经被邀请
   public boolean isHas(OnlookersUserRequest request){
       int i =onlookersUserDao.isHas(request);
       if(i>0){
           return  true;
       }
       return  false;
   }

    //通过手机号查询
    public List<OnlookersUserEntity> getOnlookersUserByPhone(String phone){
        return  onlookersUserDao.getOnlookersUserByPhone(phone);
    }
    //注册用户判断是否被邀请围观，若是需要关联围观
    public void setUserId(UserEntity user){
        List<OnlookersUserEntity> onlookersUserList = onlookersUserDao.getOnlookersUserByPhone(user.getPhone());
        if(onlookersUserList!=null && onlookersUserList.size()>0){
            for (OnlookersUserEntity onlookersUserEntity:onlookersUserList) {
                onlookersUserEntity.setOnlookerUserId(user.getId());
                onlookersUserDao.update(onlookersUserEntity);
            }
        }
     }
    //通过手机号和用户ID 判断有木有被邀请
    public boolean isHasByPhone(OnlookersUserRequest request){
     int i = onlookersUserDao.isHasByPhone(request);
        if(i>0){
            return true;
        }
        return false;
    }

    //通过围观用户ID 获取围观的行程集合
    public Page<ScheduleEntity> getSchedules(Long onlookerUserId){
        OnlookersUserRequest request =new OnlookersUserRequest();
        request.setOnlookerUserId(onlookerUserId);
        return onlookersUserDao.getSchedules(request);
    }

    //通过围观用户ID 获取最新的已经结束的围观
    public ScheduleEntity getEndScheduleOne(Long onlookerUserId){
        OnlookersUserRequest request =new OnlookersUserRequest();
        request.setOnlookerUserId(onlookerUserId);
        return onlookersUserDao.getEndScheduleOne(request);
    }
    //通过围观用户ID 获取已经结束的围观的行程集合
    public Page<ScheduleEntity> getEndSchedules(Long onlookerUserId){
        OnlookersUserRequest request =new OnlookersUserRequest();
        request.setOnlookerUserId(onlookerUserId);
        return onlookersUserDao.getEndSchedules(request);
    }

}
