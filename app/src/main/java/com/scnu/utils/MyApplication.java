package com.scnu.utils;


/**
 * Created by ChenZehao
 * on 2019/12/5
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Base64;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


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
        initSmartRefreshLayout();
        SDKInitializer.initialize(getApplicationContext());
    }

    /**
     * 初始化SmartRefreshLayout头尾布局
     * 此处优先级低于xml或具体业务代码中的设置
     */
    private void initSmartRefreshLayout(){
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //全局设置主题颜色
//                layout.setPrimaryColorsId(R.color._15AD9C, R.color.main_states_color);
//                return new BezierCircleHeader(context);
                return new ClassicsHeader(context);
            }
        });

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
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
        editor.apply();
    }
    public static String getParaValue(String key){
        SharedPreferences read = mContext.getSharedPreferences("ShareNote", MODE_PRIVATE);
        return read.getString(key, "");
    }

    /**
     * 保存对象
     * @param key
     * @param obj
     */
    public static void saveObject(String key, Object obj) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将Product对象保存在ObjectOutputStream对象中
            oos.writeObject(obj);
            SharedPreferences preferences = mContext.getSharedPreferences(
                    "base64", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            // 将对象转换成byte数组，并将其进行base64转换。
            String productBase64 = new String(Base64.encode(baos.toByteArray(),
                    Base64.DEFAULT));
            editor.putString(key, productBase64);
            editor.commit();
            oos.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    /**
     * 读取对象
     * @param key
     * @return
     */
    public static Object getObject(String key) {
        Object obj = null;
        try {
            SharedPreferences preferences = mContext.getSharedPreferences(
                    "base64", Context.MODE_PRIVATE);
            // 从源xml文件中，读取Product对象的base64格式字符串
            String base64Product = preferences.getString(key, "");
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 将base64格式字符串还原成byte数组
            byte[] productBytes = Base64.decode(base64Product.getBytes(),
                    Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(productBytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            // byte数组，转换成ActiveUserBean对象
            obj = ois.readObject();
            ois.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return obj;
    }


}