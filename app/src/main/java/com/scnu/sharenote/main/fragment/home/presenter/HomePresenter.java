package com.scnu.sharenote.main.fragment.home.presenter;

import android.content.Context;

import com.scnu.base.BasePresenter;
import com.scnu.sharenote.main.fragment.home.ui.IHomeView;

/**
 * Created by ChenZehao
 * on 2019/12/10
 */
public class HomePresenter extends BasePresenter<IHomeView> {
    public HomePresenter(Context context, IHomeView view) {
        super(context, view);
    }
}
