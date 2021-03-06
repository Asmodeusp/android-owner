package com.saimawzc.shipper.ui.order;

import static android.app.Activity.RESULT_OK;
import static com.saimawzc.shipper.ui.my.identification.CargoOwnerFragment.CHOOSE_COMPANY;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.bigkoo.pickerview.OptionsPickerView;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.ConsignmentCompanyDto;
import com.saimawzc.shipper.dto.order.consute.ConsuteDelationDto;
import com.saimawzc.shipper.dto.order.creatorder.AdressDto;
import com.saimawzc.shipper.dto.order.creatorder.AuthorityDtoSerializ;
import com.saimawzc.shipper.dto.order.creatorder.ChooseRouteDto;
import com.saimawzc.shipper.dto.order.creatorder.GoodsCompanyDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.dto.order.creatorder.UntilDto;
import com.saimawzc.shipper.presenter.order.OrderBasicinfoEditPresenter;
import com.saimawzc.shipper.ui.order.creatorder.ChooseAuthortityActivity;
import com.saimawzc.shipper.ui.order.creatorder.ChooseConsignCompanyActivity;
import com.saimawzc.shipper.view.order.OrderBasicInfoView;
import com.saimawzc.shipper.weight.TimeChooseDialogUtil;
import com.saimawzc.shipper.weight.utils.LengthFilter;
import com.saimawzc.shipper.weight.utils.dialog.WheelDialog;
import com.saimawzc.shipper.weight.utils.doubletimechoose.DoubleDateSelectDialog;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.listen.TimeChooseListener;
import com.saimawzc.shipper.weight.utils.listen.WheelListener;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/****
 * ????????????
 * **/
