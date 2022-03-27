package com.saimawzc.shipper.adapter.order;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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

public class OrderListAdapter extends BaseAdapter{

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

    public OrderListAdapter(List<OrderListDto> mDatas, Context mContext) {
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
            if (TextUtils.isEmpty(dto.getResTxt2())) {
                ((ViewHolder) holder).resTxt2Linear.setVisibility(View.GONE);
            }else {
                ((ViewHolder) holder).resTxt2Linear.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).resTxt2Text.setText(dto.getResTxt2());
            }
            ImageLoadUtil.displayImage(mContext,dto.getCompanyLogo(),((ViewHolder) holder).imageView);
            ((ViewHolder) holder).tvName.setText(""+dto.getMaterialsName());
            ((ViewHolder) holder).tvWeight.setText(dto.getTotalWeight()+dto.getWeightUnitName());
            ((ViewHolder) holder).tvoverWeight.setText(dto.getOverAllotWeight()+dto.getWeightUnitName());
            ((ViewHolder) holder).tvCreatTime.setText(""+dto.getCreateTime());
            ((ViewHolder) holder).tvZtNum.setText(dto.getUnderWay()+dto.getWeightUnitName());
            ((ViewHolder) holder).tvConsultNum.setText(dto.getConsult()+dto.getWeightUnitName());
           ((ViewHolder) holder).tvAdress.setText(dto.getFromUserAddress());
            ((ViewHolder) holder).tvAdressTo.setText(dto.getToUserAddress());
            ((ViewHolder) holder).tvFromCompany.setText(dto.getFromName());
            ((ViewHolder) holder).tvToCompany.setText(dto.getToName());
           if(dto.getCheckStatus()==1){//1审核中 2审核通过 3未通过
               ((ViewHolder) holder).viewTab1.setText("编辑");
               ((ViewHolder) holder).viewTab2.setText("删除");
               ((ViewHolder) holder).viewTab3.setText("审核");
               ((ViewHolder) holder).tvStatus.setText("未审核");
               ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
               ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);
               ((ViewHolder) holder).viewTab3.setVisibility(View.VISIBLE);
           }else if(dto.getCheckStatus()==2){
                 if(dto.getWayBillStatus()==3){//如果等于3 直接就是已分配，
                                                // 否则通过getSendType判断是竞价中还是指派
                     ((ViewHolder) holder).viewTab1.setText("分配详情");
                     ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                     ((ViewHolder) holder).tvStatus.setText("已分配");
                     if(dto.getOverAllotWeight()>0){//还有剩余量，可以继续指派
                         ((ViewHolder) holder).viewTab2.setText("指派");
                         ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);
                     }else {//剩余量不等于0，不可以指派了
                         ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
                         ((ViewHolder) holder).viewTab2.setVisibility(View.GONE);
                     }
                     ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
                   }else {
                     if(dto.getSendType()==0){//未分配
                         ((ViewHolder) holder).tvStatus.setText("未分配");
                         ((ViewHolder) holder).viewTab1.setText("删除");
                         ((ViewHolder) holder).viewTab3.setText("指派");
                         if(dto.getIsBidd()==1){
                             ((ViewHolder) holder).viewTab2.setText("竞价");
                             ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);
                         }else {
                             ((ViewHolder) holder).viewTab2.setVisibility(View.GONE);
                         }
                         ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                         ((ViewHolder) holder).viewTab3.setVisibility(View.VISIBLE);
                     }else if(dto.getSendType()==2){//竞价中
                         ((ViewHolder) holder).tvStatus.setText("竞价中");
                         ((ViewHolder) holder).viewTab1.setText("竞价详情");
                         if(dto.getOverAllotWeight()>0){//还有剩余量，可以继续指派
                             ((ViewHolder) holder).viewTab2.setText("指派");
                             ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);

                             if(dto.getIsBidd()==1){
                                 ((ViewHolder) holder).viewTab3.setText("竞价");
                                 ((ViewHolder) holder).viewTab3.setVisibility(View.VISIBLE);
                             }else {
                                 ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
                             }
                         }else {//剩余量不等于0，不可以指派了
                             ((ViewHolder) holder).viewTab2.setVisibility(View.GONE);
                             ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
                         }
                         ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                     }else if(dto.getSendType()==1){//已指派
                         ((ViewHolder) holder).viewTab1.setText("分配详情");
                         if(dto.getOverAllotWeight()>0){//还有剩余量，可以继续指派
                             ((ViewHolder) holder).viewTab2.setText("指派");
                             ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);
                             if(dto.getIsBidd()==1){
                                 ((ViewHolder) holder).viewTab3.setText("竞价");
                                 ((ViewHolder) holder).viewTab3.setVisibility(View.VISIBLE);
                             }else {
                                 ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
                             }
                         }else {//剩余量不等于0，不可以指派了
                             ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
                             ((ViewHolder) holder).viewTab2.setVisibility(View.GONE);
                         }
                         ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
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
        @BindView(R.id.tvoverWeight)TextView tvoverWeight;
        @BindView(R.id.tvStatus)TextView tvStatus;
        @BindView(R.id.tvAdressTo)TextView tvAdressTo;
        @BindView(R.id.viewTab1)TextView viewTab1;
        @BindView(R.id.viewTab2)TextView viewTab2;
        @BindView(R.id.viewTab3)TextView viewTab3;
        @BindView(R.id.from_company)TextView tvFromCompany;
        @BindView(R.id.to_company)TextView tvToCompany;
        @BindView(R.id.tvztNum)TextView tvZtNum;
        @BindView(R.id.tvconsultNum)TextView tvConsultNum;
        @BindView(R.id.resTxt2Text)TextView resTxt2Text;
        @BindView(R.id.resTxt2Linear)
        LinearLayout resTxt2Linear;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
