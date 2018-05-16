package com.sh3h.indemnity.ui.material.apply;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.sh3h.dataprovider.data.entity.DUAddress;
import com.sh3h.dataprovider.data.entity.DUMaterial;
import com.sh3h.dataprovider.data.entity.DUMaterialApply;
import com.sh3h.dataprovider.data.entity.enums.Upload;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.data.local.preference.UserSession;
import com.sh3h.dataprovider.util.NetworkUtil;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivityMaterialApplyBinding;
import com.sh3h.indemnity.service.manager.IMainServiceManager;
import com.sh3h.indemnity.ui.base.ParentActivity;
import com.sh3h.indemnity.ui.material.address.AddressManagerActivity;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.indemnity.util.CountInputFilter;
import com.sh3h.ipc.location.MyLocation;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by xulongjun on 2017/3/8.
 */

public class MaterialApplyActivity extends ParentActivity implements MaterialApplyMvpView, SwipeRefreshLayout.OnRefreshListener, TextWatcher {

    @Inject
    MaterialApplyPresenter mMaterialApplyPresenter;

    @Inject
    PreferencesHelper mPreferencesHelper;

    private ActivityMaterialApplyBinding mBinding;

    private int mSelectNamePosition = -1;//记录材料名称选中position
    private int mSelectFormatPosition = -1;//记录材料规格选中position

    private List<DUMaterial> mDUMaterials;

    private DUMaterialApply mDuMaterialApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_material_apply);
        mBinding.include.toolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        mBinding.include.toolbar.setTitle(R.string.activity_material_apply);
        setSupportActionBar(mBinding.include.toolbar);
        getActivityComponent().inject(this);
        mMaterialApplyPresenter.attachView(this);
        mBinding.setPresenter(mMaterialApplyPresenter);
        mBinding.setRefreshListener(this);
        InputFilter[] filters = {new CountInputFilter()};
        mBinding.etCount.setFilters(filters);
        mBinding.etCount.addTextChangedListener(this);
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
                if (!NetworkUtil.isNetworkConnected(this)) {
                    onToast(R.string.text_toast_not_network);
                    return super.onOptionsItemSelected(item);
                }
                if (mDUMaterials == null) {
                    return super.onOptionsItemSelected(item);
                }
                if(mMaterialApplyPresenter.getMaterialCount() == 0){
                    onToast(R.string.text_toast_please_input_apply_count);
                    return super.onOptionsItemSelected(item);
                }
                if(TextUtil.isNullOrEmpty(mDuMaterialApply.getDeliveryAddress())){
                    onToast(R.string.text_toast_please_input_delivery_address);
                    return super.onOptionsItemSelected(item);
                }
                showProgress(R.string.text_dialog_uploading);
                MyLocation location = IMainServiceManager.getInstance(this).getLocation();
                mDuMaterialApply.setApplyId(0L);
