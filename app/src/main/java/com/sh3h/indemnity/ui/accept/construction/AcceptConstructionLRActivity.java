package com.sh3h.indemnity.ui.accept.construction;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.sh3h.dataprovider.data.entity.DUBudget;
import com.sh3h.dataprovider.data.entity.DUConstructionAccept;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.entity.DUWord;
import com.sh3h.dataprovider.data.entity.enums.Upload;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.util.NetworkUtil;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivityConstructionAcceptLrBinding;
import com.sh3h.indemnity.event.BusEvent;
import com.sh3h.indemnity.service.action.SyncAction;
import com.sh3h.indemnity.service.manager.IMainServiceManager;
import com.sh3h.indemnity.ui.base.ParentActivity;
import com.sh3h.indemnity.ui.multimedia.MultimediaFragment;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.ipc.location.MyLocation;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dengzhimin on 2017/7/10.
 */

public class AcceptConstructionLRActivity extends ParentActivity implements AcceptConstructionLRMvpView, MultimediaFragment.CameraCallBack {

    public static final int MEDIA_MAX = 6;//照片上限

    @Inject
    Bus mBus;

    @Inject
    AcceptConstructionLRPresenter mPresenter;

    @Inject
    PreferencesHelper mPreferencesHelper;

    private ActivityConstructionAcceptLrBinding mBinding;

    private MultimediaFragment mMultimediaFragment;

    private List<DUMultiMedia> mMultiMedias;

    private List<DUWord> mConstructionProgramWords;
    private List<DUWord> mConstructionChangeWords;
    private List<String> mNone;

    private DUBudget mDUBudget;

    private DUConstructionAccept mAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_construction_accept_lr);
        mBinding.include.toolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        mBinding.include.toolbar.setTitle(R.string.activity_construction_accept);
        setSupportActionBar(mBinding.include.toolbar);
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mBus.register(this);
        mBinding.setPresenter(mPresenter);
        Intent intent = getIntent();
        mDUBudget = (DUBudget) intent.getSerializableExtra(Constants.INTENT_PARAM_BUDGET);
        mMultimediaFragment = new MultimediaFragment();
        mMultimediaFragment.setCallBack(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.multimedia_container, mMultimediaFragment, mMultimediaFragment.getClass().getName()).commit();
        mPresenter.getConstructionProgram(Constants.WORDS_TYPE_CONSTRUCTION_PROGRAM);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
                if (!isCanUpload()) {
                    return super.onOptionsItemSelected(item);
                }
                showProgress(R.string.text_dialog_uploading);
                //施工整改Spinner 内容改变可能不会调用onItemSelected方法
                if(mBinding.spConstructionProgram.getSelectedItem().equals("不符合")){
                    mAccept.setConstructionReform(mConstructionChangeWords.get(mBinding.spConstructionChange.getSelectedItemPosition()).getValue());
                }
                MyLocation location = IMainServiceManager.getInstance(this).getLocation();
                mAccept.setBudgetId(mDUBudget.getBudgetId());
                mAccept.setProjectId(mDUBudget.getProjectId());
                mAccept.setOperator(mPreferencesHelper.getUserSession().getUserName());
                mAccept.setOperateTime(System.currentTimeMillis());
                mAccept.setLongitude(location == null ? 0 : location.getLongitude());
                mAccept.setLatitude(location == null ? 0 : location.getLatitude());
                mAccept.setUpload(Upload.DEFAULT.getValue());
                mPresenter.saveConstructionAccept(mDUBudget, mAccept, mMultiMedias, NetworkUtil.isNetworkConnected(this));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mBus.unregister(this);
    }

    @Override
    public void onNotifySpinner(int viewId, List<DUWord> words) {
        switch (viewId){
            case R.id.sp_construction_program:
                mConstructionProgramWords = words;
                mBinding.setConstructionProgram(getWordNames(words));
                break;
            case R.id.sp_construction_change:
                mConstructionChangeWords = words;
                mBinding.setConstructionChange(getWordNames(words));
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(mAccept == null){
            mAccept = new DUConstructionAccept();
        }
        switch (parent.getId()){
            case R.id.sp_construction_program:
                mAccept.setConstructionProgram(mConstructionProgramWords.get(position).getValue());
                if(parent.getSelectedItem().equals("符合")){
                    if(mNone == null){
                        mNone = new ArrayList<>();
                        mNone.add("无");
                    }
                    mDUBudget.setBudgetState(DUBudget.State.CONSTRUCTION_QUALIFIED.getValue());
                    mBinding.setConstructionChange(mNone);
                }else{
                    mDUBudget.setBudgetState(DUBudget.State.CONSTRUCTION_UNQUALIFIED.getValue());
                    mPresenter.getConstructionChange(Constants.WORDS_TYPE_CONSTRUCTION_CHANGE);
                }
                break;
            case R.id.sp_construction_change:
                //当spinner内容改变时可能不会掉用此处代码
                if(mBinding.spConstructionProgram.getSelectedItem().equals("不符合")){
                    mAccept.setConstructionReform(mConstructionChangeWords.get(position).getValue());
                }
                break;
        }
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
    public void onToast(String msg) {
        ApplicationsUtil.showMessage(this, msg);
    }

    @Override
    public void onUploadConstructionAccept() {
        startSyncService(SyncAction.UPLOAD_ALL_ACCEPT.ordinal());
    }

    @Override
    public void onSaveSuccess(int resId) {
        hideProgress();
        onToast(resId);
        finish();
    }

    @Override
    public void onSaveError(int resId) {
        hideProgress();
        onToast(resId);
    }

    private List<String> getWordNames(List<DUWord> duWords){
        if(duWords == null || duWords.size() == 0){
            return null;
        }
        List<String> names = new ArrayList<>();
        for(DUWord duWord : duWords){
            names.add(duWord.getName());
        }
        return names;
    }

    @Override
    public void onAddImage(String imageName, String imagePath) {
        DUMultiMedia duMultiMedia = new DUMultiMedia();
        duMultiMedia.setRelateType(DUMultiMedia.RelateType.CONSTRUCTION_ACCEPT.getValue());
        duMultiMedia.setFileType(DUMultiMedia.FileType.IMAGE.getValue());
        duMultiMedia.setFileName(imageName);
        duMultiMedia.setFilePath(imagePath);
        duMultiMedia.setFileTime(System.currentTimeMillis());
        duMultiMedia.setUpload(0);
        mMultiMedias.add(duMultiMedia);
    }

    @Override
    public void onDeleteImage(String imageName, String imagePath) {
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

    @Subscribe
    public void onUploadAcceptResult(BusEvent.UploadAccept event) {
        if (event.isSuccess()) {
            onToast(R.string.text_toast_accept_upload_success);
        } else {
            hideProgress();
            onToast(R.string.text_toast_accept_upload_failed);
            finish();
        }
    }

    @Subscribe
    public void onUploadMultiMediaResult(BusEvent.UploadMultiMedia event) {
        hideProgress();
        if (event.isSuccess()) {
            onToast(String.format("照片上传成功:%s 失败:%s", event.getSuccessCount(), event.getFailedCount()));
        } else {
            onToast(R.string.text_toast_photo_upload_failed);
        }
        finish();
    }

    private boolean isCanUpload() {
        if(mMultiMedias == null || mMultiMedias.size() == 0){
            onToast(R.string.text_toast_please_take_photo);
            return false;
        }
        return true;
    }
}
