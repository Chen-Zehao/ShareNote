package com.scnu.base.ui.view.titlebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.scnu.sharenote.R;
import com.scnu.utils.DisplayUtil;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

/**
 * Created by ChenZehao
 * on 2020/2/27
 */
public class BaseTitleBar extends FrameLayout implements View.OnClickListener {

    private Context mContext;
    private TextView mTvTitle;
    private AppCompatImageView ivBack;

    private TitleBarBackListener backListener;

    public BaseTitleBar(Context context) {
        super(context);
    }

    public BaseTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(mContext,context.obtainStyledAttributes(attrs, R.styleable.TitleBarView));
    }

    protected void initView(Context context, TypedArray typedArray){
        if (null == context) {
            return;
        }
        View view = inflate(context, R.layout.view_title_bar,null);
        addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                DisplayUtil.dip2px(mContext,48));
        mTvTitle = view.findViewById(R.id.tv_bar_title);
        ivBack = view.findViewById(R.id.iv_back);
        if(!TextUtils.isEmpty(typedArray.getString(R.styleable.TitleBarView_title_text))){
            mTvTitle.setText(typedArray.getString(R.styleable.TitleBarView_title_text));
        }
        mTvTitle.setTextColor(typedArray.getColor(R.styleable.TitleBarView_title_text_color,
                ContextCompat.getColor(context,R.color.text_black)));
        ivBack.setOnClickListener(this);
    }

    /**
     * 返回按钮callBack
     * @param titleBarBackListener
     * @return
     */
    public BaseTitleBar setTitleBarOnBackListener(TitleBarBackListener titleBarBackListener){
        this.backListener = titleBarBackListener;
        return this;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            if (null != backListener) {
                backListener.onBackClick();
            }
        }
    }
}
