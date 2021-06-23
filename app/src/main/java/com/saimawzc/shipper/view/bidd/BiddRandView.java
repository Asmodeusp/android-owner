package com.saimawzc.shipper.view.bidd;

import com.saimawzc.shipper.dto.order.bidd.RankPageDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface BiddRandView extends BaseView {


    void getRandLise(List<RankPageDto.rankDto> dtos);
    void isLastPage(boolean isLastPage);

}
