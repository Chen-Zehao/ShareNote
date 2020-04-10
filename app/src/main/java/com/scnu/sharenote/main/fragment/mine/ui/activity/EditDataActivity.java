package com.scnu.sharenote.main.fragment.mine.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Picture;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citylist.utils.CityListLoader;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.lljjcoder.style.citythreelist.CityBean;
import com.lljjcoder.style.citythreelist.ProvinceActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.scnu.base.ui.activity.BaseMvpActivity;
import com.scnu.base.ui.view.titlebar.BaseTitleBar;
import com.scnu.custom.CircleImageView;
import com.scnu.enums.SexEnum;
import com.scnu.model.PictureModel;
import com.scnu.model.UserModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.main.fragment.mine.presenter.EditDataPresenter;
import com.scnu.sharenote.main.fragment.mine.ui.dialog.InputNameDialog;
import com.scnu.sharenote.main.fragment.mine.ui.dialog.SelectSexDialog;
import com.scnu.sharenote.main.ui.MainActivity;
import com.scnu.sharenote.publish.ui.PublishActivity;
import com.scnu.utils.GlideEngine;
import com.scnu.utils.GlideImageLoader;
import com.scnu.utils.ImageUtil;
import com.scnu.utils.LogUtils;
import com.scnu.utils.MyApplication;
import com.scnu.utils.ServerConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChenZehao
 * on 2020/3/5
 */
public class EditDataActivity extends BaseMvpActivity<IEditDataView, EditDataPresenter> implements IEditDataView {

