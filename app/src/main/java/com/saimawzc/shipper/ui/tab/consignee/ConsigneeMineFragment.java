package com.saimawzc.shipper.ui.tab.consignee;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.base.BaseImmersionFragment;
import com.saimawzc.shipper.dto.identification.PersonCenterDto;
import com.saimawzc.shipper.ui.login.LoginActivity;
import com.saimawzc.shipper.ui.my.ChangeRoleActivity;
import com.saimawzc.shipper.ui.my.PersonCenterActivity;
import com.saimawzc.shipper.weight.BottomDialogUtil;
import com.saimawzc.shipper.weight.CircleImageView;
import com.saimawzc.shipper.weight.utils.api.mine.MineApi;
import com.saimawzc.shipper.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.shipper.weight.utils.dialog.NormalDialog;
import com.saimawzc.shipper.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.shipper.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.http.Http;
import com.saimawzc.shipper.weight.utils.loadimg.ImageLoadUtil;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;
import com.saimawzc.shipper.weight.utils.statusbar.StatusBarUtil;
import com.saimawzc.shipper.weight.utils.statusbar.ViewColor;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/8/14.
 */

public class ConsigneeMineFragment extends BaseImmersionFragment {

    @BindView(R.id.tvName)TextView tvName;
    @BindView(R.id.avatar_min)CircleImageView headImage;
    @BindView(R.id.tvcompamy)TextView tvComapny;
    private NormalDialog dialog;
    @BindView(R.id.tv_verson)TextView tvVeson;
    @Override
    public int initContentView() {
        return R.layout.fragment_consignee_mine;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        tvComapny.setText("?????????");
        tvVeson.setText("??????????????????"+ BaseActivity.getVersionName(mContext));
    }

    @Override
    public void initData() {

    }


    @Override
    public void onResume() {
        super.onResume();
        getPersonterData();
    }

    /***??????????????????
     * ??????
     * **/
    public MineApi mineApi= Http.http.createApi(MineApi.class);
    private void getPersonterData(){
        mineApi.getPersoneCener().enqueue(new CallBack<PersonCenterDto>() {
            @Override
            public void success(PersonCenterDto response) {
                Hawk.put(PreferenceKey.PERSON_CENTER,response);
                personCenterDto=response;
                tvName.setText(response.getName());
                ImageLoadUtil.displayImage(mContext, response.getPicture(), headImage);
            }
            @Override
            public void fail(String code, String message) {

            }
        });
    }

    @OnClick({R.id.rl_LogOut,R.id.rlchangerole,R.id.imgKefu,R.id.rlset})
    public void click(View view){
        Bundle bundle=null;
        switch (view.getId()){
            case R.id.rlset:
                bundle =new Bundle();
                bundle.putString("from","set");
                readyGo(PersonCenterActivity.class,bundle);
                break;
            case R.id.rlchangerole:
                if(personCenterDto==null){
                    context.showMessage("????????????????????????");
                    getPersonterData();
                    return;
                }
                bundle=new Bundle();
                bundle.putInt("currentrole",personCenterDto.getRoleType());
                readyGo(ChangeRoleActivity.class,bundle);
                break;

            case R.id.rl_LogOut://??????
                dialog = new NormalDialog(mContext).isTitleShow(false)
                        .content("??????????????????????")
                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                        .btnNum(2).btnText("??????", "??????");
                dialog.setOnBtnClickL(
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if(!context.isFinishing()){
                                    dialog.dismiss();
                                }
                            }
                        },
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                Hawk.put(PreferenceKey.ID,"");
                                Hawk.put(PreferenceKey.USER_INFO,null);
                                Hawk.put(PreferenceKey.PERSON_CENTER,null);
                                readyGo(LoginActivity.class);
                                context.finish();
                                if(!context.isFinishing()){
                                    dialog.dismiss();
                                }
                            }
                        });
                dialog.show();
                break;
            case R.id.imgKefu:
                final BottomDialogUtil bottomDialogUtil = new BottomDialogUtil.Builder()
                        .setContext(context) //?????? context
                        .setContentView(R.layout.dialog_kefu) //??????????????????
                        .setOutSideCancel(true) //????????????????????????
                        .builder()
                        .show();


                TextView tvdayPhone= (TextView) bottomDialogUtil.getItemView(R.id.tvDayPhone);
                TextView tvNightPhone= (TextView) bottomDialogUtil.getItemView(R.id.tvNightPhone);
                TextView tvtousuPhone=(TextView) bottomDialogUtil.getItemView(R.id.tvtousuPhone);
                String[] PERMISSIONS = new String[]{
                        Manifest.permission.CALL_PHONE
                };
                if(context.permissionChecker.isLackPermissions(PERMISSIONS)){
                    context.permissionChecker.requestPermissions();
                    context.showMessage("????????????????????????");
                    return;
                }
                tvdayPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.callPhone("4008874005");
                        bottomDialogUtil.dismiss();
                    }
                });
                tvNightPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.callPhone("17398448233");
                        bottomDialogUtil.dismiss();
                    }
                });
                tvtousuPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.callPhone("13895189901");
                        bottomDialogUtil.dismiss();
                    }
                });
                break;
        }
    }


    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true).
                navigationBarColor(R.color.bg).
                init();
    }
}
