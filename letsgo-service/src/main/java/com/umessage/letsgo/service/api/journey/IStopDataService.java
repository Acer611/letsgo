package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.vo.journey.response.StopDataResponse;

/**
 * Created by wendy on 2016/8/25.
 */
public interface IStopDataService {

    public StopDataResponse getStopData(Long id);
}
