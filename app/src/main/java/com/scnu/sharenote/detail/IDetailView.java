package com.scnu.sharenote.detail;

import com.scnu.base.ui.BaseView;
import com.scnu.model.ArticleModel;

/**
 * Created by ChenZehao
 * on 2020/1/18
 */
public interface IDetailView extends BaseView {

    /**
     * 获取详情内容
     */
    void getDetailSuccess(ArticleModel article);


}
