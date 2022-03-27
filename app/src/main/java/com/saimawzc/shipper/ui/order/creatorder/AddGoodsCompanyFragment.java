package com.saimawzc.shipper.ui.order.creatorder;

import static com.saimawzc.shipper.constants.AppConfig.reshWayBillAdd;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

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
/**
 * 新增发货商和收货商
 * **/

public class AddGoodsCompanyFragment extends BaseFragment {

    @BindView(R.id.edcompany) EditText edCompany;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String type;//1表示供货单位 2.表示收货单位

    @Override
    public int initContentView() {
        return R.layout.fragment_addgooscmpany;
    }

    @Override
    public void initView() {
        type=getArguments().getString("type");
        mContext=getActivity();
        if(type.equals("1")){//供货单位
            context.setToolbar(toolbar,"新增发货商");
        }else {//收货单位
            context.setToolbar(toolbar,"新增收货商");
        }


    }

    @OnClick({R.id.tvOrder})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvOrder://新增
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                addCompany();
                break;
        }


    }

    @Override
    public void initData() {

    }
    private void addCompany(){
        if(TextUtils.isEmpty(edCompany.getText().toString())){
            context.showMessage("请输入供货单位");
            return;

        }
        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("name",edCompany.getText().toString());
            jsonObject.put("type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        context.orderApi.addCompany(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                Intent intent = new Intent();
                intent.setAction(reshWayBillAdd);
                context.sendBroadcast(intent);
                context.finish();
            }

            @Override
            public void fail(String code, String message) {
                context.showMessage(message);

            }
        });

    }
}
