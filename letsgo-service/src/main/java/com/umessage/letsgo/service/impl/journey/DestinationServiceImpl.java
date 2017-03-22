package com.umessage.letsgo.service.impl.journey;

import com.umessage.letsgo.core.timezone.TimeZoneUtil;
import com.umessage.letsgo.dao.journey.DestinationDao;
import com.umessage.letsgo.domain.po.journey.DestinationEntity;
import com.umessage.letsgo.service.api.journey.IDestinationService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wendy on 2016/7/11.
 */
@Service
public class DestinationServiceImpl implements IDestinationService {
    @Resource
    private DestinationDao destinationDao;

    @Override
    public int add(DestinationEntity destinationEntity) {
        destinationEntity.setCreateTime(new Date());
        destinationEntity.setVersion(0l);
        return destinationDao.insert(destinationEntity);
    }

    @Override
    public DestinationEntity getDestinationByDest(String destination) {
        DestinationEntity destinationEntity = destinationDao.selectWithDest(destination);
        if (destinationEntity == null) {
            return new DestinationEntity();
        }
        return destinationEntity;
    }

    @Override
    public List<DestinationEntity> getDestinationByDestList(List<String> destinations) {
        List<DestinationEntity> destinationEntityList = destinationDao.selectWithMultiDest(destinations);
        if (CollectionUtils.isEmpty(destinationEntityList)) {
            return new ArrayList<>();
        }
        return destinationEntityList;
    }

    /**
     * 调用谷歌接口，获取数据，保存到本地数据库
     * @param destList
     */
    @Override
    public void save(List<String> destList) {
        for (String dest : destList) {
            DestinationEntity destinationEntity = getDestinationByDest(dest);
            if (destinationEntity.getId() == null) {
                DestinationEntity destination = new DestinationEntity();
                Map<String, Object> map = new HashMap<>();

                // 调用Google接口
                try {
                    map = TimeZoneUtil.getInfortionWithCity(dest);
                    destination.setDestination(dest);
                    destination.setCity((String) map.get("city"));
                    destination.setCountry((String) map.get("country"));
                    destination.setLat((Double) map.get("lat"));
                    destination.setLng((Double) map.get("lng"));
                    destination.setTimezone((String) map.get("timezone"));
                    // 保存到数据库
                    add(destination);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
