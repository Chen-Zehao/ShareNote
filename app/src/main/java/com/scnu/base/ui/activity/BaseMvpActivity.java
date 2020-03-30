package com.scnu.base.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.scnu.base.BasePresenter;
import com.scnu.base.ui.BaseView;
import com.scnu.base.ui.view.titlebar.BaseTitleBar;
import com.scnu.base.ui.view.titlebar.TitleBarBackListener;
import com.scnu.sharenote.R;
import com.scnu.utils.AppManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import butterknife.ButterKnife;

/**
 * Created by ChenZehao
 * on 2019/12/5
 */

public abstract class BaseMvpActivity<V extends BaseView, T extends BasePresenter<V>> extends AppCompatActivity {

    public Context mContext;
    public T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = initPresenter();
        presenter.attach(this, (V) this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        //隐藏导航栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        mContext = this;
        ButterKnife.bind(this);
        //注册事件
        EventBus.getDefault().register(this);
        initView();
        initData();
        initStatesBar(isNeedImmersionBar(),getStatesBarColor());
        AppManager.getInstance().addActivity(this);
        initTitleBar();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object myEvent) {

    }

    /**
     * 设置布局文件
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化界面
     */
    public abstract void initView();


    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * TitleBar
     * @return
     */
    protected abstract BaseTitleBar getTitleBar();

    /**
     * 初始化通用titleBar，initView中直接调用
     * 需要定制则重新此方法
     *
     */
    protected void initTitleBar() {
        if (null != getTitleBar()) {
            getTitleBar().setTitleBarOnBackListener(new TitleBarBackListener() {
                @Override
                public void onBackClick() {
                    finish();
                }
            });
        }
    }

    /**
     * 设置系统状态栏颜色
     * @return
     */
    protected int getStatesBarColor(){
        return R.color.main_states_color;
    }

    /**
     * 是否需要沉浸式状态栏
     * @return
     */
    protected boolean isNeedImmersionBar(){
        return false;
    }

    /**
     * 系统状态栏
     */
    protected void initStatesBar(boolean isNeedImmersionBar,int color){
        if(!isNeedImmersionBar){
            //非沉浸式状态栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //默认状态栏
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(ContextCompat.getColor(mContext,color));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                getWindow().setStatusBarColor(ContextCompat.getColor(mContext,color));
            }
        } else {
            //沉浸式状态栏
            if (Build.VERSION.SDK_INT >= 21) {
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                decorView.setSystemUiVisibility(option);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    /**
     * toast
     */
    public void playShortToast(String str) {
        Toast toast=Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT);
        toast.setText(str);
        toast.show();
    }

    public void playLongToast(String str) {
        Toast toast=Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG);
        toast.setText(str);
        toast.show();
    }


    /**
     * string
     *
     * @param id
     */
    public String StringValue(int id) {
        return mContext.getResources().getString(id);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
        //注销事件
        EventBus.getDefault().unregister(this);
        AppManager.getInstance().removeActivity(this);
    }

    public abstract T initPresenter();

}

