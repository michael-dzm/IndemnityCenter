package com.sh3h.indemnity.ui.project.restart.entry;

import android.view.View;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.entity.DUOperate;
import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dengzhimin on 2017/3/8.
 */

public class RestartProjectEntryPresenter extends ParentPresenter<RestartProjectEntryMvpView> {

    public static final String TAG = RestartProjectEntryPresenter.class.getSimpleName();

    @Inject
    public RestartProjectEntryPresenter(DataManager dataManager) {
        super(dataManager);
    }

    /**
     * 拍照点击事件
     * @param view
     */
    public void onCameraClick(View view){
        getMvpView().onTakePhoto();
    }

    /**
     * 日历点击事件
     */
    public void onCalendarClick(View view){
        getMvpView().onCalendarView(view.getId());
    }

    /**
     * 更新工程
     * 添加工程操作
     * 添加多媒体
     * @param project
     * @param operate
     * @param duMultiMedias
     */
    public void saveOperate(DUProject project, DUOperate operate, List<DUMultiMedia> duMultiMedias, final boolean isNetworkConnected){
        mSubscription.add(mDataManager.saveOperate(project, operate, duMultiMedias)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "saveOperate : onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "saveOperate : onError");
                        getMvpView().onSaveError(R.string.text_toast_restart_project_save_failed);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "saveOperate : onNext");
                        if(!aBoolean){
                            getMvpView().onSaveError(R.string.text_toast_restart_project_save_failed);
                            return;
                        }
                        if(!isNetworkConnected){
                            getMvpView().onSaveSuccess(R.string.toast_network_is_not_connect_save_local);
                            return;
                        }
                        //保存成功 上传所有未上传的操作
                        getMvpView().onUploadOperates();
                    }
                }));
    }

}
