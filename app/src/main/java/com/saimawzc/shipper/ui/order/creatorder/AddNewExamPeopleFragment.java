package com.saimawzc.shipper.ui.order.creatorder;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/****
 * 新增验货人
 * **/

public class AddNewExamPeopleFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvreal_name)
    EditText edRealName;
    @BindView(R.id.tvphone)EditText edPhone;



    @Override
    public int initContentView() {
        return R.layout.fragment_addexampeople;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"添加验货人");

    }

    @Override
    public void initData() {

    }
    @OnClick({R.id.submit})
    public void click(View view){
        switch (view.getId()){
            case R.id.submit:
                if(context.isEmptyStr(edRealName)){
                    context.showMessage("请输入真实姓名");
                    return;
                }
                if(edPhone.getText().toString().length()!=11){
                    context.showMessage("输入的手机号有误");
                    return;
                }
                if(context.isEmptyStr(edPhone)){
                    context.showMessage("请输入手机号码");
                    return;
                }
                addShr();
                break;
        }
    }
    private void addShr(){
        context.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userName",edRealName.getText().toString());
            jsonObject.put("userAccount",edPhone.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        context.mineApi.addShr(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                context.dismissLoadingDialog();
                context.finish();

            }
            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
                context.dismissLoadingDialog();
            }
        });
    }
}
