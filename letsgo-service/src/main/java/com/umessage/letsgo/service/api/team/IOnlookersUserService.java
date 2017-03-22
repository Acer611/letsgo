package com.umessage.letsgo.service.api.team;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.OnlookersUserEntity;
import com.umessage.letsgo.domain.vo.team.requset.OnlookersUserRequest;
import com.umessage.letsgo.domain.vo.team.respone.OnlookersUserListResponse;

import java.util.List;

/**
 * Created by pw on 2016/9/6.
 */
public interface IOnlookersUserService {
    int add(OnlookersUserEntity onlookersUserEntity);
    //获取围观好友列表
    OnlookersUserListResponse getMyFriendlist(OnlookersUserRequest request);
    //通过围观用户ID 获取被围观的用户iD
    Long getUserIdByOnlookerUserId(Long onlookerUserId,Long scheduleId);
    //通过围观用户ID 获取围观的行程ID集合
    List<OnlookersUserEntity>  getScheduleIdByOnlookerUserId(Long onlookerUserId);
    //是否已经被邀请
     boolean isHas(OnlookersUserRequest request);
    //注册用户判断是否被邀请围观，若是需要关联围观
     public void setUserId(UserEntity user);
    //通过手机号查询
    List<OnlookersUserEntity> getOnlookersUserByPhone(String phone);

    //通过手机号和用户ID 判断有木有被邀请
    boolean isHasByPhone(OnlookersUserRequest request);

    //通过围观用户ID 获取围观的行程集合
    Page<ScheduleEntity> getSchedules(Long onlookerUserId);

    //通过围观用户ID 获取最新的已经结束的围观
    ScheduleEntity getEndScheduleOne(Long onlookerUserId);
    //通过围观用户ID 获取已经结束的围观的行程集合
    Page<ScheduleEntity> getEndSchedules(Long onlookerUserId);
}
