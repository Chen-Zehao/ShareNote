package com.scnu.source.http;

import android.util.Log;

import com.scnu.sharenote.BuildConfig;
import com.scnu.source.interfaces.RetrofitHttpClient;
import com.scnu.utils.MyApplication;
import com.scnu.utils.ServerConfig;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;


/**
 * Created by ChenZehao
 * on 2019/12/5
 */
public class HttpUtils {

    private static HttpUtils mHttpUtils;
    private static RetrofitHttpClient mHttpClient;

    public static HttpUtils getInstance() {
        if (mHttpUtils == null) {
            mHttpUtils = new HttpUtils();
        }
        return mHttpUtils;
    }

    public RetrofitHttpClient getAppServer() {
        if (mHttpClient == null) {
            mHttpClient = createHttpClient(ServerConfig.getInstance().getAppServerURL());
        }
        return mHttpClient;
    }

    private RetrofitHttpClient createHttpClient(String baseUrl) {
        OkHttpClient httpClient = getOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(RetrofitHttpClient.class);
    }

    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
                //.cache(cache)
                .connectTimeout(HttpConstant.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HttpConstant.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HttpConstant.READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        String bodyStr = response.body().string().trim();
                        //String resultStr = URLDecoder.decode(bodyStr,"UTF-8");//含%解码失败
                        MediaType mediaType = response.body().contentType();
                        Response newResponse = response.newBuilder()
                                .body(ResponseBody.create(mediaType, bodyStr)).build();
                        return newResponse;
                    }
                });
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okBuilder.addInterceptor(logging);
        }
        OkHttpClient client = okBuilder.build();
        return client;
    }

}
