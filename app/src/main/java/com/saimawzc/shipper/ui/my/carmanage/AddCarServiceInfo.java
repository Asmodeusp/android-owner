package com.saimawzc.shipper.ui.my.carmanage;

import static com.saimawzc.shipper.ui.my.identification.CargoOwnerFragment.REQUEST_CODE_PIC;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.constants.Constants;
import com.saimawzc.shipper.dto.carleader.CarServiceSfInfoDto;
import com.saimawzc.shipper.dto.carleader.EditTaxaDto;
import com.saimawzc.shipper.dto.carleader.FaceQueryDto;
import com.saimawzc.shipper.dto.carleader.SearchTeamDto;
import com.saimawzc.shipper.dto.carleader.TaxiAreaDto;
import com.saimawzc.shipper.dto.pic.PicDto;
import com.saimawzc.shipper.presenter.mine.carleader.CarServicePresenter;
import com.saimawzc.shipper.view.mine.carleader.CarServiceView;
import com.saimawzc.shipper.weight.RepeatClickUtil;
import com.saimawzc.shipper.weight.utils.FileUtil;
import com.saimawzc.shipper.weight.utils.GalleryUtils;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.loadimg.ImageLoadUtil;
import com.saimawzc.shipper.weight.utils.ocr.RecognizeService;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class AddCarServiceInfo extends BaseActivity implements CarServiceView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.imgidpositive)
    ImageView imgPositive;
    @BindView(R.id.imgidotherside) ImageView imgOtherside;
    @BindView(R.id.eduser)
    EditText edUser;
    @BindView(R.id.edIdcard)EditText edIdcard;
    @BindView(R.id.edBankNo)EditText edBankNo;
    @BindView(R.id.edBankName)EditText edBankName;
    @BindView(R.id.edphone)EditText edPhone;


    @BindView(R.id.groupblue)
    RadioButton radioCarnoBlue;
    @BindView(R.id.groupyellow) RadioButton  radioCarnoYellow;
    @BindView(R.id.edcarusername)EditText edCarUserName;//????????????
    @BindView(R.id.edcarNo)EditText edCarNo;

    @BindView(R.id.carmodel) EditText tvModel;
    @BindView(R.id.tvcarbran)EditText tvCarBand;
    @BindView(R.id.tvcaradress)
    TextView tvCarAdress;
    @BindView(R.id.edcarLength)EditText edCarLength;
    @BindView(R.id.edcarWith)EditText edcarWith;
    @BindView(R.id.edcarHight)EditText edCarHight;
    @BindView(R.id.edcarWeight)EditText edCarWeight;
    @BindView(R.id.imgdependon)ImageView imgDepenDon;
    @BindView(R.id.imgCarPic)ImageView imgCarPic;
    @BindView(R.id.driverlicensefront)ImageView imgDriverLicenFront;
    @BindView(R.id.driverlicenseback)ImageView imgDriveLicenBack;
    @BindView(R.id.edcarjsz)EditText edDriverNo;
    @BindView(R.id.imgxszFront)ImageView imgXszFront;
    @BindView(R.id.imgxszback)ImageView imgXszBack;
    @BindView(R.id.edcarxsz)EditText edXszNo;


    private String stringPosition;
    private String stringOtherSide;
    private String strinDepenDon="";
    private String stringCarPic;
    private String stringDriverLicenFront;
    private String stringDriveLicenBack;
    private String stringXszFront;
    private String stringXszBack;
    @BindView(R.id.group) RadioGroup groupBtn;
    @BindView(R.id.groupyes)RadioButton radioYes;
    @BindView(R.id.groupno)RadioButton radioNo;
    @BindView(R.id.edreal_user)EditText edreal_user;
    @BindView(R.id.edreal_user_phone) EditText edRealUserPhone;
    @BindView(R.id.llparent)
    NestedScrollView llParent;
    @BindView(R.id.tvorder)
    TextView tvSubmit;



    /**??????????????????????????????***/
    private String teamId;
    private SearchTeamDto teamDto;
    private String id;
    private int status=-1;

    @BindView(R.id.llguakao)
    LinearLayout llGuakao;
    @BindView(R.id.tvguakao)
    TextView tvGuakao;



    private String csName="";
    private CarServicePresenter presenter;
    @OnClick({R.id.imgidpositive,R.id.bankscan,R.id.imgidotherside,
            R.id.llchoosecx,R.id.llcarbrnd,R.id.imgdependon,R.id.tvcaradress,R.id.imgCarPic
            ,R.id.driverlicensefront,R.id.driverlicenseback,R.id.imgxszFront,R.id.imgxszback,R.id.tvorder})
    public void click(View view){
        switch (view.getId()){
            case R.id.imgidpositive:
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("????????????????????????,???????????????????????????");
                    permissionChecker.requestPermissions();
                    return;
                }
                initCamera(CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                break;
            case R.id.imgidotherside:
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("????????????????????????,???????????????????????????");
                    permissionChecker.requestPermissions();
                    return;
                }
                initCamera(CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                break;
            case R.id.bankscan:
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("????????????????????????,???????????????????????????");
                    permissionChecker.requestPermissions();
                    return;
                }
                initCamera(CameraActivity.CONTENT_TYPE_BANK_CARD);
                break;
            case R.id.llchoosecx:
                presenter.getCarType();
                break;
            case R.id.llcarbrnd:
                if(TextUtils.isEmpty(tvModel.getText().toString())){
                    context.showMessage("?????????????????????");
                    return;
                }
                presenter.getCarBrand(tvModel.getText().toString());
                break;
            case R.id.tvcaradress:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("????????????????????????????????????");
                    return;
                }
                readyGoForResult(ChooseTaxiAdressActivity.class,100);
                break;
            case R.id.imgdependon://????????????
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("????????????????????????,???????????????????????????");
                    permissionChecker.requestPermissions();
                    return;
                }
                presenter.showCamera(mContext,0);
                break;
            case R.id.imgCarPic:
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("????????????????????????,???????????????????????????");
                    permissionChecker.requestPermissions();
                    return;
                }
                presenter.showCamera(mContext,1);
                break;
            case R.id.driverlicensefront:
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("????????????????????????,???????????????????????????");
                    permissionChecker.requestPermissions();
                    return;
                }
                presenter.showCamera(mContext,2);
                break;
            case R.id.driverlicenseback:
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("????????????????????????,???????????????????????????");
                    permissionChecker.requestPermissions();
                    return;
                }
                presenter.showCamera(mContext,3);
                break;
            case R.id.imgxszFront:
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("????????????????????????,???????????????????????????");
                    permissionChecker.requestPermissions();
                    return;
                }
                presenter.showCamera(mContext,4);
                break;
            case R.id.imgxszback:
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("????????????????????????,???????????????????????????");
                    permissionChecker.requestPermissions();
                    return;
                }
                presenter.showCamera(mContext,5);
                break;
            case R.id.tvorder:
                if(TextUtils.isEmpty(stringPosition)){
                    context.showMessage("??????????????????????????????");
                    return;
                }
                if(TextUtils.isEmpty(stringOtherSide)){
                    context.showMessage("??????????????????????????????");
                    return;
                }
                if(context.isEmptyStr(edUser)){
                    context.showMessage("???????????????");
                    return;
                }
                if(context.isEmptyStr(edIdcard)){
                    context.showMessage("?????????????????????");
                    return;
                }
                if(context.isEmptyStr(edBankName)){
                    context.showMessage("??????????????????");
                    return;
                }
                if(context.isEmptyStr(edBankNo)){
                    context.showMessage("?????????????????????");
                    return;
                }
                if(context.isEmptyStr(edPhone)){
                    context.showMessage("?????????????????????");
                    return;
                }
                if(context.isEmptyStr(edCarUserName)){
                    context.showMessage("?????????????????????");
                    return;
                }
                if(context.isEmptyStr(edreal_user)){
                    context.showMessage("????????????????????????");
                    return;
                }
                if(context.isEmptyStr(edRealUserPhone)){
                    context.showMessage("????????????????????????????????????");
                    return;
                }
                if(context.isEmptyStr(edCarNo)){
                    context.showMessage("??????????????????");
                    return;
                }
                if(context.isEmptyStr(tvModel)){
                    context.showMessage("?????????????????????");
                    return;
                }
                if(context.isEmptyStr(tvCarBand)){
                    context.showMessage("?????????????????????");
                    return;
                }
                if(context.isEmptyStr(tvCarAdress)){
                    context.showMessage("????????????????????????");
                    return;
                }
                if(context.isEmptyStr(edCarLength)){
                    context.showMessage("?????????????????????");
                    return;
                }
                if(context.isEmptyStr(edcarWith)){
                    context.showMessage("?????????????????????");
                    return;
                }
                if(context.isEmptyStr(edCarHight)){
                    context.showMessage("?????????????????????");
                    return;
                }
                if(context.isEmptyStr(edCarWeight)){
                    context.showMessage("?????????????????????");
                    return;
                }
                if(!TextUtils.isEmpty(isGuakao)){
                    if(isGuakao.equals("???")){
                        if(TextUtils.isEmpty(strinDepenDon)){
                            context.showMessage("???????????????????????????");
                            return;
                        }
                    }
                }
                if(TextUtils.isEmpty(stringDriverLicenFront)){
                    context.showMessage("??????????????????????????????");
                    return;
                }
                if(TextUtils.isEmpty(stringDriveLicenBack)){
                    context.showMessage("??????????????????????????????");
                    return;
                }
                if(context.isEmptyStr(edDriverNo)){
                    context.showMessage("????????????????????????");
                    return;
                }
                if(TextUtils.isEmpty(stringXszFront)){
                    context.showMessage("??????????????????????????????");
                    return;
                }
                if(TextUtils.isEmpty(stringXszBack)){
                    context.showMessage("??????????????????????????????");
                    return;
                }
                if(context.isEmptyStr(edXszNo)){
                    context.showMessage("????????????????????????");
                    return;
                }
                presenter.submit();
                break;
        }
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_addcar_service;
    }


    FaceQueryDto.Facedata facedata;
    private String isGuakao="???";

    @Override
    protected void init() {
        initpermissionChecker();
        userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
        setToolbar(toolbar,"???????????????");
        presenter=new CarServicePresenter(this,this);
        facedata= (FaceQueryDto.Facedata) getIntent().getSerializableExtra("data");
         id=getIntent().getStringExtra("id");
        teamId=getIntent().getStringExtra("teamId");
        teamDto= (SearchTeamDto) getIntent().getSerializableExtra("persondata");
        if(facedata==null){
            if(teamDto!=null){
                presenter.getSfinfo(teamDto.getCarId(),teamId,teamDto.getSjId());
            }
        }
        if(facedata!=null){
            status=getIntent().getIntExtra("status",-1);
            if(status==1){
                findAllEdittexts(llParent);
                tvSubmit.setVisibility(View.GONE);
            }
            if(!TextUtils.isEmpty(facedata.getSmrzfjBos())){
                String[] sfzNode = facedata.getSmrzfjBos().split(",");
                if(sfzNode!=null&&sfzNode.length>0){
                    ImageLoadUtil.displayImage(mContext,sfzNode[0],imgPositive);
                    imgPositive.setClickable(false);
                    stringPosition=sfzNode[0];
                    if(sfzNode.length>=2){
                        ImageLoadUtil.displayImage(mContext,sfzNode[1],imgOtherside);
                        stringOtherSide=sfzNode[1];
                        imgOtherside.setClickable(false);
                    }
                }
            }
            edUser.setText(facedata.getKhrxm());
            edUser.setEnabled(false);
            edIdcard.setText(facedata.getKhrsfzjhm());
            edIdcard.setEnabled(false);
            edBankNo.setText(facedata.getKhyh());
            edPhone.setText(facedata.getYddh());
            //????????????
            edCarUserName.setText(facedata.getClsyr());
            edCarNo.setText(facedata.getCycph());
            tvModel.setText(facedata.getCycx());
            tvCarBand.setText(facedata.getCypp());
            tvCarAdress.setText(facedata.getSsdqStr());
            csName=facedata.getSsdq();
            if(!TextUtils.isEmpty(facedata.getCycc())){
                if(!facedata.getCycc().contains("*")){
                    edCarLength.setText(facedata.getCycc());
                }else {
                    String[] carInfo=facedata.getCycc().split("\\*");
                    if(carInfo==null||carInfo.length==0){
                    }else {
                        if(carInfo.length==1){
                            edCarLength.setText(carInfo[0]);
                        }else  if(carInfo.length==2){
                            edCarLength.setText(carInfo[0]);
                            edcarWith.setText(carInfo[1]);
                        }else if(carInfo.length==3){
                            edCarLength.setText(carInfo[0]);
                            edcarWith.setText(carInfo[1]);
                            edCarHight.setText(carInfo[2]);
                        }

                    }

                }
            }
            edCarWeight.setText(facedata.getCyzz());
            edreal_user.setText(facedata.getCycsjsyr());
            edRealUserPhone.setText(facedata.getCycsyrlxfs());
            //???????????? cycsfgk
            if(!TextUtils.isEmpty(facedata.getCycsfgk())){
                if(facedata.getCycsfgk().equals("???")){
                    isGuakao="???";
                    radioYes.setChecked(true);
                    strinDepenDon = facedata.getQysmgkfjBos();
                    llGuakao.setVisibility(View.VISIBLE);
                    tvGuakao.setVisibility(View.VISIBLE);
                }else {
                    isGuakao="???";
                    radioYes.setChecked(false);
                    strinDepenDon = facedata.getCzsmgkfjBos();
                    llGuakao.setVisibility(View.GONE);
                    tvGuakao.setVisibility(View.GONE);
                }

            }
            ImageLoadUtil.displayImage(mContext,strinDepenDon,imgDepenDon);
            ImageLoadUtil.displayImage(mContext,facedata.getCyczpfjBos(),imgCarPic);
            stringCarPic=facedata.getCyczpfjBos();
            edBankName.setText(facedata.getKhyh());
            edBankNo.setText(facedata.getSkzh());
            //?????????
            edDriverNo.setText(facedata.getJszjhm());
            if(!TextUtils.isEmpty(facedata.getJszfjBos())){
                String[] jszNode = facedata.getJszfjBos().split(",");
                if(jszNode!=null&&jszNode.length>0){
                    ImageLoadUtil.displayImage(mContext,jszNode[0],imgDriverLicenFront);
                    stringDriverLicenFront=jszNode[0];
                    if(jszNode.length>=2){
                        ImageLoadUtil.displayImage(mContext,jszNode[1],imgDriveLicenBack);
                        stringDriveLicenBack=jszNode[1];
                    }
                }
            }
            //?????????
            edXszNo.setText(facedata.getXszjhm());

            if(!TextUtils.isEmpty(facedata.getCycxszfjBos())){
                String[] xszNode = facedata.getCycxszfjBos().split(",");
                if(xszNode!=null){
                    ImageLoadUtil.displayImage(mContext,xszNode[0],imgXszFront);
                    stringXszFront=xszNode[0];
                    if(xszNode.length>=2&&xszNode.length>0){
                        ImageLoadUtil.displayImage(mContext,xszNode[1],imgXszBack);
                        stringXszBack=xszNode[1];
                    }
                }
            }
        }
    }

    @Override
    protected void initListener() {
        groupBtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.groupyes:
                        isGuakao="???";
                        llGuakao.setVisibility(View.VISIBLE);
                        tvGuakao.setVisibility(View.VISIBLE);
                        break;
                    case R.id.groupno:
                        isGuakao="???";
                        llGuakao.setVisibility(View.GONE);
                        tvGuakao.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }
    FunctionConfig functionConfig;
    private int chooseTpye;
    @Override
    public void OnDealCamera(int type) {
        chooseTpye=type;
        if(type<=5){//???????????????
            showCameraAction();
        }else if(type>5&&type<100) {
            if(functionConfig==null){
                functionConfig= GalleryUtils.getFbdtFunction(1);
            }
            GalleryFinal.openGalleryMuti(1001,
                    functionConfig, mOnHanlderResultCallback);
        }
    }

    @Override
    public String carId() {
        if(teamDto!=null){
            return teamDto.getCarId();
        }
        return "";
    }

    @Override
    public String clsyr() {
        return edCarUserName.getText().toString();
    }

    @Override
    public String cycc() {
        return edCarLength.getText().toString()+"*"+edcarWith.getText().toString()+"*"+
                edCarHight.getText().toString();
    }

    @Override
    public String cycph() {
        return edCarNo.getText().toString();
    }

    @Override
    public String cycx() {
        return tvModel.getText().toString();
    }

    @Override
    public String cycxszfjBos() {
        return stringXszFront+","+stringXszBack;
    }

    @Override
    public String cyczpfjBos() {
        return stringCarPic;
    }

    @Override
    public String cypp() {
        return tvCarBand.getText().toString();
    }

    @Override
    public String cyzz() {
        return edCarWeight.getText().toString();
    }

    @Override
    public String fwfsfzjhm() {
        return edIdcard.getText().toString();
    }

    @Override
    public String fwfxm() {
        return edUser.getText().toString();
    }

    @Override
    public String jszfjBos() {
        return stringDriverLicenFront+","+stringDriveLicenBack;
    }
    @Override
    public String jszjhm() {
        return edDriverNo.getText().toString();
    }

    @Override
    public String khyh() {
        return edBankName.getText().toString();
    }
    @Override
    public String qysmgkfjBos() {
        if(isGuakao.equals("???")) {
            return strinDepenDon;
        }else {
            return "";
        }
    }
    @Override
    public String role() {
        if(userInfoDto!=null){
            return userInfoDto.getRole()+"";
        }
        return "";
    }
    @Override
    public String roleId() {
        if(userInfoDto!=null){
            return userInfoDto.getRoleId()+"";
        }
        return "";
    }
    @Override
    public String smrzfjBos() {
        return stringPosition+","+stringOtherSide;
    }
    @Override
    public String xszjhm() {
        return edXszNo.getText().toString();
    }
    @Override
    public String yddh() {
        return edPhone.getText().toString();
    }
    @Override
    public String czsmgkfjBos() {
        if(this.isGuakao.equals("???")) {
            return "";
        }else {
            return this.strinDepenDon;
        }
    }
    @Override
    public String fwfsfzjlx() {
        if(facedata != null) {
            return facedata.getFwfsfzjlx();
        }else {
            return "";
        }
    }

    @Override
    public String fwfuuid() {
        if(facedata != null) {
            return facedata.getFwfuuid();
        }else {
            return "";
        }
    }
    @Override
    public String cycsfgk() {
        return isGuakao;
    }

    @Override
    public String cycsjsyr() {
        return edreal_user.getText().toString();
    }

    @Override
    public String cycsyrlxfs() {
        return edRealUserPhone.getText().toString();
    }
    @Override
    public String skzh() {
        return edBankNo.getText().toString();
    }

    @Override
    public String ssdq() {
        return csName;
    }
    @Override
    public String khrsfzjhm() {
        return edIdcard.getText().toString();
    }

    @Override
    public String khrxm() {
        return edUser.getText().toString();
    }

    @Override
    public String ptzcsj() {
        if(facedata != null) {
            return facedata.getPtzcsj();
        }else {
            return BaseActivity.getCurrentTime("yyyy-MM-dd HH:mm:ss");
        }
    }


    @Override
    public void getData(EditTaxaDto dto) {
        if(dto!=null){
            if(dto.isSuccess()){
                Intent intent=new Intent();
                context.setResult(RESULT_OK, intent);
                context.finish();
                EventBus.getDefault().post(Constants.reshService);
            }else {
                context.showMessage(dto.getMessage());
            }
        }

    }

    @Override
    public void successful(int type) {

    }

    @Override
    public String cdzId() {
        return teamId;
    }

    @Override
    public void carBrandName(String carBrandName) {
        tvCarBand.setText(carBrandName);

    }

    @Override
    public void carBrandid(String carBrandId) {

    }

    @Override
    public void carTypeName(String carBrandName) {
        tvModel.setText(carBrandName);
        tvCarBand.setText("");
    }

    @Override
    public void carTypeId(String carBrandId) {

    }

    @Override
    public String sjId() {
        if(teamDto!=null){
            return teamDto.getSjId();
        }
        return "";
    }

    @Override
    public void getSfInfo(CarServiceSfInfoDto sfInfo) {
        if(sfInfo!=null){
            if(!TextUtils.isEmpty(sfInfo.getSfzzm())){
                ImageLoadUtil.displayImage(this,sfInfo.getSfzzm(),imgPositive);
                imgPositive.setClickable(false);
                stringPosition=sfInfo.getSfzzm();
            }
            if(!TextUtils.isEmpty(sfInfo.getSfzfm())){
                ImageLoadUtil.displayImage(this,sfInfo.getSfzfm(),imgOtherside);
                imgOtherside.setClickable(false);
                stringOtherSide=sfInfo.getSfzfm();
            }
            if(!TextUtils.isEmpty(sfInfo.getKhrxm())){
                edUser.setText(sfInfo.getKhrxm());
                edUser.setEnabled(false);
            }
            if(!TextUtils.isEmpty(sfInfo.getKhrsfzjhm())){
                edIdcard.setText(sfInfo.getKhrsfzjhm());
                edIdcard.setEnabled(false);
            }
            edBankName.setText(sfInfo.getKhyh());
            edBankNo.setText(sfInfo.getSkzh());
            edPhone.setText(sfInfo.getYddh());
            edCarNo.setText(sfInfo.getCycph());
            tvModel.setText(sfInfo.getCycx());
            tvCarBand.setText(sfInfo.getCypp());
            edCarUserName.setText(sfInfo.getClsyr());
            edreal_user.setText(sfInfo.getCycsjsyr());
            edDriverNo.setText(sfInfo.getJszjhm());
            edRealUserPhone.setText(sfInfo.getCycsyrlxfs());
            if(!TextUtils.isEmpty(sfInfo.getCycc())){
                if(!sfInfo.getCycc().contains("*")){
                    edCarLength.setText(sfInfo.getCycc());
                    Log.e("msg","???????????????"+sfInfo.getCycc());
                }else {
                    String[] carInfo=sfInfo.getCycc().split("\\*");
                    if(carInfo==null||carInfo.length==0){
                        Log.e("msg","??????wei 0"+sfInfo.getCycc());
                    }else {
                        Log.e("msg","??????"+carInfo.length);
                        if(carInfo.length==1){
                            edCarLength.setText(carInfo[0]);
                        }else  if(carInfo.length==2){
                            edCarLength.setText(carInfo[0]);
                            edcarWith.setText(carInfo[1]);
                        }else if(carInfo.length==3){
                            edCarLength.setText(carInfo[0]);
                            edcarWith.setText(carInfo[1]);
                            edCarHight.setText(carInfo[2]);
                        }
                    }
                }
            }
            edCarWeight.setText(sfInfo.getCyzz());
            if(!TextUtils.isEmpty(sfInfo.getCyczpfjBos())){
                ImageLoadUtil.displayImage(mContext,sfInfo.getCyczpfjBos(),imgCarPic);
                stringCarPic=sfInfo.getCyczpfjBos();
            }
            edDriverNo.setText(sfInfo.getJszjhm());
            if(!TextUtils.isEmpty(sfInfo.getCycxszfjBos())){
                if(sfInfo.getCycxszfjBos().contains(",")){
                    String[] xszNode = sfInfo.getCycxszfjBos().split(",");
                    if(xszNode!=null){
                        ImageLoadUtil.displayImage(mContext,xszNode[0],imgXszFront);
                        stringXszFront=xszNode[0];
                        if(xszNode.length>=2&&xszNode.length>0){
                            ImageLoadUtil.displayImage(mContext,xszNode[1],imgXszBack);
                            stringXszBack=xszNode[1];
                        }
                    }
                }
            }
            if(!TextUtils.isEmpty(sfInfo.getJszfjBos())){
                if(sfInfo.getJszfjBos().contains(",")){
                    String[] jszNode = sfInfo.getJszfjBos().split(",");
                    if(jszNode!=null&&jszNode.length>0){
                        ImageLoadUtil.displayImage(mContext,jszNode[0],imgDriverLicenFront);
                        stringDriverLicenFront=jszNode[0];
                        if(jszNode.length>=2){
                            ImageLoadUtil.displayImage(mContext,jszNode[1],imgDriveLicenBack);
                            stringDriveLicenBack=jszNode[1];
                        }
                    }
                }

            }
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public String id() {
        return id;
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
        EventBus.getDefault().post(Constants.reshTeamDelation);
        finish();

    }

    TaxiAreaDto areaDto;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100 && resultCode == RESULT_OK){
            areaDto= (TaxiAreaDto) data.getSerializableExtra("data");
            if(areaDto!=null){
                csName=areaDto.getId();
                tvCarAdress.setText(areaDto.getName());
            }
        } else if(requestCode==REQUEST_CODE_CAMERA && resultCode == RESULT_OK){//??????
            Uri uri = Uri.fromFile(new File(tempImage));
            final File file=BaseActivity.compress(mContext,new File(tempImage));
            if(chooseTpye==0){//
                ImageLoadUtil.displayImage(mContext, uri, imgDepenDon);
            }else if(chooseTpye==1){
                ImageLoadUtil.displayImage(mContext, uri, imgCarPic);
            }else if(chooseTpye==2){
                ImageLoadUtil.displayImage(mContext, uri, imgDriverLicenFront);
            } else if(chooseTpye==3){
                ImageLoadUtil.displayImage(mContext, uri, imgDriveLicenBack);
            }else if(chooseTpye==4){
                ImageLoadUtil.displayImage(mContext, uri, imgXszFront);
            }else if(chooseTpye==5){
                ImageLoadUtil.displayImage(mContext, uri, imgXszBack);
            }
            Uploadpic(file);
        }else if(requestCode==REQUEST_CODE_PIC && resultCode == RESULT_OK){
            String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
            String filePath = FileUtil.getSaveFile(mContext,tempImage).getAbsolutePath();
            if(!TextUtils.isEmpty(filePath)&&new File(filePath)!=null){
                File file=BaseActivity.compress(mContext,new File(filePath));
                if(CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)){
                    chooseTpye=100;
                    recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    ImageLoadUtil.displayLocalImage(mContext, new File(filePath), imgPositive);
                    Uploadpic(file);
                }else if(CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)){
                    Log.e("msg","");
                    chooseTpye=101;
                    ImageLoadUtil.displayLocalImage(mContext, new File(filePath), imgOtherside);
                    Uploadpic(file);
                }else if(CameraActivity.CONTENT_TYPE_BANK_CARD.equals(contentType)){
                    if(!TextUtils.isEmpty(filePath)&&new File(filePath)!=null){
                        RecognizeService.recBankCard(mContext.getApplicationContext(), filePath, new RecognizeService.ServiceListener() {
                            @Override
                            public void onResult(String result) {
                                Log.e("msg",result);
                                try {
                                    JSONObject jsonObject=new JSONObject(result);
                                    JSONObject res=new JSONObject(jsonObject.getString("result"));
                                    edBankNo.setText(res.getString("bank_card_number"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }

            }
        }
    }

    private  void Uploadpic(File file){
        context.showLoadingDialog("???????????????...");
        File temp=BaseActivity.compress(mContext,file);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), temp);
        //files ????????????
        MultipartBody.Part part=
                MultipartBody.Part.createFormData("picture", temp.getName(), requestBody);
        context.authApi.loadImg(part).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
                Log.e("msg","chooseType="+chooseTpye);
                context.dismissLoadingDialog();
                if(chooseTpye==0||chooseTpye==6){
                    strinDepenDon=response.getUrl();
                    context.showMessage("????????????????????????");
                }else if(chooseTpye==1||chooseTpye==7){
                    stringCarPic=response.getUrl();
                    context.showMessage("????????????????????????");
                } else if(chooseTpye==2||chooseTpye==8){
                    stringDriverLicenFront=response.getUrl();
                    context.showMessage("???????????????????????????");
                }else if(chooseTpye==3||chooseTpye==9){
                    stringDriveLicenBack=response.getUrl();
                    context.showMessage("???????????????????????????");
                }else if(chooseTpye==4||chooseTpye==10){
                    stringXszFront=response.getUrl();
                    context.showMessage("???????????????????????????");
                }else if(chooseTpye==5||chooseTpye==11){
                    stringXszBack=response.getUrl();
                    context.showMessage("???????????????????????????");
                }else if(chooseTpye==100){
                    stringPosition=response.getUrl();
                    context.showMessage("???????????????????????????");
                }else if(chooseTpye==101){
                    stringOtherSide=response.getUrl();
                    context.showMessage("???????????????????????????");
                }
            }
            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
                context.dismissLoadingDialog();
            }
        });
    }

    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // ????????????????????????
        param.setIdCardSide(idCardSide);
        // ??????????????????
        param.setDetectDirection(true);
        // ??????????????????????????????0-100, ??????????????????????????????????????????????????? ????????????????????????20
        param.setImageQuality(20);
        OCR.getInstance(mContext).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {

                    if(result.getIdNumber()==null){
                        context.showMessage("?????????????????????");
                        return;
                    }
                    if(result.getName()==null){
                        context.showMessage("??????????????????");
                        return;
                    }
                    Log.e("msg","??????"+result.getName().toString());
                    if(!TextUtils.isEmpty(result.getIdNumber().toString())){
                        edIdcard.setText(result.getIdNumber().toString());
                    }
                    if(!TextUtils.isEmpty(result.getName().toString())){
                        edUser.setText(result.getName().toString());

                    }
                }
            }
            @Override
            public void onError(OCRError error) {
                context.showMessage( error.getMessage());
            }
        });
    }
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback =
            new GalleryFinal.OnHanlderResultCallback() {
                @Override
                public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
                    if (resultList != null) {
                        Uri uri = Uri.fromFile(new File(resultList.get(0).getPhotoPath()));
                        if(chooseTpye==6){//
                            ImageLoadUtil.displayImage(mContext, uri, imgDepenDon);

                        }
                        if(chooseTpye==7){//
                            ImageLoadUtil.displayImage(mContext, uri, imgCarPic);
                        }
                        if(chooseTpye==8){//???????????????
                            ImageLoadUtil.displayImage(mContext, uri, imgDriverLicenFront);

                        }
                        if(chooseTpye==9){//????????????
                            ImageLoadUtil.displayImage(mContext, uri, imgDriveLicenBack);

                        }
                        if(chooseTpye==10){//?????????????????????
                            ImageLoadUtil.displayImage(mContext, uri, imgXszFront);
                        }
                        if(chooseTpye==11){//???????????????
                            ImageLoadUtil.displayImage(mContext, uri, imgXszBack);
                        }

                        Uploadpic(BaseActivity.compress(mContext,new File(resultList.get(0).getPhotoPath())));
                    }
                }
                @Override
                public void onHanlderFailure(int requestCode, String errorMsg) {
                    context.showMessage(errorMsg);
                }
            };
    @Override
    public void onDestroy() {
        super.onDestroy();
        GalleryFinal.OnHanlderResultCallback back = GalleryFinal.getCallback();
        back=null;
        functionConfig=null;

    }
    private void findAllEdittexts(ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup)
                findAllEdittexts((ViewGroup) view);
            else if (view instanceof EditText) {
                view.setEnabled(false);
            }else if(view instanceof ImageView){
                view.setClickable(false);
            }
        }

    }
}
