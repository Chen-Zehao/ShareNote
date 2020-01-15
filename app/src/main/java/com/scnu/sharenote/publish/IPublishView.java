package com.scnu.sharenote.publish;

import android.graphics.Bitmap;

import com.scnu.base.BaseView;

/**
 * Created by ChenZehao
 * on 2019/12/17
 */
public interface IPublishView extends BaseView {
    void showSelectedPictures(Bitmap bitmap);
}
