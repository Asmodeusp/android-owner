package com.saimawzc.shipper.view.mine;

import com.saimawzc.shipper.dto.pic.AdListDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface BannerView extends BaseView {

    void getBanner(List<AdListDto>dtos);

}
