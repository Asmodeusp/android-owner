package com.saimawzc.shipper.adapter.order.bidd;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.FooterHolder;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.order.BiddingDelationDto;
import com.saimawzc.shipper.dto.order.OrderListDto;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.weight.utils.api.mine.MineApi;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.http.Http;

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
 */

public class OrderBiddDelationAdapter extends BaseAdapter{

    private List<BiddingDelationDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private String id;
    public OrderBiddDelationAdapter(List<BiddingDelationDto> mDatas, Context mContext,String id) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
        this.id=id;
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
            View view = mInflater.inflate(R.layout.item_bidddelation, parent,
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
                ((ViewHolder) holder).edDun.setText(dto.getPointWeight());
                ((ViewHolder) holder).edDun.setEnabled(false);
                ((ViewHolder) holder).edDun.setTextColor(mContext.getResources().getColor(R.color.red));
                if(!TextUtils.isEmpty(dto.getRoleType())){
                    if(dto.getRoleType().equals("5")){
                        ((ViewHolder) holder).edCarNum.setText(dto.getPointCarNum()+"");
                        ((ViewHolder) holder).edCarNum.setEnabled(false);
                        ((ViewHolder) holder).edCarNum.setTextColor(mContext.getResources().getColor(R.color.red));
                    }else {
                        ((ViewHolder) holder).edCarNum.setTextColor(mContext.getResources().getColor(R.color.color_black));
                    }
                }
            }else {
                ((ViewHolder) holder).edDun.setTextColor(mContext.getResources().getColor(R.color.color_black));
                ((ViewHolder) holder).edCarNum.setTextColor(mContext.getResources().getColor(R.color.color_black));
            }
            if(!TextUtils.isEmpty(dto.getRoleType())){
                if(dto.getRoleType().equals("5")){
                    ((ViewHolder) holder).llCar.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).tvDriverName.setText(dto.getSjName());
                    ((ViewHolder) holder).tvCarNo.setText(dto.getCarNo());
                    ((ViewHolder) holder).tvCarNum.setText(dto.getCarNum());
                    ((ViewHolder) holder).rlCar.setVisibility(View.VISIBLE);
                }else {
                    ((ViewHolder) holder).llCar.setVisibility(View.GONE);
                    ((ViewHolder) holder).rlCar.setVisibility(View.GONE);
                }
            }else {
                ((ViewHolder) holder).llCar.setVisibility(View.GONE);
                ((ViewHolder) holder).rlCar.setVisibility(View.GONE);
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
            ((ViewHolder) holder).edDun.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isEmpty(s.toString())){
                        if(!s.toString().equals(".")){
                            dto.setMachNum(Double.parseDouble(s.toString()));
                        }
                    }
                }
            });

            ((ViewHolder) holder).edCarNum.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isEmpty(s.toString())){
                        if(!s.toString().equals(".")){
                            dto.setPointCarNum(Double.parseDouble(s.toString()));
                        }
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
        @BindView(R.id.edDun) EditText edDun;
        @BindView(R.id.llbyCar) LinearLayout llCar;
        @BindView(R.id.tvcarno)TextView tvCarNo;
        @BindView(R.id.tv_drivername)TextView tvDriverName;
        @BindView(R.id.tvcarnum)TextView tvCarNum;
        @BindView(R.id.rlcar) RelativeLayout rlCar;
        @BindView(R.id.edCarNum)EditText edCarNum;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }






}
