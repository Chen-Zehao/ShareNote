package com.scnu.sharenote.main.fragment.home.fragment.recommend.ui;

import android.content.Intent;
import android.view.View;

import com.scnu.base.BaseRecycleAdapter;
import com.scnu.base.ui.fragment.BaseMvpFragment;
import com.scnu.base.ui.fragment.RefreshFragment;
import com.scnu.base.ui.fragment.RefreshListener;
import com.scnu.model.Article;
import com.scnu.model.User;
import com.scnu.sharenote.R;
import com.scnu.sharenote.detail.DetailActivity;
import com.scnu.sharenote.main.fragment.home.adapter.ArticleAdapter;
import com.scnu.sharenote.main.fragment.home.fragment.recommend.presenter.RecommendPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by ChenZehao
 * on 2020/1/15
 */
public class RecommendFragment extends RefreshFragment<IRecommendView, RecommendPresenter> implements IRecommendView,RefreshListener {

    private List<Article> articleList;

    @Override
    public void initData() {

    }


    @Override
    public RecyclerView.Adapter createAdapter() {

        articleList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Article article = new Article();
            User user = new User();
            user.setAvatarUrl("http://b-ssl.duitang.com/uploads/item/201607/26/20160726185736_yPmrE.thumb.224_0.jpeg");
            user.setName("哈哈哈");
            article.setPublisher(user);
            article.setTime("2020-1-1 01:12:34");
            article.setTitle("哈哈哈");
            article.setCollectionNum(999);
            article.setLikeNum(999);
            article.setCommentNum(999);
            List<String> themeList = new ArrayList<>();
            themeList.add("旅游");
            article.setThemeList(themeList);
            article.setContent("超级漂亮");
            List<String> imageList = new ArrayList<>();
            imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1579262564179&di=e51e90db3d4247d0a626e3d1b446e8fb&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20170607%2F17abce67996540b38f7795355dfd0d3e_th.jpg");
            article.setImageList(imageList);
            article.setLocation("广东·广州");
            articleList.add(article);
        }
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

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
