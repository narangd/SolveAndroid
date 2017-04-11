package com.example.jobs.solveandroid.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jobs on 2017. 4. 11..
 */

public class ViewUtil {
    public static int dp2Pixel(Context context, int dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5);
    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
