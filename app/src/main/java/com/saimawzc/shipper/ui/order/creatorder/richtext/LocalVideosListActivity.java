package com.saimawzc.shipper.ui.order.creatorder.richtext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.order.ImageDealAdapter;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.order.ImageDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2019-05-07.
 * 本地视频列表
 */

public class LocalVideosListActivity extends BaseActivity {

    private ImageDealAdapter imageDealAdapter;
    private List<ImageDto> paths=new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.recycle)RecyclerView recyclerView;
    @Override
    protected int getViewId() {
        return R.layout.activity_localvideo;
    }

    @Override
    protected void init() {
        setToolbar(toolbar,"本地视频");
        imageDealAdapter = new ImageDealAdapter(paths, context);
        gridLayoutManager=new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(imageDealAdapter);
        imageDealAdapter.addMoreData(getVideoFromSDCard(this));
    }
    @Override
    protected void initListener() {
        imageDealAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(paths.size()<=0){
                    return;
                }
                Intent i = new Intent();
                i.putExtra("path",paths.get(position).getVideos());
                context.setResult(Activity.RESULT_OK, i);
                context.finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    /**
     * 从本地得到所有的视频地址
     */
    private List<ImageDto> getVideoFromSDCard(Context context) {
        List<ImageDto> list = new ArrayList<>();
        String[] projection = new String[]{MediaStore.Video.Media.DATA, MediaStore.Video.Media
                .DURATION,MediaStore.Video.VideoColumns.DATE_ADDED};
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null,
                null, MediaStore.Video.VideoColumns.DATE_ADDED + " DESC");
        while (cursor.moveToNext()) {
            String path = cursor
                    .getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
            long duration = cursor
                    .getInt(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
            ImageDto imageDto =new ImageDto();
            imageDto.setImgurls(path);
            imageDto.setType("video");
            imageDto.setVideos(path);
            paths.add(imageDto);
        }
        cursor.close();
        return list;
    }



}
