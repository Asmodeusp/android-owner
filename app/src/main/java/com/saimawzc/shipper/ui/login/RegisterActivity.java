package com.saimawzc.shipper.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.constants.Constants;
import com.saimawzc.shipper.presenter.login.ResisterPresenter;
import com.saimawzc.shipper.ui.MainActivity;
import com.saimawzc.shipper.ui.consignee.ConsigneeMainActivity;
import com.saimawzc.shipper.view.login.ResisterView;
import com.saimawzc.shipper.weight.RepeatClickUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/7/30.
 */
public class RegisterActivity extends BaseActivity implements ResisterView {

    @BindView(R.id.toolbar) android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.edit_phone)EditText editPhone;
    @BindView(R.id.edit_yzm)EditText editCode;
    @BindView(R.id.pass)EditText editPass;
    @BindView(R.id.orderpass)EditText editOrderPass;
    @BindView(R.id.useAgreement)TextView useAgreement;
    @BindView(R.id.checkbox)CheckBox checkBox;
    @BindView(R.id.btn_acc_clear) ImageView mAccClear;
    @BindView(R.id.btn_Login_mask) TextView mLoginMask;
    @BindView(R.id.btnGetCode)TextView tvCode;
    private ResisterPresenter  presenter;
    String type;


    @Override
    protected int getViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        setToolbar(toolbar,"注册");
        useAgreement.setText("<<用户协议>>");
        presenter=new ResisterPresenter(this,this);
    }

    @Override
    protected void initListener() {
        mContext=this;
        editPhone.addTextChangedListener(textWatcher);
        editCode.addTextChangedListener(textWatcher);
        editPass.addTextChangedListener(textWatcher);
        editOrderPass.addTextChangedListener(textWatcher);
    }

    @OnClick({R.id.btnGetCode,R.id.btn_Resister,
            R.id.btn_acc_clear,R.id.tvHaveCount})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvHaveCount:
                readyGo(LoginActivity.class);
                break;
            case R.id.btnGetCode:
                presenter.getCode();
                break;
            case R.id.btn_Resister:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                if(TextUtils.isEmpty(editCode.getText().toString())){
                    showMessage("验证码不能为空");
                    return;
                }
                if(TextUtils.isEmpty(editPass.getText().toString())){
                    showMessage("密码不能为空");
                    return;
                }
                if(!editPass.getText().toString().matches(Constants.PW_PATTERN)){
                    showMessage("至少八个字符，至少一个大写字母，一个小写字母，一个数字和一个特殊字符");
                    return;
                }
                if(TextUtils.isEmpty(editOrderPass.getText().toString())){
                    showMessage("请再次确认密码");
                    return;
                }
                if(!editOrderPass.getText().toString().equals(editPass.getText().toString())){
                    showMessage("两次输不一致入密码");
                    return;
                }
                if(!checkBox.isChecked()){
                    showMessage("提示未勾选同意和阅读《用户协议");
                    return;
                }
                presenter.resister();
                break;
            case R.id.btn_acc_clear:
                editPhone.setText("");
                break;
        }
    }


    @Override
    protected void onGetBundle(Bundle bundle) {
        type=bundle.getString("type");
    }

    private CountDownTimer mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {//一分钟，间隔一秒
        @Override
        public void onTick(long millisUntilFinished) {
            tvCode.setEnabled(false);
            tvCode.setText(millisUntilFinished / 1000 + "s");
        }
        @Override
        public void onFinish() {
            tvCode.setText("获取验证码");
            tvCode.setEnabled(true);
        }
    };


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
            if (!TextUtils.isEmpty(editOrderPass.getText().toString()) &&!TextUtils.isEmpty(editCode.getText().toString()) &&!TextUtils.isEmpty(editPhone.getText().toString()) && !TextUtils.isEmpty(editPass.getText().toString())) {
                mLoginMask.setVisibility(View.GONE);
            } else {
                mLoginMask.setVisibility(View.VISIBLE);
            }
            //控制清除按钮是否显示
            if (TextUtils.isEmpty(editPhone.getText().toString())) {
                mAccClear.setVisibility(View.INVISIBLE);
            } else {
                mAccClear.setVisibility(View.VISIBLE);
            }

        }
    };




    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void dissLoading() {
        dismissLoadingDialog();
    }

    @Override
    public void Toast(String sr) {
        showMessage(sr);
    }

    @Override
    public String getPhone() {
        return editPhone.getText().toString();
    }

    @Override
    public String getYzm() {
        return editCode.getText().toString();
    }


    @Override
    public String getPassWord() {
        return editPass.getText().toString();
    }

    @Override
    public void changeStatus() {
        mCountDownTimer.start();
    }

    @Override
    public String resiserType() {
        return type;
    }

    @Override
    public void oncomplete(int type) {
        if(type==100){
            presenter.login(editPass.getText().toString());
        }else {
            if(type==1){
                Intent intent = new Intent(mContext, MainActivity.class);
               startActivity(intent);
            }else if(type==4){
                Intent intent = new Intent(mContext, ConsigneeMainActivity.class);
                startActivity(intent);
            }
        }

    }

    @Override
    public void oncomplete() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCountDownTimer!=null){
            mCountDownTimer.cancel();
        }
    }
}
