package com.scnu.source.interfaces;

/**
 * 回调
 * Created by ChenZehao
 * on 2019/12/5
 */
public interface BaseCallBack<T> {

    void onDataLoaded(T result);
    void onDataNotAvailable(Throwable e);

}
