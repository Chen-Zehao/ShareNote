package com.scnu.source.interfaces;


import com.scnu.source.beans.ArticleListResBean;
import com.scnu.source.beans.ArticleResBean;
import com.scnu.source.beans.BaseBean;
import com.scnu.source.beans.CommentListResBean;
import com.scnu.source.beans.PriceResBean;
import com.scnu.source.beans.UserListResBean;
import com.scnu.source.beans.UserResBean;
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
    Observable<UserResBean> userLogin(@Body Map<String, String> params);

    @POST("/issue/publish")
    Observable<BaseBean> publishArticle(@Body Map<String, Object> params);

    @POST("/theme/getAll")
    Observable<ThemeResBean> getThemeList(@Body Map<String, Object> params);

    @POST("/issue/query")
    Observable<ArticleListResBean> queryArticleList(@Body Map<String, Object> params);

    @POST("/issue/inquireDetail")
    Observable<ArticleResBean> getArticleDetail(@Body Map<String, Object> params);

    @POST("/user/resetUserName")
    Observable<UserResBean> resetUserName(@Body Map<String, Object> params);

    @POST("/user/resetBirthDay")
    Observable<UserResBean> resetBirthDay(@Body Map<String, Object> params);

    @POST("/user/resetAvatar")
    Observable<UserResBean> resetAvatar(@Body Map<String, Object> params);

    @POST("/user/resetSex")
    Observable<UserResBean> resetSex(@Body Map<String, Object> params);

    @POST("/user/resetLocation")
    Observable<UserResBean> resetLocation(@Body Map<String, Object> params);

    @POST("/pricelist/getPrice")
    Observable<PriceResBean> getThemePriceList(@Body Map<String, Object> params);

    @POST("/user/addAttention")
    Observable<BaseBean> addAttention(@Body Map<String, Object> params);

    @POST("/user/addCollection")
    Observable<BaseBean> addCollection(@Body Map<String, Object> params);

    @POST("/user/addLike")
    Observable<BaseBean> addLike(@Body Map<String, Object> params);

    @POST("/user/returnUserList")
    Observable<UserListResBean> getUserList(@Body Map<String, Object> params);

    @POST("/user/messageOfUser")
    Observable<UserResBean> getUserInfo(@Body Map<String, Object> params);

    @POST("/issue/deleteIssue")
    Observable<BaseBean> deleteArticle(@Body Map<String, Object> params);

    @POST("/issue/searchIssue")
    Observable<ArticleListResBean> searchArticle(@Body Map<String, Object> params);

    @POST("/comment/addComment")
    Observable<BaseBean> addComment(@Body Map<String, Object> params);

    @POST("/comment/findComment")
    Observable<CommentListResBean> findComment(@Body Map<String, Object> params);

    @POST("/comment/deleteComment")
    Observable<BaseBean> deleteComment(@Body Map<String, Object> params);
}