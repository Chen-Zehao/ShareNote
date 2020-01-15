package com.scnu.sharenote.main.fragment.home.fragment.local.ui;

import android.view.View;

import com.scnu.base.BaseMvpFragment;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.home.fragment.local.presenter.LocalPresenter;

/**
 * Created by ChenZehao
 * on 2020/1/15
 */
public class LocalFragment extends BaseMvpFragment<ILocalView, LocalPresenter> implements ILocalView {
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
        return View.inflate(mContext, R.layout.local_fragment, null);
    }

    @Override
    public LocalPresenter initPresenter() {
        return new LocalPresenter();
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
