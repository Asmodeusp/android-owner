package com.saimawzc.shipper.ui.my;

import static android.app.Activity.RESULT_OK;
import static com.saimawzc.shipper.base.BaseActivity.PERMISSIONS_CAMERA;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.identification.PersonCenterDto;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.dto.pic.PicDto;
import com.saimawzc.shipper.presenter.mine.PersonCenterPresenter;
import com.saimawzc.shipper.view.mine.PersonCenterView;
import com.saimawzc.shipper.weight.CircleImageView;
import com.saimawzc.shipper.weight.utils.GalleryUtils;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/7.
 * 中心
 */

public class PersonCenerFragment extends BaseFragment implements PersonCenterView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imgHead)
    CircleImageView headIamge;
    @BindView(R.id.tvSex)TextView tvSex;
    @BindView(R.id.tvPhone)TextView tvPhone;
    private PersonCenterDto personCenterDto;
    private UserInfoDto userInfoDto;
    private PersonCenterPresenter presenter;
    private String stringHead;

    @Override
    public int initContentView() {
        return R.layout.fragment_personcenter;
    }

    @OnClick({R.id.rlhead,R.id.rlsex,R.id.tvSave})
    public void click(View view){
        switch (view.getId()){
            case R.id.rlhead:
                if(permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)){
                    permissionChecker.requestPermissions();
                    context.showMessage("未获取到者相机权限");
                }else {
                    presenter.showCamera();
                }

                break;
            case R.id.rlsex:
                break;
            case R.id.tvSave:
                if(context.isEmptyStr(stringHead)){
                    context.showMessage("请选择头像");
                    return;
                }
                presenter.changePic();
                break;
        }
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"个人资料");
        initpermissionChecker();
        presenter=new PersonCenterPresenter(this,mContext);
        userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
        personCenterDto=Hawk.get(PreferenceKey.PERSON_CENTER);
        if(userInfoDto!=null){
            tvPhone.setText(userInfoDto.getUserAccount());
        }

        if(personCenterDto!=null){
            Glide.with(mContext.getApplicationContext()).load(personCenterDto.getPicture()).error(R.drawable.ico_head_defalut)
                    .into(headIamge);
        }
    }
    @Override
    public void initData() {
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
    FunctionConfig functionConfig;
    @Override
    public void oncomplete() {
        context.finish();
    }
    @Override
    public void OnDealCamera(int type) {
        if(type==0){
           showCameraAction();
        }else if(type==1){
            if(functionConfig==null){
                functionConfig = GalleryUtils.getFbdtFunction(1);
            }

            GalleryFinal.openGalleryMuti(1001,
                    functionConfig, mOnHanlderResultCallback);
        }
    }

    @Override
    public String stringHead() {
        return stringHead;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CAMERA && resultCode == RESULT_OK){//拍照
            if(!context.isEmptyStr(tempImage)){
                Uploadpic(BaseActivity.compress(mContext,new File(tempImage)));

            }
        }
    }
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback =
            new GalleryFinal.OnHanlderResultCallback() {
                @Override
                public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
                    if (resultList != null) {

                        Uploadpic(BaseActivity.compress(mContext,new File(resultList.get(0).getPhotoPath())));
                    }
                }
                @Override
                public void onHanlderFailure(int requestCode, String errorMsg) {
                    context.showMessage(errorMsg);
                }
            };
    private  void Uploadpic(File file){
        context.showLoadingDialog("图片上传中....");
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), file);
        //files 上传参数
        MultipartBody.Part part=
                MultipartBody.Part.createFormData("picture", file.getName(), requestBody);

        context.authApi.loadImg(part).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
                context.dismissLoadingDialog();
                stringHead=response.getUrl();
                Glide.with(mContext.getApplicationContext()).load(stringHead).error(R.drawable.ico_head_defalut)
                        .into(headIamge);
            }
            @Override
            public void fail(String code, String message) {
                context.dismissLoadingDialog();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        functionConfig=null;
        mOnHanlderResultCallback=null;
    }
}
