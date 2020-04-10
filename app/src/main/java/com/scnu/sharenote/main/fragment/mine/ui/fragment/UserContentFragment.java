package com.scnu.sharenote.main.fragment.mine.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scnu.base.BaseRecycleAdapter;
import com.scnu.base.ui.fragment.BaseMvpFragment;
import com.scnu.base.ui.fragment.RefreshListener;
import com.scnu.base.ui.view.BaseEmptyView;
import com.scnu.enums.QueryTypeEnum;
import com.scnu.eventbusmodel.DeleteArticleEvent;
import com.scnu.model.ArticleModel;
import com.scnu.model.Macro;
import com.scnu.sharenote.R;
import com.scnu.sharenote.detail.ui.DetailActivity;
import com.scnu.sharenote.main.fragment.home.adapter.ArticleAdapter;
import com.scnu.sharenote.main.fragment.mine.adapter.UserArticleAdapter;
import com.scnu.sharenote.main.fragment.mine.presenter.UserContentPresenter;
import com.scnu.utils.LogUtils;
import com.scnu.utils.MyApplication;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by ChenZehao
 * on 2020/4/1
 */
public class UserContentFragment extends BaseMvpFragment implements IUserContnetView, RefreshListener {
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.empty_view)
    BaseEmptyView emptyView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private UserContentPresenter userContentPresenter;

    protected RefreshListener refreshListener;
    protected int mPageNum = 1;

    private List<ArticleModel> articleList;

    private UserArticleAdapter articleAdapter;

    private String userId;

    /**
     * 1-笔记
     * 2-收藏
     * 3-赞
     */
    private String queryType;

    public UserContentFragment(){}


    public static UserContentFragment newInstance(String queryType, String userId) {
        Bundle args = new Bundle();
        args.putString("type",queryType);
        args.putString("userId",userId);
        UserContentFragment fragment = new UserContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_content;
    }


    @Override
    protected void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (getArguments() != null) {
            queryType = getArguments().getString("type");
            userId = getArguments().getString("userId");
        }
        userContentPresenter = new UserContentPresenter(mContext,this);
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setEnableRefresh(true);
        refreshListener = this;
        refreshLayout.setOnRefreshListener(refreshLayout1 -> {
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
        articleList = new ArrayList<>();
        articleAdapter = new UserArticleAdapter(mContext,articleList);
        articleAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("issueId",articleList.get(position).getIssueId());
                intent.putExtras(bundle);
                startActivity(intent,bundle);
            }
        });
        recycleView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
        recycleView.setAdapter(articleAdapter);
    }

    @Override
    protected void initData() {
        if(null != MyApplication.getObject(Macro.KEY_USER)) {
            userContentPresenter.queryArticleList(queryType, userId, mPageNum);
        }
    }

    /**
     * 删除文章后更新Recyclerview
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DeleteArticleEvent event){
        if(null != articleList){
            for(int i = 0; i < articleList.size(); i++){
                if(articleList.get(i).getIssueId().equals(event.getIssueId())){
                    articleList.remove(i);//集合移除该条
                    articleAdapter.notifyItemRemoved(i);//通知移除该条
                    articleAdapter.notifyItemRangeChanged(i,articleList.size() - i);//更新适配器这条后面列表的变化
                    break;
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        userContentPresenter.queryArticleList(queryType, userId, mPageNum);
    }

    @Override
    public void onLoadMore() {
        userContentPresenter.loadMoreArticle(queryType, userId, mPageNum);
    }

    @Override
    public void getArticleListSuccess(List<ArticleModel> articleModelList) {
        articleList.clear();
        articleList.addAll(articleModelList);
        if (null == articleList || articleList.isEmpty()) {
            refreshLayout.finishRefresh();
            showEmptyView();
        } else {
            if (emptyView.isVisible()) {
                emptyView.setVisibility(View.GONE);
            }
            articleAdapter.notifyDataSetChanged();
            refreshLayout.finishRefresh();
            mPageNum++;
        }
    }

    @Override
    public void getArticleListFailed() {
        refreshLayout.finishRefresh();
        showEmptyView();
    }

    @Override
    public void loadMoreArticleSuccess(List<ArticleModel> articleModelList, boolean isEnd) {
        loadMoreDataFinish();
        if(isEnd){
            noMoreData();
        }else{
            articleList.addAll(articleModelList);
            articleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadMoreArticleFailed() {
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userContentPresenter.detach();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 上拉加载网络请求成功后调用，mPageNum会+1
     * @return
     */
    protected void loadMoreDataFinish(){
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
    /**
     * 展示空页面
     */
    private void showEmptyView(){
        emptyView.setEmptyImage(R.drawable.ic_defaultpage_img_empty);
        emptyView.setVisibility(View.VISIBLE);
        emptyView.showEmptyView();
        refreshLayout.setEnableLoadMore(false);
    }

}
