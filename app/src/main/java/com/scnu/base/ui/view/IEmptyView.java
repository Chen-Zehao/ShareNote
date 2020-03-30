package com.scnu.base.ui.view;
/**
 * Created by ChenZehao
 * on 2020/2/21
 */
public interface IEmptyView {
    /**
     * 展示网络错误页面
     */
    void showErrorView();

    /**
     * 展示空页面
     */
    void showEmptyView();

    /**
     * 设置隐藏
     */
    void hideView();

    /**
     * 设置空页面图片
     */
    void setEmptyImage(int resId);

    /**
     * 设置空页面文字描述
     */
    void setEmptyDesc(int strId);

    /**
     * 设置空页面文字描述
     */
    void setEmptyDesc(String str);


    /**
     * 设置网络错误页面文字描述
     */
    void setInternetErrorDesc(String str);


    /**
     * EmptyView是否展示了
     * @return
     */
    boolean isVisible();
}
