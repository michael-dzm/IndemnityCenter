package com.sh3h.indemnity.ui.accept.project;

import android.databinding.ObservableArrayList;
import android.view.View;
import android.widget.AdapterView;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUBudget;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.entity.DUProjectAccept;
import com.sh3h.dataprovider.data.entity.DUWord;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xulongjun on 2017/3/13.
 */

public class AcceptProjectLRPresenter extends ParentPresenter<AcceptProjectLRMvpView> {
    public static final String TAG = AcceptProjectLRPresenter.class.getSimpleName();

    public ObservableArrayList<String> mAcceptResult = new ObservableArrayList<>();
    public List<DUWord> mAcceptResultWords = new ArrayList<>();

    @Inject
    public AcceptProjectLRPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void onCameraClick(View view) {
        getMvpView().onTakePhoto();
    }

    /**
     * 日历点击事件
     */
    public void onCalendarClick(View view) {
        getMvpView().onCalendarView(view.getId());
    }

    /**
     * Spinner点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        getMvpView().onItemSelected(parent, view, position, id);
    }


    public void getAcceptResultData(String group) {
        LogUtil.i(TAG, "getAcceptResultData");
        mSubscription.add(mDataManager.getWords(group)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUWord>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getAcceptResultData:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getAcceptResultData:onError:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(List<DUWord> duWords) {
                        LogUtil.i(TAG, "getAcceptResultData:onNext");
                        if (duWords == null || duWords.size() <= 0) {
                            return;
                        }
                        mAcceptResultWords = duWords;
                        for (DUWord duWord : duWords) {
                            mAcceptResult.add(duWord.getName());
                        }
                        getMvpView().onGetAcceptResultData(duWords);
                    }
                }));
    }

    /**
     * 更新工程预算信息
     * 添加工程验收操作
     * 添加多媒体
     *
     * @param duBudget
     * @param duProjectAccept
     * @param duMultiMedias
     */
    public void saveProjectAccept(DUBudget duBudget, DUProjectAccept duProjectAccept,
                                  List<DUMultiMedia> duMultiMedias, final boolean isNetworkConnected) {
        mSubscription.add(mDataManager.saveProjectAccept(duBudget, duProjectAccept, duMultiMedias)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "saveProjectAccept : onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "saveProjectAccept : onError");
                        getMvpView().onSaveError(R.string.text_toast_accept_save_failed);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "saveProjectAccept : onNext");
                        if (!aBoolean) {
                            getMvpView().onSaveError(R.string.text_toast_accept_save_failed);
                            return;
                        }
                        if (!isNetworkConnected) {
                            getMvpView().onSaveSuccess(R.string.toast_network_is_not_connect_save_local);
                            return;
                        }
                        //保存成功 上传所有验收未上传的操作
                        getMvpView().onUploadProjectAccept();
                    }
                }));
    }


}
