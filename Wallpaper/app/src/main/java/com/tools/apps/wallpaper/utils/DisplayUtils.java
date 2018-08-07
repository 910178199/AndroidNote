package com.tools.apps.wallpaper.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Display utils.
 *
 * An utils class that make operations of display easier.
 *
 * */

public class DisplayUtils {

    private int dpi = 0;

    public DisplayUtils(Context context) {
        dpi = context.getResources().getDisplayMetrics().densityDpi;
    }

    public float dpToPx(int dp) {
        if (dpi == 0) {
            return 0;
        }
        return (float) (dp * (dpi / 160.0));
    }

    public static int getStatusBarHeight(Resources r) {
        int result = 0;
        int resourceId = r.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = r.getDimensionPixelSize(resourceId);
        }
        return result;
    }





    public static void cancelTranslucentNavigation(Activity a) {
        a.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }


    public static void setTypeface(Context c, TextView t) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            t.setTypeface(Typeface.createFromAsset(c.getAssets(), "fonts/Courier.ttf"));
        }
    }

    public static String abridgeNumber(int num) {
        if (num < 1000) {
            return String.valueOf(num);
        } else {
            num = num / 100;
            return (num / 10.0) + "K";
        }
    }

    public static boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }



    public static boolean isLandscape(Context context) {
        return context.getResources()
                .getConfiguration()
                .orientation == Configuration.ORIENTATION_LANDSCAPE;
    }


}
