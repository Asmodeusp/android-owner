package com.saimawzc.shipper.weight.utils.api.bms;



import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.myselment.AccountDelationDto;
import com.saimawzc.shipper.dto.myselment.AccountQueryPageDto;
import com.saimawzc.shipper.dto.myselment.AccountType;
import com.saimawzc.shipper.dto.myselment.MySetmentPageQueryDto;
import com.saimawzc.shipper.dto.myselment.WaitComfirmQueryPageDto;
import com.saimawzc.shipper.dto.myselment.WaitDispatchQueryPageDto;
import com.saimawzc.shipper.weight.utils.http.JsonResult;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BmsApi {

    /**
     * 对账单列表
     */
    @Headers("Content-Type: application/json")
    @POST("bms/common/record/selectRecordList")
    Call<JsonResult<AccountQueryPageDto>> getAccountList(@Body RequestBody array);


    /**
     * 对账单列表
     */
    @Headers("Content-Type: application/json")
    @POST("bms/common/record/selectByIdDetails")
    Call<JsonResult<AccountDelationDto>> getAccountDelation(@Body RequestBody array);


    /**
     * 对账单列表
     */
    @Headers("Content-Type: application/json")
    @POST("bms/common/settle/selectSettleList")
    Call<JsonResult<MySetmentPageQueryDto>> getmysetmentlist(@Body RequestBody array);

    /**
     * 获取结算大单列表
     */
    @Headers("Content-Type: application/json")
    @POST("bms/cys/recordSettle/selectPendSettlePlan")
    Call<JsonResult<WaitComfirmQueryPageDto>> getBigorderlist(@Body RequestBody array);

    /**
     * 获取结算大单列表
     */
    @Headers("Content-Type: application/json")
    @POST("bms/cys/recordSettle/selectPendSettleSmall")
    Call<JsonResult<WaitDispatchQueryPageDto>> getsmallOrderList(@Body RequestBody array);
    /**
     *   sysUser/register
     */
    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("bms/cys/recordSettle/add")
    Call<JsonResult<EmptyDto>> addSetment(@Body RequestBody array);
//
    /**
     *   sysUser/register
     */
    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("bms/common/settle/editSettleStatus")
    Call<JsonResult<EmptyDto>> confirm(@Body RequestBody array);

    /**
     * 获取结算大单列表
     */
    @Headers("Content-Type: application/json")
    @POST("bms/common/record/selectRecordStatus")
    Call<JsonResult<List<AccountType>>> getaccountType(@Body RequestBody array);
}
