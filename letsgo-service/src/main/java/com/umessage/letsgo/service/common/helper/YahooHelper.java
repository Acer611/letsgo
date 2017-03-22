package com.umessage.letsgo.service.common.helper;

import com.umessage.letsgo.domain.po.system.RegionEntity;
import com.umessage.letsgo.domain.po.system.YahooRate;
import com.umessage.letsgo.domain.po.system.YahooWeather;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class YahooHelper {
	private static String base_url = "https://query.yahooapis.com/v1/public/yql";

	private static Logger logger = Logger.getLogger(YahooHelper.class);

	/**
	 * 请求雅虎汇率
	 * @param restTemplate
	 * @param countrys
     * @return
     */
	public static List<YahooRate> queryYahooRate(RestTemplate restTemplate, List<String> countrys){
		String url = getYahooRateURL(list2String(countrys));
		logger.info("雅虎汇率链接" + url);

		String result = restTemplate.getForObject(url, String.class);

		List<YahooRate> rateList = spiltRateResult(result);
		return rateList;
	}

	/**
	 * 请求雅虎天气
	 * @param restTemplate
	 * @param region
     * @return
     */
	public static List<YahooWeather> queryYahooWeather(RestTemplate restTemplate, RegionEntity region) {
		String city = region.getAlias();
		String url = "";
		if (region.getLat() != null && region.getLng() != null) {
			url = getYahooWeatherURL(region.getLat(), region.getLng());
		} else {
			// 如果是海外城市, 则优先用英文
			if (!"10100".equals(region.getParentid()) &&
					region.getAreaengname() != null) {
				city = region.getAreaengname();
			}

			url = getYahooWeatherURL("\"" + city + "\"");
		}

		logger.info("雅虎天气链接" + url);

		String result = restTemplate.getForObject(url, String.class);
		List<YahooWeather> weathers = spiltWeatherResult(result, city);

		return weathers;
	}

//	public static List<YahooWeather> queryYahooWeather(RestTemplate restTemplate, List<DestinationEntity> countrys){
//		if (CollectionUtils.isEmpty(countrys)) {
//			return new ArrayList<>();
//		}
//		List<String> cities = new ArrayList<>();
//		for (DestinationEntity destinationEntity : countrys) {
//			cities.add(destinationEntity.getCity());
//		}
//
//		// 去重
//		List<String> cityList = new ArrayList<>(new HashSet(cities));
//
//		List<YahooWeather> weathers = new ArrayList<>();
//		if (!CollectionUtils.isEmpty(cities)) {
//
//			for (String city : cityList) {
//				String url = getYahooWeatherURL("\"" + city + "\"");
//				logger.info("雅虎天气链接" + url);
//
//				String result = restTemplate.getForObject(url, String.class);
//				List<YahooWeather> tempWeathers = spiltWeatherResult(result, city);
//
//				weathers.addAll(tempWeathers);	// 把每个城市的未来十天的天气
//			}
//		}
//
//		return weathers;
//	}

	private static List<String> addDoubleQuote(List<String> cities) {
		List<String> list = new ArrayList<>();
		for (String str : cities) {
			list.add("\"" + str + "\"");
		}
		return list;
	}

	private static String list2String(List<String> list) {
		String string = org.apache.commons.lang.StringUtils.join(list.toArray(), ",");
		return string;
	}

	private static List<String> spiltString(String result) {
		List<String> cityList = new ArrayList<>();
		String[] cities = result.split(",");
		for (int i = 0; i < cities.length; i++) {
			cityList.add(cities[i].replaceAll("\"", ""));
		}
		return cityList;
	}

	private static List<YahooRate> spiltRateResult(String result) {
		JSONObject object = new JSONObject(result);
		JSONObject query = object.getJSONObject("query");
		JSONObject results = query.getJSONObject("results");
		JSONArray rates = results.getJSONArray("rate");
		List<YahooRate> rateList = new ArrayList<>();
		for (int i = 0; i < rates.length(); i++) {
			JSONObject rate = (JSONObject) rates.get(i);
			YahooRate yahooRate = new YahooRate();
			yahooRate.setId(rate.getString("id"));
			yahooRate.setName(rate.getString("Name"));
			yahooRate.setDate(rate.getString("Date"));
			yahooRate.setTime(rate.getString("Time"));
			yahooRate.setRate(rate.getString("Rate"));
			yahooRate.setAsk(rate.getString("Ask"));
			yahooRate.setBid(rate.getString("Bid"));
			rateList.add(yahooRate);
		}
		return rateList;
	}

	private static List<YahooWeather> spiltWeatherResult(String result, String cityName) {
		List<YahooWeather> list = new ArrayList<>();

		JSONObject object = new JSONObject(result);
		JSONObject query = object.getJSONObject("query");
		int count = query.getInt("count");
		if (count == 0) {
			return list;	// 如果采集不到数据，值为null时，返回一个空列表
		} else if (count == 1) {
			JSONObject results = query.getJSONObject("results");
			JSONObject channel = results.getJSONObject("channel");	// 只有一个元素

			// 获取城市名
			JSONObject location = channel.getJSONObject("location");
			String city = location.getString("city");

			JSONObject item = channel.getJSONObject("item");
			JSONArray forecast = item.getJSONArray("forecast");

			for (int i = 0; i < forecast.length(); i++) {
				JSONObject obj = (JSONObject) forecast.get(i);
				YahooWeather weather = new YahooWeather();
				String code = obj.getString("code");
				weather.setCode(obj.getString("code"));
				weather.setDate(changeDate(obj.getString("date")));
				weather.setDay(changeDay(obj.getString("day")));
				weather.setHigh(obj.getString("high"));
				weather.setLow(obj.getString("low"));
				weather.setText(obj.getString("text"));
				weather.setDescn(changeCodeToDescn(code));
				weather.setCityName(cityName);
				weather.setName(city);

				weather.setId(new Date().getTime() + i);

				list.add(weather);
			}

		} else {
			String[] cites = cityName.split(",");
			JSONObject results = query.getJSONObject("results");
			JSONArray array = results.getJSONArray("channel");	// 有多个元素
			for (int i = 0; i < array.length(); i++) {
				JSONObject channel = (JSONObject) array.get(i);

				// 获取城市名
				JSONObject location = channel.getJSONObject("location");
				String city = location.getString("city");

				JSONObject item = channel.getJSONObject("item");
				JSONArray forecast = item.getJSONArray("forecast");
				for (int j = 0; j < forecast.length(); j++) {
					JSONObject obj = (JSONObject) forecast.get(j);
					YahooWeather weather = new YahooWeather();
					String code = obj.getString("code");
					weather.setCode(obj.getString("code"));
					weather.setDate(changeDate(obj.getString("date")));
					weather.setDay(changeDay(obj.getString("day")));
					weather.setHigh(obj.getString("high"));
					weather.setLow(obj.getString("low"));
					weather.setText(obj.getString("text"));
					weather.setDescn(changeCodeToDescn(code));
					weather.setCityName(cites[i]);
					weather.setName(city);

					list.add(weather);
				}
			}
		}

		return list;
	}

	private static String changeDate(String date) {
		String [] dateArr = date.split(" ");
		String month = "";
		switch (dateArr[1]) {
			case "Jan":
				month = "01";
				break;
			case "Feb":
				month = "02";
				break;
			case "Mar":
				month = "03";
				break;
			case "Apr":
				month = "04";
				break;
			case "May":
				month = "05";
				break;
			case "Jun":
				month = "06";
				break;
			case "Jul":
				month = "07";
				break;
			case "Aug":
				month = "08";
				break;
			case "Sep":
				month = "09";
				break;
			case "Oct":
				month = "10";
				break;
			case "Nov":
				month = "11";
				break;
			case "Dec":
				month = "12";
				break;
		}

		return dateArr[2] + "-" + month + "-" + dateArr[0];
	}

	private static String changeDay(String day) {
		String dayStr = "";
		switch (day) {
			case "Mon":
				dayStr = "星期一";
				break;
			case "Tue":
				dayStr = "星期二";
				break;
			case "Wed":
				dayStr = "星期三";
				break;
			case "Thu":
				dayStr = "星期四";
				break;
			case "Fri":
				dayStr = "星期五";
				break;
			case "Sat":
				dayStr = "星期六";
				break;
			case "Sun":
				dayStr = "星期日";
				break;
		}
		return dayStr;
	}

	private static String changeCodeToDescn(String code) {
		Map<String, String> map = new HashMap<>();
		map.put("0", "龙卷风");map.put("20", "多雾");map.put("40", "零星阵雨");
		map.put("1", "热带风暴");map.put("21", "薄雾");map.put("41", "分散阵雪");
		map.put("2", "飓风");map.put("22", "烟");map.put("42", "零星阵雪");
		map.put("3", "强雷暴");map.put("23", "大风");map.put("43", "大雪");
		map.put("4", "雷暴");map.put("24", "有风");map.put("44", "晴间多云");
		map.put("5", "雨夹雪");map.put("25", "冷");map.put("45", "雷阵雨");
		map.put("6", "雨冰雹");map.put("26", "阴天");map.put("46", "阵雪");
		map.put("7", "雨雪");map.put("27", "多云（夜间）");map.put("47", "局部雷阵雨");
		map.put("8", "冻小雨");map.put("28", "多云");map.put("3200", "暂无数据");
		map.put("9", "小雨");map.put("29", "晴间多云（夜间）");
		map.put("10", "冻雨");map.put("30", "晴间多云");
		map.put("11", "阵雨");map.put("31", "晴天（晚上）");
		map.put("12", "阵雨");map.put("32", "晴天");
		map.put("13", "飘雪");map.put("33", "晴朗（夜间）");
		map.put("14", "小阵雪");map.put("34", "晴朗");
		map.put("15", "风雪");map.put("35", "混合雨和冰雹");
		map.put("16", "雪");map.put("36", "热");
		map.put("17", "冰雹");map.put("37", "局部雷暴");
		map.put("18", "雨雪");map.put("38", "零散雷雨");
		map.put("19", "灰尘");map.put("39", "零散雷雨");

		return map.get(code);
	}


	public static String getYahooRateURL(String countrys) {
        String queryKeys = "select * from yahoo.finance.xchange where pair in ({1})";
		if(null != countrys) {
			queryKeys = queryKeys.replace("{1}", countrys);
		}

		return setParamsToMakURL(queryKeys);
	}

	public static String getYahooWeatherURL(String city) {
		String queryKeys = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text in ({1})) and u = 'c'";
		if (city != null) {
			queryKeys = queryKeys.replace("{1}", city);
		}

		return setParamsToMakURL(queryKeys);
	}

	public static String getYahooWeatherURL(Double lat, Double lng) {
		String queryKeys = "select * from weather.forecast where woeid in (SELECT woeid FROM geo.places WHERE text='({1},{2})') and u = 'c'";

		queryKeys = queryKeys.replace("{1}", Double.toString(lat));
		queryKeys = queryKeys.replace("{2}", Double.toString(lng));

		return setParamsToMakURL(queryKeys);
	}

	private static String setParamsToMakURL(String queryKeys) {
		StringBuffer params = new StringBuffer();
		try {
			params.append("q=");
			params.append(queryKeys);
			params.append("&format=json&env=store://datatables.org/alltableswithkeys&callback=");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = base_url;
		url += "?";
		url += params;

		return url;
	}

	public static void main(String[] args) {
		/*String link = getYahooRateURL("\"USD\"");
		System.out.println(link);

		link = getYahooWeatherURL("\"北京\",\"纽约\",\"巴黎\"");
		System.out.println(link);

		String date = DateUtils.date2String("yyyy/MM/dd", new Date());
		System.out.println(date);

		date = changeDate("21 Jun 2016");
		System.out.println(date);*/

		/*List<String> cities = new ArrayList<>();
		cities.add("澳门");
		cities.add("香港");
		cities.add("台湾");

		System.out.println(addDoubleQuote(cities));
		System.out.println(getYahooWeatherURL(list2String(addDoubleQuote(cities))));*/

//		List<DestinationEntity> cities = new ArrayList<>();
//		DestinationEntity destinationEntity1 = new DestinationEntity();
//		destinationEntity1.setCity("北京");
//		cities.add(destinationEntity1);
//
//		DestinationEntity destinationEntity2 = new DestinationEntity();
//		destinationEntity2.setCity("香港");
//		cities.add(destinationEntity2);
//
//		DestinationEntity destinationEntity3 = new DestinationEntity();
//		destinationEntity3.setCity("清迈");
//		cities.add(destinationEntity3);
//
//		DestinationEntity destinationEntity4 = new DestinationEntity();
//		destinationEntity4.setCity("曼谷");
//		cities.add(destinationEntity4);
//
//		RestTemplate restTemplate = new RestTemplate();
//
//		List<YahooWeather> weathers = queryYahooWeather(restTemplate , cities);

		RestTemplate restTemplate = new RestTemplate();

		RegionEntity region1 = new RegionEntity();
		region1.setParentid("10100");
		region1.setAlias("北京");
		List<YahooWeather> weathers = queryYahooWeather(restTemplate , region1);
		System.out.println(weathers.toString());

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		RegionEntity region2 = new RegionEntity();
		region2.setParentid("10100");
		region2.setAlias("香港");
		weathers = queryYahooWeather(restTemplate , region2);
		System.out.println(weathers.toString());

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		RegionEntity region3 = new RegionEntity();
		region3.setParentid("10291");
		region3.setAlias("清迈");
		region3.setAreaengname("CHIANG MAI");
		weathers = queryYahooWeather(restTemplate , region3);
		System.out.println(weathers.toString());

	}
}
