package com.scnu.sharenote.main.fragment.mine.presenter;

import android.content.Context;

import com.scnu.base.BasePresenter;
import com.scnu.model.Macro;
import com.scnu.model.UserModel;
import com.scnu.sharenote.main.fragment.mine.ui.fragment.IUserContnetView;
import com.scnu.source.beans.ArticleListResBean;
import com.scnu.source.http.CustomObserver;
import com.scnu.source.http.OnlineDataSource;
import com.scnu.utils.MyApplication;
import com.scnu.utils.ToastUtils;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ChenZehao
 * on 2019/12/10
 */
public class UserContentPresenter extends BasePresenter<IUserContnetView>{
    public UserContentPresenter(Context context, IUserContnetView view) {
        super(context, view);
    }
    /**
     * 查询文章列表
     * @param queryType
     * @param userId
     * @param pageNum
     */
    public void queryArticleList(String queryType,String userId, int pageNum){
        UserModel userModel = (UserModel) MyApplication.getObject(Macro.KEY_USER);
        OnlineDataSource.getInstance().queryArticleList(queryType,"0", userModel.getUserId(), "", 0, 0,  "", "", userId, pageNum, new CustomObserver<ArticleListResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(ArticleListResBean result) {
                if(null != result){
                    if(isActivityAlive()){
                        getMvpView().getArticleListSuccess(result.getData());
                    }
                }else{
                    if(isActivityAlive()){
                        getMvpView().getArticleListFailed();
                    }
                }
            }

            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),exception.getMessage());
                if(isActivityAlive()){
                    getMvpView().getArticleListFailed();
                }
            }
        });
    }
    /**
     * 查询更多文章列表
     * @param queryType
     * @param userId
     * @param pageNum
     */
    public void loadMoreArticle(String queryType,String userId, int pageNum){
        UserModel userModel = (UserModel) MyApplication.getObject(Macro.KEY_USER);
        OnlineDataSource.getInstance().queryArticleList(queryType, "0", userModel.getUserId(), "", 0, 0, "", "", userId, pageNum, new CustomObserver<ArticleListResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(ArticleListResBean result) {
                if(null != result){
                    if(null == result.getData() || result.getData().isEmpty()){
                        if(isActivityAlive()){
                            getMvpView().loadMoreArticleSuccess(result.getData(),true);
                        }
                    }else{
                        if(isActivityAlive()){
                            getMvpView().loadMoreArticleSuccess(result.getData(),false);
                        }
                    }
                }else{
                    if(isActivityAlive()){
                        getMvpView().loadMoreArticleFailed();
                    }
                }
            }

            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),exception.getMessage());
                if(isActivityAlive()){
                    getMvpView().loadMoreArticleFailed();
                }
            }
        });
    }

}
