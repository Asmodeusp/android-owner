package com.saimawzc.shipper.view.mine.carleader;




import com.saimawzc.shipper.dto.carleader.TaxiAreaDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface TaxiAdressView extends BaseView {


    void getadressList(List<TaxiAreaDto> dtos);


}
