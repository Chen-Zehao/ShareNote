package com.scnu.source.interfaces;


import androidx.annotation.NonNull;

/**
 * Created by ChenZehao
 * on 2019/12/5
 */
public interface BaseDataSource {

    void versionCheck(@NonNull BaseCallBack callback);

}
