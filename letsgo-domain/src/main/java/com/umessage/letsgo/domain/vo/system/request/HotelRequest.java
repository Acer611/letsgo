package com.umessage.letsgo.domain.vo.system.request;
import  java.util.List;
/**
 * Created by zengguoqing on 2016/8/16.
 */
public class HotelRequest {

    public void setHotelChineseName(String hotelChineseName) {
        this.hotelChineseName = hotelChineseName;
    }



    public String getHotelChineseName() {
        return hotelChineseName;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    private String city;
    private String hotelChineseName;
    /**
     * 城市id
     */
    private List<String> cities;
}
