package com.scnu.sharenote.main.fragment.mine.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.scnu.base.ui.fragment.BaseMvpFragment;
import com.scnu.custom.CircleImageView;
import com.scnu.enums.QueryTypeEnum;
import com.scnu.model.Macro;
import com.scnu.model.UserModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.home.ui.view.LazyLoadingPagerAdapter;
import com.scnu.sharenote.main.fragment.mine.presenter.MinePresenter;
import com.scnu.sharenote.main.fragment.mine.ui.activity.UserAttentionActivity;
import com.scnu.sharenote.main.fragment.mine.ui.activity.UserFollowersActivity;
import com.scnu.sharenote.main.fragment.mine.ui.activity.SettingActivity;
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
import butterknife.OnClick;


/**
 * Created by ChenZehao
 * on 2019/12/10
 */
public class MineFragment extends BaseMvpFragment implements IMineView {


    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.iv_setting)
    AppCompatImageView ivSetting;
    @BindView(R.id.iv_message)
    AppCompatImageView ivMessage;
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
    private MinePresenter minePresenter;

    private UserModel user;

    private ArrayList<Fragment> mFragments;
    private String[] titles = new String[]{"笔记", "收藏", "赞"};
    private LazyLoadingPagerAdapter pagerAdapter;

    public MineFragment(){}

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        minePresenter = new MinePresenter(mContext, this);
        if(null != MyApplication.getObject(Macro.KEY_USER)){
            user = (UserModel) MyApplication.getObject(Macro.KEY_USER);
            if (null != user) {
                minePresenter.getUserInfo(user.getUserId());
            }
        }
        mFragments = new ArrayList<>();
        mFragments.add(UserContentFragment.newInstance(QueryTypeEnum.getTypePublish(), user.getUserId()));
        mFragments.add(UserContentFragment.newInstance(QueryTypeEnum.getTypeCollect(), user.getUserId()));
        mFragments.add(UserContentFragment.newInstance(QueryTypeEnum.getTypeStar(), user.getUserId()));
        pagerAdapter = new LazyLoadingPagerAdapter(getChildFragmentManager(),mFragments);
        vpContent.setOffscreenPageLimit(mFragments.size());
        vpContent.setAdapter(pagerAdapter);
        //关联TabLayout与ViewPager
        tlMine.setViewPager(vpContent, titles);
        tlMine.setCurrentTab(0);
    }

    @Override
    public void initData() {
    }

    @OnClick(R.id.iv_avatar)
    void ivAvatarClicked(){
        List<LocalMedia> selectList = new ArrayList<>();
        LocalMedia localMedia = new LocalMedia();
        localMedia.setPath(ServerConfig.getInstance().getAppServerURL() + user.getAvatarUrl());
        selectList.add(localMedia);
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
        bundle.putString(Macro.KEY_USER_ID,user.getUserId());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick({R.id.tv_fens,R.id.tv_fens_num})
    void fenClicked(){
        Intent intent = new Intent(mContext, UserFollowersActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Macro.KEY_USER_ID,user.getUserId());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.iv_setting)
    void ivSettingClicked() {
        startActivity(new Intent(mContext, SettingActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        minePresenter.detach();
    }

    @Override
    public void getUserInfoSuccess(UserModel user) {
        this.user = user;
        tvName.setText(user.getName());
        GlideImageLoader.getInstance().loadImage(mContext, ServerConfig.getInstance().getAppServerURL() + user.getAvatarUrl(), ivAvatar);
        tvFollowNum.setText(user.getAttentionNum()+"");
        tvFensNum.setText(user.getFansNum()+"");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            if (null != user) {
                minePresenter.getUserInfo(user.getUserId());
            }
        }
    }
}