    @BindView(R.id.title_bar)
    BaseTitleBar titleBar;
    @BindView(R.id.iv_avatar_next)
    AppCompatImageView ivAvatarNext;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.cl_avatar)
    ConstraintLayout clAvatar;
    @BindView(R.id.iv_name_next)
    AppCompatImageView ivNameNext;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.cl_name)
    ConstraintLayout clName;
    @BindView(R.id.iv_sex_next)
    AppCompatImageView ivSexNext;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.cl_sex)
    ConstraintLayout clSex;
    @BindView(R.id.iv_birthday_next)
    AppCompatImageView ivBirthdayNext;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.cl_birthday)
    ConstraintLayout clBirthday;
    @BindView(R.id.iv_area_next)
    AppCompatImageView ivAreaNext;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.cl_area)
    ConstraintLayout clArea;

    private PopupWindow pop;

    /**
     * 生日选择器
     */
    private TimePickerView timePicker;

    /**
     * 地区选择器
     */
    private CityPickerView cityPicker;

    private InputNameDialog inputNameDialog;

    private SelectSexDialog selectSexDialog;

    PictureModel picture = new PictureModel();

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_data;
    }

    @Override
    public void initView() {
        UserModel user = (UserModel) MyApplication.getObject("user");
        if(!TextUtils.isEmpty(user.getAvatarUrl())){
            GlideImageLoader.getInstance().loadImage(mContext, ServerConfig.getInstance().getAppServerURL() + user.getAvatarUrl(),ivAvatar);
        }
        if(!TextUtils.isEmpty(user.getName())) {
            tvName.setText(user.getName());
        }
        if(!TextUtils.isEmpty(user.getSex())) {
            if(user.getSex().equals(SexEnum.getMALE())){
                tvSex.setText("男");
            }else if(user.getSex().equals(SexEnum.getFEMALE())){
                tvSex.setText("女");
            }else{
                tvSex.setText("请选择");
            }
        }else{
            tvSex.setText("请选择");
        }
        if(!TextUtils.isEmpty(user.getLocation())) {
            tvArea.setText(user.getLocation());
        }else{
            tvArea.setText("请选择");
        }
        if(!TextUtils.isEmpty(user.getBirthDay())) {
            tvBirthday.setText(user.getBirthDay());
        }else{
            tvBirthday.setText("请选择");
        }
        initInputNameDialog();
        initSeleceSexDialog();
        initTimePicker();
        initCityPicker();
    }

    private void initInputNameDialog() {
        inputNameDialog = new InputNameDialog(mContext);
        inputNameDialog.setStyle(DialogFragment.STYLE_NORMAL,R.style.centerDialog);
        inputNameDialog.setInputNameListener(new InputNameDialog.InputNameListener() {
            @Override
            public void input(String name) {
                presenter.updateUserName(name);
            }
        });
    }

    private void initSeleceSexDialog() {
        selectSexDialog = new SelectSexDialog(mContext);
        selectSexDialog.setStyle(DialogFragment.STYLE_NORMAL,R.style.centerDialog);
        selectSexDialog.setOnSexSelectedListener(new SelectSexDialog.OnSexSelectedListener() {
            @Override
            public void onSelect(String sex) {
                presenter.updateUserSex(sex);
            }
        });
    }

    private void initTimePicker() {
        timePicker = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date,View v) {//选中事件回调
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String birthday = simpleDateFormat.format(date);
                presenter.updateUserBirthday(birthday);
            }
        })
                .setCancelColor(getResources().getColor(R.color.text_gray))
                .setSubmitColor(getResources().getColor(R.color.text_orange))
                .setTitleText("生日")
                .build();
    }

    private void initCityPicker() {
        cityPicker = new CityPickerView();
        CityConfig cityConfig = new CityConfig.Builder()
                .confirTextColor("FFE67445")//确认按钮文字颜色
                .confirmText("确定")//确认按钮文字
                .confirmTextSize(16)//确认按钮文字大小
                .cancelTextColor("#99000000")//取消按钮文字颜色
                .cancelText("取消")//取消按钮文字
                .cancelTextSize(16)//取消按钮文字大小
                .province("广东省")//默认显示的省份
                .city("广州市")//默认显示省份下面的城市
                .district("天河区")//默认显示省市下面的区县数据
                .build();
        cityPicker.setConfig(cityConfig);

        //监听选择点击事件及返回结果
        cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, com.lljjcoder.bean.CityBean city, DistrictBean district) {
                super.onSelected(province, city, district);
                LogUtils.e(province.getName()+","+city.getName()+","+district.getName());
                String location = province.getName()+" "+city.getName();
                presenter.updateUserLocation(location);
            }

        });

    }

    @Override
    public void initData() {
        /**
         * 预先加载省市区的数据
         */
        cityPicker.init(this);
    }

    /**
     * 头像选择
     */
    @OnClick(R.id.cl_avatar)
    void clAvatarClicked(){
        showPicturePop();
    }

    /**
     * 用户名输入
     */
    @OnClick(R.id.cl_name)
    void clNameClicked(){
        inputNameDialog.show(getSupportFragmentManager(),null);
    }

    /**
     * 性别选择
     */
    @OnClick(R.id.cl_sex)
    void clSexClicked(){
        selectSexDialog.show(getSupportFragmentManager(),null);
    }

    /**
     * 生日选择
     */
    @OnClick(R.id.cl_birthday)
    void clBirthdayClicked(){
        showDatePicker();
    }

    private void showDatePicker() {
        timePicker.show();
    }

    /**
     * 地区选择
     */
    @OnClick(R.id.cl_area)
    void clAreaClicked(){
        //显示
        cityPicker.showCityPicker( );
    }

    @Override
    protected BaseTitleBar getTitleBar() {
        return null;
    }

    @Override
    public EditDataPresenter initPresenter() {
        return new EditDataPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 选择图片方式
     */
    private void showPicturePop() {
        View bottomView = View.inflate(EditDataActivity.this, R.layout.layout_bottom_dialog, null);
        TextView mAlbum = (TextView) bottomView.findViewById(R.id.tv_album);
        TextView mCamera = (TextView) bottomView.findViewById(R.id.tv_camera);

        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        pop.setAnimationStyle(R.style.menu_photo_anim);
        pop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.tv_album) {//相册
                    PictureSelector.create(EditDataActivity.this)
                            .openGallery(PictureMimeType.ofImage())
                            .compress(true)
                            .maxSelectNum(1)
                            .minSelectNum(1)
                            .imageSpanCount(4)
                            .enableCrop(true)
                            .circleDimmedLayer(true)
                            .showCropFrame(false)
                            .showCropGrid(false)
                            .rotateEnabled(false)
                            .scaleEnabled(true)
                            .loadImageEngine(GlideEngine.createGlideEngine())
                            .selectionMode(PictureConfig.MULTIPLE)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                } else if (id == R.id.tv_camera) {//拍照
                    PictureSelector.create(EditDataActivity.this)
                            .openCamera(PictureMimeType.ofImage())
                            .compress(true)
                            .enableCrop(true)
                            .circleDimmedLayer(true)
                            .showCropFrame(false)
                            .showCropGrid(false)
                            .rotateEnabled(false)
                            .scaleEnabled(true)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }
                closePopupWindow();
            }
        };

        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
    }

    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            /**
             * 图片选择结果回调
             */
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> images;
                images = PictureSelector.obtainMultipleResult(data);
                if(null != images && !images.isEmpty()){
                    picture.setFileName(images.get(0).getFileName());
                    String path = images.get(0).getCompressPath();
                    String base64Path = ImageUtil.bitmapToString(path);
                    picture.setPictureBase64(base64Path);
                    presenter.updateUserAvatar(picture);
                }
            }
        }
    }

    @Override
    public void updateUserNameSuccess(String name) {
        tvName.setText(name);
        ToastUtils.showShortToast(mContext,"用户名修改成功");
    }

    @Override
    public void updateUserAvatarSuccess(String avatarUrl) {
        GlideImageLoader.getInstance().loadImage(mContext, ServerConfig.getInstance().getAppServerURL() + avatarUrl, ivAvatar);
        ToastUtils.showShortToast(mContext,"用户头像修改成功");
    }

    @Override
    public void updateUserSexSuccess(String sex) {
        if(sex.equals(SexEnum.getMALE())){
            tvSex.setText("男");
        }else if(sex.equals(SexEnum.getFEMALE())){
            tvSex.setText("女");
        }
        ToastUtils.showShortToast(mContext,"用户性别修改成功");
    }

    @Override
    public void updateUserBirthdaySuccess(String birthday) {
        tvBirthday.setText(birthday);
        ToastUtils.showShortToast(mContext,"用户生日修改成功");
    }

    @Override
    public void updateUserLocationSuccess(String location) {
        tvArea.setText(location);
        ToastUtils.showShortToast(mContext,"用户地区修改成功");
    }

}
