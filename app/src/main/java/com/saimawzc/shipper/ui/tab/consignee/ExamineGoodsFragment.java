package com.saimawzc.shipper.ui.tab.consignee;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.GridViewAdapter;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.pic.PicDto;
import com.saimawzc.shipper.presenter.consign.ExamGoodPresenter;
import com.saimawzc.shipper.view.consign.ExamGoodsView;
import com.saimawzc.shipper.weight.RepeatClickUtil;
import com.saimawzc.shipper.weight.utils.GalleryUtils;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.loadimg.PlusImageActivity;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static com.saimawzc.shipper.constants.Constants.resSHr;

/****
 * 验货
 * **/
public class ExamineGoodsFragment extends BaseFragment implements ExamGoodsView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.editYh) EditText editExamGoods;
    @BindView(R.id.gridView) GridView gridView;
    private String id;
    private GridViewAdapter mGridViewAddImgAdapter;
    private ArrayList<String> mPicList = new ArrayList<>();
    private ExamGoodPresenter presenter;
    @Override
    public int initContentView() {
        return R.layout.fragment_examgood;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"验货");
        id=getArguments().getString("id");
        presenter=new ExamGoodPresenter(this,mContext);
    }

    @OnClick({R.id.tvOrder})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvOrder:
                if(context.isEmptyStr(editExamGoods)){
                    context.showMessage("请输入验货描述");
                    return;
                }
                if(mPicList.size()<=0){
                    context.showMessage("请选择图片");
                    return;
                }
                if(!RepeatClickUtil.isFastClick()){
                   context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                UploadpMore(mPicList);
                break;
        }
    }

    @Override
    public void initData() {
        mGridViewAddImgAdapter = new GridViewAdapter(mContext, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount()-1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过5张，才能点击
                    if (mPicList.size() == 9) {
                        //最多添加5张图片
                        viewPluImg(position);
                    } else {
                        presenter.showCamera(mContext);

                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
    }


    @Override
    public void successful(int type) {
        if(type==0){
            showCameraAction();
        }else {
            FunctionConfig functionConfig  = GalleryUtils.getFbdtFunction(9-mPicList.size());
            GalleryFinal.openGalleryMuti(1001,
                    functionConfig, mOnHanlderResultCallback);
        }
    }

    @Override
    public void showLoading() {
        context.showLoadingDialog();

    }

    @Override
    public void dissLoading() {
        context.dismissLoadingDialog();

    }

    @Override
    public void Toast(String str) {
        context.showMessage(str);

    }

    @Override
    public void oncomplete() {
        Intent intent=new Intent();
        context.setResult(RESULT_OK,intent);
        intent.setAction(resSHr);
        context.sendBroadcast(intent);
        context.finish();
    }
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback =
            new GalleryFinal.OnHanlderResultCallback() {
                @Override
                public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
                    if (resultList != null) {
                        for(int i=0;i<resultList.size();i++){
                            mPicList.add(resultList.get(i).getPhotoPath());
                        }
                        mGridViewAddImgAdapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onHanlderFailure(int requestCode, String errorMsg) {
                    context.showMessage(errorMsg);
                }
            };

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(mContext, PlusImageActivity.class);
        intent.putStringArrayListExtra("imglist", mPicList);
        intent.putExtra("currentpos", position);
        startActivityForResult(intent, 11);
    }

    private  void UploadpMore(List<String> paths){
        if(paths==null||paths.size()<=0){
            context.showMessage("图片文件不能为空");
            return;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        context.showLoadingDialog("图片上传中...");
        for (String path : paths) {//List<String> paths多个文件本地路径地址
            File tempFile=new File(path);
            if(tempFile!=null){
                File file = BaseActivity.compress(mContext,tempFile);
                RequestBody requestBody = RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"), file);
                builder.addFormDataPart("files",file.getName(),requestBody);//files 文件上传参数
            }
        }
        final List<MultipartBody.Part> parts = builder.build().parts();
        if(parts==null){
            return;
        }
        context.authApi.uploadMorepic(parts).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
                context.dismissLoadingDialog();
                if(!TextUtils.isEmpty(response.getUrl())){
                    presenter.daka(id,response.getUrl(),editExamGoods.getText().toString());

                }
            }
            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
                context.dismissLoadingDialog();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CAMERA && resultCode == RESULT_OK){//拍照
            if(!context.isEmptyStr(tempImage)) {
                mPicList.add(tempImage);
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }
        if(requestCode==11 && resultCode == RESULT_OK){
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra("imglist"); //要删除的图片的集合
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
        }
    }
}
