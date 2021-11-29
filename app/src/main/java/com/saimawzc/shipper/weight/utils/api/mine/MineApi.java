package com.saimawzc.shipper.weight.utils.api.mine;

import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.FrameDto;
import com.saimawzc.shipper.dto.carleader.CarBrandDto;
import com.saimawzc.shipper.dto.carleader.CarLeaderListDto;
import com.saimawzc.shipper.dto.carleader.CarServiceSfInfoDto;
import com.saimawzc.shipper.dto.carleader.FaceQueryDto;
import com.saimawzc.shipper.dto.carleader.SearchTeamDto;
import com.saimawzc.shipper.dto.carleader.TaxiAreaDto;
import com.saimawzc.shipper.dto.carleader.TeamDelationDto;
import com.saimawzc.shipper.dto.carrier.MyCarrierGroupDto;
import com.saimawzc.shipper.dto.carrier.MyCarriveDto;
import com.saimawzc.shipper.dto.carrier.MycarrierGroupSecondDto;
import com.saimawzc.shipper.dto.identification.CompanyDto;
import com.saimawzc.shipper.dto.identification.OwnCrriverIdentificationDto;
import com.saimawzc.shipper.dto.identification.PersonCenterDto;
import com.saimawzc.shipper.dto.login.AreaDto;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.dto.order.bidd.CarTypeDo;
import com.saimawzc.shipper.dto.pic.AdListDto;
import com.saimawzc.shipper.dto.set.SuggestDto;
import com.saimawzc.shipper.weight.utils.http.JsonResult;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2020/8/1.
 * 我的
 */

public interface MineApi {
    /**
     * 货主认证userCardHz/addUserCardHz
     */

    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("admin/userCardHz/addUserCardHz")
    Call<JsonResult<EmptyDto>> hzInentification(@Body RequestBody array);

    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("admin/sys/getRotation")
    Call<JsonResult<List<AdListDto>>> getBanner(@Body RequestBody array);

    /**
     * 货主认证userCardHz/addUserCardHz
     */

    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("admin/userCardHz/updateUserCardHz")
    Call<JsonResult<EmptyDto>> rehzInentification(@Body RequestBody array);


    /**
     *  获取区域
     */
    //@FormUrlEncoded  archivesRegion/findRegion
    @Headers("Content-Type: application/json")
    @POST("admin/archivesRegion/findRegion")
    Call<JsonResult<List<AreaDto>>> getArea();

    /**
     *  个人中心数据sysUser/selectUserInfo
     */
    @Headers("Content-Type: application/json")
    @POST("admin/sysUser/selectUserInfo")
    Call<JsonResult<PersonCenterDto>> getPersoneCener();
    /**
     *  获取公司列表
     */
    //@FormUrlEncoded  archivesRegion/findRegion
    @Headers("Content-Type: application/json")
    @POST("admin/company/selectCompanyList")
    Call<JsonResult<List<CompanyDto>>> getCompanyList(@Body RequestBody array);

    /**
     * 修改个人数据 sysUser/modifyInfo
     */
    @Headers("Content-Type: application/json")
    @POST("admin/sysUser/modifyInfo")
    Call<JsonResult<EmptyDto>> modifyUserInfo(@Body RequestBody array);
    /**
     *  货主认证详情userCardHz/selectInfo
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userCardHz/selectInfo")
    Call<JsonResult<OwnCrriverIdentificationDto>> identification();

    /**
     *  /carrierBind/addCarrierBind
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carrierBind/addCarrierBind")
    Call<JsonResult<EmptyDto>> addcarrivegroup(@Body RequestBody array);

    /**
     *  carrierBind/findCarrierBindGroup
     查询承运商/分组
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carrierBind/findCarrierBindGroup")
    Call<JsonResult<List<MyCarrierGroupDto>>> getCarriveList();


    /**
     *  /获取承云商分组二级页面carrierBind/findCarrierBind

     */
    @Headers("Content-Type: application/json")
    @POST("admin/carrierBind/findCarrierBind")
    Call<JsonResult<List<MycarrierGroupSecondDto>>> getCarrvesecond(@Body RequestBody array);

