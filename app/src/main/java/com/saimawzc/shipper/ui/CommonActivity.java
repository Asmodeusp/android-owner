package com.saimawzc.shipper.ui;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.identification.PersonCenterDto;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import butterknife.BindView;

public class CommonActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    String title="";
    @Override
    protected int getViewId() {
        return R.layout.activity_common_activity;
    }
    @Override
    protected void init() {
        if(getIntent()!=null){
            try{
                title=getIntent().getStringExtra("title");
            }catch (Exception e){
            }

        }
        setToolbar(toolbar,title);
        showLoadingDialog();
        getPersonterData();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }
    private void getPersonterData(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mineApi.getPersoneCener().enqueue(new CallBack<PersonCenterDto>() {
                    @Override
                    public void success(final PersonCenterDto response) {
                        dismissLoadingDialog();
                        Hawk.put(PreferenceKey.PERSON_CENTER,response);

                    }
                    @Override
                    public void fail(String code, String message) {
                        dismissLoadingDialog();
                        context.showMessage(message);
                    }
                });
            }
        });
    }
}
