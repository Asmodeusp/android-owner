package com.saimawzc.shipper.view.order;

import com.saimawzc.shipper.dto.identification.CompanyDto;
import com.saimawzc.shipper.dto.order.ConsignmentCompanyDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

/***
 * 托运公司
 * */
public interface ConsignCompanyView extends BaseView {

    void getCompany(List<ConsignmentCompanyDto> companyDtoList);
    void stopResh();

}
