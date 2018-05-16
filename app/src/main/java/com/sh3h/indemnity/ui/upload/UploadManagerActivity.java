package com.sh3h.indemnity.ui.upload;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.sh3h.dataprovider.data.entity.DUPatrol;
import com.sh3h.dataprovider.data.entity.enums.Permission;
import com.sh3h.dataprovider.data.entity.enums.Upload;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.util.NetworkUtil;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivityUploadManagerBinding;
import com.sh3h.indemnity.event.BusEvent;
import com.sh3h.indemnity.service.action.SyncAction;
import com.sh3h.indemnity.service.manager.IMainServiceManager;
import com.sh3h.indemnity.ui.base.ParentActivity;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.ipc.module.MyModule;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by dengzhimin on 2017/4/24.
 */

public class UploadManagerActivity extends ParentActivity implements UploadManagerMvpView {

    @Inject
    UploadManagerPresenter mPresenter;

    @Inject
    ConfigHelper mConfigHelper;

    @Inject
    PreferencesHelper mPreferencesHelper;

    @Inject
    Bus mBus;

    private ActivityUploadManagerBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_upload_manager);
        mBinding.include.toolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        mBinding.include.toolbar.setTitle(R.string.activity_upload_manager);
        setSupportActionBar(mBinding.include.toolbar);
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mBus.register(this);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mBus.unregister(this);
        //关闭页面 更新主界面下标
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(MyModule.PACKAGE_NAME, getApplicationContext().getPackageName());
            jsonObject.put(MyModule.ACTIVITY_NAME, UploadManagerActivity.class.getName());
            JSONArray subJSONArray = new JSONArray();
            if(mPreferencesHelper.getUserSession().getRoles().contains(Permission.CONSTRUCTION_SCENE_MANAGER.getValue())){
                subJSONArray.put("count#" + (mPresenter.dailyPatrolCount.get() + mPresenter.dailyPatrolMultiMediaCount.get()));
            }else if(mPreferencesHelper.getUserSession().getRoles().contains(Permission.INDEMNITY_APP_OPERATOR.getValue()) ||
                    mPreferencesHelper.getUserSession().getRoles().contains(Permission.INDEMNITY_MANAGER.getValue()) ||
                    mPreferencesHelper.getUserSession().getRoles().contains(Permission.INDEMNITY_OPERATOR.getValue())){
                subJSONArray.put("count#" + (mPresenter.operateCount.get() + mPresenter.operateMultiMediaCount.get()
                        + mPresenter.centerPatrolCount.get() + mPresenter.centerPatrolMultiMediaCount.get()
                        + mPresenter.acceptCount.get() + mPresenter.acceptMultiMediaCount.get()));
            }
            jsonObject.put(MyModule.DATA, subJSONArray);
            MyModule myModule = new MyModule(jsonObject.toString());
            IMainServiceManager.getInstance(this).getService().setMyModule(myModule);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUploadOperates() {
        if(!NetworkUtil.isNetworkConnected(this)){
            onToast(R.string.toast_network_is_not_connect);
            return;
        }
        //上传操作
        mPresenter.operateUploading.set(true);
        mPresenter.operateMultiMediaUploading.set(true);
        startSyncService(SyncAction.UPLOAD_ALL_OPERATE.ordinal());
    }

    @Override
    public void onUploadOperateMultiMedias() {
        if(!NetworkUtil.isNetworkConnected(this)){
            onToast(R.string.toast_network_is_not_connect);
            return;
        }
        //上传操作
        mPresenter.operateMultiMediaUploading.set(true);
        startSyncService(SyncAction.UPLOAD_ALL_OPERATE_MULTIMEDIA.ordinal());
    }

    @Override
    public void onUploadPatrols(int type) {
        if(!NetworkUtil.isNetworkConnected(this)){
            onToast(R.string.toast_network_is_not_connect);
            return;
        }
        //上传巡视
        if(type == DUPatrol.Type.DAILY_PATROL.getValue()){
            mPresenter.dailyPatrolUploading.set(true);
            mPresenter.dailyPatrolMultiMediaUploading.set(true);
        }else{
            mPresenter.centerPatrolUploading.set(true);
            mPresenter.centerPatrolMultiMediaUploading.set(true);
        }
        startSyncService(SyncAction.UPLOAD_ALL_PATROL.ordinal(), type);
    }

    @Override
    public void onUploadPatrolMultiMedias(int type) {
        if(!NetworkUtil.isNetworkConnected(this)){
            onToast(R.string.toast_network_is_not_connect);
            return;
        }
        if(type == DUPatrol.Type.DAILY_PATROL.getValue()){
            mPresenter.dailyPatrolMultiMediaUploading.set(true);
        }else{
            mPresenter.centerPatrolMultiMediaUploading.set(true);
        }
        startSyncService(SyncAction.UPLOAD_ALL_PATROL_MULTIMEDIA.ordinal(), type);
    }

    @Override
    public void onUploadAccepts() {
        if(!NetworkUtil.isNetworkConnected(this)){
            onToast(R.string.toast_network_is_not_connect);
            return;
        }
        mPresenter.acceptUploading.set(true);
        mPresenter.acceptMultiMediaUploading.set(true);
        //上传验收
        startSyncService(SyncAction.UPLOAD_ALL_ACCEPT.ordinal());
    }

    @Override
    public void onUploadAcceptMultiMedias() {
        if(!NetworkUtil.isNetworkConnected(this)){
            onToast(R.string.toast_network_is_not_connect);
            return;
        }
        mPresenter.acceptMultiMediaUploading.set(true);
        //上传验收
        startSyncService(SyncAction.UPLOAD_ALL_ACCEPT_MULTIMEDIA.ordinal());
    }

    @Override
    public void onToast(int resId) {
        ApplicationsUtil.showMessage(this, resId);
    }

    @Override
    public void onToast(String message) {
        ApplicationsUtil.showMessage(this, message);
    }

    @Subscribe
    public void onUploadPatrolResult(BusEvent.UploadPatrol event){
        if(event.getType() == DUPatrol.Type.DAILY_PATROL.getValue()){
            mPresenter.dailyPatrolUploading.set(false);
        }else{
            mPresenter.centerPatrolUploading.set(false);
        }
        if(event.isSuccess()){
            onToast(R.string.text_toast_patrol_upload_success);
            mPresenter.getPatrolCount(mPreferencesHelper.getUserSession().getUserName(), event.getType(), Upload.DEFAULT.getValue());
        }else{
            onToast(R.string.text_toast_patrol_upload_failed);
        }
    }

    @Subscribe
    public void onUploadOperateResult(BusEvent.UploadOperate event){
        mPresenter.operateUploading.set(false);
        if(event.isSuccess()){
            onToast(R.string.text_toast_start_stop_restart_finish_project_upload_success);
            mPresenter.getOperateCount(mPreferencesHelper.getUserSession().getUserName(), Upload.DEFAULT.getValue());
        }else{
            onToast(R.string.text_toast_start_stop_restart_finish_project_upload_failed);
        }
    }

    @Subscribe
    public void onUploadAcceptResult(BusEvent.UploadAccept event) {
        mPresenter.acceptUploading.set(false);
        if (event.isSuccess()) {
            onToast(R.string.text_toast_accept_upload_success);
            mPresenter.getAcceptCount(mPreferencesHelper.getUserSession().getUserName(), Upload.DEFAULT.getValue());
        } else {
            onToast(R.string.text_toast_accept_upload_failed);
        }
    }

    @Subscribe
    public void onUploadMultiMediaResult(BusEvent.UploadMultiMedia event) {
        if(mPreferencesHelper.getUserSession().getRoles().contains(Permission.CONSTRUCTION_SCENE_MANAGER.getValue())){
            mPresenter.dailyPatrolMultiMediaUploading.set(false);
            //获取日常巡视已上传多媒体未上传的多媒体数量
            mPresenter.getPatrolMultiMediaCount(mPreferencesHelper.getUserSession().getUserName(),
                    DUPatrol.Type.DAILY_PATROL.getValue(), Upload.UPLOADED.getValue(), Upload.DEFAULT.getValue());
        }else if(mPreferencesHelper.getUserSession().getRoles().contains(Permission.INDEMNITY_APP_OPERATOR.getValue()) ||
                mPreferencesHelper.getUserSession().getRoles().contains(Permission.INDEMNITY_MANAGER.getValue()) ||
                mPreferencesHelper.getUserSession().getRoles().contains(Permission.INDEMNITY_OPERATOR.getValue())){
            mPresenter.operateMultiMediaUploading.set(false);
            mPresenter.centerPatrolMultiMediaUploading.set(false);
            mPresenter.acceptMultiMediaUploading.set(false);
            //获取工程操作已上传多媒体未上传的多媒体数量
            mPresenter.getOperateMultiMediaCount(mPreferencesHelper.getUserSession().getUserName(),
                    Upload.UPLOADED.getValue(), Upload.DEFAULT.getValue());
            //获取保中巡视已上传多媒体未上传的多媒体数量
            mPresenter.getPatrolMultiMediaCount(mPreferencesHelper.getUserSession().getUserName(),
                    DUPatrol.Type.INDEMNITY_CENTER_PATROL.getValue(), Upload.UPLOADED.getValue(), Upload.DEFAULT.getValue());
            //获取验收已上传多媒体未上传的多媒体数量
            mPresenter.getAcceptMultiMediaCount(mPreferencesHelper.getUserSession().getUserName(),
                    Upload.UPLOADED.getValue(), Upload.DEFAULT.getValue());
        }
        if (event.isSuccess()) {
            onToast(String.format("照片上传成功:%s 失败:%s", event.getSuccessCount(), event.getFailedCount()));
        } else {
            onToast(R.string.text_toast_photo_upload_timeout_failed);
        }
    }

    @Override
    public void onLoadData() {
        if(mPreferencesHelper.getUserSession().getRoles().contains(Permission.CONSTRUCTION_SCENE_MANAGER.getValue())){
            //获取日常巡视数量
            mPresenter.getPatrolCount(mPreferencesHelper.getUserSession().getUserName(),
                    DUPatrol.Type.DAILY_PATROL.getValue(), Upload.DEFAULT.getValue());
            //获取日常巡视已上传多媒体未上传的多媒体数量
            mPresenter.getPatrolMultiMediaCount(mPreferencesHelper.getUserSession().getUserName(),
                    DUPatrol.Type.DAILY_PATROL.getValue(), Upload.UPLOADED.getValue(), Upload.DEFAULT.getValue());
        }else if(mPreferencesHelper.getUserSession().getRoles().contains(Permission.INDEMNITY_APP_OPERATOR.getValue()) ||
                mPreferencesHelper.getUserSession().getRoles().contains(Permission.INDEMNITY_MANAGER.getValue()) ||
                mPreferencesHelper.getUserSession().getRoles().contains(Permission.INDEMNITY_OPERATOR.getValue())){
            //获取工程操作数量
            mPresenter.getOperateCount(mPreferencesHelper.getUserSession().getUserName(), Upload.DEFAULT.getValue());
            //获取工程操作已上传多媒体未上传的多媒体数量
            mPresenter.getOperateMultiMediaCount(mPreferencesHelper.getUserSession().getUserName(),
                    Upload.UPLOADED.getValue(), Upload.DEFAULT.getValue());
            //获取保中巡视数量
            mPresenter.getPatrolCount(mPreferencesHelper.getUserSession().getUserName(),
                    DUPatrol.Type.INDEMNITY_CENTER_PATROL.getValue(), Upload.DEFAULT.getValue());
            //获取保中巡视已上传多媒体未上传的多媒体数量
            mPresenter.getPatrolMultiMediaCount(mPreferencesHelper.getUserSession().getUserName(),
                    DUPatrol.Type.INDEMNITY_CENTER_PATROL.getValue(), Upload.UPLOADED.getValue(), Upload.DEFAULT.getValue());
            //获取验收数量
            mPresenter.getAcceptCount(mPreferencesHelper.getUserSession().getUserName(), Upload.DEFAULT.getValue());
            //获取验收已上传多媒体未上传的多媒体数量
            mPresenter.getAcceptMultiMediaCount(mPreferencesHelper.getUserSession().getUserName(),
                    Upload.UPLOADED.getValue(), Upload.DEFAULT.getValue());
        }
        mBinding.setRoles(mPreferencesHelper.getUserSession().getRoles());
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
        //登录用户角色为空时去重新加载用户信息
        if(mPreferencesHelper.getUserSession().getUserId() != userId || TextUtils.isEmpty(mPreferencesHelper.getUserSession().getRoles())){
            if(!NetworkUtil.isNetworkConnected(this)) finish();
            mPresenter.getUserInfo(userId);
        }else{
            onLoadData();
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        //从程序本身跳转到该界面 不需要判断uri和userId
        if(ParentActivity.TAG.equals(getIntent().getStringExtra(Constants.INTENT_PARAM_FROM))){
            onLoadData();
        }else {
            //对比主程序uri与子程序uri 避免主程序和子程序访问不同服务器数据
            mPresenter.compareUri(getIntent().getStringExtra(Constants.INTENT_PARAM_BASEURI));
        }
    }
}
