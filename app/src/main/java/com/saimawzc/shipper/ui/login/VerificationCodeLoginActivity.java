package com.saimawzc.shipper.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.presenter.login.VCodeLoginPresenter;
import com.saimawzc.shipper.ui.MainActivity;
import com.saimawzc.shipper.ui.WebViewActivity;
import com.saimawzc.shipper.view.login.VCodeLoginView;
import com.saimawzc.shipper.weight.RepeatClickUtil;
import com.saimawzc.shipper.weight.utils.dialog.BottomDialog;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.Http;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/7/30.
 * 验证码登录
 */

public class VerificationCodeLoginActivity extends BaseActivity implements VCodeLoginView {

    @BindView(R.id.edit_account)EditText edAccount;
    @BindView(R.id.edit_Code)EditText edCode;
    @BindView(R.id.btn_getCode)TextView btnCode;
    @BindView(R.id.btn_acc_clear)ImageView mAccClear;
    @BindView(R.id.useAgreement)TextView useAgreement;
    @BindView(R.id.btnPrivacy)TextView btnPrivacy;
    @BindView(R.id.btn_Login_mask) TextView mLoginMask;
    private VCodeLoginPresenter presenter;
    @BindView(R.id.check)
    CheckBox checkBox;
    @Override
    protected int getViewId() {
        return R.layout.activity_login_bycode;
    }

    @OnClick({R.id.btn_Login,R.id.btn_getCode,R.id.btn_Resister,
            R.id.loginByPass,R.id.btn_acc_clear,R.id.useAgreement,R.id.btnPrivacy})
    public  void click(View view){
        switch (view.getId()){
            case R.id.loginByPass://密码登录
                readyGo(LoginActivity.class);
                break;
            case R.id.btn_getCode://获取验证码
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                if(isEmptyStr(edAccount.getText().toString())){
                    showMessage("手机号码不能为空");
                    return;
                }
                if(edAccount.getText().toString().length()!=11){
                    showMessage("手机号码有误");
                    return;
                }
                if(!checkBox.isChecked()){
                    showMessage("请先勾选同意后再进行登录");
                    return;
                }
                presenter.getCode();
                break;
            case R.id.btn_Resister://立即注册
                chooseIdentity(1);
                break;
            case R.id.btn_Login:
                if(isEmptyStr(edAccount.getText().toString())){
                    showMessage("手机号码不能为空");
                    return;
                }
                if(isEmptyStr(edCode.getText().toString())){
                   showMessage("验证码不能为空");
                    return;}
                chooseIdentity(2);
                break;
            case R.id.btn_acc_clear:
                edAccount.setText("");
                break;
            case R.id.useAgreement://用户协议
                WebViewActivity.loadUrl(context, "用户协议","https://www.wzcwlw.com/userAgreement.html");
                break;
            case R.id.btnPrivacy://隐私声明
                WebViewActivity.loadUrl(context, "隐私声明","https://www.wzcwlw.com/privacyStatement.html");
                break;
        }
    }

    @Override
    protected void init() {
        if(!isEmptyStr(Hawk.get(PreferenceKey.ID,""))){//已经登录
            readyGo(MainActivity.class);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        useAgreement.setText("<<用户协议>>");
        btnPrivacy.setText("<<隐私声明>>");
        presenter=new VCodeLoginPresenter(this,this);
    }

    /***
     * 选择身份
     * **/
    private BottomDialog bottomDialog;
    private void chooseIdentity(final int type){//1注册  2登录
        if(bottomDialog==null){
            bottomDialog=new BottomDialog(VerificationCodeLoginActivity.this, R.style.BaseDialog,R.layout.dialog_resister);
        }
        bottomDialog.show();
        LinearLayout lineDrierv=bottomDialog.findViewById(R.id.lineDriver);
        LinearLayout lineCarrier=bottomDialog.findViewById(R.id.lineCarrier);

        lineDrierv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(type==1){
                    Bundle bundle=new Bundle();
                    bundle.putString("type","货主");
                    readyGo(RegisterActivity.class,bundle);
                }else if(type==2){
                    presenter.login(1);

                }

            }
        });
        lineCarrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(type==1){
                    Bundle bundle=new Bundle();
                    bundle.putString("type","收货人");
                    readyGo(RegisterActivity.class,bundle);
                }else if(type==2){
                    presenter.login(4);
                }


            }
        });
    }






    @Override
    protected void initListener() {
        edAccount.addTextChangedListener(textWatcher);
        edCode.addTextChangedListener(textWatcher);
    }
    @Override
    protected void onGetBundle(Bundle bundle) {
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void dissLoading() {
        dismissLoadingDialog();
    }

    @Override
    public void Toast(String str) {
        showMessage(str);
    }
    @Override
    public void oncomplete() {
        showMessage("登录成功");
        readyGo(MainActivity.class);
    }
    @Override
    public String getPhone() {
        return edAccount.getText().toString();
    }
    @Override
    public String getCode() {
        return edCode.getText().toString();
    }

    @Override
    public void changeStatus() {
        mCountDownTimer.start();
    }

    @Override
    public Context getContext() {
        return this;
    }

    /**
     * 监听输入框
     */
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void afterTextChanged(Editable editable) {
            //控制登录按钮是否可点击状态
            if (!TextUtils.isEmpty(edAccount.getText().toString()) && !TextUtils.isEmpty(edCode.getText().toString())) {
                mLoginMask.setVisibility(View.GONE);
            } else {
                mLoginMask.setVisibility(View.VISIBLE);
            }
            //控制清除按钮是否显示
            if (TextUtils.isEmpty(edAccount.getText().toString())) {
                mAccClear.setVisibility(View.INVISIBLE);
            } else {
                mAccClear.setVisibility(View.VISIBLE);
            }

        }
    };
    private CountDownTimer mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {//一分钟，间隔一秒
        @Override
        public void onTick(long millisUntilFinished) {
            btnCode.setEnabled(false);
            btnCode.setText(millisUntilFinished / 1000 + "s");
        }
        @Override
        public void onFinish() {
            btnCode.setText("获取验证码");
            btnCode.setEnabled(true);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if(bottomDialog!=null){
            bottomDialog.dismiss();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCountDownTimer!=null){
            mCountDownTimer.cancel();
        }
    }
}
