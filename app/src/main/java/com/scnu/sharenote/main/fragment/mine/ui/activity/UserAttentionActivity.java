package com.scnu.sharenote.main.fragment.mine.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.scnu.base.ui.activity.BaseMvpActivity;
import com.scnu.base.ui.view.titlebar.BaseTitleBar;
import com.scnu.model.Macro;
import com.scnu.model.UserModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.mine.adapter.MineAttentionAndFollowersAdapter;
import com.scnu.sharenote.main.fragment.mine.presenter.UserAttentionPresenter;
import com.scnu.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAttentionActivity extends BaseMvpActivity<UserAttentionView, UserAttentionPresenter> implements UserAttentionView {

    @BindView(R.id.title_bar)
    BaseTitleBar titleBar;
    @BindView(R.id.rv_attention)
    RecyclerView rvAttention;

    private MineAttentionAndFollowersAdapter attentionAndFollowersAdapter;

    private List<UserModel> userList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_attention;
    }

    @Override
    public void initView() {
        userList = new ArrayList<>();
        attentionAndFollowersAdapter = new MineAttentionAndFollowersAdapter(mContext,userList);
        rvAttention.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
        rvAttention.setAdapter(attentionAndFollowersAdapter);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if(null != intent){
            String userId = intent.getExtras().getString(Macro.KEY_USER_ID);
            presenter.getAttentionUsers(userId);
        }
    }

    @Override
    protected BaseTitleBar getTitleBar() {
        return titleBar;
    }

    @Override
    public UserAttentionPresenter initPresenter() {
        return new UserAttentionPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void getAttentionUsersSuccess(List<UserModel> userList) {
        this.userList.clear();
        this.userList.addAll(userList);
        attentionAndFollowersAdapter.notifyDataSetChanged();
    }
}
