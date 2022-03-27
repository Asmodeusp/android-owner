package com.saimawzc.shipper.ui.order.bidd;

import static android.app.Activity.RESULT_OK;
import static com.saimawzc.shipper.constants.AppConfig.reshPlanOrder;
import static com.saimawzc.shipper.constants.AppConfig.reshWaybIllOrder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.order.bidd.OrderBiddCarriveAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.dto.order.bidd.BiddTempDto;
import com.saimawzc.shipper.dto.order.bidd.CarTypeDo;
import com.saimawzc.shipper.dto.order.creatorder.OrderCarrierGroupDto;
import com.saimawzc.shipper.dto.order.manage.OrderManageDto;
import com.saimawzc.shipper.presenter.order.OrderBiddPresenter;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.view.order.OrderBiddView;
import com.saimawzc.shipper.weight.RepeatClickUtil;
import com.saimawzc.shipper.weight.SwitchButton;
import com.saimawzc.shipper.weight.TimeChooseDialogUtil;
import com.saimawzc.shipper.weight.utils.dialog.WheelDialog;
import com.saimawzc.shipper.weight.utils.listen.TimeChooseListener;
import com.saimawzc.shipper.weight.utils.listen.WheelListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/***
 * 竞价*/

public class OrderBiddingFragment extends BaseFragment
        implements OrderBiddView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String id;
    @BindView(R.id.tvtimeStart) TextView tvtimeStart;
    @BindView(R.id.tvtimeEnd)TextView tvtimeEnd;
    @BindView(R.id.edNum) EditText editNum;
    @BindView(R.id.tvcarriveGroupName)TextView tvcarriveGroupName;
    @BindView(R.id.cy)
    RecyclerView rv;
    private TimeChooseDialogUtil timeChooseDialogUtil;
    private int CHOOSE_CARRIVE=100;
    private OrderBiddCarriveAdapter adapter;
    private OrderBiddPresenter presenter;
    private List<OrderAssignmentSecondDto>mDatas=new ArrayList<>();
    private String type="";//是计划订单还是预运单 还是调度单
    private OrderManageDto.manageData data;
    @BindView(R.id.edweight)EditText edweight;
    @BindView(R.id.rljjweight) RelativeLayout rlBiddWeight;
    @BindView(R.id.group_bill) RadioGroup groupBill;
    @BindView(R.id.rlcarrivegroup)RelativeLayout relaCarriver;
    private String roleType=2+"";//roleType角色类型（2-承运商 3-司机）
    @BindView(R.id.edbiddfloor)EditText edFloorPrice;
    @BindView(R.id.rlfudu)RelativeLayout rlFudu;
    @BindView(R.id.edexcet)EditText edExcet;
    @BindView(R.id.tvtimeStart_task)TextView tvTaskTimeStart;
    @BindView(R.id.tvtimeEnd_task)TextView tvTaskTimeEnd;
    @BindView(R.id.checkBeidou) CheckBox checkBeiDou;
    @BindView(R.id.checkmoresend)CheckBox checkMoreCar;
    @BindView(R.id.tvCarType)TextView tvCarType;
    @BindView(R.id.llsendByCar) LinearLayout llSendByCar;
    @BindView(R.id.edHightBidd)EditText edBiddHight;
    @BindView(R.id.rlHightWeight)RelativeLayout rlHigheWeight;
    @BindView(R.id.radicheci) RadioButton radioCar;
    @BindView(R.id.toggle_openrank) SwitchButton switchButton;


    @OnClick({R.id.rl_timestart,R.id.rltimeend,
            R.id.rlcarrivegroup,R.id.tvOrder,R.id.rl_timestart_task,
            R.id.rltimeend_task,R.id.rlcar})
    public void click(View view){
        Bundle bundle;
        switch (view.getId()){
            case R.id.rl_timestart:
                if(timeChooseDialogUtil==null){
                    timeChooseDialogUtil=new TimeChooseDialogUtil(mContext);
                }
                timeChooseDialogUtil.showDialogSeconds(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        tvtimeStart.setText(result);
                    }
                    @Override
                    public void cancel() {
                        timeChooseDialogUtil.dissDialog();
                    }
                });
                break;
            case R.id.rltimeend:
                if(timeChooseDialogUtil==null){
                    timeChooseDialogUtil=new TimeChooseDialogUtil(mContext);
                }
                timeChooseDialogUtil.showDialogSeconds(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        tvtimeEnd.setText(result);
                    }
                    @Override
                    public void cancel() {
                        timeChooseDialogUtil.dissDialog();
                    }
                });
                break;
            case R.id.rl_timestart_task:
                if(timeChooseDialogUtil==null){
                    timeChooseDialogUtil=new TimeChooseDialogUtil(mContext);
                }
                timeChooseDialogUtil.showDialogSeconds(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        tvTaskTimeStart.setText(result);
                    }
                    @Override
                    public void cancel() {
                        timeChooseDialogUtil.dissDialog();
                    }
                });
                break;
            case R.id.rltimeend_task:
                if(timeChooseDialogUtil==null){
                    timeChooseDialogUtil=new TimeChooseDialogUtil(mContext);
                }
                timeChooseDialogUtil.showDialogSeconds(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        tvTaskTimeEnd.setText(result);
                    }
                    @Override
                    public void cancel() {
                        timeChooseDialogUtil.dissDialog();
                    }
                });
                break;
            case R.id.rlcar://选择车辆类型
                presenter.getCarType();
                break;
            case R.id.rlcarrivegroup:
                bundle =new Bundle();
                bundle.putString("from","orderzp");
                bundle.putString("id",id);
                bundle.putString("type","bidd");
                readyGoForResult(OrderMainActivity.class,CHOOSE_CARRIVE,bundle);
                break;
            case R.id.tvOrder:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                if(context.isEmptyStr(tvtimeStart)){
                    context.showMessage("请选择竞价开始时间");
                    return;
                }
                if(context.isEmptyStr(tvtimeEnd)){
                    context.showMessage("请选择竞价结束时间");
                    return;
                }
                if(context.isEmptyStr(editNum)){
                    context.showMessage("请最高输入竞价次数");
                    return;
                }
                if(type.equals("order")){
                    if(context.isEmptyStr(edweight)){
                        context.showMessage("请最高输入竞价重量");
                        return;
                    }
                }
                BiddTempDto  tempDto=new BiddTempDto();
                tempDto.setId(id);
                tempDto.setEdFloorPrice(edFloorPrice.getText().toString());
                tempDto.setExtent(edExcet.getText().toString());
                tempDto.setBiddNum(editNum.getText().toString());
                tempDto.setStartTime(tvtimeStart.getText().toString());
                tempDto.setEndTime(tvtimeEnd.getText().toString());
                tempDto.setBiddWeight(edweight.getText().toString());
                tempDto.setRoleType(roleType);
                tempDto.setTaskTimeStart(tvTaskTimeStart.getText().toString());
                tempDto.setTaskTimeEnd(tvTaskTimeEnd.getText().toString());
                tempDto.setHighBiddNum(edBiddHight.getText().toString());
                if(switchButton.isChecked()){
                   tempDto.setIsRank("1");
                }else {
                    tempDto.setIsRank("2");
                }
                if(checkBeiDou.isChecked()){
                    tempDto.setNeedBeiDou("1");
                }else {
                    tempDto.setNeedBeiDou("2");
                }
                if(checkMoreCar.isChecked()){
                    tempDto.setMoreDispatch("1");
                }else {
                    tempDto.setMoreDispatch("2");
                }
                if(TextUtils.isEmpty(tvCarType.getText().toString())){
                    tempDto.setCarTypeId("");
                    tempDto.setCarTypeName("全部");
                }else {
                    tempDto.setCarTypeId(carTypeId);
                    tempDto.setCarTypeName(tvCarType.getText().toString());
                }

                if(data!=null){
                    tempDto.setCarTypeId(data.getCarTypeId());
                    tempDto.setCarTypeName(data.getCarTypeName());
                }
                if(type.equals("waybill")){
                    tempDto.setWayBillType("2");
                }else if(type.equals("order")){
                    tempDto.setWayBillType("1");
                }else if(type.equals("manageorder")){
                    tempDto.setWayBillType("3");
                }
                presenter.addBidd(tempDto,mDatas);

                break;
        }
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_bidding;
    }

    OrderCarrierGroupDto dto;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CHOOSE_CARRIVE&& resultCode == RESULT_OK){
            dto = (OrderCarrierGroupDto) data.getSerializableExtra("data");
            if(dto!=null){
                tvcarriveGroupName.setText(dto.getName());
                mDatas.clear();
                presenter.getcarrive(dto.getId());

            }
        }
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"竞价");
        id=getArguments().getString("id");
        presenter=new OrderBiddPresenter(this,mContext);
        adapter=new OrderBiddCarriveAdapter(mDatas,mContext);
        layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        try {
            type=getArguments().getString("type");
            data= (OrderManageDto.manageData) getArguments().getSerializable("data");
        }catch (Exception e){

        }
        if(type.equals("order")){
            rlBiddWeight.setVisibility(View.VISIBLE);
            rlFudu.setVisibility(View.VISIBLE);
        }else {//小单不能够车次竞价
            radioCar.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {
        groupBill.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.radioCys:
                        //车次竞价
                        llSendByCar.setVisibility(View.GONE);
                        tvCarType.setText("");
                        //承运商分组
                        relaCarriver.setVisibility(View.VISIBLE);
                        //最高抢单量
                        rlHigheWeight.setVisibility(View.GONE);
                        edBiddHight.setText("");
                        carTypeId="";
                        roleType=2+"";
                        break;
                    case R.id.radiosiji:
                        //车次竞价
                        llSendByCar.setVisibility(View.GONE);
                        tvCarType.setText("");
                        //承运商分组
                        relaCarriver.setVisibility(View.GONE);
                        tvcarriveGroupName.setText("");
                        mDatas.clear();
                        adapter.notifyDataSetChanged();
                        //最高抢单量
                        rlHigheWeight.setVisibility(View.VISIBLE);
                        carTypeId="";
                        roleType=3+"";
                        break;
                    case R.id.radicheci://车次竞价
                        //车次竞价
                        llSendByCar.setVisibility(View.VISIBLE);
                        //承运商分组
                        relaCarriver.setVisibility(View.GONE);
                        mDatas.clear();
                        adapter.notifyDataSetChanged();
                        tvcarriveGroupName.setText("");

                        roleType=5+"";
                        break;
                }
            }
        });
    }

    @Override
    public void getCarriveList(List<OrderAssignmentSecondDto> dtos) {
        adapter.addMoreData(dtos);

    }
    private WheelDialog wheelDialog;
    private String carTypeId="";
    @Override
    public void getCarType(List<CarTypeDo> carTypeDos) {
        if(carTypeDos==null||carTypeDos.size()<=0){
            return;
        }
        List<String> strings=new ArrayList<>();

        List<CarTypeDo> temps=new ArrayList<>();
        CarTypeDo carTypeDo=new CarTypeDo();
        carTypeDo.setCarTypeName("全部");
        carTypeDo.setId("");
        temps.add(carTypeDo);
        temps.addAll(carTypeDos);
        for(int i=0;i<temps.size();i++){
            strings.add(temps.get(i).getCarTypeName());
        }
        wheelDialog=new WheelDialog(mContext,temps,strings);
        wheelDialog.Show(new WheelListener() {
            @Override
            public void callback(String name, String id) {
                tvCarType.setText(name);
                carTypeId=id;
            }
        });
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
        if(type.equals("waybill")){
            Intent intent = new Intent();
            intent.setAction(reshWaybIllOrder);
            context.sendBroadcast(intent);
        }else {
            Intent intent = new Intent();
            intent.setAction(reshPlanOrder);
            context.sendBroadcast(intent);
        }
        context.finish();
    }

}
