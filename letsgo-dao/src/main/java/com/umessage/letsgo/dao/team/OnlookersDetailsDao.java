package com.umessage.letsgo.dao.team;

import com.umessage.letsgo.domain.po.team.OnlookersDetailsEntity;
import com.umessage.letsgo.domain.vo.team.requset.OnlookersDetailsRequest;

import com.umessage.letsgo.domain.vo.team.requset.ReplyLikeRequest;

import com.umessage.letsgo.domain.vo.team.respone.OnlookersDetailsInfo;

import com.umessage.letsgo.domain.vo.team.respone.OnlookersDetailsResponse;

import java.util.List;

/**
 * Created by zengguoqing on 2016/9/1.
 */
public interface OnlookersDetailsDao {
    //获取围观小红点
    int getWatchRedPoint(OnlookersDetailsRequest request);
    List<OnlookersDetailsInfo> getLastReply(OnlookersDetailsEntity onlookersDetailsEntity);

    int updateStatus(OnlookersDetailsEntity onlookersDetailsEntity);

    //回复点赞添加
    int insert(OnlookersDetailsEntity onlookersDetailsEntity);
    //判断是否已经点赞
    int isLike(ReplyLikeRequest request);
    //删掉已经点赞的数据
    int deleteLike(ReplyLikeRequest request);
}
