package com.saimawzc.shipper.ui.my.carmanage;

import static com.saimawzc.shipper.adapter.BaseAdapter.IS_RESH;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.carleader.CarLeaderListAdapter;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.constants.Constants;
import com.saimawzc.shipper.dto.carleader.CarLeaderListDto;
import com.saimawzc.shipper.presenter.mine.carleader.CarLeaderListPresenter;
import com.saimawzc.shipper.view.mine.carleader.CarLeaderListView;
import com.saimawzc.shipper.weight.utils.LoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 车队长管理
 * ***/
public class CarLearderListActivity extends BaseActivity
        implements CarLeaderListView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cy)
    RecyclerView rv;
    private CarLeaderListPresenter presenter;
    private CarLeaderListAdapter adapter;
    private List<CarLeaderListDto.leaderDto>mDatas=new ArrayList<>();
    private int page=1;
    private LoadMoreListener loadMoreListener;

    @Override
    protected int getViewId() {
        return R.layout.activity_carleader;
    }

    @Override
    protected void init() {
        setNeedOnBus(true);
        setToolbar(toolbar,"车队长管理");
        presenter= new CarLeaderListPresenter(this, CarLearderListActivity.this);
        adapter=new CarLeaderListAdapter(mDatas,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter.getCarLeaderList(page);

        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(IS_RESH==false){
                    page++;
                    isLoading = false;
                    presenter.getCarLeaderList(page);
                    IS_RESH=true;
                }

            }
        };
        rv.setOnScrollListener(loadMoreListener);
    }

    @Override
    protected void initListener() {

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",mDatas.get(position));
                readyGo(CarTeamInfoActivity.class,bundle);

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }
    @OnClick({R.id.tvadd})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvadd:
                readyGo(CreatCarLeaderActivity.class);
                break;
        }
    }


    @Override
    public void getList(CarLeaderListDto carLeaderListDto) {
        if(carLeaderListDto!=null){
            if(page==1){
                mDatas.clear();
            }
            if(carLeaderListDto.isLastPage()){
                loadMoreListener.isLoading = true;
                adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
            }else {
                loadMoreListener.isLoading = false;
                adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
            }
            adapter.addMoreData(carLeaderListDto.getList());
        }
        IS_RESH=false;
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
            if(str.equals(Constants.reshCarLeaderList)){
                page=1;
                presenter.getCarLeaderList(page);
            }
        }
    }
}
