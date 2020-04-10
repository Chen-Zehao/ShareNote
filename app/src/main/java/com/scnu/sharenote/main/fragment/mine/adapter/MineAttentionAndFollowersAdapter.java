package com.scnu.sharenote.main.fragment.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.scnu.base.BaseRecycleAdapter;
import com.scnu.base.BaseViewHolder;
import com.scnu.custom.CircleImageView;
import com.scnu.model.Macro;
import com.scnu.model.UserModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.mine.ui.activity.OtherUserActivity;
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
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ChenZehao
 * on 2020/4/2
 */
public class MineAttentionAndFollowersAdapter extends BaseRecycleAdapter<UserModel> {


    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_follow)
    TextView tvFollow;

    public MineAttentionAndFollowersAdapter(Context context, List<UserModel> data) {
        super(context, R.layout.mine_item_attention_and_followers, data);
    }

    @Override
    protected <T> void bindView(BaseViewHolder holder, T t) {
        ButterKnife.bind(this, holder.itemView);
        UserModel userModel = (UserModel) t;
        GlideImageLoader.getInstance().loadImage(mContext, ServerConfig.getInstance().getAppServerURL() + userModel.getAvatarUrl(), ivAvatar);
        tvName.setText(userModel.getName());
        if(userModel.isAttentionFlag()){
            tvFollow.setText("取消关注");
            tvFollow.setTextColor(mContext.getResources().getColor(R.color.text_gray));
            tvFollow.setBackground(mContext.getResources().getDrawable(R.drawable.mine_shape_has_followed));
        }else{
            tvFollow.setText("＋关注");
            tvFollow.setTextColor(mContext.getResources().getColor(R.color.text_orange));
            tvFollow.setBackground(mContext.getResources().getDrawable(R.drawable.mine_shape_follow));
        }
        tvFollow.setOnClickListener(view -> {
            UserModel user = (UserModel) MyApplication.getObject(Macro.KEY_USER);
            OnlineDataSource.getInstance().addAttention(userModel.getUserId(), user.getUserId(), new CustomObserver<BaseBean>(mContext,((AppCompatActivity)mContext).getSupportFragmentManager()) {
                @Override
                public void onSuccess(BaseBean result) {
                    userModel.setAttentionFlag(!userModel.isAttentionFlag());
                    notifyDataSetChanged();
                    if(userModel.isAttentionFlag()){
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
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, OtherUserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Macro.KEY_USER_ID,userModel.getUserId());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }
}
