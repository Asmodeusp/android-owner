package com.saimawzc.shipper.adapter.order.manage;

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
import com.saimawzc.shipper.dto.order.manage.OrderManageDto;
import com.saimawzc.shipper.weight.utils.loadimg.ImageLoadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 调度
 */

public class OrderManageAdapter extends BaseAdapter{

    private List<OrderManageDto.manageData> mDatas=new ArrayList<>();

    private Context mContext;
    private LayoutInflater mInflater;

    public OrderManageAdapter(List<OrderManageDto.manageData> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }

    public void setData(List<OrderManageDto.manageData> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<OrderManageDto.manageData> getData() {
        return mDatas;
    }
    public void addMoreData(List<OrderManageDto.manageData> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }
    public OnTabClickListener onTabClickListener;

    public void setOnTabClickListener(OnTabClickListener onItemTabClickListener) {
        this.onTabClickListener = onItemTabClickListener;
    }
    public interface OnTabClickListener {
        void onItemClick(String type, int position);
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
            View view = mInflater.inflate(R.layout.item_ordermanage, parent,
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
            OrderManageDto.manageData dto=mDatas.get(position);
            ((ViewHolder) holder).tvAdress.setText(dto.getFromUserAddress());
             ((ViewHolder) holder).tvAdressTo.setText(dto.getToUserAddress());
             ((ViewHolder) holder).tvFromCompany.setText(dto.getFromName());
             ((ViewHolder) holder).tvToCompany.setText(dto.getToName());
            ((ViewHolder) holder).tvTime.setText(""+dto.getCreateTime());
            ((ViewHolder) holder).tvCarModel.setText(""+dto.getCarTypeName());
            ((ViewHolder) holder).tvWeight.setText(""+dto.getTotalWeight());
            ImageLoadUtil.displayImage(mContext,dto.getCompanyLogo(),((ViewHolder) holder).imageView);
            if(dto.getSendType()==0||dto.getStatus()==1){//未分配
                ((ViewHolder) holder).viewTab1.setText("删除");
                ((ViewHolder) holder).viewTab2.setText("竞价");
                ((ViewHolder) holder).viewTab3.setText("指派");
                ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab3.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).tvStatus.setText("未分配");

            }else if(dto.getStatus()==3){//已分配
                ((ViewHolder) holder).viewTab1.setText("分配详情");
                ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab2.setVisibility(View.GONE);
                ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
                ((ViewHolder) holder).tvStatus.setText("已分配");
            }else if(dto.getStatus()==2){//竞价中
                if(dto.getSendType()==1){//已指派
                    ((ViewHolder) holder).viewTab1.setText("分配详情");
                    ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).viewTab2.setVisibility(View.GONE);
                    ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
                    ((ViewHolder) holder).tvStatus.setText("已分配");
                }else if(dto.getSendType()==2){//已竞价
                    ((ViewHolder) holder).viewTab1.setText("竞价详情");
                    ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).viewTab2.setVisibility(View.GONE);
                    ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
                    ((ViewHolder) holder).tvStatus.setText("竞价中");
                }
            }else if(dto.getStatus()==4){//已拒绝
                ((ViewHolder) holder).viewTab1.setText("分配详情");
                ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab2.setVisibility(View.GONE);
                ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
                ((ViewHolder) holder).tvStatus.setText("已拒绝");
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
        @BindView(R.id.tvTime) TextView tvTime;
        @BindView(R.id.tvAdress)TextView tvAdress;
        @BindView(R.id.tvAdressTo)TextView tvAdressTo;
        @BindView(R.id.tvCarModel)TextView tvCarModel;
        @BindView(R.id.tvWeight)TextView tvWeight;
        @BindView(R.id.imgView)ImageView imageView;
        @BindView(R.id.from_company)TextView tvFromCompany;
        @BindView(R.id.to_company)TextView tvToCompany;
        @BindView(R.id.tvStatus)TextView tvStatus;
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
