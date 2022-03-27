package com.saimawzc.shipper.ui.homeindex.examine;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.main.PlanOrderExampleAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.main.waybillmanage.PlanOrderExampleDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/***
 * 计划订单
 * **/
public class PlanOrderExampleFragment extends BaseFragment {

    private PlanOrderExampleAdapter adapter;
    private List<PlanOrderExampleDto>mDatas=new ArrayList<>();
    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    public int initContentView() {
        return R.layout.fragment_planorderexample;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        adapter=new PlanOrderExampleAdapter(mDatas,mContext);
        layoutManager=new LinearLayoutManager(mContext);
        for(int i=0;i<4;i++){
            PlanOrderExampleDto dto=new PlanOrderExampleDto();
            dto.setName("航空母舰");
            dto.setWeiht("50吨");
            dto.setTime("2020-08-09");
            mDatas.add(dto);
        }
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);


    }

    @Override
    public void initData() {

    }
}
