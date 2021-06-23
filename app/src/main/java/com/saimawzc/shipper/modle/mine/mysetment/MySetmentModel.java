package com.saimawzc.shipper.modle.mine.mysetment;

import com.saimawzc.shipper.dto.SearchValueDto;
import com.saimawzc.shipper.view.mysetment.MySetmentView;

import java.util.List;

public interface MySetmentModel {

    void getList(int page, int type, List<SearchValueDto> dtos, MySetmentView view);

    void comfirm(int type, String id, MySetmentView view);
}
