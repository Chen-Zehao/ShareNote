package com.scnu.sharenote.detail;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.scnu.base.ui.activity.BaseMvpActivity;
import com.scnu.base.ui.view.titlebar.BaseTitleBar;
import com.scnu.custom.CircleImageView;
import com.scnu.model.ArticleModel;
import com.scnu.model.PictureModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.detail.adapter.HeaderImgAdapter;
import com.scnu.utils.GlideImageLoader;
import com.scnu.utils.ServerConfig;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ChenZehao
 * on 2020/1/18
 */
public class DetailActivity extends BaseMvpActivity<IDetailView, DetailPresenter> implements IDetailView {

    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_follow)
    AppCompatImageView ivFollow;
    @BindView(R.id.tv_follow)
    TextView tvFollow;
    @BindView(R.id.cl_follow)
    ConstraintLayout clFollow;
    @BindView(R.id.iv_share)
    AppCompatImageView ivShare;
    @BindView(R.id.vp_header_img)
    ViewPager vpHeaderImg;
    @BindView(R.id.tv_index)
    TextView tvIndex;
    @BindView(R.id.cl_title_bar)
    ConstraintLayout clTitleBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fl_title)
    FrameLayout flTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.iv_location)
    AppCompatImageView ivLocation;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.cl_location)
    ConstraintLayout clLocation;
    @BindView(R.id.cl_main)
    ConstraintLayout clMain;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_comment_total_num)
    TextView tvCommentTotalNum;
    @BindView(R.id.cl_comment)
    ConstraintLayout clComment;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.nsv_main)
    NestedScrollView nsvMain;
    @BindView(R.id.iv_edit)
    AppCompatImageView ivEdit;
    @BindView(R.id.cl_edit)
    ConstraintLayout clEdit;
    @BindView(R.id.iv_collection)
    AppCompatImageView ivCollection;
    @BindView(R.id.tv_collection_num)
    TextView tvCollectionNum;
    @BindView(R.id.iv_like)
    AppCompatImageView ivLike;
    @BindView(R.id.tv_like_num)
    TextView tvLikeNum;
    @BindView(R.id.iv_comment)
    AppCompatImageView ivComment;
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;
    @BindView(R.id.cl_bottom)
    ConstraintLayout clBottom;
    private ArticleModel article;

    private HeaderImgAdapter headerImgAdapter;

    private List<ImageView> imageViews = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void initView() {

    }


    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            String issueId =  getIntent().getStringExtra("issueId");
            presenter.getArticleDetail(issueId);
        }
    }

    @Override
    protected BaseTitleBar getTitleBar() {
        return null;
    }

    @Override
    public DetailPresenter initPresenter() {
        return new DetailPresenter();
    }

    /**
     * 显示Banner图
     */
    private void initBannerView() {
        for (PictureModel item : article.getImageList()) {
            ImageView iv = new ImageView(mContext);
            GlideImageLoader.getInstance().loadImage(mContext, item.getImgUrl(), iv);
            imageViews.add(iv);
        }
        headerImgAdapter = new HeaderImgAdapter(imageViews, vpHeaderImg);
        vpHeaderImg.setAdapter(headerImgAdapter);
        if (imageViews.size() != 0) {
            tvIndex.setText(1 + "/" + imageViews.size());
        } else {
            tvIndex.setVisibility(View.GONE);
        }
        vpHeaderImg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                tvIndex.setText((position + 1) + "/" + imageViews.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    /**
     * 显示内容信息
     */
    private void initInfoView() {
        GlideImageLoader.getInstance().loadImage(mContext, ServerConfig.getInstance().getAppServerURL() + article.getUserAvatar(), ivAvatar);
        String strTheme ="#"+article.getTheme()+"#";
        String strTitle = article.getTitle();
        ForegroundColorSpan themeSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_blue));
        ForegroundColorSpan contentSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_title));
        //使用SpannableString实现字体分颜色
        SpannableString str = new SpannableString (strTheme+"  "+strTitle);
        str.setSpan(themeSpan, 0, strTheme.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        str.setSpan(contentSpan,strTheme.length()+2,strTheme.length()+strTitle.length()+2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        str.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), strTheme.length()+2,strTheme.length()+strTitle.length()+2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTitle.setText(str);
        tvName.setText(article.getUserName());
        tvTime.setText(article.getTime());
        tvContent.setText(article.getContent());
        tvLocation.setText(article.getLocation().getName());
        tvLikeNum.setText(article.getLikeNum()+"");
        tvCollectionNum.setText(article.getCollectionNum()+"");
        tvCommentNum.setText(article.getCommentNum()+"");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void getDetailSuccess(ArticleModel article) {
        this.article = article;
        initBannerView();
        initInfoView();
    }
}
