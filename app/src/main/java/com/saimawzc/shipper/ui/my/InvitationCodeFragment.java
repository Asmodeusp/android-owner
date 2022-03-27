package com.saimawzc.shipper.ui.my;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.identification.PersonCenterDto;

import butterknife.BindView;
import cn.bertsir.zbar.utils.QRUtils;

public class InvitationCodeFragment extends BaseFragment {

    @BindView(R.id.imgInvitation)
    ImageView imageView;
    @BindView(R.id.tvCompanyName)
    TextView tvCompany;
    private PersonCenterDto personCenterDto;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int initContentView() {
        return R.layout.fragment_invitationcode;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"企业邀请码");
        try{
            personCenterDto= (PersonCenterDto) getArguments().getSerializable("data");
        }catch (Exception e){

        }

        if(personCenterDto==null){
            return;
        }
        tvCompany.setText(personCenterDto.getCompanyName());

        Bitmap qrCode = QRUtils.getInstance().createQRCode(personCenterDto.getCompanyId());
        imageView.setImageBitmap(qrCode);

    }

    @Override
    public void initData() {
    }
}
