package com.sh3h.indemnity.ui.main;

import android.content.Intent;
import android.view.View;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.ui.accept.ProjectAcceptListActivity;
import com.sh3h.indemnity.ui.base.ParentPresenter;
import com.sh3h.indemnity.ui.material.address.AddressManagerActivity;
import com.sh3h.indemnity.ui.material.apply.MaterialApplyActivity;
import com.sh3h.indemnity.ui.material.confirm.MaterialConfirmActivity;
import com.sh3h.indemnity.ui.material.search.MaterialSearchActivity;
import com.sh3h.indemnity.ui.project.list.ProjectListActivity;
import com.sh3h.indemnity.ui.project.start.search.StartProjectSearchActivity;
import com.sh3h.indemnity.ui.upload.UploadManagerActivity;
import com.sh3h.indemnity.util.Constants;
import com.sh3h.mobileutil.util.LogUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dengzhimin on 2016/8/18.
 */
public class MainPresenter extends ParentPresenter<MainMvpView>{

    public static final String TAG = MainPresenter.class.getSimpleName();

    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void toStartProjectSearchActivity(View view){
        Intent intent = new Intent(view.getContext(), StartProjectSearchActivity.class);
        view.getContext().startActivity(intent);
    }

    public void toProjectListActivity(View view, String intentFlag){
        Intent intent = new Intent(view.getContext(), ProjectListActivity.class);
        intent.putExtra(Constants.INTENT_PARAM_FLAG, intentFlag);
        view.getContext().startActivity(intent);
    }

    public void toUploadManagerActivity(View view){
        Intent intent = new Intent(view.getContext(), UploadManagerActivity.class);
        view.getContext().startActivity(intent);
    }

    public void toAddressManagerActivity(View view){
        Intent intent = new Intent(view.getContext(), AddressManagerActivity.class);
        view.getContext().startActivity(intent);
    }

    public void toMaterialApplyActivity(View view){
        Intent intent = new Intent(view.getContext(), MaterialApplyActivity.class);
        view.getContext().startActivity(intent);
    }

    public void toMaterialSearchActivity(View view){
        Intent intent = new Intent(view.getContext(), MaterialSearchActivity.class);
        view.getContext().startActivity(intent);
    }

    public void toMaterialConfirmActivity(View view){
        Intent intent = new Intent(view.getContext(), MaterialConfirmActivity.class);
        view.getContext().startActivity(intent);
    }

    public void toProjectAcceptActivity(View view){
        Intent intent = new Intent(view.getContext(), ProjectAcceptListActivity.class);
        view.getContext().startActivity(intent);
    }

    public void loadWords(){
        mSubscription.add(mDataManager.loadWords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "loadWords:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "loadWords:onError:".concat(e.getMessage()));
                        getMvpView().onToast(R.string.text_toast_word_download_failed);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "loadWords:onNext");
                        if(aBoolean){
                            getMvpView().onToast(R.string.text_toast_word_download_success);
                        }else {
                            getMvpView().onToast(R.string.text_toast_word_download_failed);
                        }
                    }
                }));
    }

}
