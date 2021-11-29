package com.saimawzc.shipper.modle.mine.carleader;


import com.saimawzc.shipper.dto.carleader.BindBankDto;
import com.saimawzc.shipper.view.mine.carleader.CreatTeamView;

public interface CreatTeamModel {

    void bind(CreatTeamView view, BindBankDto dto);

    void getBigBank(CreatTeamView view, String str);

    void cardBin(CreatTeamView view, String cardNo);

}
