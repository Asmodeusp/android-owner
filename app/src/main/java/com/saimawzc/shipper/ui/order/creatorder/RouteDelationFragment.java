package com.saimawzc.shipper.ui.order.creatorder;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
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
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.creatorder.MapDelationDto;
import com.saimawzc.shipper.presenter.order.MapDelationPresenter;
import com.saimawzc.shipper.view.order.route.MapDealtionView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RouteDelationFragment extends BaseFragment implements MapDealtionView {
    @BindView(R.id.Mapview) MapView mapView;
    private BaiduMap baiduMap;
    private MapDelationPresenter presenter;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    public int initContentView() {
        return R.layout.fragment_routedelation;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"路线详情");
        baiduMap=mapView.getMap();
        presenter=new MapDelationPresenter(this,mContext);
        presenter.getMapDelation(getArguments().getString("id"));
    }

    @Override
    public void initData() {

    }

    @Override
    public void getMapDelation(MapDelationDto dto) {
        if(dto!=null){
            List<LatLng> allPointList = new ArrayList<LatLng>();
            for(int i=0;i<dto.getPath().length;i++){
                String[] lang = dto.getPath()[i].split(",");
                LatLng center = new LatLng(Double.parseDouble(lang[1]), Double.parseDouble(lang[0]));
                allPointList.add(center);
            }
            if(allPointList.size()>=2){
                OverlayOptions mOverlayOptions1 = new PolylineOptions()
                        .width(10)
                        .color(Color.RED)
                        .points(allPointList);
                Overlay overbeidou = baiduMap.addOverlay(mOverlayOptions1);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(allPointList.get(0)).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
            if(!TextUtils.isEmpty(dto.getFromLocation())){
                String[] statrNode = dto.getFromLocation().split(",");
                LatLng startPoint = new LatLng(Double.parseDouble(statrNode[0]), Double.parseDouble(statrNode[1]));
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.ico_map_start);
                OverlayOptions option = new MarkerOptions()
                        .position(startPoint)
                        .icon(bitmap);
                baiduMap.addOverlay(option);
            }
            if(!TextUtils.isEmpty(dto.getToLocation())){
                String[] endNode = dto.getToLocation().split(",");
                LatLng endPoint = new LatLng(Double.parseDouble(endNode[0]), Double.parseDouble(endNode[1]));
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_end);
                //构 建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(endPoint)
                        .icon(bitmap);
                //在                   地图上添加Marker，并显示
                baiduMap.addOverlay(option);
            }
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
        context.showMessage(str);

    }

    @Override
    public void oncomplete() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        baiduMap.clear();
        baiduMap=null;
    }
}
