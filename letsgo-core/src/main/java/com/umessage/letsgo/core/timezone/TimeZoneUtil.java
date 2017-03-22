package com.umessage.letsgo.core.timezone;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.OverDailyLimitException;
import com.google.maps.errors.OverQueryLimitException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.utils.DateUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/5/25.
 */
public class TimeZoneUtil {
    protected static final Logger logger = LoggerFactory.getLogger(TimeZoneUtil.class);

    private static LatLng getLocation(String cityName, GeoApiContext context) throws Exception {
        GeocodingResult[] results = new GeocodingResult[0];
        GeoApiContextManager apiContextManager = GeoApiContextManager.getInstance();
        try {
            results = GeocodingApi.geocode(context, cityName).await();
        } catch (OverDailyLimitException e){
            apiContextManager.invalidGeoApiContext(context);
            logger.error("apiKey: {}, 超出每日流量", apiContextManager.getGeoApiKey(context) , e);
            //e.printStackTrace();
        } catch (OverQueryLimitException e) {
            apiContextManager.invalidGeoApiContext(context);
            logger.error("apiKey: {}, 超出查询限制", apiContextManager.getGeoApiKey(context) , e);
            //e.printStackTrace();
        }

        if (ArrayUtils.isEmpty(results) || results[0].geometry == null || results[0].geometry.location == null){
            throw new BusinessException(ErrorConstant.NOT_FOUND, "未找到对应城市的经纬度");
        }

        System.out.println(results[0].formattedAddress);
        System.out.println(results[0].addressComponents[0].shortName);
        System.out.println(results[0].geometry.location);

        LatLng location = results[0].geometry.location;
        return location;
    }

    public static LatLng getLocationWithCity(String cityName) throws Exception {
        GeoApiContext context = GeoApiContextManager.getInstance().getGeoApiContext();
        return getLocation(cityName, context);
    }

    /*
    public static LatLng getLocationWithCity(String cityName, String apiKey) throws Exception {
        GeoApiContext context = getGeoApiContext(apiKey);
        return getLocation(cityName, context);
    }
    */

    public static TimeZone getTimeZoneWithCoordinate(double lat, double lng) {
        String tzId = TimezoneMapper.latLngToTimezoneString(lat, lng);
        TimeZone timeZone = TimeZone.getTimeZone(tzId);
        return  timeZone;
    }

    public static TimeZone getTimeZoneWithCity(String cityName) throws Exception {
        LatLng location = getLocationWithCity(cityName);

        //TimeZone timeZone  = TimeZoneApi.getTimeZone(context, location).await();
        return  getTimeZoneWithCoordinate(location.lat, location.lng);
    }

    public static String getCityNameWithCoordinate(double lat, double lng) throws Exception {
        GeoApiContext context = GeoApiContextManager.getInstance().getGeoApiContext();

        LatLng latLng = new LatLng(lat, lng);
        GeocodingResult[] results = new GeocodingResult[0];
        GeoApiContextManager apiContextManager = GeoApiContextManager.getInstance();
        try {
            results = GeocodingApi.reverseGeocode(context, latLng).await();
        } catch (OverDailyLimitException e){
            apiContextManager.invalidGeoApiContext(context);
            logger.error("apiKey: {}, 超出每日流量", apiContextManager.getGeoApiKey(context) , e);
            //e.printStackTrace();
        } catch (OverQueryLimitException e) {
            apiContextManager.invalidGeoApiContext(context);
            logger.error("apiKey: {}, 超出查询限制", apiContextManager.getGeoApiKey(context) , e);
            //e.printStackTrace();
        }

        if (ArrayUtils.isEmpty(results) || ArrayUtils.isEmpty(results[0].addressComponents)){
            throw new BusinessException(ErrorConstant.NOT_FOUND, "未找到经纬度对应的城市");
        }

        AddressComponent[] addersses = results[0].addressComponents;
        String address = "";

        for (AddressComponent addr : addersses) {
            AddressComponentType type = addr.types[0];
            if ("LOCALITY".equals(type.toString())) {
                address = addr.shortName;
                break;
            }
        }

        if (address.contains("市")) {
            int index = address.indexOf("市");
            address = address.substring(0, index);
        }

        return address;
    }

