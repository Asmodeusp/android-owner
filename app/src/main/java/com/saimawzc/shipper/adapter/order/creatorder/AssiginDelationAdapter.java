package com.saimawzc.shipper.adapter.order.creatorder;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.FooterHolder;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;
import com.saimawzc.shipper.weight.utils.api.OrderApi;
import com.saimawzc.shipper.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.shipper.weight.utils.dialog.NormalDialog;
import com.saimawzc.shipper.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.shipper.weight.utils.dialog.SlideBottomExit;
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
 * 指派二级页面
 */
public class AssiginDelationAdapter extends BaseAdapter {
    public OrderApi orderApi = Http.http.createApi(OrderApi.class);
    private List<AssignDelationDto.listdata> mDatas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private String type;

    public AssiginDelationAdapter(List<AssignDelationDto.listdata> mDatas,
                                  Context mContext, String type) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity = (BaseActivity) mContext;
        this.type = type;
    }

    public void setData(List<AssignDelationDto.listdata> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<AssignDelationDto.listdata> getData() {
        return mDatas;
    }

    public void addMoreData(List<AssignDelationDto.listdata> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_assgin_delation, parent,
                    false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterHolder(mInflater.inflate(R.layout.layout_footer, parent, false));
        }
        return null;
    }

    private NormalDialog dialog;

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,
                                 final int position) {
        if (holder instanceof ViewHolder) {
            final AssignDelationDto.listdata dto = mDatas.get(position);
            ((ViewHolder) holder).tvName.setText(dto.getCysName());
            ((ViewHolder) holder).tvPhone.setText(dto.getCysPhone());
            ((ViewHolder) holder).edTrangprice.setText(dto.getPointPrice());
            ((ViewHolder) holder).edtrantNum.setText(dto.getPointWeight());
            if (!TextUtils.isEmpty(dto.getOverWeight())) {
                ((ViewHolder) holder).surplusRelative.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).surplusNum.setText(dto.getOverWeight());
            } else {
                ((ViewHolder) holder).surplusRelative.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(dto.getEndOption())) {
                ((ViewHolder) holder).suggestLinear.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).suggestText.setText(dto.getEndOption());
            } else {
                ((ViewHolder) holder).suggestLinear.setVisibility(View.GONE);
            }


            if (dto.getEndStatus() == 3) {
                ((ViewHolder) holder).lineAgreen.setVisibility(View.VISIBLE);
            } else {
                ((ViewHolder) holder).lineAgreen.setVisibility(View.GONE);
            }

            if (dto.getStatus() == 1) {
                ((ViewHolder) holder).tvStatus.setText("待确认");
            } else if (dto.getStatus() == 2) {
                ((ViewHolder) holder).tvStatus.setText("已确认");
            } else if (dto.getStatus() == 3) {
                ((ViewHolder) holder).tvStatus.setText("已拒绝");
            }
            ((ViewHolder) holder).tvArgeen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog = new NormalDialog(mContext).isTitleShow(false)
                            .content("是否同意申请?")
                            .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                            .btnNum(2).btnText("取消", "确定");
                    dialog.setOnBtnClickL(
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    if (!activity.isFinishing()) {
                                        dialog.dismiss();
                                    }
                                }
                            },
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    sh(1, mDatas.get(position).getId(), type);
                                    if (!activity.isFinishing()) {
                                        dialog.dismiss();
                                    }
                                }
                            });
                    dialog.show();

                }
            });

            ((ViewHolder) holder).tvRefuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog = new NormalDialog(mContext).isTitleShow(false)
                            .content("是否拒绝申请?")
                            .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                            .btnNum(2).btnText("取消", "确定");
                    dialog.setOnBtnClickL(
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    if (!activity.isFinishing()) {
                                        dialog.dismiss();
                                    }
                                }
                            },
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    sh(2, mDatas.get(position).getId(), type);
                                    if (!activity.isFinishing()) {
                                        dialog.dismiss();
                                    }
                                }
                            });
                    dialog.show();

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

        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvphone)
        TextView tvPhone;
        @BindView(R.id.tvstatus)
        TextView tvStatus;
        @BindView(R.id.edtrantPrice)
        TextView edTrangprice;
        @BindView(R.id.edtrantNum)
        TextView edtrantNum;
        @BindView(R.id.llBtn)
        LinearLayout lineAgreen;
        @BindView(R.id.tvAgreen)
        TextView tvArgeen;
        @BindView(R.id.tvRefuse)
        TextView tvRefuse;
        @BindView(R.id.suggest_text)
        TextView suggestText;
        @BindView(R.id.surplusNum)
        TextView surplusNum;
        @BindView(R.id.suggestLinear)
        LinearLayout suggestLinear;
        @BindView(R.id.surplusRelative)
        RelativeLayout surplusRelative;
    }

    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


    private void sh(int status, String id, String type) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", status);
            jsonObject.put("id", id);
            jsonObject.put("type", type);//1是计划单 2是小单 3 合单
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg", jsonObject.toString());
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        orderApi.agreeCysapply(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                activity.finish();
            }

            @Override
            public void fail(String code, String message) {
                activity.showMessage(message);
            }
        });
    }

}
