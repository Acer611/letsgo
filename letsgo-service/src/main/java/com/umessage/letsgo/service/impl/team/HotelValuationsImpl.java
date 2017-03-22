package com.umessage.letsgo.service.impl.team;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.umessage.letsgo.dao.team.HotelValuationsDao;
import com.umessage.letsgo.domain.po.journey.HotelEvaluationsEntity;
import com.umessage.letsgo.domain.vo.journey.request.HotelValuationsRequest;
import com.umessage.letsgo.domain.vo.journey.response.vo.HotelValuationsResponse;
import com.umessage.letsgo.service.api.team.IHotelValuationsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengguoqing on 2016/8/30.
 */
@Service
public class HotelValuationsImpl implements IHotelValuationsService {
    @Resource
    private HotelValuationsDao hotelValuationsDao;
    @Override
    public HotelValuationsResponse selectHotel(HotelValuationsRequest hotelValuationsRequest) {
        HotelValuationsResponse h=new HotelValuationsResponse();
        PageHelper.startPage(hotelValuationsRequest.getPageNum(),hotelValuationsRequest.getPageSize());
        Page<HotelEvaluationsEntity> list=hotelValuationsDao.selectHotel(hotelValuationsRequest);
        h.setTotals(list.getTotal());
        h.setPage(list);
        h.setPages(list.getPages());
        return h;
    }

    @Override
    public List<HotelEvaluationsEntity> selectStatis(HotelValuationsRequest h) {
        return hotelValuationsDao.selectStatis(h);
    }
    @Transactional
    @Override
    public boolean insert(HotelEvaluationsEntity data) {
        hotelValuationsDao.insert(data);
        return true;
    }
}
