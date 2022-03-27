package com.saimawzc.shipper.adapter.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.FooterHolder;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.main.MainIndexDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 汽车类型
 */

public class MainIndexAdpater extends BaseAdapter {

    private List<MainIndexDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    public MainIndexAdpater(List<MainIndexDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }
    public void setData(List<MainIndexDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<MainIndexDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<MainIndexDto> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_mainindex, parent,
                    false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterHolder(mInflater.inflate(R.layout.layout_footer, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ViewHolder){
            MainIndexDto dto=mDatas.get(position);
            ((ViewHolder) holder).tvName.setText(dto.getName());
            if(position==0){
                ((ViewHolder) holder).imgSrc.setImageResource(R.drawable.ico_duizhang);
            }
            if(position==1){
                ((ViewHolder) holder).imgSrc.setImageResource(R.drawable.ico_jiesuan);
            }
            if(position==2){
                ((ViewHolder) holder).imgSrc.setImageResource(R.drawable.ico_fapiao);
            }
            if(position==3){
                ((ViewHolder) holder).imgSrc.setImageResource(R.drawable.ico_koukuan);
            }
            if(position==4){
                ((ViewHolder) holder).imgSrc.setImageResource(R.drawable.ico_paiche);
            }
            if(position==5){
                ((ViewHolder) holder).imgSrc.setImageResource(R.drawable.ico_yujing);
            }
            if(position==6){
                ((ViewHolder) holder).imgSrc.setImageResource(R.drawable.ico_baobiao);
            }
            if(position==7){
                ((ViewHolder) holder).imgSrc.setImageResource(R.drawable.ico_wuliao);
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

    @Override
    public int getItemCount() {
        return mDatas.size() == 0 ? 0 : mDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @BindView(R.id.tvName) TextView tvName;
        @BindView(R.id.imgsrc)
        ImageView imgSrc;


    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

    private  int mPosition;

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }
}
