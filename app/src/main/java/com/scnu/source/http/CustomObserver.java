package com.scnu.source.http;


import android.content.Context;
import android.util.Log;

import com.scnu.base.ui.BaseLoadingDialog;
import com.scnu.source.beans.BaseBean;
import com.scnu.source.interfaces.BaseCallBack;

import androidx.fragment.app.FragmentManager;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by ChenZehao
 * on 2019/12/5
 */
public abstract class CustomObserver<T> implements Observer<T> {

    private BaseLoadingDialog mLoadingDialog;

    private Context mContext;
    private FragmentManager mFragmentManager;

    private BaseCallBack<T> callBack;

    public CustomObserver(Context context, FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
        mLoadingDialog = BaseLoadingDialog.getInstance(context);
    }

    private void showLoadingDialog(){
        if (null != mLoadingDialog) {
            mLoadingDialog.show(mFragmentManager,null);
        }
    }

    private void dismissDialog(){
        if (null != mLoadingDialog && mLoadingDialog.isVisible()) {
            mLoadingDialog.dismissAllowingStateLoss();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
            showLoadingDialog();
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
        onComplete();
        if (e != null && e.getMessage() != null) {
            Log.i("onError", e.toString());
            callBack.onDataNotAvailable(e);
        }
    }

    @Override
    public void onComplete() {
        dismissDialog();
    }
}
