package com.scnu.sharenote.main.fragment.mine.presenter;

import android.content.Context;

import com.scnu.base.BasePresenter;
import com.scnu.model.Macro;
import com.scnu.model.UserModel;
import com.scnu.sharenote.main.fragment.mine.ui.fragment.IMineView;
import com.scnu.source.beans.UserResBean;
import com.scnu.source.http.CustomObserver;
import com.scnu.source.http.OnlineDataSource;
import com.scnu.utils.MyApplication;
import com.scnu.utils.ToastUtils;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ChenZehao
 * on 2019/12/10
 */
public class MinePresenter extends BasePresenter<IMineView>{
    public MinePresenter(Context context, IMineView view) {
        super(context, view);
    }

    public void getUserInfo(String userId){
        OnlineDataSource.getInstance().getUserInfo(userId, userId, new CustomObserver<UserResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(UserResBean result) {
                if(null != result.getData()){
                    UserModel user = result.getData();
                    MyApplication.saveObject(Macro.KEY_USER,user);
                    if(isActivityAlive()){
                        getMvpView().getUserInfoSuccess(user);
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
