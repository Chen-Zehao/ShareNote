package com.scnu.base.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scnu.sharenote.R;

import androidx.annotation.Nullable;

/**
 * Created by ChenZehao
 * on 2020/2/21
 */
public class BaseEmptyView extends LinearLayout implements IEmptyView {

    private Context mContext;
    private LinearLayout rootView;
    private LinearLayout mLinError;
    private LinearLayout mLinEmpty;
    private ImageView mIvError;
    private TextView mTvRetry;
    private ImageView mIvEmpty;
    private TextView mTvEmptyDesc;
    private EmptyRetryClickListener mRetryClickListener;

    public BaseEmptyView(Context context) {
        super(context);
    }

    public BaseEmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(mContext,context.obtainStyledAttributes(attrs, R.styleable.EmptyView));
    }

    public void setEmptyRetryClickListener(EmptyRetryClickListener retryClickListener){
        this.mRetryClickListener = retryClickListener;
    }

    protected void initView(Context context, TypedArray typedArray) {
        if (null == context) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.view_empty_layout,null);
        addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        rootView = view.findViewById(R.id.root_view);
        mLinError = view.findViewById(R.id.lin_error);
        mLinEmpty = view.findViewById(R.id.lin_empty);
        mIvError = view.findViewById(R.id.iv_error_image);
        mTvRetry = view.findViewById(R.id.tv_retry);
        mIvEmpty = view.findViewById(R.id.iv_empty_image);
        mTvEmptyDesc = view.findViewById(R.id.tv_empty_message);

        /** 初始状态 */
        mLinError.setVisibility(View.GONE);
        mLinEmpty.setVisibility(View.GONE);

        /**
         * 重新加载点击事件回调
         */
        mTvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mRetryClickListener) {
                    mRetryClickListener.onRetryClick();
                }
            }
        });

        if (null != typedArray.getDrawable(R.styleable.EmptyView_empty_error_image)) {
            mIvError.setImageDrawable(typedArray.getDrawable(R.styleable.EmptyView_empty_error_image));
        }

        if (!TextUtils.isEmpty(typedArray.getString(R.styleable.EmptyView_empty_desc))) {
            mTvEmptyDesc.setText(typedArray.getString(R.styleable.EmptyView_empty_desc));
        }

        if (null != typedArray.getDrawable(R.styleable.EmptyView_empty_image)) {
            mIvEmpty.setImageDrawable(typedArray.getDrawable(R.styleable.EmptyView_empty_image));
        }
    }

    @Override
    public void showErrorView() {
        rootView.setVisibility(View.VISIBLE);
        mLinError.setVisibility(View.VISIBLE);
        mLinEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        rootView.setVisibility(View.VISIBLE);
        mLinEmpty.setVisibility(View.VISIBLE);
        mLinError.setVisibility(View.GONE);
    }

    @Override
    public void hideView() {
        if (rootView.getVisibility() == View.VISIBLE) {
            rootView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setEmptyImage(int resId) {
        mIvEmpty.setImageResource(resId);
    }

    @Override
    public void setEmptyDesc(int strId) {
        mTvEmptyDesc.setText(mContext.getString(strId));
    }

    @Override
    public void setEmptyDesc(String str) {
        mTvEmptyDesc.setText(str);
    }

    @Override
    public void setInternetErrorDesc(String str) {
        mTvEmptyDesc.setText(str);
    }

    @Override
    public boolean isVisible() {
        return rootView.getVisibility() == View.VISIBLE;
    }
}