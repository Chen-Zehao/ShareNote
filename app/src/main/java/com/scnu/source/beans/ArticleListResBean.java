package com.scnu.source.beans;

import com.scnu.model.ArticleModel;
import com.scnu.model.ThemeModel;

import java.util.List;

/**
 * Created by ChenZehao
 * on 2020/3/1
 */
public class ArticleListResBean extends BaseBean {

    private List<ArticleModel> data;

    public List<ArticleModel> getData() {
        return data;
    }

    public void setData(List<ArticleModel> data) {
        this.data = data;
    }
}
