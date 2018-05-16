package com.sh3h.indemnity.ui.material.search;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.indemnity.ui.base.FunctionPresenter;

import javax.inject.Inject;

/**
 * Created by xulongjun on 2017/4/6.
 */

public class MaterialSearchPresenter extends FunctionPresenter<MaterialSearchMvpView> {

    @Inject
    public MaterialSearchPresenter(DataManager dataManager, ConfigHelper configHelper) {
        super(dataManager, configHelper);
    }
}
