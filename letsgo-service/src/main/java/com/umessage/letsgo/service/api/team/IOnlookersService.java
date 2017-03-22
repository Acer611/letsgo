package com.umessage.letsgo.service.api.team;

import com.umessage.letsgo.domain.po.team.OnlookersEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.team.respone.OnlookersResponse;

/**
 * Created by zengguoqing on 2016/9/2.
 */
public interface IOnlookersService {
    CommonResponse sendOnlookers(Long scheduleId,Long userId,String message, String imgUrl,String thumbnailUrl,String wide,String height);

    //获取围观和回复信息【游客端】
     OnlookersResponse getWatch(Long userId, Long scheduleId,Integer type,int pageNum,int pageSize,Long onlookerUserId);

    //通过ID获取围观
    OnlookersEntity getOnlookersById(Long id);


}
