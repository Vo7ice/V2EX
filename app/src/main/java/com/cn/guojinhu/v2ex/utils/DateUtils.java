package com.cn.guojinhu.v2ex.utils;

import android.content.Context;

import com.cn.guojinhu.v2ex.R;

public class DateUtils {

    public static String longToDate(Context context, long time) {
        long now = System.currentTimeMillis();
        long delta = now / 1000 - time;
        if (delta < 60) {
            return context.getResources().getString(R.string.last_modified_00);
        } else if (delta < 60 * 60) {
            long minute = delta / 60;
            return context.getResources().getString(R.string.last_modified_01, String.valueOf(minute));
        } else if (delta < 60 * 60 * 24) {
            long hour = delta / 3600;
            long minite = delta - hour * 3600;
            return context.getResources().getString(R.string.last_modified_02, String.valueOf(hour));
        } else {
            return context.getResources().getString(R.string.last_modified_03);
        }
    }

    public static String replies(Context context, long replies){
        return context.getResources().getString(R.string.reply_count,String.valueOf(replies));
    }
}
