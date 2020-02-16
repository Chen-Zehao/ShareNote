package com.scnu.utils;


/**
 * Created by ChenZehao
 * on 2019/12/5
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;


/**
 * 编写自己的Application，管理全局状态信息，比如Context
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;
    private static Context mContext;
    private static MediaPlayer mMediaDing;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();
        SDKInitializer.initialize(getApplicationContext());
    }

    //获取MyApplication单例
    public static MyApplication getInstance(){
        return mInstance;
    }
    //获取全局context
    public static Context getContextObject(){
        return mContext;
    }

    //提示信息
    public static void playShortToast(String msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
    public static void playShortToast(int id){
        Toast.makeText(mContext, id, Toast.LENGTH_SHORT).show();
    }
    public static void playLongToast(String msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }
    public static void playLongToast(int id){
        Toast.makeText(mContext, id, Toast.LENGTH_LONG).show();
    }

    //获取字符串内容更
    public static String StringValue(int id){
        return mContext.getResources().getString(id);
    }
    public static String StringValue(Context context, int id){
        if (context == null){
            return StringValue(id);
        }
        return context.getResources().getString(id);
    }

    //程序参数设置
    /*
    * Context.MODE_PRIVATE: 指定该SharedPreferences数据只能被本应用程序读、写。
    * Context.MODE_WORLD_READABLE:  指定该SharedPreferences数据能被其他应用程序读，但不能写。
    * Context.MODE_WORLD_WRITEABLE:  指定该SharedPreferences数据能被其他应用程序读，写
    * **/
    public static void setParaValue(String key, String value){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("ShareNote", MODE_PRIVATE).edit();
        editor.putString(key,value);//editor.clear();editor.remove();
        editor.commit();
    }
    public static String getParaValue(String key){
        SharedPreferences read = mContext.getSharedPreferences("ShareNote", MODE_PRIVATE);
        return read.getString(key, "");
    }



}