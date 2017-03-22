package com.umessage.letsgo.service.impl.system;

import com.umessage.letsgo.dao.system.YahooRateDao;
import com.umessage.letsgo.domain.po.system.NationDictionaryEntity;
import com.umessage.letsgo.domain.po.system.YahooRate;
import com.umessage.letsgo.domain.vo.system.respone.RateResult;
import com.umessage.letsgo.service.api.system.INationDictionaryService;
import com.umessage.letsgo.service.api.system.IYahooRateService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class YahooRateServiceImpl implements IYahooRateService {
    @Resource
	private YahooRateDao yahooRateDao;
	@Resource
	private INationDictionaryService nationDictionaryService;

	/**
	 * 转换国家
	 *        在国家前加上准基数
	 *        LTL,NOK转为"USDLTL","USDNOK"
	 * @param countrys
	 * @return
	 */
	private List<String> convertCounrtys(List<String> countrys) {
		String baseKey = "USD";
        List<String> list = new ArrayList<>();
		for(String args : countrys) {
			list.add(baseKey + args);
		}
		return list;
	}

	@Override
	public int add(YahooRate yahooRate) {
		yahooRate.setLastupdatetime(new Date());
		yahooRate.setIsdel(0);
		return yahooRateDao.insert(yahooRate);
	}

	@Override
	public int update(YahooRate yahooRate) {
		yahooRate.setLastupdatetime(new Date());
		return yahooRateDao.update(yahooRate);
	}

	@Override
	public List<RateResult> calculationRates(List<String> currencies) {
		if (CollectionUtils.isEmpty(currencies)) {	// 默认货币换算：人民币，美元，日元，韩元
			currencies = new ArrayList<>();
			currencies.add("CNY");
			currencies.add("USD");
			currencies.add("JPY");
			currencies.add("KRW");
		}

		// 在前面加上基准参数美元 USD
		List<String> list = convertCounrtys(currencies);

		// 从数据库中获取汇率数据
		List<YahooRate> rates = yahooRateDao.selectWithIDs(list);

		List<RateResult> rateList = new ArrayList<>();
		for (String currency : currencies) {
			for (int i=0; i<rates.size(); i++) {
				if (("USD" + currency).equals(rates.get(i).getId())) {
					NationDictionaryEntity entity = nationDictionaryService.getNationDictionaryEntity(currency);

					RateResult rateResult = new RateResult();
					rateResult.setCode(entity.getCode());
					rateResult.setRate(Double.valueOf(rates.get(i).getRate()));
					rateResult.setName(entity.getName());
					rateResult.setNationflagurl(entity.getNationflagurl());
					rateList.add(rateResult);
					break;
				}
			}
		}

		return rateList;
	}

	@Override
	public YahooRate getYahooRate(String id) {
		YahooRate rate = yahooRateDao.select(id);
		if (rate == null) {
			return new YahooRate();
		}
		return rate;
	}

	@Override
	public List<YahooRate> getYahooRateList(List<String> ids) {
		List<YahooRate> rates = yahooRateDao.selectWithIDs(ids);
		if (CollectionUtils.isEmpty(rates)) {
			return Collections.emptyList();
		}
		return rates;
	}

	/**
	 * 保存及更新雅虎汇率
	 * @return
     */
	@Override
	public int saveAndUpateRate(YahooRate yahooRate) {
		YahooRate rate = this.getYahooRate(yahooRate.getId());
		if (rate.getId() == null) {
			this.add(yahooRate);
		} else {
			rate.setRate(yahooRate.getRate());
			rate.setDate(yahooRate.getDate());
			rate.setTime(yahooRate.getTime());
			rate.setAsk(yahooRate.getAsk());
			rate.setBid(yahooRate.getBid());
			this.update(rate);
		}

		return 1;
	}


}
