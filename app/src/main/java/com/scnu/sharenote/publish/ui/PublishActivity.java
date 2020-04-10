package com.scnu.sharenote.publish.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.scnu.base.ui.activity.BaseMvpActivity;
import com.scnu.base.ui.view.titlebar.BaseTitleBar;
import com.scnu.model.ArticleModel;
import com.scnu.model.LocationModel;
import com.scnu.model.Macro;
import com.scnu.model.PictureModel;
import com.scnu.model.PriceModel;
import com.scnu.model.ThemeModel;
import com.scnu.model.UserModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.publish.adapter.FullyGridLayoutManager;
import com.scnu.sharenote.publish.adapter.PictureAdapter;
import com.scnu.sharenote.publish.presenter.PublishPresenter;
import com.scnu.sharenote.publish.ui.dialog.ChooseLocationDialog;
import com.scnu.sharenote.publish.ui.dialog.ChoosePriceDialog;
import com.scnu.sharenote.publish.ui.dialog.ChooseThemeDialog;
import com.scnu.utils.DateUtils;
import com.scnu.utils.GlideCacheEngine;
import com.scnu.utils.GlideEngine;
import com.scnu.utils.ImageUtil;
import com.scnu.utils.LogUtils;
import com.scnu.utils.MyApplication;
import com.scnu.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.scnu.model.Macro.REQUEST_PERMISSION_ACCESS_FINE_LOCATION;

/**
 * Created by ChenZehao
 * on 2019/12/17
 */
