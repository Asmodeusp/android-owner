package com.saimawzc.shipper.view.mine.carleader;


import com.saimawzc.shipper.dto.carleader.FaceQueryDto;
import com.saimawzc.shipper.dto.carleader.TeamDelationDto;
import com.saimawzc.shipper.view.BaseView;

public interface TeamDelationView extends BaseView {
    void getDelation(TeamDelationDto delationDto);

    void getPersonifo(FaceQueryDto.Facedata facedata, String id);


}
