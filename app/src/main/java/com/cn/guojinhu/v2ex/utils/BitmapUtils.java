package com.cn.guojinhu.v2ex.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cn.guojinhu.v2ex.R;

public class BitmapUtils {

    public static void display(Context context, ImageView imageView, String url) {
        if (null != url) {
            Log.i("Vo7ice","BitmapUtils:"+"http:"+url);
            Glide.with(context).load("http:"+url).into(imageView);
        }else{
            Glide.with(context).load(R.mipmap.ic_launcher).into(imageView);
        }
    }
}
