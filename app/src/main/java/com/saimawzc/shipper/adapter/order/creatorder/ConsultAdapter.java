package com.saimawzc.shipper.adapter.order.creatorder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.FooterHolder;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.order.ConsultDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 *
 */

public class ConsultAdapter extends BaseAdapter{

    private List<ConsultDto.data> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public HashMap<Integer,Boolean> map=null;
    int type=0;

    public OnItemCheckListener onItemChckListener;
    public void setOnItemCheckListener(OnItemCheckListener onItemClickListener) {
        this.onItemChckListener = onItemClickListener;
    }
    public interface OnItemCheckListener {
        void onItemClick(View view, int position, boolean isselect);
    }
    public ConsultAdapter(List<ConsultDto.data> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }

    public ConsultAdapter(List<ConsultDto.data> mDatas, Context mContext,int type) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
        this.type=type;
        map = new HashMap<>();
        init();
    }

    private void init() {
        if (null == mDatas||mDatas.size()<=0) {
            return;
        }else{
            for(int i=0;i<mDatas.size();i++){
                map.put(i, false);
            }
        }
    }
    public void setData(List<ConsultDto.data> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<ConsultDto.data> getData() {
        return mDatas;
    }
    public void addMoreData(List<ConsultDto.data> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_constute, parent,
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
            ConsultDto.data dto=mDatas.get(position);


            if (map!= null) {
                if(map.get(position)!=null){
                    ((ViewHolder) holder).checkBox.setChecked(map.get(position));
                }else {
                    ((ViewHolder) holder).checkBox.setChecked(false);
                }
            } else {
                ((ViewHolder) holder).checkBox.setChecked(false);
            }
            ((ViewHolder) holder).tvThreeNo.setText(dto.getOrderCode());
            ((ViewHolder) holder).tvGoodName.setText(dto.getMatericalName());
            ((ViewHolder) holder).tvoverWeight.setText(dto.getQuantity());
            ((ViewHolder) holder).tvSendCompany.setText(dto.getFrom_Name());
            ((ViewHolder) holder).tvReceiveCompany.setText(dto.getSendTo_Name());
            ((ViewHolder) holder).tvSendAdress.setText(dto.getFhAddr());
            if(onTabClickListener!=null){
                ((ViewHolder) holder).viewTab3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("creat", position);
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
            if(type==1){//销售订单
                ((ViewHolder) holder).checkBox.setVisibility(View.VISIBLE);
                if(onItemChckListener!=null){
                    ((ViewHolder) holder).checkBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean tag;
                            if(((ViewHolder) holder).checkBox.isChecked()){
                                map.put(position,true);
                                tag=true;
                            }else {
                                map.put(position,false);
                                tag=false;

                            }
                            onItemChckListener.onItemClick(holder.itemView, position,tag);
                        }
                    });
                }
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
        @BindView(R.id.tvThreeNo)TextView tvThreeNo;
        @BindView(R.id.tvGoodName)TextView tvGoodName;
        @BindView(R.id.tvoverWeight)TextView tvoverWeight;
        @BindView(R.id.tvSendCompany)TextView tvSendCompany;
        @BindView(R.id.tvReceiveCompany)TextView tvReceiveCompany;
        @BindView(R.id.tvSendAdress)TextView tvSendAdress;
        @BindView(R.id.viewTab3)TextView viewTab3;
        @BindView(R.id.checkBox)
        CheckBox checkBox;


    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
