package com.scnu.sharenote.main.fragment.home.fragment.recommend.ui;

import android.view.View;

import com.scnu.base.BaseMvpFragment;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.home.fragment.recommend.presenter.RecommendPresenter;

/**
 * Created by ChenZehao
 * on 2020/1/15
 */
public class RecommendFragment extends BaseMvpFragment<IRecommendView, RecommendPresenter> implements IRecommendView {
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
        return View.inflate(mContext, R.layout.recommend_fragment, null);
    }

    @Override
    public RecommendPresenter initPresenter() {
        return new RecommendPresenter();
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
