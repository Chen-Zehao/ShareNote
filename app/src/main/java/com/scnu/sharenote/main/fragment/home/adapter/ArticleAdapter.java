package com.scnu.sharenote.main.fragment.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.scnu.base.BaseRecycleAdapter;
import com.scnu.base.BaseViewHolder;
import com.scnu.custom.CircleImageView;
import com.scnu.model.Article;
import com.scnu.sharenote.R;
import com.scnu.utils.GlideImageLoader;

import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChenZehao
 * on 2020/1/17
 */
public class ArticleAdapter extends BaseRecycleAdapter<Article> {

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

    public ArticleAdapter(Context context, List<Article> data) {
        super(context, R.layout.home_item_article, data);
    }

    @Override
    protected <T> void bindView(BaseViewHolder holder, T t) {
        ButterKnife.bind(this, holder.itemView);
        Article article = (Article) t;
        GlideImageLoader.getInstance().loadImage(mContext, "http://b-ssl.duitang.com/uploads/item/201607/26/20160726185736_yPmrE.thumb.224_0.jpeg", ivAvatar);
        tvName.setText(article.getPublisher().getName());
        tvTime.setText(article.getTime());
        String strTheme = "";
        for(String str:article.getThemeList()){
            strTheme += "#"+str+"#";
        }
        String strTitle = article.getTitle();
        ForegroundColorSpan themeSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_theme));
        ForegroundColorSpan contentSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_content));
        //使用SpannableString实现字体分颜色
        SpannableString str = new SpannableString (strTheme+"  "+strTitle);
        str.setSpan(themeSpan, 0, strTheme.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        str.setSpan(contentSpan,strTheme.length()+2,strTheme.length()+strTitle.length()+2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvContent.setText(str);
        if(article.getImageList() != null && !article.getImageList().isEmpty())
            GlideImageLoader.getInstance().loadImage(mContext, article.getImageList().get(0), ivCover);
    }

}
