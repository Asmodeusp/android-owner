package com.saimawzc.shipper.weight.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2020/8/14.
 */

public class MyLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context.getApplicationContext()).load((String) path).into(imageView);
    }
}
