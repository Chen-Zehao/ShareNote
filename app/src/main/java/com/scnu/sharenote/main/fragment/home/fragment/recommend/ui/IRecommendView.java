package com.scnu.sharenote.main.fragment.home.fragment.recommend.ui;

import com.scnu.base.ui.BaseView;
import com.scnu.model.ArticleModel;

import java.util.List;

/**
 * Created by ChenZehao
 * on 2020/1/15
 */
public interface IRecommendView extends BaseView {

    /**
     * 获取文章列表成功
     */
    void getArticleListSuccess(List<ArticleModel> articleModelList);

    /**
     * 获取文章列表失败
     */
    void getArticleListFailed();

    /**
     * 加载更多文章成功
     */
    void loadMoreArticleSuccess(List<ArticleModel> articleModelList, boolean isEnd);

    /**
     * 加载更多文章失败
     */
    void loadMoreArticleFailed();

}
