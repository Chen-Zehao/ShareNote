package com.scnu.sharenote.publish.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.scnu.base.BaseRecycleAdapter;
import com.scnu.base.BaseViewHolder;
import com.scnu.model.PriceModel;
import com.scnu.model.ThemeModel;
import com.scnu.sharenote.R;
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
public class PriceAdapter extends BaseRecycleAdapter<PriceModel> {

    @BindView(R.id.tv_price)
    TextView tvPrice;

    public PriceAdapter(Context context, List<PriceModel> data) {
        super(context, R.layout.publish_item_price, data);
    }

    @Override
    protected <T> void bindView(BaseViewHolder holder, T t) {
        ButterKnife.bind(this,holder.itemView);
        PriceModel data = (PriceModel) t;
        if(data.isSelected()){
            tvPrice.setBackground(mContext.getResources().getDrawable(R.drawable.shape_price_selected));
        }else{
            tvPrice.setBackground(mContext.getResources().getDrawable(R.drawable.shape_price_unselected));
        }
        tvPrice.setText(data.getPrice());
    }
}
