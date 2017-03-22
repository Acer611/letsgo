package com.umessage.letsgo.service.impl.journey;


import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.dao.journey.TravelNotesDao;
import com.umessage.letsgo.dao.journey.TravelNotesDetailCommentDao;
import com.umessage.letsgo.dao.journey.TravelNotesDetailDao;
import com.umessage.letsgo.domain.po.journey.TravelNotesDetailCommentEntity;
import com.umessage.letsgo.domain.po.journey.TravelNotesDetailEntity;
import com.umessage.letsgo.domain.po.journey.TravelNotesEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.request.TravelNotesDetailRequest;
import com.umessage.letsgo.domain.vo.journey.response.TravelNotesDetailCommentResponse;
import com.umessage.letsgo.domain.vo.journey.response.TravelNotesDetailsResponse;
import com.umessage.letsgo.domain.vo.journey.response.TravelsListResponse;
import com.umessage.letsgo.service.api.journey.ITravelNotesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class TravelNotesServiceImpl implements ITravelNotesService {

    @Resource
    private TravelNotesDetailDao travelNotesDetailDao;

    @Resource
    private TravelNotesDao travelNotesDao;

    @Resource
    private TravelNotesDetailCommentDao travelNotesDetailCommentDao;
    
	@Override
	public TravelsListResponse getTravelsList(long userId,String txGroupId){
		TravelsListResponse travelsListResponse = travelNotesDetailDao.getTravelsList(userId,txGroupId);
		if(travelsListResponse==null){
			travelsListResponse = new TravelsListResponse();
			travelsListResponse.setRetCode(ErrorConstant.INTERNAL_SERVER_ERROR);
			travelsListResponse.setRetMsg("行程不存在");
		}else{
			TravelNotesEntity travelNotesEntity = travelNotesDao.selectByUserIdAndTxGroupId(userId,txGroupId);
			if(travelNotesEntity==null){
				travelNotesEntity = new TravelNotesEntity();
				travelNotesEntity.setCreateTime(new Date());
				travelNotesEntity.setScheduleId(travelsListResponse.getScheduleId());
				travelNotesEntity.setUserId(userId);
				travelNotesEntity.setVersion(1l);
				travelNotesDao.insert(travelNotesEntity);
			}else{
				travelsListResponse.setScheduleImgurl(travelNotesEntity.getScheduleImgurl());
				travelsListResponse.setTitle(travelNotesEntity.getTitle());
			}
			travelsListResponse.setTravelNotesId(travelNotesEntity.getId());
		}
		return travelsListResponse;
	}

	@Override
	public CommonResponse updateTravelNotes(Long travelNotesId,String scheduleImgurl, String title)throws Exception{
		TravelNotesEntity travelNotesEntity = travelNotesDao.select(travelNotesId);
		travelNotesEntity.setScheduleImgurl(scheduleImgurl);
		travelNotesEntity.setTitle(title);
		travelNotesEntity.setUpdateTime(new Date());
		travelNotesDao.update(travelNotesEntity);
		return new CommonResponse();
	}

	@Override
	public TravelNotesDetailsResponse getOneScheduleDetail(long scheduleDetailId) {
		TravelNotesDetailsResponse detailsResponse = travelNotesDetailDao.getOneScheduleDetail(scheduleDetailId);
		detailsResponse.setScheduleDetaiImgurl(null);// TODO 查询行程图片
		return detailsResponse;
	}

	@Override
	public CommonResponse saveOneTravel(TravelNotesDetailRequest travelNoteRequest) {
		Date createDate = new Date();
		TravelNotesDetailEntity travelNotesDetailEntity = new TravelNotesDetailEntity();
		travelNotesDetailEntity.setScheduleDetaiIid(travelNoteRequest.getScheduleDetaiIid());
		travelNotesDetailEntity.setScheduleDetaiImgurl(travelNoteRequest.getScheduleDetaiImgurl());
		travelNotesDetailEntity.setTravelNotesId(travelNoteRequest.getTravelNotesId());
		travelNotesDetailEntity.setCreateTime(createDate);
		travelNotesDetailEntity.setVersion(1l);
		travelNotesDetailEntity.setWeather("");// TODO 天气获取
		travelNotesDetailDao.insert(travelNotesDetailEntity);
		
		for (TravelNotesDetailCommentEntity commentEntity : travelNoteRequest.getCommentEntities()) {
			commentEntity.setCreateTime(createDate);
			commentEntity.setTravelNotesDetailId(travelNotesDetailEntity.getId());
			commentEntity.setVersion(1l);
			travelNotesDetailCommentDao.insert(commentEntity);
		}
		
		return new CommonResponse();
	}
    
    public TravelNotesDetailCommentResponse selectTravelNotesComment(Long travelNotesId){
    	
    	return travelNotesDao.selectTravelNotesComment(travelNotesId);
    }
    
}
