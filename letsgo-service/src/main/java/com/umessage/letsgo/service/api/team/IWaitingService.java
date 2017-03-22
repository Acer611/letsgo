package com.umessage.letsgo.service.api.team;

import com.umessage.letsgo.domain.po.team.WaitingEntity;
import com.umessage.letsgo.domain.vo.team.respone.WaitingResponse;

import java.util.List;

/**
 * Created by wendy on 2016/9/2.
 */
public interface IWaitingService {
    // 新增
    int add(WaitingEntity waitingEntity);

    // 更新
    int update(WaitingEntity waitingEntity);

    // 获取列表
    WaitingEntity getWaitingList(Long userId);

    // 保存排期
    WaitingResponse saveWaiting(List<String> dates, int waitStatus, Long userId);

    // 获取排期
    WaitingResponse getWaiting(Long userId);

    //通过日期判断该用户在该日期是否空闲
    boolean hasTime(Long userId,String time);
   //通过用户ID 获取领队的 排期 忙碌时间
    List<String>  getWorkList(Long userId);
}
