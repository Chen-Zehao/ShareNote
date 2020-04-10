package com.scnu.sharenote.main.fragment.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.scnu.base.BaseRecycleAdapter;
import com.scnu.base.BaseViewHolder;
import com.scnu.custom.CircleImageView;
import com.scnu.model.ArticleModel;
import com.scnu.model.Macro;
import com.scnu.model.UserModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.mine.ui.activity.OtherUserActivity;
import com.scnu.sharenote.main.fragment.mine.ui.activity.UserAttentionActivity;
import com.scnu.source.beans.BaseBean;
import com.scnu.source.http.CustomObserver;
import com.scnu.source.http.OnlineDataSource;
import com.scnu.utils.GlideImageLoader;
import com.scnu.utils.LogUtils;
import com.scnu.utils.MyApplication;
import com.scnu.utils.ServerConfig;
import com.scnu.utils.ToastUtils;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.tv_follow)
    TextView tvFollow;
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
        UserModel user = (UserModel) MyApplication.getObject(Macro.KEY_USER);
        GlideImageLoader.getInstance().loadImage(mContext, ServerConfig.getInstance().getAppServerURL() + article.getUserAvatar(), ivAvatar);
        tvName.setText(article.getUserName());
        tvTime.setText(article.getTime());
        if(!TextUtils.isEmpty(article.getTheme())){
            String strTheme = "#"+article.getTheme()+"#";
            String strTitle = article.getTitle();
            ForegroundColorSpan themeSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_blue));
            ForegroundColorSpan contentSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_content));
            //使用SpannableString实现字体分颜色
            SpannableString str = new SpannableString (strTheme+"  "+strTitle);
            str.setSpan(themeSpan, 0, strTheme.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            str.setSpan(contentSpan,strTheme.length()+2,strTheme.length()+strTitle.length()+2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tvContent.setText(str);
        }else{
            tvContent.setText(article.getTitle());
        }
        if(TextUtils.isEmpty(article.getPicture())){
            ivCover.setVisibility(View.GONE);
        }else{
            ivCover.setVisibility(View.VISIBLE);
            GlideImageLoader.getInstance().loadImage(mContext, ServerConfig.getInstance().getAppServerURL() + article.getPicture(), ivCover);
        }
        if(null == article.getLocation() || TextUtils.isEmpty(article.getLocation().getCity())){
            clLocation.setVisibility(View.GONE);
        }else{
            clLocation.setVisibility(View.VISIBLE);
            if(article.getDistance() == -1){
                tvLocation.setText(article.getLocation().getProvince()+"·"+article.getLocation().getCity());
            }else{
                if(article.getDistance() >= 1000){
                    double distance = article.getDistance()/1000;
                    tvLocation.setText(String.format("%.2f", distance)+"km");
                }else{
                    tvLocation.setText((int)article.getDistance()+"m");
                }
            }
        }
        if(article.isLikeFlag()){
            ivLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_common_like_selected));
        }else{
            ivLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_common_like));
        }
        if (article.isCollectionFlag()){
            ivCollection.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_common_collection_selected));
        }else{
            ivCollection.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_common_collection));
        }
        tvLikeNum.setText(article.getLikeNum()+"");
        tvCollectionNum.setText(article.getCollectionNum()+"");
        tvCommentNum.setText(article.getCommentNum()+"");
        if(article.getUserId().equals(user.getUserId())){
            tvFollow.setVisibility(View.GONE);
        }else{
            tvFollow.setVisibility(View.VISIBLE);
        }
        if(article.isAttentionFlag()){
            tvFollow.setText("取消关注");
            tvFollow.setTextColor(mContext.getResources().getColor(R.color.text_gray));
            tvFollow.setBackground(mContext.getResources().getDrawable(R.drawable.mine_shape_has_followed));
        }else{
            tvFollow.setText("＋关注");
            tvFollow.setTextColor(mContext.getResources().getColor(R.color.text_orange));
            tvFollow.setBackground(mContext.getResources().getDrawable(R.drawable.mine_shape_follow));
        }
        ivLike.setOnClickListener(view -> {
            OnlineDataSource.getInstance().addLike(article.getIssueId(), user.getUserId(), new CustomObserver<BaseBean>(mContext,((AppCompatActivity)mContext).getSupportFragmentManager()) {
                @Override
                public void onSuccess(BaseBean result) {
                    article.setLikeFlag(!article.isLikeFlag());
                    if(article.isLikeFlag()){
                        article.setLikeNum(article.getLikeNum()+1);
                    }else{
                        article.setLikeNum(article.getLikeNum()-1);
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onFail(Throwable exception) {
                    super.onFail(exception);
                    ToastUtils.showToast(mContext,exception.getMessage());
                }
            });
        });
        tvLikeNum.setOnClickListener(view -> {
            OnlineDataSource.getInstance().addLike(article.getIssueId(), user.getUserId(), new CustomObserver<BaseBean>(mContext,((AppCompatActivity)mContext).getSupportFragmentManager()) {
                @Override
                public void onSuccess(BaseBean result) {
                    article.setLikeFlag(!article.isLikeFlag());
                    if(article.isLikeFlag()){
                        article.setLikeNum(article.getLikeNum()+1);
                    }else{
                        article.setLikeNum(article.getLikeNum()-1);
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onFail(Throwable exception) {
                    super.onFail(exception);
                    ToastUtils.showToast(mContext,exception.getMessage());
                }
            });
        });
        ivCollection.setOnClickListener(view -> {
            OnlineDataSource.getInstance().addCollection(article.getIssueId(), user.getUserId(), new CustomObserver<BaseBean>(mContext,((AppCompatActivity)mContext).getSupportFragmentManager()) {
                @Override
                public void onSuccess(BaseBean result) {
                    article.setCollectionFlag(!article.isCollectionFlag());
                    if(article.isCollectionFlag()){
                        article.setCollectionNum(article.getCollectionNum()+1);
                    }else{
                        article.setCollectionNum(article.getCollectionNum()-1);
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onFail(Throwable exception) {
                    super.onFail(exception);
                    ToastUtils.showToast(mContext,exception.getMessage());
                }
            });
        });
        tvCollectionNum.setOnClickListener(view -> {
            OnlineDataSource.getInstance().addCollection(article.getIssueId(), user.getUserId(), new CustomObserver<BaseBean>(mContext,((AppCompatActivity)mContext).getSupportFragmentManager()) {
                @Override
                public void onSuccess(BaseBean result) {
                    article.setCollectionFlag(!article.isCollectionFlag());
                    if(article.isCollectionFlag()){
                        article.setCollectionNum(article.getCollectionNum()+1);
                    }else{
                        article.setCollectionNum(article.getCollectionNum()-1);
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onFail(Throwable exception) {
                    super.onFail(exception);
                    ToastUtils.showToast(mContext,exception.getMessage());
                }
            });
        });
        tvFollow.setOnClickListener(view -> {
            OnlineDataSource.getInstance().addAttention(article.getUserId(), user.getUserId(), new CustomObserver<BaseBean>(mContext,((AppCompatActivity)mContext).getSupportFragmentManager()) {
                @Override
                public void onSuccess(BaseBean result) {
                    article.setAttentionFlag(!article.isAttentionFlag());
                    notifyDataSetChanged();
                    if(article.isAttentionFlag()){
                        ToastUtils.showToast(mContext,"关注成功");
                    }else{
                        ToastUtils.showToast(mContext,"取消关注成功");
                    }
                }
                @Override
                public void onFail(Throwable exception) {
                    super.onFail(exception);
                    ToastUtils.showToast(mContext,exception.getMessage());
                }
            });
        });
        ivAvatar.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, OtherUserActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Macro.KEY_USER_ID,article.getUserId());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
    }

}
