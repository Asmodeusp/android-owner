package com.saimawzc.shipper.adapter.consign;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.FooterHolder;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.consign.ConsignDto;
import com.saimawzc.shipper.dto.order.SendCarDto;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.weight.utils.dialog.PopupWindowUtil;
import com.saimawzc.shipper.weight.utils.loadimg.ImageLoadUtil;
import com.werb.permissionschecker.PermissionChecker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 派车
 */

public class ConsignAdapter extends BaseAdapter {

    private List<ConsignDto.data> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;



    public interface OnTabClickListener {
        void onItemClick(String type, int position);
    }

    public ConsignAdapter(List<ConsignDto.data> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
        permissionChecker=new PermissionChecker(activity);
    }

    public void setData(List<ConsignDto.data> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<ConsignDto.data> getData() {
        return mDatas;
    }
    public void addMoreData(List<ConsignDto.data> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_yanhuo, parent,
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
            ConsignDto.data dto=mDatas.get(position);
             ImageLoadUtil.displayImage(mContext,dto.getCompanyLogo(),((ViewHolder) holder).imageView);
             ((ViewHolder) holder).tvAdress.setText(dto.getFromUserAddress());
            ((ViewHolder) holder).tvAdressTo.setText(dto.getToUserAddress());
            ((ViewHolder) holder).tvFromCompany.setText(dto.getFromName());
            ((ViewHolder) holder).tvToCompany.setText(dto.getToName());
            ((ViewHolder) holder).tvUitl1.setText("预运单号：");
            ((ViewHolder) holder).tvUitl2.setText("物料：");
            ((ViewHolder) holder).tvId.setText(dto.getDispatchNo());
            ((ViewHolder) holder).tvCarInfo.setText(dto.getSjName()+"|"+dto.getCarNo());
            ((ViewHolder) holder).tvGoodInfo.setText(""+dto.getMaterialsName());
            ((ViewHolder) holder).viewTab1.setText("验货");
            ((ViewHolder) holder).viewTab2.setVisibility(View.GONE);
            ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
            ((ViewHolder) holder).ico_more.setVisibility(View.GONE);

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
        @BindView(R.id.tvAdress)TextView tvAdress;
        @BindView(R.id.tvAdressTo)TextView tvAdressTo;
        @BindView(R.id.image) ImageView imageView;
        @BindView(R.id.tvId)TextView tvId;
        @BindView(R.id.tvcarinfo)TextView tvCarInfo;
        @BindView(R.id.goodinfo)TextView tvGoodInfo;
        @BindView(R.id.viewtab1)TextView viewTab1;
        @BindView(R.id.viewtab2)TextView viewTab2;
        @BindView(R.id.viewtab3)TextView viewTab3;
        @BindView(R.id.imgPhone)ImageView imagePhone;
        @BindView(R.id.ico_more) LinearLayout ico_more;
        @BindView(R.id.from_company)TextView tvFromCompany;
        @BindView(R.id.to_company)TextView tvToCompany;
        @BindView(R.id.tvUitl1)TextView tvUitl1;
        @BindView(R.id.tvUitl2)TextView tvUitl2;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


}
