package com.sh3h.indemnity.ui.accept;

import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.indemnity.ui.base.FunctionMvpView;

import java.util.List;

/**
 * Created by xulongjun on 2017/3/8.
 */

public interface ProjectAcceptListMvpView extends FunctionMvpView{

    /**
     * 加载工程出错
     * @param action
     * @param resId
     */
    void onError(int action, int resId);

    /**
     * 加载工程完成
     * @param action
     */
    void onLoadFinish(int action);

    /**
     * 加载工程更新界面
     * @param action
     * @param duProjects
     */
    void onNotify(int action, List<DUProject> duProjects);

    void onToast(int resId);

    /**
     * 保存工程操作完成
     * @param resId
     */
    void onSuccess(int resId);

    /**
     * 上传完工操作
     */
    void onUploadOperates();

    /**
     * 跳转至预算界面
     * @param duProject
     */
    void onIntent(DUProject duProject);

    /**
     * 完工
     * @param duProject
     */
    void onFinishProject(DUProject duProject);
}
