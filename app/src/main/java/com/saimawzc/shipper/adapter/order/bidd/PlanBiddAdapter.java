package com.saimawzc.shipper.adapter.order.bidd;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.FooterHolder;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.order.bidd.PlanBiddDto;
import com.saimawzc.shipper.weight.utils.loadimg.ImageLoadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 */

public class PlanBiddAdapter extends BaseAdapter {

    private List<PlanBiddDto.planBiddData> mDatas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private String type = "";

    public PlanBiddAdapter(List<PlanBiddDto.planBiddData> mDatas, Context mContext, String type) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity = (BaseActivity) mContext;
        this.type = type;
    }

    public void setData(List<PlanBiddDto.planBiddData> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<PlanBiddDto.planBiddData> getData() {
        return mDatas;
    }

    public void addMoreData(List<PlanBiddDto.planBiddData> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_planbidd, parent,
                    false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterHolder(mInflater.inflate(R.layout.layout_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            PlanBiddDto.planBiddData dto = mDatas.get(position);
            if (TextUtils.isEmpty(dto.getResTxt2())) {
                ((ViewHolder) holder).resTxt2Linear.setVisibility(View.GONE);
            } else {
                ((ViewHolder) holder).resTxt2Linear.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).resTxt2Text.setText(dto.getResTxt2());
            }
            ((ViewHolder) holder).tvAdress.setText(dto.getFromUserAddress());
            ((ViewHolder) holder).tvAdressTo.setText(dto.getToUserAddress());
            ((ViewHolder) holder).tvToCompany.setText(dto.getToName());
            ((ViewHolder) holder).tvFromCompany.setText(dto.getFromName());
            if (type.equals("1")) {//计划单
                ((ViewHolder) holder).tvName.setText(dto.getMaterialsName());
                ((ViewHolder) holder).tvCreatTime.setText("" + dto.getCreateTime());
                if (dto.getWeightUnit() == 1) {
                    ((ViewHolder) holder).tvWeight.setText(dto.getPointWeight() + "吨");
                } else {
                    ((ViewHolder) holder).tvWeight.setText(dto.getPointWeight() + "方");
                }
                ((ViewHolder) holder).tvUitl1.setText("货物名称：");
                ((ViewHolder) holder).tvUitl2.setText("总重量：");
                ((ViewHolder) holder).tvUitl3.setText("创建时间：");
            } else if (type.equals("2")) {//预运单
                ((ViewHolder) holder).tvUitl1.setText("预运单号：");
                ((ViewHolder) holder).tvUitl2.setText("总重量：");
                ((ViewHolder) holder).tvUitl3.setText("创建时间：");
                ((ViewHolder) holder).tvName.setText("" + dto.getWaybillNo());
                if (dto.getWeightUnit() == 1) {
                    ((ViewHolder) holder).tvWeight.setText(dto.getPointWeight() + "吨");
                } else {
                    ((ViewHolder) holder).tvWeight.setText(dto.getPointWeight() + "方");
                }
                ((ViewHolder) holder).tvCreatTime.setText("" + dto.getCreateTime());
            } else if (type.equals("3")) {
                ((ViewHolder) holder).tvUitl1.setText("创建时间：");
                ((ViewHolder) holder).tvUitl2.setText("所需车型：");
                ((ViewHolder) holder).tvUitl3.setText("重量：");
                ((ViewHolder) holder).tvName.setText("" + dto.getCreateTime());
                ((ViewHolder) holder).tvWeight.setText("" + dto.getCarTypeName());
                if (dto.getWeightUnit() == 1) {
                    ((ViewHolder) holder).tvCreatTime.setText(dto.getPointWeight() + "吨");
                } else {
                    ((ViewHolder) holder).tvCreatTime.setText(dto.getPointWeight() + "方");
                }
            }
            ImageLoadUtil.displayImage(mContext, dto.getCompanyLogo(), ((ViewHolder) holder).imageView);
            if (onTabClickListener != null) {
                ((ViewHolder) holder).viewTab1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("tab1", position);
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
        }
        if (holder instanceof FooterHolder) {

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
        return mDatas.size() == 0 ? 0 : mDatas.size() + 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvWeight)
        TextView tvWeight;
        @BindView(R.id.tvCreatTime)
        TextView tvCreatTime;
        @BindView(R.id.tvAdress)
        TextView tvAdress;
        @BindView(R.id.viewTab1)
        TextView viewTab1;
        @BindView(R.id.tvAdressTo)
        TextView tvAdressTo;
        @BindView(R.id.tvUitl3)
        TextView tvUitl3;
        @BindView(R.id.tvUitl2)
        TextView tvUitl2;
        @BindView(R.id.tvUitl1)
        TextView tvUitl1;
        @BindView(R.id.from_company)
        TextView tvFromCompany;
        @BindView(R.id.to_company)
        TextView tvToCompany;
        @BindView(R.id.resTxt2Linear)
        TextView resTxt2Linear;
        @BindView(R.id.resTxt2Text)
        TextView resTxt2Text;

    }

    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


}
