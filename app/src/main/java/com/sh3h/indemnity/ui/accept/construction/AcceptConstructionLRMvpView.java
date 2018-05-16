package com.sh3h.indemnity.ui.accept.construction;

import android.view.View;
import android.widget.AdapterView;

import com.sh3h.dataprovider.data.entity.DUWord;
import com.sh3h.indemnity.ui.base.MvpView;

import java.util.List;

/**
 * Created by dengzhimin on 2017/7/10.
 */

public interface AcceptConstructionLRMvpView extends MvpView {

    void onNotifySpinner(int viewId, List<DUWord> words);

    void onItemSelected(AdapterView<?> parent, View view, int position, long id);

    void onTakePhoto();

    void onToast(int resId);

    void onToast(String msg);

    void onUploadConstructionAccept();

    void onSaveSuccess(int resId);

    void onSaveError(int resId);
}
