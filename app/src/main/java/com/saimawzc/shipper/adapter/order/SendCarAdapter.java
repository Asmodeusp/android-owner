package com.saimawzc.shipper.adapter.order;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
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

public class SendCarAdapter extends BaseAdapter {

    private List<SendCarDto.SendCarData> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private String type=1+"";

    public OnTabClickListener onTabClickListener;

    public void setOnTabClickListener(OnTabClickListener onItemTabClickListener) {
        this.onTabClickListener = onItemTabClickListener;
    }
    public interface OnTabClickListener {
        void onItemClick(String type, int position);
    }

    public SendCarAdapter(List<SendCarDto.SendCarData> mDatas, Context mContext, String type) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
        this.type=type;
        permissionChecker=new PermissionChecker(activity);
    }

    public void setData(List<SendCarDto.SendCarData> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<SendCarDto.SendCarData> getData() {
        return mDatas;
    }
    public void addMoreData(List<SendCarDto.SendCarData> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_sendcar, parent,
                    false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterHolder(mInflater.inflate(R.layout.layout_footer, parent, false));
        }
        return null;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if(holder instanceof ViewHolder){
            SendCarDto.SendCarData dto=mDatas.get(position);
            if (TextUtils.isEmpty(dto.getResTxt2())) {
                ((ViewHolder) holder).resTxt2Linear.setVisibility(View.GONE);
            }else {
                ((ViewHolder) holder).resTxt2Linear.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).resTxt2Text.setText(dto.getResTxt2());
            }
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


            if(type.equals("4")){
                ((ViewHolder) holder).ico_more.setVisibility(View.VISIBLE);
            }else {
                ((ViewHolder) holder).ico_more.setVisibility(View.GONE);
            }

            if(type.equals("1")){//已派车
                ((ViewHolder) holder).viewTab1.setText("终止运单");
                ((ViewHolder) holder).viewTab2.setText("暂停运输");
                ((ViewHolder) holder).viewTab3.setText("当前位置");
                if(dto.getStatus()==10){//已经暂停
                    ((ViewHolder) holder).viewTab2.setText("恢复运输");
                }
                ((ViewHolder) holder).viewTab1.setVisibility(View.GONE);
                ((ViewHolder) holder).viewTab2.setVisibility(View.GONE);
                ((ViewHolder) holder).viewTab3.setVisibility(View.VISIBLE);

            }else if(type.equals("2")){//运输中
                ((ViewHolder) holder).viewTab1.setText("物流信息");
                ((ViewHolder) holder).viewTab2.setText("当前位置");
                ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
            }else if(type.equals("3")){//已经完成
                if (dto.getOutFactoryWeight()!=null&&dto.getSjSignInWeight()!=null) {
                    if (dto.getOutFactoryWeight()==0.0&&dto.getSjSignInWeight()==0.0) {
                        if (dto.getHzSignInWeight()!=null&&dto.getTransportStatus()!=null) {
                            if (dto.getHzSignInWeight()==0.0&&dto.getTransportStatus()==8) {
                                ((ViewHolder) holder).viewTab2.setText("签收量存疑");
                                ((ViewHolder) holder).viewTab2.setTextColor(mContext.getResources().getColor(R.color.white));
                                ((ViewHolder) holder).viewTab2.setBackground(mContext.getResources().getDrawable(R.drawable.shape_list_btn_org));
                            }else {
                                ((ViewHolder) holder).viewTab2.setText("确认签收");
                                ((ViewHolder) holder).viewTab2.setTextColor(mContext.getResources().getColor(R.color.gray333));
                                ((ViewHolder) holder).viewTab2.setBackground(mContext.getResources().getDrawable(R.drawable.shape_list_btn));
                            }
                        }else {
                            ((ViewHolder) holder).viewTab2.setText("确认签收");
                            ((ViewHolder) holder).viewTab2.setTextColor(mContext.getResources().getColor(R.color.gray333));
                            ((ViewHolder) holder).viewTab2.setBackground(mContext.getResources().getDrawable(R.drawable.shape_list_btn));
                        }
                    }else {
                        ((ViewHolder) holder).viewTab2.setText("确认签收");
                        ((ViewHolder) holder).viewTab2.setTextColor(mContext.getResources().getColor(R.color.gray333));
                        ((ViewHolder) holder).viewTab2.setBackground(mContext.getResources().getDrawable(R.drawable.shape_list_btn));
                    }
                }else {
                    ((ViewHolder) holder).viewTab2.setText("确认签收");
                    ((ViewHolder) holder).viewTab2.setTextColor(mContext.getResources().getColor(R.color.gray333));
                    ((ViewHolder) holder).viewTab2.setBackground(mContext.getResources().getDrawable(R.drawable.shape_list_btn));
                }

                ((ViewHolder) holder).viewTab1.setText("物流信息");
                ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
            }else if(type.equals("4")){//已关闭
                ((ViewHolder) holder).viewTab1.setText("地图轨迹");
                ((ViewHolder) holder).viewTab2.setText("物流信息");
                ((ViewHolder) holder).viewTab3.setText("运输评价");
                ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
            }

            ((ViewHolder) holder).imagePhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(TextUtils.isEmpty(mDatas.get(position).getCysPhone())){
                        activity.showMessage("未获取到承运商电话");
                        return;
                    }
                    String[] PERMISSIONS = new String[]{
                            Manifest.permission.CALL_PHONE
                    };
                    if(permissionChecker.isLackPermissions(PERMISSIONS)){
                        permissionChecker.requestPermissions();
                        activity.showMessage("未获取到电话权限");
                    }else {
                        activity.callPhone(mDatas.get(position).getCysPhone());
                    }
                }
            });

            ((ViewHolder) holder).ico_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final PopupWindowUtil popupWindowUtil = new PopupWindowUtil.Builder()
                            .setContext(mContext.getApplicationContext()) //设置 context
                            .setContentView(R.layout.dialog_sendcar) //设置布局文件
                            .setOutSideCancel(true) //设置点击外部取消
                            .setwidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .setFouse(true)
                            .builder()
                            .showAsLaction(((ViewHolder) holder).ico_more, Gravity.LEFT,0,0);

                    popupWindowUtil.setOnClickListener(new int[]{R.id.rlyichang, R.id.rlchangecar,
                            R.id.paichedelation}, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle;
                            switch (v.getId()){
                                case R.id.rlyichang://异常
                                    popupWindowUtil.dismiss();
                                    break;
                                case R.id.rlchangecar://换车信息
                                    bundle=new Bundle();
                                    bundle.putString("from","changeinfo");
                                    bundle.putString("id",mDatas.get(position).getId());
                                    activity.readyGo(OrderMainActivity.class,bundle);
                                    popupWindowUtil.dismiss();
                                    break;
                                case R.id.paichedelation://派车详情
                                    bundle=new Bundle();
                                    bundle.putString("from","paichedelation");
                                    bundle.putString("id",mDatas.get(position).getId());
                                    activity.readyGo(OrderMainActivity.class,bundle);
                                    popupWindowUtil.dismiss();
                                    break;


                            }
                        }
                    });

                }
            });




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
        @BindView(R.id.resTxt2Linear)LinearLayout resTxt2Linear;
        @BindView(R.id.resTxt2Text)TextView resTxt2Text;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


}
