package com.scnu.sharenote.detail.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.scnu.base.ui.dialog.BaseDialogFragment;
import com.scnu.sharenote.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChenZehao
 * on 2020/4/7
 */
public class DeleteCommentDialog extends BaseDialogFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.bt_cancel)
    Button btCancel;
    @BindView(R.id.bt_confirm)
    Button btConfirm;

    public DeleteCommentDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_dialog_delete, container, false);
        ButterKnife.bind(this, view);
        tvName.setText("是否删除该评论？");
        return view;
    }

    @OnClick(R.id.bt_cancel)
    void btCancelClicked() {
        dismiss();
    }

    @OnClick(R.id.bt_confirm)
    void btConfirmClicked() {
        if (null != deleteCommentListener) {
            deleteCommentListener.delete();
        }
        dismiss();
    }

    public interface DeleteCommentListener {
        void delete();
    }

    private DeleteCommentListener deleteCommentListener;

    public void setDeleteCommentListener(DeleteCommentListener deleteCommentListener) {
        this.deleteCommentListener = deleteCommentListener;
    }
}
