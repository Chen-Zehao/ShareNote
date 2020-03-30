package com.scnu.sharenote.publish.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.scnu.base.BaseRecycleAdapter;
import com.scnu.base.ui.dialog.BaseDialogFragment;
import com.scnu.model.LocationModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.publish.adapter.NearAddressAdapter;
import com.scnu.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by ChenZehao
 * on 2020/2/29
 */
public class ChooseLocationDialog extends BaseDialogFragment implements OnGetPoiSearchResultListener {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_search)
    AppCompatImageView ivSearch;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.cl_search)
    ConstraintLayout clSearch;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.rv_location)
    RecyclerView rvLocation;
    private Context mContext;

    private NearAddressAdapter nearAddressAdapter;

    private List<PoiInfo> nearList = new ArrayList<>();

    private LocationClient mLocationClient = null;
    private BDLocationListener myListener;

    private double latitude;//纬度

    private double longtitude;//经度

    private PoiSearch mPoiSearch;

    private String keyWord = "写字楼";//关键字

    private int pageSize = 40;

    public ChooseLocationDialog(Context context) {
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_dialog_loaction, container, false);
        ButterKnife.bind(this, view);
        mLocationClient = new LocationClient(mContext);   //声明LocationClient类
        myListener = new NearAddressLocationListener();
        mLocationClient.registerLocationListener(myListener);  //注册监听函数
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        initView();
        return view;
    }

    private void initView() {
        nearAddressAdapter = new NearAddressAdapter(mContext, nearList);
        rvLocation.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvLocation.setAdapter(nearAddressAdapter);
        nearAddressAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(null != onLocationChooseListener){
                    PoiInfo poiInfo = nearList.get(position);
                    LocationModel locationModel = new LocationModel();
                    locationModel.setName(poiInfo.getName());
                    locationModel.setProvince(poiInfo.getProvince());
                    locationModel.setCity(poiInfo.getCity());
                    locationModel.setLatitude(poiInfo.getLocation().latitude);
                    locationModel.setLongtitude(poiInfo.getLocation().longitude);
                    onLocationChooseListener.choose(locationModel);
                    dismiss();
                }
            }
        });
        initLocation();

    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    public class NearAddressLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            }
            longtitude = location.getLongitude();
            latitude = location.getLatitude();
            searchNeayBy();
        }
    }

    /**
     * 搜索周边地理位置
     */
    private void searchNeayBy() {
        PoiNearbySearchOption option = new PoiNearbySearchOption();
        option.keyword(keyWord);
        option.sortType(PoiSortType.distance_from_near_to_far);
        option.location(new LatLng(latitude, longtitude));
        option.radius(3000);
        option.pageCapacity(pageSize);
        mPoiSearch.searchNearby(option);
    }

    @OnTextChanged(R.id.et_search)
    void onTextChanged(CharSequence text){
        if(TextUtils.isEmpty(text.toString())){
            keyWord = "写字楼";
        }else{
            keyWord = text.toString();
        }
    }

    @OnClick(R.id.tv_cancel)
    void tvCancelClicked(){
        dismiss();
    }

    /**
     * 接受周边地理位置结果
     * @param poiResult
     */
    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult != null) {
            if (poiResult.getAllPoi() != null && poiResult.getAllPoi().size() > 0) {
                nearList.clear();
                nearList.addAll(poiResult.getAllPoi());
                nearAddressAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    /**
     * 设置宽度全屏
     */
    @Override
    public void resizeDialogFragment() {
        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距
        window.setAttributes(layoutParams);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        mLocationClient.unRegisterLocationListener(myListener);
        mLocationClient.stop();
    }

    public interface OnLocationChooseListener{
        void choose(LocationModel location);
    }

    private OnLocationChooseListener onLocationChooseListener;

    public void setOnLocationChooseListener(OnLocationChooseListener onLocationChooseListener) {
        this.onLocationChooseListener = onLocationChooseListener;
    }
}
