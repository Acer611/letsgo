package com.umessage.letsgo.dao.team;

import com.github.pagehelper.Page;
import com.umessage.letsgo.domain.po.journey.HotelEvaluationsEntity;
import com.umessage.letsgo.domain.vo.journey.request.HotelValuationsRequest;

import java.util.List;

/**
 * Created by zengguoqing on 2016/8/30.
 */
public interface HotelValuationsDao {
    Page<HotelEvaluationsEntity> selectHotel(HotelValuationsRequest hotelValuationsRequest);

    List<HotelEvaluationsEntity> selectStatis(HotelValuationsRequest h);

    void insert(HotelEvaluationsEntity data);
}
