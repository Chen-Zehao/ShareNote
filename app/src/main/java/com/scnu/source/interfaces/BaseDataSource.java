package com.scnu.source.interfaces;


import com.scnu.source.http.CustomObserver;

import androidx.annotation.NonNull;

/**
 * Created by ChenZehao
 * on 2019/12/5
 */
public interface BaseDataSource {

    void versionCheck(CustomObserver callback);

}
