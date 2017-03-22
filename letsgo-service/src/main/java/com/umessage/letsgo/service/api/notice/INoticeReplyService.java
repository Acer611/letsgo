package com.umessage.letsgo.service.api.notice;

import com.umessage.letsgo.domain.po.notice.NoticeReplyEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.notice.request.DetailRequest;
import com.umessage.letsgo.domain.vo.notice.respone.NoticeReplyResponse;

public interface INoticeReplyService {
	// 新建通知回复
	int addNoticeReply(NoticeReplyEntity noticeReplyEntity);
	
	// 回复通知
	CommonResponse replyNotice(DetailRequest detailRequest);

	NoticeReplyResponse getNoticeRelayList(Long noticeId, UserEntity user);
}
