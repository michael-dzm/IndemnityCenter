package com.sh3h.indemnity.ui.upload;

import com.sh3h.indemnity.ui.base.FunctionMvpView;
import com.sh3h.indemnity.ui.base.MvpView;

/**
 * Created by dengzhimin on 2017/4/24.
 */

public interface UploadManagerMvpView extends FunctionMvpView {

    void onUploadOperates();

    void onUploadOperateMultiMedias();

    void onUploadPatrols(int type);

    void onUploadPatrolMultiMedias(int type);

    void onUploadAccepts();

    void onUploadAcceptMultiMedias();

    void onToast(int resId);

    void onToast(String message);

}
