package com.saimawzc.shipper.adapter.order.advancewaybill;

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
import com.saimawzc.shipper.dto.order.wallbill.OrderWayBillDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 */

public class WayBillListAdapter extends BaseAdapter{

    private List<OrderWayBillDto.waybillData> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public WayBillListAdapter(List<OrderWayBillDto.waybillData> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }

    public OnTabClickListener onTabClickListener;

    public void setOnTabClickListener(OnTabClickListener onItemTabClickListener) {
        this.onTabClickListener = onItemTabClickListener;
    }
    public interface OnTabClickListener {
        void onItemClick(String type, int position);
    }
    public void setData(List<OrderWayBillDto.waybillData> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<OrderWayBillDto.waybillData> getData() {
        return mDatas;
    }
    public void addMoreData(List<OrderWayBillDto.waybillData> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_waybill, parent,
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
            OrderWayBillDto.waybillData dto=mDatas.get(position);
            ((ViewHolder) holder).tvAdress.setText(dto.getFromUserAddress());
            ((ViewHolder) holder).tvAdressTo.setText(dto.getToUserAddress());
            ((ViewHolder) holder).tvToCompany.setText(dto.getToName());
            ((ViewHolder) holder).tvFromCompany.setText(dto.getFromName());

            ((ViewHolder) holder).tvId.setText(""+dto.getWayBillNo());
            ((ViewHolder) holder).tvWeight.setText(""+dto.getTotalWeight());
            ((ViewHolder) holder).tvCreatTime.setText(""+dto.getCreateTime());


            if(dto.getCheckStatus()==1){//1审核中 2审核通过 3未通过
                ((ViewHolder) holder).viewTab1.setText("删除");
                ((ViewHolder) holder).viewTab2.setText("清单");
                ((ViewHolder) holder).viewTab3.setText("审核");
                ((ViewHolder) holder).tvStatus.setText("未审核");
                ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab3.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab4.setVisibility(View.GONE);
            }else if(dto.getCheckStatus()==2){

                if(dto.getWayBillStatus()==3){//如果等于3 直接就是已分配，
                    // 否则通过getSendType判断是竞价中还是指派
                    ((ViewHolder) holder).viewTab1.setText("清单");
                    ((ViewHolder) holder).viewTab2.setText("分配详情");
                    ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
                    ((ViewHolder) holder).viewTab4.setVisibility(View.GONE);
                    ((ViewHolder) holder).tvStatus.setText("已分配");
                }else {
                    if(dto.getSendType()==0){//未分配
                        ((ViewHolder) holder).tvStatus.setText("未分配");
                        ((ViewHolder) holder).viewTab1.setText("删除");
                        ((ViewHolder) holder).viewTab2.setText("清单");
                        ((ViewHolder) holder).viewTab4.setText("指派");
                        ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                        ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);
                        ((ViewHolder) holder).viewTab4.setVisibility(View.VISIBLE);
                        if(dto.getIsBidd()==1){
                            ((ViewHolder) holder).viewTab3.setText("竞价");
                            ((ViewHolder) holder).viewTab3.setVisibility(View.VISIBLE);
                        }else {
                            ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
                        }
                    }else if(dto.getSendType()==2){//竞价中
                        ((ViewHolder) holder).viewTab1.setText("清单");
                        ((ViewHolder) holder).viewTab2.setText("竞价详情");
                        ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                        ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);
                        ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
                        ((ViewHolder) holder).viewTab4.setVisibility(View.GONE);
                        ((ViewHolder) holder).tvStatus.setText("竞价中");
                    }else if(dto.getSendType()==1){//已指派
                        ((ViewHolder) holder).viewTab1.setText("清单");
                        ((ViewHolder) holder).viewTab2.setText("分配详情");
                        ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                        ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);
                        ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
                        ((ViewHolder) holder).viewTab4.setVisibility(View.GONE);
                        ((ViewHolder) holder).tvStatus.setText("已分配");
                    }
                }
            }else if(dto.getCheckStatus()==3){
                ((ViewHolder) holder).tvStatus.setText("未通过");
                ((ViewHolder) holder).viewTab1.setText("重新编辑");
                ((ViewHolder) holder).viewTab2.setText("删除");
                ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
                ((ViewHolder) holder).viewTab4.setVisibility(View.GONE);
            }


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
                ((ViewHolder) holder).viewTab4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("tab4", position);
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
        @BindView(R.id.image) ImageView imageView;
        @BindView(R.id.tvAdress)TextView tvAdress;
        @BindView(R.id.tvAdressTo)TextView tvAdressTo;
        @BindView(R.id.tvStatus)TextView tvStatus;
        @BindView(R.id.tvId)TextView tvId;
        @BindView(R.id.tvWeight)TextView tvWeight;
        @BindView(R.id.tvCreatTime)TextView tvCreatTime;
        @BindView(R.id.from_company)TextView tvFromCompany;
        @BindView(R.id.to_company)TextView tvToCompany;
        @BindView(R.id.viewtab1)TextView viewTab1;
        @BindView(R.id.viewtab2)TextView viewTab2;
        @BindView(R.id.viewtab3)TextView viewTab3;
        @BindView(R.id.viewtab4)TextView viewTab4;

    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


}
