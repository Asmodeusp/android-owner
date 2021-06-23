package com.saimawzc.shipper.view.mine.identificaion;

import com.saimawzc.shipper.dto.identification.CompanyDto;
import com.saimawzc.shipper.dto.identification.OwnCrriverIdentificationDto;
import com.saimawzc.shipper.view.BaseView;

/**
 * Created by Administrator on 2020/8/3.
 */

public interface CargoOwnerCarrierView extends BaseView {

    void OnDealCamera(int ype);
    String sringImgPositive();
    String  sringImgOtherSide();
    String getUser();
    String getArea();
    String getIdNum();

    String proviceName();
    String proviceId();
    String cityName();
    String cityId();
    String countryName();
    String countryId();
    CompanyDto getCompany();
    void identification(OwnCrriverIdentificationDto dto);

}
