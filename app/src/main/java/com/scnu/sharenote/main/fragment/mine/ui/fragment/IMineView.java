package com.scnu.sharenote.main.fragment.mine.ui.fragment;

import com.scnu.base.ui.BaseView;
import com.scnu.model.UserModel;

/**
 * Created by ChenZehao
 * on 2019/12/10
 */
public interface IMineView extends BaseView {
    void getUserInfoSuccess(UserModel user);
}
