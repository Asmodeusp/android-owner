package com.saimawzc.shipper.ui.order.creatorder;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.weight.RepeatClickUtil;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.saimawzc.shipper.constants.AppConfig.reshContact;
import static com.saimawzc.shipper.constants.AppConfig.reshWayBillAdd;

/***
 * 新建联系人
 * **/
public class AddContractsFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edcontractname)
    EditText edContractName;
    @BindView(R.id.edphone)
    EditText edPhone;
    @BindView(R.id.edemail)
    EditText edEmal;

    @Override
    public int initContentView() {
        return R.layout.fragment_addcontracts;
    }

    @OnClick(R.id.btnOrder)
    public void click(View view){
        if(!RepeatClickUtil.isFastClick()){
            context.showMessage("您操作太频繁，请稍后再试");
            return;
        }
        if(context.isEmptyStr(edContractName)){
            context.showMessage("请输入联系人");
            return;
        }
        if(context.isEmptyStr(edPhone)){
            context.showMessage("请输入联系电话");
            return;
        }
        addContract();
    }


    @Override
    public void initView() {
        context.setToolbar(toolbar,"新增联系人");

    }

    @Override
    public void initData() {

    }
    private void addContract(){
        JSONObject jsonObject=new JSONObject();
        context.showLoadingDialog();

        try {
            jsonObject.put("phone",edPhone.getText().toString());
            jsonObject.put("name",edContractName.getText().toString());
            jsonObject.put("email",edEmal.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        context.orderApi.addContracts(body).enqueue(new CallBack<EmptyDto>() {

            @Override
            public void success(EmptyDto response) {
                Intent intent = new Intent();
                intent.setAction(reshContact);
                context.sendBroadcast(intent);
                context.finish();
                context.dismissLoadingDialog();
            }

            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
                context.dismissLoadingDialog();
            }
        });
    }
}
