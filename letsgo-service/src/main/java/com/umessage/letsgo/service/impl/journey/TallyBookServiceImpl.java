package com.umessage.letsgo.service.impl.journey;

import com.github.pagehelper.Page;
import com.umessage.letsgo.dao.journey.TallyBookDao;
import com.umessage.letsgo.domain.po.journey.ConsumptionInfoList;
import com.umessage.letsgo.domain.po.journey.StaticsConsumptionInfo;
import com.umessage.letsgo.domain.po.journey.TallyBookEntity;
import com.umessage.letsgo.domain.po.system.YahooRate;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.StatisticsConsumptionResponse;
import com.umessage.letsgo.domain.vo.journey.response.TallyBookResponse;
import com.umessage.letsgo.domain.vo.system.respone.RateResult;
import com.umessage.letsgo.service.api.journey.ITallyBookService;
import com.umessage.letsgo.service.api.system.IYahooRateService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
public class TallyBookServiceImpl implements ITallyBookService {

	@Resource
    private TallyBookDao tallyBookDao;
	@Autowired
	private IYahooRateService yahooRateService;
	
	public CommonResponse addConsumptionRecord(TallyBookEntity entity) {
		//如果当前货币类型不是人民币，计算当前货币兑美元汇率
		Double money = entity.getMoney();
		if(entity!=null && !"CNY".equals(entity.getCurrency().toUpperCase())){
			String currency = entity.getCurrency();
			List<String> list = new ArrayList<>();
			list.add(currency);
			//计算美元兑人民币汇率，等下需要把其他货币转换成兑美元货币后再转成人民币计价
			YahooRate rmbRate = yahooRateService.getYahooRate("USDCNY");
			List<RateResult> rateList = yahooRateService.calculationRates(list);
			if(rateList!=null && rateList.size()>0 && rmbRate!=null){
				RateResult result = rateList.get(0);
				Double rate = result.getRate();
				Double cnyRate = Double.valueOf(rmbRate.getRate());
				//将当前货币转换成人民币金额
				Double equivalentMoney = (money/rate)*cnyRate;
				BigDecimal b = new BigDecimal(equivalentMoney); 
				equivalentMoney = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
				entity.setEquivalentMoney(equivalentMoney);
			}else{
				return new CommonResponse(101,"货币类型不存在"); 
			}
		}else{
			entity.setEquivalentMoney(money);
		}
		entity.setCreateTime(new Date());
		tallyBookDao.insert(entity);
		return new CommonResponse();
	}

	public TallyBookResponse getConsumptionList(String txGroupId,
			Integer typeId, Long userId) {
		TallyBookResponse response = new TallyBookResponse();
		List<TallyBookEntity> tallyEntityList = tallyBookDao.selectConsumptionList(txGroupId,typeId,userId);
		Page<ConsumptionInfoList> consumptionList = transTallyEntityIntoList(tallyEntityList);
		response.setConsumptionList(consumptionList);
		return response;
	}

	public StatisticsConsumptionResponse statisticsConsumptionList(String txGroupId, Long userId) {
		StatisticsConsumptionResponse response = new StatisticsConsumptionResponse();
		List<StaticsConsumptionInfo> totalStaticsConsumption = tallyBookDao.statisticsTotalConsumptionList(txGroupId,userId);
		response.setConsumptionList(totalStaticsConsumption);
		return response;
	}
	
	private Page<ConsumptionInfoList> transTallyEntityIntoList(
			List<TallyBookEntity> tallyEntityList) {
		Map<String, ConsumptionInfoList> map = new HashMap<>();
		Page<ConsumptionInfoList> consumptionList = new Page<ConsumptionInfoList>();
		if(tallyEntityList!=null && tallyEntityList.size()>0){
			for(TallyBookEntity entity : tallyEntityList){
				String date = entity.getDate();
				if(StringUtils.isEmpty(date)){
					continue;
				}
				date = date.substring(0, 10);
				ConsumptionInfoList consumptionInfoList = map.get(date);
				if(consumptionInfoList==null){
					consumptionInfoList = new ConsumptionInfoList();
					consumptionInfoList.setDate(date);
					List<TallyBookEntity> list = new ArrayList<>();
					list.add(entity);
					consumptionInfoList.setConsumption(list);
					map.put(date, consumptionInfoList);
					consumptionList.add(consumptionInfoList);
				}else{
					List<TallyBookEntity> list = consumptionInfoList.getConsumption();
					list.add(entity);
				}
			}
		}
		return consumptionList;
	}

}
