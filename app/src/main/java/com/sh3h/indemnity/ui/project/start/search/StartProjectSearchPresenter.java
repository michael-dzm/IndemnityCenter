package com.sh3h.indemnity.ui.project.start.search;

import android.view.View;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.ui.base.FunctionPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dengzhimin on 2017/3/8.
 */

public class StartProjectSearchPresenter extends FunctionPresenter<StartProjectSearchMvpView> {

    public static final String TAG = StartProjectSearchPresenter.class.getName();

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public StartProjectSearchPresenter(DataManager dataManager, ConfigHelper configHelper, PreferencesHelper preferencesHelper) {
        super(dataManager, configHelper);
        mPreferencesHelper = preferencesHelper;
    }

    public void searchProject(View view, String projectNumber){
        getMvpView().onSearchProject(projectNumber);
    }

    public void searchProject(boolean isNetWorkConnected, String projectNumber){
        Observable<DUProject> observable;
        if(isNetWorkConnected){
            observable = mDataManager.loadProject(mPreferencesHelper.getUserSession().getUserId(), projectNumber);
        }else{
            observable = mDataManager.queryProjectByNumber(mPreferencesHelper.getUserSession().getConstructionTeam(), projectNumber);
        }
        mSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUProject>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "queryProject : onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "queryProject : onError".concat(e.getMessage()));
                        getMvpView().onError(R.string.text_toast_error);
                    }

                    @Override
                    public void onNext(DUProject project) {
                        LogUtil.i(TAG, "queryProject : onNext");
                        if(project == null){
                            getMvpView().onError(R.string.text_toast_project_number_not_exist);
                            return;
                        }
                        if(project.getState() > DUProject.State.DEFAULT.getValue()){
                            getMvpView().onError(R.string.text_toast_project_started);
                            return;
                        }
                        getMvpView().onSuccess(project);
                    }
                }));
    }

    /**
     * 扫描二维码
     * @param view
     */
    public void scanCode(View view){
        getMvpView().onScanCode();
    }
}
