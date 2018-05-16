package com.sh3h.indemnity.ui.accept.budget;

import com.sh3h.dataprovider.data.entity.DUBudget;
import com.sh3h.indemnity.ui.base.MvpView;

import java.util.List;

/**
 * Created by xulongjun on 2017/3/8.
 */

public interface BudgetAcceptListMvpView extends MvpView{

    void onError(int resId);

    void onToast(int resId);

    void onNotify(List<DUBudget> duBudgets);

    void onIntentByType(String type, DUBudget duBudget);

}
