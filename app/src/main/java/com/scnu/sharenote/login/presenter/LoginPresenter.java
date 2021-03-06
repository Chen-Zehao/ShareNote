package com.scnu.sharenote.login.presenter;

import com.scnu.base.BasePresenter;
import com.scnu.model.Macro;
import com.scnu.model.UserModel;
import com.scnu.sharenote.login.ui.ILoginView;
import com.scnu.source.beans.UserResBean;
import com.scnu.source.http.CustomObserver;
import com.scnu.source.http.OnlineDataSource;
import com.scnu.utils.MyApplication;
import com.scnu.utils.ToastUtils;

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
        OnlineDataSource.getInstance().userLogin(mobileNo, new CustomObserver<UserResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(UserResBean result) {
                if(null != result.getData()){
                    UserModel user = result.getData();
                    MyApplication.saveObject(Macro.KEY_USER,user);
                    if(isActivityAlive()){
                        getMvpView().userLoginSuccess();
                    }
                }
            }

            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),exception.toString());
            }
        });
    }
}
