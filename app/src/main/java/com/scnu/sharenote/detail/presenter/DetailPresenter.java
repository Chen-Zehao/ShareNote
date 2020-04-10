package com.scnu.sharenote.detail.presenter;

import com.scnu.base.BasePresenter;
import com.scnu.sharenote.detail.ui.IDetailView;
import com.scnu.source.beans.ArticleResBean;
import com.scnu.source.beans.BaseBean;
import com.scnu.source.beans.CommentListResBean;
import com.scnu.source.http.CustomObserver;
import com.scnu.source.http.OnlineDataSource;
import com.scnu.utils.ToastUtils;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ChenZehao
 * on 2020/1/18
 */
public class DetailPresenter extends BasePresenter<IDetailView> {


    public void getArticleDetail(String issueId, String userId){
        OnlineDataSource.getInstance().getArticleDetail(issueId, userId, new CustomObserver<ArticleResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
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

    public void addLike(String issueId, String userId){
        OnlineDataSource.getInstance().addLike(issueId, userId, new CustomObserver<BaseBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(BaseBean result) {
                if (isActivityAlive()){
                    getMvpView().addLikeSuccess();
                }
            }
            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),exception.getMessage());
            }
        });
    }

    public void addCollection(String issueId, String userId){
        OnlineDataSource.getInstance().addCollection(issueId, userId, new CustomObserver<BaseBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(BaseBean result) {
                if (isActivityAlive()){
                    getMvpView().addCollectionSuccess();
                }
            }
            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),exception.getMessage());
            }
        });
    }

    public void addAttention(String attentionId, String userId){
        OnlineDataSource.getInstance().addAttention(attentionId, userId, new CustomObserver<BaseBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(BaseBean result) {
                if (isActivityAlive()){
                    getMvpView().addAttentionSuccess();
                }
            }
            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),exception.getMessage());
            }
        });
    }

    public void deleteArticle(String issueId){
        OnlineDataSource.getInstance().deleteArticle(issueId, new CustomObserver<BaseBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(BaseBean result) {
                if (isActivityAlive()){
                    getMvpView().deleteArticleSuccess();
                }
            }
            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),exception.getMessage());
            }
        });
    }

    public void findComment(String issueId){
        OnlineDataSource.getInstance().findComment(issueId, new CustomObserver<CommentListResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(CommentListResBean result) {
                if(null != result){
                    if (isActivityAlive()){
                        getMvpView().findCommentSuccess(result.getData());
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

    public void addComment(String issueId, String content, String pid, String replyUserId, String time, String userId){
        OnlineDataSource.getInstance().addComment(issueId, content, pid, replyUserId, time, userId, new CustomObserver<BaseBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(BaseBean result) {
                if (isActivityAlive()){
                    getMvpView().addCommentSuccess();
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
