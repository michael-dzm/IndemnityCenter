package com.sh3h.indemnity.ui.project.list;

import com.sh3h.dataprovider.data.entity.DUProject;
import com.sh3h.indemnity.ui.base.FunctionMvpView;
import com.sh3h.indemnity.ui.base.MvpView;

import java.util.List;

/**
 * Created by dengzhimin on 2017/3/8.
 */

public interface ProjectListMvpView extends FunctionMvpView {

    /**
     * RecyclerView on item click event
     * @param project
     */
    void onItemClick(DUProject project);

    /**
     * 判断是否显示施工队信息
     * @return
     */
    boolean onVisible();

    void onToast(int resId);

    /**
     * 加载工程出错
     * @param action
     * @param resId
     */
    void onError(int action, int resId);

    /**
     * 加载工程更新界面
     * @param action
     * @param projects
     */
    void onNotify(int action, List<DUProject> projects);

    /**
     * 加载工程完成
     * @param action
     */
    void onLoadFinish(int action);

}
