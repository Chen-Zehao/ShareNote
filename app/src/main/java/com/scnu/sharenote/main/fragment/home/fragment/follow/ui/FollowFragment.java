package com.scnu.sharenote.main.fragment.home.fragment.follow.ui;

import android.view.View;

import com.scnu.base.BaseMvpFragment;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.home.fragment.follow.presenter.FollowPresenter;

/**
 * Created by ChenZehao
 * on 2020/1/15
 */
public class FollowFragment extends BaseMvpFragment<IFollowView,FollowPresenter> implements IFollowView {
    @Override
    public void initHolder() {

    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {

    }

    @Override
    public View initView() {
        return View.inflate(mContext, R.layout.follow_fragment, null);
    }

    @Override
    public FollowPresenter initPresenter() {
        return new FollowPresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }
}
