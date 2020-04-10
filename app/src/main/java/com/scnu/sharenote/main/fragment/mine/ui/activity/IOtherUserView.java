package com.scnu.sharenote.main.fragment.mine.ui.activity;

import com.scnu.base.ui.BaseView;
import com.scnu.model.UserModel;

/**
 * Created by ChenZehao
 * on 2020/4/5
 */
public interface IOtherUserView extends BaseView {
    /**
     * 获取用户信息成功
     * @param user
     */
    void getUserInfoSuccess(UserModel user);

    /**
     * 关注操作成功
     */
    void addAttentionSuccess();
}
