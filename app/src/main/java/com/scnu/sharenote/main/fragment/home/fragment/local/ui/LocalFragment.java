package com.scnu.sharenote.main.fragment.home.fragment.local.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.scnu.base.ui.fragment.RefreshFragment;
import com.scnu.base.ui.fragment.RefreshListener;
import com.scnu.custom.citypicker.CityLocationListener;
import com.scnu.custom.citypicker.CityPicker;
import com.scnu.eventbusmodel.LocationInfoEvent;
import com.scnu.model.ArticleModel;
import com.scnu.model.PictureModel;
import com.scnu.model.UserModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.home.adapter.ArticleAdapter;
import com.scnu.sharenote.main.fragment.home.fragment.local.presenter.LocalPresenter;
import com.scnu.utils.LogUtils;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.scnu.model.Macro.REQUEST_PERMISSION_ACCESS_FINE_LOCATION;

/**
 * Created by ChenZehao
 * on 2020/1/15
 */
public class LocalFragment extends RefreshFragment implements ILocalView,RefreshListener {

    private LocalPresenter localPresenter;

    private List<ArticleModel> articleList;
    private ArticleAdapter articleAdapter;

    public LocationClient mLocationClient = null;
    private CityLocationListener cityLocationListener;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local;
    }

    @Override
    public void initView() {
        super.initView();
        localPresenter = new LocalPresenter(mContext,this);
    }

    @Override
    public void initData() {
        //注册事件
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        cityLocationListener = new CityLocationListener();
        //声明LocationClient类
        mLocationClient = new LocationClient(mContext);
        //注册监听函数
        mLocationClient.registerLocationListener(cityLocationListener);
        LocationClientOption option = new LocationClientOption();
        //默认是false，如果需要获取省市区以及详细地址等信息则需置为true
        option.setIsNeedAddress(true);
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        mLocationClient.setLocOption(option);

    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        articleList = new ArrayList<>();
        articleAdapter = new ArticleAdapter(mContext, articleList);
        return articleAdapter;
    }

    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public RefreshListener getRefreshListener() {
        return this;
    }


    private void showPermissionRequireDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext, R.style.center_dialog)
                .setCancelable(false)
                .create();
        Window window = alertDialog.getWindow();
        alertDialog.show();
        window.setContentView(R.layout.mine_dialog_require_permission);
        TextView tv_choose = (TextView) window.findViewById(R.id.tv_choose);
        tv_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                CityPicker.from(LocalFragment.this) //activity或者fragment
                        .enableAnimation(true)    //启用动画效果，默认无
                        .setLocatedCity(null) //APP自身已定位的城市，传null会自动定位（默认）
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                Toast.makeText(mContext, data.getName(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(mContext, "取消选择", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLocate() {
                                mLocationClient.start();
                            }
                        })
                        .show();
            }
        });
        TextView tv_open = (TextView) window.findViewById(R.id.tv_open);
        tv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_ACCESS_FINE_LOCATION);
                alertDialog.dismiss();
            }
        });
    }

    /**
     * 定位完成之后更新数据
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LocationInfoEvent event){
        LogUtils.e(JSON.toJSONString(event));
        CityPicker.from(LocalFragment.this)
                .locateComplete(new LocatedCity(event.getCity(), event.getProvince(), "0"), LocateState.SUCCESS);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    articleAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "位置权限被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Fragment要用本身申请权限，不可用ActivityCompat.requestPermissions
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //没有权限需要弹框
            showPermissionRequireDialog();
        }
    }

    @Override
    public void onDestroy() {
        //注册事件
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        mLocationClient.unRegisterLocationListener(cityLocationListener);
        super.onDestroy();
        localPresenter.detach();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
