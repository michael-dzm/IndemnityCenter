package com.sh3h.indemnity.ui.patrol.search;

import android.view.View;
import android.widget.AdapterView;

import com.sh3h.dataprovider.data.entity.DUPatrolSearchResult;
import com.sh3h.dataprovider.data.entity.DUViolation;
import com.sh3h.dataprovider.data.entity.DUWord;
import com.sh3h.indemnity.ui.base.MvpView;

import java.util.List;

/**
 * Created by dengzhimin on 2017/3/8.
 */

public interface SearchPatrolMvpView extends MvpView {

    void onItemSelected(AdapterView<?> parent, View view, int position, long id);

    void onItemClick(DUPatrolSearchResult patrol);

    void onCalendarView(int viewId);

    void onSearch();

    void onToast(int resId);

    void onError(int resId);

    void onNotifyTypeSpinner(List<DUViolation> violations);

    void onNotifyContentSpinner(List<DUViolation> violations);

    void onNotifyResultSpinner(List<DUWord> words);

    void noNotifyDataChanged(List<DUPatrolSearchResult> datas);

}
