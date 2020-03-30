package com.scnu.base.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.scnu.base.BasePresenter;
import com.scnu.base.ui.BaseView;

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
public abstract class BaseMvpFragment extends Fragment {

    protected Context mContext;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(getLayoutId(),container,false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    /**
     * 设置布局文件
     * @return
     */
    protected abstract int getLayoutId();


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //数据处理要在attach后，否则报空
        super.onActivityCreated(savedInstanceState);
        initData();
    }


    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化view
     * @return
     */
    protected abstract void initView();



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
        if(unbinder != null)
            unbinder.unbind();
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
