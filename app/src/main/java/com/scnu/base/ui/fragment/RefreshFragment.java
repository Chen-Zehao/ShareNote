package com.scnu.base.ui.fragment;

import android.view.View;

import com.scnu.base.BasePresenter;
import com.scnu.base.ui.BaseView;
import com.scnu.base.ui.view.BaseEmptyView;
import com.scnu.sharenote.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by ChenZehao
 * on 2020/2/21
 */
public abstract class RefreshFragment<V extends BaseView, T extends BasePresenter<V>> extends BaseMvpFragment{

    @BindView(R.id.recycle_view)
    protected RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    protected SmartRefreshLayout refreshLayout;
    @BindView(R.id.empty_view)
    protected BaseEmptyView emptyView;

    protected RecyclerView.Adapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected RefreshListener refreshListener;
    protected int mPageNum = 1;
    protected final int RESULT_TYPE_ERROR = -1;
    protected final int RESULT_TYPE_EMPTY = -2;
    private boolean isEnableRefresh;
    private boolean isEnableLoadMore;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh;
    }


    @Override
    public void initView() {
        mAdapter = createAdapter();
        if (null != mAdapter) {
            recycleView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }

        mLayoutManager = createLayoutManager();
        if(null != mLayoutManager){
            recycleView.setLayoutManager(mLayoutManager);
        }

        isEnableRefresh = isNeedRefresh();
        isEnableLoadMore = isNeedLoadMore();
        refreshLayout.setEnableLoadMore(isEnableLoadMore);
        refreshLayout.setEnableRefresh(isEnableRefresh);
        refreshListener = getRefreshListener();
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            if (null != refreshListener) {
                mPageNum = 1;
                refreshListener.onRefresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (null != refreshListener) {
                refreshListener.onLoadMore();
            }
        });
    }

    @Override
    public void initData() {

    }
    /**
     * 创建适配器
     * @return
     */
    public abstract RecyclerView.Adapter createAdapter();

    /**
     * 创建LayoutManager
     * @return
     */
    public abstract RecyclerView.LayoutManager createLayoutManager();

    /**
     * RefreshListener回调，必须实现
     * @return
     */
    public abstract RefreshListener getRefreshListener();

    /**
     * 是否需要上拉加载
     * @return
     */
    public boolean isNeedLoadMore(){
        return false;
    }

    /**
     * 是否需要上拉刷新
     * @return
     */
    public boolean isNeedRefresh(){
        return true;
    }


    /**
     * 下拉刷新网络请求成功后调用，mPageNum会重置
     * @param isSuccess 是否成功
     * @param type 错误类型,isSuccess为false时必传，类型在父类中
     */
    protected void refreshDataFinish(boolean isSuccess,int type){
        if (isSuccess) {
            if (emptyView.isVisible()) {
                emptyView.setVisibility(View.GONE);
//                emptyView.hideView();
            }

            refreshLayout.finishRefresh();
            mPageNum++;
        } else {
            switch (type){
                case RESULT_TYPE_ERROR:
//                    showErrorView();
                    break;

                case RESULT_TYPE_EMPTY:
//                    showEmptyView();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 上拉加载网络请求成功后调用，mPageNum会+1
     * @return
     */
    protected void LoadMoreDataFinish(){
        refreshLayout.finishLoadMore();
        mPageNum++;
    }

    /**
     * 上拉加载没有更多数据时调用
     * 谨慎调用，确认无更多数据再调用，调用后关闭页面前上拉加载不可再次启用
     */
    protected void noMoreData(){
        refreshLayout.finishLoadMoreWithNoMoreData();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
