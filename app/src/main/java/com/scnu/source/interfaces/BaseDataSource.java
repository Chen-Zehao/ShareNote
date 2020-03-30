package com.scnu.source.interfaces;


import com.scnu.model.ArticleModel;
import com.scnu.source.http.CustomObserver;

/**
 * Created by ChenZehao
 * on 2019/12/5
 */
public interface BaseDataSource {

    /**
     * 用户登录
     * @param callback
     */
    void userLogin(String mobileNo, CustomObserver callback);

    /**
     * 发布内容
     * @param article
     * @param callback
     */
    void publishArticle(ArticleModel article, CustomObserver callback);

    /**
     * 获取主题列表
     * @param callback
     */
    void getThemeList(CustomObserver callback);

    /**
     * 获取文章列表
     * @param pageNum
     * @param callback
     */
    void queryArticleList(int pageNum, CustomObserver callback);


    /**
     * 获取文章详情
     * @param issueId
     * @param callback
     */
    void getArticleDetail(String issueId, CustomObserver callback);


}
