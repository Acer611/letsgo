package com.umessage.letsgo.service.impl.system;

import com.umessage.letsgo.dao.system.NationDictionaryDao;
import com.umessage.letsgo.domain.po.system.NationDictionaryEntity;
import com.umessage.letsgo.domain.po.system.YahooRate;
import com.umessage.letsgo.domain.vo.system.respone.NationDictionaryResponse;
import com.umessage.letsgo.service.api.system.INationDictionaryService;
import com.umessage.letsgo.service.api.system.IYahooRateService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 2016/6/6.
 */
@Service
public class NationDictionaryServiceServiceImpl implements INationDictionaryService {
    @Resource
    private NationDictionaryDao nationDictionaryDao;
    @Resource
    private IYahooRateService yahooRateService;

    @Override
    public List<NationDictionaryEntity> getAllNationDictionaryEntity() {
        List<NationDictionaryEntity> entities = nationDictionaryDao.selectAll();

        if (CollectionUtils.isEmpty(entities)) {
            return new ArrayList<>();
        }

        return entities;
    }

    @Override
    public List<NationDictionaryEntity> getHotNationDictionaryEntity() {
        List<NationDictionaryEntity> entities = nationDictionaryDao.selectHot();
        if (CollectionUtils.isEmpty(entities)) {
            return new ArrayList<>();
        }

        return entities;
    }

    @Override
    public NationDictionaryEntity getNationDictionaryEntity(String code) {
        NationDictionaryEntity nationDictionaryEntity = nationDictionaryDao.selectWithCode(code);
        if (nationDictionaryEntity == null) {
            return new NationDictionaryEntity();
        }
        return nationDictionaryEntity;
    }

    private List<NationDictionaryEntity> setRateToNation(List<NationDictionaryEntity> nations) {
        List<String> rateIds = new ArrayList<>();
        for (NationDictionaryEntity nation : nations) {
            String rateId = "USD" + nation.getCode();
            rateIds.add(rateId);
        }

        List<YahooRate> rates = yahooRateService.getYahooRateList(rateIds);

        for (YahooRate rate : rates) {
            for (NationDictionaryEntity nation : nations) {
                if (("USD" + nation.getCode()).equals(rate.getId())) {
                    nation.setRate(rate.getRate());
                }
            }
        }
        return null;
    }

    @Override
    public NationDictionaryResponse getNationDictionaryList() {
        List<NationDictionaryEntity> entities = this.getAllNationDictionaryEntity();
        // 获取对应的汇率
        setRateToNation(entities);
        List<NationDictionaryEntity> hotList = this.getHotNationDictionaryEntity();
        // 获取对应的汇率
        setRateToNation(hotList);

        NationDictionaryResponse response = new NationDictionaryResponse();
        response.setNationDictionaryList(entities);
        response.setHotNationDictionaryList(hotList);
        return response;
    }
}
