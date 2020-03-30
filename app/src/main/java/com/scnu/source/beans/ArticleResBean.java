package com.scnu.source.beans;

import com.scnu.model.ArticleModel;

import java.util.List;

/**
 * Created by ChenZehao
 * on 2020/3/21
 */
public class ArticleResBean extends BaseBean{

    private ArticleModel data;

    public ArticleModel getData() {
        return data;
    }

    public void setData(ArticleModel data) {
        this.data = data;
    }
}
