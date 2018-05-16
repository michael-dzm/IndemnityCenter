package com.sh3h.indemnity.ui.project.restart.entry;

import com.sh3h.indemnity.ui.base.MvpView;

/**
 * Created by dengzhimin on 2017/3/8.
 */

public interface RestartProjectEntryMvpView extends MvpView {

    void onTakePhoto();

    void onCalendarView(int viewId);

    void onToast(int resId);

    void onToast(String message);

    void onSaveError(int resId);

    void onSaveSuccess(int resId);

    void onUploadOperates();
}
