package com.scnu.sharenote.publish.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scnu.base.BaseRecycleAdapter;
import com.scnu.base.ui.dialog.BaseDialogFragment;
import com.scnu.model.PictureModel;
import com.scnu.model.PriceModel;
import com.scnu.model.ThemeModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.publish.adapter.PriceAdapter;
import com.scnu.sharenote.publish.adapter.ThemeAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChenZehao
 * on 2020/2/29
 */
public class ChoosePriceDialog extends BaseDialogFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_price)
    RecyclerView rvPrice;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    private Context mContext;

    private List<PriceModel> priceList;

    private PriceAdapter priceAdapter;

    public ChoosePriceDialog(Context context,List<PriceModel> priceList) {
        mContext = context;
        this.priceList = priceList;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_dialog_price, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        priceAdapter = new PriceAdapter(mContext,priceList);
        rvPrice.setLayoutManager(new GridLayoutManager(mContext,2, LinearLayoutManager.VERTICAL,false));
        rvPrice.setAdapter(priceAdapter);
        priceAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                for(int i = 0; i < priceList.size(); i++){
                    priceList.get(i).setSelected((position == i));
                    priceAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnClick(R.id.tv_cancel)
    void tvCancelClicked(){
        dismiss();
    }

    @OnClick(R.id.tv_confirm)
    void tvConfirmClicked(){
        for(PriceModel priceModel : priceList){
            if(priceModel.isSelected()){
                if(null != priceChooseListener){
                    priceChooseListener.priceChosen(priceModel.getPrice());
                }
                break;
            }
        }
        dismiss();
    }

    public interface PriceChooseListener{
        void priceChosen(String theme);
    }

    private PriceChooseListener priceChooseListener;

    public void setPriceChooseListener(PriceChooseListener priceChooseListener) {
        this.priceChooseListener = priceChooseListener;
    }
}
