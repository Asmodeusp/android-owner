package com.saimawzc.shipper.view.mine.identificaion;

import com.saimawzc.shipper.dto.identification.CompanyDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2020/8/6.
 */

public interface ChooseCompanyView extends BaseView{

    void getCompany(List<CompanyDto>companyDtoList);
}
