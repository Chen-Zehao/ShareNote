package com.scnu.sharenote.main.fragment.home.fragment.follow.ui;

import android.view.View;

import com.scnu.base.ui.BaseMvpFragment;
import com.scnu.model.Article;
import com.scnu.model.User;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.home.adapter.ArticleAdapter;
import com.scnu.sharenote.main.fragment.home.fragment.follow.presenter.FollowPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by ChenZehao
 * on 2020/1/15
 */
public class FollowFragment extends BaseMvpFragment<IFollowView,FollowPresenter> implements IFollowView {

    @BindView(R.id.rv_article)
    RecyclerView rvArticle;

    private List<Article> articleList;

    private ArticleAdapter articleAdapter;

    @Override
    public void initHolder() {

    }

    @Override
    public void initLayoutParams() {
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
        articleAdapter = new ArticleAdapter(mContext,articleList);
        rvArticle.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        rvArticle.setAdapter(articleAdapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public View initView() {
        return View.inflate(mContext, R.layout.fragment_follow, null);
    }

    @Override
    public FollowPresenter initPresenter() {
        return new FollowPresenter();
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
