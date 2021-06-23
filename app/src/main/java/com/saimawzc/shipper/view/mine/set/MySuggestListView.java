package com.saimawzc.shipper.view.mine.set;



import com.saimawzc.shipper.dto.set.SuggestDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface MySuggestListView extends BaseView {
    void getErrorList(List<SuggestDto> myErrDtos);

}
