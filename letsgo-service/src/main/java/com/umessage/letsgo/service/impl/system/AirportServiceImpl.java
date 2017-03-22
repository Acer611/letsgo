package com.umessage.letsgo.service.impl.system;

import com.umessage.letsgo.dao.system.AirportDao;
import com.umessage.letsgo.domain.po.system.AirportEntity;
import com.umessage.letsgo.service.api.system.IAirportService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 2016/8/15.
 */
@Service
public class AirportServiceImpl implements IAirportService {

    @Resource
    private AirportDao airportDao;

    /**
     * 获取航班信息
     * @param query
     * @return
     */
    @Override
    public List<AirportEntity> getAirportList(String query) {
        List<AirportEntity> airportEntityList = airportDao.selectLike(query);
        if (CollectionUtils.isEmpty(airportEntityList)) {
            return new ArrayList<>();
        }
        return airportEntityList;
    }
}
