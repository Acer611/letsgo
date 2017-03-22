package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.po.journey.TallyBookEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.StatisticsConsumptionResponse;
import com.umessage.letsgo.domain.vo.journey.response.TallyBookResponse;

public interface ITallyBookService {

	CommonResponse addConsumptionRecord(TallyBookEntity entity);

	TallyBookResponse getConsumptionList(String txGroupId, Integer typeId, Long id);

	StatisticsConsumptionResponse statisticsConsumptionList(String txGroupId, Long userId);

}
