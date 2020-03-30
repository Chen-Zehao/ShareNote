package com.scnu.source.http;


import android.util.Log;

import com.scnu.model.ArticleModel;
import com.scnu.source.interfaces.BaseDataSource;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by ChenZehao
 * on 2019/12/5
 */
public class OnlineDataSource implements BaseDataSource {

    private static OnlineDataSource mDataSource;

    private OnlineDataSource() {
    }

    public static OnlineDataSource getInstance() {
        if (mDataSource == null) {
            mDataSource = new OnlineDataSource();
        }
        return mDataSource;
    }

    /**
     * 用户登录
     * @param mobileNo
     * @param callback
     */
    @Override
    public void userLogin(String mobileNo, CustomObserver callback) {
        Map<String,String> params = new HashMap<>();
        params.put("mobileNo", mobileNo);
        HttpUtils.getInstance().getAppServer().userLogin(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    /**
     * 发布文章
     * @param article
     * @param callback
     */
    @Override
    public void publishArticle(ArticleModel article, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("title", article.getTitle());
        params.put("content", article.getContent());
        params.put("picture", article.getImageList());
        params.put("theme",article.getTheme());
        params.put("location",article.getLocation());
        params.put("userId",article.getUserId());
        params.put("time",article.getTime());
        HttpUtils.getInstance().getAppServer().publishArticle(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    /**
     * 获取主题列表
     * @param callback
     */
    @Override
    public void getThemeList(CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        HttpUtils.getInstance().getAppServer().getThemeList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void queryArticleList(int pageNum, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("pageNum",pageNum);
        params.put("pageSize",10);
        HttpUtils.getInstance().getAppServer().queryArticleList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void getArticleDetail(String issueId, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("issueId",issueId);
        HttpUtils.getInstance().getAppServer().getArticleDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

}