    public static Map<String, Object> getInfortionWithCity(String cityName) throws Exception {
        GeoApiContext context = GeoApiContextManager.getInstance().getGeoApiContext();
        GeocodingResult[] results = new GeocodingResult[0];
        GeoApiContextManager apiContextManager = GeoApiContextManager.getInstance();
        try {
            results = GeocodingApi.geocode(context, cityName).await();
        } catch (OverDailyLimitException e){
            apiContextManager.invalidGeoApiContext(context);
            logger.error("apiKey: {}, 超出每日流量", apiContextManager.getGeoApiKey(context) , e);
            //e.printStackTrace();
        } catch (OverQueryLimitException e) {
            apiContextManager.invalidGeoApiContext(context);
            logger.error("apiKey: {}, 超出查询限制", apiContextManager.getGeoApiKey(context) , e);
            //e.printStackTrace();
        }

        if (ArrayUtils.isEmpty(results) || results[0].geometry == null || results[0].geometry.location == null){
            throw new BusinessException(ErrorConstant.NOT_FOUND, "未找到对应城市的经纬度");
        }

        Map<String, Object> map = new HashMap<>();

        // 获取城市和国家
        AddressComponent[] addressComponents = results[0].addressComponents;
        if (addressComponents.length > 0 && addressComponents.length < 2) {
            map.put("city", addressComponents[0].longName);
            map.put("country", addressComponents[0].longName);
        } else {
            for (AddressComponent addressComponent : addressComponents) {
                String type = addressComponent.types[0].toString();
                String longName = addressComponent.longName;
                if ("LOCALITY".equals(type)) {
                    if (longName.contains("市")) {
                        int index = longName.indexOf("市");
                        longName = longName.substring(0, index);
                    }
                    map.put("city", longName);
                } else {
                    map.put("city", cityName);
                }
                if ("COUNTRY".equals(type)) {
                    map.put("country", longName);
                }
            }
        }

        // 获取经纬度
        LatLng location = results[0].geometry.location;
        map.put("lat", location.lat);
        map.put("lng", location.lng);

        // 获取时区
        String tzId = TimezoneMapper.latLngToTimezoneString(location.lat, location.lng);
        map.put("timezone", tzId);

        return map;
    }

    /**
     * 统一中国所有地区的时区，统一使用上海时区
     * @param zone
     * @return
     */
    public static TimeZone changeTimeZone(String zone) {
        TimeZone timezone = TimeZone.getTimeZone(zone);
        switch (zone) {
            case "Asia/Shanghai" :
                timezone = TimeZone.getTimeZone("Asia/Shanghai");
                break;
            case "Asia/Chongqing" :
                timezone = TimeZone.getTimeZone("Asia/Shanghai");
                break;
            case "Asia/Harbin" :
                timezone = TimeZone.getTimeZone("Asia/Shanghai");
                break;
            case "Asia/Urumqi" :
                timezone = TimeZone.getTimeZone("Asia/Shanghai");
                break;
            case "Asia/Kashgar" :
                timezone = TimeZone.getTimeZone("Asia/Shanghai");
                break;
            default:
                timezone = TimeZone.getTimeZone(zone);
        }

        return timezone;
    }

    public static void getCurrentTime(TimeZone timeZone) {
//        TimeZone time = TimeZone.getTimeZone("GMT+8"); //设置为东八区
//        time = TimeZone.getDefault();// 这个是国际化所用的
        System.out.println(timeZone);
        TimeZone.setDefault(timeZone);// 设置时区
        Calendar calendar = Calendar.getInstance();// 获取实例
        Date date = calendar.getTime(); //获取Date对象

        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//构造格式化模板
        String str = format1.format(date);//对象进行格式化，获取字符串格式的输出
        System.out.println("Date:" + str);
    }

