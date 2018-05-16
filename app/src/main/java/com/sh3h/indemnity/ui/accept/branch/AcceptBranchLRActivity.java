package com.sh3h.indemnity.ui.accept.branch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.sh3h.dataprovider.data.entity.DUBranchAccept;
import com.sh3h.dataprovider.data.entity.DUBudget;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.entity.DUWord;
import com.sh3h.dataprovider.data.entity.enums.Upload;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.util.NetworkUtil;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.databinding.ActivityAcceptBranchLrBinding;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 强度/气密验收
 * 先获取验收时间限制 再加载验收数据
 * Created by xulongjun on 2017/3/13.
 */

public class AcceptBranchLRActivity extends ParentActivity implements AcceptBranchLRMvpView, MultimediaFragment.CameraCallBack {

    @Inject
    AcceptBranchLRPresenter mProjectAcceptBranchLRPresenter;

    @Inject
    Bus mBus;

    @Inject
    PreferencesHelper mPreferencesHelper;

    @Inject
    ConfigHelper mConfigHelper;

    private ActivityAcceptBranchLrBinding mBinding;

    private MultimediaFragment mMultimediaFragment;

    private List<DUMultiMedia> mMultiMedias;

    private String mFlag;

    private DUBudget mDUBudget;

    private DUBranchAccept mDUBranchAccept;

    private MenuItem mSaveMenuItem;

    private Animation mAnimationIn;
    private Animation mAnimationOut;

    private SimpleDateFormat mDateFormat;

    private CountDownTimer mCountDownTimer;

    private boolean mIsShowing;

