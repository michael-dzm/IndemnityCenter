package com.sh3h.indemnity.ui.accept;

import android.view.View;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.entity.DUOperate;
import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.ui.base.FunctionPresenter;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.localprovider.greendaoEntity.Project;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xulongjun on 2017/3/8.
 */

public class ProjectAcceptListPresenter extends FunctionPresenter<ProjectAcceptListMvpView> {
    public static final String TAG = ProjectAcceptListPresenter.class.getSimpleName();

    @Inject
    public ProjectAcceptListPresenter(DataManager dataManager, ConfigHelper configHelper) {
        super(dataManager, configHelper);
    }

    public void onItemClick(View view, DUProject item) {
        getMvpView().onIntent(item);
    }

    public void loadProjects(final int action, int userId, List<Integer> projectState, int index, int offset) {
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
                        if(duProjects != null){
                            loadProjectBudgets(duProjects);
                        }
                    }
                }));
    }

    public void loadProjects(final int action, String constructionTeam, List<Integer> projectState, int index, int offset) {
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
                        if(duProjects != null){
                            loadProjectBudgets(duProjects);
                        }
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

    public void loadProjectBudgets(List<DUProject> duProjects) {
        LogUtil.i(TAG, "loadLocalProjectBudgets");
        mSubscription.add(mDataManager.loadProjectBudgetCount(duProjects)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DUProject>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "loadLocalProjectBudgets:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "loadLocalProjectBudgets:onError".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(DUProject duProject) {
                        LogUtil.i(TAG, "loadLocalProjectBudgets:onNext");
                    }
                }));
    }

    public void onFinishProject(View view, DUProject duProject) {
        switch (view.getId()) {
            case R.id.btn_finish:
                getMvpView().onFinishProject(duProject);
                break;
        }
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
                        getMvpView().onToast(R.string.text_toast_finish_project_save_failed);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "saveOperate : onNext");
                        if(!aBoolean){
                            getMvpView().onToast(R.string.text_toast_finish_project_save_failed);
                            return;
                        }
                        if(!isNetworkConnected){
                            getMvpView().onSuccess(R.string.toast_network_is_not_connect_save_local);
                            return;
                        }
                        //保存成功 上传所有未上传的操作
                        getMvpView().onUploadOperates();
                    }
                }));
    }

    public boolean isEnabled(DUProject duProject) {
        //预算总数 == 工程验收合格数
        if (duProject.getBudgetTotalCount() != 0 && duProject.getProjectAcceptCount() == duProject.getBudgetTotalCount()) {
            return true;
        }
        return false;
    }


}
