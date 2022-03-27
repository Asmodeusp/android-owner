package com.saimawzc.shipper.adapter.order.creatorder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.FooterHolder;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.order.bidd.CarTypeDo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 发货商收货商
 */

public class ChooseCarTypeAdapter extends BaseAdapter{

    private List<CarTypeDo> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private int type=0;
    public HashMap<Integer,Boolean> map=null;

    public ChooseCarTypeAdapter(List<CarTypeDo> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;

    }
    public ChooseCarTypeAdapter(List<CarTypeDo> mDatas, Context mContext, int type) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
        this.type=type;
        map = new HashMap<>();
        init();
    }
    public OnItemCheckListener onItemChckListener;
    public void setOnItemCheckListener(OnItemCheckListener onItemClickListener) {
        this.onItemChckListener = onItemClickListener;

    }
    public interface OnItemCheckListener {
        void onItemClick(View view, int position, boolean isselect);
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

    public void setData(List<CarTypeDo> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<CarTypeDo> getData() {
        return mDatas;
    }
    public void addMoreData(List<CarTypeDo> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }

    public HashMap<Integer, Boolean> getMap() {
        return map;
    }

    public void setMap(HashMap<Integer, Boolean> map) {
        this.map = map;
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
            View view = mInflater.inflate(R.layout.item_goodscompany, parent,
                    false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterHolder(mInflater.inflate(R.layout.layout_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if(holder instanceof ViewHolder){
                 CarTypeDo dto=mDatas.get(position);
                 ((ViewHolder) holder).tvCompany.setText(dto.getCarTypeName());
                ((ViewHolder) holder).checkBox.setVisibility(View.VISIBLE);
                if (map!= null) {
                    if(map.get(position)!=null){
                        ((ViewHolder) holder).checkBox.setChecked(map.get(position));
                    }else {
                        ((ViewHolder) holder).checkBox.setChecked(false);
                    }
                } else {
                    ((ViewHolder) holder).checkBox.setChecked(false);
                }

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
        @BindView(R.id.tvgoodscompany) TextView tvCompany;
        @BindView(R.id.checkBox) CheckBox checkBox;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
