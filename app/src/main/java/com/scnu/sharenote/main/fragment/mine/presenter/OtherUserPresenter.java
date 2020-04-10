package com.scnu.sharenote.main.fragment.mine.presenter;

import com.scnu.base.BasePresenter;
import com.scnu.model.Macro;
import com.scnu.model.UserModel;
import com.scnu.sharenote.main.fragment.mine.ui.activity.IOtherUserView;
import com.scnu.source.beans.BaseBean;
import com.scnu.source.beans.UserResBean;
import com.scnu.source.http.CustomObserver;
import com.scnu.source.http.OnlineDataSource;
import com.scnu.utils.MyApplication;
import com.scnu.utils.ToastUtils;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ChenZehao
 * on 2020/4/5
 */
public class OtherUserPresenter extends BasePresenter<IOtherUserView> {
    /**
     * 获取用户信息
     * @param attentionId
     * @param userId
     */
    public void getUserInfo(String attentionId, String userId){
        OnlineDataSource.getInstance().getUserInfo(attentionId, userId, new CustomObserver<UserResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(UserResBean result) {
                if(null != result.getData()){
                    UserModel user = result.getData();
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

    public void addAttention(String attentionId, String userId){
        OnlineDataSource.getInstance().addAttention(attentionId, userId, new CustomObserver<BaseBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(BaseBean result) {
                if(isActivityAlive()){
                    getMvpView().addAttentionSuccess();
                }
            }
            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),exception.getMessage());
            }
        });
    }

}
