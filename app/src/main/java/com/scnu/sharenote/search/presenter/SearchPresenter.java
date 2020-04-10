package com.scnu.sharenote.search.presenter;

import com.scnu.base.BasePresenter;
import com.scnu.sharenote.search.ui.ISearchView;
import com.scnu.source.beans.ArticleListResBean;
import com.scnu.source.http.CustomObserver;
import com.scnu.source.http.OnlineDataSource;
import com.scnu.utils.ToastUtils;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ChenZehao
 * on 2020/4/8
 */
public class SearchPresenter extends BasePresenter<ISearchView> {
    /**
     * 搜索文章
     * @param message
     * @param userId
     */
    public void searchArticle(String message, String userId){
        OnlineDataSource.getInstance().searchArticle(message, userId, new CustomObserver<ArticleListResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(ArticleListResBean result) {
                if(null != result){
                    if (isActivityAlive()){
                        getMvpView().searchArticleSuccess(result.getData());
                    }
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
