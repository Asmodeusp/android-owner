package com.saimawzc.shipper.ui.my.settlement.acount;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bigkoo.pickerview.OptionsPickerView;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.SearchValueDto;
import com.saimawzc.shipper.dto.myselment.AccountType;
import com.saimawzc.shipper.dto.order.ConsignmentCompanyDto;
import com.saimawzc.shipper.dto.order.creatorder.AuthorityDtoSerializ;
import com.saimawzc.shipper.ui.order.creatorder.ChooseAuthortityActivity;
import com.saimawzc.shipper.ui.order.creatorder.ChooseConsignCompanyActivity;
import com.saimawzc.shipper.weight.TimeChooseDialogUtil;
import com.saimawzc.shipper.weight.utils.api.bms.BmsApi;
import com.saimawzc.shipper.weight.utils.dialog.WheelDialog;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.http.Http;
import com.saimawzc.shipper.weight.utils.listen.TimeChooseListener;
import com.saimawzc.shipper.weight.utils.listen.WheelListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/***
 * 对账查询
 * **/
public class AccountQueryFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvStartTime)
    TextView tvStartTime;
    @BindView(R.id.tvEndTime)TextView tvEndTime;
    @BindView(R.id.tvaccountcompany)TextView tvAccountcompany;//对账公司
    @BindView(R.id.tvtycompany)TextView tvTuoYunCompany;//托运公司
    @BindView(R.id.tvBillType)TextView tvBillType;
    @BindView(R.id.edCarNo)
    EditText edCarNo;
    @BindView(R.id.edWulIao)EditText edWuLiao;
    private TimeChooseDialogUtil timeChooseDialogUtil;
    ArrayList<SearchValueDto> list=new ArrayList<>();
    public  final int CHOOSE_COMPANY=101;
    public  final int CONSIGNcOMPANY=102;//托运公司
    @OnClick({R.id.btnsearch,R.id.tvStartTime,R.id.tvEndTime
            ,R.id.rlaccountcompany,R.id.rltycompany,R.id.rlbilltype})
    public void  click(View view){
        switch (view.getId()){
            case R.id.btnsearch://搜索

                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                list.clear();
                if(authorityDto!=null){
                    SearchValueDto dto=new SearchValueDto();
                    dto.setSearchName("companyId");
                    dto.setGetSearchValue(authorityDto.getId());
                    list.add(dto);
                }
                if(!TextUtils.isEmpty(tvStartTime.getText().toString())){
                    SearchValueDto dto=new SearchValueDto();
                    dto.setSearchName("startTime");
                    dto.setGetSearchValue(tvStartTime.getText().toString());
                    list.add(dto);

                }
                if(!TextUtils.isEmpty(tvEndTime.getText().toString())){
                    SearchValueDto dto=new SearchValueDto();
                    dto.setSearchName("endTime");
                    dto.setGetSearchValue(tvEndTime.getText().toString());
                    list.add(dto);

                }
                if(consignmentCompanyDto!=null){
                    SearchValueDto dto=new SearchValueDto();
                    dto.setSearchName("handCompanyId");
                    dto.setGetSearchValue(consignmentCompanyDto.getId());
                    list.add(dto);
                }
                if(!context.isEmptyStr(edCarNo)){
                    SearchValueDto dto=new SearchValueDto();
                    dto.setSearchName("carNo");
                    dto.setGetSearchValue(edCarNo.getText().toString());
                    list.add(dto);
                }
                if(!context.isEmptyStr(edWuLiao)){
                    SearchValueDto dto=new SearchValueDto();
                    dto.setSearchName("materialsName");
                    dto.setGetSearchValue(edWuLiao.getText().toString());
                    list.add(dto);
                }

                bundle.putSerializable("list",list);
                intent.putExtras(bundle);
                context.setResult(RESULT_OK, intent);
                context.finish();
                break;
            case R.id.tvStartTime:
                if(timeChooseDialogUtil==null){
                    timeChooseDialogUtil=new TimeChooseDialogUtil(mContext);
                }
                timeChooseDialogUtil.showDialogSeconds(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        tvStartTime.setText(result);
                    }
                    @Override
                    public void cancel() {
                        timeChooseDialogUtil.dissDialog();
                    }
                });
                break;
            case R.id.tvEndTime:
                if(timeChooseDialogUtil==null){
                    timeChooseDialogUtil=new TimeChooseDialogUtil(mContext);
                }
                timeChooseDialogUtil.showDialogSeconds(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        tvEndTime.setText(result);
                    }
                    @Override
                    public void cancel() {
                        timeChooseDialogUtil.dissDialog();
                    }
                });
                break;
            case R.id.rlaccountcompany:
                readyGoForResult(ChooseAuthortityActivity.class,CHOOSE_COMPANY);
                break;
            case R.id.rltycompany:
                readyGoForResult(ChooseConsignCompanyActivity.class,CONSIGNcOMPANY);
                break;
            case R.id.rlbilltype:
