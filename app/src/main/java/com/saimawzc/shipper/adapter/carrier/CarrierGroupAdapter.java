package com.saimawzc.shipper.adapter.carrier;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.FooterHolder;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.carrier.MyCarrierGroupDto;
import com.saimawzc.shipper.dto.identification.CompanyDto;
import com.saimawzc.shipper.weight.utils.api.mine.MineApi;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
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

public class CarrierGroupAdapter extends BaseAdapter{

    private List<MyCarrierGroupDto> mDatas=new ArrayList<>();
    public MineApi mineApi= Http.http.createApi(MineApi.class);
    private Context mContext;
    private LayoutInflater mInflater;
    private int type=0;
    public CarrierGroupAdapter(List<MyCarrierGroupDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }

    public void setData(List<MyCarrierGroupDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<MyCarrierGroupDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<MyCarrierGroupDto> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_carrier_group, parent,
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
            MyCarrierGroupDto companydto=mDatas.get(position);
            ((ViewHolder) holder).tvGroupName.setText(companydto.getCysName());

            ((ViewHolder) holder).tvDelete.setOnClickListener
                    (new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete(mDatas.get(position).getId(),position);
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
        @BindView(R.id.tvgroupname)TextView tvGroupName;
        @BindView(R.id.tv_delete)TextView tvDelete;

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
