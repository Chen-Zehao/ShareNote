package com.scnu.custom.citypicker;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.scnu.eventbusmodel.LocationInfoEvent;
import com.scnu.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ChenZehao
 * on 2020/2/16
 */
public class CityLocationListener extends BDAbstractLocationListener {

    @Override
    public void onReceiveLocation(BDLocation location){
        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
        String country = location.getCountry();    //获取国家
        String province = location.getProvince();    //获取省份
        String city = location.getCity();    //获取城市
        if(!TextUtils.isEmpty(city) && city.contains("市"))
            city = city.substring(0,city.indexOf("市"));
        String district = location.getDistrict();    //获取区县
        String street = location.getStreet();    //获取街道信息
        EventBus.getDefault().post(new LocationInfoEvent(city,province));
    }
}
