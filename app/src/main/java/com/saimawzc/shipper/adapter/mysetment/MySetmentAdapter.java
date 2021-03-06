package com.saimawzc.shipper.adapter.mysetment;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.FooterHolder;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.myselment.MySetmentDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 我的结算
 */

public class MySetmentAdapter extends BaseAdapter {

    private List<MySetmentDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private int type=1;

    public OnTabClickListener onTabClickListener;

    public void setOnTabClickListener(OnTabClickListener onItemTabClickListener) {
        this.onTabClickListener = onItemTabClickListener;
    }
    public interface OnTabClickListener {
        void onItemClick(String type, int position);
    }

    public MySetmentAdapter(List<MySetmentDto> mDatas, Context mContext,int type) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
        this.type=type;
    }

    public void setData(List<MySetmentDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<MySetmentDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<MySetmentDto> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_mysetment, parent,
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
            MySetmentDto dto=mDatas.get(position);
            ((ViewHolder) holder).tvCompany.setText(dto.getCompanyName());
            ((ViewHolder) holder).tvId.setText(dto.getSettleNo());
            ((ViewHolder) holder).tvFromadress.setText(dto.getFromUserAddress());
            ((ViewHolder) holder).tvToadress.setText(dto.getToUserAddress());
            ((ViewHolder) holder).tvGoodInfo.setText(dto.getMaterialsName());
            ((ViewHolder) holder).tvWeight.setText(dto.getSettleWeight());
            ((ViewHolder) holder).tvMoney.setText(dto.getSettleAmount());
            ((ViewHolder) holder).tvTime.setText(dto.getStartTime()+"-"+dto.getEndTime());
            if(type==2){
                ((ViewHolder) holder).viewTab1.setVisibility(View.GONE);
            }else {
                ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
            }


            if(onTabClickListener!=null){
                ((ViewHolder) holder).viewTab1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("tab1", position);
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
        @BindView(R.id.tvCompany)TextView tvCompany;
        @BindView(R.id.tvId)TextView tvId;
        @BindView(R.id.tvFromadress)TextView tvFromadress;
        @BindView(R.id.tvToadress)TextView tvToadress;
        @BindView(R.id.tvGoodInfo) TextView tvGoodInfo;
        @BindView(R.id.tvWeight)TextView tvWeight;
        @BindView(R.id.tvMoney)TextView tvMoney;
        @BindView(R.id.tvTime)TextView tvTime;
        @BindView(R.id.viewtab1)TextView viewTab1;


    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


}
