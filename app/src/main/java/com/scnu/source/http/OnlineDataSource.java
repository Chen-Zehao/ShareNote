package com.scnu.source.http;


import com.scnu.source.interfaces.BaseCallBack;
import com.scnu.source.interfaces.BaseDataSource;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by ChenZehao
 * on 2019/12/5
 */
public class OnlineDataSource implements BaseDataSource {

    private static OnlineDataSource mDataSource;

    private OnlineDataSource() {
    }

    public static OnlineDataSource getInstance() {
        if (mDataSource == null) {
            mDataSource = new OnlineDataSource();
        }
        return mDataSource;
    }

    @Override
    public void versionCheck(CustomObserver callback) {
//        HttpUtils.getInstance().getUpdateServer().versionCheck()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(callback);
    }

}