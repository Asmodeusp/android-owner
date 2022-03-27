package com.saimawzc.shipper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.dto.order.manage.OrderManageRoleDto;

import java.util.List;

/**
 * creator :  tlp
 * time    :  2016/5/4.
 * 广告
 * content :
 */
public class AdvertisementAdatper extends PagerAdapter {

    private Context mcontext;
    //kk

    private List<OrderManageRoleDto.mapData> murls;

    public AdvertisementAdatper(Context context, List<OrderManageRoleDto.mapData> urls) {
        mcontext = context;
        murls = urls;
    }

    @Override
    public int getCount() {
        if (murls == null) return 0;
        return murls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LinearLayout mitemView = (LinearLayout) LayoutInflater.from(mcontext).
                inflate(R.layout.item_role, null);
        TextView tvName = (TextView) mitemView.findViewById(R.id.tvName);
        TextView tvId = (TextView) mitemView.findViewById(R.id.tvId);
        final TextView tvRole = (TextView) mitemView.findViewById(R.id.tvRole);
        TextView tvSendAdress = (TextView) mitemView.findViewById(R.id.tvSendAdress);
        TextView tvReceiveAdress = (TextView) mitemView.findViewById(R.id.tvReceiveAdress);
        OrderManageRoleDto.mapData dto=murls.get(position);
        tvName.setText(dto.getMaterialsName());
        tvId.setText(dto.getWayBillNo());
        tvSendAdress.setText(dto.getFromUserAddress());
        tvReceiveAdress.setText(dto.getToUserAddress());
        container.addView(mitemView);
        if (onItemClickListener != null) {
            tvRole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(tvRole, position);
                }
            });


        }

        return mitemView;

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    // 声明监听器
    private OnItemClickListener onItemClickListener;

    // 提供设置监听器的公共方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
}
