package com.saimawzc.shipper.ui.sendcar;

import static com.saimawzc.shipper.adapter.BaseAdapter.IS_RESH;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.order.SendCarAdapter;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.SendCarDto;
import com.saimawzc.shipper.dto.order.SignWeightDto;
import com.saimawzc.shipper.dto.pic.PicDto;
import com.saimawzc.shipper.presenter.order.SendCarLsitPresenter;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.view.order.SendCarListView;
import com.saimawzc.shipper.weight.BottomDialogUtil;
import com.saimawzc.shipper.weight.ClearTextEditText;
import com.saimawzc.shipper.weight.NoData;
import com.saimawzc.shipper.weight.utils.GlideEngine;
import com.saimawzc.shipper.weight.utils.LengthFilter;
import com.saimawzc.shipper.weight.utils.LoadMoreListener;
import com.saimawzc.shipper.weight.utils.dialog.PopupWindowUtil;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.http.JsonResult;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/***
 * ?????????
 * **/
public class CompeleteCarFrament extends BaseFragment implements SendCarListView {
    @BindView(R.id.cy)
    RecyclerView rv;
    private SendCarAdapter adapter;
    private List<SendCarDto.SendCarData> mDatas = new ArrayList<>();
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private SendCarLsitPresenter presenter;
    private int page = 1;
    private LoadMoreListener loadMoreListener;
    private String status = "7";//
    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.tvpopuw)
    TextView tvPopuw;
    @BindView(R.id.llpopuw)
    LinearLayout llpopuw;
    @BindView(R.id.nodata)
    NoData noData;
    private String searchType = "";
    private List<LocalMedia> localMedia;
    private String fileName;
    private ImageView uploadImage;
    private EditText doubtEd;
    private EditText hzSignInWeightEd;
    private TextView sjSignInWeightText;
    private TextView outFactoryWeightText;
    private TextView dismissButton;
    private TextView determineButton;
    private String imageUrl;
    private String id;
    private Boolean Re = false;
    private BottomDialogUtil bottomDialogUtil;

    @OnClick({R.id.llSearch, R.id.llpopuw})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.llSearch:
                page = 1;
                presenter.getSendCarList(page, status, searchType, edSearch.getText().toString());
                break;
            case R.id.llpopuw:
                final PopupWindowUtil popupWindowUtil = new PopupWindowUtil.Builder()
                        .setContext(mContext.getApplicationContext()) //?????? context
                        .setContentView(R.layout.dialog_paiche) //??????????????????
                        .setOutSideCancel(true) //????????????????????????
                        .setwidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setFouse(true)
                        .builder()
                        .showAsLaction(llpopuw, Gravity.LEFT, 0, 0);


                popupWindowUtil.setOnClickListener(new int[]{R.id.rlall, R.id.rlwuliao, R.id.rlcarNo, R.id.rlsjName,
                        R.id.rldanhao, R.id.rlqidi, R.id.rlfahuoshang, R.id.rlend, R.id.rlshouhuo}, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.rlall://??????
                                tvPopuw.setText("??????");
                                searchType = "";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlcarNo:
                                tvPopuw.setText("?????????");
                                searchType = "carNo";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlsjName:
                                tvPopuw.setText("????????????");
                                searchType = "sjName";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlwuliao://
                                tvPopuw.setText("??????");
                                searchType = "materialsName";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rldanhao://
                                tvPopuw.setText("??????");
                                searchType = "dispatchNo";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlqidi://
                                tvPopuw.setText("?????????");
                                searchType = "fromUserAddress";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlend:

                                tvPopuw.setText("?????????");
                                searchType = "toUserAddress";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlfahuoshang://
                                tvPopuw.setText("?????????");
                                searchType = "fromName";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlshouhuo://

                                tvPopuw.setText("?????????");
                                searchType = "toName";
                                popupWindowUtil.dismiss();
                                break;
                        }
                    }
                });
                break;
        }

    }

    @Override
    public int initContentView() {
        return R.layout.fragment_sendcar_mainindex;
    }

    @Override
    public void initView() {
        mContext = getActivity();
        initBroadCastReceiver();
        adapter = new SendCarAdapter(mDatas, mContext, "3");
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if (!IS_RESH) {
                    page++;
                    presenter.getSendCarList(page, status, searchType, edSearch.getText().toString());
                    IS_RESH = true;
                }

            }
        };
        rv.setOnScrollListener(loadMoreListener);

        presenter = new SendCarLsitPresenter(this, mContext);
        presenter.getSendCarList(page, status, searchType, edSearch.getText().toString());
        edSearch.addTextChangedListener(textWatcher);
        edSearch.hiddenIco();
    }

    private String yunDanId;

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                presenter.getSendCarList(page, status, searchType, edSearch.getText().toString());
            }
        });


        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mDatas.size() <= position) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("from", "waybillsh");
                bundle.putString("id", mDatas.get(position).getId());
                bundle.putString("type", "delation");
                readyGo(OrderMainActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        adapter.setOnTabClickListener(new SendCarAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String type, final int position) {
                if (mDatas.size() <= position) {
                    return;
                }
                Bundle bundle;
                if (type.equals("tab1")) {//????????????
                    bundle = new Bundle();
                    bundle.putString("from", "logistsc");
                    bundle.putString("id", mDatas.get(position).getId());
                    readyGo(OrderMainActivity.class, bundle);
                } else if (type.equals("tab2")) {//????????????
                    if (mDatas.get(position).getOutFactoryWeight() != null && mDatas.get(position).getSjSignInWeight() != null) {
                        if (mDatas.get(position).getOutFactoryWeight() == 0.0 && mDatas.get(position).getSjSignInWeight() == 0.0) {
                            if (mDatas.get(position).getHzSignInWeight() != null && mDatas.get(position).getTransportStatus() != null) {
                                if (mDatas.get(position).getHzSignInWeight() == 0.0 && mDatas.get(position).getTransportStatus() == 8) {
                                    showPopup(mDatas.get(position));
                                    id = mDatas.get(position).getId();
                                } else {
                                    yunDanId = mDatas.get(position).getId();
                                    presenter.getSignWeight(mDatas.get(position).getId());
                                }
                            } else {
                                yunDanId = mDatas.get(position).getId();
                                presenter.getSignWeight(mDatas.get(position).getId());
                            }
                        } else {
                            yunDanId = mDatas.get(position).getId();
                            presenter.getSignWeight(mDatas.get(position).getId());
                        }
                    } else {
                        yunDanId = mDatas.get(position).getId();
                        presenter.getSignWeight(mDatas.get(position).getId());
                    }
                } else if (type.equals("3")) {//????????????

                }
            }
        });
    }

    @Override
    public void getSendCarList(List<SendCarDto.SendCarData> dtos) {
        if (page == 1) {
            mDatas.clear();
            adapter.notifyDataSetChanged();
            if (dtos == null || dtos.size() <= 0) {
                noData.setVisibility(View.VISIBLE);
            } else {
                noData.setVisibility(View.GONE);
            }
        }
        if (Re) {
            mDatas.clear();
            adapter.notifyDataSetChanged();
            adapter.addMoreData(dtos);
            IS_RESH = true;
            Re = false;
        } else {
            llSearch.setVisibility(View.GONE);
            adapter.addMoreData(dtos);
            IS_RESH = false;
        }

    }

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);

    }

    @Override
    public void isLastPage(boolean isLastPage) {
        if (isLastPage) {
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        } else {
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }

    }

    @Override
    public void getSignWeight(final SignWeightDto dto) {

        final BottomDialogUtil bottomDialogUtil = new BottomDialogUtil.Builder()
                .setContext(context) //?????? context
                .setContentView(R.layout.dialog_sign) //??????????????????
                .setOutSideCancel(true) //????????????????????????
                .builder()
                .show();
        final EditText editText = (EditText) bottomDialogUtil.getItemView(R.id.idnum);

        TextView tvAlreadySign = (TextView) bottomDialogUtil.getItemView(R.id.tvAlreadySign);
        editText.setFilters(new InputFilter[]{new LengthFilter(3)});
        if (dto != null) {
            tvAlreadySign.setText("????????????" + dto.getWeight() + "  ??????????????????" + dto.getSjSignInWeight());
        }
        editText.setText(dto.getWeight());
        bottomDialogUtil.setOnClickListener(R.id.tvOrder, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (context.isEmptyStr(editText)) {
                    context.showMessage("??????????????????");
                    return;
                }
                bottomDialogUtil.dismiss();
                presenter.sign(yunDanId, editText.getText().toString());
            }
        });
    }

    @Override
    public void getDoubtSignIn(String dto) {
        JsonResult jsonResult = new Gson().fromJson(dto, JsonResult.class);
        if (jsonResult.message.equals("??????")) {

            Re = true;
            presenter.getSendCarList(page, status, searchType, edSearch.getText().toString());
        }
    }

    public void showPopup(final SendCarDto.SendCarData dto) {

        bottomDialogUtil = new BottomDialogUtil.Builder()
                .setContext(context) //?????? context
                .setContentView(R.layout.weigh_doubt_popup) //??????????????????
                .setOutSideCancel(false) //????????????????????????
                .builder()
                .show();
        //?????????
        outFactoryWeightText = (TextView) bottomDialogUtil.getItemView(R.id.outFactoryWeightText);
        //???????????????
        sjSignInWeightText = (TextView) bottomDialogUtil.getItemView(R.id.sjSignInWeightText);
        //???????????????
        hzSignInWeightEd = (EditText) bottomDialogUtil.getItemView(R.id.hzSignInWeightEd);
        //????????????
        doubtEd = (EditText) bottomDialogUtil.getItemView(R.id.doubtEd);
        //????????????
        uploadImage = (ImageView) bottomDialogUtil.getItemView(R.id.uploadImage);
        //??????
        dismissButton = (TextView) bottomDialogUtil.getItemView(R.id.dismissButton);
        //??????
        determineButton = (TextView) bottomDialogUtil.getItemView(R.id.determineButton);
        //??????
        outFactoryWeightText.setText(dto.getOutFactoryWeight() + "");
        sjSignInWeightText.setText(dto.getSjSignInWeight() + "");
        hzSignInWeightEd.setText(dto.getHzSignInWeight() + "");
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(CompeleteCarFrament.this)
                        .openGallery(PictureMimeType.ofAll())
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .theme(R.style.picture_default_style)
                        .maxSelectNum(1)
                        .minSelectNum(1)
                        .selectionMode(PictureConfig.MULTIPLE)
                        .isCamera(true)
                        .isGif(false)
                        .isMultipleSkipCrop(false)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                context.showLoadingDialog("???????????????...");
            }
        });

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialogUtil.dismiss();
            }
        });
        determineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id != null) {
                    if (TextUtils.isEmpty(imageUrl)) {
                        Toast.makeText(mContext, "???????????????", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(doubtEd.getText().toString().trim())) {
                        Toast.makeText(mContext, "?????????????????????", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(hzSignInWeightEd.getText().toString().trim())) {
                        Toast.makeText(mContext, "???????????????", Toast.LENGTH_SHORT).show();
                    } else {
                        presenter.getDoubtSignIn(id, imageUrl, doubtEd.getText().toString(), hzSignInWeightEd.getText().toString());
                        bottomDialogUtil.dismiss();
                        imageUrl=null;
                    }
                }
            }
        });

    }

    /**
     * ????????????????????????
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    localMedia = PictureSelector.obtainMultipleResult(data);
                    fileName = localMedia.get(0).getRealPath();
                    Uploadpic(new File(fileName));
                    break;
                default:
            }
        }
    }

    private void Uploadpic(File file) {

        File temp = BaseActivity.compress(mContext, file);
        final RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), temp);
        //files ????????????
        MultipartBody.Part part =
                MultipartBody.Part.createFormData("picture", temp.getName(), requestBody);
        context.authApi.loadImg(part).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
                context.dismissLoadingDialog();
                imageUrl = response.getUrl();
                if (imageUrl != null) {
                    Glide.with(mContext.getApplicationContext()).load(response.getUrl()).error(R.drawable.ico_head_defalut)
                            .into(uploadImage);
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
    public void showLoading() {
        context.showLoadingDialog();
    }

    @Override
    public void dissLoading() {
        context.dismissLoadingDialog();
    }

    @Override
    public void Toast(String str) {
        if (TextUtils.isEmpty(PreferenceKey.HZ_IS_RZ) || !Hawk.get(PreferenceKey.HZ_IS_RZ, "").equals("1")) {
            if (!str.contains("??????")) {
                context.showMessage(str);
            }
        } else {
            context.showMessage(str);
            if (mDatas == null || mDatas.size() <= 0) {
                noData.setVisibility(View.VISIBLE);
            } else {
                noData.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void oncomplete() {
        page = 1;
        presenter.getSendCarList(page, status, searchType, edSearch.getText().toString());
        IS_RESH = false;
    }

    /**
     * ???????????????
     */
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //???????????????????????????????????????
            if (!TextUtils.isEmpty(edSearch.getText().toString())) {
                llSearch.setVisibility(View.VISIBLE);
                tvSearch.setText(edSearch.getText().toString());
            } else {
                llSearch.setVisibility(View.GONE);
            }
        }
    };

    private void initBroadCastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("reshChange");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                page = 1;
                presenter.getSendCarList(page, status, searchType, edSearch.getText().toString());
            }
        };
        context.registerReceiver(mReceiver, filter);
    }
}
