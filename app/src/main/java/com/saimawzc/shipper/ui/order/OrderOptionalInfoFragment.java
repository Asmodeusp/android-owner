package com.saimawzc.shipper.ui.order;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;

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


/***
 * 选填信息
 * **/
public class OrderOptionalInfoFragment extends BaseFragment
        implements OrderOptionalInfoView {

    boolean isChoose = false;//是否有操作
    @BindView(R.id.edksNum)
    EditText edksNum;
    @BindView(R.id.tvmakeTime)
    TextView tvmakeTime;
    @BindView(R.id.toggleIszc)
    SwitchButton switchZc;
    @BindView(R.id.togglexh)
    SwitchButton switchXh;
    @BindView(R.id.togglefp)
    SwitchButton switchFp;
    @BindView(R.id.toggleYh)
    SwitchButton toggleYh;
    @BindView(R.id.togglepz)
    SwitchButton togglezhPz;
    //装货拍照
    @BindView(R.id.togglexhpz)
    SwitchButton togglexhpz;
    //卸货拍照
    @BindView(R.id.tvxy)
    TextView tvxy;//付款协议
    @BindView(R.id.edmark)
    EditText edMark;//备注
    private TimeChooseDialogUtil timeChooseDialogUtil;
    @BindView(R.id.tvMakePeople)
    EditText tvMakePeople;
    private String from;
    /**
     * 出厂榜单
     **/
    @BindView(R.id.togglebz)
    SwitchButton swBangBan;
    /**
     * 偏离预警
     **/
    @BindView(R.id.togglepl)
    SwitchButton swDeviate;
    /**
     * 偏离预警下布局
     **/
    @BindView(R.id.toggleplLinear)
    LinearLayout swDeviateLinear;
    /**
     * 偏离预警站内接收人
     **/
    @BindView(R.id.toggleplZNObj)
    TextView toggleplZNObj;
    /**
     * 偏离预警短信接收人
     **/
    @BindView(R.id.toggleplDXObj)
    TextView toggleplDXObj;
    /***
     * 偏离预警货主列表
     */
    @BindView(R.id.toggleplTvhzlist)
    TextView toggleplTvhzlist;
    /**
     * 离线预警
     **/
    @BindView(R.id.toggleoffline)
    SwitchButton swOffline;
    /**
     * 离线预警下布局
     **/
    @BindView(R.id.toggleofflineLinear)
    LinearLayout swOfflineLinear;
    /**
     * 离线预警站内接收人
     **/
    @BindView(R.id.toggleoffZNObj)
    TextView toggleoffZNObj;
    /**
     * 离线预警短信接收人
     **/
    @BindView(R.id.toggleoffDXObj)
    TextView toggleoffDXObj;
    /***
     * 离线预警货主列表
     */
    @BindView(R.id.toggleoffTvhzlist)
    TextView toggleoffTvhzlist;

    /**
     * 榜单预警
     **/
    @BindView(R.id.togglebangdan)
    SwitchButton togglebangdan;
    /**
     * 榜单预警下布局
     **/
    @BindView(R.id.togglebangdanLinear)
    LinearLayout togglebangdanLinear;
    /**
     * 磅单预警站内接收人
     **/
    @BindView(R.id.togglebangdanZNObj)
    TextView togglebangdanZNObj;
    /**
     * 磅单预警短信接收人
     **/
    @BindView(R.id.togglebangdanDXObj)
    TextView togglebangdanDXObj;
    /***
     * 磅单预警货主列表
     */
    @BindView(R.id.togglebangdanTvhzlist)
    TextView togglebangdanTvhzlist;
    /**
     * 停留预警
     **/
    @BindView(R.id.togglestay)
    SwitchButton swStay;
    /**
     * 停留预警下布局
     **/
    @BindView(R.id.togglestayLinear)
    LinearLayout swStayLinear;
    /**
     * 停留预警站内接收人
     **/
    @BindView(R.id.togglestayZNObj)
    TextView togglestayZNObj;
    /**
     * 停留预警短信接收人
     **/
    @BindView(R.id.togglestayDXObj)
    TextView togglestayDXObj;
    /***
     * 停留预警货主列表
     */
    @BindView(R.id.togglestayTvhzlist)
    TextView togglestayTvhzlist;
    /**
     * 选择高危围栏
     **/
    @BindView(R.id.tvdangerousfence)
    TextView tvDangerousFence;
    /**
     * 高危围栏下布局
     **/
    @BindView(R.id.dangerousFenceLinear)
    LinearLayout dangerousFenceLinear;
    /**
     * 高危围栏站内接收人
     **/
    @BindView(R.id.dangerousFenceZNObj)
    TextView dangerousFenceZNObj;
    /**
     * 高危围栏短信接收人
     **/
    @BindView(R.id.dangerousFenceDXObj)
    TextView dangerousFenceDXObj;
    /***
     * 高危围栏货主列表
     */
    @BindView(R.id.dangerousFenceTvhzlist)
    TextView dangerousFenceTvhzlist;

    @BindView(R.id.edstayTime)
    EditText edStayTime;
    /**
     * 智能绑锁
     **/
    @BindView(R.id.togglelock)
    SwitchButton swLock;


    @BindView(R.id.tvreceiveObj)
    TextView tvReceiveObj;

    @BindView(R.id.tvexaminelist)
    TextView tvexaminelist;
    public final static int CHOOSE_RECEIVE_OBJ = 12345;
    public final static int CHOOSE_CAR_TYPE = 12346;
    public final static int ANQUANTIP = 12347;
    public final static int CHOOSE_COMPANY = 12348;
    public final static int DANGEROUS_FENCE = 12349;

    @BindView(R.id.rlyh)
    RelativeLayout rlYh;

    /****启用车型**/
    @BindView(R.id.togglestartcarmodel)
    SwitchButton swCarModel;
    @BindView(R.id.tvcarmodel)
    TextView tvCarModel;
    @BindView(R.id.tvanquan)
    TextView tvAnquan;
    private String carModerId;
    @BindView(R.id.eddriverage)
    EditText edDriverAge;
    @BindView(R.id.edcarage)
    EditText edCarAge;
    @BindView(R.id.tvrelationCom)
    TextView tvRelaCom;//关联公司

    @BindView(R.id.toggleIsWeilansign)
    SwitchButton isWeiLanSign;

    private UserInfoDto userInfoDto;
    private String id;
    private String orderCode;
    private String businessType;
    private OrderOpintalnfoEditPresenter presenter;
    private String htmlContext;
    /**
     * 站点信息布局
     **/
    @BindView(R.id.datacenterRelative)
    RelativeLayout datacenterRelative;
    /**
     * 站点信息
     **/
    @BindView(R.id.datacenterText)
    TextView datacenterText;
    /**
     * 运输确认
     **/
    @BindView(R.id.toggletrantorder)
    SwitchButton toggletrantorder;
    /**
     * 入场签到
     **/
    @BindView(R.id.toggleopenFactorySignIn)
    SwitchButton toggleopenFactorySignIn;
    /**
     * 到货通知
     **/
    @BindView(R.id.toggleopenArrival)
    SwitchButton toggleopenArrival;
    /**
     * 自动运输
     **/
    @BindView(R.id.toggleutotrant)
    SwitchButton toggleAutoTrant;

    @BindView(R.id.edroadLoss)
    EditText edRoadLoss;

    /**
     * 入场签到允许访问相册
     **/
    @BindView(R.id.toggleInFactoryAlbum)
    SwitchButton toggleInFactoryAlbum;

    /**
     * 装货拍照是否允许访问相册
     **/
    @BindView(R.id.toggleLoadAlbum)
    SwitchButton toggleLoadAlbum;
    /**
     * 卸货拍照是否允许访问相册
     **/
    @BindView(R.id.toggleUnloadAlbum)
    SwitchButton toggleUnloadAlbum;
    /**
     * 出厂签到拍照是否允许访问相册
     **/
    @BindView(R.id.toggleOutFactoryAlbum)
    SwitchButton toggleOutFactoryAlbum;
    /**
     * 到货确认拍照是否允许访问相册
     **/
    @BindView(R.id.toggleArrivalAlbum)
    SwitchButton toggleArrivalAlbum;


    @BindView(R.id.groupbeidou)
    RadioGroup groupBeiDou;
    /***是否强制北斗***/
    private int beiDouStatus = 3;
    @BindView(R.id.radiomust)
    RadioButton radiomust;
    @BindView(R.id.radiotips)
    RadioButton radiotips;
    @BindView(R.id.radiono)
    RadioButton radiono;
    /***自动到货确认***/
    @BindView(R.id.llautoarrive)
    LinearLayout llArriver;
    @BindView(R.id.toggleautiarrive)
    SwitchButton toggleAutoArrive;
    @BindView(R.id.edbeiDouOffTime)
    EditText edBeiDouOffTime;
    /***停留间隔时间*/
    @BindView(R.id.edspaceTime)
    EditText edSpaceTime;

    @Override
    public int initContentView() {
        return R.layout.tab_order_optional;
    }

    @OnClick({R.id.tvmakeTime, R.id.tvreceiveObj,
            R.id.tvexaminelist, R.id.tvanquan, R.id.tvcarmodel,
            R.id.tvrelationCom, R.id.deleterelacom, R.id.tvdangerousfence,
            /*偏离*/
            R.id.toggleplZNObj, R.id.toggleplDXObj, R.id.toggleplTvhzlist,
            /*离线*/
            R.id.toggleoffDXObj, R.id.toggleoffZNObj, R.id.toggleoffTvhzlist,
            /*磅单*/
            R.id.togglebangdanZNObj, R.id.togglebangdanDXObj, R.id.togglebangdanTvhzlist,
            /*停留*/
            R.id.togglestayDXObj, R.id.togglestayTvhzlist, R.id.togglestayZNObj,
            /*高危围栏*/
            R.id.dangerousFenceZNObj, R.id.dangerousFenceDXObj, R.id.dangerousFenceTvhzlist})
    public void click(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.deleterelacom:
                tvRelaCom.setText("");
                relationConId = "";
                break;
            case R.id.tvcarmodel://车型列表
                bundle = new Bundle();
                bundle.putString("from", "choosecartype");
                readyGoForResult(OrderMainActivity.class, CHOOSE_CAR_TYPE, bundle);
                break;
            case R.id.tvanquan:
                bundle = new Bundle();
                bundle.putString("data", htmlContext);
                readyGoForResult(RichPublishActivity.class, ANQUANTIP, bundle);
                break;
            case R.id.tvmakeTime:

                if (timeChooseDialogUtil == null) {
                    timeChooseDialogUtil = new TimeChooseDialogUtil(mContext);
                }
                timeChooseDialogUtil.showDialog(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        isChoose = true;
                        tvmakeTime.setText(result);
                    }

                    @Override
                    public void cancel() {
                        timeChooseDialogUtil.dissDialog();
                    }
                });
                break;
            case R.id.tvreceiveObj:
                bundle = new Bundle();
                bundle.putString("type", 8 + "");
                bundle.putString("from", "choosegoodscompany");
                readyGoForResult(OrderMainActivity.class, CHOOSE_RECEIVE_OBJ, bundle);
                break;
            /**
             * 偏离
             */
            case R.id.toggleplZNObj:
                bundle = new Bundle();
                bundle.putString("type", 8 + "");
                bundle.putString("from", "choosegoodscompany");
                readyGoForResult(OrderMainActivity.class, CHOOSE_RECEIVE_OBJ, bundle);
                break;
            case R.id.toggleplDXObj:
                bundle = new Bundle();
                bundle.putString("type", 11 + "");
                bundle.putString("from", "choosegoodscompany");
                readyGoForResult(OrderMainActivity.class, CHOOSE_RECEIVE_OBJ, bundle);
                break;
            case R.id.toggleplTvhzlist:
                bundle = new Bundle();
                bundle.putString("type", 9 + "");
                bundle.putString("from", "chooseHzList");
                readyGoForResult(OrderMainActivity.class, CHOOSE_RECEIVE_OBJ, bundle);
                break;
            /**
             * 离线
             */
            case R.id.toggleoffZNObj:
                bundle = new Bundle();
                bundle.putString("type", 12 + "");
                bundle.putString("from", "choosegoodscompany");
                readyGoForResult(OrderMainActivity.class, CHOOSE_RECEIVE_OBJ, bundle);
                break;
            case R.id.toggleoffDXObj:
                bundle = new Bundle();
                bundle.putString("type", 13 + "");
                bundle.putString("from", "choosegoodscompany");
                readyGoForResult(OrderMainActivity.class, CHOOSE_RECEIVE_OBJ, bundle);
                break;

            case R.id.toggleoffTvhzlist:
                bundle = new Bundle();
                bundle.putString("type", 14 + "");
                bundle.putString("from", "chooseHzList");
                readyGoForResult(OrderMainActivity.class, CHOOSE_RECEIVE_OBJ, bundle);
                break;
            /**
             * 磅单
             */
            case R.id.togglebangdanZNObj:
                bundle = new Bundle();
                bundle.putString("type", 15 + "");
                bundle.putString("from", "choosegoodscompany");
                readyGoForResult(OrderMainActivity.class, CHOOSE_RECEIVE_OBJ, bundle);
                break;
            case R.id.togglebangdanDXObj:
                bundle = new Bundle();
                bundle.putString("type", 16 + "");
                bundle.putString("from", "choosegoodscompany");
                readyGoForResult(OrderMainActivity.class, CHOOSE_RECEIVE_OBJ, bundle);
                break;
            case R.id.togglebangdanTvhzlist:
                bundle = new Bundle();
                bundle.putString("type", 17 + "");
                bundle.putString("from", "chooseHzList");
                readyGoForResult(OrderMainActivity.class, CHOOSE_RECEIVE_OBJ, bundle);
                break;
            /**
             * 停留
             */
            case R.id.togglestayZNObj:
                bundle = new Bundle();
                bundle.putString("type", 18 + "");
                bundle.putString("from", "choosegoodscompany");
                readyGoForResult(OrderMainActivity.class, CHOOSE_RECEIVE_OBJ, bundle);
                break;
            case R.id.togglestayDXObj:
                bundle = new Bundle();
                bundle.putString("type", 19 + "");
                bundle.putString("from", "choosegoodscompany");
                readyGoForResult(OrderMainActivity.class, CHOOSE_RECEIVE_OBJ, bundle);
                break;

            case R.id.togglestayTvhzlist:
                bundle = new Bundle();
                bundle.putString("type", 20 + "");
                bundle.putString("from", "chooseHzList");
                readyGoForResult(OrderMainActivity.class, CHOOSE_RECEIVE_OBJ, bundle);
                break;
            /**
             * 高危
             */
            case R.id.dangerousFenceZNObj:
                bundle = new Bundle();
                bundle.putString("type", 21 + "");
                bundle.putString("from", "choosegoodscompany");
                readyGoForResult(OrderMainActivity.class, CHOOSE_RECEIVE_OBJ, bundle);
                break;
            case R.id.dangerousFenceDXObj:
                bundle = new Bundle();
                bundle.putString("type", 22 + "");
                bundle.putString("from", "choosegoodscompany");
                readyGoForResult(OrderMainActivity.class, CHOOSE_RECEIVE_OBJ, bundle);
                break;

            case R.id.dangerousFenceTvhzlist:
                bundle = new Bundle();
                bundle.putString("type", 23 + "");
                bundle.putString("from", "chooseHzList");
                readyGoForResult(OrderMainActivity.class, CHOOSE_RECEIVE_OBJ, bundle);
                break;

            case R.id.tvexaminelist://验货人
                bundle = new Bundle();
                bundle.putString("type", 10 + "");
                bundle.putString("from", "chooseHzList");
                readyGoForResult(OrderMainActivity.class, CHOOSE_RECEIVE_OBJ, bundle);
                break;
            case R.id.tvrelationCom://关联公司
                readyGoForResult(RelationCompanyActivity.class, CHOOSE_COMPANY);
                break;
            case R.id.tvdangerousfence://高危围栏
                readyGoForResult(DangerousFenceActivity.class, DANGEROUS_FENCE);
                break;
        }
    }

    @Override
    public void initView() {
        mContext = getActivity();
        datacenterRelative.setVisibility(View.GONE);
        userInfoDto = Hawk.get(PreferenceKey.USER_INFO);
        if (userInfoDto != null) {
            if (!TextUtils.isEmpty(userInfoDto.getName())) {
                tvMakePeople.setText(userInfoDto.getName());
            }
        }
        presenter = new OrderOpintalnfoEditPresenter(this, mContext);
        try {

            id = getArguments().getString("id");
            if (!TextUtils.isEmpty(id)) {
                from = getArguments().getString("from");
                presenter.getpOrderDelation(id, from);
            }
        } catch (Exception e) {
        }
        try {
            orderCode = getArguments().getString("orderCode");
            businessType = getArguments().getString("businessType");
            if (!TextUtils.isEmpty(orderCode)) {
                presenter.getConsuteDelation(orderCode, businessType);
            }
        } catch (Exception E) {
        }
    }

    @Override
    public void initData() {

        groupBeiDou.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radiomust:
                        beiDouStatus = 1;
                        break;
                    case R.id.radiotips:
                        beiDouStatus = 2;
                        break;
                    case R.id.radiono:
                        beiDouStatus = 3;
                        break;
                }
            }
        });
        edksNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    isChoose = false;
                } else {
                    isChoose = true;
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
                if (TextUtils.isEmpty(s.toString())) {
                    isChoose = false;
                } else {
                    isChoose = true;
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
                if (TextUtils.isEmpty(s.toString())) {
                    isChoose = false;
                } else {
                    isChoose = true;
                }
            }
        });
        switchZc.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose = isChecked;
            }
        });
        switchXh.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose = isChecked;
            }
        });
        switchFp.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose = isChecked;
            }
        });
        toggleYh.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose = isChecked;
                if (isChecked) {
                    rlYh.setVisibility(View.VISIBLE);
                } else {
                    tvexaminelist.setText("");
                    examinelistId = "";
                    rlYh.setVisibility(View.GONE);
                }
            }
        });
        togglezhPz.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose = isChecked;
            }
        });
        togglexhpz.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose = isChecked;
            }
        });

        swLock.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose = isChecked;
            }
        });
        swDeviate.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose = isChecked;
            }
        });
        swBangBan.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose = isChecked;

            }
        });
        swStay.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose = isChecked;

            }
        });
        swOffline.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose = isChecked;

            }
        });
        isWeiLanSign.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose = isChecked;
            }
        });
        toggleAutoTrant.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose = isChecked;

            }
        });
        togglebangdan.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isChoose = isChecked;
            }
        });

    }


    @Override
    public void getOrderDelation(OrderDelationDto dto) {
        if (dto != null) {
            OrderDelationDto.choosedata choosedata = dto.getChoose();
            if (choosedata != null) {
                /**
                 * 偏离预警站内接收人
                 **/
                 toggleplZNObj.setText(choosedata.getDeviateStation());
                /**
                 * 偏离预警短信接收人
                 **/
                 toggleplDXObj.setText(choosedata.getDeviateShortMessage());
                /**
                 * 离线预警站内接收人
                 **/
                 toggleoffZNObj.setText(choosedata.getOfflineStation());
                /**
                 * 离线预警短信接收人
                 **/
                 toggleoffDXObj.setText(choosedata.getOfflineShortMessage());
                /**
                 * 高危围栏站内接收人
                 **/
                 dangerousFenceZNObj.setText(choosedata.getHighRiskStation());
                /**
                 * 高危围栏短信接收人
                 **/
                 dangerousFenceDXObj.setText(choosedata.getHighRiskSms());
                /**
                 * 停留预警短信接收人
                 **/
                 togglestayDXObj.setText(choosedata.getStopShortMessage());
                /***
                 * 停留预警货主列表
                 */
                 togglestayZNObj.setText(choosedata.getStopStation());
                /**
                 * 磅单预警站内接收人
                 **/
                 togglebangdanZNObj.setText(choosedata.getPoundListStation());
                /**
                 * 磅单预警短信接收人
                 **/
                 togglebangdanDXObj.setText(choosedata.getPoundListShortMessage());
                tvReceiveObj.setText(dto.getChoose().getPushAlarmRoleName());
                receivObjId = dto.getChoose().getPushAlarmRole();
                edksNum.setText(choosedata.getThirdPartyNo());
                tvmakeTime.setText(choosedata.getMakerName());
                tvmakeTime.setText(choosedata.getMakerTime());
                tvxy.setText(choosedata.getPayProtocolName());
                edMark.setText(choosedata.getRemark());
                if (dto.getChoose().getProvideInvoice() == 1) {//发票
                    switchFp.setChecked(true);
                }
                if (dto.getChoose().getProvideUnload() == 1) {//提供卸货
                    switchXh.setChecked(true);
                }
                if (dto.getChoose().getProvideLoad() == 1) {////提供发货
                    switchZc.setChecked(true);
                }
                if (dto.getChoose().getFenceClock() == 1) {
                    isWeiLanSign.setChecked(true);
                }

                if (dto.getChoose().getPoundAlarm() == 1) {
                    togglebangdan.setChecked(true);
                }
                if (dto.getChoose().getCheck() == 1) {//验货
                    toggleYh.setChecked(true);
                    rlYh.setVisibility(View.VISIBLE);
                    examinelistId = dto.getChoose().getCheckUserList();
                    tvexaminelist.setText(dto.getChoose().getCheckUserListName());
                } else {
                    rlYh.setVisibility(View.GONE);
                }
                if (dto.getChoose().getLoadPhotos() == 1) {//装货拍照
                    togglezhPz.setChecked(true);
                }
                if (dto.getChoose().getUnloadPhotos() == 1) {//卸货拍照
                    togglexhpz.setChecked(true);
                }
                if (dto.getChoose().getOutFactoryPhotos() == 1) {
                    swBangBan.setChecked(true);
                }
                if (dto.getChoose().getStopAlarm() == 1) {
                    swStay.setChecked(true);
                }
                if (dto.getChoose().getBindSmartLock() == 1) {
                    swLock.setChecked(true);
                }
                if (dto.getChoose().getSjSignIn() == 1) {
                    toggleAutoArrive.setChecked(true);
                }
                if (dto.getChoose().getDeviationAlarm() == 1) {
                    swDeviate.setChecked(true);
                }
                if (dto.getChoose().getOffLineAlarm() == 1) {
                    swOffline.setChecked(true);
                }
                if (dto.getChoose().getAutoTransport() == 1) {
                    toggleAutoTrant.setChecked(true);
                }
                if (dto.getChoose().getAlarmTime() != 0) {
                    edStayTime.setText(dto.getChoose().getAlarmTime() + "");
                }

                if (dto.getChoose().getOpenTransport() == 2) {
                    toggletrantorder.setChecked(false);
                }
                if (dto.getChoose().getOpenFactorySignIn() == 2) {
                    toggleopenFactorySignIn.setChecked(false);
                }
                if (dto.getChoose().getOpenArrival() == 2) {
                    toggleopenArrival.setChecked(false);
                }

                if (dto.getChoose().getInFactoryAlbum() == 1) {
                    toggleInFactoryAlbum.setChecked(true);
                }
                if (dto.getChoose().getLoadAlbum() == 1) {
                    toggleLoadAlbum.setChecked(true);
                }
                if (dto.getChoose().getUnloadAlbum() == 1) {
                    toggleUnloadAlbum.setChecked(true);
                }
                if (dto.getChoose().getOutFactoryAlbum() == 1) {
                    toggleOutFactoryAlbum.setChecked(true);
                }
                if (dto.getChoose().getArrivalAlbum() == 1) {
                    toggleArrivalAlbum.setChecked(true);
                }

                deviateStation=choosedata.getDeviateStation();
               

                if (dto.getChoose().getOpenCarType() == 1) {
                    swCarModel.setChecked(true);
                } else {
                    swCarModel.setChecked(false);
                }
                tvCarModel.setText(dto.getChoose().getCarTypeName());
                carModerId = dto.choose.getCarTypeId();
                htmlContext = dto.choose.getContext();
                if (!TextUtils.isEmpty(htmlContext)) {
                    tvAnquan.setText("点击查看");
                }
                edDriverAge.setText(dto.getChoose().getDrivingYears());
                edCarAge.setText(dto.getChoose().getTravelYears());
                tvRelaCom.setText(dto.getChoose().getRelationComName());
                relationConId = dto.getChoose().getRelationCom();
                edRoadLoss.setText(dto.getChoose().getRoadLoss());
                fenceId = dto.getChoose().getHighEnclosureId();
                tvDangerousFence.setText(dto.getChoose().getHighEnclosureName());
                beiDouStatus = dto.getChoose().getBeiDouStatus();
                edBeiDouOffTime.setText(dto.getChoose().getBeiDouOffTime());
                edSpaceTime.setText(dto.getChoose().getSpaceTime());
                if (beiDouStatus == 1) {//
                    radiomust.setChecked(true);
                } else if (beiDouStatus == 2) {
                    radiotips.setChecked(true);
                } else {
                    radiono.setChecked(true);
                }
            }
        }
    }

    @Override
    public boolean getIschoose() {
        return isChoose;
    }

    @Override
    public void getConsuteDealtion(ConsuteDelationDto dto) {
        if (dto != null) {
            edksNum.setText(dto.getThirdPartyNo());
            tvMakePeople.setText(dto.getMakerName());
            if (dto.getResTxt2() != null && !TextUtils.isEmpty(dto.getResTxt2())) {
                datacenterRelative.setVisibility(View.VISIBLE);
                datacenterText.setText(dto.getResTxt2() + "");
            }
        }
    }

    @Override
    public int fenceClock() {
        if (isWeiLanSign.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public String deviateShortMessage() {
        return deviateShortMessage ;
    }

    @Override
    public String deviateStation() {
        return deviateStation;
    }

    @Override
    public String deviateCargoOwner() {
        return deviateCargoOwner;
    }

    @Override
    public String OfflineShortMessage() {
        return OfflineShortMessage;
    }

    @Override
    public String OfflineStation() {
        return OfflineStation;
    }

    @Override
    public String OfflineCargoOwner() {
        return OfflineCargoOwner;
    }

    @Override
    public String poundListShortMessage() {
        return poundListShortMessage;
    }

    @Override
    public String poundListStation() {
        return poundListStation;
    }

    @Override
    public String poundListCargoOwner() {
        return poundListCargoOwner;
    }

    @Override
    public String stopShortMessage() {
        return stopShortMessage;
    }

    @Override
    public String stopStation() {
        return stopStation;
    }

    @Override
    public String stopCargoOwner() {
        return stopCargoOwner;
    }

    @Override
    public String HighRiskSms() {
        return HighRiskSms;
    }

    @Override
    public String HighRiskStation() {
        return HighRiskStation;
    }

    @Override
    public String HighRiskCargo() {
        return HighRiskCargo;
    }

    @Override
    public int openTransport() {
        if (toggletrantorder.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int openFactorySignIn() {
        if (toggleopenFactorySignIn.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int openArrival() {
        if (toggleopenArrival.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int autoTransport() {
        if (toggleAutoTrant.isChecked()) {
            return 1;
        } else {
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
    public int beiDouStatus() {
        return beiDouStatus;
    }

    @Override
    public String beiDouOffTime() {
        return edBeiDouOffTime.getText().toString();
    }

    @Override
    public String spaceTime() {
        return edSpaceTime.getText().toString();
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
        if (switchZc.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int isApplxh() {
        if (switchXh.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int isApplfp() {
        if (switchFp.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int isApplyh() {
        if (toggleYh.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int isApplzhpz() {
        if (togglezhPz.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int isApplxhpz() {
        if (togglexhpz.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int inFactoryAlbum() {
        if (toggleInFactoryAlbum.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int loadAlbum() {
        if (toggleLoadAlbum.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int unloadAlbum() {
        if (toggleUnloadAlbum.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int outFactoryAlbum() {
        if (toggleOutFactoryAlbum.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int arrivalAlbum() {
        if (toggleArrivalAlbum.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int poundAlarm() {
        if (togglebangdan.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int bindSmartLock() {
        if (swLock.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int outFactoryPhotos() {
        if (swBangBan.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int stopAlarm() {
        if (swStay.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    //离线
    @Override
    public int offLineAlarm() {
        if (swOffline.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    /***
     * 偏离预警
     * **/
    @Override
    public int deviationAlarm() {
        if (swDeviate.isChecked()) {

            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int openCarType() {
        if (swCarModel.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int sjSignIn() {
        if (toggleAutoArrive.isChecked()) {
            return 1;
        } else {
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
        return null;
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

    private ArrayList<String> nameList;
    private String receivObjId = "";//接收对象ID
    private String examinelistId = "";//验货人ID
    private String relationConId = "";//关联公司ID
    private String fenceId = "";//高危围栏Id
    /**
     *  偏离预警短信对象
     */
    private String deviateShortMessage ="";
    /**
     *  偏离预警站内对象
     */
    private String deviateStation ="";
    /**
     *  偏离预警货主列表
     */
    private String deviateCargoOwner ="";
    /**
     *  离线预警短信对象
     */
    private String OfflineShortMessage ="";
    /**
     *  离线预警站内对象
     */
    private String OfflineStation ="";
    /**
     *  离线预警货主列表
     */
    private String OfflineCargoOwner ="";
    /**
     *  磅单预警短信对象
     */
    private String poundListShortMessage ="";
    /**
     *  磅单预警站内对象
     */
    private String poundListStation ="";
    /**
     *  磅单预警货主列表
     */
    private String poundListCargoOwner ="";
    /**
     *  停留预警短信对象
     */

    private String stopShortMessage ="";
    /**
     *  停留预警站内对象
     */
    private String stopStation ="";
    /**
     *  停留预警货主列表
     */
    private String stopCargoOwner ="";




    /**
     *  高危围栏短信接收人
     */
    public String  HighRiskSms ="";

    /**
     *  高危围栏站内接收人
     */
    public String HighRiskStation ="";
    /**
     *  高危围栏货主列表
     */
    public String HighRiskCargo ="";
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DANGEROUS_FENCE && resultCode == RESULT_OK) {
            DangerousFenceDto fenceDto = (DangerousFenceDto) data.getSerializableExtra("data");
            if (fenceDto != null && !TextUtils.isEmpty(fenceDto.getEnclosureName())) {
                if (!tvDangerousFence.getText().toString().contains(fenceDto.getEnclosureName())) {
                    tvDangerousFence.setText(tvDangerousFence.getText().toString() + fenceDto.getEnclosureName() + ",");
                    fenceId = fenceId + fenceDto.getId() + ",";
                }
            }

        } else if (requestCode == CHOOSE_COMPANY && resultCode == RESULT_OK) {
            AuthorityDtoSerializ authorityDto = (AuthorityDtoSerializ) data.getSerializableExtra("data");
            if (authorityDto != null) {
                if (!tvRelaCom.getText().toString().contains(authorityDto.getCompanyName())) {
                    tvRelaCom.setText(tvRelaCom.getText().toString() + authorityDto.getCompanyName() + ",");
                    relationConId = relationConId + authorityDto.getId() + ",";
                }
            }
        } else if (requestCode == ANQUANTIP && resultCode == RESULT_OK) {
            htmlContext = data.getStringExtra("data");
            tvAnquan.setText("点击查看");
        } else if (requestCode == CHOOSE_CAR_TYPE && resultCode == RESULT_OK) {
            isChoose = true;
            ArrayList<String> idList = (ArrayList<String>) data.getSerializableExtra("data");
            nameList = (ArrayList<String>) data.getSerializableExtra("nameList");
            if (idList != null) {
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
        } else if (requestCode == CHOOSE_RECEIVE_OBJ && resultCode == RESULT_OK) {
            isChoose = true;
            if (data.getStringExtra("type").equals("10")) {
                ArrayList<String> idList = (ArrayList<String>) data.getSerializableExtra("data");
                nameList = (ArrayList<String>) data.getSerializableExtra("nameList");
                if (idList != null) {
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
            } else if (data.getStringExtra("type").equals("9")) {
                /**
                 * 偏离司机
                 */
                ArrayList<String> idList = (ArrayList<String>) data.getSerializableExtra("data");
                nameList = (ArrayList<String>) data.getSerializableExtra("nameList");
                if (idList != null) {
                    deviateCargoOwner = "";
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
                            deviateCargoOwner += idList.get(i);
                        } else {
                            deviateCargoOwner += idList.get(i) + ",";
                        }
                    }
                    toggleplTvhzlist.setText(nameString);
                }
            } else if (data.getStringExtra("type").equals("14")) {
                /**
                 * 离线司机
                 */
                ArrayList<String> idList = (ArrayList<String>) data.getSerializableExtra("data");
                nameList = (ArrayList<String>) data.getSerializableExtra("nameList");
                if (idList != null) {
                    OfflineCargoOwner = "";
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
                            OfflineCargoOwner += idList.get(i);
                        } else {
                            OfflineCargoOwner += idList.get(i) + ",";
                        }
                    }
                    toggleoffTvhzlist.setText(nameString);
                }
            } else if (data.getStringExtra("type").equals("17")) {
                /**
                 * 磅单司机
                 */
                ArrayList<String> idList = (ArrayList<String>) data.getSerializableExtra("data");
                nameList = (ArrayList<String>) data.getSerializableExtra("nameList");
                if (idList != null) {
                    poundListCargoOwner = "";
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
                            poundListCargoOwner += idList.get(i);
                        } else {
                            poundListCargoOwner += idList.get(i) + ",";
                        }
                    }
                    togglebangdanTvhzlist.setText(nameString);
                }
            } else if (data.getStringExtra("type").equals("20")) {
                /**
                 * 停留司机
                 */
                ArrayList<String> idList = (ArrayList<String>) data.getSerializableExtra("data");
                nameList = (ArrayList<String>) data.getSerializableExtra("nameList");
                if (idList != null) {
                    stopCargoOwner = "";
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
                            stopCargoOwner += idList.get(i);
                        } else {
                            stopCargoOwner += idList.get(i) + ",";
                        }
                    }
                    togglestayTvhzlist.setText(nameString);
                }
            } else if (data.getStringExtra("type").equals("23")) {
                /**
                 * 高危司机
                 */
                ArrayList<String> idList = (ArrayList<String>) data.getSerializableExtra("data");
                nameList = (ArrayList<String>) data.getSerializableExtra("nameList");
                if (idList != null) {
                    HighRiskCargo = "";
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
                            HighRiskCargo += idList.get(i);
                        } else {
                            HighRiskCargo += idList.get(i) + ",";
                        }
                    }
                    dangerousFenceTvhzlist.setText(nameString);
                }
            }

            else if (data.getStringExtra("type").equals("8")) {
                /**
                 * 偏离站内
                 */
                ArrayList<String> idList = (ArrayList<String>) data.getSerializableExtra("data");
                nameList = (ArrayList<String>) data.getSerializableExtra("nameList");
                if (idList != null) {
                    String nameString = "";
                    deviateStation = "";
                    for (int i = 0; i < nameList.size(); i++) {
                        if (i == nameList.size() - 1) {
                            nameString += nameList.get(i);
                        } else {
                            nameString += nameList.get(i) + ",";
                        }
                    }
                    for (int i = 0; i < idList.size(); i++) {
                        if (i == idList.size() - 1) {
                            deviateStation += idList.get(i);
                        } else {
                            deviateStation += idList.get(i) + ",";
                        }
                    }
                    toggleplZNObj.setText(nameString);
                }
            } else if (data.getStringExtra("type").equals("11")) {
                /**
                 * 偏离短信
                 */
                ArrayList<String> idList = (ArrayList<String>) data.getSerializableExtra("data");
                nameList = (ArrayList<String>) data.getSerializableExtra("nameList");
                if (idList != null) {
                    String nameString = "";
                    deviateShortMessage = "";
                    for (int i = 0; i < nameList.size(); i++) {
                        if (i == nameList.size() - 1) {
                            nameString += nameList.get(i);
                        } else {
                            nameString += nameList.get(i) + ",";
                        }
                    }
                    for (int i = 0; i < idList.size(); i++) {
                        if (i == idList.size() - 1) {
                            deviateShortMessage += idList.get(i);
                        } else {
                            deviateShortMessage += idList.get(i) + ",";
                        }
                    }
                    toggleplDXObj.setText(nameString);
                }
            } else if (data.getStringExtra("type").equals("12")) {
                /**
                 * 离线站内
                 */
                ArrayList<String> idList = (ArrayList<String>) data.getSerializableExtra("data");
                nameList = (ArrayList<String>) data.getSerializableExtra("nameList");
                if (idList != null) {
                    String nameString = "";
                    OfflineStation = "";
                    for (int i = 0; i < nameList.size(); i++) {
                        if (i == nameList.size() - 1) {
                            nameString += nameList.get(i);
                        } else {
                            nameString += nameList.get(i) + ",";
                        }
                    }
                    for (int i = 0; i < idList.size(); i++) {
                        if (i == idList.size() - 1) {
                            OfflineStation += idList.get(i);
                        } else {
                            OfflineStation += idList.get(i) + ",";
                        }
                    }
                    toggleoffZNObj.setText(nameString);
                }
            } else if (data.getStringExtra("type").equals("13")) {
                /**
                 * 离线短信
                 */
                ArrayList<String> idList = (ArrayList<String>) data.getSerializableExtra("data");
                nameList = (ArrayList<String>) data.getSerializableExtra("nameList");
                if (idList != null) {
                    String nameString = "";
                    OfflineShortMessage = "";
                    for (int i = 0; i < nameList.size(); i++) {
                        if (i == nameList.size() - 1) {
                            nameString += nameList.get(i);
                        } else {
                            nameString += nameList.get(i) + ",";
                        }
                    }
                    for (int i = 0; i < idList.size(); i++) {
                        if (i == idList.size() - 1) {
                            OfflineShortMessage += idList.get(i);
                        } else {
                            OfflineShortMessage += idList.get(i) + ",";
                        }
                    }
                    toggleoffDXObj.setText(nameString);
                }
            } else if (data.getStringExtra("type").equals("15")) {
                /**
                 * 磅单站内
                 */
                ArrayList<String> idList = (ArrayList<String>) data.getSerializableExtra("data");
                nameList = (ArrayList<String>) data.getSerializableExtra("nameList");
                if (idList != null) {
                    String nameString = "";
                    receivObjId = "";
                    for (int i = 0; i < nameList.size(); i++) {
                        if (i == nameList.size() - 1) {
                            nameString += nameList.get(i);
                        } else {
                            nameString += nameList.get(i) + ",";
                        }
                    }
                    for (int i = 0; i < idList.size(); i++) {
                        if (i == idList.size() - 1) {
                            poundListStation += idList.get(i);
                        } else {
                            poundListStation += idList.get(i) + ",";
                        }
                    }
                    togglebangdanZNObj.setText(nameString);
                }
            } else if (data.getStringExtra("type").equals("16")) {
                /**
                 * 磅单短信
                 */
                ArrayList<String> idList = (ArrayList<String>) data.getSerializableExtra("data");
                nameList = (ArrayList<String>) data.getSerializableExtra("nameList");
                if (idList != null) {
                    String nameString = "";
                    poundListShortMessage = "";
                    for (int i = 0; i < nameList.size(); i++) {
                        if (i == nameList.size() - 1) {
                            nameString += nameList.get(i);
                        } else {
                            nameString += nameList.get(i) + ",";
                        }
                    }
                    for (int i = 0; i < idList.size(); i++) {
                        if (i == idList.size() - 1) {
                            poundListShortMessage += idList.get(i);
                        } else {
                            poundListShortMessage += idList.get(i) + ",";
                        }
                    }
                    togglebangdanDXObj.setText(nameString);
                }
            } else if (data.getStringExtra("type").equals("18")) {
                /**
                 * 停留站内
                 */
                ArrayList<String> idList = (ArrayList<String>) data.getSerializableExtra("data");
                nameList = (ArrayList<String>) data.getSerializableExtra("nameList");
                if (idList != null) {
                    String nameString = "";
                    stopStation = "";
                    for (int i = 0; i < nameList.size(); i++) {
                        if (i == nameList.size() - 1) {
                            nameString += nameList.get(i);
                        } else {
                            nameString += nameList.get(i) + ",";
                        }
                    }
                    for (int i = 0; i < idList.size(); i++) {
                        if (i == idList.size() - 1) {
                            stopStation += idList.get(i);
                        } else {
                            stopStation += idList.get(i) + ",";
                        }
                    }
                    togglestayZNObj.setText(nameString);
                }
            } else if (data.getStringExtra("type").equals("19")) {
                /**
                 * 停留短信
                 */
                ArrayList<String> idList = (ArrayList<String>) data.getSerializableExtra("data");
                nameList = (ArrayList<String>) data.getSerializableExtra("nameList");
                if (idList != null) {
                    String nameString = "";
                    stopShortMessage = "";
                    for (int i = 0; i < nameList.size(); i++) {
                        if (i == nameList.size() - 1) {
                            nameString += nameList.get(i);
                        } else {
                            nameString += nameList.get(i) + ",";
                        }
                    }
                    for (int i = 0; i < idList.size(); i++) {
                        if (i == idList.size() - 1) {
                            stopShortMessage += idList.get(i);
                        } else {
                            stopShortMessage += idList.get(i) + ",";
                        }
                    }
                    togglestayDXObj.setText(nameString);
                }
            } else if (data.getStringExtra("type").equals("21")) {
                /**
                 * 高危站内
                 */
                ArrayList<String> idList = (ArrayList<String>) data.getSerializableExtra("data");
                nameList = (ArrayList<String>) data.getSerializableExtra("nameList");
                if (idList != null) {
                    String nameString = "";
                    HighRiskStation = "";
                    for (int i = 0; i < nameList.size(); i++) {
                        if (i == nameList.size() - 1) {
                            nameString += nameList.get(i);
                        } else {
                            nameString += nameList.get(i) + ",";
                        }
                    }
                    for (int i = 0; i < idList.size(); i++) {
                        if (i == idList.size() - 1) {
                            HighRiskStation += idList.get(i);
                        } else {
                            HighRiskStation += idList.get(i) + ",";
                        }
                    }
                    dangerousFenceZNObj.setText(nameString);
                }
            } else if (data.getStringExtra("type").equals("22")) {
                /**
                 * 高危短信
                 */
                ArrayList<String> idList = (ArrayList<String>) data.getSerializableExtra("data");
                nameList = (ArrayList<String>) data.getSerializableExtra("nameList");
                if (idList != null) {
                    String nameString = "";
                    HighRiskSms = "";
                    for (int i = 0; i < nameList.size(); i++) {
                        if (i == nameList.size() - 1) {
                            nameString += nameList.get(i);
                        } else {
                            nameString += nameList.get(i) + ",";
                        }
                    }
                    for (int i = 0; i < idList.size(); i++) {
                        if (i == idList.size() - 1) {
                            HighRiskSms += idList.get(i);
                        } else {
                            HighRiskSms += idList.get(i) + ",";
                        }
                    }
                    dangerousFenceDXObj.setText(nameString);
                }
            }


        }
    }
}
