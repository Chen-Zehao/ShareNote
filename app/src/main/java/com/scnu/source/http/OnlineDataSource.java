package com.scnu.source.http;


import android.util.Log;

import com.scnu.model.ArticleModel;
import com.scnu.model.Macro;
import com.scnu.model.PictureModel;
import com.scnu.model.UserModel;
import com.scnu.source.interfaces.BaseDataSource;
import com.scnu.utils.LogUtils;
import com.scnu.utils.MyApplication;

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
        params.put("picture", article.getPictureList());
        params.put("theme",article.getTheme());
        params.put("location",article.getLocation());
        params.put("userId",article.getUserId());
        params.put("time",article.getTime());
        params.put("price",article.getPrice());
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
    public void queryArticleList(String queryType, String sortType, String currentUserId, String city, double latitude, double longtitude, String price, String theme, String userId, int pageNum, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("pageNum",pageNum);
        params.put("pageSize",10);
        params.put("currentUserId",currentUserId);
        params.put("userId",userId);
        params.put("sort",sortType);
        params.put("city",city);
        params.put("latitude",latitude);
        params.put("longtitude",longtitude);
        params.put("price",price);
        params.put("theme",theme);
        params.put("type",queryType);
        HttpUtils.getInstance().getAppServer().queryArticleList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void getArticleDetail(String issueId, String userId, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("issueId",issueId);
        params.put("userId",userId);
        HttpUtils.getInstance().getAppServer().getArticleDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void resetUserName(String name, String userId, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("userId",userId);
        params.put("userName",name);
        HttpUtils.getInstance().getAppServer().resetUserName(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void resetAvatar(PictureModel avatar, String userId, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("userId",userId);
        params.put("base64",avatar.getPictureBase64());
        params.put("pictureName",avatar.getFileName());
        HttpUtils.getInstance().getAppServer().resetAvatar(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void resetSex(String sex, String userId, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("userId",userId);
        params.put("sex",sex);
        HttpUtils.getInstance().getAppServer().resetSex(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void resetBirthday(String birthday, String userId, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("userId",userId);
        params.put("birthDay",birthday);
        HttpUtils.getInstance().getAppServer().resetBirthDay(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void resetLocation(String location, String userId, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("userId",userId);
        params.put("location",location);
        HttpUtils.getInstance().getAppServer().resetLocation(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }


    @Override
    public void getThemePriceList(String theme, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("theme",theme);
        HttpUtils.getInstance().getAppServer().getThemePriceList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void addAttention(String attentionId, String userId, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("attentionId",attentionId);
        params.put("userId",userId);
        HttpUtils.getInstance().getAppServer().addAttention(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void addCollection(String issueId, String userId, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("issueId",issueId);
        params.put("userId",userId);
        HttpUtils.getInstance().getAppServer().addCollection(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void addLike(String issueId, String userId, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("issueId",issueId);
        params.put("userId",userId);
        HttpUtils.getInstance().getAppServer().addLike(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void getUserList(String type, String userId, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("type",type);
        params.put("userId",userId);
        HttpUtils.getInstance().getAppServer().getUserList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void getUserInfo(String attentionId, String userId, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("attentionId",attentionId);
        params.put("userId",userId);
        HttpUtils.getInstance().getAppServer().getUserInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void deleteArticle(String issueId, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("issueId",issueId);
        HttpUtils.getInstance().getAppServer().deleteArticle(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void searchArticle(String message,String userId, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("message",message);
        params.put("userId",userId);
        HttpUtils.getInstance().getAppServer().searchArticle(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void addComment(String issueId, String content, String pid, String replyUserId, String time, String userId, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("issueId",issueId);
        params.put("content",content);
        params.put("pid",pid);
        params.put("replyUserId",replyUserId);
        params.put("time",time);
        params.put("userId",userId);
        HttpUtils.getInstance().getAppServer().addComment(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void findComment(String issueId, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("issueId",issueId);
        HttpUtils.getInstance().getAppServer().findComment(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void deleteComment(String commentId, CustomObserver callback) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("commentId",commentId);
        HttpUtils.getInstance().getAppServer().deleteComment(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }
}