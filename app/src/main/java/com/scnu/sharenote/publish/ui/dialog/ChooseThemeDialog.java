package com.scnu.sharenote.publish.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scnu.base.BaseRecycleAdapter;
import com.scnu.base.ui.dialog.BaseDialogFragment;
import com.scnu.model.ThemeModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.publish.adapter.ThemeAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChenZehao
 * on 2020/2/29
 */
public class ChooseThemeDialog extends BaseDialogFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_theme)
    RecyclerView rvTheme;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    private Context mContext;

    private List<ThemeModel> themeList;

    private ThemeAdapter themeAdapter;

    public ChooseThemeDialog(Context context, List<ThemeModel> themeList) {
        mContext = context;
        this.themeList = themeList;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_dialog_theme, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        themeAdapter = new ThemeAdapter(mContext,themeList);
        rvTheme.setLayoutManager(new GridLayoutManager(mContext,2,RecyclerView.VERTICAL,false));
        rvTheme.setAdapter(themeAdapter);
        themeAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                for(int i = 0; i < themeList.size(); i++){
                    themeList.get(i).isSelected = (position == i);
                    themeAdapter.notifyDataSetChanged();
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
        for(ThemeModel themeModel : themeList){
            if(themeModel.isSelected){
                if(null != themeChooseListener){
                    themeChooseListener.themeChosen(themeModel.getName());
                }
                break;
            }
        }
        dismiss();
    }

    public interface ThemeChooseListener{
        void themeChosen(String theme);
    }

    private ThemeChooseListener themeChooseListener;

    public void setThemeChooseListener(ThemeChooseListener themeChooseListener) {
        this.themeChooseListener = themeChooseListener;
    }
}
