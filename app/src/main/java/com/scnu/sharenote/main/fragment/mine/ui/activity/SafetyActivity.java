package com.scnu.sharenote.main.fragment.mine.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.scnu.base.ui.activity.BaseMvpActivity;
import com.scnu.base.ui.view.titlebar.BaseTitleBar;
import com.scnu.model.Macro;
import com.scnu.model.UserModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.mine.presenter.SafetyPresenter;
import com.scnu.utils.MyApplication;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ChenZehao
 * on 2020/3/5
 */
public class SafetyActivity extends BaseMvpActivity<ISafetyView, SafetyPresenter> implements ISafetyView {

    @BindView(R.id.title_bar)
    BaseTitleBar titleBar;
    @BindView(R.id.iv_tel_next)
    AppCompatImageView ivTelNext;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.cl_tel)
    ConstraintLayout clTel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_safety;
    }

    @Override
    public void initView() {
        UserModel user = (UserModel) MyApplication.getObject(Macro.KEY_USER);
        if(null != user){
            if(!TextUtils.isEmpty(user.getMobileNo()))
                tvTel.setText(user.getMobileNo());
        }
    }

    @Override
    public void initData() {

    }

    @Override
    protected BaseTitleBar getTitleBar() {
        return titleBar;
    }

    @Override
    public SafetyPresenter initPresenter() {
        return new SafetyPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
