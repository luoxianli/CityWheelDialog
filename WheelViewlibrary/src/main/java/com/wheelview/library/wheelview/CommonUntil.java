package com.wheelview.library.wheelview;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @desc：
 * @author: zy
 * @time: 2017/2/17 0017
 * @reviser_and_time:
 */

public class CommonUntil {
    //获取屏幕高度
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    //获取屏幕宽度
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
