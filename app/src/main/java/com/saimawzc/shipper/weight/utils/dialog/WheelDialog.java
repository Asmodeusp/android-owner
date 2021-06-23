package com.saimawzc.shipper.weight.utils.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;


import com.saimawzc.shipper.dto.myselment.AccountType;
import com.saimawzc.shipper.dto.order.bidd.CarTypeDo;
import com.saimawzc.shipper.dto.order.creatorder.UntilDto;
import com.saimawzc.shipper.weight.utils.listen.WheelListener;
import com.bigkoo.pickerview.OptionsPickerView;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 */
public class WheelDialog<T> {

    Context context;

    public List<T>listDatas;

    private List<String>stringLists;

    public WheelDialog(Context c, List<T>datas, List<String>strings){
        this.context=c;
        this.listDatas=datas;
        this.stringLists=strings;
    }
    public void Show(final WheelListener listener) {// 弹出条件选择器

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(context,
                new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {

                        if(listDatas.size()<=0){
                            return;
                        }
                        if(listDatas.get(options1)instanceof AccountType){
                            String name = ((AccountType) listDatas.get(options1)).getRecordStatusName();
                            String id = ((AccountType) listDatas.get(options1)).getRecordStatus();
                            listener.callback(name,id);
                        }else if(listDatas.get(options1)instanceof CarTypeDo){
                            String name = ((CarTypeDo) listDatas.get(options1)).getCarTypeName();
                            String id = ((CarTypeDo) listDatas.get(options1)).getId();
                            listener.callback(name,id);
                        }else if(listDatas.get(options1)instanceof UntilDto){
                            String name = ((UntilDto) listDatas.get(options1)).getUnit();
                            String id = ((UntilDto) listDatas.get(options1)).getId();
                            listener.callback(name,id);
                        }

                    }
                })
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置文字大小
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(stringLists);//条件选择器

        pvOptions.show();
    }
}
