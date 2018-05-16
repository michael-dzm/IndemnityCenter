package com.sh3h.indemnity.ui.patrol.entry;

import android.view.View;
import android.widget.AdapterView;

import com.sh3h.dataprovider.data.entity.DUViolation;
import com.sh3h.dataprovider.data.entity.DUWord;
import com.sh3h.indemnity.ui.base.MvpView;

import java.util.List;

/**
 * Created by dengzhimin on 2017/3/8.
 */

public interface PatrolEntryMvpView extends MvpView {

    void onItemSelected(AdapterView<?> parent, View view, int position, long id);

    void onTakePhoto();

    void onToast(int resId);

    void onToast(String message);

    void onLoadError(int resId);

    void onLoadSuccess();

    void onSaveError(int resId);

    void onSaveSuccess(int resId);

    void onNotifyTypeSpinner(List<DUViolation> violations);

    void onNotifyContentSpinner(List<DUViolation> violations);

    void onNotifyResultSpinner(List<DUWord> words);

    void onUploadPatrols();

}
