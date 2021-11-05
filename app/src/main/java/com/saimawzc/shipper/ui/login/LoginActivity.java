package com.saimawzc.shipper.ui.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.presenter.login.LoginPresenter;
import com.saimawzc.shipper.ui.MainActivity;
import com.saimawzc.shipper.ui.WebViewActivity;
import com.saimawzc.shipper.ui.consignee.ConsigneeMainActivity;
import com.saimawzc.shipper.view.login.LoginView;
import com.saimawzc.shipper.weight.BottomDialogUtil;
import com.saimawzc.shipper.weight.utils.app.AppManager;
import com.saimawzc.shipper.weight.utils.dialog.BottomDialog;
import com.saimawzc.shipper.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.shipper.weight.utils.dialog.NormalDialog;
import com.saimawzc.shipper.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.shipper.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.Http;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import butterknife.BindView;
import butterknife.OnClick;

/**Created by Administrator on 2020/7/30.
 * 登录
 */
public class LoginActivity extends BaseActivity
        implements LoginView {

    @BindView(R.id.edit_account)EditText editAccount;
    @BindView(R.id.edit_password)EditText editPassword;
    @BindView(R.id.btn_Login_mask) TextView mLoginMask;
    @BindView(R.id.btn_acc_clear) ImageView mAccClear;
    @BindView(R.id.btn_passclear) ImageView mPassClear;
    @BindView(R.id.useAgreement)TextView useAgreement;
    @BindView(R.id.btnPrivacy)TextView btnPrivacy;
    @BindView(R.id.checkbox) CheckBox checkBox;
    @BindView(R.id.checkPrivaty)CheckBox checkPrivaty;
    private LoginPresenter presenter;

    @Override
    protected int getViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        useAgreement.setText("<<用户协议>>");
        btnPrivacy.setText("<<隐私声明>>");
        presenter=new LoginPresenter(this,this);
        if(!TextUtils.isEmpty(Hawk.get(PreferenceKey.USER_ACCOUNT,""))){
            editAccount.setText(Hawk.get(PreferenceKey.USER_ACCOUNT,""));
            mLoginMask.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(Hawk.get(PreferenceKey.PASS_WORD,""))){
            editPassword.setText(Hawk.get(PreferenceKey.PASS_WORD,""));
        }
        if(Hawk.get(PreferenceKey.ISREMEMBER_PASS,"").equals("1")){
            checkBox.setChecked(true);
        }
        if(TextUtils.isEmpty(Hawk.get(PreferenceKey.READ_PRIVACY,""))){
            showPrivacyDialog();
        }else {
            checkPrivaty.setChecked(true);
        }
    }
    @Override
    protected void initListener() {
        editAccount.addTextChangedListener(textWatcher);
        editPassword.addTextChangedListener(textWatcher);

    }
    @Override
    protected void onGetBundle(Bundle bundle) {

    }
    @OnClick({R.id.btn_acc_clear,R.id.btn_passclear,R.id.btnCode,
            R.id.btn_ForgetPsw,R.id.btn_Login,R.id.btn_Resister,R.id.useAgreement,R.id.btnPrivacy})
    public void click(View view ){
        switch (view.getId()){
            case R.id.btn_acc_clear://清除用户账号
                editAccount.setText("");
                break;
            case R.id.btn_passclear://清除密码
                editPassword.setText("");
                break;
            case R.id.btnCode://验证码登录
                readyGo(VerificationCodeLoginActivity.class);
                break;
            case R.id.btn_ForgetPsw://忘记密码
                readyGo(ForgetPassActivity.class);
                break;
            case R.id.btn_Resister://注册
                chooseIdentity(1);
                break;
            case R.id.useAgreement://用户协议
                WebViewActivity.loadUrl(context, "用户协议","https://www.wzcwlw.com/userAgreementHZ.html");
                break;
            case R.id.btnPrivacy://隐私声明
                WebViewActivity.loadUrl(context, "隐私声明","https://www.wzcwlw.com/privacyStatementHz.html");
                break;
            case R.id.btn_Login:
                if(TextUtils.isEmpty(editAccount.getText().toString())){
                    showMessage("请输入账号");
                    return;
                }
                if(TextUtils.isEmpty(editPassword.getText().toString())){
                    showMessage("请输入密码");
                    return;
                }
                if(!checkPrivaty.isChecked()){
                    showMessage("请先勾选同意后再进行登录");
                    return;
                }
                chooseIdentity(2);
                break;
        }
    }




    /***
     * 选择身份
     * **/
    private BottomDialog bottomDialog;
    private void chooseIdentity(final int type){//1注册  2登录
        if(bottomDialog==null){
            bottomDialog=new BottomDialog(LoginActivity.this, R.style.BaseDialog,R.layout.dialog_resister);
        }
        LinearLayout lineDrierv=bottomDialog.findViewById(R.id.lineDriver);
        LinearLayout lineCarrier=bottomDialog.findViewById(R.id.lineCarrier);
        TextView tvTip=bottomDialog.findViewById(R.id.tvTime);
        if(type==1){
            tvTip.setText("请选择你要以什么样的身份进行注册");
        }else {
            tvTip.setText("请选择你要以什么样的身份进行登录");
        }
        bottomDialog.show();
        lineDrierv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==1){
                    Bundle bundle=new Bundle();
                    bundle.putString("type","货主");
                    readyGo(RegisterActivity.class,bundle);
                }else if(type==2){
                    presenter.login(1,checkBox.isChecked());
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
                    presenter.login(4,checkBox.isChecked());
                }
            }
        });
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
            if (!TextUtils.isEmpty(editAccount.getText().toString()) && !TextUtils.isEmpty(editPassword.getText().toString())) {
                mLoginMask.setVisibility(View.GONE);
            } else {
                mLoginMask.setVisibility(View.VISIBLE);
            }
            //控制清除按钮是否显示
            if (TextUtils.isEmpty(editAccount.getText().toString())) {
                mAccClear.setVisibility(View.INVISIBLE);
            } else {
                mAccClear.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(editPassword.getText().toString())) {
                mPassClear.setVisibility(View.INVISIBLE);
            } else {
                mPassClear.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        try{
            if(bottomDialog!=null){
                bottomDialog.dismiss();
            }
        }catch (Exception e){
        }
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

    }

    @Override
    public String getPhone() {
        return editAccount.getText().toString();
    }
    @Override
    public String getPass() {
        return editPassword.getText().toString();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            if(bottomDialog!=null){
                bottomDialog.dismiss();
            }
        }catch (Exception e) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bottomDialog != null && !this.isFinishing()) {
            bottomDialog.dismiss();
        }
    }

    private NormalDialog dialog;
    private void showPrivacyDialog(){
        final BottomDialogUtil bottomDialogUtil = new BottomDialogUtil.Builder()
                .setContext(context) //设置 context
                .setContentView(R.layout.dialog_privacy) //设置布局文件
                .setOutSideCancel(false) //设置点击外部取消
                .builder()
                .show();
        TextView tvtips= (TextView) bottomDialogUtil.getItemView(R.id.tvtips);
        String tips="欢迎使用我找车货主，我们将通过《用户协议》和《隐私政策》帮助您了解我们为你提供的服务，以及收集处理个人信息的方式。";
        CustomClickableSpan clickableSpan = new CustomClickableSpan(LoginActivity.this,15,21,"用户协议");
        SpannableStringBuilder builder = new SpannableStringBuilder(tips);
        builder.setSpan(clickableSpan, 15, 21, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#4098FD")), 15, 21, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        CustomClickableSpan clickableSpan2 = new CustomClickableSpan(LoginActivity.this,22,28,"隐私政策");
        builder.setSpan(clickableSpan2, 22, 28, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#4098FD")), 22, 28, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tvtips.setMovementMethod(LinkMovementMethod.getInstance());
        tvtips.setText(builder);
        TextView tvcancel=(TextView) bottomDialogUtil.getItemView(R.id.tvcancel);
        TextView tvconfire=(TextView) bottomDialogUtil.getItemView(R.id.tvconfire);
        tvcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new NormalDialog(mContext).isTitleShow(false)
                        .content("需同意《个人信息保护指引》我们才能够继续为您提供服务")
                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                        .btnNum(2).btnText("放弃使用", "同意并继续");
                dialog.setOnBtnClickL(
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if(!context.isFinishing()){
                                    dialog.dismiss();
                                }
                                AppManager.get().finishAllActivity();
                            }
                        },
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if(!context.isFinishing()){
                                    dialog.dismiss();
                                }
                                Hawk.put(PreferenceKey.READ_PRIVACY,"true");
                                checkPrivaty.setChecked(true);

                            }
                        });
                dialog.show();
                if(bottomDialogUtil!=null){
                    bottomDialogUtil.dismiss();
                }
            }
        });
        tvconfire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hawk.put(PreferenceKey.READ_PRIVACY,"true");
                checkPrivaty.setChecked(true);
                if(bottomDialogUtil!=null){
                    bottomDialogUtil.dismiss();
                }
            }
        });
    }


    class  CustomClickableSpan extends ClickableSpan {
        private Context mContext;
        private String randTag;

        public CustomClickableSpan(Context context, int start,int end,String tag) {
            mContext = context;
            this.randTag=tag;
        }
        @Override
        public void onClick(View widget) {
            if (widget instanceof TextView) {
                if(randTag.equals("用户协议")){
                    WebViewActivity.loadUrl(context, "用户协议","https://www.wzcwlw.com/userAgreementHZ.html");
                }else if(randTag.equals("隐私政策")){
                    WebViewActivity.loadUrl(context, "隐私声明","https://www.wzcwlw.com/privacyStatementHz.html");
                }
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.clearShadowLayer();
        }
    }
}