//                mDuMaterialApply.setApplyNumber(UUID.randomUUID().toString());
                mDuMaterialApply.setMaterialId(mDUMaterials.get(mSelectFormatPosition).getMaterialId());
                mDuMaterialApply.setApplyCount(mMaterialApplyPresenter.getMaterialCount());
                mDuMaterialApply.setOperateTime(System.currentTimeMillis());
                mDuMaterialApply.setOperator(mPreferencesHelper.getUserSession().getUserName());
                mDuMaterialApply.setLongitude(location == null ? 0 : location.getLongitude());
                mDuMaterialApply.setLatitude(location == null ? 0 : location.getLatitude());
                mDuMaterialApply.setUpload(Upload.DEFAULT.getValue());
                mMaterialApplyPresenter.uploadMaterialApply(mDuMaterialApply, mPreferencesHelper.getUserSession().getUserId());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMaterialApplyPresenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                if (data == null) {
                    mMaterialApplyPresenter.getDefaultDUAddress(mPreferencesHelper.getUserSession().getUserId());
                    return;
                }
                DUAddress duAddress = data.getParcelableExtra(Constants.INTENT_PARAM_ADDRESS);
                mDuMaterialApply.setDeliveryAddress(duAddress.getAddressContent());
                break;
        }
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
    public void onLoadSuccess() {
        mBinding.swiperefreshLayout.setRefreshing(false);
        mMaterialApplyPresenter.getAllMaterialCategory();
    }

    @Override
    public void onUploadSuccess(int resId) {
        hideProgress();
        onToast(resId);
        finish();
    }

    @Override
    public void toAddressManagerActivity() {
        Intent intent = new Intent(this, AddressManagerActivity.class);
        intent.putExtra(Constants.INTENT_PARAM_FLAG, Constants.FLAG_MATERIAL_APPLY);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_material_category:
                mMaterialApplyPresenter.getMaterialNameByCategory((String) parent.getSelectedItem());
                break;
            case R.id.sp_material_name:
                mSelectNamePosition = position;
                mMaterialApplyPresenter.getMaterialFormatByName((String) mBinding.spMaterialCategory.getSelectedItem(), (String) parent.getSelectedItem());
                break;
            case R.id.sp_material_format:
                mSelectFormatPosition = position;
                mMaterialApplyPresenter.resetApplyCount(mDUMaterials.get(position));
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
                    mMaterialApplyPresenter.getMaterialFormatByName((String) mBinding.spMaterialCategory.getSelectedItem(),
                            duMaterials.get(mSelectNamePosition).getMaterialName());
                } else {
                    mBinding.spMaterialName.setSelection(0);
                }
                break;
            case R.id.sp_material_format:
                mDUMaterials = duMaterials;
                if (mSelectFormatPosition == 0) {//如果材料规格选中position=0，将不再触发onItemSelected事件，继而不会刷新材料单位，所以需要主动重置材料单位
                    mMaterialApplyPresenter.resetApplyCount(mDUMaterials.get(mSelectFormatPosition));
                } else {
                    mBinding.spMaterialFormat.setSelection(0);
                }
                break;
        }

    }

    @Override
    public void onAddress(String addressContent) {
        if(mDuMaterialApply == null){
            mDuMaterialApply = new DUMaterialApply();
        }
        mDuMaterialApply.setDeliveryAddress(addressContent);
    }

    @Override
    public void onRefresh() {
        //do refresh data
        mBinding.swiperefreshLayout.setRefreshing(false);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mMaterialApplyPresenter.setApplyCount(s.length() == 0 ? 0 : Float.valueOf(s.toString()));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onLoadData() {
        if (NetworkUtil.isNetworkConnected(this)) {//在线加载
            mBinding.swiperefreshLayout.setRefreshing(true);
            mMaterialApplyPresenter.loadMaterials();
        } else {//离线加载
            mMaterialApplyPresenter.getAllMaterialCategory();
        }
        //初始化送货信息
        UserSession userSession = mPreferencesHelper.getUserSession();
        if(mDuMaterialApply == null){
            mDuMaterialApply = new DUMaterialApply();
        }
        mDuMaterialApply.setConstructionTeam(userSession.getConstructionTeam());
        mDuMaterialApply.setApplyDepartment(userSession.getConstructionTeam());
        mDuMaterialApply.setApplyPersion(userSession.getUserName());
        mDuMaterialApply.setContactPhone(userSession.getMobile());
        mBinding.setMaterialApply(mDuMaterialApply);
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
        mMaterialApplyPresenter.getDefaultDUAddress(userId);
        if(mPreferencesHelper.getUserSession().getUserId() != userId){
            if(!NetworkUtil.isNetworkConnected(this)) finish();
            mMaterialApplyPresenter.getUserInfo(userId);
        }else{
            onLoadData();
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        //对比主程序uri与子程序uri 避免主程序和子程序访问不同服务器数据
        mMaterialApplyPresenter.compareUri(getIntent().getStringExtra(Constants.INTENT_PARAM_BASEURI));
    }
}
