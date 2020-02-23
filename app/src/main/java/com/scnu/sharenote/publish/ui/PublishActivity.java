package com.scnu.sharenote.publish.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.scnu.base.ui.activity.BaseMvpActivity;
import com.scnu.sharenote.R;
import com.scnu.sharenote.publish.adapter.FullyGridLayoutManager;
import com.scnu.sharenote.publish.adapter.PictureAdapter;
import com.scnu.sharenote.publish.presenter.PublishPresenter;
import com.scnu.utils.GlideEngine;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    private PictureAdapter pictureAdapter;
    private int maxSelectNum = 9;
    private List<LocalMedia> selectList = new ArrayList<>();
    private PopupWindow pop;

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
                showPop();
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
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(NewContentActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(PublishActivity.this).externalPicturePreview(position, selectList, 0);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(PublishActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(PublishActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public PublishPresenter initPresenter() {
        return new PublishPresenter();
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

    @OnClick(R.id.iv_back)
    void ivBackClicked() {
        finish();
    }

    private void showPop() {
        View bottomView = View.inflate(PublishActivity.this, R.layout.layout_bottom_dialog, null);
        TextView mAlbum = (TextView) bottomView.findViewById(R.id.tv_album);
        TextView mCamera = (TextView) bottomView.findViewById(R.id.tv_camera);
        TextView mCancel = (TextView) bottomView.findViewById(R.id.tv_cancel);

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
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .loadImageEngine(GlideEngine.createGlideEngine())
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_camera:
                        //拍照
                        PictureSelector.create(PublishActivity.this)
                                .openCamera(PictureMimeType.ofImage())
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_cancel:
                        //取消
                        //closePopupWindow();
                        break;
                }
                closePopupWindow();
            }
        };

        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);
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
}
