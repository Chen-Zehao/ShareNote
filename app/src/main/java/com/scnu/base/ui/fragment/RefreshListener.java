package com.scnu.base.ui.fragment;

/**
 * Created by ChenZehao
 * on 2020/2/21
 */
public interface RefreshListener {
    /**
     * 下拉刷新
     */
    void onRefresh();

    /**
     * 上拉加载
     */
    void onLoadMore();
}
