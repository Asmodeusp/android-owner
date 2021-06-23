package com.saimawzc.shipper.modle.mine.identification;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.weight.utils.listen.identifiction.ChooseCopmanyListener;

/**
 * Created by Administrator on 2020/8/6.
 */

public interface ChooseCompanyModel{

    void getCompanyList(String companyName, ChooseCopmanyListener listener);

}
