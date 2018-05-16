package com.sh3h.indemnity.ui.material.search.apply.info;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.indemnity.ui.base.ParentPresenter;

import javax.inject.Inject;

/**
 * Created by xulongjun on 2017/3/8.
 */

public class MaterialApplyInformationPresenter extends ParentPresenter<MaterialApplyInformationMvpView> {

    @Inject
    public MaterialApplyInformationPresenter(DataManager dataManager) {
        super(dataManager);
    }


}
