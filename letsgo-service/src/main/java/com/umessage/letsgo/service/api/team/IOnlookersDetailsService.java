package com.umessage.letsgo.service.api.team;

import com.umessage.letsgo.domain.po.team.OnlookersDetailsEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.team.requset.InviteWatchRequest;
import com.umessage.letsgo.domain.vo.team.requset.OnlookersDetailsRequest;
import com.umessage.letsgo.domain.vo.team.requset.ReplyLikeRequest;
import com.umessage.letsgo.domain.vo.team.respone.OnlookersDetailsInfo;
import com.umessage.letsgo.domain.vo.team.respone.RedPointResponse;

import java.util.List;

/**
 * Created by zengguoqing on 2016/9/5.
 */
public interface IOnlookersDetailsService {
    //获取围观小红点
    RedPointResponse getWatchRedPoint(OnlookersDetailsRequest request);
    List<OnlookersDetailsInfo> getLastReply(Long userId, Long scheduleId);

    //回复点赞接口
    CommonResponse  replyLikes( ReplyLikeRequest request);

    //邀请围观接口
    CommonResponse inviteWatch(InviteWatchRequest request);

    //判断是否已经点赞
    boolean isLike(ReplyLikeRequest request);

    //删掉已经点赞的数据
    int deleteLike(ReplyLikeRequest request);

    //修改查看状态
    int updateStatus(OnlookersDetailsEntity onlookersDetailsEntity);
}
