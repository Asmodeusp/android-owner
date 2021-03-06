package com.saimawzc.shipper.ui.order.manage;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.AdvertisementAdatper;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.order.manage.OrderManageRoleDto;
import com.saimawzc.shipper.presenter.order.ordermange.OrderManageMapPresenter;
import com.saimawzc.shipper.view.order.manage.OrderManageMapView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderManageMapActivity extends BaseActivity
        implements OrderManageMapView {

    @BindView(R.id.mapview) MapView mapView;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.ll_dot) LinearLayout llDot;
    private List<OrderManageRoleDto.mapData>mdatas=new ArrayList<>();
    private int dotSize = 12;
    private int dotSpace = 12;
    private Animator animatorToLarge;
    private Animator animatorToSmall;
    private BaiduMap mBaiduMap;
    private AdvertisementAdatper adatper;
    private SparseBooleanArray isLarge;
    private String id;
    private OrderManageMapPresenter presenter;
    @OnClick({R.id.back})
    public void click(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_manage;
    }

    @Override
    protected void init() {
        mBaiduMap=mapView.getMap();
        animatorToLarge = AnimatorInflater.loadAnimator(OrderManageMapActivity.this, R.animator.scale_to_large);
        animatorToSmall = AnimatorInflater.loadAnimator(OrderManageMapActivity.this, R.animator.scale_to_small);
        adatper = new AdvertisementAdatper(this, mdatas);
        viewPager.setAdapter(adatper);


    }
    /***
     * ????????????????????????
     * ***/
    Overlay mPolyline;
    @Override
    protected void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(final int position) {
                // ???????????????View???????????????????????????
                for (int i = 0; i < llDot.getChildCount(); i++) {
                    if (i == position ) {// ?????????
                        llDot.getChildAt(i).setBackgroundResource(R.drawable.dot_selected);
                        if (!isLarge.get(i)) {
                            animatorToLarge.setTarget(llDot.getChildAt(i));
                            animatorToLarge.start();
                            isLarge.put(i, true);
                        }
                    } else {// ????????????
                        llDot.getChildAt(i).setBackgroundResource(R.drawable.dot_unselected);
                        if (isLarge.get(i)) {
                            animatorToSmall.setTarget(llDot.getChildAt(i));
                            animatorToSmall.start();
                            isLarge.put(i, false);
                        }
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adatper.setOnItemClickListener(new AdvertisementAdatper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mdatas.size()<=position){
                    return;
                }

                if(mdatas.get(position).getLocations()!=null){
                    Log.e("msg","???????????????");
                    if(mPolyline!=null){
                        mBaiduMap.clear();
                        if(!TextUtils.isEmpty(mdatas.get(position).getToLocation())){
                            String[] statrNode = mdatas.get(position).getToLocation().split(",");
                            LatLng startPoint = new LatLng(Double.parseDouble(statrNode[1]), Double.parseDouble(statrNode[0]));
                            BitmapDescriptor bitmap = BitmapDescriptorFactory
                                    .fromResource(R.drawable.icon_end);
                            OverlayOptions option = new MarkerOptions()
                                    .position(startPoint)
                                    .icon(bitmap);
                            mBaiduMap.addOverlay(option);
                        }
                        if(!TextUtils.isEmpty(mdatas.get(position).getFromLocation())){
                            String[] endNode = mdatas.get(position).getFromLocation().split(",");
                            LatLng endPoint = new LatLng(Double.parseDouble(endNode[1]), Double.parseDouble(endNode[0]));
                            BitmapDescriptor bitmap = BitmapDescriptorFactory
                                    .fromResource(R.drawable.ico_map_start);
                            //??? ???MarkerOption???????????????????????????Marker
                            OverlayOptions option = new MarkerOptions()
                                    .position(endPoint)
                                    .icon(bitmap);
                            //???                   ???????????????Marker????????????
                            mBaiduMap.addOverlay(option);
                        }
                    }
                    if(allPointList.size()>=2){
                        OverlayOptions mOverlayOptions1 = new PolylineOptions()
                                .width(20)
                                .color(Color.RED)
                                .points(allPointList);

                        Overlay mPolyline1 = mBaiduMap.addOverlay(mOverlayOptions1);

                        List<LatLng> points = new ArrayList<LatLng>();
                        for(int i=0;i<mdatas.get(position).getLocations().size();i++){
                            if(mdatas.get(position).getLocations().get(i).getLatitude()!=0&&mdatas.get(position).getLocations().get(i).getLongitude()!=0){
                                LatLng latLng=new LatLng( mdatas.get(position).getLocations().get(i).getLatitude(),mdatas.get(position).getLocations().get(i).getLongitude());
                                points.add(latLng);
                            }
                        }
                        if(points.size()>=2){
                            OverlayOptions mOverlayOptions = new PolylineOptions()
                                    .width(10)
                                    .color(Color.BLUE)
                                    .points(points);
                            mPolyline = mBaiduMap.addOverlay(mOverlayOptions);
                        }else {
                            context.showMessage("??????????????????");
                        }
                    }
                }

            }
        });
    }

    @Override
    protected void onGetBundle(Bundle bundle) {
        if(bundle!=null){
            id=bundle.getString("id");
            presenter=new OrderManageMapPresenter(this,mContext);
            presenter.getcarrive(id);
        }

    }
    private void setIndicator() {
        isLarge = new SparseBooleanArray();
        // ?????????????????????????????????????????????????????????????????????
        llDot.removeAllViews();
        for (int i = 0; i < mdatas.size(); i++) {
            View view = new View(OrderManageMapActivity.this);
            view.setBackgroundResource(R.drawable.dot_unselected);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dotSize, dotSize);
            layoutParams.leftMargin = dotSpace / 2;
            layoutParams.rightMargin = dotSpace / 2;
            layoutParams.topMargin = dotSpace / 2;
            layoutParams.bottomMargin = dotSpace / 2;
            llDot.addView(view, layoutParams);
            isLarge.put(i, false);
        }
        llDot.getChildAt(0).setBackgroundResource(R.drawable.dot_selected);
        animatorToLarge.setTarget(llDot.getChildAt(0));
        animatorToLarge.start();
        isLarge.put(0, true);
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
        context.showMessage(str);

    }

    @Override
    public void oncomplete() {

    }
    List<LatLng> allPointList = new ArrayList<LatLng>();
    @Override
    public void getList(OrderManageRoleDto dto) {
        if(dto!=null){
            mdatas.addAll(dto.getList());
            setIndicator();
            adatper.notifyDataSetChanged();

            if(!TextUtils.isEmpty(dto.getToLocation())){
                String[] tempNode = dto.getToLocation().split(",");
                LatLng tempPoint = new LatLng(Double.parseDouble(tempNode[1]), Double.parseDouble(tempNode[0]));
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(tempPoint).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
            if(!TextUtils.isEmpty(dto.getToLocation())){
                String[] statrNode = dto.getToLocation().split(",");
                LatLng startPoint = new LatLng(Double.parseDouble(statrNode[1]), Double.parseDouble(statrNode[0]));
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_end);
                OverlayOptions option = new MarkerOptions()
                        .position(startPoint)
                        .icon(bitmap);
                mBaiduMap.addOverlay(option);
            }
            if(!TextUtils.isEmpty(dto.getFromLocation())){
                String[] endNode = dto.getFromLocation().split(",");
                LatLng endPoint = new LatLng(Double.parseDouble(endNode[1]), Double.parseDouble(endNode[0]));
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.ico_map_start);
                //??? ???MarkerOption???????????????????????????Marker
                OverlayOptions option = new MarkerOptions()
                        .position(endPoint)
                        .icon(bitmap);
                //???                   ???????????????Marker????????????
                mBaiduMap.addOverlay(option);
            }


            if(dto.getLocations()!=null){
                Log.e("msg","??????????????????");
                List<LatLng> points = new ArrayList<LatLng>();
                for(int i=0;i<dto.getLocations().size();i++){
                    if(dto.getLocations().get(i).getLatitude()!=0&&dto.getLocations().get(i).getLongitude()!=0){
                        LatLng latLng=new LatLng( dto.getLocations().get(i).getLatitude(),dto.getLocations().get(i).getLongitude());
                        points.add(latLng);
                    }
                }
                allPointList=points;
                OverlayOptions mOverlayOptions = new PolylineOptions()
                        .width(20)
                        .color(Color.RED)
                        .points(points);
                Overlay mPolyline1 = mBaiduMap.addOverlay(mOverlayOptions);
            }
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBaiduMap!=null){
            mBaiduMap.clear();
        }
        if(mapView!=null){
            mapView.onDestroy();
        }
    }
}
