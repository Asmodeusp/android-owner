package com.saimawzc.shipper.ui.order;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.dto.order.consute.ConsuteDelationDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.presenter.order.OrderBasicinfoEditPresenter;
import com.saimawzc.shipper.presenter.order.OrderOpintalnfoEditPresenter;
import com.saimawzc.shipper.ui.order.creatorder.richtext.RichPublishActivity;
import com.saimawzc.shipper.ui.order.creatorder.richtext.ShowArtActivity;
import com.saimawzc.shipper.view.order.OrderOptionalInfoView;
import com.saimawzc.shipper.weight.SwitchButton;
import com.saimawzc.shipper.weight.TimeChooseDialogUtil;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.listen.TimeChooseListener;
import com.saimawzc.shipper.weight.utils.loadimg.ImageLoadUtil;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/***
 * 选填信息
 * **/
public class OrderOptionalInfoFragment extends BaseFragment
        implements OrderOptionalInfoView {

    boolean isChoose=false;//是否有操作
    @BindView(R.id.edksNum) EditText edksNum;
    @BindView(R.id.tvmakeTime) TextView tvmakeTime;
    @BindView(R.id.toggleIszc) SwitchButton switchZc;
    @BindView(R.id.togglexh)SwitchButton switchXh;
    @BindView(R.id.togglefp)SwitchButton switchFp;
    @BindView(R.id.toggleYh)SwitchButton toggleYh;
    @BindView(R.id.togglepz)SwitchButton togglezhPz;//装货拍照
    @BindView(R.id.togglexhpz)SwitchButton togglexhpz;//卸货拍照
    @BindView(R.id.tvxy)TextView tvxy;//付款协议
    @BindView(R.id.edmark)EditText edMark;//备注
    private TimeChooseDialogUtil timeChooseDialogUtil;
    @BindView(R.id.tvMakePeople)EditText tvMakePeople;
    private String from;
    /**出厂榜单**/
    @BindView(R.id.togglebz)SwitchButton swBangBan;
    /**偏离预警**/
    @BindView(R.id.togglepl)SwitchButton swDeviate;
    /**停留预警**/
    @BindView(R.id.togglestay)SwitchButton swStay;
    @BindView(R.id.edstayTime)EditText edStayTime;
    /**智能绑锁**/
    @BindView(R.id.togglelock)SwitchButton swLock;
    /**离线预警**/
    @BindView(R.id.toggleoffline)SwitchButton swOffline;

    @BindView(R.id.tvreceiveObj)TextView tvReceiveObj;
    @BindView(R.id.tvhzlist)TextView tvHzList;
    @BindView(R.id.tvexaminelist)TextView tvexaminelist;
    public final static int CHOOSE_RECEIVE_OBJ=12345;
    public final static int CHOOSE_CAR_TYPE=12346;
    public final static int ANQUANTIP=12347;
    @BindView(R.id.rlyh) RelativeLayout rlYh;

    /****启用车型**/
    @BindView(R.id.togglestartcarmodel)SwitchButton swCarModel;
    @BindView(R.id.tvcarmodel)TextView tvCarModel;
    @BindView(R.id.tvanquan)TextView tvAnquan;
    private String carModerId;
    @BindView(R.id.eddriverage)EditText edDriverAge;
    @BindView(R.id.edcarage)EditText edCarAge;

    private UserInfoDto userInfoDto;
    private String id;
    private String orderCode;
    private String businessType;
    private OrderOpintalnfoEditPresenter presenter;
    private String htmlContext;

    @Override
    public int initContentView() {
        return R.layout.tab_order_optional;
    }

    @OnClick({R.id.tvmakeTime,R.id.tvreceiveObj,R.id.tvhzlist,
            R.id.tvexaminelist,R.id.tvanquan,R.id.tvcarmodel})
    public void click(View view){
        Bundle bundle;
        switch (view.getId()){
            case R.id.tvcarmodel://车型列表
                bundle=new Bundle();
                bundle.putString("from","choosecartype");
                readyGoForResult(OrderMainActivity.class,CHOOSE_CAR_TYPE,bundle);
                break;
            case R.id.tvanquan:
                 bundle=new Bundle();
                bundle.putString("data",htmlContext);
                readyGoForResult(RichPublishActivity.class,ANQUANTIP,bundle);
                break;
            case R.id.tvmakeTime:

                if(timeChooseDialogUtil==null){
                    timeChooseDialogUtil=new TimeChooseDialogUtil(mContext);
                }
                timeChooseDialogUtil.showDialog(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        isChoose=true;
                        tvmakeTime.setText(result);
                    }
                    @Override
                    public void cancel() {
                        timeChooseDialogUtil.dissDialog();
                    }
                });
                break;
            case R.id.tvreceiveObj:
                bundle=new Bundle();
                bundle.putString("type",8+"");
                bundle.putString("from","choosegoodscompany");
                readyGoForResult(OrderMainActivity.class,CHOOSE_RECEIVE_OBJ,bundle);
                break;
            case R.id.tvhzlist:
                bundle=new Bundle();
                bundle.putString("type",9+"");
                bundle.putString("from","chooseHzList");
                readyGoForResult(OrderMainActivity.class,CHOOSE_RECEIVE_OBJ,bundle);
                break;
            case R.id.tvexaminelist://验货人
                bundle=new Bundle();
                bundle.putString("type",10+"");
                bundle.putString("from","chooseHzList");
                readyGoForResult(OrderMainActivity.class,CHOOSE_RECEIVE_OBJ,bundle);
                break;
        }
    }

    @Override
    public void initView() {
        mContext=getActivity();
        userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
        if(userInfoDto!=null){
            if(!TextUtils.isEmpty(userInfoDto.getName())){
                tvMakePeople.setText(userInfoDto.getName());
            }
        }
        try{
            presenter=new OrderOpintalnfoEditPresenter(this,mContext);
            id=getArguments().getString("id");
            if(!TextUtils.isEmpty(id)){
                from=getArguments().getString("from");
                presenter.getpOrderDelation(id,from);
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
    }
    @Override
    public void initData() {
        edksNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s.toString())){
                    isChoose=false;
                }else {
                    isChoose=true;
                }
            }
        });

        edMark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s.toString())){
                    isChoose=false;
                }else {
                    isChoose=true;
                }
            }
        });
        edStayTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s.toString())){
                    isChoose=false;
                }else {
                    isChoose=true;
                }
            }
        });
        switchZc.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
               isChoose=isChecked;
            }
        });
        switchXh.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose=isChecked;
            }
        });
        switchFp.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose=isChecked;
            }
        });
        toggleYh.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose=isChecked;
                if(isChecked){
                    rlYh.setVisibility(View.VISIBLE);
                }else {
                    tvexaminelist.setText("");
                    examinelistId="";
                    rlYh.setVisibility(View.GONE);
                }
            }
        });
        togglezhPz.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose=isChecked;
            }
        });
        togglexhpz.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose=isChecked;
            }
        });

        swLock.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose=isChecked;
            }
        });
        swDeviate.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose=isChecked;
            }
        });
        swBangBan.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose=isChecked;
            }
        });
        swStay.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose=isChecked;
            }
        });
        swOffline.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose=isChecked;
            }
        });

    }
    @Override
    public String makeOrderTime() {
        return tvmakeTime.getText().toString();
    }
    @Override
    public String makePeople() {
        return tvMakePeople.getText().toString();
    }

    @Override
    public String OrderNum() {
        return edksNum.getText().toString();
    }

    @Override
    public int isApplzc() {
        if(switchZc.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int isApplxh() {
        if(switchXh.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int isApplfp() {
        if(switchFp.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int isApplyh() {
        if(toggleYh.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int isApplzhpz() {
        if(togglezhPz.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int isApplxhpz() {
        if(togglexhpz.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int bindSmartLock() {
        if(swLock.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int outFactoryPhotos() {
        if(swBangBan.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int stopAlarm() {
        if(swStay.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    //离线
    @Override
    public int offLineAlarm() {
        if(swOffline.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    /***
     * 偏离预警
     * **/
    @Override
    public int deviationAlarm() {
        if(swDeviate.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int openCarType() {
        if(swCarModel.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public String carTypeId() {
        return carModerId;
    }

    @Override
    public String carTypeName() {
        return tvCarModel.getText().toString();
    }

    @Override
    public String context() {
        return htmlContext;
    }

    @Override
    public String stayTime() {
        return edStayTime.getText().toString();
    }

    @Override
    public String pushAlarmRole() {
        return receivObjId;
    }

    @Override
    public String alarmHz() {
        return hzlistId;
    }

    @Override
    public String xieyi() {
        return "";
    }

    @Override
    public String mark() {
        return edMark.getText().toString();
    }

    @Override
    public String checkUserList() {
        return examinelistId;
    }

    @Override
    public String driverAge() {
        return edDriverAge.getText().toString();
    }

    @Override
    public String carAge() {
        return edCarAge.getText().toString();
    }

    @Override
    public void getOrderDelation(OrderDelationDto dto) {
        if(dto!=null){
            OrderDelationDto.choosedata choosedata=dto.getChoose();
            if(choosedata!=null){
                edksNum.setText(choosedata.getThirdPartyNo());
                tvmakeTime.setText(choosedata.getMakerName());
                tvmakeTime.setText(choosedata.getMakerTime());
                tvxy.setText(choosedata.getPayProtocolName());
                edMark.setText(choosedata.getRemark());
                if(dto.getChoose().getProvideInvoice()==1){//发票
                    switchFp.setChecked(true);
                }
                if(dto.getChoose().getProvideUnload()==1){//提供卸货
                    switchXh.setChecked(true);
                }
                if(dto.getChoose().getProvideLoad()==1){////提供发货
                    switchZc.setChecked(true);
                }
                if(dto.getChoose().getCheck()==1){//验货
                    toggleYh.setChecked(true);
                    rlYh.setVisibility(View.VISIBLE);
                    examinelistId=dto.getChoose().getCheckUserList();
                    tvexaminelist.setText(dto.getChoose().getCheckUserListName());
                }else {
                    rlYh.setVisibility(View.GONE);
                }
                if(dto.getChoose().getLoadPhotos()==1){//装货拍照
                    togglezhPz.setChecked(true);
                }
                if(dto.getChoose().getUnloadPhotos()==1){//卸货拍照
                    togglexhpz.setChecked(true);
                }
                if(dto.getChoose().getOutFactoryPhotos()==1){
                    swBangBan.setChecked(true);
                }
                if(dto.getChoose().getStopAlarm()==1){
                    swStay.setChecked(true);
                }
                if(dto.getChoose().getBindSmartLock()==1){
                    swLock.setChecked(true);
                }
                if(dto.getChoose().getDeviationAlarm()==1){
                    swDeviate.setChecked(true);
                }
                if(dto.getChoose().getOffLineAlarm()==1){
                    swOffline.setChecked(true);
                }
                if(dto.getChoose().getAlarmTime()!=0){
                    edStayTime.setText(dto.getChoose().getAlarmTime()+"");
                }
                tvHzList.setText(dto.getChoose().getAlarmHzName());
                hzlistId=dto.getChoose().getAlarmHz();
                tvReceiveObj.setText(dto.getChoose().getPushAlarmRoleName());
                receivObjId=dto.getChoose().getPushAlarmRole();
                if(dto.getChoose().getOpenCarType()==1){
                    swCarModel.setChecked(true);
                }else {
                    swCarModel.setChecked(false);
                }
                tvCarModel.setText(dto.getChoose().getCarTypeName());
                carModerId=dto.choose.getCarTypeId();
                htmlContext=dto.choose.getContext();
                if(!TextUtils.isEmpty(htmlContext)){
                    tvAnquan.setText("点击查看");
                }
                edDriverAge.setText(dto.getChoose().getDrivingYears());
                edCarAge.setText(dto.getChoose().getTravelYears());

            }
        }
    }

    @Override
    public boolean getIschoose() {
        return isChoose;
    }

    @Override
    public void getConsuteDealtion(ConsuteDelationDto dto) {
        if(dto!=null){
            edksNum.setText(dto.getThirdPartyNo());
            tvMakePeople.setText(dto.getMakerName());
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
    private ArrayList<String>nameList;
    private String receivObjId="";//接收对象ID
    private String hzlistId="";
    private String examinelistId="";//验货人ID
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ANQUANTIP&& resultCode == RESULT_OK){
            htmlContext=data.getStringExtra("data");
            tvAnquan.setText("点击查看");
        }else if(requestCode==CHOOSE_CAR_TYPE&& resultCode == RESULT_OK){
            isChoose=true;
            ArrayList<String> idList= (ArrayList<String>) data.getSerializableExtra("data");
            nameList= (ArrayList<String>) data.getSerializableExtra("nameList");
            if(idList!=null) {
                carModerId = "";
                String nameString = "";
                for (int i = 0; i < nameList.size(); i++) {
                    if (i == nameList.size() - 1) {
                        nameString += nameList.get(i);
                    } else {
                        nameString += nameList.get(i) + ",";
                    }
                }
                for (int i = 0; i < idList.size(); i++) {
                    if (i == idList.size() - 1) {
                        carModerId += idList.get(i);
                    } else {
                        carModerId += idList.get(i) + ",";
                    }
                }
                tvCarModel.setText(nameString);
            }
        }else if(requestCode==CHOOSE_RECEIVE_OBJ&& resultCode == RESULT_OK){
            isChoose=true;
            if(data.getStringExtra("type").equals("10")){
                ArrayList<String> idList= (ArrayList<String>) data.getSerializableExtra("data");
                nameList= (ArrayList<String>) data.getSerializableExtra("nameList");
                if(idList!=null) {
                    examinelistId = "";
                    String nameString = "";
                    for (int i = 0; i < nameList.size(); i++) {
                        if (i == nameList.size() - 1) {
                            nameString += nameList.get(i);
                        } else {
                            nameString += nameList.get(i) + ",";
                        }
                    }
                    for (int i = 0; i < idList.size(); i++) {
                        if (i == idList.size() - 1) {
                            examinelistId += idList.get(i);
                        } else {
                            examinelistId += idList.get(i) + ",";
                        }
                    }
                    tvexaminelist.setText(nameString);
                }
            }else if(data.getStringExtra("type").equals("9")){
                ArrayList<String> idList= (ArrayList<String>) data.getSerializableExtra("data");
                nameList= (ArrayList<String>) data.getSerializableExtra("nameList");
                if(idList!=null){
                    hzlistId="";
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
                            hzlistId+=idList.get(i);
                        }else {
                            hzlistId+=idList.get(i)+",";
                        }
                    }
                    tvHzList.setText(nameString);
                }
            }else if(data.getStringExtra("type").equals("8")){
                ArrayList<String> idList= (ArrayList<String>) data.getSerializableExtra("data");
                nameList= (ArrayList<String>) data.getSerializableExtra("nameList");
                if(idList!=null){
                    String nameString="";
                    receivObjId="";
                    for(int i=0;i<nameList.size();i++){
                        if(i==nameList.size()-1){
                            nameString+=nameList.get(i);
                        }else {
                            nameString+=nameList.get(i)+",";
                        }
                    }
                    for(int i=0;i<idList.size();i++){
                        if(i==idList.size()-1){
                            receivObjId+=idList.get(i);
                        }else {
                            receivObjId+=idList.get(i)+",";
                        }
                    }
                    tvReceiveObj.setText(nameString);
                }
            }
        }
    }
}
