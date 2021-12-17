package com.saimawzc.shipper.adapter.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.FooterHolder;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.carrier.MyCarrierGroupDto;
import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.weight.RepeatClickUtil;
import com.saimawzc.shipper.weight.TimeChooseDialogUtil;
import com.saimawzc.shipper.weight.utils.api.mine.MineApi;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.http.Http;
import com.saimawzc.shipper.weight.utils.listen.TimeChooseListener;

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
 * 指派二级页面
 */
public class OrderAssiginSecondAdapter extends BaseAdapter{

    private List<OrderAssignmentSecondDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private int isShowTime=2;
     TimeChooseDialogUtil timeChooseDialogUtil;
    public OrderAssiginSecondAdapter(List<OrderAssignmentSecondDto> mDatas,
                                     Context mContext,int isShowTime) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
        this.isShowTime=isShowTime;
        timeChooseDialogUtil=new TimeChooseDialogUtil(activity);
    }

    public void setData(List<OrderAssignmentSecondDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<OrderAssignmentSecondDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<OrderAssignmentSecondDto> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_order_assignsecond, parent,
                    false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterHolder(mInflater.inflate(R.layout.layout_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        if(holder instanceof ViewHolder){
            holder.setIsRecyclable(false);
            final OrderAssignmentSecondDto dto=mDatas.get(position);
            ((ViewHolder) holder).tvName.setText(dto.getCysName());
            ((ViewHolder) holder).tvPhone.setText(dto.getCysPhone());

            if(dto.getTrantPrice()==0){
                ((ViewHolder) holder).edTrangprice.setText("");
            }else {
                ((ViewHolder) holder).edTrangprice.setText(dto.getTrantPrice()+"");
            }
            if(dto.getTrantNum()==0){
                ((ViewHolder) holder).edtrantNum.setText("");
            }else {
                ((ViewHolder) holder).edtrantNum.setText(dto.getTrantNum()+"");
            }
            if(isShowTime==1){
                ((ViewHolder) holder).rlTime.setVisibility(View.VISIBLE);
            }else {
                ((ViewHolder) holder).rlTime.setVisibility(View.GONE);
            }
            ((ViewHolder) holder).tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatas.remove(position);
                    notifyDataSetChanged();
                }
            });

            ((ViewHolder) holder).edTrangprice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                    if(!TextUtils.isEmpty(s.toString())){
                        if(!s.toString().equals(".")){
                            dto.setTrantPrice(Double.parseDouble(s.toString()));
                        }
                    }
                }
            });
            ((ViewHolder) holder).edtrantNum.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                    if(!TextUtils.isEmpty(s.toString())){
                        dto.setTrantNum(Double.parseDouble(s.toString()));
                    }
                }
            });
            ((ViewHolder) holder).tvTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("msg","点击时间");
                    if(!RepeatClickUtil.isFastClick()){
                        activity.showMessage("您操作太频繁，请稍后再试");
                        return;
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(timeChooseDialogUtil==null){
                                timeChooseDialogUtil=new TimeChooseDialogUtil(mContext);
                            }
                            timeChooseDialogUtil.showDialog(new TimeChooseListener() {
                                @Override
                                public void getTime(String result) {
                                    ((ViewHolder) holder).tvTime.setText(result);
                                    dto.setZpTime(result);
                                }
                                @Override
                                public void cancel() {
                                    timeChooseDialogUtil.dissDialog();
                                }
                            });
                        }
                    });

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
        @BindView(R.id.tvName)TextView tvName;
        @BindView(R.id.tvphone)TextView tvPhone;
        @BindView(R.id.tvDelete)TextView tvDelete;
        @BindView(R.id.edtrantPrice) EditText edTrangprice;
        @BindView(R.id.edtrantNum)EditText edtrantNum;
        @BindView(R.id.rltime)
        RelativeLayout rlTime;
        @BindView(R.id.tvtime)
        TextView tvTime;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
