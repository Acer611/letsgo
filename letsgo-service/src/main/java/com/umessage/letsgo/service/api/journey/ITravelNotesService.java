package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.TravelNotesDetailRequest;
import com.umessage.letsgo.domain.vo.journey.response.TravelNotesDetailCommentResponse;
import com.umessage.letsgo.domain.vo.journey.response.TravelNotesDetailsResponse;
import com.umessage.letsgo.domain.vo.journey.response.TravelsListResponse;

public interface ITravelNotesService {
	// 新增每日行程
	TravelsListResponse getTravelsList(long userId, String txGroupId);

	CommonResponse updateTravelNotes(Long travelNotesId, String scheduleImgurl,String title)throws Exception;

	TravelNotesDetailsResponse getOneScheduleDetail(long scheduleDetailId);

	CommonResponse saveOneTravel(TravelNotesDetailRequest travelNoteRequest);
	
	TravelNotesDetailCommentResponse selectTravelNotesComment(Long travelNotesId);
}
