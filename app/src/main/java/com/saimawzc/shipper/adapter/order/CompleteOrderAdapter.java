package com.saimawzc.shipper.adapter.order;

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
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.order.OrderListDto;
import com.saimawzc.shipper.weight.utils.loadimg.ImageLoadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 *
 */

public class CompleteOrderAdapter extends BaseAdapter{

    private List<OrderListDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    public OnTabClickListener onTabClickListener;

    public void setOnTabClickListener(OnTabClickListener onItemTabClickListener) {
        this.onTabClickListener = onItemTabClickListener;
    }
    public interface OnTabClickListener {
        void onItemClick(String type, int position);
    }

    public CompleteOrderAdapter(List<OrderListDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }

    public void setData(List<OrderListDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<OrderListDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<OrderListDto> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_orderlist, parent,
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
            OrderListDto dto=mDatas.get(position);
            ImageLoadUtil.displayImage(mContext,dto.getCompanyLogo(),((ViewHolder) holder).imageView);
            ((ViewHolder) holder).tvName.setText(dto.getMaterialsName());

            if(dto.getWeightUnit()==1){
                ((ViewHolder) holder).tvWeight.setText(dto.getTotalWeight()+"吨");
                ((ViewHolder) holder).tvoverWeight.setText(dto.getOverAllotWeight()+"吨");
            }else {
                ((ViewHolder) holder).tvWeight.setText(dto.getTotalWeight()+"方");
                ((ViewHolder) holder).tvoverWeight.setText(dto.getOverAllotWeight()+"方");
            }

            ((ViewHolder) holder).tvCreatTime.setText(dto.getCreateTime());

           ((ViewHolder) holder).tvAdress.setText(dto.getFromUserAddress());
           ((ViewHolder) holder).tvAdressTo.setText(dto.getToUserAddress());
           ((ViewHolder) holder).tvFromCompany.setText(dto.getFromName());
           ((ViewHolder) holder).tvToCompany.setText(dto.getToName());


           ((ViewHolder) holder).viewTab1.setText("对账");
            ((ViewHolder) holder).viewTab2.setVisibility(View.GONE);
            ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
            ((ViewHolder) holder).tvStatus.setText("已完成");


            if(onTabClickListener!=null){
                ((ViewHolder) holder).viewTab1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("tab1", position);
                    }
                });
                ((ViewHolder) holder).viewTab2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("tab2", position);
                    }
                });
                ((ViewHolder) holder).viewTab3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("tab3", position);
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
        @BindView(R.id.image) ImageView imageView;
        @BindView(R.id.tvName)TextView tvName;
        @BindView(R.id.tvWeight)TextView tvWeight;
        @BindView(R.id.tvCreatTime)TextView tvCreatTime;
        @BindView(R.id.tvAdress)TextView tvAdress;
        @BindView(R.id.tvStatus)TextView tvStatus;
        @BindView(R.id.tvAdressTo)TextView tvAdressTo;
        @BindView(R.id.tvoverWeight)TextView tvoverWeight;//剩余量
        @BindView(R.id.from_company)TextView tvFromCompany;
        @BindView(R.id.to_company)TextView tvToCompany;
        @BindView(R.id.viewTab1)TextView viewTab1;
        @BindView(R.id.viewTab2)TextView viewTab2;
        @BindView(R.id.viewTab3)TextView viewTab3;

    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
