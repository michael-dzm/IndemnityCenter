package com.sh3h.indemnity.ui.accept.project;

import android.view.View;
import android.widget.AdapterView;

import com.sh3h.dataprovider.data.entity.DUWord;
import com.sh3h.indemnity.ui.base.MvpView;

import java.util.List;

/**
 * Created by xulongjun on 2017/3/8.
 */

public interface AcceptProjectLRMvpView extends MvpView{

    void onTakePhoto();

    void onToast(int resId);

    void onToast(String message);

    void onSaveError(int resId);

    void onSaveSuccess(int resId);

    void onCalendarView(int viewId);

    void onUploadProjectAccept();

    void onGetAcceptResultData(List<DUWord> duWords);

    void onItemSelected(AdapterView<?> parent, View view, int position, long id);
}
