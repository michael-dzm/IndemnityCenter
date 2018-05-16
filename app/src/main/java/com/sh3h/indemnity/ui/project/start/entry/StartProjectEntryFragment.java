package com.sh3h.indemnity.ui.project.start.entry;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.entity.DUOperate;
import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.dataprovider.data.entity.enums.Upload;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.util.NetworkUtil;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.FragmentStartProjectEntryBinding;
import com.sh3h.indemnity.event.BusEvent;
import com.sh3h.indemnity.service.action.SyncAction;
import com.sh3h.indemnity.service.manager.IMainServiceManager;
import com.sh3h.indemnity.ui.base.ParentFragment;
import com.sh3h.indemnity.ui.multimedia.MultimediaFragment;
import com.sh3h.indemnity.ui.project.start.StartProjectActivity;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.ipc.location.MyLocation;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dengzhimin on 2017/3/8.
 */

public class StartProjectEntryFragment extends ParentFragment implements StartProjectEntryMvpView, MultimediaFragment.CameraCallBack{

    @Inject
    StartProjectEntryPresenter mPresenter;

    @Inject
    Bus mBus;

    @Inject
    PreferencesHelper mPreferencesHelper;

    private MultimediaFragment mMultimediaFragment;

    private FragmentStartProjectEntryBinding mBinding;

    private DUProject mProject;

    private List<DUMultiMedia> mMultiMedias;

    private int mCurrentRelateType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((StartProjectActivity)getActivity()).getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mBus.register(this);
        Bundle bundle = getArguments();
        if(bundle != null){
            mProject = bundle.getParcelable(Constants.INTENT_PARAM_PROJECT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_start_project_entry, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.setPresenter(mPresenter);
        //load multimedia fragment
        mMultimediaFragment = new MultimediaFragment();
        mMultimediaFragment.setCallBack(this);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.multimedia_container, mMultimediaFragment, mMultimediaFragment.getClass().getName()).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                //
                if(!checkMultimediaExist(DUMultiMedia.RelateType.START_PROJECT_CARD.getValue())){
                    onToast(R.string.text_toast_please_take_card_photo);
                    return false;
                }
                if(!checkMultimediaExist(DUMultiMedia.RelateType.START_PROJECT_ORDER.getValue())){
                    onToast(R.string.text_toast_take_order_photo);
                    return false;
                }
                if(!checkMultimediaExist(DUMultiMedia.RelateType.START_PROJECT_NAMEPLATE.getValue())){
                    onToast(R.string.text_toast_take_nameplate_photo);
                    return false;
                }
                if(!checkMultimediaExist(DUMultiMedia.RelateType.START_PROJECT_FACILITY.getValue())){
                    onToast(R.string.text_toast_take_facility_photo);
                    return false;
                }
                showProgress(R.string.text_dialog_uploading);
                MyLocation location = IMainServiceManager.getInstance(getContext()).getLocation();
                // do save
                mProject.setState(DUProject.State.START.getValue());
                mProject.setStartTime(System.currentTimeMillis());
                DUOperate duOperate = new DUOperate();
                duOperate.setProjectId(mProject.getProjectId());
                duOperate.setOperateType(DUOperate.Type.START.getValue());
                duOperate.setStartTime(System.currentTimeMillis());
                duOperate.setOperator(mPreferencesHelper.getUserSession().getUserName());
                duOperate.setOperateTime(System.currentTimeMillis());
                duOperate.setLongitude(location == null ? 0 : location.getLongitude());
                duOperate.setLatitude(location == null ? 0 : location.getLatitude());
                duOperate.setUpload(Upload.DEFAULT.getValue());
                mPresenter.saveOperate(mProject, duOperate, mMultiMedias, NetworkUtil.isNetworkConnected(getContext()));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mBus.unregister(this);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onTakePhoto(int relateType) {
        mCurrentRelateType = relateType;
        if(mMultimediaFragment == null){
            return;
        }
        if(mMultiMedias == null){
            mMultiMedias = new ArrayList<>();
        }
        mMultimediaFragment.takePhoto();
    }

    @Override
    public void onToast(int resId) {
        ApplicationsUtil.showMessage(getContext(), resId);
    }

    @Override
    public void onToast(String message) {
        ApplicationsUtil.showMessage(getContext(), message);
    }

    @Override
    public void onSaveSuccess(int resId) {
        hideProgress();
        onToast(resId);
        getActivity().finish();
    }

    @Override
    public void onSaveError(int resId) {
        hideProgress();
        onToast(resId);
    }

    @Override
    public void onUploadOperates() {
        startSyncService(SyncAction.UPLOAD_ALL_OPERATE.ordinal());
    }

    @Override
    public void onAddImage(String imageName, String imagePath) {
        // do save image
        DUMultiMedia duMultiMedia = new DUMultiMedia();
        //关联ID是操作ID 不是工程ID
        duMultiMedia.setRelateType(mCurrentRelateType);
        duMultiMedia.setFileType(DUMultiMedia.FileType.IMAGE.getValue());
        duMultiMedia.setFileName(imageName);
        duMultiMedia.setFilePath(imagePath);
        duMultiMedia.setFileTime(System.currentTimeMillis());
        duMultiMedia.setUpload(Upload.DEFAULT.getValue());
        mMultiMedias.add(duMultiMedia);
    }

    @Override
    public void onDeleteImage(String imageName, String imagePath) {
        //do delete image
        if(mMultiMedias == null || mMultiMedias.size() == 0){
            return;
        }
        for(DUMultiMedia media : mMultiMedias){
            if(media.getFileName().equals(imageName)){
                mMultiMedias.remove(media);
                break;
            }
        }
    }

    @Subscribe
    public void onUploadOperateResult(BusEvent.UploadOperate event){
        if(event.isSuccess()){
            onToast(R.string.text_toast_start_project_upload_success);
        }else{
            hideProgress();
            onToast(R.string.text_toast_start_project_upload_failed);
            getActivity().finish();
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
        getActivity().finish();
    }

    /**
     * 检查图片是否存在
     */
    private boolean checkMultimediaExist(int relateType){
        if(mMultiMedias == null || mMultiMedias.size() == 0){
            return false;
        }
        for(DUMultiMedia multiMedia : mMultiMedias){
            if(multiMedia.getRelateType() == relateType){
                return true;
            }
        }
        return false;
    }
}
