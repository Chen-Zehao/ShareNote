package com.scnu.sharenote.publish.presenter;

import com.scnu.base.BasePresenter;
import com.scnu.model.ArticleModel;
import com.scnu.sharenote.publish.ui.IPublishView;
import com.scnu.source.beans.BaseBean;
import com.scnu.source.beans.ThemeResBean;
import com.scnu.source.http.CustomObserver;
import com.scnu.source.http.OnlineDataSource;
import com.scnu.utils.ToastUtils;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ChenZehao
 * on 2019/12/17
 */
public class PublishPresenter extends BasePresenter<IPublishView> {

    public void getThemeList(){
        OnlineDataSource.getInstance().getThemeList(new CustomObserver<ThemeResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(ThemeResBean result) {
                if(null != result.getData() && !result.getData().isEmpty()){
                    if(isActivityAlive()){
                        getMvpView().getThemeListSuccess(result.getData());
                    }
                }else{
                    ToastUtils.showToast(getContext(),"暂无主题数据");
                }
            }

            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),exception.toString());
            }
        });
    }


    /**
     * 发布文章
     * @param article
     */
    public void publish(ArticleModel article){
        OnlineDataSource.getInstance().publishArticle(article, new CustomObserver<BaseBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(BaseBean result) {
                if(isActivityAlive()){
                    getMvpView().publishSuccess();
                }
            }

            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),exception.toString());
            }
        });
    }
}
