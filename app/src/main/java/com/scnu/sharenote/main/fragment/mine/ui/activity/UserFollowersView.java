package com.scnu.sharenote.main.fragment.mine.ui.activity;

import com.scnu.base.ui.BaseView;
import com.scnu.model.UserModel;

import java.util.List;

/**
 * Created by ChenZehao
 * on 2020/4/2
 */
public interface UserFollowersView extends BaseView {
    void getFollowUsersSuccess(List<UserModel> userList);
}
