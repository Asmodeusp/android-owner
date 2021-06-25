package com.saimawzc.shipper.weight;

import android.content.Context;
import android.view.View;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.weight.utils.dialog.BottomDialog;


public class BottomDialogUtil {

    private BottomDialog bottomDialog;
    Builder builder;


    private Context mContext;
    public BottomDialogUtil(Builder builder){
        mContext = builder.context;
        this.builder=builder;
        bottomDialog =
                new BottomDialog(mContext, R.style.BaseDialog,builder.contentviewid);
        bottomDialog.setCancelable(builder.outsidecancel);
        bottomDialog.setCanceledOnTouchOutside(builder.outsidecancel);
    }
    /**
     * popup 消失
     */
    public void dismiss(){
        if (bottomDialog != null){
            bottomDialog.dismiss();
        }
    }

    public BottomDialogUtil show(){
        if (bottomDialog != null){
            bottomDialog.show();
        }
        return this;
    }

    /**
     * 根据id获取view
     * @param viewid
     * @return
     */
    public View getItemView(int viewid){
        if (bottomDialog != null){
            return this.bottomDialog.findViewById(viewid);
        }
        return null;
    }

    public void setOnClickListener(int viewid,View.OnClickListener listener){
        View view = getItemView(viewid);
        view.setOnClickListener(listener);
    }
    /**
     * builder 类
     */
    public static class Builder{
        private int contentviewid;
        private boolean outsidecancel;
        private Context context;
        public Builder setContext(Context context){
            this.context = context;
            return this;
        }

        public Builder setContentView(int contentviewid){
            this.contentviewid = contentviewid;
            return this;
        }


        public Builder setOutSideCancel(boolean outsidecancel){
            this.outsidecancel = outsidecancel;
            return this;
        }

        public BottomDialogUtil builder(){
            return new BottomDialogUtil(this);
        }
    }

    public Builder getBulider(){
        return builder;
    }

}
