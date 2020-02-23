package com.scnu.sharenote.login.presenter;

import com.scnu.base.BasePresenter;
import com.scnu.sharenote.login.ui.ILoginView;
import com.scnu.source.beans.LoginResBean;
import com.scnu.source.http.CustomObserver;
import com.scnu.source.http.OnlineDataSource;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ChenZehao
 * on 2019/12/5
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    /**
     * 用户登录
     */
    public void userLogin(String mobileNo){
        OnlineDataSource.getInstance().userLogin(mobileNo, new CustomObserver<LoginResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(LoginResBean result) {
                if(isActivityAlive()){
                    getMvpView().userLoginSuccess();
                }
            }

            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
            }
        });
    }
}
