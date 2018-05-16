package com.sh3h.indemnity.ui.base;

/**
 * Created by dengzhimin on 2017/5/12.
 */

public interface FunctionMvpView extends MvpView {

    /**
     * 加载数据
     */
    void onLoadData();

    /**
     * 退出
     */
    void onExit(int resId);

    void onCompareUser();

}
