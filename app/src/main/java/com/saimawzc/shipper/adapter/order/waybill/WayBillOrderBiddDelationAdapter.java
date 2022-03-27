package com.saimawzc.shipper.adapter.order.waybill;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.FooterHolder;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.order.BiddingDelationDto;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/6.
 * 预运单竞价详情
 */

public class WayBillOrderBiddDelationAdapter extends BaseAdapter{

    private List<BiddingDelationDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private String id;
    private String type="";
    public WayBillOrderBiddDelationAdapter(List<BiddingDelationDto> mDatas, Context mContext,
                                           String id,String type) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
        this.id=id;
        this.type=type;
    }

    public void setData(List<BiddingDelationDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<BiddingDelationDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<BiddingDelationDto> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_waybillbidddelation, parent,
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
            final BiddingDelationDto dto=mDatas.get(position);
            ((ViewHolder) holder).tvCarriveName.setText(dto.getCysName());
            ((ViewHolder) holder).tvLowprice.setText(dto.getBiddPrice()+"");
            ((ViewHolder) holder).tvBiddNum.setText(dto.getCountNum()+"");
            ((ViewHolder) holder).tvrobNum.setText(dto.getBiddWeight()+"");
            if(dto.getPointStatus()==2){//已经分配
                ((ViewHolder) holder).tvOrder.setText("已分配");
                ((ViewHolder) holder).tvOrder.setClickable(false);
            }

            ((ViewHolder) holder).tvBiddhistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    bundle.putString("from","orderbiddhistory");
                    bundle.putString("id",id);
                    bundle.putString("cysId",dto.getCysId());
                    activity.readyGo(OrderMainActivity.class,bundle);

                }
            });


            ((ViewHolder) holder).tvOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(type.equals("waybill")){
                        orderBidden(id,dto.getCysId(),2);
                    }else {
                        orderBidden(id,dto.getCysId(),3);
                    }

                }
            });

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
        @BindView(R.id.tvCarriveName) TextView tvCarriveName;
        @BindView(R.id.biddhistory)TextView tvBiddhistory;
        @BindView(R.id.tvLowprice)TextView tvLowprice;
        @BindView(R.id.tvrobNum)TextView tvrobNum;
        @BindView(R.id.tvBiddNum)TextView tvBiddNum;
        @BindView(R.id.tvOrder) TextView tvOrder;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

    private void orderBidden(String id,String cysId,int wayBillType){
        activity.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("cysId",cysId);
            jsonObject.put("wayBillType",wayBillType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        activity.orderApi.wayBillBiddOrder(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                activity.dismissLoadingDialog();
                activity.finish();


            }
            @Override
            public void fail(String code, String message) {
                activity.dismissLoadingDialog();
                activity.showMessage(message);
            }
        });
    }






}
