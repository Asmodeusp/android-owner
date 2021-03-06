package com.saimawzc.shipper.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.gyf.immersionbar.ImmersionBar;
import com.jpeng.jptabbar.BadgeDismissListener;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.FrameDto;
import com.saimawzc.shipper.dto.identification.PersonCenterDto;
import com.saimawzc.shipper.dto.order.selectEndStatuesDto;
import com.saimawzc.shipper.ui.login.LoginActivity;
import com.saimawzc.shipper.ui.my.PersonCenterActivity;
import com.saimawzc.shipper.ui.tab.BiddingListFragment;
import com.saimawzc.shipper.ui.tab.MainIndexFragment;
import com.saimawzc.shipper.ui.tab.MineFragment;
import com.saimawzc.shipper.ui.tab.SendCarIndexFragment;
import com.saimawzc.shipper.ui.tab.WaybillFragment;
import com.saimawzc.shipper.weight.BottomDialogUtil;
import com.saimawzc.shipper.weight.utils.app.AppManager;
import com.saimawzc.shipper.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.shipper.weight.utils.dialog.NormalDialog;
import com.saimawzc.shipper.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.shipper.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

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
        //??????Badge???????????????
        mTabbar.setDismissListener(this);
        if(from!=0){
            mTabbar.setSelectTab(from);
        }
        initAccessToken();//?????????b????????????????????????
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
        getSelectEndStatues();
        try{
            String path="";
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                path= getExternalFilesDir("Caches").getAbsolutePath()+"/nxshiper/";
            }else {
                path= Environment.getExternalStorageDirectory().getAbsolutePath() + "/nxshiper";
            }
            File file=new File(path);
            deleteFile(file);
        }catch (Exception e){
        }
        Integer endStatues = Hawk.get("endStatues");

    }

    private void getSelectEndStatues() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                orderApi.selectEndStatues().enqueue(new CallBack<selectEndStatuesDto>() {
                    @Override
                    public void success(final selectEndStatuesDto response) {
                        if (response!=null) {
                            if (response.getEndStatus()!=1) {
                                mTabbar.HideBadge(1);
                            } else {
                                mTabbar.ShowBadge(1, response.getNum()+"");
                            }
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
    protected void initListener() {
        if (Build.VERSION.SDK_INT >= 26) {

        }

    }
    private boolean isBreak = false;
    @Override
    public void onBackPressed() {
        if (isBreak) {
            AppManager.get().finishAllActivity();
        } else {
            isBreak = true;
            showMessage("????????????????????????!");
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

    //??????????????????????????????
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
    /**
     * ?????????????????????????????????
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
                            if(dialog==null){//?????????
                                dialog = new NormalDialog(mContext).isTitleShow(false)
                                        .content("???????????????????????????????????????")
                                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                                        .btnNum(2).btnText("??????", "??????");
                            }
                            dialog.setOnBtnClickL(
                                    new OnBtnClickL() {//??????
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
                                    new OnBtnClickL() {//??????
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
    public void onDismiss(int position) {//????????????
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
                //??????????????????????????????????????????????????????
                Uri packageURI = Uri.parse("package:" + getPackageName());
//                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
//                startActivityForResult(intent, 10086);
            }else{
                Log.e("msg","?????????");
                //????????????????????????????????????
            }
        }else{
            //??????android??????????????????????????????????????????
        }
    }
    private void getDialog(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("","");//state?????? ???1.????????? 2.????????????
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
                .setContext(context) //?????? context
                .setContentView(R.layout.dialog_sijitip) //??????????????????
                .setOutSideCancel(false) //????????????????????????
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


    public void deleteFile(File file) {
        if(!file.exists()){
            return;
        }
        if(file!=null){
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if(files==null||files.length==0){
                  return;
                }
                for (int i = 0; i < files.length; i++) {
                    File f = files[i];
                    if(f!=null){
                        deleteFile(f);
                    }
                }
                //file.delete();//?????????????????????????????????????????????????????????
            } else if (file.exists()) {
                file.delete();
            }
        }
    }

}
