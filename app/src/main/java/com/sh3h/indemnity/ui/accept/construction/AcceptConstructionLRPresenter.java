package com.sh3h.indemnity.ui.accept.construction;

import android.view.View;
import android.widget.AdapterView;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUBudget;
import com.sh3h.dataprovider.data.entity.DUConstructionAccept;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
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
 * Created by dengzhimin on 2017/7/10.
 */

public class AcceptConstructionLRPresenter extends ParentPresenter<AcceptConstructionLRMvpView> {

    @Inject
    public AcceptConstructionLRPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        getMvpView().onItemSelected(parent, view, position, id);
    }

    public void onCameraClick(View view){
        getMvpView().onTakePhoto();
    }

    public void getConstructionProgram(String groups) {
        LogUtil.i(TAG, "getConstructionProgram");
        mSubscription.add(mDataManager.getWords(groups)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUWord>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getConstructionProgram:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getConstructionProgram:onError:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(List<DUWord> duWords) {
                        LogUtil.i(TAG, "getConstructionProgram:onNext");
                        getMvpView().onNotifySpinner(R.id.sp_construction_program, duWords);
                    }
                }));
    }

    public void getConstructionChange(String groups) {
        LogUtil.i(TAG, "getConstructionChange");
        mSubscription.add(mDataManager.getWords(groups)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUWord>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getConstructionChange:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getConstructionChange:onError:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(List<DUWord> duWords) {
                        LogUtil.i(TAG, "getConstructionChange:onNext");
                        getMvpView().onNotifySpinner(R.id.sp_construction_change, duWords);
                    }
                }));
    }

    public void saveConstructionAccept(DUBudget duBudget, DUConstructionAccept duAccept, List<DUMultiMedia> multiMedias, final boolean isNetWorkConnected){
        mSubscription.add(mDataManager.saveConstructionAccept(duBudget, duAccept, multiMedias)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "saveConstructionAccept:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "saveConstructionAccept:onError".concat(e.getMessage()));
                        getMvpView().onSaveError(R.string.text_toast_accept_save_failed);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "saveConstructionAccept:onNext");
                        if (!aBoolean) {
                            getMvpView().onSaveError(R.string.text_toast_accept_save_failed);
                            return;
                        }
                        if (!isNetWorkConnected) {
                            getMvpView().onSaveSuccess(R.string.toast_network_is_not_connect_save_local);
                            return;
                        }
                        //保存成功 上传所有验收未上传的操作
                        getMvpView().onUploadConstructionAccept();
                    }
                }));
    }
}
