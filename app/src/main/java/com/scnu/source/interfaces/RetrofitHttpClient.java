package com.scnu.source.interfaces;


import com.scnu.source.beans.ArticleListResBean;
import com.scnu.source.beans.ArticleResBean;
import com.scnu.source.beans.BaseBean;
import com.scnu.source.beans.LoginResBean;
import com.scnu.source.beans.ThemeResBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 请求server
 * Created by ChenZehao
 * on 2019/12/5
 */
public interface RetrofitHttpClient {

    @POST("/user/user_login")
    Observable<LoginResBean> userLogin(@Body Map<String, String> params);

    @POST("/issue/publish")
    Observable<BaseBean> publishArticle(@Body Map<String, Object> params);

    @POST("/theme/getAll")
    Observable<ThemeResBean> getThemeList(@Body Map<String, Object> params);

    @POST("/issue/query")
    Observable<ArticleListResBean> queryArticleList(@Body Map<String, Object> params);

    @POST("/issue/inquireDetail")
    Observable<ArticleResBean> getArticleDetail(@Body Map<String, Object> params);
}