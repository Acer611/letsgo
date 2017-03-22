package com.umessage.letsgo.service.api.system;

import com.umessage.letsgo.domain.po.system.AirportEntity;

import java.util.List;

/**
 * Created by wendy on 2016/8/15.
 */
public interface IAirportService {
    public List<AirportEntity> getAirportList(String query);
}
