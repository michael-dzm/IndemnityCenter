package com.sh3h.indemnity.ui.accept.branch;

import android.view.View;
import android.widget.AdapterView;

import com.sh3h.dataprovider.data.entity.DUBranchAccept;
import com.sh3h.dataprovider.data.entity.DUMultiMedia;
import com.sh3h.dataprovider.data.entity.DUWord;
import com.sh3h.indemnity.ui.base.MvpView;

import java.util.List;
import java.util.Map;

/**
 * Created by xulongjun on 2017/3/8.
 */

public interface AcceptBranchLRMvpView extends MvpView {

    void onTakePhoto();

    void onToast(int resId);

    void onError(int resId);

    void onSuccess(int resId);

    void onUploadBranchAccept();

    void onItemSelected(AdapterView<?> parent, View view, int position, long id);

    void onInitAccept(DUBranchAccept accept);

    void onSaveAcceptSuccess(Map<String, Long> resultId);

    void onDeleteAcceptSuccess();

    void onDeleteMultiMediaSuccess(DUMultiMedia multiMedia);

    void onInitMultiMedias(List<DUMultiMedia> multiMedias);

    void onClick(View view);

    void onNotifySpinner(String group, List<DUWord> duWords);

    void onLoadBranchAccept();

}
