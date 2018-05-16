package com.sh3h.indemnity.ui.material.search.apply;


import com.sh3h.dataprovider.data.entity.DUMaterialApply;
import com.sh3h.indemnity.ui.base.MvpView;

import java.util.List;

/**
 * Created by xulongjun on 2017/4/6.
 */
public interface MaterialApplySearchMvpView extends MvpView {
    void onError(String message);

    void onCalendarView(int viewId);

    void onSearch();

    void onToast(int resId);

    void onSearchMaterialApply(List<DUMaterialApply> duMaterialApplies);
}
