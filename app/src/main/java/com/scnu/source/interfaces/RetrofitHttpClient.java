package com.scnu.source.interfaces;




import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 请求server
 * Created by ChenZehao
 * on 2019/12/5
 */
public interface RetrofitHttpClient {

    @GET("/android/version.xml")
    Observable<ResponseBody> versionCheck();

}