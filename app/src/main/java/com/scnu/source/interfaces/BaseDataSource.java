package com.scnu.source.interfaces;


import com.scnu.source.http.CustomObserver;

import androidx.annotation.NonNull;

/**
 * Created by ChenZehao
 * on 2019/12/5
 */
public interface BaseDataSource {

    /**
     * 用户登录
     * @param callback
     */
    void userLogin(String mobileNo, CustomObserver callback);

}
