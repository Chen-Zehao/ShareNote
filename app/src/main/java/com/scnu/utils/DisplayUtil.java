package com.scnu.utils;

import android.content.Context;
import android.content.res.Resources;
import android.text.Selection;
import android.text.Spannable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * Created by ChenZehao
 * on 2019/12/5
 */
/**
 * 分辨率转换工具类
 */
public class DisplayUtil {

    /**
     * <将px值转换为dip或dp值，保证尺寸大小不变> <功能详细描述>
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * <将dip或dp值转换为px值，保证尺寸大小不变> <功能详细描述>
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * <将px值转换为sp值，保证文字大小不变> <功能详细描述>
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * <将sp值转换为px值，保证文字大小不变> <功能详细描述>
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * <动态设置控件大小，单位PX> <功能详细描述>
     */
    public static void setParams(Context context, int width, int height,
                                 View view) {
        ViewGroup.LayoutParams Params = (ViewGroup.LayoutParams) view
                .getLayoutParams();
        Params.width = DisplayUtil.dip2px(context, width / 2);
        Params.height = DisplayUtil.dip2px(context, height / 2);
        view.setLayoutParams(Params);
    }

    public static void margin(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    /**
     * <全屏控件按比例设置大小，防止不等比拉伸，填写比例时尽量化简，最好控制在十以下> widthPercent 标注宽度 heightPercent
     * 标注高度 <功能详细描述>
     */

    public static void setFullScreenView(Context context, int widthPercent,
                                         int heightPercent, View view) {
        if (widthPercent == 0)
            return;
        if (heightPercent == 0)
            return;
        ViewGroup.LayoutParams Params = (ViewGroup.LayoutParams) view
                .getLayoutParams();
        Params.width = context.getResources().getDisplayMetrics().widthPixels;
        Params.height = Params.width * heightPercent / widthPercent;
        view.setLayoutParams(Params);
    }

    /**
     * <按比例设置控件宽高> <功能详细描述> Blank 标注中空白区域的总和 number 平分的等份
     */

    public static void setPercentView(Context context, int widthPercent,
                                      int heightPercent, int Blank, int number, View view) {
        if (widthPercent == 0)
            return;
        if (heightPercent == 0)
            return;
        if (number < 1)
            return;
        ViewGroup.LayoutParams Params = (ViewGroup.LayoutParams) view.getLayoutParams();
        if (Blank == 0) {
            Params.width = context.getResources().getDisplayMetrics().widthPixels / number;
            Params.height = Params.width * heightPercent / widthPercent;
        } else {
            Params.width = (context.getResources().getDisplayMetrics().widthPixels - DisplayUtil
                    .dip2px(context, (Blank / 2))) / number;
            Params.height = Params.width * heightPercent / widthPercent;
        }
        view.setLayoutParams(Params);
    }

    /**
     * 设置光标位置在文字最后
     *
     * @param editText
     */
    public static void setEditTextCursorLocation(EditText editText) {
        CharSequence text = editText.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

    /**
     * 设置光标位置在文字最后
     */
    public static int getTitleBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources resources = context.getResources();
        //获取status_bar_height资源的ID
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public static float getStatusBarHeightDP(Context context) {
        float statusBarHeight = 0;
        Resources resources = context.getResources();
        //获取status_bar_height资源的ID
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            synchronized (mTmpValue) {
                TypedValue value = mTmpValue;
                context.getResources().getValue(resourceId, value, true);
                statusBarHeight = (int) TypedValue.complexToFloat(value.data);
            }
        }

        return statusBarHeight;
    }

    private static TypedValue mTmpValue = new TypedValue();

    public static int getXmlDef(Context context, int id) {
        synchronized (mTmpValue) {
            TypedValue value = mTmpValue;
            context.getResources().getValue(id, value, true);
            return (int) TypedValue.complexToFloat(value.data);
        }
    }
}