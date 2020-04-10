package com.scnu.eventbusmodel;

/**
 * Created by ChenZehao
 * on 2020/2/16
 */
public class LocationInfoEvent {

    private String city;

    private String province;

    private double latitude;

    private double longtitude;

    public LocationInfoEvent(String city,String province,double latitude,double longtitude){
        this.city = city;
        this.province = province;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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
