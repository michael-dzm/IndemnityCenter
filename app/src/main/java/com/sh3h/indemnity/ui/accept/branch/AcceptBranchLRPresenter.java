package com.sh3h.indemnity.ui.accept.branch;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.view.View;
import android.widget.AdapterView;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUBranchAccept;
import com.sh3h.dataprovider.data.entity.DUBudget;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.entity.DUWord;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.sh3h.indemnity.util.Constants.WORDS_TYPE_LEAK_CHANGE;
import static com.sh3h.indemnity.util.Constants.WORDS_TYPE_CHECK_SITUATION;
import static com.sh3h.indemnity.util.Constants.WORDS_TYPE_LEAK_SITUATION;

/**
 * Created by xulongjun on 2017/3/13.
 */

public class AcceptBranchLRPresenter extends ParentPresenter<AcceptBranchLRMvpView> {
    public static final String TAG = AcceptBranchLRPresenter.class.getSimpleName();

    public ObservableBoolean mIsTimeOut = new ObservableBoolean();
    //第一步1 第二步2 第二步到第三步等待3 第三步4 完成5
    public ObservableInt mCurrentStep = new ObservableInt(1);

    @Inject
    public AcceptBranchLRPresenter(DataManager dataManager) {
        super(dataManager);
    }

    /**
     * Spinner点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        getMvpView().onItemSelected(parent, view, position, id);
    }

    public void onCameraClick(View view) {
        getMvpView().onTakePhoto();
    }

    public void setStep(int step){
        mCurrentStep.set(step);
    }

    public int getStep(){
        return mCurrentStep.get();
    }

    public void nextStep(){
        mCurrentStep.set(mCurrentStep.get() + 1);
    }

    public void preStep(){
        mCurrentStep.set(mCurrentStep.get() - 1);
    }

    public void setTimeOut(boolean timeOut){
        mIsTimeOut.set(timeOut);
    }

    public boolean isTimeOut(){
        return mIsTimeOut.get();
    }

    public void onClick(View view){
        getMvpView().onClick(view);
    }

    public void getAcceptWords(final String group) {
        LogUtil.i(TAG, "getConstructionProgram");
        mSubscription.add(mDataManager.getWords(group)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUWord>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getAcceptWords:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getAcceptWords:onError:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(List<DUWord> duWords) {
                        LogUtil.i(TAG, "getAcceptWords:onNext");
                        if (duWords == null || duWords.size() == 0) {
                            return;
                        }
                        getMvpView().onNotifySpinner(group, duWords);
                    }
                }));
    }

    /**
     * 更新工程预算信息
     * 添加或更新支管验收操作
     * @param duBudget
     * @param duBranchAccept
     */
    public void saveBranchAccept(DUBudget duBudget, DUBranchAccept duBranchAccept, final boolean isNetworkConnected){
        mSubscription.add(mDataManager.saveBranchAccept(duBudget, duBranchAccept)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "saveBranchAccept : onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "saveBranchAccept : onToast");
                        getMvpView().onError(R.string.text_toast_accept_save_failed);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "saveBranchAccept : onNext");
                        if(!aBoolean){
                            getMvpView().onError(R.string.text_toast_accept_save_failed);
                            return;
                        }
                        if(!isNetworkConnected){
                            getMvpView().onSuccess(R.string.toast_network_is_not_connect_save_local);
                            return;
                        }
                        //保存成功 上传所有验收未上传的操作
                        getMvpView().onUploadBranchAccept();
                    }
                }));
    }

    /**
     * 添加或更新支管验收操作
     * 添加多媒体
     * @param duBranchAccept
     * @param duMultiMedia
     */
    public void saveBranchAccept(DUBranchAccept duBranchAccept, DUMultiMedia duMultiMedia){
        mSubscription.add(mDataManager.saveBranchAccept(duBranchAccept, duMultiMedia)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Map<String, Long>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "saveBranchAccept : onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "saveBranchAccept : onError");
                        getMvpView().onToast(R.string.text_toast_accept_save_failed);
                    }

                    @Override
                    public void onNext(Map<String, Long> result) {
                        LogUtil.i(TAG, "saveBranchAccept : onNext");
                        if(result == null){
                            getMvpView().onToast(R.string.text_toast_accept_save_failed);
                            return;
                        }
                        getMvpView().onSaveAcceptSuccess(result);
                    }
                }));
    }

    /**
     * 手动删除多媒体数据
     * @param multiMedia
     */
    public void deleteMultiMedia(final DUMultiMedia multiMedia){
        mSubscription.add(mDataManager.deleteMultiMedia(multiMedia)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "deleteMultiMedia : onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "deleteMultiMedia : onError".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        LogUtil.i(TAG, "deleteMultiMedia : onNext");
                        getMvpView().onDeleteMultiMediaSuccess(multiMedia);
                    }
                }));

    }

    /**
     * 验收超时
     * 删除验收数据和多媒体数据
     */
    public void deleteBranchAccept(DUBranchAccept branchAccept, List<DUMultiMedia> multiMedias){
        mSubscription.add(mDataManager.deleteBranchAccept(branchAccept, multiMedias)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "deleteBranchAccept : onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "deleteBranchAccept : onError".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "deleteBranchAccept : onNext");
                        getMvpView().onDeleteAcceptSuccess();
                    }
                }));
    }

    /**
     * 获取当前用户工程预算下对应的验收类型的验收数据
     * @param upload
     * @param userName
     * @param projectId
     * @param budgetId
     * @param acceptType
     */
    public void getBranchAccept(int upload, String userName, long projectId, long budgetId, int acceptType){
        mSubscription.add(mDataManager.getBranchAccept(upload, userName, projectId, budgetId, acceptType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUBranchAccept>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getBranchAccept : onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getBranchAccept : onError".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(DUBranchAccept duBranchAccept) {
                        LogUtil.i(TAG, "getBranchAccept : onNext");
                        getMvpView().onInitAccept(duBranchAccept);
                    }
                }));
    }

    public void getMultiMedias(long relateId, int relateType){
        mSubscription.add(mDataManager.getMultiMedias(relateId, relateType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUMultiMedia>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getMultiMedias : onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getMultiMedias : onError".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(List<DUMultiMedia> duMultiMedias) {
                        LogUtil.i(TAG, "getMultiMedias : onNext");
                        getMvpView().onInitMultiMedias(duMultiMedias);
                    }
                }));
    }

    public void getAcceptTimeLimit(boolean isNetWorkConnect){
        if(!isNetWorkConnect){
            getMvpView().onLoadBranchAccept();
            return;
        }
        mSubscription.add(mDataManager.getAcceptTimeLimit()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getAcceptTimeLimit : onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getAcceptTimeLimit : onError".concat(e.getMessage()));
                        getMvpView().onLoadBranchAccept();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "getAcceptTimeLimit : onNext");
                        getMvpView().onLoadBranchAccept();
                    }
                }));
    }


}
