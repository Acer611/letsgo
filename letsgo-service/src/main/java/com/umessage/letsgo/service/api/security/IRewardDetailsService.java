package com.umessage.letsgo.service.api.security;

import com.umessage.letsgo.domain.po.security.RewardDetailsEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wendy on 2016/8/29.
 */
public interface IRewardDetailsService {

    int add(RewardDetailsEntity rewardDetailsEntity);

    int update(RewardDetailsEntity rewardDetailsEntity);

    int getRewardDetailsCount(Long userId);

    // 根据UserID获取当前用户的奖励列表
    List<RewardDetailsEntity> getRewardDetailsList(Long userId);

    Map<String, List<RewardDetailsEntity>> getRewardDetailsByUser(Long userId, int pageNum, int pageSiz);
    //根据用户ID和奖励状态获取奖励明细列表
    List<RewardDetailsEntity> selectInviteRewardByUserId(Long userId, Long fromId, Integer rewardType, Integer isInto, Date startDate, Date endDate, Long teamId, Integer rewardFrom);
}
