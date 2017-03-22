package com.umessage.letsgo.dao.team;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.team.OnlookersUserEntity;
import com.umessage.letsgo.domain.vo.team.requset.OnlookersUserRequest;

import java.util.List;

/**
 * Created by zengguoqing on 2016/9/1.
 */
public interface OnlookersUserDao {
    //新增
    int insert(OnlookersUserEntity onlookersUserEntity);

    //获取围观好友ID集合
    List<Long> getMyFriendlist(OnlookersUserRequest request);

    //通过围观用户ID 获取被围观的用户iD
     Long getUserIdByOnlookerUserId(OnlookersUserRequest request);

    //通过围观用户ID 获取围观的行程ID集合
    List<OnlookersUserEntity>  getScheduleIdByOnlookerUserId(OnlookersUserRequest request);
    //是否已经被邀请
    int isHas(OnlookersUserRequest request);

    //修改
    int update(OnlookersUserEntity onlookersUserEntity);
    //通过手机号查询
    List<OnlookersUserEntity> getOnlookersUserByPhone(String phone);

    //通过手机号和用户ID 判断有木有被邀请
    int isHasByPhone(OnlookersUserRequest request);

    //通过围观用户ID 获取围观的行程集合
    Page<ScheduleEntity> getSchedules(OnlookersUserRequest request);
    //通过围观用户ID 获取最新的已经结束的围观
    ScheduleEntity getEndScheduleOne(OnlookersUserRequest request);
    //通过围观用户ID 获取已经结束的围观的行程集合
    Page<ScheduleEntity> getEndSchedules(OnlookersUserRequest request);
}
