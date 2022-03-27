package com.saimawzc.shipper.ui.my.identification;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.identification.CompanyAdapter;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.identification.CompanyDto;
import com.saimawzc.shipper.presenter.mine.identification.ChooseCompanyPresenter;
import com.saimawzc.shipper.view.mine.identificaion.ChooseCompanyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/8/6.
 *
 * 选择公司CC
 */

public class ChooseCompanyActivity  extends BaseActivity implements ChooseCompanyView{

    @BindView(R.id.edsearch)EditText editSearch;
    @BindView(R.id.tvCannel)TextView tvCannel;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tvSearch)TextView tvSearch;
    @BindView(R.id.llSearch)LinearLayout llSearch;
    private CompanyAdapter adapter;
    private List<CompanyDto>mDatas=new ArrayList<>();
    private ChooseCompanyPresenter presenter;
    @BindView(R.id.llchoose)LinearLayout llchoose;


    @Override
    protected int getViewId() {
        return R.layout.activity_choosecompany;
    }

    @OnClick({R.id.llSearch,R.id.tvCannel})
    public void click(View view){
        switch (view.getId()){
            case R.id.llSearch://搜索
                mDatas.clear();
                presenter.getCompanyList(editSearch.getText().toString());
                break;
            case R.id.tvCannel:
                finish();
                break;
        }
    }

    @Override
    protected void init() {

        presenter=new ChooseCompanyPresenter(this,this);
        adapter = new CompanyAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter.getCompanyList("");
    }

    @Override
    protected void initListener() {
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(editSearch.getText().toString())){
                    llSearch.setVisibility(View.GONE);
                    tvSearch.setText("");
                }else {
                    llSearch.setVisibility(View.VISIBLE);
                    tvSearch.setText(editSearch.getText().toString());
                }

            }
        });


        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent();
                intent.putExtra("data",mDatas.get(position));
                setResult(RESULT_OK, intent);
                finish(); //结束当前的activity的生命周期

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
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

    }

    @Override
    public void getCompany(List<CompanyDto> companyDtoList) {
        llSearch.setVisibility(View.GONE);
        adapter.addMoreData(companyDtoList);

    }
    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).statusBarDarkFont(true).titleBar(llchoose).
                init();

    }
}
