package com.sh3h.indemnity.ui.material.search.confirm.info;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.indemnity.ui.base.ParentPresenter;

import javax.inject.Inject;

/**
 * Created by xulongjun on 2017/3/8.
 */

public class MaterialConfirmInformationPresenter extends ParentPresenter<MaterialConfirmInformationMvpView> {

    @Inject
    public MaterialConfirmInformationPresenter(DataManager dataManager) {
        super(dataManager);
    }


}
