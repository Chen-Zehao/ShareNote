package com.scnu.base.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.scnu.sharenote.R;
import com.scnu.utils.DisplayUtil;
import com.wang.avi.AVLoadingIndicatorView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author zhang_sen
 * @version v1.0
 * @since 2020-01-07 11:29
 */
public class BaseLoadingDialog extends BaseDialogFragment {

    private Context mContext;
    private AVLoadingIndicatorView loadingView;

    public BaseLoadingDialog(Context mContext) {
        this.mContext = mContext;
    }

    public static BaseLoadingDialog getInstance(Context context){
        BaseLoadingDialog loadingDialog = new BaseLoadingDialog(context);
        return loadingDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.bg_loading_dialog);
        return inflater.inflate(R.layout.dialog_loading, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f;
        window.setAttributes(windowParams);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingView = view.findViewById(R.id.dialog_loading);
        loadingView.show();
    }

    @Override
    public void resizeDialogFragment() {
        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
        lp.width = DisplayUtil.dip2px(mContext,110);
        window.setLayout(lp.width, lp.height);
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != loadingView) {
            loadingView.hide();
        }
    }
}
