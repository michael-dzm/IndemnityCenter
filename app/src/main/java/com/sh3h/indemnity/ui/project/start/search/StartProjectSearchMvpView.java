package com.sh3h.indemnity.ui.project.start.search;

import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.indemnity.ui.base.FunctionMvpView;
import com.sh3h.indemnity.ui.base.MvpView;

/**
 * Created by dengzhimin on 2017/3/8.
 */

public interface StartProjectSearchMvpView extends FunctionMvpView {

    void onToast(int resId);

    void onError(int resId);

    void onSuccess(DUProject project);

    void onScanCode();

    void onSearchProject(String projectNumber);
}
