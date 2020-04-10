package com.scnu.sharenote.main.fragment.mine.presenter;

import com.scnu.base.BasePresenter;
import com.scnu.enums.UserTypeEnum;
import com.scnu.sharenote.main.fragment.mine.ui.activity.UserAttentionView;
import com.scnu.source.beans.UserListResBean;
import com.scnu.source.http.CustomObserver;
import com.scnu.source.http.OnlineDataSource;
import com.scnu.utils.ToastUtils;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ChenZehao
 * on 2020/4/2
 */
public class UserAttentionPresenter extends BasePresenter<UserAttentionView> {
    public void getAttentionUsers(String userId){
        OnlineDataSource.getInstance().getUserList(UserTypeEnum.getUserAttention(), userId, new CustomObserver<UserListResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(UserListResBean result) {
                if(null != result.getData()){
                    if(isActivityAlive()){
                        getMvpView().getAttentionUsersSuccess(result.getData());
                    }
                }
            }

            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),"获取关注列表失败");
            }
        });
    }
}
