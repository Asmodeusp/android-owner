package com.saimawzc.shipper.ui.order.creatorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.order.creatorder.ContarctsDto;
import com.saimawzc.shipper.dto.order.creatorder.GoodsCompanyDto;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.weight.utils.AreaChooseDialog;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.AreaListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import static android.app.Activity.RESULT_OK;
import static com.saimawzc.shipper.constants.AppConfig.reshWayBillAdd;
import static com.saimawzc.shipper.ui.my.identification.CargoOwnerFragment.CHOOSE_COMPANY;
import static com.saimawzc.shipper.ui.order.OrderBasicInfoFragment.CHOOSE_GOODSCOMPANY;
/**
 * 新增地址
 * **/
public class AddAdressFragment extends BaseFragment {
    private String type;//1发货地址 2收货地址
    @BindView(R.id.tvContract)
    TextView tvContract;
    @BindView(R.id.tvarea)TextView tvArea;
    @BindView(R.id.eddelationadress)
    TextView edDelationAdress;
    @BindView(R.id.edemail)EditText edEmail;//邮箱
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.right_btn)TextView tvRightBtn;
    @BindView(R.id.tvUntil)TextView tvUntil;
    private AreaChooseDialog areaChooseDialog;
    private String proName;
    private String proId;
    private String citysName;
    private String citysId;
    private String countrysName;
    private String countrysId;
    private double latitude;
    private double longitude;
    @Override
    public int initContentView() {
        return R.layout.fragment_addadress;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        utilDto= (GoodsCompanyDto) getArguments().getSerializable("data");
        if(areaChooseDialog==null){
            areaChooseDialog =new AreaChooseDialog(mContext);
            areaChooseDialog.initData();
        }
        type=getArguments().getString("type");
        if(type.equals("1")){//新增发货地址
            context.setToolbar(toolbar,"新增发货地址");
        }else if(type.equals("2")){
            context.setToolbar(toolbar,"新增收货地址");
        }

    }
    private int CHOOSE_CONTRACTS=104;
    private int CHOOSE_DelationCONTRACTS=105;
    @OnClick({R.id.rlContarct,R.id.tvarea,
            R.id.btnOrder,R.id.rl_area,R.id.rlUtil,R.id.rldelationadress})
    public void click(View view){
        Bundle bundle=null;
        switch (view.getId()){
            case R.id.rldelationadress:
                if(context.isEmptyStr(tvArea)){
                    context.showMessage("请选择地址");
                    return;
                }
                bundle=new Bundle();
                bundle.putString("area",countrysName);
                bundle.putString("city",citysName);
                readyGoForResult(DelationAdressMapActivity.class,CHOOSE_DelationCONTRACTS,bundle);
                break;
            case R.id.rlUtil://选择单位
                bundle=new Bundle();
                bundle.putString("type",type+"");
                bundle.putString("from","choosegoodscompany");
                readyGoForResult(OrderMainActivity.class,CHOOSE_GOODSCOMPANY,bundle);
                break;
            case R.id.rlContarct:
                bundle=new Bundle();
                bundle.putString("from","choosecontracts");
                readyGoForResult(OrderMainActivity.class,CHOOSE_CONTRACTS,bundle);
                break;
            case R.id.rl_area:
                areaChooseDialog.show(new AreaListener() {
                    @Override
                    public void getArea(String area, String proviceName, String cityName, String countryName, String proivceid, String cityId, String countryId) {
                        tvArea.setText(area);
                        proName=proviceName;
                        proId=proivceid;
                        citysName=cityName;
                        citysId=cityId;
                        countrysName=countryName;
                        countrysId=countryId;
                    }
                });
                break;
            case R.id.btnOrder:
                if(context.isEmptyStr(tvContract)){
                    context.showMessage("请选择联系人");
                    return;
                }
                if(context.isEmptyStr(tvArea)){
                    context.showMessage("请选择地址");
                    return;
                }
                if(context.isEmptyStr(edDelationAdress)){
                    context.showMessage("请输入详细地址");
                    return;
                }
                if(latitude==0){
                    context.showMessage("未获取到经纬度信息");
                    return;
                }
                if(utilDto==null){
                    context.showMessage("请选择单位");
                    return;
                }
                addAdress();

                break;
        }


    }

    ContarctsDto contarctsDto;
    GoodsCompanyDto utilDto;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CHOOSE_CONTRACTS&& resultCode == RESULT_OK){
            contarctsDto = (ContarctsDto) data.getSerializableExtra("data");
            if(contarctsDto!=null){
                tvContract.setText(contarctsDto.getName());
            }
        }
        if(requestCode==CHOOSE_GOODSCOMPANY&& resultCode == RESULT_OK){
            utilDto = (GoodsCompanyDto) data.getSerializableExtra("data");
            if(utilDto!=null){
                tvUntil.setText(utilDto.getName());
            }
        }
        if(requestCode==CHOOSE_DelationCONTRACTS&& resultCode == RESULT_OK){
           Bundle bundle= data.getExtras();
            if(bundle!=null){
                edDelationAdress.setText(bundle.getString("name"));
                latitude=bundle.getDouble("latitude");
                longitude=bundle.getDouble("longitude");
            }
        }
    }
    @Override
    public void initData() {
    }


    private void addAdress(){
        context.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("allAddress",edDelationAdress.getText().toString());
            jsonObject.put("addressType",type);
            jsonObject.put("proName",proName);
            jsonObject.put("unitId",utilDto.getId());
            jsonObject.put("proId",proId);
            jsonObject.put("location",longitude+","+latitude);
            jsonObject.put("contactsId",contarctsDto.getId());
            jsonObject.put("cityName",citysName);
            jsonObject.put("cityId",citysId);
            jsonObject.put("areaName",countrysName);
            jsonObject.put("areaId",countrysId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        context.orderApi.addAdress(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                context.finish();
                Intent intent = new Intent();
                intent.setAction(reshWayBillAdd);
                context.sendBroadcast(intent);
                context.dismissLoadingDialog();
            }

            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
                context.dismissLoadingDialog();

            }
        });

    }
}
