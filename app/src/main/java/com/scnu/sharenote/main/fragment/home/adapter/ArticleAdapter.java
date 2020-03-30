package com.scnu.sharenote.main.fragment.home.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.scnu.base.BaseRecycleAdapter;
import com.scnu.base.BaseViewHolder;
import com.scnu.custom.CircleImageView;
import com.scnu.model.ArticleModel;
import com.scnu.sharenote.R;
import com.scnu.utils.GlideImageLoader;
import com.scnu.utils.ServerConfig;

import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ChenZehao
 * on 2020/1/17
 */
public class ArticleAdapter extends BaseRecycleAdapter<ArticleModel> {

    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_follow)
    AppCompatImageView ivFollow;
    @BindView(R.id.tv_follow)
    TextView tvFollow;
    @BindView(R.id.cl_follow)
    ConstraintLayout clFollow;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.fl_content)
    FrameLayout clContent;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
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
    @BindView(R.id.iv_location)
    AppCompatImageView ivLocation;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.cl_location)
    ConstraintLayout clLocation;

    public ArticleAdapter(Context context, List<ArticleModel> data) {
        super(context, R.layout.home_item_article, data);
    }

    @Override
    protected <T> void bindView(BaseViewHolder holder, T t) {
        ButterKnife.bind(this, holder.itemView);
        ArticleModel article = (ArticleModel) t;
        GlideImageLoader.getInstance().loadImage(mContext, ServerConfig.getInstance().getAppServerURL() + article.getUserAvatar(), ivAvatar);
        tvName.setText(article.getUserName());
        tvTime.setText(article.getTime());
        String strTheme = "#"+article.getTheme()+"#";
        String strTitle = article.getTitle();
        ForegroundColorSpan themeSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_blue));
        ForegroundColorSpan contentSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_content));
        //使用SpannableString实现字体分颜色
        SpannableString str = new SpannableString (strTheme+"  "+strTitle);
        str.setSpan(themeSpan, 0, strTheme.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        str.setSpan(contentSpan,strTheme.length()+2,strTheme.length()+strTitle.length()+2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvContent.setText(str);
        GlideImageLoader.getInstance().loadImage(mContext, ServerConfig.getInstance().getAppServerURL() + article.getPicture(), ivCover);
        tvLocation.setText(article.getLocation().getProvince()+"·"+article.getLocation().getCity());
        tvLikeNum.setText(article.getLikeNum()+"");
        tvCollectionNum.setText(article.getCollectionNum()+"");
        tvCommentNum.setText(article.getCommentNum()+"");
    }

}
