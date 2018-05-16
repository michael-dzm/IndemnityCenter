package com.sh3h.indemnity.ui.material.address;

import android.view.View;

import com.sh3h.dataprovider.data.entity.DUAddress;
import com.sh3h.indemnity.ui.base.FunctionMvpView;

import java.util.List;

/**
 * Created by xulongjun on 2017/3/9.
 */

public interface AddressManagerMvpView extends FunctionMvpView{

    void showAddAddressDialog();

    void showDeleteAddressDialog(DUAddress duAddress);

    void showUpdateAddressDialog(DUAddress duAddress);

    void changeDefaultAddress(View view, DUAddress duAddress);

    void onNotify(List<DUAddress> duAddresses);

    void onToast(int resId);

    void onError(int resId);

    void onItemClick(DUAddress duAddress);
}
