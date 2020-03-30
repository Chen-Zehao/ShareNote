package com.scnu.sharenote.main.fragment.mine.ui.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import com.scnu.base.ui.fragment.BaseMvpFragment;
import com.scnu.custom.CircleImageView;
import com.scnu.model.Macro;
import com.scnu.model.UserModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.mine.presenter.MinePresenter;
import com.scnu.sharenote.main.fragment.mine.ui.activity.SettingActivity;
import com.scnu.utils.MyApplication;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by ChenZehao
 * on 2019/12/10
 */
public class MineFragment extends BaseMvpFragment implements IMineView {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.iv_setting)
    AppCompatImageView ivSetting;
    @BindView(R.id.iv_message)
    AppCompatImageView ivMessage;
    @BindView(R.id.tv_fens_num)
    TextView tvFensNum;
    @BindView(R.id.tv_fens)
    TextView tvFens;
    @BindView(R.id.tv_follow_num)
    TextView tvFollowNum;
    @BindView(R.id.tv_follow)
    TextView tvFollow;
    @BindView(R.id.cl_header)
    ConstraintLayout clHeader;

    private MinePresenter minePresenter;

    private UserModel user;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        minePresenter = new MinePresenter(mContext,this);
        user = (UserModel) MyApplication.getObject(Macro.KEY_USER);
        if(null != user){
            if(!TextUtils.isEmpty(user.getName())){
                tvName.setText(user.getName());
            }
        }
    }

    @Override
    public void initData() {


    }

    @OnClick(R.id.iv_setting)
    void ivSettingClicked(){
        startActivity(new Intent(mContext, SettingActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        minePresenter.detach();
    }
}
