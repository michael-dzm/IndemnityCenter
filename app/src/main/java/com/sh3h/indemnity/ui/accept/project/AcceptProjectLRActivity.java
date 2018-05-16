package com.sh3h.indemnity.ui.accept.project;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;

import com.sh3h.dataprovider.data.entity.DUBudget;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.entity.DUProjectAccept;
import com.sh3h.dataprovider.data.entity.DUWord;
import com.sh3h.dataprovider.data.entity.enums.Upload;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.util.NetworkUtil;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivityAcceptProjectLrBinding;
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
 * Created by xulongjun on 2017/3/13.
 */

public class AcceptProjectLRActivity extends ParentActivity implements AcceptProjectLRMvpView, MultimediaFragment.CameraCallBack {

    public static final int MEDIA_MAX = 6;//照片上限

    @Inject
    AcceptProjectLRPresenter mAcceptProjectLRPresenter;

    @Inject
    Bus mBus;

    @Inject
    PreferencesHelper mPreferencesHelper;

    private ActivityAcceptProjectLrBinding mBinding;

    private DatePickerDialog mAcceptDatePickerDialog;

    private MultimediaFragment mMultimediaFragment;

    private List<DUMultiMedia> mMultiMedias;

    private List<DUWord> mDuWords;

    private DUBudget mDUBudget;

    private DUProjectAccept mDUProjectAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_accept_project_lr);
        mBinding.include.toolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        mBinding.include.toolbar.setTitle(R.string.activity_project_accept);
        setSupportActionBar(mBinding.include.toolbar);
        getActivityComponent().inject(this);
        mAcceptProjectLRPresenter.attachView(this);
        mBus.register(this);
        mDUBudget = (DUBudget) getIntent().getSerializableExtra(Constants.INTENT_PARAM_BUDGET);
        mBinding.setPresenter(mAcceptProjectLRPresenter);

        mMultimediaFragment = new MultimediaFragment();
        mMultimediaFragment.setCallBack(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.multimedia_container, mMultimediaFragment, mMultimediaFragment.getClass().getName()).commit();

        mAcceptProjectLRPresenter.getAcceptResultData(Constants.WORDS_TYPE_ACCEPT_RESULT);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAcceptProjectLRPresenter.detachView();
        mBus.unregister(this);
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
    public void onSaveError(int resId) {
        hideProgress();
        onToast(resId);
    }

    @Override
    public void onSaveSuccess(int resId) {
        hideProgress();
        onToast(resId);
        finish();
    }

    @Override
    public void onCalendarView(int viewId) {
        if (mAcceptDatePickerDialog == null) {
            mAcceptDatePickerDialog = createDatePickerDialog(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                }
            }, System.currentTimeMillis());
        }
        mAcceptDatePickerDialog.show();
    }

    @Override
    public void onUploadProjectAccept() {
        startSyncService(SyncAction.UPLOAD_ALL_ACCEPT.ordinal());
    }

    @Override
    public void onGetAcceptResultData(List<DUWord> duWords) {
        mDuWords = duWords;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (mDUProjectAccept == null) {
            mDUProjectAccept = new DUProjectAccept();
        }
        switch (parent.getId()) {
            case R.id.sp_accept_result:
                int acceptResultValue = Integer.valueOf(mAcceptProjectLRPresenter.mAcceptResultWords.get(position).getValue());
                mDUProjectAccept.setAcceptResult(acceptResultValue);
                if (String.valueOf(acceptResultValue).equals(Constants.WORDS_AGREE_VALUE)) {
                    mDUBudget.setBudgetState(DUBudget.State.PROJECT_QUALIFIED.getValue());
                } else {
                    mDUBudget.setBudgetState(DUBudget.State.PROJECT_UNQUALIFIED.getValue());
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (mMultiMedias == null || mMultiMedias.size() == 0) {
                    onToast(R.string.text_toast_please_take_photo);
                    return false;
                }
                showProgress(R.string.text_dialog_uploading);
                // do save
                MyLocation location = IMainServiceManager.getInstance(this).getLocation();
                mDUProjectAccept.setBudgetId(mDUBudget.getBudgetId());
                mDUProjectAccept.setProjectId(mDUBudget.getProjectId());
                mDUProjectAccept.setOperator(mPreferencesHelper.getUserSession().getUserName());
                mDUProjectAccept.setOperateTime(System.currentTimeMillis());
                mDUProjectAccept.setRemark(mBinding.etRemark.getText().toString());
                mDUProjectAccept.setLongitude(location == null ? 0 : location.getLongitude());
                mDUProjectAccept.setLatitude(location == null ? 0 : location.getLatitude());
                mDUProjectAccept.setUpload(Upload.DEFAULT.getValue());
                mAcceptProjectLRPresenter.saveProjectAccept(mDUBudget, mDUProjectAccept, mMultiMedias,
                        NetworkUtil.isNetworkConnected(this));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddImage(String imageName, String imagePath) {
        // do save image
        DUMultiMedia duMultiMedia = new DUMultiMedia();
        duMultiMedia.setRelateType(DUMultiMedia.RelateType.PROJECT_ACCEPT.getValue());
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

}

