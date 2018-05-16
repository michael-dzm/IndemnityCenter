package com.sh3h.indemnity.ui.material.confirm;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.sh3h.dataprovider.data.entity.DUMaterial;
import com.sh3h.dataprovider.data.entity.DUMaterialVerify;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.util.NetworkUtil;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivityMaterialConfirmBinding;
import com.sh3h.indemnity.service.manager.IMainServiceManager;
import com.sh3h.indemnity.ui.base.ParentActivity;
import com.sh3h.indemnity.ui.multimedia.MultimediaFragment;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.indemnity.util.CountInputFilter;
import com.sh3h.ipc.location.MyLocation;
import com.sh3h.mobileutil.util.ApplicationsUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by xulongjun on 2017/3/10.
 */

public class MaterialConfirmActivity extends ParentActivity implements MaterialConfirmMvpView, MultimediaFragment.CameraCallBack,
        SwipeRefreshLayout.OnRefreshListener, TextWatcher {
    private static final String TAG = "MaterialConfirmActivity";

    public static final int MEDIA_MAX = 1;//照片上限

    @Inject
    MaterialConfirmPresenter mMaterialConfirmPresenter;

    @Inject
    PreferencesHelper mPreferencesHelper;

    private MultimediaFragment mMultimediaFragment;

    private ActivityMaterialConfirmBinding mBinding;

    private List<DUMultiMedia> mMultiMedias;

    private int mSelectNamePosition = -1;//记录材料名称选中position
    private int mSelectFormatPosition = -1;//记录材料规格选中position

    private List<DUMaterial> mDUMaterials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_material_confirm);
        mBinding.include.toolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        mBinding.include.toolbar.setTitle(R.string.activity_material_confirm);
        setSupportActionBar(mBinding.include.toolbar);
        getActivityComponent().inject(this);
        mMaterialConfirmPresenter.attachView(this);
        mBinding.setPresenter(mMaterialConfirmPresenter);
        mBinding.setRefreshListener(this);
        InputFilter[] filters = {new CountInputFilter()};
        mBinding.etCount.setFilters(filters);
        mBinding.etCount.addTextChangedListener(this);
        mMultimediaFragment = new MultimediaFragment();
        mMultimediaFragment.setCallBack(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.multimedia_container, mMultimediaFragment, mMultimediaFragment.getClass().getName()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                //do upload
                if (!isCanUpload()) {
                    return super.onOptionsItemSelected(item);
                }
                showProgress(R.string.text_dialog_uploading);
                MyLocation location = IMainServiceManager.getInstance(this).getLocation();
                DUMaterialVerify verify = new DUMaterialVerify();
                verify.setVerifyId(0L);
                verify.setMaterialId(mDUMaterials.get(mSelectFormatPosition).getMaterialId());
                verify.setDeliveryNumber(mBinding.etDeliveryCode.getText().toString().trim());
                verify.setConstructionTeam(mPreferencesHelper.getUserSession().getConstructionTeam());
                verify.setVerifyCount(mMaterialConfirmPresenter.getMaterialCount());
                verify.setOperator(mPreferencesHelper.getUserSession().getUserName());
                verify.setOperateTime(System.currentTimeMillis());
                verify.setLongitude(location == null ? 0 : location.getLongitude());
                verify.setLatitude(location == null ? 0 : location.getLatitude());
                List<DUMaterialVerify> duMaterialVerifies = new ArrayList<>();
                duMaterialVerifies.add(verify);
                mMaterialConfirmPresenter.uploadMaterialConfirm(duMaterialVerifies, mPreferencesHelper.getUserSession().getUserId());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isCanUpload() {
        if (!NetworkUtil.isNetworkConnected(this)) {
            onToast(R.string.text_toast_not_network);
            return false;
        }
        if (mBinding.etDeliveryCode.getText().toString().trim().equals("")) {
            onToast(R.string.text_toast_please_input_delivery_number);
            return false;
        }
        if (mMaterialConfirmPresenter.getMaterialCount() == 0) {
            onToast(R.string.text_toast_please_input_confirm_count);
            return false;
        }
        if(mMultiMedias == null || mMultiMedias.size() == 0){
            onToast(R.string.text_toast_please_take_photo);
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMaterialConfirmPresenter.detachView();
    }

    @Override
    public void setApplyCount(int applyCount) {
        mBinding.etCount.setText(String.valueOf(applyCount));
    }

    @Override
    public void onTakePhoto() {
        if (mMultimediaFragment == null) {
            return;
        }
        if (mMultiMedias == null) {
            mMultiMedias = new ArrayList<>();
        }
        if (mMultiMedias.size() >= MEDIA_MAX) {
            onToast(R.string.text_toast_take_photo_limit);
            return;
        }
        mMultimediaFragment.takePhoto();

    }

    @Override
    public void onToast(int resId) {
        ApplicationsUtil.showMessage(this, resId);
    }

    @Override
    public void onToast(String message) {
        ApplicationsUtil.showMessage(this, message);
    }

    @Override
    public void onUploadSuccess(int resId, long serverId) {
        onToast(resId);
        for (DUMultiMedia duMultiMedia : mMultiMedias) {
            duMultiMedia.setRelateId(serverId);
        }
        mMaterialConfirmPresenter.uploadMultiMedias(mMultiMedias);
    }

    @Override
    public void onUploadPhotoSuccess(int resId) {
        hideProgress();
        onToast(resId);
        finish();
    }

    @Override
    public void onUploadPhotoError(int resId) {
        hideProgress();
        onToast(resId);
        finish();
    }

    @Override
    public void onLoadSuccess() {
        mBinding.swiperefreshLayout.setRefreshing(false);
        mMaterialConfirmPresenter.getAllMaterialCategory();
    }

    @Override
    public void onUploadError(int resId) {
        hideProgress();
        onToast(resId);
    }

    @Override
    public void onUploadError(String message) {
        hideProgress();
        onToast(message);
    }

    @Override
    public void onLoadError(int resId) {
        mBinding.swiperefreshLayout.setRefreshing(false);
        onToast(resId);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_material_category:
                mMaterialConfirmPresenter.getMaterialNameByCategory((String) parent.getSelectedItem());
                break;
            case R.id.sp_material_name:
                mSelectNamePosition = position;
                mMaterialConfirmPresenter.getMaterialFormatByName((String) mBinding.spMaterialCategory.getSelectedItem(), (String) parent.getSelectedItem());
                break;
            case R.id.sp_material_format:
                mSelectFormatPosition = position;
                mMaterialConfirmPresenter.resetApplyCount(mDUMaterials.get(position));
                break;
        }
    }

    @Override
    public void onNotifySpinner(int spinnerId, List<DUMaterial> duMaterials) {
        switch (spinnerId) {
            case R.id.sp_material_category:
                mBinding.spMaterialCategory.setSelection(0);
                break;
            case R.id.sp_material_name:
                if (mSelectNamePosition == 0) {//如果材料名称选中position=0，将不再触发onItemSelected事件，继而不会刷新材料规格，所以需要主动刷新材料规格
                    mMaterialConfirmPresenter.getMaterialFormatByName((String) mBinding.spMaterialCategory.getSelectedItem(),
                            duMaterials.get(mSelectNamePosition).getMaterialName());
                } else {
                    mBinding.spMaterialName.setSelection(0);
                }
                break;
            case R.id.sp_material_format:
                mDUMaterials = duMaterials;
                if (mSelectFormatPosition == 0) {//如果材料规格选中position=0，将不再触发onItemSelected事件，继而不会刷新材料单位，所以需要主动重置材料单位
                    mMaterialConfirmPresenter.resetApplyCount(mDUMaterials.get(mSelectFormatPosition));
                } else {
                    mBinding.spMaterialFormat.setSelection(0);
                }
                break;
        }
    }

    @Override
    public void onAddImage(String imageName, String imagePath) {
        // do save image
        DUMultiMedia duMultiMedia = new DUMultiMedia();
        duMultiMedia.setRelateType(DUMultiMedia.RelateType.MATERIAL_VERIFY.getValue());
        duMultiMedia.setFileType(DUMultiMedia.FileType.IMAGE.getValue());
        duMultiMedia.setFileName(imageName);
        duMultiMedia.setFilePath(imagePath);
        duMultiMedia.setFileTime(System.currentTimeMillis());
        duMultiMedia.setUpload(0);
        mMultiMedias.add(duMultiMedia);
    }

    @Override
    public void onDeleteImage(String imageName, String imagePath) {
        //do delete image
        if (mMultiMedias == null || mMultiMedias.size() == 0) {
            return;
        }
        for (DUMultiMedia media : mMultiMedias) {
            if (media.getFileName().equals(imageName)) {
                mMultiMedias.remove(media);
                break;
            }
        }
    }

    @Override
    public void onRefresh() {
        mBinding.swiperefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadData() {
        //加载数据
        if (NetworkUtil.isNetworkConnected(this)) {//在线加载
            mBinding.swiperefreshLayout.setRefreshing(true);
            mMaterialConfirmPresenter.loadMaterials();
        } else {//离线加载
            onToast(R.string.text_toast_not_network);
            mMaterialConfirmPresenter.getAllMaterialCategory();
        }
    }

    @Override
    public void onExit(int resId) {
        onToast(resId);
        finish();
    }

    @Override
    public void onCompareUser() {
        //主程序的userId与子程序的userId不一样 子程序必须重新加载用户信息 无网络连接则退出
        int userId = getIntent().getIntExtra(Constants.INTENT_PARAM_USERID, 0);
        if(userId == 0){
            onExit(R.string.text_toast_host_app_user_info_error);
            return;
        }
        if(mPreferencesHelper.getUserSession().getUserId() != userId){
            if(!NetworkUtil.isNetworkConnected(this)) finish();
            mMaterialConfirmPresenter.getUserInfo(userId);
        }else{
            onLoadData();
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        //对比主程序uri与子程序uri 避免主程序和子程序访问不同服务器数据
        mMaterialConfirmPresenter.compareUri(getIntent().getStringExtra(Constants.INTENT_PARAM_BASEURI));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mMaterialConfirmPresenter.setApplyCount(s.length() == 0 ? 0 : Float.valueOf(s.toString()));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