    public static void convertZoneTime(TimeZone timeZone){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a");
        Date date = new Date();
        TimeZone tz = TimeZone.getDefault();

        // From TimeZone Asia/Singapore
        //System.out.println("TimeZone : " + tz.getID() + " - " + tz.getDisplayName());
        //System.out.println("TimeZone : " + tz);
        //System.out.println("Date : " + formatter.format(date));

        // To TimeZone America/New_York
        SimpleDateFormat sdfAmerica = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a");
        sdfAmerica.setTimeZone(timeZone);

        String sDateInAmerica = sdfAmerica.format(date); // Convert to String first
        Date dateInAmerica = null;
        try {
            dateInAmerica = formatter.parse(sDateInAmerica);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("\nTimeZone : " + timeZone.getID() +
                " - " + timeZone.getDisplayName());
        System.out.println("TimeZone : " + timeZone);
        System.out.println("Date (String) : " + sDateInAmerica);
        System.out.println("Date (Object) : " + formatter.format(dateInAmerica));
        System.out.println();
    }

    /**
     * 将指定系统时间转换为对应时区时间
     */
    public static Date getTimeZoneDate(Date date,TimeZone newZone) {
        // 系统时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(newZone);

        // 时区时间
        Calendar cal = Calendar.getInstance();
        cal.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),calendar.get(Calendar.SECOND));
        return cal.getTime();
    }

    /**
     * 将指定时区的时间转换为对应系统时间
     */
    public static Date getSystemDateByTimeZone(Date date, TimeZone zone){
        // 系统时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 时区时间
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(zone);
        cal.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),calendar.get(Calendar.SECOND));
        return cal.getTime();
    }

    public static void main(String args[]) throws Exception {
        /*Map<String,Object> map = getInfortionWithCity("香港");
        for (String key : map.keySet()) {
            System.out.println(map.get(key));
        }*/

        /*getTimeZoneWithCoordinate(40.7127837000,-74.0059413000);*/

        // System.out.println(getInfortionWithCity("东京"));

        /*TimeZone timeZone = getTimeZoneWithCity("纽约");
        convertZoneTime(timeZone);

        getCityNameWithCoordinate(40.712784,-74.005941);*/

        /*String cityName = getAddressWithCity("北戴河");
        cityName = getAddressWithCity("香港");
        cityName = getAddressWithCity("北京");
        cityName = getAddressWithCity("纽约");
        cityName = getAddressWithCity("北戴河");
        if (cityName.contains("市")) {
            int index = cityName.indexOf("市");
            cityName = cityName.substring(0, index);
        }
        System.out.println(cityName);*/

        /*TimeZone timeZone = getTimeZoneWithCity("武汉");
        convertZoneTime(timeZone);

        timeZone = getTimeZoneWithCity("昆明");
        convertZoneTime(timeZone);

        timeZone = getTimeZoneWithCity("拉萨");
        convertZoneTime(timeZone);

        timeZone = getTimeZoneWithCity("乌鲁木齐");
        convertZoneTime(timeZone);

        timeZone = getTimeZoneWithCity("伊犁");
        convertZoneTime(timeZone);

        timeZone = getTimeZoneWithCity("哈尔滨");
        convertZoneTime(timeZone);

        timeZone = getTimeZoneWithCity("Bayan-Undur - Ulziit Rd, 蒙古");
        convertZoneTime(timeZone);*/

        /*System.out.println(getCityNameWithCoordinate(40.7127837000,-74.0059413000));

        System.out.println(getCityNameWithCoordinate(39.9042110000,116.4073950000));

        System.out.println(getCityNameWithCoordinate(23.5464941916,108.9176935460));*/

//        timeZone = getTimeZoneWithCity("1600 Amphitheatre Parkway Mountain View, CA 94043");
//        System.out.println(timeZone.toString());

        /*DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//构造格式化模板
        System.out.println(TimeZone.getDefault());

        TimeZone time = getTimeZoneWithCoordinate(52.30,13.25);
        System.out.println(time);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(time);
        System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
        */

        Date date = getTimeZoneDate(new Date(), TimeZone.getTimeZone("Pacific/Auckland"));
        System.out.println(DateUtils.getTimeStampStr(date));

        date = DateUtils.parseDate("2017-01-27 19:00:00", "yyyy-MM-dd HH:mm:ss");
        Date timeZoneDate = getTimeZoneDate(date, TimeZone.getTimeZone("Pacific/Auckland"));
        System.out.println(DateUtils.getTimeStampStr(timeZoneDate));

        date = DateUtils.parseDate("2017-01-28 00:00:00", "yyyy-MM-dd HH:mm:ss");
        Date sysDate = getSystemDateByTimeZone(date, TimeZone.getTimeZone("Pacific/Auckland"));
        System.out.println(DateUtils.getTimeStampStr(sysDate));
    }
}
