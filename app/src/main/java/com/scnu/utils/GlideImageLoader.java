package com.scnu.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;


/**
 * Created by ChenZehao
 * on 2019/12/5
 */
public class GlideImageLoader{

    private static GlideImageLoader mInstance;

    public static GlideImageLoader getInstance(){
        if (mInstance == null){
            mInstance = new GlideImageLoader();
        }
        return mInstance;
    }


    //加载图片
    public void loadImage(Context context, Object path, ImageView imageView){
        Glide.with(context)
                .load(path)//.override(800, 800)//加载尺寸
                .priority(Priority.NORMAL)//优先级
//                .centerCrop()//.fitCenter()//图片缩放
//                .transform(new GlideRoundTransform(2))//圆角图片
                //.transform(new GlideCircleTransform(this))//圆形图片
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)//磁盘缓存
                .into(imageView);
    }


    //加载圆角图片
    public void loadFilletImage(Context context, Object path, ImageView imageView){
        Glide.with(context)
                .load(path)//.override(800, 800)//加载尺寸
                .priority(Priority.NORMAL)//优先级
//                .centerCrop()//.fitCenter()//图片缩放
                .transform(new RoundedCorners(4))//圆角图片
                //.transform(new GlideCircleTransform(this))//圆形图片
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)//磁盘缓存
                .into(imageView);
    }

}

