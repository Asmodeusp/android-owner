package com.saimawzc.shipper.ui.tab;

import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.ocr.sdk.model.BaseImageParams;
import com.bumptech.glide.Glide;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.base.BaseImmersionFragment;
import com.saimawzc.shipper.dto.identification.PersonCenterDto;
import com.saimawzc.shipper.ui.login.LoginActivity;
import com.saimawzc.shipper.ui.my.ChangeRoleActivity;
import com.saimawzc.shipper.ui.my.PersonCenterActivity;
import com.saimawzc.shipper.ui.my.carmanage.CarLearderListActivity;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.weight.BottomDialogUtil;
import com.saimawzc.shipper.weight.CircleImageView;
import com.saimawzc.shipper.weight.StatusBarUtil;
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
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
/**Created by Administrator on 2020/7/31.
 * 我的
 */
public class MineFragment  extends BaseImmersionFragment {

    private NormalDialog dialog;
    @BindView(R.id.tvName)TextView tvName;
    @BindView(R.id.avatar_min)CircleImageView headImage;
    @BindView(R.id.tvcompamy)TextView tvComapny;
    @BindView(R.id.tvIdentification)TextView tvIdentification;
    @BindView(R.id.tv_verson)TextView tvVeson;

    @Override
    public int initContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        tvVeson.setText("当前版本号："+ BaseActivity.getVersionName(mContext));
    }

    @Override
    public void initData() {


    }
    @OnClick({R.id.rl_LogOut,R.id.rl_carrier,R.id.rl_useridentification,
            R.id.rlchangerole,R.id.mywalley,R.id.rlset,R.id.avatar_min,R.id.imgKefu
            ,R.id.invitation,R.id.rlcarmanage})
    public void click(View view){
        Bundle bundle=null;
        switch (view.getId()){
            case R.id.rlset:
                bundle =new Bundle();
                bundle.putString("from","set");
                readyGo(PersonCenterActivity.class,bundle);
                break;
            case R.id.avatar_min://修改头像
                bundle=new Bundle();
                bundle.putString("from","personcenter");
                readyGo(PersonCenterActivity.class,bundle);
                break;
            case R.id.mywalley://我的钱包
                break;
            case R.id.invitation:
                if(personCenterDto==null){
                    context.showMessage("用户信息获取失败");
                    getPersonterData();
                    return;
                }
                bundle=new Bundle();
                bundle.putString("from","invitation");
                bundle.putSerializable("data",personCenterDto);
                readyGo(PersonCenterActivity.class,bundle);
                break;
            case R.id.rlchangerole:
                if(personCenterDto==null){
                    context.showMessage("用户信息获取失败");
                    getPersonterData();
                    return;
                }
                bundle=new Bundle();
                bundle.putInt("currentrole",personCenterDto.getRoleType());
                readyGo(ChangeRoleActivity.class,bundle);
                break;
            case R.id.rl_carrier://我的承运商
                if(personCenterDto==null){
                    context.showMessage("用户信息获取失败");
                    getPersonterData();
                    return;
                }
                if(personCenterDto.getAuthState()!=1){
                    context.showMessage("您尚未通过认证");
                    return;
                }
                bundle=new Bundle();
                bundle.putString("from","mycarrier");
                readyGo(PersonCenterActivity.class,bundle);
                break;
            case R.id.rl_useridentification://用户认证
                bundle=new Bundle();
                bundle.putString("from","huozhucarrier");
                readyGo(PersonCenterActivity.class,bundle);
                break;
            case R.id.rlcarmanage:
                readyGo(CarLearderListActivity.class);
                break;

            case R.id.rl_LogOut://注销
                dialog = new NormalDialog(mContext).isTitleShow(false)
                        .content("确定退出登录吗?")
                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                        .btnNum(2).btnText("取消", "确定");
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
                                Hawk.put(PreferenceKey.HZ_IS_RZ,"");
                                Hawk.put(PreferenceKey.PERSON_CENTER,null);
                                readyGo(LoginActivity.class);

                                if(!context.isFinishing()){
                                    dialog.dismiss();
                                }
                            }
                        });
                dialog.show();
                break;
            case R.id.imgKefu:
                String[] PERMISSIONS = new String[]{
                        Manifest.permission.CALL_PHONE
                };
                if(context.permissionChecker.isLackPermissions(PERMISSIONS)){
                    context.permissionChecker.requestPermissions();
                    context.showMessage("未获取到电话权限");
                    return;
                }
                final BottomDialogUtil bottomDialogUtil = new BottomDialogUtil.Builder()
                        .setContext(context) //设置 context
                        .setContentView(R.layout.dialog_kefu) //设置布局文件
                        .setOutSideCancel(true) //设置点击外部取消
                        .builder()
                        .show();


                TextView tvdayPhone= (TextView) bottomDialogUtil.getItemView(R.id.tvDayPhone);
                TextView tvNightPhone= (TextView) bottomDialogUtil.getItemView(R.id.tvNightPhone);
                TextView tvtousuPhone=(TextView) bottomDialogUtil.getItemView(R.id.tvtousuPhone);

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
    public void onResume() {
        super.onResume();
        getPersonterData();
    }
    /***个人中心数据
     * 获取
     * **/
    public MineApi mineApi= Http.http.createApi(MineApi.class);
    private void getPersonterData(){
        mineApi.getPersoneCener().enqueue(new CallBack<PersonCenterDto>() {
            @Override
            public void success(PersonCenterDto response) {
                Hawk.put(PreferenceKey.PERSON_CENTER,response);
                Hawk.put(PreferenceKey.IS_TUOYUN,response.getTrustFlag()+"");
                personCenterDto=response;
                tvName.setText(response.getName());
                tvComapny.setText(response.getCompanyName());
                Glide.with(mContext.getApplicationContext()).load(response.getPicture()).error(R.drawable.ico_head_defalut)
                        .into(headImage);
                Hawk.put(PreferenceKey.HZ_IS_RZ,response.getAuthState()+"");
                if(response.getAuthState()==1){////0 未认证 1已认证 2 认证中 3 认证失败
                    tvIdentification.setText("已认证");
                }else if(response.getAuthState()==2){
                    tvIdentification.setText("认证中");
                }else if(response.getAuthState()==3){
                    tvIdentification.setText("认证失败");
                }else  if(response.getAuthState()==0){
                    tvIdentification.setText("未认证");
                }
            }
            @Override
            public void fail(String code, String message) {

            }
        });
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true).
                navigationBarColor(R.color.bg).
                init();
    }
}
