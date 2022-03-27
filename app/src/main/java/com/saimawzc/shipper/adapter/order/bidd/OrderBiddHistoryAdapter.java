package com.saimawzc.shipper.adapter.order.bidd;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.FooterHolder;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.order.OrderBiddenHistoryDto;
import com.saimawzc.shipper.weight.utils.api.mine.MineApi;
import com.saimawzc.shipper.weight.utils.http.Http;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 竞价历史
 */

public class OrderBiddHistoryAdapter extends BaseAdapter{

    private List<OrderBiddenHistoryDto> mDatas=new ArrayList<>();
    public MineApi mineApi= Http.http.createApi(MineApi.class);
    private Context mContext;
    private LayoutInflater mInflater;
    private int type=0;
    public OrderBiddHistoryAdapter(List<OrderBiddenHistoryDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }

    public void setData(List<OrderBiddenHistoryDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<OrderBiddenHistoryDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<OrderBiddenHistoryDto> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_orderhistory, parent,
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
            OrderBiddenHistoryDto dto=mDatas.get(position);
            ((ViewHolder) holder).tvXuhao.setText("第"+(position+1)+"次竞价");
            ((ViewHolder) holder).tvNum.setText(dto.getBiddWeight()+"吨");
            ((ViewHolder) holder).tvTime.setText(dto.getCreateTime());
            ((ViewHolder) holder).tvjj.setText(dto.getBiddPrice()+"元");
            if(!TextUtils.isEmpty(dto.getRoleType())){
                if(dto.getRoleType().equals("5")){
                    ((ViewHolder) holder).llCar.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).tvDriverName.setText(dto.getSjName());
                    ((ViewHolder) holder).tvCarNo.setText(dto.getCarNo());
                    ((ViewHolder) holder).tvCarNum.setText(dto.getCarNum());
                }else {
                    ((ViewHolder) holder).llCar.setVisibility(View.GONE);
                }
            }else {
                ((ViewHolder) holder).llCar.setVisibility(View.GONE);
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
        @BindView(R.id.tvXuhao) TextView tvXuhao;
        @BindView(R.id.tvTime)TextView tvTime;
        @BindView(R.id.tvjj)TextView tvjj;
        @BindView(R.id.tvNum)TextView tvNum;
        @BindView(R.id.llbyCar) LinearLayout llCar;
        @BindView(R.id.tvcarno)TextView tvCarNo;
        @BindView(R.id.tv_drivername)TextView tvDriverName;
        @BindView(R.id.tvcarnum)TextView tvCarNum;

    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

}
