package com.saimawzc.shipper.ui.order.creatorder.richtext;



import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.base.CameraListener;
import com.saimawzc.shipper.dto.pic.PicDto;
import com.saimawzc.shipper.dto.richtext.ImageItem;
import com.saimawzc.shipper.weight.utils.CameraDialogUtil;
import com.saimawzc.shipper.weight.utils.GalleryUtils;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;
import com.saimawzc.shipper.weight.utils.richtext.RichEditor;
import com.saimawzc.shipper.weight.utils.richtext.utils.KeyBoardUtils;
import com.saimawzc.shipper.weight.utils.richtext.utils.RichUtils;
import com.saimawzc.shipper.weight.utils.richtext.utils.popup.CommonPopupWindow;
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

/**
 * 富文本发布
 */
public class RichPublishActivity extends BaseActivity {

    @BindView(R.id.rich_Editor) RichEditor editor;
    private int isFrom;//0:表示正常编辑  1:表示是重新编辑
    private String currentUrl = "";
    private CommonPopupWindow popupWindow; //编辑图片的pop
    @BindView(R.id.edit_name) EditText editName;
    @BindView(R.id.txt_publish) TextView txtPublish;
    @BindView(R.id.button_bold) ImageView buttonBold;
    @BindView(R.id.button_underline)ImageView buttonUnderline;
    @BindView(R.id.button_list_ul)ImageView buttonListUl;
    @BindView(R.id.button_list_ol)ImageView buttonListOl;
    @BindView(R.id.button_image)ImageView buttonPic;
    @BindView(R.id.button_video)ImageView buttonVideo;
    @BindView(R.id.root) LinearLayout root;
    public static final int VIDEOS=12;
    public static final int LOCAL_VIDEOS=13;

