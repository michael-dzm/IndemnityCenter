package com.sh3h.indemnity.ui.project.list;

import android.databinding.ObservableField;
import android.view.View;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.ui.base.FunctionPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dengzhimin on 2017/3/8.
 */

public class ProjectListPresenter extends FunctionPresenter<ProjectListMvpView> {

    public static final String TAG = ProjectListPresenter.class.getSimpleName();

    @Inject
    public ProjectListPresenter(DataManager dataManager, ConfigHelper configHelper) {
        super(dataManager, configHelper);
    }

    public void onItemClick(View view, DUProject project){
        getMvpView().onItemClick(project);
    }

    public boolean visible(){
        return getMvpView().onVisible();
    }

    public void loadProjects(final int action, int userId, List<Integer> projectState, int index, int offset){
        LogUtil.i(TAG, "loadProjects");
        mSubscription.add(mDataManager.loadProjects(userId, projectState, index, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUProject>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "loadProjects:onCompleted");
                        getMvpView().onLoadFinish(action);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "loadProjects:onError".concat(e.getMessage()));
                        getMvpView().onError(action, R.string.text_toast_project_load_failed);
                    }

                    @Override
                    public void onNext(List<DUProject> duProjects) {
                        LogUtil.i(TAG, "loadProjects:onNext");
                        getMvpView().onNotify(action, duProjects);
                    }
                }));
    }

    public void loadProjects(int userId, int projectState){
        LogUtil.i(TAG, "loadProjects");
        mSubscription.add(mDataManager.loadProjects(userId, projectState)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUProject>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "loadProjects:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "loadProjects:onError".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(List<DUProject> duProjects) {
                        LogUtil.i(TAG, "loadProjects:onNext");
                    }
                }));
    }

    public void loadProjects(final int action, String constructionTeam, List<Integer> projectState, int index, int offset){
        LogUtil.i(TAG, "loadProjects");
        mSubscription.add(mDataManager.getProjects(constructionTeam, projectState, index, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUProject>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "loadProjects:onCompleted");
                        getMvpView().onLoadFinish(action);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "loadProjects:onError".concat(e.getMessage()));
                        getMvpView().onError(action, R.string.text_toast_project_load_failed);
                    }

                    @Override
                    public void onNext(List<DUProject> duProjects) {
                        LogUtil.i(TAG, "loadProjects:onNext");
                        getMvpView().onNotify(action, duProjects);
                    }
                }));
    }

}
