package com.saimawzc.shipper.ui.order.creatorder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.weight.ClearTextEditText;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;

/***
 * 地图选择详细地址
 * **/
public class DelationAdressMapActivity extends BaseActivity
        implements OnGetDistricSearchResultListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.mapView) MapView mMapView;
    private DistrictSearch mDistrictSearch;
    private BaiduMap mBaiduMap;
    private String area;
    private String city;
    @BindView(R.id.searchkey) AutoCompleteTextView mKeyWordsView;
    @BindView(R.id.sug_list) ListView mSugListView;
    private SuggestionSearch  mSuggestionSearch ;
    List<HashMap<String, String>> suggest = new ArrayList<>();
    SimpleAdapter simpleAdapter;

    @Override
    protected int getViewId() {
        return R.layout.activity_mapdaress;
    }

    @Override
    protected void init() {
        setToolbar(toolbar,"详细地址");
        mBaiduMap=mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        area=getIntent().getStringExtra("area");
        city=getIntent().getStringExtra("city");
        Log.e("msg","选择"+area+"城市"+city);
        //显示城市轮廓
        mDistrictSearch = DistrictSearch.newInstance();
        mDistrictSearch.setOnDistrictSearchListener(this);
        mDistrictSearch.searchDistrict(new DistrictSearchOption().cityName(area));

        mSuggestionSearch=SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);
        // 当输入关键字变化时，动态更新建议列表
        mKeyWordsView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (TextUtils.isEmpty(cs.toString())) {
                    mSugListView.setVisibility(View.GONE);
                    return;
                }
                mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                        .city(city)
                        .citylimit(true)
                        .keyword(cs.toString()));
            }
        });
    }
    @Override
    protected void initListener() {
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //showInfoWindow("",latLng);
            }
            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
                showInfoWindow(mapPoi.getName(),mapPoi.getPosition());
            }
        });
        mSugListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(suggest.size()<=position){
                    return;
                }
                if(!TextUtils.isEmpty(suggest.get(position).get("latitude"))){
                    double latuye=Double.parseDouble(suggest.get(position).get("latitude"));
                    double longtute=Double.parseDouble(suggest.get(position).get("longitude"));
                    LatLng latLng=new LatLng(latuye,longtute);
                    Log.e("msg",suggest.get(position).get("key")+latuye+"``"+longtute);
                    mSugListView.setVisibility(View.GONE);
                    showInfoWindow(suggest.get(position).get("key"),latLng);
                }else {
                    context.showMessage("获取位置有误");
                }


            }
        });

    }

    @Override
    protected void onGetBundle(Bundle bundle) {
    }

    @Override
    public void onGetDistrictResult(DistrictResult districtResult) {
        if (districtResult.error == SearchResult.ERRORNO.NO_ERROR){
            List<List<LatLng>> polyLines = districtResult.getPolylines();
            if (polyLines == null){
                return;
            }
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (List<LatLng> polyline : polyLines){
                PolylineOptions ooPolyline11 = new PolylineOptions().width(4)
                        .points(polyline).dottedLine(true).color(Color.RED);
                mBaiduMap.addOverlay(ooPolyline11);
                for (LatLng latLng : polyline){
                    builder.include(latLng);
                }
            }
            mBaiduMap.setMapStatus(MapStatusUpdateFactory
                    .newLatLngBounds(builder.build()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDistrictSearch.destroy();
        mSuggestionSearch.destroy();
        mBaiduMap.clear();
        mBaiduMap=null;
        mMapView=null;

    }

    OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {
            //处理sug检索结果
            if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
                return;
            }
            suggest.clear();
            mSugListView.setVisibility(View.VISIBLE);
            for (SuggestionResult.SuggestionInfo info : suggestionResult.getAllSuggestions()) {
                if (info.getKey() != null && info.getDistrict() != null && info.getCity() != null) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("key",info.getKey());
                    map.put("city",info.getCity());
                    map.put("dis",info.getDistrict());
                    if(info.getPt()!=null){
                        map.put("latitude",info.getPt().latitude+"");
                        map.put("longitude",info.getPt().longitude+"");
                    }
                    suggest.add(map);
                }
            }
            if(simpleAdapter==null){
                simpleAdapter= new SimpleAdapter(getApplicationContext(),
                        suggest,
                        R.layout.item_autoview,
                        new String[]{"key", "city","dis"},
                        new int[]{R.id.sug_key, R.id.sug_city, R.id.sug_dis});
            }
            mSugListView.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
        }
    };

    private void showInfoWindow(String name, LatLng location){
        View view= LayoutInflater.from(DelationAdressMapActivity.this).
                inflate(R.layout.map_show,null);
        TextView tvName=view.findViewById(R.id.tvName);
        TextView tvLocation=view.findViewById(R.id.tvlocation);
        tvName.setText(name);
        tvLocation.setText("经度"+location.longitude+"纬度："+location.latitude);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(location).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        //构造InfoWindow
        //point 描述的位置点
        //-100 InfoWindow相对于point在y轴的偏移量
        InfoWindow mInfoWindow = new InfoWindow(view, location, -47);
        //使InfoWindow生效
        mBaiduMap.showInfoWindow(mInfoWindow);
        final Bundle  bundle=new Bundle();
        bundle.putDouble("latitude",location.latitude);
        bundle.putDouble("longitude",location.longitude);
        bundle.putString("name",name);
        ImageView imgDaoahng=view.findViewById(R.id.imgDaoahng);
        imgDaoahng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
}
