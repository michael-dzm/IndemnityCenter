package com.sh3h.indemnity.ui.material.search.apply;

import android.content.Intent;
import android.databinding.ObservableLong;
import android.view.View;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUMaterialApply;
import com.sh3h.indemnity.ui.base.ParentPresenter;
import com.sh3h.indemnity.ui.material.search.apply.info.MaterialApplyInformationActivity;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xulongjun on 2017/3/8.
 */

public class MaterialApplySearchPresenter extends ParentPresenter<MaterialApplySearchMvpView> {
    public static final String TAG = MaterialApplySearchPresenter.class.getSimpleName();

    public ObservableLong mStartTime = new ObservableLong();
    public ObservableLong mEndTime = new ObservableLong();

    @Inject
    public MaterialApplySearchPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void onItemClick(View view, DUMaterialApply item) {
        Intent intent = new Intent(view.getContext(), MaterialApplyInformationActivity.class);
        intent.putExtra(Constants.INTENT_PARAM_MATERIAL_APPLY, item);
        view.getContext().startActivity(intent);
    }

    /**
     * 日历点击事件
     */
    public void onCalendarClick(View view) {
        getMvpView().onCalendarView(view.getId());
    }

    public void onSearchClick(View view) {
        getMvpView().onSearch();
    }

    public void searchMaterialApply(int userId, long startTime, long endTime) {
        LogUtil.i(TAG, "searchMaterialApply");
        mSubscription.add(mDataManager.searchMaterialApply(userId, startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUMaterialApply>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "searchMaterialApply:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "searchMaterialApply:onError:".concat(e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUMaterialApply> duMaterialApplies) {
                        LogUtil.i(TAG, "searchMaterialApply:onNext");
                        getMvpView().onSearchMaterialApply(duMaterialApplies);
                    }
                }));
    }

}
