package com.sh3h.indemnity.ui.project.start.entry;

import com.sh3h.indemnity.ui.base.MvpView;

/**
 * Created by dengzhimin on 2017/3/8.
 */

public interface StartProjectEntryMvpView extends MvpView {

    void onTakePhoto(int relateType);

    void onToast(int resId);

    void onToast(String message);

    void onSaveSuccess(int resId);

    void onSaveError(int resId);

    void onUploadOperates();
}
