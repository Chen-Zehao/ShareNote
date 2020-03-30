package com.scnu.sharenote.main.fragment.mine.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scnu.base.ui.activity.BaseMvpActivity;
import com.scnu.base.ui.view.titlebar.BaseTitleBar;
import com.scnu.model.Macro;
import com.scnu.sharenote.R;
import com.scnu.sharenote.login.ui.LoginActivity;
import com.scnu.sharenote.main.fragment.mine.presenter.SettingPresenter;
import com.scnu.utils.MyApplication;
import com.scnu.utils.ToastUtils;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChenZehao
 * on 2020/2/27
 */
public class SettingActivity extends BaseMvpActivity<ISettingView, SettingPresenter> implements ISettingView {


    @BindView(R.id.title_bar)
    BaseTitleBar titleBar;
    @BindView(R.id.iv_edit)
    AppCompatImageView ivEdit;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.iv_edit_next)
    AppCompatImageView ivEditNext;
    @BindView(R.id.cl_edit)
    ConstraintLayout clEdit;
    @BindView(R.id.iv_safety)
    AppCompatImageView ivSafety;
    @BindView(R.id.tv_safety)
    TextView tvSafety;
    @BindView(R.id.iv_safety_next)
    AppCompatImageView ivSafetyNext;
    @BindView(R.id.cl_safety)
    ConstraintLayout clSafety;
    @BindView(R.id.v_line)
    View vLine;
    @BindView(R.id.iv_logout)
    AppCompatImageView ivLogout;
    @BindView(R.id.tv_loggout)
    TextView tvLoggout;
    @BindView(R.id.iv_logout_next)
    AppCompatImageView ivLogoutNext;
    @BindView(R.id.cl_logout)
    ConstraintLayout clLogout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.cl_edit, R.id.cl_safety, R.id.cl_logout})
    void onClicked(View v) {
        int id = v.getId();
        if (id == R.id.cl_edit) {
            startActivity(new Intent(mContext,EditDataActivity.class));
        } else if (id == R.id.cl_safety) {
            startActivity(new Intent(mContext,SafetyActivity.class));
        } else if (id == R.id.cl_logout) {
            //清除用户信息
            MyApplication.setParaValue(Macro.KEY_IS_LOGIN,"N");
            MyApplication.saveObject(Macro.KEY_USER,null);
            startActivity(new Intent(mContext,LoginActivity.class));
            finish();
            ToastUtils.showToast(mContext,"退出登录成功");
        }
    }

    @Override
    protected BaseTitleBar getTitleBar() {
        return titleBar;
    }

    @Override
    public SettingPresenter initPresenter() {
        return new SettingPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
