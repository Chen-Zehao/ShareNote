package com.scnu.sharenote.main.fragment.home.fragment.follow.ui;

import com.scnu.base.ui.fragment.RefreshFragment;
import com.scnu.base.ui.fragment.RefreshListener;
import com.scnu.model.ArticleModel;
import com.scnu.model.PictureModel;
import com.scnu.model.UserModel;
import com.scnu.sharenote.main.fragment.home.adapter.ArticleAdapter;
import com.scnu.sharenote.main.fragment.home.fragment.follow.presenter.FollowPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by ChenZehao
 * on 2020/1/15
 */
public class FollowFragment extends RefreshFragment implements IFollowView,RefreshListener {

    private FollowPresenter followPresenter;

    private List<ArticleModel> articleList;

    @Override
    public void initView() {
        super.initView();
        followPresenter = new FollowPresenter(mContext,this);
    }

    @Override
    public void initData() {

    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        articleList = new ArrayList<>();
        return new ArticleAdapter(mContext,articleList);
    }

    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
    }

    @Override
    public RefreshListener getRefreshListener() {
        return this;
    }

    @Override
    public boolean isNeedRefresh() {
        return true;
    }

    @Override
    public boolean isNeedLoadMore() {
        return true;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        followPresenter.detach();
    }
}