public class PublishActivity extends BaseMvpActivity<IPublishView, PublishPresenter> implements IPublishView {


    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.cl_header)
    ConstraintLayout clHeader;
    @BindView(R.id.rv_picture)
    RecyclerView rvPicture;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.iv_theme)
    AppCompatImageView ivTheme;
    @BindView(R.id.tv_theme)
    TextView tvTheme;
    @BindView(R.id.iv_theme_pulldown)
    AppCompatImageView ivThemePulldown;
    @BindView(R.id.cl_theme)
    ConstraintLayout clTheme;
    @BindView(R.id.iv_address)
    AppCompatImageView ivAddress;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_address_pulldown)
    AppCompatImageView ivAddressPulldown;
    @BindView(R.id.cl_address)
    ConstraintLayout clAddress;
    @BindView(R.id.bt_publish)
    Button btPublish;
    @BindView(R.id.iv_price)
    AppCompatImageView ivPrice;
    @BindView(R.id.tv_price_name)
    TextView tvPriceName;
    @BindView(R.id.iv_price_pulldown)
    AppCompatImageView ivPricePulldown;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.cl_price)
    ConstraintLayout clPrice;

    private PictureAdapter pictureAdapter;
    private int maxSelectNum = 9;
    private List<LocalMedia> selectList = new ArrayList<>();
    private PopupWindow pop;
    private ChooseThemeDialog chooseThemeDialog;
    private ChooseLocationDialog chooseLocationDialog;
    private ChoosePriceDialog choosePriceDialog;
    private LocationModel locationModel = new LocationModel();
    private String themeText = "";
    private String priceText = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish;
    }

    @Override
    public void initView() {
        pictureAdapter = new PictureAdapter(mContext);
        pictureAdapter.setList(selectList);
        pictureAdapter.setmOnAddPicClickListener(new PictureAdapter.OnAddPicClickListener() {
            @Override
            public void onAddPicClick() {
//                showPop();
                selectAlbum();
            }
        });
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        rvPicture.setLayoutManager(manager);
        rvPicture.setAdapter(pictureAdapter);
        pictureAdapter.setOnItemClickListener(new PictureAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getMimeType();
                    int mediaType = PictureMimeType.getMimeType(pictureType);
                    if (mediaType == PictureConfig.TYPE_IMAGE) {// 预览图片 可自定长按保存路径
                        //PictureSelector.create(NewContentActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                        PictureSelector.create(PublishActivity.this).externalPicturePreview(position, selectList, 0);
                    } else if (mediaType == PictureConfig.TYPE_VIDEO) {// 预览视频
                        PictureSelector.create(PublishActivity.this).externalPictureVideo(media.getPath());
                    } else if (mediaType == PictureConfig.TYPE_AUDIO) {// 预览音频
                        PictureSelector.create(PublishActivity.this).externalPictureAudio(media.getPath());
                    }
                }
            }
        });
        chooseLocationDialog = new ChooseLocationDialog(mContext);
        chooseLocationDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.moveUpDialog);
    }

    @Override
    public void initData() {
    }

    @Override
    public PublishPresenter initPresenter() {
        return new PublishPresenter();
    }

    @Override
    protected BaseTitleBar getTitleBar() {
        return null;
    }

    @OnClick(R.id.iv_back)
    void ivBackClicked() {
        finish();
    }

    @OnClick(R.id.cl_theme)
    void clThemeClicked() {
        presenter.getThemeList();
    }

    @OnClick(R.id.cl_address)
    void clAddressClicked() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //没有权限需要弹框
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_ACCESS_FINE_LOCATION);
            }
        }else{
            chooseLocationDialog.show(getSupportFragmentManager(), null);
            chooseLocationDialog.setOnLocationChooseListener(new ChooseLocationDialog.OnLocationChooseListener() {
                @Override
                public void choose(LocationModel location) {
                    tvAddress.setText(location.getName());
                    locationModel = location;
                }
            });
        }
    }

    @OnClick(R.id.cl_price)
    void clPriceClicked(){
        if(TextUtils.isEmpty(themeText)){
            return;
        }
        presenter.getThemePriceList(themeText);
    }

    @OnClick(R.id.bt_publish)
    void btnPublishClicked() {
        //没有填标题或内容
        if (TextUtils.isEmpty(etTitle.getText().toString()) || TextUtils.isEmpty(etContent.getText().toString())) {
            ToastUtils.showToast(mContext, "请填写标题和内容");
            return;
        }
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        List<PictureModel> picList = new ArrayList<>();
        for (LocalMedia localMedia : selectList) {
            String path = "";
            if(localMedia.isCompressed()){
                path = localMedia.getCompressPath();
            }else{
                path = localMedia.getPath();
            }
            String fileName = localMedia.getFileName();
            String base64Path = ImageUtil.bitmapToString(path);
            PictureModel picture = new PictureModel();
            picture.setPictureBase64(base64Path);
            picture.setFileName(fileName);
            picList.add(picture);
        }
        ArticleModel article = new ArticleModel();
        article.setTitle(title);
        article.setContent(content);
        article.setPictureList(picList);
        article.setLocation(locationModel);
        UserModel user = (UserModel) MyApplication.getObject(Macro.KEY_USER);
        article.setUserId(user.getUserId());
        article.setTheme(themeText);
        article.setTime(DateUtils.getCurTime());
        article.setPrice(priceText);
        presenter.publish(article);
    }

    @OnTextChanged(R.id.et_title)
    void onTitleTextChanged(CharSequence text) {
        if (TextUtils.isEmpty(text.toString())) {
            btPublish.setBackground(mContext.getResources().getDrawable(R.drawable.publish_bg_btn_unable));
        } else {
            if (TextUtils.isEmpty(etContent.getText().toString())) {
                btPublish.setBackground(mContext.getResources().getDrawable(R.drawable.publish_bg_btn_unable));
            } else {
                btPublish.setBackground(mContext.getResources().getDrawable(R.drawable.publish_bg_btn_able));
            }
        }
    }

    @OnTextChanged(R.id.et_content)
    void onContentTextChanged(CharSequence text) {
        if (TextUtils.isEmpty(text.toString())) {
            btPublish.setBackground(mContext.getResources().getDrawable(R.drawable.publish_bg_btn_unable));
        } else {
            if (TextUtils.isEmpty(etTitle.getText().toString())) {
                btPublish.setBackground(mContext.getResources().getDrawable(R.drawable.publish_bg_btn_unable));
            } else {
                btPublish.setBackground(mContext.getResources().getDrawable(R.drawable.publish_bg_btn_able));
            }
        }
    }

    /**
     * 选择相册
     */
    private void selectAlbum(){
        PictureSelector.create(PublishActivity.this)
                .openGallery(PictureMimeType.ofImage())
                .compress(true)
                .maxSelectNum(maxSelectNum)
                .minSelectNum(1)
                .imageSpanCount(4)
                .loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine())
                .loadImageEngine(GlideEngine.createGlideEngine())
                .selectionMode(PictureConfig.MULTIPLE)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 选择图片方式
     */
    private void showPop() {
        View bottomView = View.inflate(PublishActivity.this, R.layout.layout_bottom_dialog, null);
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
                switch (view.getId()) {
                    case R.id.tv_album:
                        //相册
                        PictureSelector.create(PublishActivity.this)
                                .openGallery(PictureMimeType.ofImage())
                                .compress(true)
                                .maxSelectNum(maxSelectNum)
                                .loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine())
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .loadImageEngine(GlideEngine.createGlideEngine())
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_camera:
                        //拍照
                        PictureSelector.create(PublishActivity.this)
                                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                                .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                .loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine())// 获取图片资源缓存，主要是解决华为10部分机型在拷贝文件过多时会出现卡的问题，这里可以判断只在会出现一直转圈问题机型上使用
                                .previewImage(true)// 是否可预览图片
                                .compress(true)// 是否压缩
                                .compressQuality(60)// 图片压缩后输出质量
                                .cutOutQuality(90)// 裁剪输出质量 默认100
                                .minimumCompressSize(100)// 小于100kb的图片不压缩
                                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                        break;
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
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调
                images = PictureSelector.obtainMultipleResult(data);
                for (LocalMedia localMedia:images){
                    if(TextUtils.isEmpty(localMedia.getFileName())){
                        String time = DateUtils.getCurYMDHMS();
                        localMedia.setFileName("IMG_"+time.substring(0,8)+"_"+time.substring(8)+".jpg");
                    }
                }
                selectList.addAll(images);

                //selectList = PictureSelector.obtainMultipleResult(data);

                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                pictureAdapter.setList(selectList);
                pictureAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void getThemeListSuccess(List<ThemeModel> themeModelList) {
        chooseThemeDialog = new ChooseThemeDialog(mContext, themeModelList);
        chooseThemeDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.centerDialog);
        chooseThemeDialog.show(getSupportFragmentManager(), null);
        chooseThemeDialog.setThemeChooseListener(new ChooseThemeDialog.ThemeChooseListener() {
            @Override
            public void themeChosen(String theme) {
                tvTheme.setText(theme);
                themeText = theme;
                ivPrice.setImageDrawable(getResources().getDrawable(R.mipmap.publish_ic_price_able));
                tvPriceName.setTextColor(getResources().getColor(R.color.text_gray));
                ivPricePulldown.setVisibility(View.VISIBLE);
                tvPrice.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void getPriceListSuccess(List<PriceModel> priceModelList) {
        choosePriceDialog = new ChoosePriceDialog(mContext, priceModelList);
        choosePriceDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.centerDialog);
        choosePriceDialog.show(getSupportFragmentManager(), null);
        choosePriceDialog.setPriceChooseListener(new ChoosePriceDialog.PriceChooseListener() {
            @Override
            public void priceChosen(String price) {
                tvPrice.setText(price);
                priceText = price;
            }
        });
    }

    @Override
    public void publishSuccess() {
        ToastUtils.showToast(mContext, "文章发布成功");
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseLocationDialog.show(getSupportFragmentManager(), null);
                chooseLocationDialog.setOnLocationChooseListener(new ChooseLocationDialog.OnLocationChooseListener() {
                    @Override
                    public void choose(LocationModel location) {
                        tvAddress.setText(location.getName());
                        locationModel = location;
                    }
                });
            } else {
                Toast.makeText(mContext, "位置权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
