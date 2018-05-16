package com.sh3h.indemnity.ui.patrol.entry;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.entity.DUPatrol;
import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.dataprovider.data.entity.DUViolation;
import com.sh3h.dataprovider.data.entity.DUWord;
import com.sh3h.dataprovider.data.entity.enums.Permission;
import com.sh3h.dataprovider.data.entity.enums.Upload;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.util.NetworkUtil;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.FragmentPatrolEntryBinding;
import com.sh3h.indemnity.event.BusEvent;
import com.sh3h.indemnity.service.action.SyncAction;
import com.sh3h.indemnity.service.manager.IMainServiceManager;
import com.sh3h.indemnity.ui.base.ParentFragment;
import com.sh3h.indemnity.ui.multimedia.MultimediaFragment;
import com.sh3h.indemnity.ui.patrol.PatrolActivity;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.ipc.location.MyLocation;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dengzhimin on 2017/3/8.
 */

public class PatrolEntryFragment extends ParentFragment implements PatrolEntryMvpView, MultimediaFragment.CameraCallBack,
        SwipeRefreshLayout.OnRefreshListener{

    public static final int MEDIA_MAX = 1;//照片上限

    @Inject
    PatrolEntryPresenter mPresenter;

    @Inject
    Bus mBus;

    @Inject
    PreferencesHelper mPreferencesHelper;

    private MultimediaFragment mMultimediaFragment;

    private FragmentPatrolEntryBinding mBinding;

    private List<DUMultiMedia> mMultiMedias;

    private List<DUViolation> mViolations;//违规内容

    private List<DUWord> mWords;//违规结果

    private DUPatrol mPatrol;//巡视信息

    private DUProject mProject;

    private String mIntentFlag;

    private int mConstructionContentPosition = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((PatrolActivity)getActivity()).getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mBus.register(this);
        Bundle bundle = getArguments();
        if(bundle != null){
            mProject = bundle.getParcelable(Constants.INTENT_PARAM_PROJECT);
            mIntentFlag = bundle.getString(Constants.INTENT_PARAM_FLAG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_patrol_entry, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.setPresenter(mPresenter);
        mBinding.setRefreshListener(this);
        mPatrol = new DUPatrol();
        mBinding.setPatrol(mPatrol);
        //加载违规配置信息
        if(NetworkUtil.isNetworkConnected(getContext())){
            mBinding.swiperefreshLayout.setRefreshing(true);
            mPresenter.loadViolations();//在线加载
        }else {
            mPresenter.getAllViolationTypes();//本地加载
        }
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
                if(mMultiMedias == null || mMultiMedias.size() == 0){
                    onToast(R.string.text_toast_please_take_photo);
                    break;
                }
                showProgress(R.string.text_dialog_uploading);
                MyLocation location = IMainServiceManager.getInstance(getContext()).getLocation();
                // do save
                mPatrol.setProjectId(mProject.getProjectId());
                mPatrol.setPatrolType(mIntentFlag.equals(Constants.FLAG_DAILY_PATROL) ?
                        DUPatrol.Type.DAILY_PATROL.getValue() : DUPatrol.Type.INDEMNITY_CENTER_PATROL.getValue());
                mPatrol.setOperator(mPreferencesHelper.getUserSession().getUserName());
                mPatrol.setOperateTime(System.currentTimeMillis());
                mPatrol.setLongitude(location == null ? 0 : location.getLongitude());
                mPatrol.setLatitude(location == null ? 0 : location.getLatitude());
                mPatrol.setUpload(Upload.DEFAULT.getValue());
                mPresenter.savePatrol(mPatrol, mMultiMedias, NetworkUtil.isNetworkConnected(getContext()));
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.sp_construction_type:
                mPresenter.getViolationByType((String) parent.getSelectedItem());
                break;
            case R.id.sp_construction_content:
                mConstructionContentPosition = position;
                mPatrol.setViolationNumber(mViolations.get(position).getViolationNumber());
                break;
            case R.id.sp_construction_result:
                mPatrol.setPatrolSituation(mWords.get(position).getValue());
                break;
        }

    }

    @Override
    public void onTakePhoto() {
        if(mMultimediaFragment == null){
            return;
        }
        if(mMultiMedias == null){
            mMultiMedias = new ArrayList<>();
        }
        if(mMultiMedias.size() >= MEDIA_MAX){
            onToast(R.string.text_toast_take_photo_limit);
            return;
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
    public void onLoadError(int resId) {
        mBinding.swiperefreshLayout.setRefreshing(false);
        onToast(resId);
    }

    @Override
    public void onLoadSuccess() {
        mBinding.swiperefreshLayout.setRefreshing(false);
        //获取所有违规类型
        mPresenter.getAllViolationTypes();
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
        showExistDialog();
    }

    @Override
    public void onNotifyTypeSpinner(List<DUViolation> violations) {
        if(violations != null && violations.size() > 0){
            //停工工程只能选择施工类型"停工巡视"
            for(Iterator<DUViolation> it = violations.iterator();it.hasNext();){
                DUViolation duViolation = it.next();
                if(mProject.getState() == DUProject.State.STOP.getValue()){
                    if(!"stop".equals(duViolation.getRemark())){
                        it.remove();
                    }
                }else{
                    if("stop".equals(duViolation.getRemark())){
                        it.remove();
                    }
                }
            }
            List<String> violationTypes = new ArrayList<String>();
            for(DUViolation duViolation : violations){
                violationTypes.add(duViolation.getViolationType());
            }
            mBinding.setConstructionTypes(violationTypes);
        }
    }

    @Override
    public void onNotifyContentSpinner(List<DUViolation> violations) {
        if(violations != null && violations.size() > 0){
            mViolations = violations;
            List<String> constructionContents = new ArrayList<>();
            for(DUViolation duViolation : mViolations){
                constructionContents.add(duViolation.getViolationContent());
            }
            mBinding.setConstructionContents(constructionContents);
            if(mConstructionContentPosition == 0){//如果施工内容选中position=0，将不再触发onItemSelected事件
                mPatrol.setViolationNumber(mViolations.get(0).getViolationNumber());
            }else{
                mBinding.spConstructionContent.setSelection(0);
            }
        }
    }

    @Override
    public void onNotifyResultSpinner(List<DUWord> words) {
        if(words != null && words.size() > 0){
            mWords = words;
            List<String> constructionResults = new ArrayList<>();
            for(DUWord duWord : mWords){
                constructionResults.add(duWord.getName());
            }
            mBinding.setConstructionResults(constructionResults);
        }
    }

    @Override
    public void onUploadPatrols() {
        startSyncService(SyncAction.UPLOAD_ALL_PATROL.ordinal(), mIntentFlag.equals(Constants.FLAG_DAILY_PATROL) ?
                DUPatrol.Type.DAILY_PATROL.getValue() : DUPatrol.Type.INDEMNITY_CENTER_PATROL.getValue());
    }

    @Override
    public void onAddImage(String imageName, String imagePath) {
        // do save image
        DUMultiMedia duMultiMedia = new DUMultiMedia();
        //多媒体应该关联巡视ID 而不是工程ID
        duMultiMedia.setRelateType(mIntentFlag.equals(Constants.FLAG_DAILY_PATROL) ?
                DUMultiMedia.RelateType.DAILY_PATROL.getValue() : DUMultiMedia.RelateType.CENTER_PATROL.getValue());
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
    public void onUploadPatrolResult(BusEvent.UploadPatrol event){
        onToast(event.isSuccess() ? R.string.text_toast_patrol_upload_success : R.string.text_toast_patrol_upload_failed);
        if(!event.isSuccess()){
            hideProgress();
            showExistDialog();
        }
    }

    @Subscribe
    public void onUploadMultiMediaResult(BusEvent.UploadMultiMedia event){
        hideProgress();
        if (event.isSuccess()) {
            onToast(String.format("照片上传成功:%s 失败:%s", event.getSuccessCount(), event.getFailedCount()));
        } else {
            onToast(R.string.text_toast_photo_upload_failed);
        }
        showExistDialog();
    }

    @Override
    public void onRefresh() {
        mBinding.swiperefreshLayout.setRefreshing(false);
    }

    private void showExistDialog(){
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.text_dialog_title_prompt)
                .setMessage(R.string.text_dialog_content_is_next_patrol)
                .setPositiveButton(R.string.text_dialog_button_next_patrol, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //resert UI
                        mBinding.spConstructionType.setSelection(0);
                        mBinding.spConstructionContent.setSelection(0);
                        mBinding.spConstructionResult.setSelection(0);
                        mBinding.etViolationRemark.setText(null);
                        mMultiMedias.clear();
                        mMultimediaFragment.clearImage();

                    }
                })
                .setNegativeButton(R.string.text_dialog_button_exist, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .show();
    }
}
