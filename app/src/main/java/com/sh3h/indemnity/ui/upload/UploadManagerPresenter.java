package com.sh3h.indemnity.ui.upload;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.view.View;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.entity.DUPatrol;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.indemnity.ui.base.FunctionPresenter;
import com.sh3h.indemnity.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dengzhimin on 2017/4/24.
 */

public class UploadManagerPresenter extends FunctionPresenter<UploadManagerMvpView> {

    public static final String TAG = UploadManagerPresenter.class.getSimpleName();

    /** 未上传数据数量 **/
    public ObservableInt operateCount = new ObservableInt();
    public ObservableInt dailyPatrolCount = new ObservableInt();
    public ObservableInt centerPatrolCount = new ObservableInt();
    public ObservableInt acceptCount = new ObservableInt();

    /** 补传多媒体数量 **/
    public ObservableInt operateMultiMediaCount = new ObservableInt();
    public ObservableInt dailyPatrolMultiMediaCount = new ObservableInt();
    public ObservableInt centerPatrolMultiMediaCount = new ObservableInt();
    public ObservableInt acceptMultiMediaCount = new ObservableInt();

    /** 数据上传状态 **/
    public final ObservableBoolean operateUploading = new ObservableBoolean();
    public final ObservableBoolean operateMultiMediaUploading = new ObservableBoolean();
    public final ObservableBoolean dailyPatrolUploading = new ObservableBoolean();
    public final ObservableBoolean dailyPatrolMultiMediaUploading = new ObservableBoolean();
    public final ObservableBoolean centerPatrolUploading = new ObservableBoolean();
    public final ObservableBoolean centerPatrolMultiMediaUploading = new ObservableBoolean();
    public final ObservableBoolean acceptUploading = new ObservableBoolean();
    public final ObservableBoolean acceptMultiMediaUploading = new ObservableBoolean();

    @Inject
    public UploadManagerPresenter(DataManager dataManager, ConfigHelper configHelper) {
        super(dataManager, configHelper);
    }

    public void uploadOperates(View view){
        getMvpView().onUploadOperates();
    }

    public void uploadOperateMultiMedias(View view){
        getMvpView().onUploadOperateMultiMedias();
    }

    public void uploadPatrols(View view, int type){
        getMvpView().onUploadPatrols(type);
    }

    public void uploadPatrolMultiMedias(View view, int type){
        getMvpView().onUploadPatrolMultiMedias(type);
    }

    public void uploadAccepts(View view){
        getMvpView().onUploadAccepts();
    }

    public void uploadAcceptMultiMedias(View view){
        getMvpView().onUploadAcceptMultiMedias();
    }

    public void getOperateCount(String userName, int upload){
        LogUtil.i(TAG, "getOperateCount");
        mSubscription.add(mDataManager.getOperateCount(userName, upload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getOperateCount:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getOperateCount:onError:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.i(TAG, "getOperateCount:onNext");
                        operateCount.set(integer);
                    }
                }));
    }

    public void getOperateMultiMediaCount(String userName, int operateUpload, int mediaUpload){
        List<Integer> types = new ArrayList<>();
        types.add(DUMultiMedia.RelateType.STOP_PROJECT.getValue());
        types.add(DUMultiMedia.RelateType.RESTART_PROJECT.getValue());
        types.add(DUMultiMedia.RelateType.START_PROJECT_CARD.getValue());
        types.add(DUMultiMedia.RelateType.START_PROJECT_ORDER.getValue());
        types.add(DUMultiMedia.RelateType.START_PROJECT_NAMEPLATE.getValue());
        types.add(DUMultiMedia.RelateType.START_PROJECT_FACILITY.getValue());
        types.add(DUMultiMedia.RelateType.START_PROJECT_OTHER.getValue());
        mSubscription.add(mDataManager.getOperateMultiMediaCount(userName, types, operateUpload, mediaUpload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getOperateMultiMediaCount:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getOperateMultiMediaCount:onError:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.i(TAG, "getOperateMultiMediaCount:onNext");
                        operateMultiMediaCount.set(integer);
                    }
                }));

    }

    public void getPatrolCount(String userName, final int type, int upload){
        LogUtil.i(TAG, "getDailyPatrolCount");
        mSubscription.add(mDataManager.getPatrolCount(userName, type, upload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getPatrolCount:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getPatrolCount:onError:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.i(TAG, "getPatrolCount:onNext");
                        if(type == DUPatrol.Type.DAILY_PATROL.getValue()){
                            dailyPatrolCount.set(integer);
                        }else if(type == DUPatrol.Type.INDEMNITY_CENTER_PATROL.getValue()){
                            centerPatrolCount.set(integer);
                        }
                    }
                }));
    }

    public void getPatrolMultiMediaCount(String userName, final int type, int patrolUpload, int mediaUpload){
        mSubscription.add(mDataManager.getPatrolMultiMediaCount(userName, type, patrolUpload, mediaUpload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getPatrolMultiMediaCount:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getPatrolMultiMediaCount:onError:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.i(TAG, "getPatrolMultiMediaCount:onNext");
                        if(type == DUPatrol.Type.DAILY_PATROL.getValue()){
                            dailyPatrolMultiMediaCount.set(integer);
                        }else if(type == DUPatrol.Type.INDEMNITY_CENTER_PATROL.getValue()){
                            centerPatrolMultiMediaCount.set(integer);
                        }
                    }
                }));

    }

    public void getAcceptCount(String userName, int upload){
        LogUtil.i(TAG, "getAcceptCount");
        mSubscription.add(mDataManager.getAcceptCount(userName, upload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getAcceptCount:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getAcceptCount:onError:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.i(TAG, "getAcceptCount:onNext");
                        acceptCount.set(integer);
                    }
                }));
    }

    public void getAcceptMultiMediaCount(String userName, int acceptUpload, int mediaUpload){
        mSubscription.add(mDataManager.getAcceptMultiMediaCount(userName, acceptUpload, mediaUpload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getAcceptMultiMediaCount:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getAcceptMultiMediaCount:onError:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.i(TAG, "getAcceptMultiMediaCount:onNext");
                        acceptMultiMediaCount.set(integer);
                    }
                }));
    }

}
