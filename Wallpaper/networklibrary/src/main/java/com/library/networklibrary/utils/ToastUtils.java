package com.library.networklibrary.utils;

import android.widget.Toast;

import com.library.networklibrary.HttpUtils;

public class ToastUtils {

    private static Toast mToast;

    public static void showToast(String str) {
        if (mToast == null) {
            mToast = Toast.makeText(HttpUtils.getContext(), str, Toast.LENGTH_LONG);
        } else {
            mToast.setText(str);
        }
        mToast.show();
    }

}
