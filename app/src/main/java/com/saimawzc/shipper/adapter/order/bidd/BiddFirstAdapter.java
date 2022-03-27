package com.saimawzc.shipper.adapter.order.bidd;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.FooterHolder;
import com.saimawzc.shipper.dto.order.bidd.BiddFirstDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 */

public class BiddFirstAdapter extends BaseAdapter{

    private List<BiddFirstDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    public BiddFirstAdapter(List<BiddFirstDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<BiddFirstDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<BiddFirstDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<BiddFirstDto> newDatas) {
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
            View view = mInflater.inflate(R.layout.biddfirst_item, parent,
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
            final BiddFirstDto dto=mDatas.get(position);
            ((ViewHolder) holder).tvStartTime.setText(dto.getStartTime());
            ((ViewHolder) holder).tvEndTime.setText(dto.getEndTime());
            ((ViewHolder) holder).tvBiddWeight.setText(dto.getPointWeight()+dto.getUnit());
            ((ViewHolder) holder).tvBiddRole.setText(dto.getRoleType());
            ((ViewHolder) holder).tvTaskTimeStart.setText(dto.getTaskStartTime());
            ((ViewHolder) holder).tvTaskTimeEnd.setText(dto.getTaskEndTime());
            if(dto.getStatus()==0){
                ((ViewHolder) holder).tvStatus.setText("未开始");
            }else if(dto.getStatus()==1){
                ((ViewHolder) holder).tvStatus.setText("竞价中");
            }else if(dto.getStatus()==2){
                ((ViewHolder) holder).tvStatus.setText("分配中");
            }else if(dto.getStatus()==3){
                ((ViewHolder) holder).tvStatus.setText("已终止");
            }else if(dto.getStatus()==4){
                ((ViewHolder) holder).tvStatus.setText("已分配");
            }
            if(!TextUtils.isEmpty(dto.getShowRanking())){
                if(dto.getShowRanking().equals("1")){
                    ((ViewHolder) holder).tvRank.setVisibility(View.VISIBLE);
                }else {
                    ((ViewHolder) holder).tvRank.setVisibility(View.GONE);
                }
            }
            if(dto.getStatus()==1){
                ((ViewHolder) holder).tvEndBidd.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).tvEnMach.setVisibility(View.GONE);
            }else if(dto.getStatus()==2){
                ((ViewHolder) holder).tvEndBidd.setVisibility(View.GONE);
                ((ViewHolder) holder).tvEnMach.setVisibility(View.VISIBLE);
            }else {
                ((ViewHolder) holder).tvEndBidd.setVisibility(View.GONE);
                ((ViewHolder) holder).tvEnMach.setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(dto.getType())){
                if(dto.getType().equals("5")){
                    ((ViewHolder) holder).llsendByCar.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).tvCarType.setText(dto.getCarTypeName());
                   if(!TextUtils.isEmpty(dto.getNeedBeiDou())){
                       if(dto.getNeedBeiDou().equals("1")){
                           ((ViewHolder) holder).checkBeiDou.setChecked(true);
                       }else {
                           ((ViewHolder) holder).checkBeiDou.setChecked(false);
                       }
                   }
                    if(dto.getMoreDispatch().equals("1")){
                        ((ViewHolder) holder).checkMore.setChecked(true);
                    }else {
                        ((ViewHolder) holder).checkMore.setChecked(false);
                    }
                }else {
                    ((ViewHolder) holder).llsendByCar.setVisibility(View.GONE);
                }
            }

            if(onTabClickListener!=null){
                ((ViewHolder) holder).tvRank.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("rank", position);
                    }
                });
                ((ViewHolder) holder).tvMatch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("match", position);
                    }
                });
                ((ViewHolder) holder).tvEndBidd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("endbidd", position);
                    }
                });
                ((ViewHolder) holder).tvEnMach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("endmach", position);
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
        @BindView(R.id.tvstart_time) TextView tvStartTime;
        @BindView(R.id.tvend_time)TextView tvEndTime;
        @BindView(R.id.tv_biddweight)TextView tvBiddWeight;
        @BindView(R.id.tv_biddrole)TextView tvBiddRole;
        @BindView(R.id.tvstart_time_task)TextView tvTaskTimeStart;
        @BindView(R.id.tvend_timetask)TextView tvTaskTimeEnd;
        @BindView(R.id.llsendByCar) LinearLayout llsendByCar;
        @BindView(R.id.checkBeidou) CheckBox checkBeiDou;
        @BindView(R.id.checkmoresend)CheckBox checkMore;
        @BindView(R.id.tvCarType)TextView tvCarType;
        @BindView(R.id.tvRank)TextView tvRank;
        @BindView(R.id.tvMatch)TextView tvMatch;
        @BindView(R.id.tvEndBidd)TextView tvEndBidd;
        @BindView(R.id.tvEndMach)TextView tvEnMach;
        @BindView(R.id.tvStatus)TextView tvStatus;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
