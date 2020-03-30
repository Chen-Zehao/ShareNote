package com.scnu.sharenote.publish.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.scnu.base.BaseRecycleAdapter;
import com.scnu.base.BaseViewHolder;
import com.scnu.model.ThemeModel;
import com.scnu.sharenote.R;
import com.scnu.source.http.HttpUtils;
import com.scnu.utils.GlideImageLoader;
import com.scnu.utils.ServerConfig;

import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ChenZehao
 * on 2020/2/29
 */
public class ThemeAdapter extends BaseRecycleAdapter<ThemeModel> {

    @BindView(R.id.iv_theme)
    AppCompatImageView ivTheme;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.iv_select)
    AppCompatImageView ivSelect;

    public ThemeAdapter(Context context, List<ThemeModel> data) {
        super(context, R.layout.publish_item_theme, data);
    }

    @Override
    protected <T> void bindView(BaseViewHolder holder, T t) {
        ButterKnife.bind(this,holder.itemView);
        ThemeModel model = (ThemeModel) t;
        GlideImageLoader.getInstance().loadFilletImage(mContext, ServerConfig.getInstance().getAppServerURL() + model.getImage(),ivTheme);
        tvTheme.setText(model.getName());
        if(model.isSelected)
            ivSelect.setVisibility(View.VISIBLE);
        else
            ivSelect.setVisibility(View.GONE);
    }
}
