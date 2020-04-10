package com.scnu.sharenote.main.fragment.home.fragment.local.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.donkingliang.labels.LabelsView;
import com.scnu.base.BaseRecycleAdapter;
import com.scnu.base.ui.fragment.RefreshFragment;
import com.scnu.base.ui.fragment.RefreshListener;
import com.scnu.custom.citypicker.CityLocationListener;
import com.scnu.custom.citypicker.CityPicker;
import com.scnu.enums.SortEnum;
import com.scnu.eventbusmodel.DeleteArticleEvent;
import com.scnu.eventbusmodel.LocationInfoEvent;
import com.scnu.model.ArticleModel;
import com.scnu.model.Macro;
import com.scnu.sharenote.R;
import com.scnu.sharenote.detail.ui.DetailActivity;
import com.scnu.sharenote.main.fragment.home.adapter.ArticleAdapter;
import com.scnu.sharenote.main.fragment.home.fragment.local.presenter.LocalPresenter;
import com.scnu.utils.LogUtils;
import com.scnu.utils.MyApplication;
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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import static com.scnu.model.Macro.REQUEST_PERMISSION_ACCESS_FINE_LOCATION;

/**
 * Created by ChenZehao
 * on 2020/1/15
 */
public class LocalFragment extends RefreshFragment implements ILocalView, RefreshListener {