public class OrderBasicInfoFragment extends BaseFragment
        implements OrderBasicInfoView {
    @BindView(R.id.tvauthority) TextView tvAuthority;
    @BindView(R.id.tvtrantcompany) TextView tvTrantCompany;//????????????
    @BindView(R.id.tvbilltype) TextView tvBillType;
    @BindView(R.id.tvsendgoodscompany) TextView tvSendCompany;//?????????
    @BindView(R.id.tvsendadress) TextView tvSendAdress;//????????????
    @BindView(R.id.tvsendtime) TextView tvSendTime;
    @BindView(R.id.tvreceivetime)TextView tvReceiveTime;
    @BindView(R.id.tvreceivecompany) TextView tvReceiveCompany;
    @BindView(R.id.tvreceivetrategy)TextView tvReceiveTrategy;
    @BindView(R.id.tvarrivetime_start) TextView tvArriveTimeStart;
    @BindView(R.id.tvarrivetime_end) TextView tvArriveTimeEnd;
    @BindView(R.id.edprice) EditText edprice;
    @BindView(R.id.edgoodprice)EditText edGoodPrice;
    @BindView(R.id.edTrantNum) EditText edTrantNum;
    @BindView(R.id.tvOrderpeople)TextView tvOrderpeople;//???????????????
    @BindView(R.id.tvreceiveadress)TextView tvReceiveAdress;//????????????
    private TimeChooseDialogUtil timeChooseDialogUtil;
    @BindView(R.id.tvGoodName)TextView tvGoodName;
    @BindView(R.id.group_strage) RadioGroup groupStrage;
    @BindView(R.id.groupautosign)RadioGroup groupAutoSign;
    @BindView(R.id.radioCompelete) RadioButton radioCompelete;
    @BindView(R.id.radioSameType)RadioButton radioSameType;
    @BindView(R.id.radioAuto)RadioButton radioAuto;
    @BindView(R.id.radioNoAuto)RadioButton radioNoAuto;
    private String orderpeopleId="";//???????????????ID
    @BindView(R.id.tvWeightUtil)TextView tvWeightUtil;
    private OptionsPickerView optionsPickerView;//??????????????????
    private String id;
    private String orderCode;
    private String businessType;
    @BindView(R.id.rl_yewu)
    RelativeLayout rlYw_Type;//????????????
    @BindView(R.id.tvywtype)TextView tvYeType;
    @BindView(R.id.tvtrantway)TextView tvTrantWay;//????????????

    @BindView(R.id.tvRoute)TextView tvRoute;
    private ChooseRouteDto routeDto;

    private OrderBasicinfoEditPresenter presenter;
    @Override
    public int initContentView() {
        return R.layout.tab_order_basic;
    }

    @Override
    public void initView() {

        try{
            presenter=new OrderBasicinfoEditPresenter(this,mContext);
            id=getArguments().getString("id");
            if(!TextUtils.isEmpty(id)){
                presenter.getpOrderDelation(id);
            }
        }catch (Exception e){

        }

        try{
            orderCode=getArguments().getString("orderCode");
            businessType=getArguments().getString("businessType");
            if(!TextUtils.isEmpty(orderCode)){
                presenter.getConsuteDelation(orderCode,businessType);
            }
        }catch (Exception E){

        }



        if(!TextUtils.isEmpty(Hawk.get(PreferenceKey.IS_TUOYUN,""))){
            if(Hawk.get(PreferenceKey.IS_TUOYUN,"").equals("1")){
                rlYw_Type.setVisibility(View.VISIBLE);
            }
        }
        edTrantNum.setFilters(new InputFilter[] {new LengthFilter(3)});
    }

    int receiveStarge=2;//1-??????????????????2-????????????
    int autSign=1;//????????????1-??????2-???
    @Override
    public void initData() {
        groupStrage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.radioCompelete:
                        receiveStarge=2;
                        break;
                    case R.id.radioSameType:
                        receiveStarge=1;
                        break;
                }
            }
        });
        groupAutoSign.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.radioAuto:
                        autSign=1;
                        radioAuto.setChecked(true);
                        radioNoAuto.setChecked(false);
                        break;
                    case R.id.radioNoAuto:
                        autSign=2;
                        radioAuto.setChecked(false);
                        radioNoAuto.setChecked(true);
                        break;
                }
            }
        });
    }
    //data
    AuthorityDtoSerializ authorityDto;//????????????
    ConsignmentCompanyDto consignmentCompanyDto;//????????????
    GoodsCompanyDto sendCompanyDto;//????????????
    GoodsCompanyDto receiveCompanyDto;//????????????
    GoodsCompanyDto billTypesDto;//????????????
    GoodsCompanyDto signStrageDto;//????????????
    //GoodsCompanyDto orderPeopelDto;//???????????????
    AdressDto sendAdressDto;
    AdressDto receiveAdressDto;
    GoodsCompanyDto goodNameDto;//????????????
    GoodsCompanyDto trantTypeDto;//????????????
    private ArrayList<String>nameList;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CHOOSE_COMPANY&& resultCode == RESULT_OK){
            authorityDto = (AuthorityDtoSerializ) data.getSerializableExtra("data");
            if(authorityDto!=null){
                tvAuthority.setText(authorityDto.getCompanyName());
                sendCompanyDto=null;
                receiveCompanyDto=null;
                tvSendCompany.setText("");
                tvReceiveCompany.setText("");
                Hawk.put(PreferenceKey.AuthorID,authorityDto.getId());
            }
        }
        if(requestCode==CHOOSE_Route&& resultCode == RESULT_OK){//????????????
            routeDto= (ChooseRouteDto) data.getSerializableExtra("data");
            if(routeDto!=null){
                tvRoute.setText(routeDto.getRouteName());
            }
        }
        if(requestCode==CONSIGNcOMPANY&& resultCode == RESULT_OK){//????????????
            consignmentCompanyDto = (ConsignmentCompanyDto) data.getSerializableExtra("data");
            if(consignmentCompanyDto!=null){
                tvTrantCompany.setText(consignmentCompanyDto.getCompanyName());
            }
        }
        //???????????????????????????
        if(requestCode==CHOOSE_ADRESS&& resultCode == RESULT_OK){//
            if(data.getStringExtra("type").equals("1")){
                sendAdressDto = (AdressDto) data.getSerializableExtra("data");
                if(sendAdressDto!=null){
                    tvSendAdress.setText(sendAdressDto.getAllAddress());
                }
            }else if( data.getStringExtra("type").equals("2")){
                receiveAdressDto = (AdressDto) data.getSerializableExtra("data");
                if(receiveAdressDto!=null){
                    tvReceiveAdress.setText(receiveAdressDto.getAllAddress());
                }
            }
        }
        //?????????????????????
        if(requestCode==CHOOSE_GOODSCOMPANY&& resultCode == RESULT_OK){
            if(data.getStringExtra("type").equals("1")){
                sendCompanyDto = (GoodsCompanyDto) data.getSerializableExtra("data");
                tvSendCompany.setText(sendCompanyDto.getName());
                sendAdressDto=null;
                tvSendAdress.setText("");
            }else if(data.getStringExtra("type").equals("2")){
                receiveCompanyDto=(GoodsCompanyDto) data.getSerializableExtra("data");
                tvReceiveCompany.setText(receiveCompanyDto.getName());
                receiveAdressDto=null;
                tvReceiveAdress.setText("");

            }else if(data.getStringExtra("type").equals("3")){
                billTypesDto= (GoodsCompanyDto) data.getSerializableExtra("data");
                tvBillType.setText(billTypesDto.getName());
            }else if(data.getStringExtra("type").equals("4")){
                signStrageDto= (GoodsCompanyDto) data.getSerializableExtra("data");
                tvReceiveTrategy.setText(signStrageDto.getName());
            }else if(data.getStringExtra("type").equals("5")){
               // orderPeopelDto= (GoodsCompanyDto) data.getSerializableExtra("data");
                ArrayList<String>idList= (ArrayList<String>) data.getSerializableExtra("data");
                nameList= (ArrayList<String>) data.getSerializableExtra("nameList");
                if(idList!=null){
                    String nameString="";
                    for(int i=0;i<nameList.size();i++){
                        if(i==nameList.size()-1){
                            nameString+=nameList.get(i);
                        }else {
                            nameString+=nameList.get(i)+",";
                        }
                    }
                    for(int i=0;i<idList.size();i++){
                        if(i==idList.size()-1){
                            orderpeopleId+=idList.get(i);
                        }else {
                            orderpeopleId+=idList.get(i)+",";
                        }
                    }
                    tvOrderpeople.setText(nameString);
                }
            }else if(data.getStringExtra("type").equals("6")){
                goodNameDto= (GoodsCompanyDto) data.getSerializableExtra("data");
                if(goodNameDto!=null){
                    tvGoodName.setText(goodNameDto.getName());
                }
            }else if(data.getStringExtra("type").equals("7")){
                trantTypeDto =(GoodsCompanyDto) data.getSerializableExtra("data");
                if(trantTypeDto!=null){
                    tvTrantWay.setText(trantTypeDto.getName());
                }
            }
        }
    }
    private List<String>oplitions=new ArrayList<>();
    public static final int CONSIGNcOMPANY=102;//????????????
    public static final int CHOOSE_GOODSCOMPANY=103;//
    public static final int CHOOSE_ADRESS=104;//
    public static final int CHOOSE_Route=105;//
    @OnClick({R.id.rl_authority,R.id.rl_company,R.id.rl_bill,R.id.rl_sendcompany
    ,R.id.rl_sendadress,R.id.rl_sendtime,R.id.rl_receivecompany,R.id.rl_receivetime
    ,R.id.rl_receivepeople,R.id.rl_receivestrategy,R.id.rl_arrivetime_start,
            R.id.rl_arrivetime_end,R.id.rl_goodsname,R.id.rl_trant
    ,R.id.rl_receiveadress,R.id.rl_chooseUtil,R.id.rl_yewu,R.id.rlRoute})
    public void click(View view){
        Bundle bundle=null;
        switch (view.getId()){
            case R.id.rlRoute://????????????
                bundle=new Bundle();
                bundle.putString("from","route");
                readyGoForResult(OrderMainActivity.class,CHOOSE_Route,bundle);
                break;
            case R.id.rl_trant://????????????
                bundle=new Bundle();
                bundle.putString("type",7+"");
                bundle.putString("from","choosegoodscompany");
                readyGoForResult(OrderMainActivity.class,CHOOSE_GOODSCOMPANY,bundle);
                break;
            case R.id.rl_yewu://????????????
                oplitions.clear();
                oplitions.add("??????");
                oplitions.add("??????");
                oplitions.add("??????");
                initOptionPicker(oplitions,2);
                break;
            case R.id.rl_authority://????????????
                readyGoForResult(ChooseAuthortityActivity.class,CHOOSE_COMPANY);
                break;
            case R.id.rl_company://????????????
                readyGoForResult(ChooseConsignCompanyActivity.class,CONSIGNcOMPANY);
                break;
            case R.id.rl_bill://????????????
                bundle=new Bundle();
                bundle.putString("type",3+"");
                bundle.putString("from","choosegoodscompany");
                readyGoForResult(OrderMainActivity.class,CHOOSE_GOODSCOMPANY,bundle);
                break;
            case R.id.rl_sendcompany://?????????
                if(authorityDto==null){
                    context.showMessage("?????????????????????");
                    return;
                }
                bundle=new Bundle();
                bundle.putString("type",1+"");
                bundle.putString("authorityId",authorityDto.getId());
                bundle.putString("from","choosegoodscompany");
                readyGoForResult(OrderMainActivity.class,CHOOSE_GOODSCOMPANY,bundle);
                break;
            case R.id.rl_sendtime://????????????
                showCustomTimePicker("send");
                break;
            case R.id.rl_sendadress://????????????
                if(context.isEmptyStr(tvSendCompany)){
                    context.showMessage("??????????????????");
                    return;
                }
                bundle=new Bundle();
                bundle.putString("unitId",sendCompanyDto.getId());
                bundle.putSerializable("data",sendCompanyDto);
                bundle.putString("from","sendadress");
                bundle.putString("type",1+"");
                readyGoForResult(OrderMainActivity.class,CHOOSE_ADRESS,bundle);
                break;
            case R.id.rl_receiveadress://????????????
                if(context.isEmptyStr(tvReceiveCompany)){
                    context.showMessage("??????????????????");
                    return;
                }
                bundle=new Bundle();
                bundle.putSerializable("data",receiveCompanyDto);
                bundle.putString("unitId",receiveCompanyDto.getId());
                bundle.putString("from","sendadress");
                bundle.putString("type",2+"");
                readyGoForResult(OrderMainActivity.class,CHOOSE_ADRESS,bundle);
                break;
            case R.id.rl_receivecompany://?????????
                if(authorityDto==null){
                    context.showMessage("?????????????????????");
                    return;
                }
                bundle=new Bundle();
                bundle.putString("authorityId",authorityDto.getId());
                bundle.putString("type",2+"");
                bundle.putString("from","choosegoodscompany");
                readyGoForResult(OrderMainActivity.class,CHOOSE_GOODSCOMPANY,bundle);
                break;
            case R.id.rl_receivetime://????????????
                showCustomTimePicker("receive");
                break;
            case R.id.rl_receivepeople://???????????????
                bundle=new Bundle();
                bundle.putString("type",5+"");
                bundle.putString("from","choosegoodscompany");
                readyGoForResult(OrderMainActivity.class,CHOOSE_GOODSCOMPANY,bundle);
                break;
            case R.id.rl_receivestrategy://????????????
                bundle=new Bundle();
                bundle.putString("type",4+"");
                bundle.putString("from","choosegoodscompany");
                readyGoForResult(OrderMainActivity.class,CHOOSE_GOODSCOMPANY,bundle);
                break;
            case R.id.rl_arrivetime_start://????????????
                if(timeChooseDialogUtil==null){
                    timeChooseDialogUtil=new TimeChooseDialogUtil(mContext);
                }
                timeChooseDialogUtil.showDialog(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        tvArriveTimeStart.setText(result);
                    }
                    @Override
                    public void cancel() {
                        timeChooseDialogUtil.dissDialog();
                    }
                });
                break;
            case R.id.rl_arrivetime_end://????????????
                if(timeChooseDialogUtil==null){
                    timeChooseDialogUtil=new TimeChooseDialogUtil(mContext);
                }
                timeChooseDialogUtil.showDialog(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        tvArriveTimeEnd.setText(result);
                    }
                    @Override
                    public void cancel() {
                        timeChooseDialogUtil.dissDialog();
                    }
                });
                break;
            case R.id.rl_goodsname://????????????
                bundle=new Bundle();
                bundle.putString("type",6+"");
                bundle.putString("from","choosegoodscompany");
                readyGoForResult(OrderMainActivity.class,CHOOSE_GOODSCOMPANY,bundle);
                break;
            case R.id.rl_chooseUtil://??????????????????
                presenter.getUntil();
                break;

        }
    }

    private DoubleDateSelectDialog mDoubleTimeSelectDialog;
    public void showCustomTimePicker(final String type) {
            mDoubleTimeSelectDialog = new DoubleDateSelectDialog(mContext, "00:00", "23:59", "");
            mDoubleTimeSelectDialog.setOnDateSelectFinished(new DoubleDateSelectDialog.OnDateSelectFinished() {
                @Override
                public void onSelectFinished(String startTime, String endTime) {
                  if(type.equals("send")){
                      tvSendTime.setText(startTime.replace("-", ".") + "~" + endTime.replace("-", "."));
                  }else if(type.equals("receive")) {
                      tvReceiveTime.setText(startTime.replace("-", ".") + "~" + endTime.replace("-", "."));
                  }
                }
            });
            mDoubleTimeSelectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                }
            });
        if (!mDoubleTimeSelectDialog.isShowing()) {
            mDoubleTimeSelectDialog.show();
        }
    }

    private void initOptionPicker(final List<String> optionList, final int type){

            optionsPickerView = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    String str = optionList.get(options1);
                    if(type==1){//??????
                        tvWeightUtil.setText(str);
                    }else if(type==2){//????????????
                        tvYeType.setText(str);
                    }

                }
            }).setCancelColor(Color.GRAY).
                    setSubmitColor(Color.RED).build();
            optionsPickerView.setNPicker(optionList,null,null);

        optionsPickerView.show();
    }

    @Override
    public AuthorityDtoSerializ getAuthorityDto() {
        return authorityDto;
    }

    @Override
    public ConsignmentCompanyDto getConsignmentCompanyDto() {
        return consignmentCompanyDto;
    }

    @Override
    public GoodsCompanyDto getBilltype() {
        return billTypesDto;
    }

    @Override
    public GoodsCompanyDto getSendCompany() {
        return sendCompanyDto;
    }

    @Override
    public GoodsCompanyDto getReceiveCompany() {
        return receiveCompanyDto;
    }

    @Override
    public AdressDto getSendAdress() {
        return sendAdressDto;
    }

    @Override
    public AdressDto getReceiveAdress() {
        return receiveAdressDto;
    }

    @Override
    public GoodsCompanyDto getorderPeopelDto() {
        return null;
    }

    @Override
    public GoodsCompanyDto getsignStrageDto() {
        return signStrageDto;
    }

    @Override
    public GoodsCompanyDto getGoodName() {
        return goodNameDto;
    }

    @Override
    public String getSendBussineTime() {
        return tvSendTime.getText().toString();
    }

    @Override
    public String getReceiveBussineTime() {
        return tvReceiveTime.getText().toString();
    }

    @Override
    public String isAuthSign() {//??????????????????
        return autSign+"";
    }

    @Override
    public String getreceivesing() {//????????????
        return receiveStarge+"";
    }

    @Override
    public String getarriveTimeStart() {
        return tvArriveTimeStart.getText().toString();
    }

    @Override
    public String getarriveTimeEnd() {
        return tvArriveTimeEnd.getText().toString();
    }

    @Override
    public ChooseRouteDto getRouteDto() {
        return routeDto;
    }

    @Override
    public String getNum() {
        return edTrantNum.getText().toString();
    }

    @Override
    public String getPrice() {
        return edprice.getText().toString();
    }

    @Override
    public String getOrderPeopleId() {
        return orderpeopleId;
    }

    private String untilId;
    @Override
    public String getWeightUtil() {
        return  untilId;

    }

    @Override
    public String getGoodPrice() {
        return edGoodPrice.getText().toString();
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
    public void getOrderDelation(OrderDelationDto dto) {
        if(dto!=null){
            //????????????
            authorityDto=new AuthorityDtoSerializ();
            authorityDto.setCompanyName(dto.getCompanyName());
            authorityDto.setId(dto.getCompanyId());
            Hawk.put(PreferenceKey.AuthorID,dto.getCompanyId());
            tvAuthority.setText(dto.getCompanyName());

            //????????????
            consignmentCompanyDto=new ConsignmentCompanyDto();
            if(consignmentCompanyDto!=null){
                consignmentCompanyDto.setCompanyName(dto.getHandComName());
                consignmentCompanyDto.setId(dto.getHandCompanyId());
                tvTrantCompany.setText(dto.getHandComName());
            }
            if(dto.getBusinessType()==1){
                tvYeType.setText("??????");
            }else if(dto.getBusinessType()==2){
                tvYeType.setText("??????");
            }else {
                tvYeType.setText("??????");
            }
            //????????????
            billTypesDto=new GoodsCompanyDto();
            billTypesDto.setId(dto.getWayBillType());
            billTypesDto.setName(dto.getWayBillTypeName());
            tvBillType.setText(dto.getWayBillTypeName());
             ////????????????
            trantTypeDto=new GoodsCompanyDto();
            trantTypeDto.setId(dto.getTranType());
            trantTypeDto.setName(dto.getTranTypeName());
            tvTrantWay.setText(dto.getTranTypeName());
            //?????????
            sendCompanyDto=new GoodsCompanyDto();
            sendCompanyDto.setName(dto.getFromName());
            sendCompanyDto.setId(dto.getFromId());
            tvSendCompany.setText(dto.getFromName());
            //????????????
            tvSendAdress.setText(dto.getFromUserAddress());
            sendAdressDto=new AdressDto();
            sendAdressDto.setAllAddress(dto.getFromUserAddress());
            sendAdressDto.setLocation(dto.getFromLocation());
            sendAdressDto.setProName(dto.getFromProName());
            sendAdressDto.setCityName(dto.getFromCityName());
            sendAdressDto.setContactsName(dto.getFromUserName());
            sendAdressDto.setContactsPhone(dto.getFromUserPhone());
            //????????????
            tvSendTime.setText(dto.getFromOperateTime());
            //????????????
            receiveCompanyDto=new GoodsCompanyDto();
            receiveCompanyDto.setName(dto.getToName());
            receiveCompanyDto.setId(dto.getToId());
            tvReceiveCompany.setText(dto.getToName());
            //????????????
            receiveAdressDto =new AdressDto();
            receiveAdressDto.setAllAddress(dto.getToUserAddress());
            receiveAdressDto.setLocation(dto.getToLocation());
            receiveAdressDto.setProName(dto.getToProName());
            receiveAdressDto.setCityName(dto.getToCityName());
            receiveAdressDto.setContactsName(dto.getToUserName());
            receiveAdressDto.setContactsPhone(dto.getToUserPhone());
            tvReceiveAdress.setText(dto.getToUserAddress());
            //??????????????????
            tvReceiveTime.setText(dto.getToOperateTime());
            //???????????????
            GoodsCompanyDto orderPeopelDto=new GoodsCompanyDto();
            orderPeopelDto.setName(dto.getConfirmorName());
            orderPeopelDto.setId(dto.getConfirmor());
            tvOrderpeople.setText(dto.getConfirmorName());
            orderpeopleId=dto.getConfirmor();
            //????????????
            if(dto.getConfirmorStactics().equals("1")){//???????????????
                receiveStarge=1;
                radioCompelete.setChecked(false);
                radioSameType.setChecked(true);
            }else {
                radioCompelete.setChecked(true);
                radioSameType.setChecked(false);
                receiveStarge=2;
            }
            //????????????
            signStrageDto=new GoodsCompanyDto();
            signStrageDto.setId(dto.getSingStacticsId());
            signStrageDto.setName(dto.getSingStacticsName());
            tvReceiveTrategy.setText(signStrageDto.getName());
            //??????????????????
            autSign=dto.getAutoSign();
            if(dto.getAutoSign()==1){
                radioAuto.setChecked(true);
                radioNoAuto.setChecked(false);
            }else {
                radioAuto.setChecked(false);
                radioNoAuto.setChecked(true);
            }
            //????????????
            routeDto=new ChooseRouteDto();
            routeDto.setRouteName(dto.getRouteName());
            routeDto.setId(dto.getTrackRouteId());
            tvRoute.setText(dto.getRouteName());
            //??????????????????
            tvArriveTimeStart.setText(dto.getArrivalStartTime());
            tvArriveTimeEnd.setText(dto.getArrivalEndTime());
            //????????????
            goodNameDto=new GoodsCompanyDto();
            goodNameDto.setName(dto.getList().get(0).getMaterialsName());
            goodNameDto.setId(dto.getList().get(0).getMaterialsId());
            tvGoodName.setText(dto.getList().get(0).getMaterialsName());
            edprice.setText(dto.getList().get(0).getPrice()+"");
            edGoodPrice.setText(dto.getList().get(0).getGoodprice()+"");

            edTrantNum.setText(dto.getList().get(0).getWeight()+"");
            tvWeightUtil.setText(dto.getList().get(0).getUnitName());
            untilId=dto.getList().get(0).getUnit()+"";
        }
    }
    /*****
     * ????????????
     * ***/
    @Override
    public void getConsuteDelation(ConsuteDelationDto dto) {
        if(dto!=null){
            //????????????
            authorityDto=new AuthorityDtoSerializ();
            authorityDto.setCompanyName(dto.getCompanyName());
            authorityDto.setId(dto.getCompanyId());
            Hawk.put(PreferenceKey.AuthorID,dto.getCompanyId());
            tvAuthority.setText(dto.getCompanyName());

            //????????????
            billTypesDto=new GoodsCompanyDto();
            billTypesDto.setId(dto.getWayBillType());
            if(dto.getWayBillType().equals("1")){
                billTypesDto.setName("????????????");
                tvBillType.setText("????????????");
            }else if(dto.getWayBillType().equals("2")){
                billTypesDto.setName("????????????");
                tvBillType.setText("????????????");
            }else {
                billTypesDto.setName("????????????");
                tvBillType.setText("????????????");
            }

            //?????????
            sendCompanyDto=new GoodsCompanyDto();
            sendCompanyDto.setName(dto.getFromName());
            sendCompanyDto.setId(dto.getFromId());
            tvSendCompany.setText(dto.getFromName());

            //????????????
            receiveCompanyDto=new GoodsCompanyDto();
            receiveCompanyDto.setName(dto.getToName());
            receiveCompanyDto.setId(dto.getToId());
            tvReceiveCompany.setText(dto.getToName());

            ////????????????
            trantTypeDto=new GoodsCompanyDto();
            trantTypeDto.setId(dto.getTranType()+"");
            if(dto.getTranType()==1){
                trantTypeDto.setName("??????");
                tvTrantWay.setText("??????");
            }else {
                trantTypeDto.setName("??????");
                tvTrantWay.setText("??????");
            }
            //????????????
            goodNameDto=new GoodsCompanyDto();
            goodNameDto.setName(dto.getMaterialsName());
            goodNameDto.setId(dto.getMaterialsId());
            tvGoodName.setText(dto.getMaterialsName());
            if(!TextUtils.isEmpty(dto.getPrice())){
                edprice.setText(dto.getPrice()+"");
            }

            edTrantNum.setText(dto.getWeight()+"");
            tvWeightUtil.setText("???");
            untilId=1+"";

        }

    }



    @Override
    public String yewuType() {
        return tvYeType.getText().toString();
    }

    @Override
    public GoodsCompanyDto trantWay() {
        return trantTypeDto;
    }

    @Override
    public void getUntil(List<UntilDto> untilDtos) {
        if(untilDtos!=null){
            if(untilDtos.size()<=0){
                return;
            }
            List<String> strings=new ArrayList<>();
            for(int i=0;i<untilDtos.size();i++){
                strings.add(untilDtos.get(i).getUnit());
            }
            WheelDialog  wheelDialog=new WheelDialog(mContext,untilDtos,strings);
            wheelDialog.Show(new WheelListener() {
                @Override
                public void callback(String name, String id) {
                    tvWeightUtil.setText(name);
                    untilId=id;
                }
            });
        }

    }


}
