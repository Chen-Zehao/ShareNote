package com.scnu.sharenote.main.fragment.mine.presenter;

import com.scnu.base.BasePresenter;
import com.scnu.enums.UserTypeEnum;
import com.scnu.sharenote.main.fragment.mine.ui.activity.UserFollowersView;
import com.scnu.source.beans.UserListResBean;
import com.scnu.source.http.CustomObserver;
import com.scnu.source.http.OnlineDataSource;
import com.scnu.utils.ToastUtils;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ChenZehao
 * on 2020/4/2
 */
public class UserFollowersPresenter extends BasePresenter<UserFollowersView> {
    public void getFollowUsers(String userId){
        OnlineDataSource.getInstance().getUserList(UserTypeEnum.getUserFens(), userId, new CustomObserver<UserListResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(UserListResBean result) {
                if(null != result.getData()){
                    if(isActivityAlive()){
                        getMvpView().getFollowUsersSuccess(result.getData());
                    }
                }
            }

            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),"获取粉丝列表失败");
            }
        });
    }
}
