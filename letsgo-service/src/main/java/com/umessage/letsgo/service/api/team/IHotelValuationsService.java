package com.umessage.letsgo.service.api.team;

import com.umessage.letsgo.domain.po.journey.HotelEvaluationsEntity;
import com.umessage.letsgo.domain.vo.journey.request.HotelValuationsRequest;
import com.umessage.letsgo.domain.vo.journey.response.vo.HotelValuationsResponse;

import java.util.List;

public interface IHotelValuationsService {
	HotelValuationsResponse selectHotel(HotelValuationsRequest hotelValuationsRequest);

	List<HotelEvaluationsEntity> selectStatis(HotelValuationsRequest h);

	boolean insert(HotelEvaluationsEntity data);
}
