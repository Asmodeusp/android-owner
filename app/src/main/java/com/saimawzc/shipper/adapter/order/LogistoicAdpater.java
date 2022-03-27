package com.saimawzc.shipper.adapter.order;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.FooterHolder;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.order.send.LogistcsDto;
import com.saimawzc.shipper.weight.utils.loadimg.PlusImageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 物流信息
 */
public class LogistoicAdpater extends BaseAdapter {

    private List<LogistcsDto.transportLogList> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private ShowImageAdpater imgAdapter;
    public LogistoicAdpater(List<LogistcsDto.transportLogList> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }
    public void setData(List<LogistcsDto.transportLogList> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
    public List<LogistcsDto.transportLogList> getData() {
        return mDatas;
    }
    public void addMoreData(List<LogistcsDto.transportLogList> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_logticas, parent,
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
            LogistcsDto.transportLogList dto=mDatas.get(position);
            ((ViewHolder) holder).tvContect.setText(dto.getName());
            ((ViewHolder) holder).tvtime.setText(dto.getCreateTime());
            if(!TextUtils.isEmpty(dto.getPicture())){
                ((ViewHolder) holder).tvline.getLayoutParams().height=200;
                ((ViewHolder) holder).rv.setVisibility(View.VISIBLE);
                final ArrayList<String>datas=new ArrayList<>();
                 if(!TextUtils.isEmpty(dto.getPicture())){
                     if(!dto.getPicture().contains(",")){
                         datas.add(dto.getPicture());
                     }else {
                         String[] node = dto.getPicture().split(",");
                         for(int i=0;i<node.length;i++){
                             datas.add(node[i]);
                         }
                     }
                 }
                imgAdapter=new ShowImageAdpater(datas,mContext);
                GridLayoutManager layoutManager= new GridLayoutManager(mContext, 3, RecyclerView.VERTICAL, false);
                ((ViewHolder) holder).rv.setLayoutManager(layoutManager);
                ((ViewHolder) holder).rv.setAdapter(imgAdapter);
                imgAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(mContext, PlusImageActivity.class);
                        intent.putStringArrayListExtra("imglist", datas);
                        intent.putExtra("currentpos", position);
                        intent.putExtra("from", "delation");
                        activity.startActivity(intent);
                    }
                    @Override
                    public void onItemLongClick(View view, int position) {
                    }
                });
            }else {
                ((ViewHolder) holder).rv.setVisibility(View.GONE);
                ((ViewHolder) holder).tvline.getLayoutParams().height=80;
            }
            if(dto.isFlag()){//已经处理
                ((ViewHolder) holder).imgisDeal.setBackgroundResource(R.drawable.ico_process_black);
                ((ViewHolder) holder).tvContect.setTextColor(mContext.getResources().getColor(R.color.gray333));
                ((ViewHolder) holder).tvline.setBackgroundResource(R.color.blue);
                ((ViewHolder) holder).rv.setVisibility(View.VISIBLE);
            }else {//尚未处理
                ((ViewHolder) holder).imgisDeal.setBackgroundResource(R.drawable.ico_process_glay);
                ((ViewHolder) holder).tvContect.setTextColor(mContext.getResources().getColor(R.color.gray888));
                ((ViewHolder) holder).tvline.setBackgroundResource(R.color.bg_18);
                ((ViewHolder) holder).rv.setVisibility(View.GONE);
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
        }if (holder instanceof FooterHolder) {
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    ((FooterHolder) holder).tvFooter.setVisibility(View.VISIBLE);
                    ((FooterHolder) holder).tvFooter.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    ((FooterHolder) holder).tvFooter.setVisibility(View.VISIBLE);
                    ((FooterHolder) holder).tvFooter.setText("正在加载数据...");
                    break;
                case LOADING_FINISH:
                    ((FooterHolder) holder).tvFooter.setVisibility(View.VISIBLE);
                    ((FooterHolder) holder).tvFooter.setText("没有更多了~");
                    break;
                default:
                    //  ((FooterHolder) holder).tvFooter.setVisibility(View.GONE);
                    break;
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
        @BindView(R.id.imgisDeal) ImageView imgisDeal;
        @BindView(R.id.tvContect) TextView tvContect;
        @BindView(R.id.rv) RecyclerView rv;
        @BindView(R.id.tvline) TextView tvline;
        @BindView(R.id.lldpwn) RelativeLayout lldpwn;
        @BindView(R.id.tvtime)TextView tvtime;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
