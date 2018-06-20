package com.example.superman.expandabletextview.utlis;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class OsUtils {

    public static int[] getScreenWidthHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        int[] ints = new int[]{width, height};
        return ints;
    }
}
