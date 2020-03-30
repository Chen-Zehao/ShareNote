package com.scnu.sharenote.main.fragment.mine.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.scnu.base.ui.dialog.BaseDialogFragment;
import com.scnu.model.UserModel;
import com.scnu.sharenote.R;
import com.scnu.utils.MyApplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChenZehao
 * on 2020/3/30
 */
public class InputNameDialog extends BaseDialogFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.bt_cancel)
    Button btCancel;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    private Context mContext;

    public InputNameDialog(Context context) {
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_dialog_input_name, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.bt_cancel)
    void btCancelClicked(){
        dismiss();
    }

    @OnClick(R.id.bt_confirm)
    void btConfirmClicked(){
        dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        UserModel userModel = (UserModel) MyApplication.getObject("user");
        etName.setText(userModel.getName());
    }
}
