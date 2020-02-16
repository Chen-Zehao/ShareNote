package com.scnu.sharenote.detail;

import com.scnu.base.BasePresenter;

/**
 * Created by ChenZehao
 * on 2020/1/18
 */
public class DetailPresenter extends BasePresenter<IDetailView> {

    public void initArticleDetailData() {
        getMvpView().getDetailSuccess();
    }

}
