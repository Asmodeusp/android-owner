package com.saimawzc.shipper.adapter.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.dto.order.send.WarnInfoDto;
import com.saimawzc.shipper.weight.CircleImageView;
import com.saimawzc.shipper.weight.WrapContentLinearLayoutManager;

import java.util.List;

/**
 * creator :  tlp
 * time    :  2016/5/4.
 * 物流规划
 * content :
 */
public class WarnInfoViewPageAdatper extends PagerAdapter {

    private Context mcontext;

    private List<WarnInfoDto> murls;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public WarnInfoViewPageAdatper(Context context, List<WarnInfoDto> urls) {
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
                inflate(R.layout.item_logistica_viewpage, null);

        RecyclerView cv=mitemView.findViewById(R.id.cv);
        TextView tvDanHao = (TextView) mitemView.findViewById(R.id.tvDanHao);
        TextView tvName = (TextView) mitemView.findViewById(R.id.tvName);
        TextView tvPhone = (TextView) mitemView.findViewById(R.id.tvPhone);
        TextView carNo = (TextView) mitemView.findViewById(R.id.carNo);
        CircleImageView imgHead=(CircleImageView)mitemView.findViewById(R.id.imgHead);
        WarnInfoDto dto=murls.get(position);

        if(dto!=null){
            tvDanHao.setText(dto.getWayBillNo());
            tvName.setText(dto.getSjName());
            carNo.setText(dto.getCarNo());
            WrapContentLinearLayoutManager layoutManager=new WrapContentLinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false);
            layoutManager=new WrapContentLinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false);
            WarnAdpater adpater=new WarnAdpater(dto.getTransportLogList(),mcontext);;
            cv.setLayoutManager(layoutManager);
           cv.setAdapter(adpater);
        }




        container.addView(mitemView);
        return mitemView;

    }


    // 声明监听器
    private OnItemClickListener onItemClickListener;

    // 提供设置监听器的公共方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
}
