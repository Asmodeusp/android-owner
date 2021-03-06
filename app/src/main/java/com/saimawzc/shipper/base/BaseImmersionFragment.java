package com.saimawzc.shipper.base;

import static com.saimawzc.shipper.ui.my.identification.CargoOwnerFragment.REQUEST_CODE_PIC;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.baidu.ocr.ui.camera.CameraActivity;
import com.gyf.immersionbar.components.SimpleImmersionFragment;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.dto.identification.PersonCenterDto;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.weight.utils.FileUtil;
import com.saimawzc.shipper.weight.utils.SdCardUtil;
import com.werb.permissionschecker.PermissionChecker;

import butterknife.ButterKnife;

/**
 * 项目名称：TOPRACING
 * 类名称：
 * 类描述：基础Fragment，封装了基本方法
 * 创建人：nsj
 * 创建时间：2017/1/12 12:18
 * 修改备注：
 */
public abstract class BaseImmersionFragment extends SimpleImmersionFragment {
    protected static final String ARG_PARAM1 = "param1";
    protected static final String ARG_PARAM2 = "param2";
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    private String mParam1;
    private String mParam2;
    public Context mContext;
    public View view;
    public LinearLayoutManager layoutManager;
    private OnFragmentInteractionListener mListener;
    protected BaseActivity context;
    public PermissionChecker permissionChecker;
    public PersonCenterDto personCenterDto;
    public UserInfoDto userInfoDto;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (BaseActivity) getActivity();
        mContext=getActivity();
        //防止fragment出现重叠
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();

            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }

        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 判断view是否可用
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view); // 移除重复的view
            }

        } else {
            // Inflate the layout for this fragment_bidden_history
            view = inflater.inflate(initContentView(), container, false);
            ButterKnife.bind(this, view);
            initView();
            initData();

        }

       return view;
    }

    /**
     * 子类要实现该方法，return 要填充的布局
     *
     * @return
     */
    public abstract int initContentView();

    /**
     * 子类要实现该方法，以找到控件
     */
    public abstract void initView();

    /**
     * 获取数据
     * @param
     */
    public abstract  void initData();

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivityForResult(intent, requestCode);
    }
    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {

        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {

            intent.putExtras(bundle);
        }

        startActivityForResult(intent, requestCode);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) view.getParent()).removeView(view);
    }

    /**
     * 本地上传图片
     * **/
    public String tempImage;
    public final int REQUEST_CODE_CAMERA=1000;
    public void showCameraAction() {
        tempImage = SdCardUtil.getCacheTempImage(context);
        openCamera(tempImage, REQUEST_CODE_CAMERA);
    }

    public void openCamera(String path, int code) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.parse("file:///" + path));
        startActivityForResult(intent, code);
    }
    public void initpermissionChecker(){
        permissionChecker=new PermissionChecker(context);
        permissionChecker.setTitle(getString(R.string.check_info_title));
        permissionChecker.setMessage(getString(R.string.check_info_message));
    }

    protected void initCamera(String type){
        tempImage=System.currentTimeMillis()+"";
        Intent intent = new Intent(mContext, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(mContext,tempImage).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                true);
        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                true);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                type);
        startActivityForResult(intent, REQUEST_CODE_PIC);
    }

}
