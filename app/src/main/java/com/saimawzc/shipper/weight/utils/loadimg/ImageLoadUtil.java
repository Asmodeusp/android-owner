package com.saimawzc.shipper.weight.utils.loadimg;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.saimawzc.shipper.R;


import java.io.File;
import java.security.MessageDigest;


/**
 * Created by yyfy_yf on 2017/8/15.
 *
 * @ author nsj
 * @ deprecated 图片加载
 */

public class ImageLoadUtil {


    public static ImageLoadUtil imageLoaderProxy;


    public static void getInstance() {
        imageLoaderProxy = new ImageLoadUtil();

    }

    /**
     * @param
     * @return
     * @author tryedhp
     * @time 2017/8/28/0028  14:45
     * @describe 显示网络图片
     */
    public static void displayImage(Context context, String imgeUrl, ImageView imageView) {
        try {
            if (imgeUrl != null) {
                if (!imgeUrl.equals("")) {
                    Glide.with(context.getApplicationContext())
                            .load(imgeUrl)
                            .placeholder(R.drawable.ic_gf_default_photo)
                            .error(R.drawable.ic_gf_default_photo)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                           // .a()
                            .transform(new CenterCrop(),new GlideRoundTransform(context,8))
                            .thumbnail(0.5f)
                            //.override(200, 200)
                            .into(imageView);

                } else {
                    imageView.setImageResource(R.drawable.ic_gf_default_photo);
                }
            } else {
                imageView.setImageResource(R.drawable.ic_gf_default_photo);
            }
        } catch (Exception e) {
        }

    }

    /**
     * @param
     * @time 2017/8/28/0028  14:45
     * @describe 显示 URL图片
     */
    public static void displayImage(Context context, Uri uri, ImageView imageView) {
        try {
            if (uri != null) {
                Glide.with(context.getApplicationContext())
                        .load(uri)
                        .placeholder(R.drawable.ic_gf_default_photo)
                        .error(R.drawable.ic_gf_default_photo)
                        .dontAnimate()
                        .into(imageView);

            } else {
                imageView.setImageResource(R.drawable.ic_gf_default_photo);
            }
        } catch (Exception e) {

        }

    }

    /**
     * @param
     * @describe 显示资源文件
     */
    public static void displayImage(Context context, int imageRes, ImageView imageView) {
        try {
            if (imageRes > 0) {
                Glide.with(context.getApplicationContext())
                        .load(imageRes)
                        .placeholder(R.drawable.ic_gf_default_photo)
                        .error(R.drawable.ic_gf_default_photo)
                        .dontAnimate()
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.ic_gf_default_photo);
            }
        } catch (Exception e) {

        }

    }


    /**
     * @param
     * @return
     * @author tryedhp
     * @time 2017/8/28/0028  14:45
     * @describe 显示本地文件
     */
    public static void displayLocalImage(Context context, File file, ImageView imageView) {
        try {
            if (file != null) {
                Glide.with(context.getApplicationContext())
                        .load(file)
                        .placeholder(R.drawable.ic_gf_default_photo)
                        .error(R.drawable.ic_gf_default_photo)
                        .dontAnimate()
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.ic_gf_default_photo);
            }
        } catch (Exception e) {

        }

    }

    /**
     * Glide 圆角 Transform
     */
    public static class GlideRoundTransform extends BitmapTransformation {

        private  float radius = 0f;

        /**
         * 构造函数 默认圆角半径 4dp
         *
         * @param context Context
         */
        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        /**
         * 构造函数
         *
         * @param context Context
         * @param dp 圆角半径
         */
        public GlideRoundTransform(Context context, int dp) {

            radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private  Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }



        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        }
    }
}
