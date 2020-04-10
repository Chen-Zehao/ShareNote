package com.scnu.sharenote.main.fragment.mine.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.scnu.base.ui.activity.BaseMvpActivity;
import com.scnu.base.ui.view.titlebar.BaseTitleBar;
import com.scnu.custom.CircleImageView;
import com.scnu.enums.QueryTypeEnum;
import com.scnu.model.Macro;
import com.scnu.model.UserModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.home.ui.view.LazyLoadingPagerAdapter;
import com.scnu.sharenote.main.fragment.mine.presenter.OtherUserPresenter;
import com.scnu.sharenote.main.fragment.mine.ui.fragment.UserContentFragment;
import com.scnu.utils.GlideEngine;
import com.scnu.utils.GlideImageLoader;
import com.scnu.utils.MyApplication;
import com.scnu.utils.ServerConfig;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChenZehao
 * on 2020/4/5
 */
public class OtherUserActivity extends BaseMvpActivity<IOtherUserView, OtherUserPresenter> implements IOtherUserView {

    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_user_follow)
    TextView tvUserFollow;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_fens_num)
    TextView tvFensNum;
    @BindView(R.id.tv_fens)
    TextView tvFens;
    @BindView(R.id.tv_follow_num)
    TextView tvFollowNum;
    @BindView(R.id.tv_follow)
    TextView tvFollow;
    @BindView(R.id.cl_header)
    ConstraintLayout clHeader;
    @BindView(R.id.tl_mine)
    SlidingTabLayout tlMine;
    @BindView(R.id.vp_content)
    ViewPager vpContent;


    private String userId = "";

    private ArrayList<Fragment> mFragments;
    private String[] titles = new String[]{"笔记", "收藏", "赞"};
    private LazyLoadingPagerAdapter pagerAdapter;
    private UserModel userModel;
    List<LocalMedia> selectList = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_other_user;
    }

    @Override
    public void initView() {
        userModel = (UserModel) MyApplication.getObject(Macro.KEY_USER);
        Intent intent = getIntent();
        if (null != intent) {
            userId = intent.getExtras().getString(Macro.KEY_USER_ID);
            if(userModel.getUserId().equals(userId)){
                tvUserFollow.setVisibility(View.GONE);
            }
        }
        mFragments = new ArrayList<>();
        mFragments.add(UserContentFragment.newInstance(QueryTypeEnum.getTypePublish(),userId));
        mFragments.add(UserContentFragment.newInstance(QueryTypeEnum.getTypeCollect(),userId));
        mFragments.add(UserContentFragment.newInstance(QueryTypeEnum.getTypeStar(),userId));
        pagerAdapter = new LazyLoadingPagerAdapter(getSupportFragmentManager(),mFragments);
        vpContent.setOffscreenPageLimit(mFragments.size());
        vpContent.setAdapter(pagerAdapter);
        //关联TabLayout与ViewPager
        tlMine.setViewPager(vpContent, titles);
        tlMine.setCurrentTab(0);
    }

    @Override
    public void initData() {
        presenter.getUserInfo(userModel.getUserId(), userId);
    }

    @Override
    protected BaseTitleBar getTitleBar() {
        return null;
    }

    @Override
    public OtherUserPresenter initPresenter() {
        return new OtherUserPresenter();
    }

    @OnClick(R.id.iv_avatar)
    void ivAvatarClicked(){
        PictureSelector.create(this)
                .themeStyle(R.style.picture_default_style)
                .isNotPreviewDownload(true)
                .loadImageEngine(GlideEngine.createGlideEngine())
                .openExternalPreview(0, selectList);

    }


    @OnClick({R.id.tv_follow,R.id.tv_follow_num})
    void followClicked(){
        Intent intent = new Intent(mContext, UserAttentionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Macro.KEY_USER_ID,userId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick({R.id.tv_fens,R.id.tv_fens_num})
    void fenClicked(){
        Intent intent = new Intent(mContext, UserFollowersActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Macro.KEY_USER_ID,userId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.tv_user_follow)
    void tvUserFollowClicked(){
        presenter.addAttention(userId,userModel.getUserId());
    }

    @Override
    public void getUserInfoSuccess(UserModel user) {
        tvName.setText(user.getName());
        LocalMedia localMedia = new LocalMedia();
        localMedia.setPath(ServerConfig.getInstance().getAppServerURL() + user.getAvatarUrl());
        selectList.add(localMedia);
        GlideImageLoader.getInstance().loadImage(mContext, ServerConfig.getInstance().getAppServerURL() + user.getAvatarUrl(), ivAvatar);
        tvFollowNum.setText(user.getAttentionNum()+"");
        tvFensNum.setText(user.getFansNum()+"");
        if(user.isAttentionFlag()){
            tvUserFollow.setText("取消关注");
            tvUserFollow.setTextColor(mContext.getResources().getColor(R.color.text_gray));
            tvUserFollow.setBackground(mContext.getResources().getDrawable(R.drawable.mine_user_shape_has_followed));
        }else{
            tvUserFollow.setText("＋关注");
            tvUserFollow.setTextColor(mContext.getResources().getColor(R.color.text_orange));
            tvUserFollow.setBackground(mContext.getResources().getDrawable(R.drawable.mine_shape_follow));
        }
}

    @Override
    public void addAttentionSuccess() {
        if(tvUserFollow.getText().toString().equals("＋关注")){
            tvUserFollow.setText("取消关注");
            tvUserFollow.setTextColor(mContext.getResources().getColor(R.color.text_gray));
            tvUserFollow.setBackground(mContext.getResources().getDrawable(R.drawable.mine_user_shape_has_followed));
        }else{
            tvUserFollow.setText("＋关注");
            tvUserFollow.setTextColor(mContext.getResources().getColor(R.color.text_orange));
            tvUserFollow.setBackground(mContext.getResources().getDrawable(R.drawable.mine_shape_follow));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
