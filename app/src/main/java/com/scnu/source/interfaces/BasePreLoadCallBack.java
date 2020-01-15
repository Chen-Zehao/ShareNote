package com.scnu.source.interfaces;

import com.scnu.source.beans.BaseBean;

/**
 * onOnlineDataLoaded用于网络数据回调,onOfflineDataLoaded用于本地数据率先回调
 * Created by ChenZehao
 * on 2019/12/5
 */
public interface BasePreLoadCallBack<T> extends BaseCallBack<T> {

    void onOnlineDataLoaded(BaseBean<T> baseBean);
    void onOfflineDataLoaded(BaseBean<T> baseBean);

}
