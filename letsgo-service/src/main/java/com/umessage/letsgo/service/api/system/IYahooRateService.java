package com.umessage.letsgo.service.api.system;

import com.umessage.letsgo.domain.po.system.YahooRate;
import com.umessage.letsgo.domain.vo.system.respone.RateResult;

import java.util.List;

public interface IYahooRateService {

    int add(YahooRate yahooRate);

    int update(YahooRate yahooRate);

    /**
     * 根据请求参数，获取以美元为基准的汇率列表
     * @param currencies
     * @return
     */
    List<RateResult> calculationRates(List<String> currencies);

    YahooRate getYahooRate(String id);

    List<YahooRate> getYahooRateList(List<String> Ids);

    int saveAndUpateRate(YahooRate yahooRate);

}
