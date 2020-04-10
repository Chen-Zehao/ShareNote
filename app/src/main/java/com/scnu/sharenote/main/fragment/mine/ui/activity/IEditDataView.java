package com.scnu.sharenote.main.fragment.mine.ui.activity;

import com.scnu.base.ui.BaseView;

/**
 * Created by ChenZehao
 * on 2020/3/5
 */
public interface IEditDataView extends BaseView {
    /**
     * 更新用户名称
     */
    void updateUserNameSuccess(String name);

    /**
     * 更新用户头像
     */
    void updateUserAvatarSuccess(String avatarUrl);

    /**
     * 更新用户性别
     */
    void updateUserSexSuccess(String sex);

    /**
     * 更新用户生日
     */
    void updateUserBirthdaySuccess(String birthday);

    /**
     * 更新用户位置
     */
    void updateUserLocationSuccess(String location);

}
