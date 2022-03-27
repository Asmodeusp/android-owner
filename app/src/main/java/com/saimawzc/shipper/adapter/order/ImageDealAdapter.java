package com.saimawzc.shipper.adapter.order;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.order.ImageDto;
import com.saimawzc.shipper.weight.utils.loadimg.ImageLoadUtil;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yyfy_yf on 2017/12/20.
 * 企业执照
 *
 */

public class ImageDealAdapter extends BaseAdapter {


    private List<ImageDto> mDatas;
    private BaseActivity mContext;
    private LayoutInflater mInflater;


    public ImageDealAdapter(List<ImageDto> mDatas, BaseActivity mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }
    public void setData(List<ImageDto> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<ImageDto> getData() {
        return mDatas;
    }

    public void addMoreData(List<ImageDto> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_phonto, parent,
                    false);
            return new ImageDealAdapter.ViewHolder(view);

    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ImageDealAdapter.ViewHolder) {

            ImageDto imageDto=mDatas.get(position);

            if(imageDto.getType().equals("pic")){
                ((ViewHolder) holder).video_play.setVisibility(View.GONE);
            }else if( imageDto.getType().equals("video")){
                ((ViewHolder) holder).video_play.setVisibility(View.VISIBLE);
            }

            if(!TextUtils.isEmpty(mDatas.get(position).getImgurls())){
                if(mDatas.get(position).getImgurls().contains("http")){
                    Log.e("msg","加载网络"+mDatas.get(position).getImgurls());
                    ImageLoadUtil.imageLoaderProxy.displayImage(mContext,mDatas.get(position).getImgurls(),((ViewHolder) holder).imgView);
                }else {
                    Log.e("msg","加载本地图片"+mDatas.get(position).getImgurls());
                    Uri uri = Uri.fromFile(new File(mDatas.get(position).getImgurls()));
                    ImageLoadUtil.displayImage(mContext, uri, ((ViewHolder) holder).imgView);
                }
            }

            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(holder.itemView, position);
                        return false;
                    }
                });
            }
        }

    }
    /**
     * 获取网落图片资源
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return bitmap;

    }
    @Override
    public int getItemCount() {
        return mDatas.size() == 0 ? 0 : mDatas.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)ImageView imgView;
        @BindView(R.id.delete)ImageView imgDelete;
        @BindView(R.id.video_play)ImageView video_play;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }
}
