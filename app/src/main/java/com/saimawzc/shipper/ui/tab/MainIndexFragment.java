package com.saimawzc.shipper.ui.tab;


import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.main.MainIndexAdpater;
import com.saimawzc.shipper.adapter.main.SpaceItemDecoration;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.main.MainIndexDto;
import com.saimawzc.shipper.dto.pic.AdListDto;
import com.saimawzc.shipper.presenter.mine.BannerPresenter;
import com.saimawzc.shipper.ui.CommonActivity;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.view.mine.BannerView;
import com.saimawzc.shipper.weight.utils.MyLoader;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/7/31.
 * 首页
 */

public class MainIndexFragment extends BaseFragment
        implements OnBannerListener, BannerView {

    @BindView(R.id.babner) Banner banner;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    @BindView(R.id.rv) RecyclerView rv;
    private MainIndexAdpater adpater;
    private List<MainIndexDto>mDatas=new ArrayList<>();
    private String arr[]={"对账管理","结算管理","发票管理","扣款管理","运单管理","预警信息"
    ,"公司报表","物料报表"};

    private BannerPresenter presenter;
    @BindView(R.id.rlmessage)
    LinearLayout lMessage;

    @Override
    public int initContentView() {
        return R.layout.fragment_mainindex;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        presenter=new BannerPresenter(this,mContext);
        presenter.getBanner();
        for(int i=0;i<arr.length;i++){
            MainIndexDto dto=new MainIndexDto();
            dto.setName(arr[i]);
            mDatas.add(dto);
        }
        adpater=new MainIndexAdpater(mDatas,mContext);
        rv.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv.setAdapter(adpater);
        rv.addItemDecoration(new SpaceItemDecoration(22));
    }

    @Override
    public void initData() {
        adpater.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle=null;
                switch (position){
                    case 0:
                        bundle=new Bundle();
                        bundle.putString("from","accountmanage");
                        readyGo(OrderMainActivity.class,bundle);
                        break;
                    case 1://计算管理
                        bundle=new Bundle();
                        bundle.putString("from","mysettelment");
                        readyGo(OrderMainActivity.class,bundle);
                        break;
                    case 2:
                        bundle=new Bundle();
                        bundle.putString("title","发票管理");
                        readyGo(CommonActivity.class,bundle);
                        break;
                    case 3:
                        bundle=new Bundle();
                        bundle.putString("title","扣款管理");
                        readyGo(CommonActivity.class,bundle);
                        break;
                    case 4:
                        bundle=new Bundle();
                        bundle.putString("title","运单管理");
                        readyGo(CommonActivity.class,bundle);
                        break;
                    case 5:
                        bundle=new Bundle();
                        bundle.putString("title","预警信息");
                        readyGo(CommonActivity.class,bundle);
                        break;
                    case 6:
                        bundle=new Bundle();
                        bundle.putString("title","公司报表");
                        readyGo(CommonActivity.class,bundle);
                        break;
                    case 7:
                        bundle=new Bundle();
                        bundle.putString("title","物料报表");
                        readyGo(CommonActivity.class,bundle);
                        break;

                }
            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        lMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("title","消息通知");
                readyGo(CommonActivity.class,bundle);
            }
        });
    }

    @Override
    public void OnBannerClick(int position) {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void getBanner(List<AdListDto> dtos) {
        if(dtos!=null){
            if(list_path==null){
                list_path = new ArrayList<>();
            }
            if(list_title==null){
                list_title = new ArrayList<>();
            }
            list_path.clear();
            list_title.clear();

            for(int i=0;i<dtos.size();i++){
                list_path.add(dtos.get(i).getImgSrc());
                list_title.add("");
            }

            //设置内置样式，共有六种可以点入方法内逐一体验使用。
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            //设置图片加载器，图片加载器在下方
            banner.setImageLoader(new MyLoader());
            //设置图片网址或地址的集合
            banner.setImages(list_path);
            //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
            banner.setBannerAnimation(Transformer.Default);
            //设置轮播图的标题集合
            banner.setBannerTitles(list_title);
            //设置轮播间隔时间
            banner.setDelayTime(3000);
            //设置是否为自动轮播，默认是“是”。
            banner.isAutoPlay(true);
            //设置圆角
            banner.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 6);
                }
            });
            banner.setClipToOutline(true);
            //设置指示器的位置，小点点，左中右。
            banner.setIndicatorGravity(BannerConfig.CENTER)
                    //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                    .setOnBannerListener(this)
                    //必须最后调用的方法，启动轮播图。
                    .start();

            banner.setFocusable(true);
            banner.setFocusableInTouchMode(true);

            banner.requestFocus();
        }
    }

    @Override
    public void showLoading() {
        context.showLoadingDialog();

    }

    @Override
    public void dissLoading() {
       context.dismissLoadingDialog();
    }

    @Override
    public void Toast(String str) {
        if(TextUtils.isEmpty(PreferenceKey.HZ_IS_RZ)||!Hawk.get(PreferenceKey.HZ_IS_RZ,"").equals("1")){
            if(!str.contains("权限")){
                context.showMessage(str);
            }
        }else {
            context.showMessage(str);
        }
    }

    @Override
    public void oncomplete() {

    }

}
