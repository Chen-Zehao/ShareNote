package com.scnu.sharenote.main.fragment.home.ui;

import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.scnu.base.ui.BaseMvpFragment;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.home.fragment.follow.ui.FollowFragment;
import com.scnu.sharenote.main.fragment.home.fragment.local.ui.LocalFragment;
import com.scnu.sharenote.main.fragment.home.fragment.recommend.ui.RecommendFragment;
import com.scnu.sharenote.main.fragment.home.presenter.HomePresenter;

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
public class HomeFragment extends BaseMvpFragment<IHomeView, HomePresenter> implements IHomeView {


    @BindView(R.id.tl_header)
    SlidingTabLayout tlHeader;
    @BindView(R.id.iv_search)
    AppCompatImageView ivSearch;
    @BindView(R.id.cl_header)
    ConstraintLayout clHeader;
    @BindView(R.id.vp_content)
    ViewPager vpContent;

    private ArrayList<Fragment> mFragments;

    @Override
    public void initHolder() {

    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new FollowFragment());
        mFragments.add(new RecommendFragment());
        mFragments.add(new LocalFragment());
        //关联TabLayout与ViewPager
        tlHeader.setViewPager(vpContent, new String[]{"关注", "推荐", "同城"},getActivity(),mFragments);
        //初始显示推荐页面
        tlHeader.setCurrentTab(1);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        return view;
    }

    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter();
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
    public void onDestroy() {
        super.onDestroy();
    }

}
