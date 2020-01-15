package com.scnu.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.jaeger.library.StatusBarUtil;
import com.scnu.sharenote.R;
import com.scnu.utils.AppManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.appcompat.app.AppCompatActivity;
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
        StatusBarUtil.setLightMode(this);
        initHolder();
        initData();
        initLayoutParams();
        AppManager.getInstance().addActivity(this);

        //注册事件
        EventBus.getDefault().register(this);
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
     * 初始化控件
     */
    public abstract void initHolder();

    /**
     * 初始化布局
     */
    public abstract void initLayoutParams();

    /**
     * 初始化数据
     */
    public abstract void initData();

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

