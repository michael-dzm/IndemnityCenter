package com.sh3h.indemnity.ui.patrol.entry;

import android.view.View;
import android.widget.AdapterView;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.entity.DUPatrol;
import com.sh3h.dataprovider.data.entity.DUViolation;
import com.sh3h.dataprovider.data.entity.DUWord;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.ui.base.ParentPresenter;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dengzhimin on 2017/3/8.
 */

public class PatrolEntryPresenter extends ParentPresenter<PatrolEntryMvpView> {

    public static final String TAG = PatrolEntryPresenter.class.getSimpleName();

    @Inject
    public PatrolEntryPresenter(DataManager dataManager) {
        super(dataManager);
    }

    /**
     * Spinner点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        getMvpView().onItemSelected(parent, view, position, id);
    }

    /**
     * 拍照点击事件
     * @param view
     */
    public void onCameraClick(View view){
        getMvpView().onTakePhoto();
    }

    /**
     * 下载违规配置信息
     */
    public void loadViolations(){
        LogUtil.i(TAG, "loadViolations");
        mSubscription.add(mDataManager.loadViolationWords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUViolation>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "loadViolations:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "loadViolations:onError:".concat(e.getMessage()));
                        getMvpView().onLoadError(R.string.text_toast_violation_download_failed);
                    }

                    @Override
                    public void onNext(List<DUViolation> duViolations) {
                        LogUtil.i(TAG, "loadViolations:onNext");
                        getMvpView().onLoadSuccess();
                    }
                }));
    }

    /**
     * 获取所有违规类型
     */
    public void getAllViolationTypes(){
        LogUtil.i(TAG, "getViolationTypes");
        mSubscription.add(mDataManager.getAllViolationTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUViolation>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getViolationTypes:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getViolationTypes:onError:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(List<DUViolation> violations) {
                        LogUtil.i(TAG, "getViolationTypes:onNext");
                        getMvpView().onNotifyTypeSpinner(violations);
                    }
                }));
    }

    public void getViolationByType(String violationType){
        LogUtil.i(TAG, "getViolationTypes");
        mSubscription.add(mDataManager.getViolationByType(violationType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUViolation>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getViolationTypes:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getViolationTypes:onError:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(List<DUViolation> violations) {
                        LogUtil.i(TAG, "getViolationTypes:onNext");
                        getMvpView().onNotifyContentSpinner(violations);
                        //获取违规结果
                        getViolationResult();
                    }
                }));
    }

    public void getViolationResult(){
        LogUtil.i(TAG, "getViolationResult");
        mSubscription.add(mDataManager.getWords(Constants.WORDS_TYPE_VIOLATION_RESULT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUWord>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getViolationResult:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getViolationResult:onError:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(List<DUWord> duWords) {
                        LogUtil.i(TAG, "getViolationResult:onNext");
                        getMvpView().onNotifyResultSpinner(duWords);
                    }
                }));
    }

    public void savePatrol(DUPatrol duPatrol, List<DUMultiMedia> medias, final boolean isNetworkConnected){
        LogUtil.i(TAG, "savePatrol");
        mSubscription.add(mDataManager.savePatrol(duPatrol, medias)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "savePatrol:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "savePatrol:onError:".concat(e.getMessage()));
                        getMvpView().onSaveError(R.string.text_toast_patrol_save_failed);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "savePatrol:onNext");
                        if(!aBoolean){
                            getMvpView().onSaveError(R.string.text_toast_patrol_save_failed);
                            return;
                        }
                        if(!isNetworkConnected){
                            getMvpView().onSaveSuccess(R.string.toast_network_is_not_connect_save_local);
                            return;
                        }
                        //保存成功 上传所有未上传的操作
                        getMvpView().onUploadPatrols();
                    }
                }));
    }

}
