package com.scnu.source.http;


import android.content.Context;
import android.util.Log;

import com.scnu.base.ui.dialog.BaseLoadingDialog;
import com.scnu.source.beans.BaseBean;
import com.scnu.utils.ToastUtils;

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
        if (null != mLoadingDialog) {
            mLoadingDialog.dismissAllowingStateLoss();
        }
    }

    /**
     * 请求成功
     * @param result
     */
    public abstract void onSuccess(T result);

    /**
     * 请求失败
     * @param exception
     */
    public void onFail(Throwable exception){
        ToastUtils.showToast(mContext,exception.getMessage());
    }

    @Override
    public void onSubscribe(Disposable d) {
            showLoadingDialog();
    }

    @Override
    public void onNext(T value) {
        if (value.getClass().getGenericSuperclass() == ResponseBody.class){
            onSuccess(value);
            return;
        }
        BaseBean baseBean = (BaseBean) value;
        if (baseBean.getResCode().equals("0")) {
            onSuccess(value);
        } else {
            onFail(new Throwable(baseBean.getResDisc()));
        }
    }

    @Override
    public void onError(Throwable e) {
        onComplete();
        if (e != null && e.getMessage() != null) {
            onFail(e);
        }
    }

    @Override
    public void onComplete() {
        dismissDialog();
    }
}
