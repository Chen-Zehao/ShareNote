package com.scnu.sharenote.main.fragment.mine.presenter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.scnu.base.BasePresenter;
import com.scnu.model.Macro;
import com.scnu.model.PictureModel;
import com.scnu.model.UserModel;
import com.scnu.sharenote.main.fragment.mine.ui.activity.IEditDataView;
import com.scnu.source.beans.BaseBean;
import com.scnu.source.beans.UserResBean;
import com.scnu.source.http.CustomObserver;
import com.scnu.source.http.OnlineDataSource;
import com.scnu.utils.LogUtils;
import com.scnu.utils.MyApplication;
import com.scnu.utils.ToastUtils;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ChenZehao
 * on 2020/3/5
 */
public class EditDataPresenter extends BasePresenter<IEditDataView> {
    /**
     * 更新用户名称
     */
    public void updateUserName(String name){
        UserModel userModel = (UserModel) MyApplication.getObject(Macro.KEY_USER);
        OnlineDataSource.getInstance().resetUserName(name, userModel.getUserId(),new CustomObserver<UserResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(UserResBean result) {
                if(null != result && null != result.getData()){
                    MyApplication.saveObject(Macro.KEY_USER,result.getData());
                    if (!TextUtils.isEmpty(result.getData().getName())){
                        if (isActivityAlive()){
                            getMvpView().updateUserNameSuccess(result.getData().getName());
                        }
                    }
                }else{
                    ToastUtils.showToast(getContext(),"修改用户名称失败");
                }
            }

            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),"修改用户名称失败");
            }
        });
    }

    /**
     * 更新用户头像
     */
    public void updateUserAvatar(PictureModel picture){
        UserModel userModel = (UserModel) MyApplication.getObject(Macro.KEY_USER);
        OnlineDataSource.getInstance().resetAvatar(picture, userModel.getUserId(),new CustomObserver<UserResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager())
        {
            @Override
            public void onSuccess(UserResBean result) {
                if(null != result && null != result.getData()){
                    MyApplication.saveObject(Macro.KEY_USER,result.getData());
                    if (!TextUtils.isEmpty(result.getData().getAvatarUrl())){
                        if (isActivityAlive()){
                            getMvpView().updateUserAvatarSuccess(result.getData().getAvatarUrl());
                        }
                    }
                }else{
                    ToastUtils.showToast(getContext(),"修改用户头像失败");
                }
            }

            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),"修改用户头像失败");
            }
        });
    }

    /**
     * 更新用户性别
     */
    public void updateUserSex(String sex){
        UserModel userModel = (UserModel) MyApplication.getObject(Macro.KEY_USER);
        OnlineDataSource.getInstance().resetSex(sex, userModel.getUserId(),new CustomObserver<UserResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(UserResBean result) {
                if(null != result && null != result.getData()){
                    MyApplication.saveObject(Macro.KEY_USER,result.getData());
                    if (!TextUtils.isEmpty(result.getData().getSex())){
                        if (isActivityAlive()){
                            getMvpView().updateUserSexSuccess(result.getData().getSex());
                        }
                    }
                }else{
                    ToastUtils.showToast(getContext(),"修改用户性别失败");
                }
            }

            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),"修改用户性别失败");
            }
        });
    }

    /**
     * 更新用户生日
     */
    public void updateUserBirthday(String birthday){
        UserModel userModel = (UserModel) MyApplication.getObject(Macro.KEY_USER);
        OnlineDataSource.getInstance().resetBirthday(birthday, userModel.getUserId(),new CustomObserver<UserResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(UserResBean result) {
                if(null != result && null != result.getData()){
                    MyApplication.saveObject(Macro.KEY_USER,result.getData());
                    if (!TextUtils.isEmpty(result.getData().getBirthDay())){
                        if (isActivityAlive()){
                            getMvpView().updateUserBirthdaySuccess(result.getData().getBirthDay());
                        }
                    }
                }else{
                    ToastUtils.showToast(getContext(),"修改用户生日失败");
                }
            }

            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),"修改用户生日失败");
            }
        });
    }

    /**
     * 更新用户位置
     */
    public void updateUserLocation(String location){
        UserModel userModel = (UserModel) MyApplication.getObject(Macro.KEY_USER);
        OnlineDataSource.getInstance().resetLocation(location, userModel.getUserId(),new CustomObserver<UserResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(UserResBean result) {
                if(null != result && null != result.getData()){
                    MyApplication.saveObject(Macro.KEY_USER,result.getData());
                    if (!TextUtils.isEmpty(result.getData().getLocation())){
                        if (isActivityAlive()){
                            getMvpView().updateUserLocationSuccess(result.getData().getLocation());
                        }
                    }
                }else{
                    ToastUtils.showToast(getContext(),"修改用户地区失败");
                }
            }

            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),"修改用户地区失败");
            }
        });
    }
}
