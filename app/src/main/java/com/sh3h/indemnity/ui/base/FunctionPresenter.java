package com.sh3h.indemnity.ui.base;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.indemnity.R;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dengzhimin on 2017/5/12.
 */

public class FunctionPresenter<T extends FunctionMvpView> extends ParentPresenter<T> {

    protected final ConfigHelper mConfigHelper;

    public FunctionPresenter(DataManager dataManager, ConfigHelper configHelper) {
        super(dataManager);
        mConfigHelper = configHelper;
    }

    public void compareUri(String hostUri){
        if(TextUtil.isNullOrEmpty(hostUri)){
            getMvpView().onExit(R.string.text_toast_network_address_error);
            return;
        }
        if(mConfigHelper.getBaseUri().equals(hostUri)){
            getMvpView().onCompareUser();
            return;
        }
        if(mConfigHelper.saveNetWorkSetting(hostUri)){
            getMvpView().onCompareUser();
        }else{
            getMvpView().onExit(R.string.text_toast_network_address_error);
        }
    }

    public void getUserInfo(int userId){
        mSubscription.add(mDataManager.getUserInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "getUserInfo : onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "getUserInfo : onToast".concat(e.getMessage()));
                        getMvpView().onExit(R.string.text_toast_user_info_load_error);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "getUserInfo : onNext");
                        getMvpView().onLoadData();
                    }
                }));
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
                        LogUtil.i(TAG, "loadWords:onToast:".concat(e.getMessage()));
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "loadWords:onNext");
                    }
                }));
    }
}
