package com.scnu.sharenote.detail;

import com.scnu.base.BasePresenter;
import com.scnu.source.beans.ArticleListResBean;
import com.scnu.source.beans.ArticleResBean;
import com.scnu.source.http.CustomObserver;
import com.scnu.source.http.OnlineDataSource;
import com.scnu.utils.ToastUtils;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ChenZehao
 * on 2020/1/18
 */
public class DetailPresenter extends BasePresenter<IDetailView> {


    public void getArticleDetail(String issueId){
        OnlineDataSource.getInstance().getArticleDetail(issueId, new CustomObserver<ArticleResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(ArticleResBean result) {
                if(isActivityAlive()){
                    getMvpView().getDetailSuccess(result.getData());
                }
            }

            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),exception.getMessage());
            }
        });
    }

}
