package com.saimawzc.shipper.adapter.order.creatorder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.FooterHolder;
import com.saimawzc.shipper.dto.order.creatorder.ChooseRouteDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 发货商收货商
 */

public class ChooseRouteAdapter extends BaseAdapter{

    private List<ChooseRouteDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    public ChooseRouteAdapter(List<ChooseRouteDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);

    }
    public OnItemCheckListener onItemChckListener;
    public void setOnItemCheckListener(OnItemCheckListener onItemClickListener) {
        this.onItemChckListener = onItemClickListener;

    }
    public interface OnItemCheckListener {
        void onItemClick(View view, int position, boolean isselect);
    }


    public void setData(List<ChooseRouteDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<ChooseRouteDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<ChooseRouteDto> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        }
        else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_chooseroute, parent,
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
            ChooseRouteDto dto=mDatas.get(position);
            ((ViewHolder) holder).tvRoute.setText(""+dto.getRouteName());
            ((ViewHolder) holder).tvStartLocation.setText(""+dto.getFromUserAddress());
            ((ViewHolder) holder).tvEndLocation.setText(""+dto.getToUserAddress());

            if(onTabClickListener!=null){
                ((ViewHolder) holder).imgDelation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("imgdealtion", position);
                    }
                });

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
        return mDatas.size() == 0 ? 0 : mDatas.size()+1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @BindView(R.id.tvroute) TextView tvRoute;
        @BindView(R.id.tvstartLocation) TextView tvStartLocation;
        @BindView(R.id.tvendLocation) TextView tvEndLocation;
        @BindView(R.id.imgdelation) ImageView imgDelation;

    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
