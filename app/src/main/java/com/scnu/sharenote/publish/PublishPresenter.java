package com.scnu.sharenote.publish;

import android.graphics.Bitmap;

import com.scnu.base.BasePresenter;

/**
 * Created by ChenZehao
 * on 2019/12/17
 */
public class PublishPresenter extends BasePresenter<IPublishView> {
    public void selectPicture(Bitmap bitmap){
        getMvpView().showSelectedPictures(bitmap);
    }

    public void takePhoto(Bitmap bitmap){
        getMvpView().showSelectedPictures(bitmap);
    }
}
