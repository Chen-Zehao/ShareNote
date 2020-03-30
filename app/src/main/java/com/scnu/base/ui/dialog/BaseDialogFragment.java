package com.scnu.base.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * DialogFragment基础类
 */
public class BaseDialogFragment extends DialogFragment{

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (dialog.getWindow() != null) {
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.gravity = Gravity.CENTER;
            dialog.getWindow().setAttributes(lp);
        }
        return dialog;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentManager temp = null;
            super.show(temp, tag);
        } catch (Exception e) {
        }

        if (manager == null) {
            return;
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onResume() {
        super.onResume();
        resizeDialogFragment();
    }

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
        lp.width = Resources.getSystem().getDisplayMetrics().widthPixels * 86 / 100;
        window.setLayout(lp.width, lp.height);
    }

}
