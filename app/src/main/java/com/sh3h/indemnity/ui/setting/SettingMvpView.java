package com.sh3h.indemnity.ui.setting;

import com.sh3h.indemnity.ui.base.MvpView;

import java.util.List;

/**
 * Created by dengzhimin on 2017/4/24.
 */

public interface SettingMvpView extends MvpView {

    void onNetWorkSetting();

    void onClearCache();

    void onToast(int resId);

    void onTimeLimit();

    void onCalculateFileSize(List<String> mediaNames);
}