//                oplitions.clear();
//                oplitions.add("已签收未对账");
//                oplitions.add("已对账未结算");
//                oplitions.add("存疑单据");
//                oplitions.add("已结算");
//                oplitions.add("拒绝");
//                initOptionPicker(oplitions,2);
                getAccountType();
                break;
        }
    }

    private List<String> oplitions=new ArrayList<>();


    @Override
    public int initContentView() {
        return R.layout.frogment_accountquery;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"对账查询");

    }

    @Override
    public void initData() {

    }

    AuthorityDtoSerializ authorityDto;//组织机构
    ConsignmentCompanyDto consignmentCompanyDto;//托运公司
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CHOOSE_COMPANY&& resultCode == RESULT_OK){
            authorityDto = (AuthorityDtoSerializ) data.getSerializableExtra("data");
            if(authorityDto!=null){
                tvAccountcompany.setText(authorityDto.getCompanyName());
            }
        }
        if(requestCode==CONSIGNcOMPANY&& resultCode == RESULT_OK){//托运公司
            consignmentCompanyDto = (ConsignmentCompanyDto) data.getSerializableExtra("data");
            if(consignmentCompanyDto!=null){
                tvTuoYunCompany.setText(consignmentCompanyDto.getCompanyName());
            }
        }

    }

    private OptionsPickerView optionsPickerView;//底部滚轮实现
    private void initOptionPicker(final List<String> optionList, final int type){

        optionsPickerView = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String str = optionList.get(options1);
                tvBillType.setText(str);

            }
        }).setCancelColor(Color.GRAY).
                setSubmitColor(Color.RED).build();
        optionsPickerView.setNPicker(optionList,null,null);

        optionsPickerView.show();
    }

    public BmsApi bmsApi= Http.http.createApi(BmsApi.class);
    private WheelDialog wheelDialog;
    private void getAccountType(){
        context.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.getaccountType(body).enqueue(new CallBack<List<AccountType>>() {
            @Override
            public void success(List<AccountType> response) {
                context.dismissLoadingDialog();
                if(response.size()<=0){
                    return;
                }
                List<String> strings=new ArrayList<>();
                for(int i=0;i<response.size();i++){
                    strings.add(response.get(i).getRecordStatusName());
                }

                wheelDialog=new WheelDialog(mContext,response,strings);
                wheelDialog.Show(new WheelListener() {
                    @Override
                    public void callback(String name, String id) {
                        tvBillType.setText(name);
                        SearchValueDto dto=new SearchValueDto();
                        dto.setSearchName("recordStatus");
                        dto.setGetSearchValue(id+"");
                        list.add(dto);
                    }
                });
            }

            @Override
            public void fail(String code, String message) {
                context.dismissLoadingDialog();
                context.showMessage(message);
            }
        });
    }

}
