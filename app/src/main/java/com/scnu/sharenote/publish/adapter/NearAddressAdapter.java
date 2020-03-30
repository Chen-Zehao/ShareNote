package com.scnu.sharenote.publish.adapter;

import android.content.Context;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.scnu.base.BaseRecycleAdapter;
import com.scnu.base.BaseViewHolder;
import com.scnu.sharenote.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ChenZehao
 * on 2020/3/1
 */
public class NearAddressAdapter extends BaseRecycleAdapter<PoiInfo> {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    public NearAddressAdapter(Context context, List<PoiInfo> data) {
        super(context, R.layout.publish_item_location, data);
    }

    @Override
    protected <T> void bindView(BaseViewHolder holder, T t) {
        ButterKnife.bind(this, holder.itemView);
        PoiInfo info = (PoiInfo) t;
        tvName.setText(info.getName());
        tvAddress.setText(info.getAddress());
    }
}
