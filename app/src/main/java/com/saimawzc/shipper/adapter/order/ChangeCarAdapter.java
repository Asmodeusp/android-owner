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
import com.saimawzc.shipper.dto.order.send.ChangeCarDto;
import com.saimawzc.shipper.weight.utils.loadimg.ImageLoadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 *换车信息
 */

public class ChangeCarAdapter extends BaseAdapter{

    private List<ChangeCarDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    public OnTabClickListener onTabClickListener;

    public void setOnTabClickListener(OnTabClickListener onItemTabClickListener) {
        this.onTabClickListener = onItemTabClickListener;
    }
    public interface OnTabClickListener {
        void onItemClick(String type, int position);
    }

    public ChangeCarAdapter(List<ChangeCarDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }

    public void setData(List<ChangeCarDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<ChangeCarDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<ChangeCarDto> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_changecar, parent,
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
            ChangeCarDto dto=mDatas.get(position);
            ((ViewHolder) holder).tvDanHao.setText("运单单号："+dto.getCarNo());
            ((ViewHolder) holder).tvOldCar.setText("原车辆："+dto.getOldCarNo());
            ((ViewHolder) holder).tvnewCar.setText("替换车辆："+dto.getCarNo());
            ((ViewHolder) holder).tvbeizhu.setText("备注："+dto.getReason());


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
        @BindView(R.id.tvdanhao)TextView tvDanHao;
        @BindView(R.id.tvOldCar)TextView tvOldCar;
        @BindView(R.id.tvnewCar)TextView tvnewCar;
        @BindView(R.id.tvbeizhu)TextView tvbeizhu;
        @BindView(R.id.tvcreatime)TextView tvcreatime;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