    public  final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO
    };

    @Override
    protected int getViewId() {
        return R.layout.activity_richtext;
    }

    @Override
    protected void init() {
        initpermissionChecker();
        initPop();
        initEditor();
        if(galleryFinal==null){
            galleryFinal=new GalleryFinal();
        }
        if(!TextUtils.isEmpty(getIntent().getStringExtra("data"))){
            editor.setHtml(getIntent().getStringExtra("data"));
        }
        if(permissionChecker.isLackPermissions(PERMISSIONS)){
            permissionChecker.requestPermissions();
        }else {
        }

    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void onGetBundle(Bundle bundle) {
    }

    private void initPop() {
        View view = LayoutInflater.from(RichPublishActivity.this).inflate(R.layout.newapp_pop_picture, null);
        view.findViewById(R.id.linear_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        view.findViewById(R.id.linear_delete_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String removeUrl = "<img src=\"" + currentUrl + "\" alt=\"dachshund\" width=\"100%\"><br>";
                String newUrl = editor.getHtml().replace(removeUrl, "");
                currentUrl = "";
                editor.setHtml(newUrl);
                if (RichUtils.isEmpty(editor.getHtml())) {
                    editor.setHtml("");
                }
                popupWindow.dismiss();
            }
        });

        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(view)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchable(true)//在外不可用手指取消
                .setAnimationStyle(R.style.pop_animation)//设置popWindow的出场动画
                .create();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                editor .setInputEnabled(true);
            }
        });
    }

    private void initEditor() {
        //输入框显示字体的大小
        editor.setEditorFontSize(18);
        //输入框显示字体的颜色
        editor.setEditorFontColor(getResources().getColor(R.color.color_black));
        //输入框背景设置
        editor.setEditorBackgroundColor(Color.WHITE);
        //输入框文本padding
        editor.setPadding(10, 10, 10, 10);
        //输入提示文本
        editor.setPlaceholder("在此编辑安全告知！~");
        //文本输入框监听事件
        editor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
//                if (TextUtils.isEmpty(editName.getText().toString().trim())) {
//                    txtPublish.setSelected(false);
//                    txtPublish.setEnabled(false);
//                    return;
//                }
                if (TextUtils.isEmpty(text)) {
                    txtPublish.setSelected(false);
                    txtPublish.setEnabled(false);
                } else {
                    if (TextUtils.isEmpty(Html.fromHtml(text))) {
                        txtPublish.setSelected(false);
                        txtPublish.setEnabled(false);
                    } else {
                        txtPublish.setSelected(true);
                        txtPublish.setEnabled(true);
                    }
                }
            }
        });

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String html = editor.getHtml();
                if (TextUtils.isEmpty(html)) {
                    txtPublish.setSelected(false);
                    txtPublish.setEnabled(false);
                    return;
                } else {
                    if (TextUtils.isEmpty(Html.fromHtml(html))) {
                        txtPublish.setSelected(false);
                        txtPublish.setEnabled(false);
                        return;
                    } else {
                        txtPublish.setSelected(true);
                        txtPublish.setEnabled(true);
                    }
                }
                if (TextUtils.isEmpty(s.toString())) {
                    txtPublish.setSelected(false);
                    txtPublish.setEnabled(false);
                } else {
                    txtPublish.setSelected(true);
                    txtPublish.setEnabled(true);
                }
            }
        });

        editor.setOnDecorationChangeListener(new RichEditor.OnDecorationStateListener() {
            @Override
            public void onStateChangeListener(String text, List<RichEditor.Type> types) {
                ArrayList<String> flagArr = new ArrayList<>();
                for (int i = 0; i < types.size(); i++) {
                    flagArr.add(types.get(i).name());
                }
                if (flagArr.contains("BOLD")) {
                    buttonBold.setImageResource(R.drawable.bold_);
                } else {
                    buttonBold.setImageResource(R.drawable.bold);
                }
                if (flagArr.contains("UNDERLINE")) {
                    buttonUnderline.setImageResource(R.drawable.underline_);
                } else {
                    buttonUnderline.setImageResource(R.drawable.underline);
                }
                if (flagArr.contains("ORDEREDLIST")) {
                    buttonListUl.setImageResource(R.drawable.list_ul);
                    buttonListOl.setImageResource(R.drawable.list_ol_);
                } else {
                    buttonListOl.setImageResource(R.drawable.list_ol);
                }
                if (flagArr.contains("UNORDEREDLIST")) {
                    buttonListOl.setImageResource(R.drawable.list_ol);
                    buttonListUl.setImageResource(R.drawable.list_ul_);
                } else {
                    buttonListUl.setImageResource(R.drawable.list_ul);
                }

            }
        });


        editor.setImageClickListener(new RichEditor.ImageClickListener() {
            @Override
            public void onImageClick(String imageUrl) {
                Log.e("msg","点击图片"+imageUrl);
                currentUrl = imageUrl;
                editor .setInputEnabled(true);
                //popupWindow.showBottom(root, 0.5f);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==VIDEOS && resultCode == RESULT_OK){
            String videosurl=data.getStringExtra("path");
            Uploadpic(new File(videosurl),2);
        }else if(requestCode==LOCAL_VIDEOS && resultCode == RESULT_OK){
            String videosurl=data.getStringExtra("path");
            if(!TextUtils.isEmpty(videosurl)){
                Uploadpic(new File(videosurl),2);
            }

        }

    }
    private GalleryFinal galleryFinal;
    FunctionConfig functionConfig;
    private CameraDialogUtil cameraDialogUtil;
    @OnClick({R.id.txt_finish,R.id.txt_publish,R.id.button_rich_do,
            R.id.button_rich_undo,R.id.button_bold,R.id.button_underline
    ,R.id.button_list_ul,R.id.button_list_ol,R.id.button_image,R.id.button_video})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.txt_finish:
                finish();
                break;
            case R.id.txt_publish:
                Intent intent=new Intent();
                intent.putExtra("data",editor.getHtml());
                context.setResult(RESULT_OK, intent);
                context. finish();
                break;

            case R.id.button_rich_do:
                //反撤销
                editor.redo();
                break;
            case R.id.button_rich_undo:
                //撤销
                editor.undo();
                break;

            case R.id.button_bold:
                //加粗
                againEdit();
                editor.setBold();
                break;

            case R.id.button_underline:
                //加下划线
                againEdit();
                editor.setUnderline();
                break;

            case R.id.button_list_ul:
                //加带点的序列号
                againEdit();
                editor.setBullets();
                break;

            case R.id.button_list_ol:
                //加带数字的序列号
                againEdit();
                editor.setNumbers();
                break;
            case R.id.button_image:
                if (!TextUtils.isEmpty(editor.getHtml())) {
                    ArrayList<String> arrayList = RichUtils.returnImageUrlsFromHtml(editor.getHtml());
                }
                functionConfig = GalleryUtils.getFbdtFunction(1);
                galleryFinal.openGalleryMuti(1001,
                        functionConfig, mOnHanlderResultCallback);
                break;
            case R.id.button_video:
              //  readyGoForResult(JcTakePhoneActivity.class,VIDEOS);
                if(cameraDialogUtil==null){
                    cameraDialogUtil=new CameraDialogUtil(context);
                }
                cameraDialogUtil.showDialog(new CameraListener() {//type
                    @Override
                    public void takePic() {
                        readyGoForResult(JcTakePhoneActivity.class,VIDEOS);
                        cameraDialogUtil.dialog.dismiss();
                    }
                    @Override
                    public void chooseLocal() {
                        readyGoForResult(LocalVideosListActivity.class,LOCAL_VIDEOS);
                        cameraDialogUtil.dialog.dismiss();
                    }
                    @Override
                    public void cancel() {
                        cameraDialogUtil.dialog.dismiss();
                    }
                });

                break;

        }
    }
    private void againEdit() {
        //如果第一次点击例如加粗，没有焦点时，获取焦点并弹出软键盘
        editor.focusEditor();
        KeyBoardUtils.openKeybord(editName, this);
    }
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback =
            new GalleryFinal.OnHanlderResultCallback() {
                @Override
                public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
                    if (resultList != null) {
                        againEdit();
                        Uploadpic(BaseActivity.compress(mContext,new File(resultList.get(0).getPhotoPath())),1);

                    }
                }
                @Override
                public void onHanlderFailure(int requestCode, String errorMsg) {
                    context.showMessage(errorMsg);
                }
            };
    private  void Uploadpic(File file, final int type){
        context.showLoadingDialog("图片上传中....");
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), file);
        //files 上传参数
        MultipartBody.Part part=
                MultipartBody.Part.createFormData("picture", file.getName(), requestBody);
        authApi.loadImg(part).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
                context.dismissLoadingDialog();
                if(type==1){
                    editor.insertImage(response.getUrl(), "dachshund",getAndroiodScreenProperty() ,200);
                }else {
                    editor.insertVideo(response.getUrl(),getAndroiodScreenProperty(),200);
                 }
                KeyBoardUtils.openKeybord(editName, RichPublishActivity.this);
                editor.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (editor != null) {
                            editor.scrollToBottom();
                        }
                    }
                }, 200);

            }
            @Override
            public void fail(String code, String message) {
                context.dismissLoadingDialog();
            }
        });
    }
    public int  getAndroiodScreenProperty(){
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;// 屏幕宽度（像素）
        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width/density);//屏幕宽度(dp)
        if(screenWidth<=0){
            screenWidth=300;
        }else {
            screenWidth=screenWidth-50;
        }
        return screenWidth;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        galleryFinal=null;
        functionConfig=null;
        mOnHanlderResultCallback=null;
    }
}
