package com.sh3h.indemnity.ui.setting;

import android.view.View;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.indemnity.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dengzhimin on 2017/4/24.
 */

public class SettingPresenter extends ParentPresenter<SettingMvpView> {

    public static final String TAG = SettingPresenter.class.getSimpleName();

    @Inject
    public SettingPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void setNetWork(View view){
        getMvpView().onNetWorkSetting();
    }

    public void clearCache(View view){
        getMvpView().onClearCache();
    }

    public void resetRestfulApiService(){
        mDataManager.resetRestfulApiService();
    }

    public void timeLimit(View view){
        getMvpView().onTimeLimit();
    }

    public void calculateFileSize(int[] uploads){
        mSubscription.add(mDataManager.getMultiMediaNames(uploads)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "calculateFileSize:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "calculateFileSize:onError".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(List<String> mediaNames) {
                        LogUtil.i(TAG, "calculateFileSize:onNext");
                        getMvpView().onCalculateFileSize(mediaNames);
                    }
                }));
    }
}
