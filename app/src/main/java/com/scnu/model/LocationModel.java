package com.scnu.model;

import java.io.Serializable;

/**
 * Created by ChenZehao
 * on 2020/3/1
 */
public class LocationModel implements Serializable {

    private String name;//名称

    public String province;//省份

    public String city;//城市

    private double latitude;//纬度

    private double longtitude;//经度

    public LocationModel() {
        name = "";
        province = "";
        city = "";
        latitude = 0;
        longtitude = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
}
