package com.saimawzc.shipper.ui.sendcar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.send.RouteDto;
import com.saimawzc.shipper.presenter.order.sendcar.MapTravelPresenter;
import com.saimawzc.shipper.view.order.sendcar.MapTravelView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/****
 * 地图轨迹
 * **/
public class MapTravelFragment extends BaseFragment implements MapTravelView {


    private String id;
    private MapTravelPresenter presenter;
    @BindView(R.id.mapview)
    MapView mapView;
    private BaiduMap baiduMap;
    @OnClick({R.id.back})
    public void click(View view){
        switch (view.getId()){
            case R.id.back:
                context.finish();
                break;
        }
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_maptravel;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        id=getArguments().getString("id");
        baiduMap=mapView.getMap();
        presenter=new MapTravelPresenter(this,mContext);
        presenter.getData(id);
    }

    @Override
    public void initData() {

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                View view= LayoutInflater.from(mContext).inflate(R.layout.map_show,null);

                if((marker.getExtraInfo()!=null)){
                    TextView tvName=view.findViewById(R.id.tvName);
                    tvName.setText(marker.getExtraInfo().getString("name"));
                    LatLng ll = new LatLng(marker.getExtraInfo().getDouble("latute"),
                            marker.getExtraInfo().getDouble("lontute"));
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);
                    baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                    //构造InfoWindow
                    //point 描述的位置点
                    //-100 InfoWindow相对于point在y轴的偏移量
                    InfoWindow mInfoWindow = new InfoWindow(view, marker.getPosition(), -47);
                    //使InfoWindow生效
                    baiduMap.showInfoWindow(mInfoWindow);


                    return true;
                }else {
                    return  false;
                }
            }
        });
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                baiduMap.hideInfoWindow();
            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {

            }
        });



    }
    @Override
    public void getRolte(RouteDto dto) {

        if(dto!=null){

            if(dto.getLocationList()!=null){
                //聚焦到当前位置
                if(dto.getLocationList().size()>0){
                    String tempLocation=dto.getLocationList().get(0).getLocation();
                    String[] tempNode = tempLocation.split(",");
                    LatLng tempPoint = new LatLng(Double.parseDouble(tempNode[1]), Double.parseDouble(tempNode[0]));
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(tempPoint).zoom(18.0f);
                    baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                    ///描绘中间点
                    for(int i=0;i<dto.getLocationList().size();i++){
                        //定义Maker坐标点
                        String location=dto.getLocationList().get(i).getLocation();
                        String[] node = location.split(",");
                        LatLng point = new LatLng(Double.parseDouble(node[1]), Double.parseDouble(node[0]));
                        //构建Marker图标
                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(R.drawable.map_blue);

                        //构建MarkerOption，用于在地图上添加Marker
                        Bundle bundle=new Bundle();
                        bundle.putString("name",dto.getLocationList().get(i).getAddress());
                        bundle.putDouble("latute",point.latitude);
                        bundle.putDouble("lontute",point.longitude);
                        OverlayOptions option = new MarkerOptions()
                                .position(point)
                                .extraInfo(bundle)
                                .icon(bitmap);
                        //在地图上添加Marker，并显示
                        baiduMap.addOverlay(option);

                    }
                }
            }

            List<LatLng> points = new ArrayList<LatLng>();
            if(dto.getSteps()==null){
                context.showMessage("未获取到位置信息");
            }
            for(int i=0;i<dto.getSteps().size();i++){
                RouteDto.steps tempData=dto.getSteps().get(i);
                ///////////
                String nodeStart=tempData.getStartLocation();
                String[] nodeStartarr = nodeStart.split(",");
                LatLng p2 = new LatLng(Double.parseDouble(nodeStartarr[1]), Double.parseDouble(nodeStartarr[0]));
                points.add(p2);

                //////////////////
                String nodeEnd=tempData.getEndLocation();
                String[] nodEndarr = nodeEnd.split(",");

                LatLng p3 = new LatLng(Double.parseDouble(nodEndarr[1]), Double.parseDouble(nodEndarr[0]));
                points.add(p3);
            }


            OverlayOptions mOverlayOptions = new PolylineOptions()
                    .width(15)
                    .color(0xAAFF0000)
                    .points(points);
            Overlay mPolyline = baiduMap.addOverlay(mOverlayOptions);
            //baiduMap.ov
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(baiduMap!=null){
            baiduMap.clear();
        }
        if(mapView!=null){
            mapView.onDestroy();
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


}
