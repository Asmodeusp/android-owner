package com.saimawzc.shipper.view.mine.carleader;



import com.saimawzc.shipper.dto.carleader.MsBankDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface CreatTeamView extends BaseView {
    void getBigBankList(List<MsBankDto> dtos);
    void getCarBin(MsBankDto carBinDto);



}
