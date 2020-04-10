package com.scnu.sharenote.search.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.scnu.base.BaseRecycleAdapter;
import com.scnu.base.ui.activity.BaseMvpActivity;
import com.scnu.base.ui.view.BaseEmptyView;
import com.scnu.base.ui.view.titlebar.BaseTitleBar;
import com.scnu.model.ArticleModel;
import com.scnu.model.Macro;
import com.scnu.model.UserModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.detail.ui.DetailActivity;
import com.scnu.sharenote.main.fragment.home.adapter.ArticleAdapter;
import com.scnu.sharenote.search.presenter.SearchPresenter;
import com.scnu.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

/**
 * Created by ChenZehao
 * on 2020/4/8
 */
public class SearchActivity extends BaseMvpActivity<ISearchView, SearchPresenter> implements ISearchView {


    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.cl_search)
    ConstraintLayout clSearch;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    @BindView(R.id.empty_view)
    BaseEmptyView emptyView;

    private UserModel userModel;

    private ArticleAdapter articleAdapter;

    private List<ArticleModel> articleList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        emptyView.setEmptyDesc("没有找到相关内容 换个词试试吧");
        emptyView.setEmptyImage(R.drawable.ic_defaultpage_img_empty);
        articleList = new ArrayList<>();
        userModel = (UserModel) MyApplication.getObject(Macro.KEY_USER);
        rvSearch.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        articleAdapter = new ArticleAdapter(mContext, articleList);
        rvSearch.setAdapter(articleAdapter);
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
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.iv_back)
    void ivBackClicked(){
        finish();
    }

    @OnEditorAction(R.id.et_search)
    boolean onEditorAction(TextView v, int actionId, KeyEvent event){
        if(actionId == EditorInfo.IME_ACTION_SEARCH){
            //如果actionId是搜索的id，则进行下一步的操作
            presenter.searchArticle(v.getText().toString(),userModel.getUserId());
        }
        return false;
    }

    @Override
    protected BaseTitleBar getTitleBar() {
        return null;
    }

    @Override
    public SearchPresenter initPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 展示空页面
     */
    private void showEmptyView(){
        emptyView.setVisibility(View.VISIBLE);
        emptyView.showEmptyView();
    }

    @Override
    public void searchArticleSuccess(List<ArticleModel> articleList) {
        if(null == articleList || articleList.isEmpty()){
            showEmptyView();
        }else{
            if (emptyView.isVisible()) {
                emptyView.setVisibility(View.GONE);
            }
            this.articleList.clear();
            this.articleList.addAll(articleList);
            articleAdapter.notifyDataSetChanged();
        }
    }
}
