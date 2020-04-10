package com.scnu.sharenote.main.fragment.mine.ui.activity;

import com.scnu.base.ui.BaseView;
import com.scnu.model.UserModel;

import java.util.List;

/**
 * Created by ChenZehao
 * on 2020/4/2
 */
public interface UserAttentionView extends BaseView {
    void getAttentionUsersSuccess(List<UserModel> userList);
}
