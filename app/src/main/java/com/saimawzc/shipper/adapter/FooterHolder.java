package com.saimawzc.shipper.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;


/**
 * package:
 * author: nsj
 * description:上拉加载的footer
 * time: create at 2017/8/15
 */

public class FooterHolder extends RecyclerView.ViewHolder {
    public TextView tvFooter;

    public FooterHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        tvFooter = (TextView) itemView.findViewById(R.id.tv_footer);
    }
}
