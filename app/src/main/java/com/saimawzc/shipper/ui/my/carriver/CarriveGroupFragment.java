package com.saimawzc.shipper.ui.my.carriver;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.carrier.CarrierGroupAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.carrier.MyCarrierGroupDto;
import com.saimawzc.shipper.presenter.mine.carrive.CarriveGroupPresenter;
import com.saimawzc.shipper.ui.my.PersonCenterActivity;
import com.saimawzc.shipper.view.mine.carrive.CarriveGroupView;
import com.saimawzc.shipper.weight.SlideRecyclerView;
import com.saimawzc.shipper.weight.utils.dialog.BottomDialog;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/10.
 * 承运商分组
 */

public class CarriveGroupFragment extends BaseFragment implements CarriveGroupView {

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.rv) SlideRecyclerView rv;
    private CarrierGroupAdapter adapter;
    private List<MyCarrierGroupDto> mDatas=new ArrayList<>();
    @BindView(R.id.SwipeRefreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.right_btn)TextView btnRight;

    private CarriveGroupPresenter presenter;
    @Override
    public int initContentView() {
        return R.layout.fragment_carriergroup;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        userInfoDto=Hawk.get(PreferenceKey.USER_INFO);
        context.setToolbar(toolbar,"承运商分组");
        btnRight.setText("添加");
        btnRight.setVisibility(View.VISIBLE);
        presenter=new CarriveGroupPresenter(this,mContext);
        adapter = new CarrierGroupAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter.getCarreListiv();
    }

    @Override
    public void initData() {

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDatas.clear();
                presenter.getCarreListiv();
            }
        });

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("from","carriersecond");
                bundle.putString("id",mDatas.get(position).getId());
                bundle.putString("name",mDatas.get(position).getCysName());
                readyGo(PersonCenterActivity.class,bundle);
            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();

            }
        });

    }

    /***
     * 选择身份
     * **/
    private BottomDialog bottomDialog;
    private void showDialog(){
        if(bottomDialog==null){
            bottomDialog=new BottomDialog(mContext, R.style.BaseDialog,R.layout.dialog_addgroup);
        }
        bottomDialog.show();
        final EditText edGrupname=bottomDialog.findViewById(R.id.edgroupname);
        edGrupname.setText("");
        TextView btnOrder=bottomDialog.findViewById(R.id.btnOrder);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edGrupname.getText().toString())){
                   context.showMessage("请输入组名");
                    return;
                }

                addGroup(edGrupname.getText().toString());
                bottomDialog.dismiss();
            }
        });
    }
    private void addGroup(final String name){
         context.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("cysName",name);
            if(userInfoDto!=null){
                jsonObject.put("cysId",userInfoDto.getId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
       context. mineApi.addcarrivegroup(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                context.dismissLoadingDialog();
                context.showMessage("添加成功");
                mDatas.clear();
                presenter.getCarreListiv();
            }
            @Override
            public void fail(String code, String message) {
                context.dismissLoadingDialog();
                context.showMessage(message);
            }
        });
    }
    @Override
    public void showLoading() {
        context.showLoadingDialog();
    }

    @Override
    public void dissLoading() {
    context.dismissLoadingDialog();
    }

    @Override
    public void Toast(String str) {
       context.showMessage(str);
    }

    @Override
    public void oncomplete() {
      context.stopSwipeRefreshLayout(refreshLayout);
    }
    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);
    }
    @Override
    public void getCarriveList(List<MyCarrierGroupDto> groupDtos) {
        adapter.addMoreData(groupDtos);

    }




}
