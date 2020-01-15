package com.scnu.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ChenZehao
 * on 2019/12/5
 */
public abstract class BaseMvpFragment<V extends BaseView, T extends BasePresenter<V>> extends Fragment {

    public T presenter;
    protected Context mContext;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View view = initView();
        unbinder = ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        presenter = initPresenter();
        presenter.attach(mContext, (V) this);
        //数据处理要在attach后，否则报空
        super.onActivityCreated(savedInstanceState);
        initHolder();
        initData();
        initLayoutParams();
        //注册事件
        EventBus.getDefault().register(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object myEvent) {

    }

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
     * 设置布局文件
     * @return
     */
    public abstract View initView();



    /**
     * toast
     */
    public void playShortToast(String str) {
        Toast toast=Toast.makeText(mContext.getApplicationContext(),str,Toast.LENGTH_SHORT);
        toast.setText(str);
        toast.show();
    }

    public void playLongToast(String str) {
        Toast toast=Toast.makeText(mContext.getApplicationContext(),str,Toast.LENGTH_LONG);
        toast.setText(str);
        toast.show();
    }

    /**
     * string
     * @param id
     */
    public String StringValue(int id){
        return mContext.getResources().getString(id);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        //注销事件
        EventBus.getDefault().unregister(this);
        if(unbinder != null)
            unbinder.unbind();
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    public abstract T initPresenter();

}