    @BindView(R.id.iv_location)
    AppCompatImageView ivLocation;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.cl_location)
    ConstraintLayout clLocation;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.iv_type)
    AppCompatImageView ivType;
    @BindView(R.id.cl_type)
    ConstraintLayout clType;
    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.iv_sort)
    AppCompatImageView ivSort;
    @BindView(R.id.cl_sort)
    ConstraintLayout clSort;
    @BindView(R.id.tv_filter)
    TextView tvFilter;
    @BindView(R.id.iv_filter)
    AppCompatImageView ivFilter;
    @BindView(R.id.cl_filter)
    ConstraintLayout clFilter;
    @BindView(R.id.cl_header)
    ConstraintLayout clHeader;
    @BindView(R.id.lv_type)
    LabelsView lvType;
    @BindView(R.id.cl_type_view)
    ConstraintLayout clTypeView;
    @BindView(R.id.lv_sort)
    LabelsView lvSort;
    @BindView(R.id.cl_sort_view)
    ConstraintLayout clSortView;
    @BindView(R.id.lv_price)
    LabelsView lvPrice;
    @BindView(R.id.cl_price_view)
    ConstraintLayout clPriceView;
    @BindView(R.id.v_mask)
    View vMask;

    private LocalPresenter localPresenter;

    private  LinearLayoutManager linearLayoutManager;

    private List<ArticleModel> articleList;
    private ArticleAdapter articleAdapter;

    public LocationClient mLocationClient = null;
    private CityLocationListener cityLocationListener;

    private String curCity = "未知";
    private String curTheme = "";
    private String curPrice = "全部价格";
    private String curSort = SortEnum.getSmartSort();
    private List<String> sortList = new ArrayList<>();

    private double latitude;
    private double longtitude;

    private boolean isFirstLoad = true;

    private boolean isFirstGetLocation = false;

    public LocalFragment(){}

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local;
    }

    @Override
    public void initView() {
        super.initView();
        if (null != MyApplication.getParaValue(Macro.KEY_CITY)) {
            if(!TextUtils.isEmpty(MyApplication.getParaValue(Macro.KEY_CITY))){
                curCity = MyApplication.getParaValue(Macro.KEY_CITY);
                tvLocation.setText(curCity);
            }
        }
        localPresenter = new LocalPresenter(mContext, this);
        lvType.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @Override
            public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
                if (isSelect) {
                    curTheme = label.getText().toString();
                    tvType.setText(curTheme);
                    localPresenter.getPriceList(curTheme);
                    if (curTheme.equals("全部类型")) {
                        clFilter.setVisibility(View.GONE);
                    } else {
                        clFilter.setVisibility(View.VISIBLE);
                    }
                    linearLayoutManager.scrollToPositionWithOffset(0,0);
                    mPageNum = 1;
                    localPresenter.queryArticleList(curSort, curCity, latitude, longtitude, curPrice, curTheme, mPageNum);
                    refreshLayout.setNoMoreData(false);
                }
            }
        });
        sortList.add("智能排序");
        sortList.add("离我最近");
        sortList.add("人气优先");
        sortList.add("发布时间");
        lvSort.setLabels(sortList);
        lvSort.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @Override
            public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
                if (isSelect) {
                    tvSort.setText(label.getText().toString());
                    if (position == 0) {
                        curSort = SortEnum.getSmartSort();
                    } else if (position == 1) {
                        curSort = SortEnum.getDistanceSort();
                    } else if (position == 2) {
                        curSort = SortEnum.getPopularitySort();
                    } else if (position == 3) {
                        curSort = SortEnum.getTimeSort();
                    }
                    mPageNum = 1;
                    linearLayoutManager.scrollToPositionWithOffset(0,0);
                    localPresenter.queryArticleList(curSort, curCity, latitude, longtitude, curPrice, curTheme, mPageNum);
                    refreshLayout.setNoMoreData(false);
                }
            }
        });
        lvPrice.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @Override
            public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
                if (isSelect) {
                    curPrice = label.getText().toString();
                    tvFilter.setText(curPrice);
                    linearLayoutManager.scrollToPositionWithOffset(0,0);
                    mPageNum = 1;
                    localPresenter.queryArticleList(curSort, curCity, latitude, longtitude, curPrice, curTheme, mPageNum);
                    refreshLayout.setNoMoreData(false);
                }
            }
        });
    }

    @Override
    public void initData() {
        //注册事件
        if (!EventBus.getDefault().isRegistered(this)) {
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
        articleAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("issueId",articleList.get(position).getIssueId());
                intent.putExtras(bundle);
                startActivity(intent,bundle);
            }
        });
        return articleAdapter;
    }

    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        return linearLayoutManager;
    }

    @Override
    public RefreshListener getRefreshListener() {
        return this;
    }

    /**
     * 删除文章后更新Recyclerview
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DeleteArticleEvent event){
        if(null != articleList){
            for(int i = 0; i < articleList.size(); i++){
                if(articleList.get(i).getIssueId().equals(event.getIssueId())){
                    articleList.remove(i);//集合移除该条
                    articleAdapter.notifyItemRemoved(i);//通知移除该条
                    articleAdapter.notifyItemRangeChanged(i,articleList.size() - i);//更新适配器这条后面列表的变化
                    break;
                }
            }
        }
    }

    @OnClick(R.id.cl_location)
    void clLocationClicked() {
        CityPicker.from(LocalFragment.this) //activity或者fragment
                .enableAnimation(true)    //启用动画效果，默认无
                .setLocatedCity(null) //APP自身已定位的城市，传null会自动定位（默认）
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        curCity = data.getName();
                        MyApplication.setParaValue(Macro.KEY_CITY, curCity);
                        tvLocation.setText(data.getName());
                        linearLayoutManager.scrollToPositionWithOffset(0,0);
                        mPageNum = 1;
                        localPresenter.queryArticleList(curSort, curCity, latitude, longtitude, curPrice, curTheme, mPageNum);
                        refreshLayout.setNoMoreData(false);
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

    @OnClick(R.id.cl_type)
    void clTypeClicked() {
        if (clTypeView.getVisibility() == View.GONE) {
            vMask.setVisibility(View.VISIBLE);
            ivType.setImageDrawable(getResources().getDrawable(R.drawable.ic_common_caretdown_unfolder));
            clTypeView.setVisibility(View.VISIBLE);
            clSortView.setVisibility(View.GONE);
            ivSort.setImageDrawable(getResources().getDrawable(R.drawable.ic_common_caretdown_default));
            clPriceView.setVisibility(View.GONE);
            ivFilter.setImageDrawable(getResources().getDrawable(R.drawable.ic_common_caretdown_default));
        } else {
            vMask.setVisibility(View.GONE);
            ivType.setImageDrawable(getResources().getDrawable(R.drawable.ic_common_caretdown_default));
            clTypeView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.cl_sort)
    void clSortClicked() {
        if (clSortView.getVisibility() == View.GONE) {
            vMask.setVisibility(View.VISIBLE);
            ivSort.setImageDrawable(getResources().getDrawable(R.drawable.ic_common_caretdown_unfolder));
            clSortView.setVisibility(View.VISIBLE);
            clTypeView.setVisibility(View.GONE);
            ivType.setImageDrawable(getResources().getDrawable(R.drawable.ic_common_caretdown_default));
            clPriceView.setVisibility(View.GONE);
            ivFilter.setImageDrawable(getResources().getDrawable(R.drawable.ic_common_caretdown_default));
        } else {
            vMask.setVisibility(View.GONE);
            ivSort.setImageDrawable(getResources().getDrawable(R.drawable.ic_common_caretdown_default));
            clSortView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.cl_filter)
    void clPriceClicked() {
        if (clPriceView.getVisibility() == View.GONE) {
            vMask.setVisibility(View.VISIBLE);
            ivFilter.setImageDrawable(getResources().getDrawable(R.drawable.ic_common_caretdown_unfolder));
            clPriceView.setVisibility(View.VISIBLE);
            clTypeView.setVisibility(View.GONE);
            ivType.setImageDrawable(getResources().getDrawable(R.drawable.ic_common_caretdown_default));
            clSortView.setVisibility(View.GONE);
            ivSort.setImageDrawable(getResources().getDrawable(R.drawable.ic_common_caretdown_default));
        } else {
            vMask.setVisibility(View.GONE);
            ivFilter.setImageDrawable(getResources().getDrawable(R.drawable.ic_common_caretdown_default));
            clPriceView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.v_mask)
    void vMaskClicked(){
        vMask.setVisibility(View.GONE);
        ivType.setImageDrawable(getResources().getDrawable(R.drawable.ic_common_caretdown_default));
        clTypeView.setVisibility(View.GONE);
        ivSort.setImageDrawable(getResources().getDrawable(R.drawable.ic_common_caretdown_default));
        clSortView.setVisibility(View.GONE);
        ivFilter.setImageDrawable(getResources().getDrawable(R.drawable.ic_common_caretdown_default));
        clPriceView.setVisibility(View.GONE);
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
                                curCity = data.getName();
                                MyApplication.setParaValue(Macro.KEY_CITY, curCity);
                                tvLocation.setText(data.getName());
                                linearLayoutManager.scrollToPositionWithOffset(0,0);
                                mPageNum = 1;
                                localPresenter.queryArticleList(curSort, curCity, latitude, longtitude, curPrice, curTheme, mPageNum);
                                refreshLayout.setNoMoreData(false);
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
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LocationInfoEvent event) {
        mLocationClient.stop();
        if(isFirstGetLocation){
            curCity = event.getCity();
            MyApplication.setParaValue(Macro.KEY_CITY, curCity);
            tvLocation.setText(curCity);
            localPresenter.queryArticleList(curSort ,curCity, latitude, longtitude, curPrice, curTheme, mPageNum);
            return;
        }
        if(isFirstLoad){
            if(curCity.equals("未知")){
                curCity = event.getCity();
                MyApplication.setParaValue(Macro.KEY_CITY, curCity);
                tvLocation.setText(curCity);
            }
            latitude = event.getLatitude();
            longtitude = event.getLongtitude();
            localPresenter.queryArticleList(curSort ,curCity, latitude, longtitude, curPrice, curTheme, mPageNum);
            isFirstLoad = false;
            return;
        }
        CityPicker.from(LocalFragment.this)
                .locateComplete(new LocatedCity(event.getCity(), event.getProvince(), "0"), LocateState.SUCCESS);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isFirstGetLocation = true;
                mLocationClient.start();
            } else {
                Toast.makeText(getContext(), "位置权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Fragment要用本身申请权限，不可用ActivityCompat.requestPermissions
        if (curCity.equals("未知") && ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //没有权限需要弹框
            showPermissionRequireDialog();
            return;
        }
        if(isFirstLoad){
            localPresenter.getThemeList();
            if(null != MyApplication.getObject(Macro.KEY_USER)) {
                /**
                 * 已授权定位则每次获取经纬度
                 */
                if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    mLocationClient.start();
                }else{
                    localPresenter.queryArticleList(curSort ,curCity, latitude, longtitude, curPrice, curTheme, mPageNum);
                    isFirstLoad = false;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        //注册事件
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        mLocationClient.unRegisterLocationListener(cityLocationListener);
        super.onDestroy();
        localPresenter.detach();
    }


    @Override
    public boolean isNeedRefresh() {
        return true;
    }

    @Override
    public boolean isNeedLoadMore() {
        return true;
    }

    @Override
    public void onRefresh() {
        localPresenter.queryArticleList(curSort ,curCity, latitude, longtitude, curPrice, curTheme, mPageNum);
    }

    @Override
    public void onLoadMore() {
        localPresenter.loadMoreArticle(curSort ,curCity, latitude, longtitude, curPrice, curTheme, mPageNum);
    }

    @Override
    public void getArticleListSuccess(List<ArticleModel> articleModelList) {
        articleList.clear();
        articleList.addAll(articleModelList);
        if (null == articleList || articleList.isEmpty()) {
            refreshDataFinish(false, RESULT_TYPE_EMPTY);
        } else {
            articleAdapter.notifyDataSetChanged();
            refreshDataFinish(true, 0);
        }
    }

    @Override
    public void getArticleListFailed() {
        refreshDataFinish(false, RESULT_TYPE_EMPTY);
    }

    @Override
    public void loadMoreArticleSuccess(List<ArticleModel> articleModelList, boolean isEnd) {
        loadMoreDataFinish();
        if(isEnd){
            noMoreData();
        }else{
            articleList.addAll(articleModelList);
            articleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadMoreArticleFailed() {
        refreshLayout.finishLoadMore();
    }

    @Override
    public void getThemeListSuccess(List<String> themeList) {
        lvType.setLabels(themeList);
    }

    @Override
    public void getPriceListSuccess(List<String> priceList) {
        lvPrice.setLabels(priceList);
    }
}
