package com.scnu.sharenote.main.fragment.mine.ui;

import android.view.View;

import com.scnu.base.ui.BaseMvpFragment;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.mine.presenter.MinePresenter;


/**
 * Created by ChenZehao
 * on 2019/12/10
 */
public class MineFragment extends BaseMvpFragment<IMineView, MinePresenter> implements IMineView{
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
        View view = View.inflate(mContext, R.layout.fragment_mine, null);
        return view;
    }

    @Override
    public MinePresenter initPresenter() {
        return new MinePresenter();
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
