package com.scnu.source.interfaces;


import com.scnu.source.beans.LoginResBean;

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

    @POST("/phone/user_login")
    Observable<LoginResBean> userLogin(@Body Map<String, String> params);

}