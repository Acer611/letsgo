package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.po.journey.DestinationEntity;

import java.util.List;

/**
 * Created by wendy on 2016/7/11.
 */
public interface IDestinationService {
    int add(DestinationEntity destinationEntity);

    DestinationEntity getDestinationByDest(String destination);

    List<DestinationEntity> getDestinationByDestList(List<String> destinations);

    void save(List<String> destList);
}
