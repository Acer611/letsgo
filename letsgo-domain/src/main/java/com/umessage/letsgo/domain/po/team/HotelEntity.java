/*
 * GroupEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-05-31 Created by Administrator
 */
package com.umessage.letsgo.domain.po.team;

import java.io.Serializable;

public class HotelEntity implements Serializable {
    private static final long serialVersionUID = 1L;
   private String hotelId;

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public void setHotelChineseName(String hotelChineseName) {
        this.hotelChineseName = hotelChineseName;
    }

    public void setHotelEnglishName(String hotelEnglishName) {
        this.hotelEnglishName = hotelEnglishName;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setDelta(String delta) {
        this.delta = delta;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPartIcularAddress(String partIcularAddress) {
        this.partIcularAddress = partIcularAddress;
    }

    public void setStarLv(String starLv) {
        this.starLv = starLv;
    }

    public void setBriefintroduction(String briefintroduction) {
        this.briefintroduction = briefintroduction;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setHotelPingyinName(String hotelPingyinName) {
        this.hotelPingyinName = hotelPingyinName;
    }

    private String hotelChineseName;
    private String hotelEnglishName;
    private String lon;

    public String getHotelId() {
        return hotelId;
    }

    public String getHotelChineseName() {
        return hotelChineseName;
    }

    public String getHotelEnglishName() {
        return hotelEnglishName;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

    public String getDelta() {
        return delta;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getPartIcularAddress() {
        return partIcularAddress;
    }

    public String getStarLv() {
        return starLv;
    }

    public String getBriefintroduction() {
        return briefintroduction;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getHotelPingyinName() {
        return hotelPingyinName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    private String lat;
    private String delta;
    private String country;
    private String countryName;
    private String countryId;
    private String city;
    private String cityName;
    private String cityId;
    private String partIcularAddress;
    private String starLv;
    private String briefintroduction;
    private String telephone;
    private String picUrl;
    private String hotelPingyinName;


}