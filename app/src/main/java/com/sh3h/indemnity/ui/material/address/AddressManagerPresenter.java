package com.sh3h.indemnity.ui.material.address;

import android.view.View;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.entity.DUAddress;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.indemnity.R;
import com.sh3h.indemnity.ui.base.FunctionPresenter;
import com.sh3h.indemnity.ui.material.apply.MaterialApplyPresenter;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dengzhimin on 2017/3/31.
 */

public class AddressManagerPresenter extends FunctionPresenter<AddressManagerMvpView> {
    public static final String TAG = MaterialApplyPresenter.class.getSimpleName();

    @Inject
    public AddressManagerPresenter(DataManager dataManager, ConfigHelper configHelper) {
        super(dataManager, configHelper);
    }

    public void showAddAddressDialog(View view) {
        getMvpView().showAddAddressDialog();
    }

    public void showDeleteAddressDialog(View view, DUAddress duAddress) {
        getMvpView().showDeleteAddressDialog(duAddress);
    }

    public void showUpdateAddressDialog(View view, DUAddress duAddress) {
        getMvpView().showUpdateAddressDialog(duAddress);
    }

    public void onItemClick(View view, DUAddress duAddress){
        getMvpView().onItemClick(duAddress);
    }

    //changeDefaultAddress
    public void changeDefaultAddress(View view, DUAddress duAddress) {
        getMvpView().changeDefaultAddress(view, duAddress);
    }

    /**
     * 新增地址
     * @param duAddress
     * @param userId
     */
    public void addAddress(final boolean isNetWorkConnected, DUAddress duAddress, final int userId) {
        LogUtil.i(TAG, "addAddress");
        Observable<Boolean> observable = null;
        if(isNetWorkConnected){
            observable = mDataManager.uploadAddress(duAddress, userId);
        }else{
            observable = mDataManager.saveAddress(duAddress);
        }
        mSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "addAddress:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "addAddress:onError:".concat(e.getMessage()));
                        getMvpView().onToast(R.string.text_address_add_fail);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "addAddress:onNext");
                        if (!aBoolean) {
                            getMvpView().onToast(R.string.text_address_add_fail);
                            return;
                        }
                        //添加完成 重新加载地址
                        loadAddresses(isNetWorkConnected, userId);
                    }
                }));
    }

    /**
     * 更新地址
     * @param isNetWorkConnected
     * @param duAddress
     */
    public void updateAddress(final boolean isNetWorkConnected, final DUAddress duAddress) {
        LogUtil.i(TAG, "updateDUAddress");
        Observable<Boolean> observable = null;
        if(isNetWorkConnected){
            observable = mDataManager.updateAddress(duAddress);
        }else{
            //修改地址仅支持在线操作
            getMvpView().onToast(R.string.text_address_modify_only_online);
            return;
        }
        mSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "updateDUAddress:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "updateDUAddress:onError:".concat(e.getMessage()));
                        getMvpView().onToast(R.string.text_address_modify_fail);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "updateDUAddress:onNext");
                        if (!aBoolean) {
                            getMvpView().onToast(R.string.text_address_modify_fail);
                            return;
                        }
                        //修改完成 重新加载
                        loadAddresses(isNetWorkConnected, duAddress.getUserId().intValue());
                    }
                }));
    }

    /**
     * 加载地址
     * @param isNetWorkConnected
     * @param userId
     */
    public void loadAddresses(boolean isNetWorkConnected, final int userId) {
        LogUtil.i(TAG, "loadAddresses");
        Observable<List<DUAddress>> observable = null;
        if(isNetWorkConnected){
            observable = mDataManager.loadAddresses(userId);
        }else{
            observable = mDataManager.getAddresses(userId);
        }
        mSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DUAddress>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "loadAddresses:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "loadAddresses:onError:".concat(e.getMessage()));
                        getMvpView().onError(R.string.text_toast_address_load_failed);
                    }

                    @Override
                    public void onNext(List<DUAddress> duAddresses) {
                        LogUtil.i(TAG, "loadAddresses:onNext");
                        getMvpView().onNotify(duAddresses);
                    }
                }));
    }

    /**
     * 删除地址
     * @param isNetWorkConnected
     * @param duAddress
     */
    public void deleteAddress(final boolean isNetWorkConnected, final DUAddress duAddress){
        LogUtil.i(TAG, "deleteAddress");
        Observable<Boolean> observable = null;
        if(isNetWorkConnected){
            observable = mDataManager.deleteAddress(duAddress);
        }else{
            //删除地址仅支持在线操作
            getMvpView().onToast(R.string.text_address_delete_only_online);
            return;
        }
        mSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "deleteAddress:onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "deleteAddress:onError:".concat(e.getMessage()));
                        getMvpView().onToast(R.string.text_address_delete_fail);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        LogUtil.i(TAG, "deleteAddress:onNext");
                        if (!aBoolean) {
                            getMvpView().onToast(R.string.text_address_delete_fail);
                            return;
                        }
                        //删除完成 重新加载地址
                        loadAddresses(isNetWorkConnected, duAddress.getUserId().intValue());
                    }
                }));
    }

}
