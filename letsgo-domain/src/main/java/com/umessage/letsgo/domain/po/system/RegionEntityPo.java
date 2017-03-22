/*
 * RegionEntity.java
 * Copyright(C) 2015-2016 迅奇连城
 * All rights reserved.
 * --------------------------------------------
 * 2016-08-15 Created by wendy
 */
package com.umessage.letsgo.domain.po.system;

public class RegionEntityPo extends RegionEntity{

    private String country;

    private String countryId;

    private String countryEngName;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryEngName() {
        return countryEngName;
    }

    public void setCountryEngName(String countryEngName) {
        this.countryEngName = countryEngName;
    }

    @Override
    public String toString() {
        return "RegionEntityPo{" +
                "country='" + country + '\'' +
                ", countryId='" + countryId + '\'' +
                '}';
    }
}