package com.scnu.sharenote.main.fragment.home.ui;

import com.flyco.tablayout.SlidingTabLayout;
import com.scnu.base.ui.fragment.BaseMvpFragment;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.home.fragment.follow.ui.FollowFragment;
import com.scnu.sharenote.main.fragment.home.fragment.local.ui.LocalFragment;
import com.scnu.sharenote.main.fragment.home.fragment.recommend.ui.RecommendFragment;
import com.scnu.sharenote.main.fragment.home.presenter.HomePresenter;
import com.scnu.sharenote.main.fragment.home.ui.view.LazyLoadingPagerAdapter;
import com.scnu.utils.LogUtils;

import java.util.ArrayList;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * Created by ChenZehao
 * on 2019/12/10
 */
public class HomeFragment extends BaseMvpFragment implements IHomeView {


    @BindView(R.id.tl_header)
    SlidingTabLayout tlHeader;
    @BindView(R.id.iv_search)
    AppCompatImageView ivSearch;
    @BindView(R.id.cl_header)
    ConstraintLayout clHeader;
    @BindView(R.id.vp_content)
    ViewPager vpContent;

    private HomePresenter homePresenter;

    private ArrayList<Fragment> mFragments;

    private LazyLoadingPagerAdapter pagerAdapter;

    private String[] titles = new String[]{"关注", "推荐", "同城"};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        homePresenter = new HomePresenter(mContext,this);
        mFragments = new ArrayList<>();
        mFragments.add(new FollowFragment());
        mFragments.add(new RecommendFragment());
        mFragments.add(new LocalFragment());
        pagerAdapter = new LazyLoadingPagerAdapter(getChildFragmentManager(),mFragments);
        vpContent.setOffscreenPageLimit(mFragments.size());
        vpContent.setAdapter(pagerAdapter);
        //关联TabLayout与ViewPager
        tlHeader.setViewPager(vpContent, titles);
        //初始显示推荐页面
        tlHeader.setCurrentTab(1);
    }

    @Override
    public void initData() {
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        homePresenter.detach();
    }

}
