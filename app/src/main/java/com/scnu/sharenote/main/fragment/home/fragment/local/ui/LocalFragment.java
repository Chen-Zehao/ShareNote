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
import com.scnu.base.ui.fragment.BaseMvpFragment;
import com.scnu.base.ui.fragment.RefreshFragment;
import com.scnu.base.ui.fragment.RefreshListener;
import com.scnu.custom.citypicker.CityLocationListener;
import com.scnu.custom.citypicker.CityPicker;
import com.scnu.eventbusmodel.LocationInfoEvent;
import com.scnu.model.Article;
import com.scnu.model.User;
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
import butterknife.BindView;

import static com.scnu.model.Macro.REQUEST_PERMISSION_ACCESS_FINE_LOCATION;

/**
 * Created by ChenZehao
 * on 2020/1/15
 */
public class LocalFragment extends RefreshFragment<ILocalView, LocalPresenter> implements ILocalView,RefreshListener {

    private List<Article> articleList;
    private ArticleAdapter articleAdapter;

    public LocationClient mLocationClient = null;
    private CityLocationListener cityLocationListener;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local;
    }

    @Override
    public void initData() {
        //注册事件
        EventBus.getDefault().register(this);
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

    @Override
    public LocalPresenter initPresenter() {
        return new LocalPresenter();
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



    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

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
                    for (int i = 0; i < 10; i++) {
                        Article article = new Article();
                        User user = new User();
                        user.setAvatarUrl("http://b-ssl.duitang.com/uploads/item/201607/26/20160726185736_yPmrE.thumb.224_0.jpeg");
                        user.setName("哈哈哈");
                        article.setPublisher(user);
                        article.setTime("2020-1-1 01:12:34");
                        article.setTitle("哈哈哈");
                        article.setCollectionNum(999);
                        article.setLikeNum(999);
                        article.setCommentNum(999);
                        List<String> themeList = new ArrayList<>();
                        themeList.add("旅游");
                        article.setThemeList(themeList);
                        article.setContent("超级漂亮");
                        List<String> imageList = new ArrayList<>();
                        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1579262564179&di=e51e90db3d4247d0a626e3d1b446e8fb&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20170607%2F17abce67996540b38f7795355dfd0d3e_th.jpg");
                        article.setImageList(imageList);
                        article.setLocation("广东·广州");
                        articleList.add(article);
                    }
                    articleAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "位置权限被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //Fragment要用本身申请权限，不可用ActivityCompat.requestPermissions
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //没有权限需要弹框
                showPermissionRequireDialog();
            }
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mLocationClient.unRegisterLocationListener(cityLocationListener);
        super.onDestroy();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
