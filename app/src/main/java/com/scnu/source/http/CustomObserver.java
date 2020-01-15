package com.scnu.source.http;


import android.util.Log;

import com.scnu.source.beans.BaseBean;
import com.scnu.source.interfaces.BaseCallBack;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by ChenZehao
 * on 2019/12/5
 */
public class CustomObserver<T> implements Observer<T> {

    private BaseCallBack<T> callBack;

    public CustomObserver(BaseCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T value) {
        if (value.getClass().getGenericSuperclass() == ResponseBody.class){
            callBack.onDataLoaded(value);
            return;
        }
        BaseBean baseBean = (BaseBean) value;
        if (baseBean.getResCode() == 0) {
            callBack.onDataLoaded(value);
        } else {
            callBack.onDataNotAvailable(new Throwable(baseBean.getResDisc()));
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e != null && e.getMessage() != null) {
            Log.i("onError", e.toString());
            callBack.onDataNotAvailable(e);
        }
    }

    @Override
    public void onComplete() {

    }
}
