package com.scnu.eventbusmodel;

/**
 * Created by ChenZehao
 * on 2020/2/16
 */
public class LocationInfoEvent {

    private String city;

    private String province;

    public LocationInfoEvent(String city,String province){
        this.city = city;
        this.province = province;
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
}
