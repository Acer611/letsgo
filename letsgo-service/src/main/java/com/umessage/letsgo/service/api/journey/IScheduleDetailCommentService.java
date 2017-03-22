package com.umessage.letsgo.service.api.journey;


import com.umessage.letsgo.domain.po.journey.ScheduleDetailCommentEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailIdUserIdEntity;
import com.umessage.letsgo.domain.po.journey.ScoreRecordEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleDetailCommentRequest;
import com.umessage.letsgo.domain.vo.journey.response.DetailCommentResponse;

import java.util.List;

public interface IScheduleDetailCommentService {
	// 新增每日行程
	CommonResponse submitJournarComment(ScheduleDetailCommentRequest scheduleDetailCommentRequest)throws Exception;

	DetailCommentResponse selectComment(Long scheduleDetailId, Long userId);

	boolean setSatisfiedStatusForUserId(ScheduleDetailIdUserIdEntity scheduleDetailIdUserIdEntity);

	ScheduleDetailCommentEntity getComment(Long scheduleDetailId, Long userId);

	boolean sendEveryDayComment(MemberEntity memberEntity);

	List<ScoreRecordEntity> selectAllByScheduleDate();

	DetailCommentResponse selectCommentDefault(Long scheduleDetailId, Long userId);
}
