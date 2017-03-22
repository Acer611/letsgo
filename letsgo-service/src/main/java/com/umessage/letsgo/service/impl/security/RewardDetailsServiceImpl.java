package com.umessage.letsgo.service.impl.security;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.dao.security.RewardDetailsDao;
import com.umessage.letsgo.domain.po.security.RewardDetailsEntity;
import com.umessage.letsgo.domain.vo.security.request.RewardDetailsRequest;
import com.umessage.letsgo.service.api.security.IRewardDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wendy on 2016/8/29.
 */
@Service
public class RewardDetailsServiceImpl implements IRewardDetailsService {

    @Resource
    private RewardDetailsDao rewardDetailsDao;

    @Override
    public int add(RewardDetailsEntity rewardDetailsEntity) {
        rewardDetailsEntity.setCreateTime(new Date());
        return rewardDetailsDao.insert(rewardDetailsEntity);
    }

    @Override
    public int update(RewardDetailsEntity rewardDetailsEntity) {
        return rewardDetailsDao.update(rewardDetailsEntity);
    }

    @Override
    public int getRewardDetailsCount(Long userId) {
        return rewardDetailsDao.selectCountWithUserId(userId);
    }

    @Override
    public Page<RewardDetailsEntity> getRewardDetailsList(Long userId) {

        Page<RewardDetailsEntity> rewardDetailsEntityList = rewardDetailsDao.selectWithUserId(userId);
        if (CollectionUtils.isEmpty(rewardDetailsEntityList)) {
            return new Page<>();
        }

        return rewardDetailsEntityList;
    }

    @Override
    public Map<String, List<RewardDetailsEntity>> getRewardDetailsByUser(Long userId, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        Page<RewardDetailsEntity> rewardDetailsEntityList = this.getRewardDetailsList(userId);

        Map<String, List<RewardDetailsEntity>> map = new LinkedHashMap<>();
        for (RewardDetailsEntity reward : rewardDetailsEntityList) {
            String date = DateUtils.date2String("yyyy-MM-dd", reward.getCreateTime());
            if (map.containsKey(date)) {
                List<RewardDetailsEntity> list = map.get(date);
                list.add(reward);
            } else {
                List<RewardDetailsEntity> list = new ArrayList<>();
                list.add(reward);
                map.put(date, list);
            }
        }

        // 处理日期及手机号
        Map<String, List<RewardDetailsEntity>> newMap = this.dealDateAndPhone(map);

        return newMap;
    }

    private Map<String, List<RewardDetailsEntity>> dealDateAndPhone(Map<String, List<RewardDetailsEntity>> map) {
        Map<String, List<RewardDetailsEntity>> newMap = new LinkedHashMap<>();

        for (String key : map.keySet()) {

            // 处理手机号
            for (RewardDetailsEntity r : map.get(key)) {
                String phone = r.getPhone().substring(3);
                phone = phone.substring(0,phone.length()-(phone.substring(3)).length()) + "****" + phone.substring(7);
                r.setPhone(phone);
            }

            // 处理日期
            String today = DateUtils.date2String("yyyy-MM-dd", new Date());
            String yesterday = DateUtils.date2String("yyyy-MM-dd", DateUtils.addDay(new Date(), -1));

            if (today.equals(key)) {
                newMap.put("今天", map.get(key));
            } else if (yesterday.equals(key)) {
                newMap.put("昨天", map.get(key));
            } else {
                newMap.put(key, map.get(key));
            }
        }

        return newMap;
    }

    /**
     * 根据用户ID和奖励状态获取奖励明细列表（邀请人）
     * @param userId
     * @param rewardType
     * @param isInto
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<RewardDetailsEntity> selectInviteRewardByUserId(Long userId, Long fromId, Integer rewardType, Integer isInto, Date startDate, Date endDate, Long teamId, Integer rewardFrom) {
        RewardDetailsRequest request = new RewardDetailsRequest();
        request.setUserId(userId);
        request.setRewardType(rewardType);
        request.setIsInto(isInto);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setTeamId(teamId);
        request.setFromId(fromId);
        request.setRewardFrom(rewardFrom);
        List<RewardDetailsEntity> rewardDetailsEntities = rewardDetailsDao.selectInviteRewardByUserId(request);
        return rewardDetailsEntities;
    }

}
