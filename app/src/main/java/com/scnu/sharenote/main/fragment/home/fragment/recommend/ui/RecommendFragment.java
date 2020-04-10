package com.scnu.sharenote.main.fragment.home.fragment.recommend.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.scnu.base.BaseRecycleAdapter;
import com.scnu.base.ui.fragment.RefreshFragment;
import com.scnu.base.ui.fragment.RefreshListener;
import com.scnu.eventbusmodel.DeleteArticleEvent;
import com.scnu.model.ArticleModel;
import com.scnu.model.Macro;
import com.scnu.sharenote.detail.ui.DetailActivity;
import com.scnu.sharenote.main.fragment.home.adapter.ArticleAdapter;
import com.scnu.sharenote.main.fragment.home.fragment.recommend.presenter.RecommendPresenter;
import com.scnu.utils.LogUtils;
import com.scnu.utils.MyApplication;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by ChenZehao
 * on 2020/1/15
 */
public class RecommendFragment extends RefreshFragment implements IRecommendView,RefreshListener {

    private RecommendPresenter recommendPresenter;

    private List<ArticleModel> articleList;

    private ArticleAdapter articleAdapter;

    public RecommendFragment(){}

    @Override
    public void initView() {
        super.initView();
        //注册事件
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        recommendPresenter = new RecommendPresenter(mContext,this);
        emptyView.setEmptyDesc("暂无文章数据哦～");
    }

    @Override
    public void initData() {
        if(null != MyApplication.getObject(Macro.KEY_USER)){
            recommendPresenter.queryArticleList(mPageNum);
        }
    }


    @Override
    public RecyclerView.Adapter createAdapter() {
        articleList = new ArrayList<>();
        articleAdapter = new ArticleAdapter(mContext,articleList);
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
        return articleAdapter;
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
        recommendPresenter.queryArticleList(mPageNum);
    }

    @Override
    public void onLoadMore() {
        recommendPresenter.loadMoreArticle(mPageNum);
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
    public void getArticleListSuccess(List<ArticleModel> articleModelList) {
        articleList.clear();
        articleList.addAll(articleModelList);
        if (null == articleList || articleList.isEmpty()) {
            refreshDataFinish(false, RESULT_TYPE_EMPTY);
        } else {
            articleAdapter.notifyDataSetChanged();
            refreshDataFinish(true, 0);
        }
    }

    @Override
    public void getArticleListFailed() {
        refreshDataFinish(false, RESULT_TYPE_EMPTY);
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
    public void onDestroy() {
        super.onDestroy();
        recommendPresenter.detach();
        //注册事件
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
