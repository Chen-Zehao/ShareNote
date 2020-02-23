package com.scnu.utils;

import android.content.Context;
import android.util.Log;

/**
 * Created by ChenZehao
 * on 2019/12/5
 */
public class ServerConfig {

    private Context mContext;

    private String appServerIP;//应用服务ip
    private String appServerPort;//应用服务port

    private ServerConfig(){
        //初始化
        mContext = MyApplication.getContextObject();
        appServerIP = "134.175.233.92";
        appServerPort = "8080";
    }

    private static ServerConfig instance;
    public static ServerConfig getInstance() {
        if (instance == null){
            instance = new ServerConfig();
        }
        return instance;
    }

    //获取网址
    public String getAppServerURL(){
        return "http://"+appServerIP+":"+appServerPort;
    }

}
