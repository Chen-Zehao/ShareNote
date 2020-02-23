package com.scnu.sharenote.main.fragment.mine.ui;

import com.scnu.base.ui.fragment.BaseMvpFragment;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.mine.presenter.MinePresenter;


/**
 * Created by ChenZehao
 * on 2019/12/10
 */
public class MineFragment extends BaseMvpFragment<IMineView, MinePresenter> implements IMineView{

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initData() {


    }

    @Override
    public void initView() {
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
