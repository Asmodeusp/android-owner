package com.saimawzc.shipper.ui.order.creatorder;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.order.creatorder.ChooseCarTypeAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.bidd.CarTypeDo;
import com.saimawzc.shipper.weight.ClearTextEditText;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/***
 * 发货商和收货商  客户类型(1.发货地址 2.收货地址)
 * **/

public class ChooseCarTypeFragment extends BaseFragment {
    private ChooseCarTypeAdapter adapter;
    private List<CarTypeDo>mDatas=new ArrayList<>();
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.right_btn) TextView tvRightBtn;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tvOrder)TextView  tvOrder;
    @BindView(R.id.edsearch) ClearTextEditText edSearch;
    @BindView(R.id.llSearch) LinearLayout llSearch;
    @BindView(R.id.tvSearch) TextView tvSearch;
    @BindView(R.id.viewSearch)View viewSearch;

    @Override
    public int initContentView() {
        return R.layout.fragment_choosegoodscompany;
    }
    @Override
    public void initView() {

        llSearch.setVisibility(View.GONE);
        viewSearch.setVisibility(View.GONE);
        edSearch.hiddenIco();
        mContext=getActivity();
        context.setToolbar(toolbar,"车型列表");
        adapter=new ChooseCarTypeAdapter(mDatas,mContext,5);
        tvOrder.setVisibility(View.VISIBLE);
        edSearch.addTextChangedListener(textWatcher);
        layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        getCarType();

    }
    private ArrayList<String>idList=new ArrayList<>();
    private ArrayList<String>nameList=new ArrayList<>();
    @Override
    public void initData() {
      adapter.setOnItemCheckListener(new ChooseCarTypeAdapter.OnItemCheckListener() {
          @Override
          public void onItemClick(View view, int position, boolean isselect) {
              if(mDatas.size()<=position){
                  return;
              }
              if(isselect==true){
                  idList.add(mDatas.get(position).getId());
                  nameList.add(mDatas.get(position).getCarTypeName());
              }else {
                  idList.remove(mDatas.get(position).getId());
                  nameList.remove(mDatas.get(position).getCarTypeName());
              }
          }
      });


        tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idList.size()<=0){
                    context.showMessage("请选择车型");
                    return;
                }
                Intent intent=new Intent();
                intent.putExtra("data",idList);
                intent.putExtra("nameList",nameList);
                context.setResult(RESULT_OK, intent);
                context. finish();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCarType();
            }
        });
    }
    private void getCarType(){
        context.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        try {
            jsonObject.put("type",1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        context.authApi.getCarType(body).enqueue(new CallBack<List<CarTypeDo>>() {
            @Override
            public void success(List<CarTypeDo> response) {
                if(mDatas!=null){
                    mDatas.clear();
                }
                context.dismissLoadingDialog();
                adapter.addMoreData(response);
            }

            @Override
            public void fail(String code, String message) {
                context.dismissLoadingDialog();
                context.showMessage(message);
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
            if (!TextUtils.isEmpty(edSearch.getText().toString())) {
                llSearch.setVisibility(View.VISIBLE);
                tvSearch.setText(edSearch.getText().toString());
            } else {
                llSearch.setVisibility(View.GONE);

            }
        }
    };

}
