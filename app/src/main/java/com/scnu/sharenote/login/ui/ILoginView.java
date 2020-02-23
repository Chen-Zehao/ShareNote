package com.scnu.sharenote.login.ui;

import com.scnu.base.ui.BaseView;
import com.scnu.model.User;

/**
 * Created by ChenZehao
 * on 2019/12/5
 */
public interface ILoginView extends BaseView {

    /**
     * 用户登录成功
     * @param user
     */
    void userLoginSuccess();
}
