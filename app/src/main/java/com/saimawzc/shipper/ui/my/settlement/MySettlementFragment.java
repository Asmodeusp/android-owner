package com.saimawzc.shipper.ui.my.settlement;

import static android.app.Activity.RESULT_OK;
import static com.saimawzc.shipper.constants.Constants.reshAccount_confirm;
import static com.saimawzc.shipper.constants.Constants.reshAccount_unconfirm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.SearchValueDto;
import com.saimawzc.shipper.dto.myselment.MySetmentPageQueryDto;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.weight.CaterpillarIndicator2;
import com.saimawzc.shipper.weight.utils.api.bms.BmsApi;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.http.Http;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/****
 * 我的结算
 * */
public class MySettlementFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pager_title)
    CaterpillarIndicator2 pagerTitle;
    @BindView(R.id.viewpage)
    ViewPager viewPager;
    private WaitOrderSetmentFragment waitOrderSetmentFragment;
    private AlreadyOrderSetmentFragment alreadyOrderSetmentFragment;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;
//    @BindView(R.id.rightImgBtn)
//    ImageView tvRightBtn;

    @BindView(R.id.right_btn)TextView tvRightBtn;
    public final int seachCode=111;

    @Override
    public int initContentView() {
        return R.layout.fragment_mysetlement;
    }

    @Override
    public void initView() {
        context.setToolbar(toolbar,"我的结算单");
        waitOrderSetmentFragment=new WaitOrderSetmentFragment();
        alreadyOrderSetmentFragment=new AlreadyOrderSetmentFragment();
        list = new ArrayList<Fragment>();
        list.add(waitOrderSetmentFragment);
        list.add(alreadyOrderSetmentFragment);

        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }
            @Override
            public int getCount() {
                return list.size();
            }
        };
        viewPager.setAdapter(mAdapter);
        getLsit();
    }

    @Override
    public void initData() {
        tvRightBtn.setVisibility(View.VISIBLE);
        tvRightBtn.setText("查询");
        tvRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Bundle bundle=new Bundle();
                bundle.putString("from","queryaccount");
                readyGoForResult(OrderMainActivity.class,seachCode,bundle);
            }
        });

        // tvRightBtn.setOnClickListener(new View.OnClickListener() {
        //                    @Override
        //                    public void onClick(View v) {
        //                        final PopupWindowUtil popupWindowUtil = new PopupWindowUtil.Builder()
        //                                .setContext(mContext) //设置 context
        //                                .setContentView(R.layout.dialog_mysetmemt) //设置布局文件
        //                                .setOutSideCancel(true) //设置点击外部取消
        //                                .setwidth(ViewGroup.LayoutParams.WRAP_CONTENT)
        //                                .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
        //                                .setFouse(true)
        //                                .builder()
        //                                .showAsLaction(tvRightBtn, Gravity.RIGHT,0,0);
        //                        popupWindowUtil.setOnClickListener(new int[]{R.id.rladd,R.id.rlquery}, new View.OnClickListener() {
        //                            @Override
        //                            public void onClick(View v) {
        //                                Bundle bundle=null;
        //                                switch (v.getId()){
        //                                    case R.id.rladd://添加结算单
        //                                        bundle=new Bundle();
        //                                        bundle.putString("from","waitaccount");
        //                                        readyGo(OrderMainActivity.class,bundle);
        //                                        break;
        //                                    case R.id.rlquery://查询结算单
        //                                        bundle=new Bundle();
        //                                        bundle.putString("from","queryaccount");
        //                                        readyGoForResult(OrderMainActivity.class,seachCode,bundle);
        //                                        break;
        //
        //                                }
        //                            }
        //                        });
        //                    }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==seachCode&& resultCode == RESULT_OK){
            ArrayList<SearchValueDto>  searchValueDtos = (ArrayList<SearchValueDto>) data.getSerializableExtra("list");
            Intent intent = new Intent();
            Bundle bundle=new Bundle();
            bundle.putSerializable("list",searchValueDtos);
            intent.putExtras(bundle);
            Log.e("msg","当前"+viewPager.getCurrentItem());
            if(viewPager.getCurrentItem()==1){//
                intent.setAction(reshAccount_confirm);
            }else if(viewPager.getCurrentItem()==0){//未确认
                intent.setAction(reshAccount_unconfirm);
            }
            context.sendBroadcast(intent);

        }


    }

    public BmsApi bmsApi= Http.http.createApi(BmsApi.class);
    private void getLsit(){

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",1);
            jsonObject.put("pageSize",20);
            jsonObject.put("checkStatus",2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.getmysetmentlist(body).enqueue(new CallBack<MySetmentPageQueryDto>() {
            @Override
            public void success(MySetmentPageQueryDto response) {
                if(response==null||response.getList()==null||response.getList().size()<=0){
                    List<CaterpillarIndicator2.TitleInfo> titleInfos = new ArrayList<>();
                    titleInfos.add(new CaterpillarIndicator2.TitleInfo("未确认",0));
                    titleInfos.add(new CaterpillarIndicator2.TitleInfo("已确认",0));
                    pagerTitle.init(0, titleInfos, viewPager);
                }else {
                    int count=response.getList().get(0).getUnsettleNum();
                    List<CaterpillarIndicator2.TitleInfo> titleInfos = new ArrayList<>();
                    titleInfos.add(new CaterpillarIndicator2.TitleInfo("未确认",count));
                    titleInfos.add(new CaterpillarIndicator2.TitleInfo("已确认"));
                    pagerTitle.init(0, titleInfos, viewPager);
                }
            }
            @Override
            public void fail(String code, String message) {
                List<CaterpillarIndicator2.TitleInfo> titleInfos = new ArrayList<>();
                titleInfos.add(new CaterpillarIndicator2.TitleInfo("未确认",0));
                titleInfos.add(new CaterpillarIndicator2.TitleInfo("已确认"));
                pagerTitle.init(0, titleInfos, viewPager);
            }
        });
    }
}
