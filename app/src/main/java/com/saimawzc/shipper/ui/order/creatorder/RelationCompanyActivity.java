package com.saimawzc.shipper.ui.order.creatorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.order.creatorder.AuthorityAdapter;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.order.creatorder.AuthorityDto;
import com.saimawzc.shipper.dto.order.creatorder.AuthorityDtoSerializ;
import com.saimawzc.shipper.weight.ClearTextEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/****
 * 关联公司
 * **/
public class RelationCompanyActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private AuthorityAdapter authorityAdapter;
    private List<AuthorityDto> mDatas=new ArrayList<>();
    int tag=0;

    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;

    @Override
    protected int getViewId() {
        return R.layout.activity_chooserelacom;
    }

    @OnClick({R.id.tvuplever,R.id.tvdownlever,R.id.tvOrder,R.id.llSearch})
    public void click(View view){
        switch (view.getId()){
            case R.id.llSearch:
                if(isEmptyStr(edSearch)){
                    context.showMessage("请输入关联公司关键字");
                    return;
                }
                mDatas.clear();
                getData();
                break;
            case R.id.tvdownlever:
                if(tag>=mDatas.size()){
                    return;
                }
                fresh(mDatas.get(tag).getJsonObject(),mDatas.get(tag).getCurremtleve());
                break;
            case R.id.tvuplever:
                if(tag>=mDatas.size()){
                    return;
                }
                if((mDatas.get(tag).getCurremtleve()<0)){
                    return;
                }
                upfresh(mDatas.get(tag).getCurremtleve());
                break;
            case R.id.tvOrder:
                if(mDatas.size()<=tag){
                    return;
                }
                Intent intent=new Intent();
                AuthorityDtoSerializ dtoSerializ=new AuthorityDtoSerializ();
                dtoSerializ.setCompanyName(mDatas.get(tag).getCompanyName());
                dtoSerializ.setId(mDatas.get(tag).getId());
                dtoSerializ.setParentId(mDatas.get(tag).getParentId());
                intent.putExtra("data",dtoSerializ);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
    @Override
    protected void init() {
        setToolbar(toolbar,"选择关联公司");
        llSearch.setVisibility(View.GONE);

        edSearch.hiddenIco();
        edSearch.setHint("请输入关联公司关键字");
        authorityAdapter=new AuthorityAdapter(mDatas,this);
        layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(authorityAdapter);
        edSearch.addTextChangedListener(textWatcher);
        //getData();
    }
    @Override
    protected void initListener() {
        authorityAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                tag=position;
                authorityAdapter.setmPosition(position);
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
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).fitsLayoutOverlapEnable(true).
                statusBarDarkFont(true).init();
    }
    List<JSONArray>tt=new ArrayList<>();
    private void getData(){
        context.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("companyName",edSearch.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getrelationCom(body).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                final String msg=response.body();
                context.dismissLoadingDialog();
                try {
                    if(TextUtils.isEmpty(msg)){
                        return;
                    }
                    JSONObject object=new JSONObject(msg);
                    JSONArray array =new JSONArray(object.getString("data"));
                    tt.add(array);
                    if(array.length()>0){
                        for(int i=0;i<array.length();i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                            AuthorityDto dto=new AuthorityDto();
                            dto.setCompanyName(jsonObject.getString("companyName"));
                            dto.setId(jsonObject.getString("id"));
                            dto.setParentId(jsonObject.getString("parentId"));
                            dto.setJsonObject(jsonObject);
                            dto.setCurremtleve(0);
                            mDatas.add(dto);
                        }
                        authorityAdapter.notifyDataSetChanged();
                        llSearch.setVisibility(View.GONE);
                        edSearch.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                context.dismissLoadingDialog();
            }
        });
    }


    private void fresh(JSONObject object,int currentlevel){
        try {
            JSONArray array =new JSONArray(object.getString("children"));
            if(!tt.contains(array)){
                tt.add(array);
            }
            if(array.length()>0){
                mDatas.clear();
                for(int i=0;i<array.length();i++){
                    JSONObject jsonObject = array.getJSONObject(i);
                    AuthorityDto dto=new AuthorityDto();
                    dto.setCompanyName(jsonObject.getString("companyName"));
                    dto.setId(jsonObject.getString("id"));
                    dto.setParentId(jsonObject.getString("parentId"));
                    dto.setJsonObject(jsonObject);
                    dto.setCurremtleve(currentlevel+1);
                    mDatas.add(dto);
                }
                authorityAdapter.setmPosition(0);
                tag=0;
                authorityAdapter.notifyDataSetChanged();
            }else {
                showMessage("没有了");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void upfresh(int currentlevel){
        JSONArray array=tt.get(currentlevel);
        try{
            if(array.length()>0){
                mDatas.clear();
                for(int i=0;i<array.length();i++){
                    JSONObject jsonObject = array.getJSONObject(i);
                    AuthorityDto dto=new AuthorityDto();
                    dto.setCompanyName(jsonObject.getString("companyName"));
                    dto.setId(jsonObject.getString("id"));
                    dto.setParentId(jsonObject.getString("parentId"));
                    dto.setJsonObject(jsonObject);
                    dto.setCurremtleve(currentlevel-1);
                    mDatas.add(dto);
                }
                authorityAdapter.setmPosition(0);
                tag=0;
                authorityAdapter.notifyDataSetChanged();
            }else {
                showMessage("没有了");
            }
        }catch (Exception E){

        }
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
            if (!TextUtils.isEmpty(edSearch.getText().toString())) {
                llSearch.setVisibility(View.VISIBLE);
                tvSearch.setText(edSearch.getText().toString());
            } else {
                llSearch.setVisibility(View.GONE);

            }
        }
    };


}
