package com.umessage.letsgo.dao.team;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.team.OnlookersEntity;
import com.umessage.letsgo.domain.vo.team.requset.OnlookersRequest;
import com.umessage.letsgo.domain.vo.team.respone.ReplyInfo;
import com.umessage.letsgo.domain.vo.team.respone.WatchMessageResponse;

import java.util.List;

/**
 * Created by zengguoqing on 2016/9/1.
 */
public interface OnlookersDao {
    void sendOnlookers(OnlookersEntity onlookersEntity);
   //发布围观
   Page<WatchMessageResponse>  getWatchOwner(OnlookersRequest onlookersRequest);
   //围观别人
   Page<WatchMessageResponse>  getWatchOther(OnlookersRequest onlookersRequest);

    //获取最新消息
    List<OnlookersEntity> getSystemMess(Long scheduleId);

    //发布围观的用户的 围观明细
    List<ReplyInfo> getWatchOwner2(OnlookersRequest onlookersRequest);

    //围观别人的用户的 围观明细
    List<ReplyInfo> getWatchOther2(OnlookersRequest onlookersRequest);

    //通过ID获取围观
    OnlookersEntity getOnlookersById(Long id);

}
