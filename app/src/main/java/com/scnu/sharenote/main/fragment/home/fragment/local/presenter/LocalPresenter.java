package com.scnu.sharenote.main.fragment.home.fragment.local.presenter;

import android.content.Context;

import com.scnu.base.BasePresenter;
import com.scnu.enums.QueryTypeEnum;
import com.scnu.model.Macro;
import com.scnu.model.ThemeModel;
import com.scnu.model.UserModel;
import com.scnu.sharenote.main.fragment.home.fragment.local.ui.ILocalView;
import com.scnu.source.beans.ArticleListResBean;
import com.scnu.source.beans.PriceResBean;
import com.scnu.source.beans.ThemeResBean;
import com.scnu.source.http.CustomObserver;
import com.scnu.source.http.OnlineDataSource;
import com.scnu.utils.MyApplication;
import com.scnu.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ChenZehao
 * on 2020/1/15
 */
public class LocalPresenter extends BasePresenter<ILocalView> {
    public LocalPresenter(Context context, ILocalView view) {
        super(context, view);
    }

    public void getThemeList(){
        OnlineDataSource.getInstance().getThemeList(new CustomObserver<ThemeResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(ThemeResBean result) {
                if(null != result.getData() && !result.getData().isEmpty()){
                    List<String> themeList = new ArrayList<>();
                    themeList.add("全部类型");
                    for (ThemeModel datum : result.getData()) {
                        themeList.add(datum.getName());
                    }
                    if(isActivityAlive()){
                        getMvpView().getThemeListSuccess(themeList);
                    }
                }
            }

            @Override
            public void onFail(Throwable exception) {
                super.onFail(exception);
                ToastUtils.showToast(getContext(),exception.toString());
            }
        });
    }

    public void getPriceList(String theme){
        OnlineDataSource.getInstance().getThemePriceList(theme, new CustomObserver<PriceResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
            @Override
            public void onSuccess(PriceResBean result) {
                if(null != result.getData() && !result.getData().isEmpty()){
                    if(isActivityAlive()){
                        getMvpView().getPriceListSuccess(result.getData());
                    }
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
     * 查询文章列表
     * @param sortType
     * @param city
     * @param pageNum
     */
    public void queryArticleList(String sortType, String city, double latitude, double longtitude, String price, String theme, int pageNum){
        UserModel userModel = (UserModel) MyApplication.getObject(Macro.KEY_USER);
        OnlineDataSource.getInstance().queryArticleList(QueryTypeEnum.getTypeCity(), sortType, userModel.getUserId(), city, latitude, longtitude, price, theme, userModel.getUserId(), pageNum, new CustomObserver<ArticleListResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
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
     * @param sortType
     * @param city
     * @param pageNum
     */
    public void loadMoreArticle(String sortType, String city, double latitude, double longtitude, String price, String theme, int pageNum){
        UserModel userModel = (UserModel) MyApplication.getObject(Macro.KEY_USER);
        OnlineDataSource.getInstance().queryArticleList(QueryTypeEnum.getTypeCity(), sortType, userModel.getUserId(), city, latitude, longtitude, price, theme, userModel.getUserId(), pageNum, new CustomObserver<ArticleListResBean>(getContext(),((AppCompatActivity)getContext()).getSupportFragmentManager()) {
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
