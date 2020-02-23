package com.scnu.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;


/**
 * Created by ChenZehao
 * on 2019/12/5
 */
public class ToastUtils {
    private static Toast mToast;

    @SuppressLint("ShowToast")
    public static void showToast(Context mContext, String message, int duration) {
        if (TextUtils.isEmpty(message) || mContext == null) {
            return;
        }

        if (mToast == null) {
            mToast = Toast.makeText(mContext.getApplicationContext(), message, duration);
        } else {
            mToast.setDuration(duration);
            mToast.setText(message);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public static void showToast(Context mContext, String message) {
        showToast(mContext, message, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context mContext, int resId) {
        try {
            String message = mContext.getString(resId);
            showToast(mContext, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
