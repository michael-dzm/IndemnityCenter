package com.sh3h.indemnity.ui.accept.budget;

import android.view.View;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUBudget;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.ui.base.ParentPresenter;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xulongjun on 2017/3/8.
 */

public class BudgetAcceptListPresenter extends ParentPresenter<BudgetAcceptListMvpView> {
    public static final String TAG = BudgetAcceptListPresenter.class.getSimpleName();

    @Inject
    public BudgetAcceptListPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void onButtonClick(View view, DUBudget duBudget) {
        switch (view.getId()) {
            case R.id.btn_item_construction:
                getMvpView().onIntentByType(Constants.FLAG_ACCEPT_CONSTRUCTION, duBudget);
                break;
            case R.id.btn_item_strength:
                getMvpView().onIntentByType(Constants.FLAG_ACCEPT_STRENGTH,duBudget);
                break;
            case R.id.btn_item_airtight:
                getMvpView().onIntentByType(Constants.FLAG_ACCEPT_AIRTIGHT,duBudget);
                break;
            case R.id.btn_item_accept:
                getMvpView().onIntentByType(Constants.FLAG_ACCEPT_PROJECT,duBudget);
                break;
        }
    }

    public void loadProjectBudgets(boolean isNetWorkConnected, long projectId) {
        LogUtil.i(TAG, "loadProjectBudgets");
        Observable<List<DUBudget>> observable = null;
        if(isNetWorkConnected){
            observable = mDataManager.loadProjectBudgets(projectId);
        }else{
            observable = mDataManager.getProjectBudgets(projectId);
        }
        mSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUBudget>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "loadProjectBudgets:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "loadProjectBudgets:onError".concat(e.getMessage()));
                        getMvpView().onError(R.string.text_toast_budget_load_failed);
                    }

                    @Override
                    public void onNext(List<DUBudget> duBudgets) {
                        LogUtil.i(TAG, "loadProjectBudgets:onNext");
                        getMvpView().onNotify(duBudgets);
                    }
                }));
    }

    public void loadProjectBudgets(boolean isNetWorkConnected, long projectId, int state) {
        LogUtil.i(TAG, "loadProjectBudgets");
        Observable<List<DUBudget>> observable = null;
        if(isNetWorkConnected){
            observable = mDataManager.loadProjectBudgets(projectId, state);
        }else{
            observable = mDataManager.getProjectBudgets(projectId, state);
        }
        mSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUBudget>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "loadProjectBudgets:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "loadProjectBudgets:onError".concat(e.getMessage()));
                        getMvpView().onError(R.string.text_toast_budget_load_failed);
                    }

                    @Override
                    public void onNext(List<DUBudget> duBudgets) {
                        LogUtil.i(TAG, "loadProjectBudgets:onNext");
                        getMvpView().onNotify(duBudgets);
                    }
                }));
    }

    public String getBudgetStateName(int budgetState){
        String budgetStateName = null;
        switch (budgetState) {
            case 0:
                budgetStateName = "未验收";
                break;
            case 1:
                budgetStateName = "施工方案不合格";
                break;
            case 2:
                budgetStateName = "施工方案合格";
                break;
            case 3:
                budgetStateName = "强度不合格";
                break;
            case 4:
                budgetStateName = "强度合格";
                break;
            case 5:
                budgetStateName = "气密不合格";
                break;
            case 6:
                budgetStateName = "气密合格";
                break;
            case 7:
                budgetStateName = "验收不合格";
                break;
            case 8:
                budgetStateName = "验收合格";
                break;
        }
        return budgetStateName;
    }

}
