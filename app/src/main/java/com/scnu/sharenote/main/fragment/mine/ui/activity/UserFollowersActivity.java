package com.scnu.sharenote.main.fragment.mine.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.scnu.base.ui.activity.BaseMvpActivity;
import com.scnu.base.ui.view.titlebar.BaseTitleBar;
import com.scnu.model.Macro;
import com.scnu.model.UserModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.mine.adapter.MineAttentionAndFollowersAdapter;
import com.scnu.sharenote.main.fragment.mine.presenter.UserFollowersPresenter;
import com.scnu.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFollowersActivity extends BaseMvpActivity<UserFollowersView, UserFollowersPresenter> implements UserFollowersView {

    @BindView(R.id.title_bar)
    BaseTitleBar titleBar;
    @BindView(R.id.rv_followers)
    RecyclerView rvFollowers;

    private MineAttentionAndFollowersAdapter attentionAndFollowersAdapter;

    private List<UserModel> userList;


    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_followers;
    }

    @Override
    public void initView() {
        userList = new ArrayList<>();
        attentionAndFollowersAdapter = new MineAttentionAndFollowersAdapter(mContext,userList);
        rvFollowers.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
        rvFollowers.setAdapter(attentionAndFollowersAdapter);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if(null != intent){
            String userId = intent.getExtras().getString(Macro.KEY_USER_ID);
            presenter.getFollowUsers(userId);
        }
    }

    @Override
    protected BaseTitleBar getTitleBar() {
        return titleBar;
    }

    @Override
    public UserFollowersPresenter initPresenter() {
        return new UserFollowersPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void getFollowUsersSuccess(List<UserModel> userList) {
        this.userList.clear();
        this.userList.addAll(userList);
        attentionAndFollowersAdapter.notifyDataSetChanged();
    }
}
