package com.sh3h.indemnity.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.entity.DUPatrol;
import com.sh3h.dataprovider.data.entity.enums.Upload;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.util.EventPosterHelper;
import com.sh3h.indemnity.MainApplication;
import com.sh3h.indemnity.event.BusEvent;
import com.sh3h.indemnity.service.action.SyncAction;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.mobileutil.util.LogUtil;
import com.squareup.otto.Bus;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by dengzhimin on 2017/4/26.
 */

public class SyncService extends IntentService {

    public static final String TAG = SyncService.class.getSimpleName();

    @Inject
    DataManager mDataManager;

    @Inject
    Bus mBus;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @Inject
    PreferencesHelper mPreferencesHelper;

    public SyncService() {
        super(SyncService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG, "onCreate");
        MainApplication.get(this).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtil.i(TAG, "onHandleIntent");
        if (intent == null) {
            return;
        }
        SyncAction action = SyncAction.values()[intent.getIntExtra(Constants.INTENT_PARAM_FLAG, -1)];
        switch (action) {
            case CHECK_NO_UPLOAD_DATA:
                break;
            case UPLOAD_ALL_OPERATE:
                uploadOperates(Upload.DEFAULT.getValue(), mPreferencesHelper.getUserSession().getUserId(),
                        mPreferencesHelper.getUserSession().getUserName());
                break;
            case UPLOAD_ALL_OPERATE_MULTIMEDIA:
                uploadOperateMultiMedias(mPreferencesHelper.getUserSession().getUserName(), Upload.UPLOADED.getValue(), Upload.DEFAULT.getValue());
                break;
            case UPLOAD_ALL_PATROL:
                uploadPatrols(intent.getIntExtra(Constants.INTENT_PARAM_PATROL_TYPE, 0), Upload.DEFAULT.getValue(),
                        mPreferencesHelper.getUserSession().getUserId(), mPreferencesHelper.getUserSession().getUserName());
                break;
            case UPLOAD_ALL_PATROL_MULTIMEDIA:
                uploadPatrolMultiMedias(mPreferencesHelper.getUserSession().getUserName(), intent.getIntExtra(Constants.INTENT_PARAM_PATROL_TYPE, 0),
                        Upload.UPLOADED.getValue(), Upload.DEFAULT.getValue());
                break;
            case UPLOAD_ALL_ACCEPT:
                uploadAccept(Upload.DEFAULT.getValue(),
                        mPreferencesHelper.getUserSession().getUserId(), mPreferencesHelper.getUserSession().getUserName());
                break;
            case UPLOAD_ALL_ACCEPT_MULTIMEDIA:
                uploadAcceptMultiMedias(mPreferencesHelper.getUserSession().getUserName(), Upload.UPLOADED.getValue(), Upload.DEFAULT.getValue());
                break;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i(TAG, "onBind");
        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.i(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        LogUtil.i(TAG, "onDestroy");
        super.onDestroy();
    }

    /**
     * 上传工程操作数据
     *
     * @param upload
     * @param userId
     * @param userName
     */
    public void uploadOperates(int upload, int userId, final String userName) {
        LogUtil.i(TAG, "uploadOperates");
        mDataManager.uploadOperates(userName, upload, userId)
                .subscribe(new Subscriber<List<Map<String, Long>>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "uploadOperates:onCompleted");
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "uploadOperates:onError:".concat(e.getMessage()));
                        mEventPosterHelper.postEventSafely(new BusEvent.UploadOperate(false));
                    }

                    @Override
                    public void onNext(List<Map<String, Long>> ids) {
                        LogUtil.i(TAG, "uploadOperates:onNext");
                        mEventPosterHelper.postEventSafely(new BusEvent.UploadOperate(true));
                        uploadOperateMultiMedias(userName, Upload.UPLOADED.getValue(), Upload.DEFAULT.getValue());
                    }
                });
    }

    /**
     * 上传工程巡视数据
     *
     * @param type
     * @param upload
     * @param userId
     */
    public void uploadPatrols(final int type, int upload, int userId, final String userName) {
        LogUtil.i(TAG, "uploadPatrols");
        mDataManager.uploadPatrols(type, upload, userId, userName)
                .subscribe(new Subscriber<List<Map<String, Object>>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "uploadPatrols:onCompleted");
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "uploadPatrols:onError:".concat(e.getMessage()));
                        mEventPosterHelper.postEventSafely(new BusEvent.UploadPatrol(type, false));
                    }

                    @Override
                    public void onNext(List<Map<String, Object>> idTypes) {
                        LogUtil.i(TAG, "uploadPatrols:onNext");
                        mEventPosterHelper.postEventSafely(new BusEvent.UploadPatrol(type, true));
                        uploadPatrolMultiMedias(userName, type, Upload.UPLOADED.getValue(), Upload.DEFAULT.getValue());
                    }
                });
    }

    /**
     * 上传验收数据
     *
     * @param upload
     * @param userId
     * @param userName
     */
    public void uploadAccept(int upload, int userId, final String userName) {
        LogUtil.i(TAG, "uploadBranchAccept");
        mDataManager.uploadAccepts(upload, userId, userName)
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "uploadProjectAccept:onCompleted");
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "uploadBranchAccept:onError:".concat(e.getMessage()));
                        mEventPosterHelper.postEventSafely(new BusEvent.UploadAccept(false));
                    }

                    @Override
                    public void onNext(Object o) {
                        LogUtil.i(TAG, "uploadBranchAccept:onNext");
                        mEventPosterHelper.postEventSafely(new BusEvent.UploadAccept(true));
                        uploadAcceptMultiMedias(userName, Upload.UPLOADED.getValue(), Upload.DEFAULT.getValue());
                    }
                });
    }

    /**
     * 上传工程操作多媒体
     * @param userName
     * @param operateUpload 操作上传状态
     * @param mediaUpload 多媒体上传状态
     */
    public void uploadOperateMultiMedias(String userName, int operateUpload, final int mediaUpload){
        LogUtil.i(TAG, "uploadOperateMultiMedias");
        mDataManager.getOperateIds(userName, operateUpload)
                .subscribe(new Subscriber<List<Map<String, Long>>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getOperateIds:onCompleted");
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getOperateIds:onError:".concat(e.getMessage()));
                        mEventPosterHelper.postEventSafely(new BusEvent.UploadMultiMedia(false));
                    }

                    @Override
                    public void onNext(List<Map<String, Long>> ids) {
                        LogUtil.i(TAG, "getOperateIds:onNext");
                        List<Integer> types = new ArrayList<>();
                        types.add(DUMultiMedia.RelateType.STOP_PROJECT.getValue());
                        types.add(DUMultiMedia.RelateType.RESTART_PROJECT.getValue());
                        types.add(DUMultiMedia.RelateType.START_PROJECT_CARD.getValue());
                        types.add(DUMultiMedia.RelateType.START_PROJECT_ORDER.getValue());
                        types.add(DUMultiMedia.RelateType.START_PROJECT_NAMEPLATE.getValue());
                        types.add(DUMultiMedia.RelateType.START_PROJECT_FACILITY.getValue());
                        types.add(DUMultiMedia.RelateType.START_PROJECT_OTHER.getValue());
                        uploadMultiMedias(ids, types, mediaUpload);
                    }
                });
    }

    /**
     * 上传巡视多媒体
     * @param userName
     * @param type 巡视类型
     * @param patrolUpload 巡视上传状态
     * @param mediaUpload 多媒体上传状态
     */
    public void uploadPatrolMultiMedias(String userName, int type, int patrolUpload, final int mediaUpload){
        LogUtil.i(TAG, "uploadPatrolMultiMedias");
        mDataManager.getPatrolIds(userName, type, patrolUpload)
                .subscribe(new Subscriber<List<Map<String, Object>>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getPatrolIds:onCompleted");
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getPatrolIds:onError:".concat(e.getMessage()));
                        mEventPosterHelper.postEventSafely(new BusEvent.UploadMultiMedia(false));
                    }

                    @Override
                    public void onNext(List<Map<String, Object>> idTypes) {
                        LogUtil.i(TAG, "getPatrolIds:onNext");
                        uploadMultiMedias(idTypes, mediaUpload);
                    }
                });
    }

    /**
     * 上传验收多媒体
     * @param userName
     * @param acceptUpload 验收上传状态
     * @param mediaUpload 多媒体上传状态
     */
    public void uploadAcceptMultiMedias(String userName, int acceptUpload, final int mediaUpload){
        LogUtil.i(TAG, "uploadAcceptMultiMedias");
        mDataManager.getAcceptIds(userName, acceptUpload)
                .subscribe(new Subscriber<List<Map<String, Object>>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getAcceptIds:onCompleted");
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getAcceptIds:onError:".concat(e.getMessage()));
                        mEventPosterHelper.postEventSafely(new BusEvent.UploadMultiMedia(false));
                    }

                    @Override
                    public void onNext(List<Map<String, Object>> idTypes) {
                        LogUtil.i(TAG, "getAcceptIds:onNext");
                        uploadMultiMedias(idTypes, mediaUpload);
                    }
                });
    }

    /**
     * 上传多媒体
     * @param idTypes
     * @param upload
     */
    private void uploadMultiMedias(final List<Map<String, Object>> idTypes, int upload) {
        LogUtil.i(TAG, "uploadMultiMedias");
        mDataManager.uploadMultiMedias(idTypes, upload)
                .subscribe(new Subscriber<HashMap<String, Integer>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "uploadMultiMedias:onCompleted");
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "uploadMultiMedias:onError:".concat(e.getMessage()));
                        mEventPosterHelper.postEventSafely(new BusEvent.UploadMultiMedia(false));
                    }

                    @Override
                    public void onNext(HashMap<String, Integer> result) {
                        LogUtil.i(TAG, "uploadMultiMedias:onNext");
                        mEventPosterHelper.postEventSafely(new BusEvent.UploadMultiMedia(true, result.get("successCount"), result.get("failedCount")));
                    }
                });
    }

    /**
     * 上传多媒体
     * @param ids
     * @param types
     * @param upload
     */
    private void uploadMultiMedias(final List<Map<String, Long>> ids, List<Integer> types, int upload) {
        LogUtil.i(TAG, "uploadMultiMedias");
        mDataManager.uploadMultiMedias(ids, types, upload)
                .subscribe(new Subscriber<HashMap<String, Integer>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "uploadMultiMedias:onCompleted");
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "uploadMultiMedias:onError:".concat(e.getMessage()));
                        mEventPosterHelper.postEventSafely(new BusEvent.UploadMultiMedia(false));
                    }

                    @Override
                    public void onNext(HashMap<String, Integer> result) {
                        LogUtil.i(TAG, "uploadMultiMedias:onNext");
                        mEventPosterHelper.postEventSafely(new BusEvent.UploadMultiMedia(true, result.get("successCount"), result.get("failedCount")));
                    }
                });
    }

}
