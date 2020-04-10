package com.scnu.sharenote.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.scnu.base.ui.activity.BaseMvpActivity;
import com.scnu.base.ui.fragment.BaseMvpFragment;
import com.scnu.base.ui.view.titlebar.BaseTitleBar;
import com.scnu.model.Macro;
import com.scnu.model.UserModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.presenter.MainPresenter;
import com.scnu.sharenote.main.fragment.home.ui.HomeFragment;
import com.scnu.sharenote.main.fragment.mine.ui.fragment.MineFragment;
import com.scnu.sharenote.publish.ui.PublishActivity;
import com.scnu.utils.AppManager;
import com.scnu.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MainActivity extends BaseMvpActivity<IMainView, MainPresenter> implements IMainView {

    private static final String TAG = "MainActivity";

    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.iv_new_content)
    AppCompatImageView ivNewContent;
    @BindView(R.id.rb_mine)
    RadioButton rbMine;
    @BindView(R.id.rg_bottom)
    RadioGroup rgBottom;
    private List<BaseMvpFragment> mFragment;

    //选中的Fragment的对应的位置
    private int position;
    //上一个Fragment
    private BaseMvpFragment mLastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mFragment = new ArrayList<>();
        mFragment.add(new HomeFragment());
        mFragment.add(new MineFragment());
        rbHome.setChecked(true);
    }

    @Override
    public void initData() {
        UserModel user = (UserModel)MyApplication.getObject(Macro.KEY_USER);
    }

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected BaseTitleBar getTitleBar() {
        return null;
    }

    /**
     * listener
     */
    @OnCheckedChanged({R.id.rb_home, R.id.rb_mine})
    void onRadioButtonClicked(RadioButton radioButton) {
        boolean checked = radioButton.isChecked();
        switch (radioButton.getId()) {
            case R.id.rb_home:
                if (checked) {
                    position = 0;
                }
                break;
            case R.id.rb_mine:
                if (checked) {
                    position = 1;
                }
                break;
        }
        //根据位置得到对应的Fragment
        BaseMvpFragment to = getFragment();
        //替换
        switchFrament(mLastFragment,to);
    }

    /**
     *
     * @param from 刚显示的Fragment,马上就要被隐藏了
     * @param to 马上要切换到的Fragment，一会要显示
     */
    private void switchFrament(BaseMvpFragment from, BaseMvpFragment to) {
        if(from != to){
            mLastFragment = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //判断有没有被添加
            if(!to.isAdded()){
                //to没有被添加
                //from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //添加to
                if(to != null){
                    ft.add(R.id.fl_main,to).commit();
                }
            }else{
                //to已经被添加
                // from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //显示to
                if(to != null){
                    ft.show(to).commit();
                }
            }
        }

    }


    /**
     * 根据位置得到对应的Fragment
     * @return
     */
    private BaseMvpFragment getFragment() {
        BaseMvpFragment fragment = mFragment.get(position);
        return fragment;
    }

    @OnClick(R.id.iv_new_content)
    void btnPublishClicked(){
        startActivity(new Intent(mContext, PublishActivity.class));
    }

    /**
     * double backPress exit
     */
    private long mExitTime;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            mExitTime = System.currentTimeMillis();
            playShortToast("请再按一次返回键退出");
        } else {
            AppManager.getInstance().AppExit();
        }
    }


}
