package com.saimawzc.shipper.ui.my.carmanage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.carleader.TeamDelationAdapter;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.constants.Constants;
import com.saimawzc.shipper.dto.carleader.CarLeaderListDto;
import com.saimawzc.shipper.dto.carleader.FaceQueryDto;
import com.saimawzc.shipper.dto.carleader.TeamDelationDto;
import com.saimawzc.shipper.presenter.mine.carleader.TeamDelationPresenter;
import com.saimawzc.shipper.view.mine.carleader.TeamDelationView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/***车队长详情****/
public class CarTeamInfoActivity extends BaseActivity implements TeamDelationView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private TeamDelationPresenter presenter;

    private CarLeaderListDto.leaderDto leaderDto;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvOpenHang)
    TextView tvOpenBrach;
    @BindView(R.id.tvbankNum)
    TextView tvBankNum;
    @BindView(R.id.tvCardNum)
    TextView tvCardNum;
    private TeamDelationAdapter adapter;
    private List<TeamDelationDto.TeamList>mDatas=new ArrayList<>();
    @BindView(R.id.cy) RecyclerView rv;
    @BindView(R.id.rightImgBtn)
    ImageView imgRight;




    @Override
    protected int getViewId() {
        return R.layout.activity_cattexminfo;
    }

    @Override
    protected void init() {
        leaderDto= (CarLeaderListDto.leaderDto) getIntent().getSerializableExtra("data");
        if(leaderDto!=null){
            setToolbar(toolbar,leaderDto.getUserName());
            setNeedOnBus(true);
            imgRight.setVisibility(View.VISIBLE);
            imgRight.setImageResource(R.drawable.ico_add_serice_people);
            presenter=new TeamDelationPresenter(this,this);
            presenter.getDelation(leaderDto.getId());
        }


    }

    @Override
    protected void initListener() {
        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("id",leaderDto.getId());
                readyGo(TeamGroupSearchActivity.class,bundle);

            }
        });
    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    private int status=-1;
    @Override
    public void getDelation(TeamDelationDto delationDto) {
        if(delationDto!=null){
            tvName.setText(delationDto.getClientName());
            tvOpenBrach.setText(delationDto.getBankName());
            tvBankNum.setText(delationDto.getCardId());
            tvCardNum.setText(delationDto.getIdCardNum());
            if(adapter==null){
                adapter=new TeamDelationAdapter(mDatas,this);
            }
            mDatas.clear();
            mDatas.addAll(delationDto.getList());
            LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
            rv.setLayoutManager(layoutManager);
            rv.setAdapter(adapter);

            adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if(mDatas.size()<=position){
                        return;
                    }
                    presenter.getPerInfo(mDatas.get(position).getId());
                    status=mDatas.get(position).getStatus();
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
        }

    }

    @Override
    public void getPersonifo(FaceQueryDto.Facedata facedata, String id) {
        if(facedata!=null){
            Bundle bundle=new Bundle();
            bundle.putString("id",id);
            bundle.putSerializable("data",facedata);
            bundle.putInt("status",status);
            readyGo(AddCarServiceInfo.class,bundle);
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshCarLeader(String str) {
        if(!TextUtils.isEmpty(str)){

            if(str.equals(Constants.reshTeamDelation)){
                presenter.getDelation(leaderDto.getId());
            }
        }
    }
}
