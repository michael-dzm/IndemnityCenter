package com.sh3h.indemnity.ui.patrol.search;

import android.view.View;
import android.widget.AdapterView;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUPatrolSearchResult;
import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.dataprovider.data.entity.DUViolation;
import com.sh3h.dataprovider.data.entity.DUWord;
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

public class SearchPatrolPresenter extends ParentPresenter<SearchPatrolMvpView> {

    public static final String TAG = SearchPatrolPresenter.class.getSimpleName();

    @Inject
    public SearchPatrolPresenter(DataManager dataManager) {
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

    public void onItemClick(View view, DUPatrolSearchResult patrol){
        getMvpView().onItemClick(patrol);
    }

    /**
     * 日历点击事件
     */
    public void onCalendarClick(View view){
        getMvpView().onCalendarView(view.getId());
    }

    public void onSearchClick(View view){
        getMvpView().onSearch();
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
                        getMvpView().onToast(R.string.text_toast_violation_download_failed);
                    }

                    @Override
                    public void onNext(List<DUViolation> duViolations) {
                        LogUtil.i(TAG, "loadViolations:onNext");
                        //获取所有违规类型
                        getAllViolationTypes();
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
                        getMvpView().onToast(R.string.text_toast_violation_load_failed);
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
                        getMvpView().onToast(R.string.text_toast_violation_load_failed);
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
        mSubscription.add(mDataManager.getWords("2751")
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
                        getMvpView().onToast(R.string.text_toast_violation_load_failed);
                    }

                    @Override
                    public void onNext(List<DUWord> duWords) {
                        LogUtil.i(TAG, "getViolationResult:onNext");
                        getMvpView().onNotifyResultSpinner(duWords);
                    }
                }));
    }

    /**
     *
     * @param projectId
     * @param startDate
     * @param endDate
     * @param type 类型中文字符 all/施工安全
     * @param content 违规编号
     * @param result 语信息里面的value值
     */
    public void searchPatrols(long projectId, long startDate, long endDate, String type, String content, String result){
        mSubscription.add(mDataManager.searchPatrols(projectId, startDate, endDate, type, content, result)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUPatrolSearchResult>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "searchPatrols:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "searchPatrols:onError".concat(e.getMessage()));
                        getMvpView().onError(R.string.text_toast_project_patrol_search_failed);
                    }

                    @Override
                    public void onNext(List<DUPatrolSearchResult> results) {
                        LogUtil.i(TAG, "searchPatrols:onNext");
                        getMvpView().noNotifyDataChanged(results);
                    }
                }));

    }

}