    /**
     *  /获取承云根据手机号码获取承运商userCardCys/selectByPhone
     根据手机号查询承运商认证信息
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userCardCys/selectByPhone")
    Call<JsonResult<MyCarriveDto>> searchCarrivebyphone(@Body RequestBody array);

    /**
     *  carrierBind/delCarrierBind删除承运商/分组  删除承运商
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carrierBind/delCarrierBind")
    Call<JsonResult<EmptyDto>> deleteCarrive(@Body RequestBody array);

    /**
     切换角色 /sysUser/changeRole
     切换角色
     */
    @Headers("Content-Type: application/json")
    @POST("admin/sysUser/changeRole")
    Call<JsonResult<UserInfoDto>> changeRole(@Body RequestBody array);

    /**
     *  添加收货人
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userCardShr/addShr")
    Call<JsonResult<EmptyDto>> addShr(@Body RequestBody array);
    /**
     获取弹框
     */
    @Headers("Content-Type: application/json")
    @POST("admin/sys/bulletFrame/selectByRole")
    Call<JsonResult<FrameDto>> getFram(@Body RequestBody array);


    /**
     新增建议
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userOpinion/add")
    Call<JsonResult<EmptyDto>> addSuggest(@Body RequestBody array);

    /**
     获取建议列表
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userOpinion/selectByInfo")
    Call<JsonResult<List<SuggestDto>>> mySuggestList(@Body RequestBody array);

    /**
     根据ID获取建议详情
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userOpinion/selectById")
    Call<JsonResult<SuggestDto>> mySuggesDelarion(@Body RequestBody array);


    /**
     获取车队长列表
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/selectCarCaptainList")
    Call<JsonResult<CarLeaderListDto>> getCarLeaderList(@Body RequestBody array);

    /**
     删除车队长
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/deleteCarCaptain")
    Call<JsonResult<EmptyDto>> deleteCarTeam(@Body RequestBody array);


    /**
     车队长删除服务方
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/deleteFwf")
    Call<JsonResult<EmptyDto>> teamLeaderdelService(@Body RequestBody array);

    /**
     查询车队长详情
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/selectCarCaptainInfo")
    Call<JsonResult<TeamDelationDto>> getTeamDelation(@Body RequestBody array);

    /**
     车队长添加服务方搜索
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/searchFwf")
    Call<JsonResult<SearchTeamDto>> getSearchTeam(@Body RequestBody array);

    /**
     车队长添加服务方
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/addFwf")
    Call<JsonResult<EmptyDto>> addCarService(@Body RequestBody array);

    /**
     车队长添加服务方
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/joinFwf")
    Call<JsonResult<CarServiceSfInfoDto>> getSFiNFO(@Body RequestBody array);
    /**
     *  车辆/类型    carType/selectAll
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carType/selectAll")
    Call<JsonResult<List<CarTypeDo>>> getCarType(@Body RequestBody array);
    /**
     *  车辆品牌 carBrand/selectAll
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carBrand/selectAll")
    Call<JsonResult<List<CarBrandDto>>> getCarBrand(@Body RequestBody array);

    /**
     车队长添加服务方
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/selectFwfById")
    Call<JsonResult<FaceQueryDto.Facedata>> getFaceINfo(@Body RequestBody array);

    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/addCarCaptain")
    Call<JsonResult<EmptyDto>> createam(@Body RequestBody array);

    /**
     *  获取区域
     */
    @Headers("Content-Type: application/json")
    @POST("admin/sysRegion/findSysRegion")
    Call<JsonResult<List<TaxiAreaDto>>> getAreaTaxi(@Body RequestBody array);
}
