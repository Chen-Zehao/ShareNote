package com.scnu.sharenote.search.ui;

import com.scnu.base.ui.BaseView;
import com.scnu.model.ArticleModel;

import java.util.List;

/**
 * Created by ChenZehao
 * on 2020/4/8
 */
public interface ISearchView extends BaseView {
    /**
     * 搜索文章成功
     * @param articleList
     */
    void searchArticleSuccess(List<ArticleModel> articleList);

}
