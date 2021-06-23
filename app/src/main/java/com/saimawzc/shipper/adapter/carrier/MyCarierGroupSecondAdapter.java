package com.saimawzc.shipper.adapter.carrier;

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
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.carrier.MycarrierGroupSecondDto;
import com.saimawzc.shipper.weight.utils.api.mine.MineApi;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.http.Http;
import com.saimawzc.shipper.weight.utils.loadimg.ImageLoadUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/4.
 * 我的司机
 */

public class MyCarierGroupSecondAdapter extends BaseAdapter {
    private List<MycarrierGroupSecondDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public MineApi mineApi= Http.http.createApi(MineApi.class);
    public MyCarierGroupSecondAdapter(List<MycarrierGroupSecondDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }
    public void setData(List<MycarrierGroupSecondDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
    public List<MycarrierGroupSecondDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<MycarrierGroupSecondDto> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_carrier_groupsecond, parent,
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
            final MycarrierGroupSecondDto myDriverDto=mDatas.get(position);
            ((ViewHolder) holder).tvName.setText(myDriverDto.getCysName());
            ((ViewHolder) holder).tvPhone.setText(myDriverDto.getPhone());
            ImageLoadUtil.displayImage(mContext,myDriverDto.getPicture(),((ViewHolder) holder).imageView);
            ((ViewHolder) holder).tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete(myDriverDto.getId(),position);
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
       @BindView(R.id.tvname)TextView tvName;
        @BindView(R.id.tv_delete)TextView tvDelete;
        @BindView(R.id.imgView)ImageView imageView;
        @BindView(R.id.tvphone)TextView tvPhone;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
    private void delete(String id,final int position){
        activity.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.deleteCarrive(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                activity.dismissLoadingDialog();
                activity.showMessage("删除成功");
                mDatas.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void fail(String code, String message) {
                activity.dismissLoadingDialog();
                activity.showMessage(message);
            }
        });

    }
}