    private List<DUWord> mAcceptCheckSituationWords;
    private List<DUWord> mAcceptLeakSituationWords;
    private List<DUWord> mAcceptLeakChangeWords;
    private List<String> mNone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_accept_branch_lr);
        mBinding.include.toolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        getActivityComponent().inject(this);
        mProjectAcceptBranchLRPresenter.attachView(this);
        mBus.register(this);
        Intent intent = getIntent();
        mFlag = intent.getStringExtra(Constants.INTENT_PARAM_ACCEPT_TYPE);
        mDUBudget = (DUBudget) intent.getSerializableExtra(Constants.INTENT_PARAM_BUDGET);
        if (mFlag.equals(Constants.FLAG_ACCEPT_STRENGTH)) {
            mBinding.include.toolbar.setTitle(R.string.activity_accept_strength);
            mBinding.setIsStrength(true);
        } else {
            mBinding.include.toolbar.setTitle(R.string.activity_accept_airtight);
            mBinding.setIsStrength(false);
        }
        setSupportActionBar(mBinding.include.toolbar);
        mBinding.setPresenter(mProjectAcceptBranchLRPresenter);
        mBinding.swiperefreshLayout.setEnabled(false);

        mMultimediaFragment = new MultimediaFragment();
        mMultimediaFragment.setCallBack(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.multimedia_container, mMultimediaFragment, mMultimediaFragment.getClass().getName()).commit();
        mBinding.swiperefreshLayout.setRefreshing(true);
        mProjectAcceptBranchLRPresenter.getAcceptWords(Constants.WORDS_TYPE_CHECK_SITUATION);
        mProjectAcceptBranchLRPresenter.getAcceptWords(Constants.WORDS_TYPE_LEAK_SITUATION);
        mProjectAcceptBranchLRPresenter.getAcceptTimeLimit(NetworkUtil.isNetworkConnected(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        mSaveMenuItem = menu.findItem(R.id.action_save);
        mSaveMenuItem.setVisible(mMultiMedias != null && mMultiMedias.size() == 3);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                showProgress(R.string.text_dialog_uploading);
                // do save
                if (mFlag.equals(Constants.FLAG_ACCEPT_STRENGTH)) {
                    if ((mDUBranchAccept.getCheckSituation()).equals(Constants.WORDS_TYPE_NORMAL)
                            && (mDUBranchAccept.getLeakSituation()).equals(Constants.WORDS_TYPE_LEAK_NO)) {
                        mDUBudget.setBudgetState(DUBudget.State.STRENGTH_QUALIFIED.getValue());
                    } else {
                        mDUBudget.setBudgetState(DUBudget.State.STRENGTH_UNQUALIFIED.getValue());
                    }
                } else {
                    if ((mDUBranchAccept.getCheckSituation()).equals(Constants.WORDS_TYPE_NORMAL)
                            && (mDUBranchAccept.getLeakSituation()).equals(Constants.WORDS_TYPE_LEAK_NO)) {
                        mDUBudget.setBudgetState(DUBudget.State.AIRTIGHT_QUALIFIED.getValue());
                    } else {
                        mDUBudget.setBudgetState(DUBudget.State.AIRTIGHT_UNQUALIFIED.getValue());
                    }
                }
                if(mBinding.spLeakSituation.getSelectedItem().equals("有")){
                    mDUBranchAccept.setLeakReform(mAcceptLeakChangeWords.get(mBinding.spLeakChange.getSelectedItemPosition()).getValue());
                }
                MyLocation location = IMainServiceManager.getInstance(this).getLocation();
                mDUBranchAccept.setOperateTime(System.currentTimeMillis());
                mDUBranchAccept.setLongitude(location == null ? 0 : location.getLongitude());
                mDUBranchAccept.setLatitude(location == null ? 0 : location.getLatitude());
                mDUBranchAccept.setUpload(Upload.DEFAULT.getValue());
                mProjectAcceptBranchLRPresenter.saveBranchAccept(mDUBudget, mDUBranchAccept, NetworkUtil.isNetworkConnected(this));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
        mProjectAcceptBranchLRPresenter.detachView();
    }

    @Override
    public void onTakePhoto() {
        if (mMultimediaFragment == null) {
            return;
        }
        if (mMultiMedias == null) {
            mMultiMedias = new ArrayList<>();
        }
        if((mBinding.layoutStepOne.getVisibility() == View.VISIBLE && mMultiMedias.size() >= 1) ||
                (mBinding.layoutStepTwo.getVisibility() == View.VISIBLE && mMultiMedias.size() >= 2) ||
                (mBinding.layoutStepThree.getVisibility() == View.VISIBLE && mMultiMedias.size() >= 3)){
            ApplicationsUtil.showMessage(this, R.string.text_toast_every_step_limit_take_one_photo);
            return;
        }
        mMultimediaFragment.takePhoto();
    }

    @Override
    public void onToast(int resId) {
        ApplicationsUtil.showMessage(this, resId);
    }

    public void onToast(String resId) {
        ApplicationsUtil.showMessage(this, resId);
    }

    @Override
    public void onError(int resId) {
        hideProgress();
        onToast(resId);
    }

    @Override
    public void onSuccess(int resId) {
        hideProgress();
        onToast(resId);
        finish();
    }

    @Override
    public void onUploadBranchAccept() {
        startSyncService(SyncAction.UPLOAD_ALL_ACCEPT.ordinal());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (mDUBranchAccept == null) {
            mDUBranchAccept = new DUBranchAccept();
        }
        switch (parent.getId()) {
            case R.id.sp_check_situation:
                mDUBranchAccept.setCheckSituation(mAcceptCheckSituationWords.get(position).getValue());
                break;
            case R.id.sp_leak_situation:
                mDUBranchAccept.setLeakSituation(mAcceptLeakSituationWords.get(position).getValue());
                if(parent.getSelectedItem().equals("无")){
                    if(mNone == null){
                        mNone = new ArrayList<>();
                        mNone.add("无");
                    }
                    mBinding.setAcceptLeakChange(mNone);
                }else{
                    mProjectAcceptBranchLRPresenter.getAcceptWords(Constants.WORDS_TYPE_LEAK_CHANGE);
                }
                break;
            case R.id.sp_leak_change:
                if(mBinding.spLeakSituation.getSelectedItem().equals("有")){
                    mDUBranchAccept.setLeakReform(mAcceptLeakChangeWords.get(position).getValue());
                }
                break;
        }
    }

    @Override
    public void onAddImage(String imageName, String imagePath) {
        // do save image
        DUMultiMedia duMultiMedia = new DUMultiMedia();
        duMultiMedia.setRelateType(mFlag.equals(Constants.FLAG_ACCEPT_STRENGTH) ?
                DUMultiMedia.RelateType.STRENGTH_ACCEPT.getValue() : DUMultiMedia.RelateType.AIRTIGHT_ACCEPT.getValue());
        duMultiMedia.setFileType(DUMultiMedia.FileType.IMAGE.getValue());
        duMultiMedia.setFileName(imageName);
        duMultiMedia.setFilePath(imagePath);
        duMultiMedia.setFileTime(System.currentTimeMillis());
        duMultiMedia.setUpload(Upload.DEFAULT.getValue());
        mMultiMedias.add(duMultiMedia);
        //第一次添加图片时必须同时保存试验数据，保证图片关联ID不为空
        if(mDUBranchAccept == null){
            mDUBranchAccept= new DUBranchAccept();
            MyLocation location = IMainServiceManager.getInstance(this).getLocation();
            mDUBranchAccept.setBudgetId(mDUBudget.getBudgetId());
            mDUBranchAccept.setProjectId(mDUBudget.getProjectId());
            mDUBranchAccept.setAcceptType(mFlag.equals(Constants.FLAG_ACCEPT_STRENGTH) ?
                    DUBranchAccept.ACCEPT_TYPE_STRENGTH : DUBranchAccept.ACCEPT_TYPE_AIRTIGHT);
            mDUBranchAccept.setOperator(mPreferencesHelper.getUserSession().getUserName());
            mDUBranchAccept.setOperateTime(System.currentTimeMillis());
            mDUBranchAccept.setLongitude(location == null ? 0 : location.getLongitude());
            mDUBranchAccept.setLatitude(location == null ? 0 : location.getLatitude());
            //试验上传标志为不可上传 需要完成三步才能上传
            mDUBranchAccept.setUpload(Upload.CANNOT.getValue());
        }
        mProjectAcceptBranchLRPresenter.saveBranchAccept(mDUBranchAccept, duMultiMedia);
    }

    @Override
    public void onDeleteImage(String imageName, String imagePath) {
        if (mMultiMedias == null || mMultiMedias.size() == 0) {
            return;
        }
        for (DUMultiMedia media : mMultiMedias) {
            if (media.getFileName().equals(imageName)) {
                mProjectAcceptBranchLRPresenter.deleteMultiMedia(media);
                break;
            }
        }
    }

    @Override
    public void onInitAccept(DUBranchAccept accept) {
        mDUBranchAccept = accept;
        if(mDUBranchAccept != null){
            mProjectAcceptBranchLRPresenter.getMultiMedias(accept.getAcceptId(),
                    accept.getAcceptType() == DUBranchAccept.ACCEPT_TYPE_STRENGTH ?
                            DUMultiMedia.RelateType.STRENGTH_ACCEPT.getValue() : DUMultiMedia.RelateType.AIRTIGHT_ACCEPT.getValue());
        }
    }

    @Override
    public void onSaveAcceptSuccess(Map<String, Long> resultId) {
        //下一步
        mProjectAcceptBranchLRPresenter.nextStep();
        //第三步做完显示上传保存按钮
        mSaveMenuItem.setVisible(mMultiMedias.size() == 3);
        if(mDUBranchAccept.getAcceptId() == null){
            mDUBranchAccept.setAcceptId(resultId.get("acceptId"));
        }
        DUMultiMedia multiMedia = mMultiMedias.get(mMultiMedias.size() - 1);
        if(multiMedia.getMultimediaId() == null){
            multiMedia.setMultimediaId(resultId.get("multimediaId"));
            multiMedia.setRelateId(resultId.get("acceptId"));
        }
        if(mMultiMedias.size() == 3){
            clearCountDownTimer();
            startAnimationOut();
        }else{
            //显示提示信息
            startAnimationIn();
        }
    }

    @Override
    public void onDeleteAcceptSuccess() {
        //删除数据成功 重置页面数据
        mDUBranchAccept = null;
        for (DUMultiMedia duMultiMedia : mMultiMedias){
            new File(duMultiMedia.getFilePath()).delete();
        }
        //清空多媒体数据
        mMultiMedias.clear();
        mMultimediaFragment.notifyMultiMedia(mMultiMedias);
        //超时重置
        mProjectAcceptBranchLRPresenter.setTimeOut(false);
        //步数重置
        mProjectAcceptBranchLRPresenter.setStep(1);
        //关闭提示信息
        startAnimationOut();
    }

    @Override
    public void onDeleteMultiMediaSuccess(DUMultiMedia multiMedia) {
        mMultiMedias.remove(multiMedia);
        mSaveMenuItem.setVisible(mMultiMedias.size() == 3);
        //上一步
        mProjectAcceptBranchLRPresenter.preStep();
        if(mMultiMedias.size() > 0){
            //显示提示信息
            startAnimationIn();
        }else{
            //关闭提示信息
            startAnimationOut();
            //结束计时
            clearCountDownTimer();
        }
    }

    @Override
    public void onInitMultiMedias(List<DUMultiMedia> multiMedias) {
        //初始化多媒体文件
        mMultiMedias = multiMedias;
        //初始化试验步数
        //多媒体数量为空是第一步
        //多媒体数量为1是第二步
        //多媒体数量为2是 根据时间配置判断是否是等待第三步
        if(mMultiMedias == null || mMultiMedias.size() <= 1){
            mProjectAcceptBranchLRPresenter.setStep(mMultiMedias == null ? 1 : mMultiMedias.size() + 1);
        }else if(mMultiMedias.size() == 2){
            mProjectAcceptBranchLRPresenter.setStep(mMultiMedias.get(mMultiMedias.size() - 1).getFileTime() +
                    (mBinding.getIsStrength() ? mConfigHelper.getStrengthStepTwoToThreeTimeInterval() :
                            mConfigHelper.getAirtightStepTwoToThreeTimeInterval()) > System.currentTimeMillis() ? 3 : 4);
        }else if(mMultiMedias.size() == 3){
            mProjectAcceptBranchLRPresenter.setStep(5);
        }
        if(mMultiMedias != null && mMultiMedias.size() > 0){
            mMultimediaFragment.notifyMultiMedia(mMultiMedias);
            if(mMultiMedias.size() < 3) startAnimationIn();
        }
        if(mSaveMenuItem != null){
            mSaveMenuItem.setVisible(mMultiMedias != null && mMultiMedias.size() == 3);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_step_one:
                mBinding.layoutStepOne.setVisibility(View.VISIBLE);
                mBinding.layoutStepTwo.setVisibility(View.GONE);
                mBinding.layoutStepThree.setVisibility(View.GONE);
                break;
            case R.id.btn_step_two:
                mBinding.layoutStepOne.setVisibility(View.GONE);
                mBinding.layoutStepTwo.setVisibility(View.VISIBLE);
                mBinding.layoutStepThree.setVisibility(View.GONE);
                break;
            case R.id.btn_step_three:
                mBinding.layoutStepOne.setVisibility(View.GONE);
                mBinding.layoutStepTwo.setVisibility(View.GONE);
                mBinding.layoutStepThree.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_hide_prompt:
                if(mProjectAcceptBranchLRPresenter.isTimeOut()){
                    //重新开始 删除数据
                    mProjectAcceptBranchLRPresenter.deleteBranchAccept(mDUBranchAccept, mMultiMedias);
                }else {
                    //关闭提示信息
                    startAnimationOut();
                }
                break;
        }
    }

    @Override
    public void onNotifySpinner(String group, List<DUWord> duWords) {
        switch (group) {
            case Constants.WORDS_TYPE_CHECK_SITUATION:
                mAcceptCheckSituationWords = duWords;
                mBinding.setAcceptCheckSituation(getWordNames(duWords));
                break;
            case Constants.WORDS_TYPE_LEAK_SITUATION:
                mAcceptLeakSituationWords = duWords;
                mBinding.setAcceptLeakSituation(getWordNames(duWords));
                break;
            case Constants.WORDS_TYPE_LEAK_CHANGE:
                mAcceptLeakChangeWords = duWords;
                mBinding.setAcceptLeakChange(getWordNames(duWords));
                break;
        }
    }

    @Override
    public void onLoadBranchAccept() {
        mBinding.swiperefreshLayout.setRefreshing(false);
        mProjectAcceptBranchLRPresenter.getBranchAccept(Upload.CANNOT.getValue(), mPreferencesHelper.getUserSession().getUserName(),
                mDUBudget.getProjectId(), mDUBudget.getBudgetId(),
                mFlag.equals(Constants.FLAG_ACCEPT_STRENGTH) ? DUBranchAccept.ACCEPT_TYPE_STRENGTH : DUBranchAccept.ACCEPT_TYPE_AIRTIGHT);
    }

    /**
     * 显示动画监听
     */
    private Animation.AnimationListener mInListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            resetCountDownTimer();
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mIsShowing = true;
            mBinding.layoutPrompt.clearAnimation();
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBinding.layoutPrompt.getLayoutParams();
            params.topMargin = 0;
            mBinding.layoutPrompt.setLayoutParams(params);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    /**
     * 隐藏动画监听
     */
    private Animation.AnimationListener mOutListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mIsShowing = false;
            mBinding.layoutPrompt.clearAnimation();
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBinding.layoutPrompt.getLayoutParams();
            params.topMargin = -mBinding.layoutPrompt.getHeight();
            mBinding.layoutPrompt.setLayoutParams(params);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    //计时器
    private class AcceptCountDownTimer extends CountDownTimer{

        public AcceptCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @SuppressLint("StringFormatMatches")
        @Override
        public void onTick(long millisUntilFinished) {
            if(mDateFormat == null){
                mDateFormat = new SimpleDateFormat("mm:ss");
            }
            if(mProjectAcceptBranchLRPresenter.getStep() == 3){
                mBinding.textPrompt.setText(getResources().getString(R.string.text_prompt_time_within,
                        mDateFormat.format(new Date(millisUntilFinished)), getResources().getString(R.string.text_step_three)));
            }else {
                mBinding.textPrompt.setText(getResources().getString(R.string.text_prompt_time_after,
                        mDateFormat.format(new Date(millisUntilFinished)), mProjectAcceptBranchLRPresenter.getStep() < 3 ?
                                getResources().getString(R.string.text_step_two) : getResources().getString(R.string.text_step_three)));
            }

        }

        @Override
        public void onFinish() {
            if(mProjectAcceptBranchLRPresenter.getStep() == 3){
                mProjectAcceptBranchLRPresenter.nextStep();
            }else{
                mBinding.textPrompt.setText(getResources().getString(R.string.text_accept_out_time));
                mProjectAcceptBranchLRPresenter.setTimeOut(true);
            }
            startAnimationIn();
        }
    }

    /**
     * 显示动画
     */
    protected void startAnimationIn(){
        if(mIsShowing){//正在提示时 不加载动画效果
            resetCountDownTimer();
            return;
        }
        if(mAnimationIn == null){
            mAnimationIn = AnimationUtils.loadAnimation(this, R.anim.in_from_top);
            mAnimationIn.setAnimationListener(mInListener);
        }
        mBinding.layoutPrompt.startAnimation(mAnimationIn);
    }

    /**
     * 隐藏动画
     */
    protected void startAnimationOut(){
        if(!mIsShowing) return;
        if(mAnimationOut == null){
            mAnimationOut = AnimationUtils.loadAnimation(this, R.anim.out_from_top);
            mAnimationOut.setAnimationListener(mOutListener);
        }
        mBinding.layoutPrompt.startAnimation(mAnimationOut);
    }

    /**
     * 开启计时器
     */
    protected void resetCountDownTimer(){
        clearCountDownTimer();
        //超时不计时
        if(mProjectAcceptBranchLRPresenter.isTimeOut()){
            return;
        }
        //计算时间
        long fileTime = mMultiMedias.get(mMultiMedias.size() - 1).getFileTime();
        long millisInFuture = 0;
        switch (mProjectAcceptBranchLRPresenter.getStep()){
            case 2:
                millisInFuture = fileTime + (mBinding.getIsStrength() ? mConfigHelper.getStrengthStepTwoTimeLimit() :
                        mConfigHelper.getAirtightStepTwoTimeLimit()) - System.currentTimeMillis();
                break;
            case 3:
                millisInFuture = fileTime + (mBinding.getIsStrength() ? mConfigHelper.getStrengthStepTwoToThreeTimeInterval() :
                        mConfigHelper.getAirtightStepTwoToThreeTimeInterval()) - System.currentTimeMillis();
                break;
            case 4:
                millisInFuture = fileTime + (mBinding.getIsStrength() ? mConfigHelper.getStrengthStepThreeTimeLimit() : mConfigHelper.getAirtightStepThreeTimeLimit()) +
                        (mBinding.getIsStrength() ? mConfigHelper.getStrengthStepTwoToThreeTimeInterval() : mConfigHelper.getAirtightStepTwoToThreeTimeInterval()) - System.currentTimeMillis();
                break;
        }
        mCountDownTimer = new AcceptCountDownTimer(millisInFuture, 1000);
        mCountDownTimer.start();
    }

    /**
     * 清除计时器
     */
    protected void clearCountDownTimer(){
        if(mCountDownTimer != null){
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
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

}

