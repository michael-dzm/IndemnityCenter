package com.sh3h.indemnity.ui.material.search.confirm;

import android.content.Intent;
import android.databinding.ObservableLong;
import android.view.View;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUMaterialVerify;
import com.sh3h.indemnity.ui.base.ParentPresenter;
import com.sh3h.indemnity.ui.material.search.confirm.info.MaterialConfirmInformationActivity;
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

public class MaterialConfirmSearchPresenter extends ParentPresenter<MaterialConfirmSearchMvpView> {
    public static final String TAG = MaterialConfirmSearchPresenter.class.getSimpleName();

    public ObservableLong mStartTime = new ObservableLong();
    public ObservableLong mEndTime = new ObservableLong();

    @Inject
    public MaterialConfirmSearchPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void onItemClick(View view, DUMaterialVerify item){
        Intent intent = new Intent(view.getContext(),MaterialConfirmInformationActivity.class);
        intent.putExtra(Constants.INTENT_PARAM_MATERIAL_VERIFY, item);
        view.getContext().startActivity(intent);
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

    public void searchMaterialConfirm(int userId, long startTime, long endTime) {
        LogUtil.i(TAG, "searchMaterialConfirm");
        mSubscription.add(mDataManager.searchMaterialConfirm(userId, startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUMaterialVerify>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "searchMaterialConfirm:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "searchMaterialConfirm:onError:".concat(e.getMessage()));
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUMaterialVerify> duMaterialVerifies) {
                        LogUtil.i(TAG, "searchMaterialConfirm:onNext");
                        getMvpView().onSearchMaterialVerify(duMaterialVerifies);
                    }
                }));
    }
}
