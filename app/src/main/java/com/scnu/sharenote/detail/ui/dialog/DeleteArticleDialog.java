package com.scnu.sharenote.detail.ui.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scnu.base.ui.dialog.BaseDialogFragment;
import com.scnu.sharenote.R;
import com.scnu.utils.ToastUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChenZehao
 * on 2020/4/7
 */
public class DeleteArticleDialog extends BaseDialogFragment {

    public DeleteArticleDialog(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_dialog_delete, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.bt_cancel)
    void btCancelClicked(){
        dismiss();
    }

    @OnClick(R.id.bt_confirm)
    void btConfirmClicked(){
        if(null != deleteArticleListener){
            deleteArticleListener.delete();
        }
        dismiss();
    }

    public interface DeleteArticleListener{
        void delete();
    }

    private DeleteArticleListener deleteArticleListener;

    public void setDeleteArticleListener(DeleteArticleListener deleteArticleListener) {
        this.deleteArticleListener = deleteArticleListener;
    }
}
