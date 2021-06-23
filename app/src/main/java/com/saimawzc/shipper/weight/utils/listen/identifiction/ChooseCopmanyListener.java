package com.saimawzc.shipper.weight.utils.listen.identifiction;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.identification.CompanyDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/6.
 */

public interface ChooseCopmanyListener extends BaseListener{

    void getCompanyList(List<CompanyDto> companyDtoList);

}
