package com.saimawzc.shipper.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.FrameDto;
import com.saimawzc.shipper.dto.VersonDto;
import com.saimawzc.shipper.dto.identification.PersonCenterDto;
import com.saimawzc.shipper.ui.login.LoginActivity;
import com.saimawzc.shipper.ui.login.SplashActivity;
import com.saimawzc.shipper.ui.my.PersonCenterActivity;
import com.saimawzc.shipper.ui.tab.SendCarIndexFragment;
import com.saimawzc.shipper.ui.tab.BiddingListFragment;
import com.saimawzc.shipper.ui.tab.MainIndexFragment;
import com.saimawzc.shipper.ui.tab.MineFragment;
import com.saimawzc.shipper.ui.tab.WaybillFragment;
import com.saimawzc.shipper.weight.BottomDialogUtil;
import com.saimawzc.shipper.weight.utils.app.AppManager;
import com.saimawzc.shipper.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.shipper.weight.utils.dialog.NormalDialog;
import com.saimawzc.shipper.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.shipper.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.shipper.weight.utils.dialog.UpdateDialog;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;
import com.saimawzc.shipper.weight.utils.update.InstallUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jpeng.jptabbar.BadgeDismissListener;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.werb.permissionschecker.PermissionChecker;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainActivity extends BaseActivity
        implements OnTabSelectListener,BadgeDismissListener {

    @Titles
    private static final int[] mTitles = {R.string.tab1,R.string.tab2,R.string.tab3,R.string.tab4,R.string.tab5};

    @SeleIcons
    private static final int[] mSeleIcons = {R.drawable.ico_index_syblue,R.drawable.ico_indexyundan_blue,R.drawable.ico_indexjingjia_blue,R.drawable.ico_indexpaiche_blue,R.drawable.ico_index_minr_blue};

    @NorIcons
    private static final int[] mNormalIcons = {R.drawable.ico_index_sygray, R.drawable.ico_indexyundan_gray, R.drawable.ico_indexjingjia_gray, R.drawable.ico_indexpaiche_gray, R.drawable.ico_indexmine_gray};

    @BindView(R.id.tabbar)JPTabBar mTabbar;
    private Fragment[] fragments;
    private MainIndexFragment mainIndexFragment;
    private WaybillFragment waybillFragment;
    private BiddingListFragment biddingListFragment;
    private SendCarIndexFragment sendCarIndexFragment;
    private MineFragment mineFragment;
    private int from=0;
    public  final String[] PERMISSIONSq = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA
    };
    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }
    @Override
    protected void init() {
        if(!isLogin()){
            readyGo(LoginActivity.class);
        }
        initpermissionChecker();
        if(permissionChecker.isLackPermissions(PERMISSIONSq)){
            permissionChecker.requestPermissions();
        }else {
        }
        try{
            if(getIntent()!=null){
                from=getIntent().getIntExtra("from",0);
            }
        }catch (Exception e){
        }
        userInfoDto=Hawk.get(PreferenceKey.USER_INFO);
        mainIndexFragment = new MainIndexFragment();
        waybillFragment = new WaybillFragment();
        biddingListFragment = new BiddingListFragment();
        mineFragment = new MineFragment();
        sendCarIndexFragment=new SendCarIndexFragment();
        mTabbar.setTabListener(this);

        fragments = new Fragment[]{mainIndexFragment, waybillFragment, biddingListFragment,sendCarIndexFragment, mineFragment};
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, mainIndexFragment)
                .add(R.id.fragmentContainer, waybillFragment).hide(waybillFragment).show(mainIndexFragment)
                .commitAllowingStateLoss();
        mTabbar.setTabListener(this);
        //设置Badge消失的代理
        mTabbar.setDismissListener(this);
        if(from!=0){
            mTabbar.setSelectTab(from);
        }
        initAccessToken();//初始化b百度云读取身份证
        checkPermission();
        try {
            getPersonterData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(Hawk.get(PreferenceKey.CITY_INFO)==null){
            cacheArae();
        }
        initWithApiKey();
        getDialog();

    }

    @Override
    protected void initListener() {
        if (Build.VERSION.SDK_INT >= 26) {
            if(getPackageManager()==null){
                return;
            }
            //来判断应用是否有权限安装apk
            boolean installAllowed= getPackageManager().canRequestPackageInstalls();
            //有权限
            if (installAllowed) {
            } else {
                //无权限 申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, 1000);
            }
        } else {

        }
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (MainActivity.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    MainActivity.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                } else {
                    //申请读
                    Log.e("msg","申请读写权限");
                    //这里就是权限打开之后自己要操作的逻辑
                }
            }
        }

    }
    private boolean isBreak = false;
    @Override
    public void onBackPressed() {
        if (isBreak) {
            AppManager.get().finishAllActivity();
        } else {
            isBreak = true;
            showMessage("再次点击退出应用!");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isBreak = false;
                }
            }, 3000);
        }
    }
    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    //扫描后返回结果的处理
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
    /**
     * 判断该用户是否已经认证
     * ***/
    private NormalDialog dialog;
    private void getPersonterData(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mineApi.getPersoneCener().enqueue(new CallBack<PersonCenterDto>() {
                    @Override
                    public void success(final PersonCenterDto response) {
                        Hawk.put(PreferenceKey.PERSON_CENTER,response);
                        if(response.getAuthState()==0){
                            if(dialog==null){//未认证
                                dialog = new NormalDialog(mContext).isTitleShow(false)
                                        .content("您当前未认证，请前往认证？")
                                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                                        .btnNum(2).btnText("确认", "取消");
                            }
                            dialog.setOnBtnClickL(
                                    new OnBtnClickL() {//搜索
                                        @Override
                                        public void onBtnClick() {
                                            Bundle bundle=new Bundle();
                                            bundle.putString("from","huozhucarrier");
                                            readyGo(PersonCenterActivity.class,bundle);
                                            if(!isFinishing()){
                                                dialog.dismiss();
                                            }
                                            if(!isFinishing()){
                                                dialog.dismiss();
                                            }
                                        }
                                    },
                                    new OnBtnClickL() {//注册
                                        @Override
                                        public void onBtnClick() {
                                            dialog.dismiss();
                                        }
                                    });
                            dialog.show();
                        }else {
                        }
                    }
                    @Override
                    public void fail(String code, String message) {
                        context.showMessage(message);
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            unBindForApp();
            if(dialog!=null){
                if(!context.isFinishing()){
                    dialog.dismiss();
                }
            }
        }catch (Exception e) {
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState){

    }
    int  currentTabIndex=0;
    @Override
    public void onTabSelect(int index) {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().
                    beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragmentContainer, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        currentTabIndex = index;
    }
    @Override
    public void onClickMiddle(View middleBtn) {
    }
    @Override
    public void onDismiss(int position) {//红点消失
    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).statusBarDarkFont(true).
                navigationBarColor(R.color.bg).
                init();
    }
    public void checkPermission(){
        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if(!haveInstallPermission){
                //没有权限让调到设置页面进行开启权限；
                Uri packageURI = Uri.parse("package:" + getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                startActivityForResult(intent, 10086);
            }else{
                Log.e("msg","有权限");
                //有权限，执行自己的逻辑；
            }
        }else{
            //其他android版本，可以直接执行安装逻辑；
        }
    }
    private void getDialog(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("","");//state状态 （1.以添加 2.待确认）
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON,jsonObject.toString());
                mineApi.getFram(body).enqueue(new CallBack<FrameDto>() {
                    @Override
                    public void success(FrameDto response) {
                        if(response!=null){
                            if(!TextUtils.isEmpty(response.getContent())){
                                showDialog(response.getContent());
                            }
                        }
                    }
                    @Override
                    public void fail(String code, String message) {

                    }
                });
            }
        });

    }
    private void showDialog(String contect){
        final BottomDialogUtil bottomDialogUtil = new BottomDialogUtil.Builder()
                .setContext(context) //设置 context
                .setContentView(R.layout.dialog_sijitip) //设置布局文件
                .setOutSideCancel(false) //设置点击外部取消
                .builder()
                .show();
        TextView tvOrder= (TextView) bottomDialogUtil.getItemView(R.id.tvOrder);
        TextView tvcontect=(TextView) bottomDialogUtil.getItemView(R.id.tvcontect);
        tvcontect.setText(contect);
        tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialogUtil.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(Hawk.get(PreferenceKey.isChange_or_login, "")) &&
                (Hawk.get(PreferenceKey.isChange_or_login, "").equals("true"))) {
            Intent intent = new Intent();
            intent.setAction("reshChange");
            this.sendBroadcast(intent);
            Hawk.put(PreferenceKey.isChange_or_login, "false");
        }
    }




}
