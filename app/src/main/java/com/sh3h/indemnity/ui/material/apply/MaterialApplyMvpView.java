package com.sh3h.indemnity.ui.material.apply;

import android.view.View;
import android.widget.AdapterView;

import com.sh3h.dataprovider.data.entity.DUMaterial;
import com.sh3h.indemnity.ui.base.FunctionMvpView;
import com.sh3h.indemnity.ui.base.MvpView;

import java.util.List;

/**
 * Created by xulongjun on 2017/3/8.
 */

public interface MaterialApplyMvpView extends FunctionMvpView{

    void onToast(int resId);

    void onToast(String message);

    void onLoadError(int resId);

    void onLoadSuccess();

    void onUploadError(int resId);

    void onUploadError(String message);

    void onUploadSuccess(int resId);

    void toAddressManagerActivity();

    void onItemSelected(AdapterView<?> parent, View view, int position, long id);

    void onNotifySpinner(int spinnerId, List<DUMaterial> duMaterials);

    void onAddress(String addressContent);

}
