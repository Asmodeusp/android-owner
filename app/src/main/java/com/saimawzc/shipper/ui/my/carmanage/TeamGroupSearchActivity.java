package com.saimawzc.shipper.ui.my.carmanage;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.carleader.SearchTeamDto;
import com.saimawzc.shipper.presenter.mine.carleader.SearchTeamPresenter;
import com.saimawzc.shipper.view.mine.carleader.SearchTeamView;
import com.saimawzc.shipper.weight.CircleImageView;
import com.saimawzc.shipper.weight.utils.loadimg.ImageLoadUtil;

import butterknife.BindView;
import butterknife.OnClick;

/****
 * 成员搜索
 * ***/
public class TeamGroupSearchActivity extends BaseActivity implements SearchTeamView {

    private String id;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private SearchTeamPresenter presenter;
    @BindView(R.id.edcarNo)
    EditText edCarNo;
    @BindView(R.id.edphone)
    EditText edPhone;
    @BindView(R.id.rlserach)
    RelativeLayout rlSearch;

    @BindView(R.id.rightImgBtn)
    ImageView imgRight;

    @BindView(R.id.avatar_min)
    CircleImageView circleImageView;

    @BindView(R.id.tvCarNo)
    TextView tvCarNo;
    @BindView(R.id.tvName)
    TextView tvName;
    private SearchTeamDto searchTeamDto;


    @Override
    protected int getViewId() {
        return R.layout.activity_teamsearch;
    }

    @Override
    protected void init() {
        setToolbar(toolbar,"添加服务方");
        id=getIntent().getStringExtra("id");
        presenter=new SearchTeamPresenter(this,this);
        imgRight.setVisibility(View.VISIBLE);
        imgRight.setImageResource(R.drawable.ico_search_blue);

    }
    @OnClick({R.id.tvSubmit,R.id.tvJoin})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvSubmit:
                if(isEmptyStr(edCarNo)){
                    showMessage("请输入车牌号");
                    return;
                }
                if(isEmptyStr(edPhone)){
                    showMessage("请输入手机号");
                    return;
                }
                presenter.getData(edCarNo.getText().toString(),edPhone.getText().toString());
                break;
            case R.id.tvJoin:
                if(isEmptyStr(id)){
                    showMessage("车队长ID不能为空");
                    return;
                }
                if(searchTeamDto==null){
                    showMessage("人员信息不能为空");
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("teamId",id);
                bundle.putSerializable("persondata",searchTeamDto);
                readyGo(AddCarServiceInfo.class,bundle);
                finish();
                break;
        }

    }


    @Override
    protected void initListener() {
        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTeamDto=null;
                rlSearch.setVisibility(View.GONE);
                edCarNo.setText("");
                edPhone.setText("");

            }
        });
    }
    @Override
    protected void onGetBundle(Bundle bundle) {
    }
    @Override
    public void getPersonList(SearchTeamDto dtos) {
        if(dtos!=null){
            rlSearch.setVisibility(View.VISIBLE);
            searchTeamDto=dtos;
            ImageLoadUtil.displayImage(this,searchTeamDto.getPicture(),circleImageView);
            tvCarNo.setText(searchTeamDto.getCarNo());
            tvName.setText(searchTeamDto.getSjName()+"   "+searchTeamDto.getSjPhone());
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
}
