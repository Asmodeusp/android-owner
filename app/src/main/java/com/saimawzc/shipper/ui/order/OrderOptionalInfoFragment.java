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
import com.saimawzc.shipper.dto.order.creatorder.AuthorityDtoSerializ;
import com.saimawzc.shipper.dto.order.creatorder.DangerousFenceDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.presenter.order.OrderOpintalnfoEditPresenter;
import com.saimawzc.shipper.ui.order.creatorder.DangerousFenceActivity;
import com.saimawzc.shipper.ui.order.creatorder.RelationCompanyActivity;
import com.saimawzc.shipper.ui.order.creatorder.richtext.RichPublishActivity;
import com.saimawzc.shipper.view.order.OrderOptionalInfoView;
import com.saimawzc.shipper.weight.SwitchButton;
import com.saimawzc.shipper.weight.TimeChooseDialogUtil;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.listen.TimeChooseListener;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.saimawzc.shipper.ui.my.identification.CargoOwnerFragment.CHOOSE_COMPANY;


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
    public final static int CHOOSE_COMPANY=12348;
    public final static int DANGEROUS_FENCE=12349;

    @BindView(R.id.rlyh) RelativeLayout rlYh;

    /****启用车型**/
    @BindView(R.id.togglestartcarmodel)SwitchButton swCarModel;
    @BindView(R.id.tvcarmodel)TextView tvCarModel;
    @BindView(R.id.tvanquan)TextView tvAnquan;
    private String carModerId;
    @BindView(R.id.eddriverage)EditText edDriverAge;
    @BindView(R.id.edcarage)EditText edCarAge;
    @BindView(R.id.tvrelationCom)TextView tvRelaCom;//关联公司

    @BindView(R.id.toggleIsWeilansign)SwitchButton isWeiLanSign;

    private UserInfoDto userInfoDto;
    private String id;
    private String orderCode;
    private String businessType;
    private OrderOpintalnfoEditPresenter presenter;
    private String htmlContext;

    /**运输确认**/
    @BindView(R.id.toggletrantorder)SwitchButton toggletrantorder;
    /**入场签到**/
    @BindView(R.id.toggleopenFactorySignIn)SwitchButton toggleopenFactorySignIn;
    /**到货通知**/
    @BindView(R.id.toggleopenArrival)SwitchButton toggleopenArrival;
    /**自动运输**/
    @BindView(R.id.toggleutotrant)SwitchButton toggleAutoTrant;

    @BindView(R.id.edroadLoss)EditText edRoadLoss;

    /**入场签到允许访问相册**/
    @BindView(R.id.toggleInFactoryAlbum)SwitchButton toggleInFactoryAlbum;

    /**装货拍照是否允许访问相册**/
    @BindView(R.id.toggleLoadAlbum)SwitchButton toggleLoadAlbum;
    /**卸货拍照是否允许访问相册**/
    @BindView(R.id.toggleUnloadAlbum)SwitchButton toggleUnloadAlbum;
    /**出厂签到拍照是否允许访问相册**/
    @BindView(R.id.toggleOutFactoryAlbum)SwitchButton toggleOutFactoryAlbum;
    /**到货确认拍照是否允许访问相册**/
    @BindView(R.id.toggleArrivalAlbum)SwitchButton toggleArrivalAlbum;
    /**榜单预警**/
    @BindView(R.id.togglebangdan)SwitchButton togglebangdan;
    /**选择高危围栏**/
    @BindView(R.id.tvdangerousfence)TextView tvDangerousFence;



    @Override
    public int initContentView() {
        return R.layout.tab_order_optional;
    }

    @OnClick({R.id.tvmakeTime,R.id.tvreceiveObj,R.id.tvhzlist,
            R.id.tvexaminelist,R.id.tvanquan,R.id.tvcarmodel,
            R.id.tvrelationCom,R.id.deleterelacom,R.id.tvdangerousfence})
    public void click(View view){
        Bundle bundle;
        switch (view.getId()){
            case R.id.deleterelacom:
                tvRelaCom.setText("");
                relationConId="";
                break;
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
            case R.id.tvrelationCom://关联公司
                readyGoForResult(RelationCompanyActivity.class,CHOOSE_COMPANY);
                break;
            case R.id.tvdangerousfence://高危围栏
                readyGoForResult(DangerousFenceActivity.class,DANGEROUS_FENCE);
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
        isWeiLanSign.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose=isChecked;
            }
        });
        toggleAutoTrant.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
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
    public int inFactoryAlbum() {
        if(toggleInFactoryAlbum.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int loadAlbum() {
        if(toggleLoadAlbum.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int unloadAlbum() {
        if(toggleUnloadAlbum.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int outFactoryAlbum() {
        if(toggleOutFactoryAlbum.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int arrivalAlbum() {
        if(toggleArrivalAlbum.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int poundAlarm() {
        if(togglebangdan.isChecked()){
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
    public String relationCom() {
        return relationConId;
    }

    @Override
    public String roadLoss() {
        return edRoadLoss.getText().toString();
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
                if(dto.getChoose().getFenceClock()==1){
                    isWeiLanSign.setChecked(true);
                }

                if(dto.getChoose().getPoundAlarm()==1){
                    togglebangdan.setChecked(true);
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
                if(dto.getChoose().getAutoTransport()==1){
                    toggleAutoTrant.setChecked(true);
                }

                if(dto.getChoose().getAlarmTime()!=0){
                    edStayTime.setText(dto.getChoose().getAlarmTime()+"");
                }

                if(dto.getChoose().getOpenTransport()==2){
                    toggletrantorder.setChecked(false);
                }
                if(dto.getChoose().getOpenFactorySignIn()==2){
                    toggleopenFactorySignIn.setChecked(false);
                }
                if(dto.getChoose().getOpenArrival()==2){
                    toggleopenArrival.setChecked(false);
                }

                if(dto.getChoose().getInFactoryAlbum()==1){
                    toggleInFactoryAlbum.setChecked(true);
                }
                if(dto.getChoose().getLoadAlbum()==1){
                    toggleLoadAlbum.setChecked(true);
                }
                if(dto.getChoose().getUnloadAlbum()==1){
                    toggleUnloadAlbum.setChecked(true);
                }
                if(dto.getChoose().getOutFactoryAlbum()==1){
                    toggleOutFactoryAlbum.setChecked(true);
                }
                if(dto.getChoose().getArrivalAlbum()==1){
                    toggleArrivalAlbum.setChecked(true);
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
                tvRelaCom.setText(dto.getChoose().getRelationComName());
                relationConId=dto.getChoose().getRelationCom();
                edRoadLoss.setText(dto.getChoose().getRoadLoss());
                fenceId=dto.getChoose().getHighEnclosureId();
                tvDangerousFence.setText(dto.getChoose().getHighEnclosureName());

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
    public int fenceClock() {
        if(isWeiLanSign.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int openTransport() {
        if(toggletrantorder.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int openFactorySignIn() {
        if(toggleopenFactorySignIn.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int openArrival() {
        if(toggleopenArrival.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int autoTransport() {
        if(toggleAutoTrant.isChecked()){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public String highEnclosureId() {
        return fenceId;
    }

    @Override
    public String highEnclosureName() {
        return tvDangerousFence.getText().toString();
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
    private String relationConId="";//关联公司ID
    private String fenceId="";//高危围栏Id

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==DANGEROUS_FENCE&& resultCode == RESULT_OK){
            DangerousFenceDto fenceDto= (DangerousFenceDto) data.getSerializableExtra("data");
            if(fenceDto!=null&&!TextUtils.isEmpty(fenceDto.getEnclosureName())){
                if(!tvDangerousFence.getText().toString().contains(fenceDto.getEnclosureName())){
                    tvDangerousFence.setText(tvDangerousFence.getText().toString()+fenceDto.getEnclosureName()+",");
                    fenceId=fenceId+fenceDto.getId()+",";
                }
            }

        }else if(requestCode==CHOOSE_COMPANY&& resultCode == RESULT_OK){
            AuthorityDtoSerializ  authorityDto = (AuthorityDtoSerializ) data.getSerializableExtra("data");
            if(authorityDto!=null){
                if(!tvRelaCom.getText().toString().contains(authorityDto.getCompanyName())){
                    tvRelaCom.setText(tvRelaCom.getText().toString()+authorityDto.getCompanyName()+",");
                    relationConId=relationConId+authorityDto.getId()+",";
                }
            }
        } else if(requestCode==ANQUANTIP&& resultCode == RESULT_OK){
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
